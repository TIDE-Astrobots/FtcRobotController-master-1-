package HelpfulFunctions;/*
Manual MechanumWheelsTestOp; Created 9/19/24; Last Updated 9/19/24
This op mode is to test the Mecanum Wheels which Anshoul says are superior!

Changelog:
-
 */

//package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


abstract public class TouchSensorFunctions {
    //RevTouchSensor digitalTouch;  // Digital channel Object
    static public RevTouchSensor initializeTouchSensor(HardwareMap hardwareMap, String sensorName) {
        RevTouchSensor touchSensor = hardwareMap.get(RevTouchSensor.class, sensorName);
        return touchSensor;
    }

    static public void addTouchSensorTelemetry(Telemetry telemetry, RevTouchSensor touchSensor) {
        telemetry.addData("Touch Sensor " + touchSensor.getDeviceName() +" is pressed: ", touchSensor.isPressed());
    }


}
