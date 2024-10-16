/*
Main_TwoCs, created 1/29/23
This file contains code which allows users to move up to the second servo on the robot's arm. This configuration works only with
two controllers, the first uses tank controls to move WheelMotorLeftFront with the left stick's y-axis and WheelMotorRightFront with the
right stick's y-axis. The second controller uses the right stick's x-axis to rotate the base motor of the arm.
Left and right bumper are used to rotate the second servo and the left/right triggers are used to rotate the first servo.
 */

/* CHANGES (1/29/23)
1. commented out all uses of "arm"
2. renamed "Paddle" to "arm_base"
3. renamed "Armmotor2" to "claw_direction_servo"
4. renamed "Armjoint1" to "extension_servo"
5. Added comments to help readability
6. Added and commented out Relative Positions for Servos
7. Added real positions and directions to telemetry
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name = "Arm Functionality Test 10/03/24")
public class ArmFunctionalityTest extends LinearOpMode
{
    //these variables correspond to servos and motors. They are displayed in order of distance to Control Hub.

    private DcMotor chainLeft;
    private DcMotor chainRight;
    private DcMotor extendoLeft;
    private DcMotor extendoRight;
    private float speedMultipler = 0.4f;


    @Override
    public void runOpMode() throws InterruptedException {
        //this block maps the variables to their corresponding motors/servos. 
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
        waitForStart();

        //called continuously while OpMode is active
        while(opModeIsActive()) {
            if(gamepad1.dpad_up) {
                chainLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainLeft.setPower(1);
                chainRight.setPower(1);
            }
            else if (gamepad1.dpad_down) {
                chainLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainLeft.setPower(-1);
                chainRight.setPower(-1);
            }
            else if (gamepad1.a) {
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

            if(gamepad1.dpad_right) {
                extendoLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                extendoRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                extendoRight.setPower(1);
                extendoLeft.setPower(1);
            }
            else if(gamepad1.dpad_left) {
                extendoLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                extendoRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                extendoLeft.setPower(-1);
                extendoRight.setPower(-1);
            }
            else {
                extendoLeft.setTargetPosition(extendoLeft.getCurrentPosition());
                extendoRight.setTargetPosition(extendoRight.getCurrentPosition());
                extendoLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                extendoRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }



        }
    }
}