import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "TournamentOpMode V1.0 [Updated 11/14/24]")
public class TournamentOpMode extends LinearOpMode

{
    //region: Creating Variables
    private DcMotor WheelMotorLeftFront;
    private DcMotor WheelMotorLeftBack;
    private DcMotor WheelMotorRightBack;
    private DcMotor WheelMotorRightFront;
    private DcMotor chainLeft;
    private DcMotor chainRight;
    private DcMotor extendoLeft;
    private DcMotor extendoRight;
    private Servo clawServo;
    private boolean hangingMode;
    //endregion

    @Override
    public void runOpMode() throws InterruptedException {
        //region: Initialize Variables
        //These variables do NOT correspond to a physical object; they are entirely digital and for coding purposes.
        hangingMode = false;

        //This section maps the variables to their corresponding motors/servos.
        WheelMotorLeftFront = HelpfulFunctions.MotorFunctions.initializeMotor("WheelMotorLeftFront", hardwareMap);
        WheelMotorLeftBack = HelpfulFunctions.MotorFunctions.initializeMotor("WheelMotorLeftBack", hardwareMap);
        WheelMotorRightFront = HelpfulFunctions.MotorFunctions.initializeMotor("WheelMotorRightFront", hardwareMap);
        WheelMotorRightBack = HelpfulFunctions.MotorFunctions.initializeMotor("WheelMotorRightBack", hardwareMap);

        //This section sets the directions of motors which should move in reverse so the robot works.
        WheelMotorRightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        WheelMotorRightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        //This section initializes the motors attached to the chains and sets their settings
        chainLeft = hardwareMap.dcMotor.get("chainLeft");
        chainRight = hardwareMap.dcMotor.get("chainRight");
        chainRight.setDirection(DcMotorSimple.Direction.REVERSE);
        chainLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chainRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //This section initializes the motors that control the extension arms and sets their settings
        extendoLeft = hardwareMap.dcMotor.get("extendoLeft");
        extendoRight = hardwareMap.dcMotor.get("extendoRight");
        extendoRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //Use BRAKE zero power behavior so that the motors do not allow the arms to move when no power is applied
        extendoLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendoRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //This section initializes the claw servo
        clawServo = hardwareMap.servo.get("clawServo");
        //endregion

        //region: Initialize the IMU for navigation
        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        //endregion

        //Wait for the user to hit start
        waitForStart();
        //Called continuously while OpMode is active
        while(opModeIsActive()) {
            // This button choice was made so that it is hard to hit on accident.
            // This will reset the robot's navigation
            if (gamepad1.options) {
                imu.resetYaw();
            }

            //region: Move the wheels when the user moves the joystick
            double x = -gamepad1.left_stick_x; // Remember, Y stick value is reversed
            double y = gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;

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

            //Allow the users to move at half speed if they are holding A
            if(gamepad1.a) {
                 denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1) * 2;
                 WheelMotorFrontLeftPower = (rotY + rotX + rx) / denominator;
                 WheelMotorBackLeftPower = (rotY - rotX + rx) / denominator;
                 WheelMotorFrontRightPower = (rotY - rotX - rx) / denominator;
                 WheelMotorBackRightPower = (rotY + rotX - rx) / denominator;
            }

            //Apply the power
            WheelMotorLeftFront.setPower(WheelMotorFrontLeftPower);
            WheelMotorLeftBack.setPower(WheelMotorBackLeftPower);
            WheelMotorRightFront.setPower(WheelMotorFrontRightPower);
            WheelMotorRightBack.setPower(WheelMotorBackRightPower);
            //endregion

            //region: Chain controls
            if(gamepad2.dpad_up) {
                chainLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainLeft.setPower(1);
                chainRight.setPower(1);
            }
            else if (gamepad2.dpad_down) {
                chainLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainLeft.setPower(0);
                chainRight.setPower(0);
            }
            else if(gamepad2.a){
                chainLeft.setTargetPosition(500);
                chainRight.setTargetPosition(500);
                chainLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                chainRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            else {
                chainLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainLeft.setPower(0);
                chainRight.setPower(0);
            }
            //endregion

            //region: Extendo arm controls
            if(gamepad2.dpad_right) {
                extendoRight.setPower(1);
                extendoLeft.setPower(1);
            }
            else if(gamepad2.dpad_left) {
                extendoLeft.setPower(-1);
                extendoRight.setPower(-1);
            }
            else if(gamepad2.y) {
                //Make the robot start or stop hanging by setting hangingMode to true or false
                if(hangingMode) {
                    hangingMode = false;
                }
                else {
                    hangingMode = true;
                }
            }
            else if(hangingMode) {
                //If the robot has entered hanging mode, keep it hanging!
                extendoLeft.setPower(1);
                extendoRight.setPower(1);
            }
            else {
                //If the robot is not hanging and nothing is being pressed, apply no power to the arm
                extendoLeft.setPower(0);
                extendoRight.setPower(0);
            }
            //endregion

            //region: Claw controls
            if(gamepad2.right_bumper) {
                clawServo.setPosition(1);
            }
            else if(gamepad2.left_bumper) {
                clawServo.setPosition(0);
            }
            //endregion

eeee;

            Integer num1 = 2;
            Integer num2 = 3;
            Integer num3 = num1 + num2;

            Integer num4 = 200;
            Integer num5 = 400;
            Integer num6 = num4 + num5;
        }
    }
}