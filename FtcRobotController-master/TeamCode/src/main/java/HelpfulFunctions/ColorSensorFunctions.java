package HelpfulFunctions;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


abstract public class ColorSensorFunctions {
    static public ColorSensor initializeTouchSensor(HardwareMap hardwareMap, String sensorName) {
        ColorSensor colorSensor = hardwareMap.colorSensor.get(sensorName);
        return colorSensor;
    }

    static public void addColorSensorTelemetry(Telemetry telemetry, ColorSensor colorSensor, boolean blue, boolean green, boolean red) {
        telemetry.addData("---------------------------------------------------------", "");
        telemetry.addData("Color Sensor Telemetry:", "");
        if(blue) {
            telemetry.addData("Blue: ", colorSensor.blue());
        }
        if(green) {
            telemetry.addData("Green ", colorSensor.green());
        }
        if(red) {
            telemetry.addData("Red: ", colorSensor.red());
        }
        telemetry.addData("---------------------------------------------------------", "");

    }


}
