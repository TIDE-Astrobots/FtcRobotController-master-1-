import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Tournament Op Mode 10/17/24")
public class TournamentOpMode extends LinearOpMode
{
    //these variables correspond to servos and motors. They are displayed in order of distance to Control Hub.
    private DcMotor WheelMotorLeftFront;
    private DcMotor WheelMotorLeftBack;
    private DcMotor WheelMotorRightFront;
    private DcMotor WheelMotorRightBack;
    private DcMotor chainLeft;
    private DcMotor chainRight;
    private DcMotor extendoLeft;
    private DcMotor extendoRight;

    @Override
    public void runOpMode() throws InterruptedException {
        //this block maps the variables to their corresponding motors/servos. 
        WheelMotorLeftFront = hardwareMap.dcMotor.get("WheelMotorLeftFront");
        WheelMotorRightFront = hardwareMap.dcMotor.get("WheelMotorRightFront");
        WheelMotorLeftBack = hardwareMap.dcMotor.get("WheelMotorLeftBack");
        WheelMotorRightBack = hardwareMap.dcMotor.get("WheelMotorRightBack");

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
        float speedMultipler = 0.4f;
        waitForStart();

        //called continuously while OpMode is active
        while(opModeIsActive()) {
            if(gamepad1.dpad_up) {
                WheelMotorLeftFront.setPower(-1 * speedMultipler);
                WheelMotorRightFront.setPower(1 * speedMultipler);
                WheelMotorLeftBack.setPower(-1 * speedMultipler);
                WheelMotorRightBack.setPower(1 * speedMultipler);
            } else if(gamepad1.dpad_down) {
                WheelMotorLeftFront.setPower(1 * speedMultipler);
                WheelMotorRightFront.setPower(-1 * speedMultipler);
                WheelMotorLeftBack.setPower(1 * speedMultipler);
                WheelMotorRightBack.setPower(-1 * speedMultipler);
            } else if(gamepad1.dpad_right) {
                WheelMotorLeftFront.setPower(-1 * speedMultipler);
                WheelMotorRightFront.setPower(-1 * speedMultipler);
                WheelMotorLeftBack.setPower(-1 * speedMultipler);
                WheelMotorRightBack.setPower(-1 * speedMultipler);
            }
            else if(gamepad1.dpad_left) {
                WheelMotorLeftFront.setPower(1 * speedMultipler);
                WheelMotorRightFront.setPower(1 * speedMultipler);
                WheelMotorLeftBack.setPower(1 * speedMultipler);
                WheelMotorRightBack.setPower(1 * speedMultipler);
            } else {
                WheelMotorLeftFront.setPower(0);
                WheelMotorRightFront.setPower(0);
                WheelMotorLeftBack.setPower(0);
                WheelMotorRightBack.setPower(0);
            }

            if(gamepad2.dpad_up) {
                chainLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainLeft.setPower(1);
                chainRight.setPower(1);
            }
            else if (gamepad2.dpad_down) {
                chainLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                chainLeft.setPower(-1);
                chainRight.setPower(-1);
            }
            else if (gamepad2.a) {
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






























//            if(gamepad1.dpad_up) {
//                WheelMotorLeftFront.setPower(-1);
//                WheelMotorRightFront.setPower(1);
//                WheelMotorLeftBack.setPower(-1);
//                WheelMotorRightBack.setPower(1);
//            }
//            else {
//                WheelMotorLeftFront.setPower(0);
//                WheelMotorRightFront.setPower(0);
//                WheelMotorLeftBack.setPower(0);
//                WheelMotorRightBack.setPower(0);
//            }
//            if(gamepad1.dpad_down) {
//                WheelMotorLeftFront.setPower(1);
//                WheelMotorRightFront.setPower(-1);
//                WheelMotorLeftBack.setPower(1);
//                WheelMotorRightBack.setPower(-1);
//            }
//            else {
//                WheelMotorLeftFront.setPower(0);
//                WheelMotorRightFront.setPower(0);
//                WheelMotorLeftBack.setPower(0);
//                WheelMotorRightBack.setPower(0);
//            }

            //idle();
        }
    }
}