/*
Manual MechanumWheelsTestOp; Created 9/19/24; Last Updated 9/19/24
This op mode is to test the Mecanum Wheels which Anshoul says are superior!

Changelog:
-
 */

//package org.firstinspires.ftc.teamcode;

import android.text.method.Touch;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import HelpfulFunctions.TouchSensorFunctions;

@TeleOp(name="MechanumTest")
public class MechanumWheelsTestOp extends LinearOpMode {
    RevTouchSensor digitalTouch;  // Digital channel Object

    @Override
    public void runOpMode() throws InterruptedException {
        digitalTouch = TouchSensorFunctions.initializeTouchSensor(hardwareMap, "touchSensor");
        //digitalTouch = hardwareMap.get(RevTouchSensor.class, "touchSensor");

        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor WheelMotorFrontLeft = hardwareMap.dcMotor.get("WheelMotorLeftFront");
        DcMotor WheelMotorBackLeft = hardwareMap.dcMotor.get("WheelMotorLeftBack");
        DcMotor WheelMotorFrontRight = hardwareMap.dcMotor.get("WheelMotorRightFront");
        DcMotor WheelMotorBackRight = hardwareMap.dcMotor.get("WheelMotorRightBack");

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        WheelMotorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        WheelMotorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
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

            if(gamepad1.dpad_up) {
                WheelMotorBackLeftPower = 1;
                WheelMotorBackRightPower = 1;
                WheelMotorFrontLeftPower = 1;
                WheelMotorFrontRightPower = 1;
            }
            if (digitalTouch.isPressed() == false) {
                telemetry.addData("Button", "PRESSED");
            } else {
                telemetry.addData("Button", "NOT PRESSED");
            }
            telemetry.addData("Current state: ", digitalTouch.isPressed());

            telemetry.update();

            WheelMotorFrontLeft.setPower(WheelMotorFrontLeftPower);
            WheelMotorBackLeft.setPower(WheelMotorBackLeftPower);
            WheelMotorFrontRight.setPower(WheelMotorFrontRightPower);
            WheelMotorBackRight.setPower(WheelMotorBackRightPower);

        }
    }
}
