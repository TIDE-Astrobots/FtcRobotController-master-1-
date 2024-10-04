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
import HelpfulFunctions.ColorSensorFunctions;

@TeleOp(name="Color Test Op (10/03/24)")
public class ColorTestOp extends LinearOpMode {
    ColorSensor colorSensor;

    @Override
    public void runOpMode() throws InterruptedException {
        colorSensor = ColorSensorFunctions.initializeTouchSensor(hardwareMap, "colorSensor");
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            ColorSensorFunctions.addColorSensorTelemetry(telemetry, colorSensor, true, true, true);
            telemetry.update();

        }
    }
}
