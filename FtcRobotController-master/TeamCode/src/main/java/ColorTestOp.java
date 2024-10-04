/*
Manual MechanumWheelsTestOp; Created 9/19/24; Last Updated 9/19/24
This op mode is to test the Mecanum Wheels which Anshoul says are superior!

Changelog:
-
 */

//package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import android.text.method.Touch;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import HelpfulFunctions.TouchSensorFunctions;

@TeleOp(name="Color Test Op (10/03/24)")
public class ColorTestOp extends LinearOpMode {
    ColorSensor colorSensor;

    @Override
    public void runOpMode() throws InterruptedException {
        colorSensor = hardwareMap.colorSensor.get("colorSensor");
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            telemetry.addData("Colors: ", colorSensor.argb());
            telemetry.addData("Red: ", colorSensor.red());
            telemetry.addData("Blue: ", colorSensor.blue());
            telemetry.addData("Green: ", colorSensor.green());
            telemetry.addData("Alpha: ", colorSensor.alpha());
            if(gamepad1.dpad_up) {
                colorSensor.enableLed(true);
            }
            else if(gamepad1.dpad_down) {
                colorSensor.enableLed(false);

            }
            telemetry.update();

        }
    }
}
