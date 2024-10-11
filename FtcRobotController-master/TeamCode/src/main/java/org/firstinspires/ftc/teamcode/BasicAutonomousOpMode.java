package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;


@Autonomous(name="BasicAutonomousOpMode 10/03/24")
public class BasicAutonomousOpMode extends LinearOpMode {
    private DcMotor WheelMotorLeftFront;
    private DcMotor WheelMotorLeftBack;
    private DcMotor WheelMotorRightFront;
    private DcMotor WheelMotorRightBack;

    @Override
    public void runOpMode() throws InterruptedException {
        WheelMotorLeftFront = hardwareMap.dcMotor.get("WheelMotorLeftFront");
        WheelMotorRightFront = hardwareMap.dcMotor.get("WheelMotorRightFront");
        WheelMotorLeftBack = hardwareMap.dcMotor.get("WheelMotorLeftBack");
        WheelMotorRightBack = hardwareMap.dcMotor.get("WheelMotorRightBack");
        float speedMultipler = 0.05f;
        float ticksPerRevolution = ((((1+(46/17))) * (1+(46/11))) * 28);
        float circumference = 5.512f;
        WheelMotorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        WheelMotorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        WheelMotorLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        WheelMotorRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForStart();

        WheelMotorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        WheelMotorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        WheelMotorLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        WheelMotorRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while(opModeIsActive()) {
            telemetry.addData("Left Fron Pos ", WheelMotorLeftFront.getCurrentPosition());
            telemetry.addData("Right Front Pos ", WheelMotorRightFront.getCurrentPosition());
            telemetry.addData("Left Back Pos ", WheelMotorLeftBack.getCurrentPosition());
            telemetry.addData("Right Back Pos ", WheelMotorRightBack.getCurrentPosition());

            telemetry.addData("-----------------------", "----------------");
            telemetry.addData("Left Fron Pos Calc ", 2 * (Math.abs(WheelMotorLeftFront.getCurrentPosition())*circumference/ticksPerRevolution));
            telemetry.addData("Right Front Pos Calc ", 2 * (Math.abs(WheelMotorRightFront.getCurrentPosition())*circumference/ticksPerRevolution));
            telemetry.addData("Left Back Pos Calc ", 2 * (Math.abs(WheelMotorLeftBack.getCurrentPosition())*circumference/ticksPerRevolution));
            telemetry.addData("Right Back Pos Calc ",  2 * (Math.abs(WheelMotorRightBack.getCurrentPosition())*circumference/ticksPerRevolution));

            telemetry.update();

            if(2 * (Math.abs(WheelMotorRightBack.getCurrentPosition())*circumference/ticksPerRevolution) > 96) {
                WheelMotorLeftFront.setPower(0);
                WheelMotorRightFront.setPower(0);
                WheelMotorLeftBack.setPower(0);
                WheelMotorRightBack.setPower(0);
            }
            else {
                WheelMotorLeftFront.setPower(1);
                WheelMotorRightFront.setPower(-1);
                WheelMotorLeftBack.setPower(1);
                WheelMotorRightBack.setPower(-1);
            }



        }


    }
}
