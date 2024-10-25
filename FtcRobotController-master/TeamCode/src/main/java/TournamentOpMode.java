import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "Tournament Op Mode 10/17/24")
public class TournamentOpMode extends LinearOpMode
{
    //these variables correspond to servos and motors. They are displayed in order of distance to Control Hub.
    private DcMotor WheelMotorLeftFront;
    private DcMotor WheelMotorLeftBack;
    private DcMotor WheelMotorRightBack;
    private DcMotor WheelMotorRightFront;
    private DcMotor chainLeft;
    private DcMotor chainRight;
    private DcMotor extendoLeft;
    private DcMotor extendoRight;

    @Override
    public void runOpMode() throws InterruptedException {
        //this block maps the variables to their corresponding motors/servos. 
        WheelMotorLeftFront = hardwareMap.dcMotor.get("WheelMotorLeftFront");
        WheelMotorRightFront = hardwareMap.dcMotor.get("WheelMotorRightFront");
        WheelMotorLeftBack = hardwareMap.dcMotor.get("WheelMotorLeftBack");
        WheelMotorRightBack = hardwareMap.dcMotor.get("WheelMotorRightBack");

        chainLeft = hardwareMap.dcMotor.get("chainLeft");
        chainRight = hardwareMap.dcMotor.get("chainRight");
        chainRight.setDirection(DcMotorSimple.Direction.REVERSE);
        chainLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        chainRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        extendoLeft = hardwareMap.dcMotor.get("extendoLeft");
        extendoRight = hardwareMap.dcMotor.get("extendoRight");
        extendoRight.setDirection(DcMotorSimple.Direction.REVERSE);
        extendoLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendoRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        WheelMotorRightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        WheelMotorRightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        float speedMultipler = 0.4f;
        boolean shouldSetPosition = true;
        waitForStart();

        //called continuously while OpMode is active
        while(opModeIsActive()) {
            double x = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double y = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.
            if (gamepad1.options) {
                imu.resetYaw();
            }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double WheelMotorFrontLeftPower = (rotY + rotX + rx) / denominator;
            double WheelMotorBackLeftPower = (rotY - rotX + rx) / denominator;
            double WheelMotorFrontRightPower = (rotY - rotX - rx) / denominator;
            double WheelMotorBackRightPower = (rotY + rotX - rx) / denominator;



            if(gamepad2.dpad_up) {
                chainLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainLeft.setPower(1);
                chainRight.setPower(1);
            }
            else if (gamepad2.dpad_down) {
                chainLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainLeft.setPower(-1);
                chainRight.setPower(-1);
            }
            else if (gamepad2.a) {
                chainLeft.setTargetPosition(chainLeft.getCurrentPosition());
                chainRight.setTargetPosition(chainLeft.getCurrentPosition());
                chainLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                chainRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                chainLeft.setPower(1);
                chainRight.setPower(1);

            }
            else {
                chainLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainLeft.setPower(0);
                chainRight.setPower(0);
            }

            if(gamepad2.dpad_right) {
                extendoLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                extendoRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                extendoRight.setPower(1);
                extendoLeft.setPower(1);
                shouldSetPosition = true;
            }
            else if(gamepad2.dpad_left) {
                extendoLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                extendoRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                extendoLeft.setPower(-1);
                extendoRight.setPower(-1);
                shouldSetPosition = true;
            }
            else {
                if(shouldSetPosition) {
                    extendoLeft.setTargetPosition(extendoLeft.getCurrentPosition());
                    extendoRight.setTargetPosition(extendoRight.getCurrentPosition());
                    extendoLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    extendoRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    shouldSetPosition = false;
                }

            }

            WheelMotorLeftFront.setPower(WheelMotorFrontLeftPower);
            WheelMotorLeftBack.setPower(WheelMotorBackLeftPower);
            WheelMotorRightFront.setPower(WheelMotorFrontRightPower);
            WheelMotorRightBack.setPower(WheelMotorBackRightPower);






























//            if(gamepad1.dpad_up) {
//                WheelMotorLeftFront.setPower(-1);
//                WheelMotorRightFront.setPower(1);
//                WheelMotorLeftBack.setPower(-1);
//                WheelMotorRightBack.setPower(1);
//            }
//            else {
//                WheelMotorLeftFront.setPower(0);
//                WheelMotorRightFront.setPower(0);
//                WheelMotorLeftBack.setPower(0);
//                WheelMotorRightBack.setPower(0);
//            }
//            if(gamepad1.dpad_down) {
//                WheelMotorLeftFront.setPower(1);
//                WheelMotorRightFront.setPower(-1);
//                WheelMotorLeftBack.setPower(1);
//                WheelMotorRightBack.setPower(-1);
//            }
//            else {
//                WheelMotorLeftFront.setPower(0);
//                WheelMotorRightFront.setPower(0);
//                WheelMotorLeftBack.setPower(0);
//                WheelMotorRightBack.setPower(0);
//            }

            //idle();
        }
    }
}