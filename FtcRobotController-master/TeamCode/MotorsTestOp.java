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
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "MotorsTestOp")
public class MotorsTestOp extends LinearOpMode
{
    //these variables correspond to servos and motors. They are displayed in order of distance to Control Hub.
    private DcMotor WheelMotorLeftFront;
    private DcMotor WheelMotorLeftBack;
    private DcMotor WheelMotorRightFront;
    private DcMotor WheelMotorRightBack;

    @Override
    public void runOpMode() throws InterruptedException {

        //this block maps the variables to their corresponding motors/servos.
        WheelMotorLeftFront = hardwareMap.dcMotor.get("WheelMotorLeftFront");
        WheelMotorRightFront = hardwareMap.dcMotor.get("WheelMotorRightFront");
        WheelMotorLeftBack = hardwareMap.dcMotor.get("WheelMotorLeftBack");
        WheelMotorRightBack = hardwareMap.dcMotor.get("WheelMotorRightBack");
        waitForStart();

        //called continuously while OpMode is active
        while(opModeIsActive()) {
            if(gamepad1.dpad_up) {
                WheelMotorLeftFront.setPower(1);
                WheelMotorRightFront.setPower(1);
                WheelMotorLeftBack.setPower(1);
                WheelMotorRightBack.setPower(1);
            }
            else {
                WheelMotorLeftFront.setPower(0);
                WheelMotorRightFront.setPower(0);
                WheelMotorLeftBack.setPower(0);
                WheelMotorRightBack.setPower(0);
            }
            if(gamepad1.dpad_down) {
                WheelMotorLeftFront.setPower(-1);
                WheelMotorRightFront.setPower(-1);
                WheelMotorLeftBack.setPower(-1);
                WheelMotorRightBack.setPower(-1);
            }
            else {
                WheelMotorLeftFront.setPower(0);
                WheelMotorRightFront.setPower(0);
                WheelMotorLeftBack.setPower(0);
                WheelMotorRightBack.setPower(0);
            }

            //idle();
        }
    }
}