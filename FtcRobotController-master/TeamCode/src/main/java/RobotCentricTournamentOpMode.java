import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "RobotCentricTournamentOpMode")
public class RobotCentricTournamentOpMode extends LinearOpMode
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
    private Servo clawServo;

    @Override
    public void runOpMode() throws InterruptedException {
        WheelMotorLeftFront = hardwareMap.dcMotor.get("WheelMotorLeftFront");
        WheelMotorRightFront = hardwareMap.dcMotor.get("WheelMotorRightFront");
        WheelMotorLeftBack = hardwareMap.dcMotor.get("WheelMotorLeftBack");
        WheelMotorRightBack = hardwareMap.dcMotor.get("WheelMotorRightBack");

        WheelMotorRightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        WheelMotorRightBack.setDirection(DcMotorSimple.Direction.REVERSE);


        chainLeft = hardwareMap.dcMotor.get("chainLeft");
        chainRight = hardwareMap.dcMotor.get("chainRight");
        chainRight.setDirection(DcMotorSimple.Direction.REVERSE);
        chainLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chainRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extendoLeft = hardwareMap.dcMotor.get("extendoLeft");
        extendoRight = hardwareMap.dcMotor.get("extendoRight");
        extendoRight.setDirection(DcMotorSimple.Direction.REVERSE);
        extendoLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendoRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        clawServo = hardwareMap.servo.get("clawServo");

        boolean hangingMode = false;

        while(opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            if(gamepad1.a) {
                denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1) * 2;
            }
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;


            WheelMotorLeftFront.setPower(frontLeftPower);
            WheelMotorLeftBack.setPower(backLeftPower);
            WheelMotorRightFront.setPower(frontRightPower);
            WheelMotorRightBack.setPower(backRightPower);



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


            telemetry.addData("LeftPos: ", chainLeft.getCurrentPosition());
            telemetry.addData("RightPos: ", chainRight.getCurrentPosition());
            telemetry.update();

            if(gamepad2.dpad_right) {
                extendoLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                extendoRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                extendoRight.setPower(1);
                extendoLeft.setPower(1);
            }
            else if(gamepad2.dpad_left) {
                extendoLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                extendoRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                extendoLeft.setPower(-1);
                extendoRight.setPower(-1);
            }
            else if(gamepad2.y) {
                if(hangingMode) {
                    hangingMode = false;
                }
                else {
                    hangingMode = true;
                }
            }
            else if(hangingMode) {
                extendoLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                extendoRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                extendoLeft.setPower(1);
                extendoRight.setPower(1);
            }
            else {
                extendoLeft.setPower(0);
                extendoRight.setPower(0);
            }
            if(gamepad2.right_bumper) {
                clawServo.setPosition(1);
            }
            else if(gamepad2.left_bumper) {
                clawServo.setPosition(0);
            }

        }
    }
}