import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Tournament Op Mode 10/17/24")
public class RecentAutonomous extends LinearOpMode
{
    //region: Creating Variables

    //these variables correspond to servos and motors. They are displayed in order of distance to Control Hub.
    private DcMotor WheelMotorLeftFront;
    private DcMotor WheelMotorLeftBack;
    private DcMotor WheelMotorRightBack;
    private DcMotor WheelMotorRightFront;
    private DcMotor chainLeft;
    private DcMotor chainRight;
    private DcMotor extendoLeft;
    private DcMotor extendoRight;
    private DcMotor[] WheelMotors;
    private float ticksPerRevolution;
    private float wheelCircumference;
    //endregion

    @Override
    public void runOpMode() throws InterruptedException {
        //region: Mapping variables
        //this block maps the variables to their corresponding motors/servos.
        WheelMotorLeftFront = hardwareMap.dcMotor.get("WheelMotorLeftFront");
        WheelMotorRightFront = hardwareMap.dcMotor.get("WheelMotorRightFront");
        WheelMotorLeftBack = hardwareMap.dcMotor.get("WheelMotorLeftBack");
        WheelMotorRightBack = hardwareMap.dcMotor.get("WheelMotorRightBack");
        WheelMotors = new DcMotor[4];
        WheelMotors[0] = WheelMotorLeftFront;
        WheelMotors[1] = WheelMotorRightFront;
        WheelMotors[2] = WheelMotorLeftBack;
        WheelMotors[3] = WheelMotorRightBack;
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

        //endregion

        float speedMultipler = 0.4f;
        ticksPerRevolution = 537.7f;
        wheelCircumference = 3.780f;
        waitForStart();

        //called continuously while OpMode is active
        while(opModeIsActive()) {
            /*
            Measurements:
            Circumference = 3.780"
            float ticksPerRevolution = ((((1+(46/17))) * (1+(46/11))) * 28);
            ticksPerRevolution = 537.7
             */
        }
    }

    public void moveDistanceInInches(DcMotor[] motors, float distance) {
        for(DcMotor motor : motors) {
            //
            motor.setTargetPosition(Math.round(ticksPerRevolution * wheelCircumference * distancec));
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public void moveRobotInDirection(String direction, float speedMultipler) {
        if(direction == "forward") {
            WheelMotorLeftFront.setPower(-1 * speedMultipler);
            WheelMotorRightFront.setPower(1 * speedMultipler);
            WheelMotorLeftBack.setPower(-1 * speedMultipler);
            WheelMotorRightBack.setPower(1 * speedMultipler);
        }
        else if(direction == "backward") {
            WheelMotorLeftFront.setPower(1 * speedMultipler);
            WheelMotorRightFront.setPower(-1 * speedMultipler);
            WheelMotorLeftBack.setPower(1 * speedMultipler);
            WheelMotorRightBack.setPower(-1 * speedMultipler);
        }
        else if(direction == "left") {
            WheelMotorLeftFront.setPower(1 * speedMultipler);
            WheelMotorRightFront.setPower(1 * speedMultipler);
            WheelMotorLeftBack.setPower(1 * speedMultipler);
            WheelMotorRightBack.setPower(1 * speedMultipler);
        }
        else if(direction == "right") {
            WheelMotorLeftFront.setPower(-1 * speedMultipler);
            WheelMotorRightFront.setPower(-1 * speedMultipler);
            WheelMotorLeftBack.setPower(-1 * speedMultipler);
            WheelMotorRightBack.setPower(-1 * speedMultipler);
        }
    }
    public void stopRobot() {
        WheelMotorLeftFront.setPower(0);
        WheelMotorRightFront.setPower(0);
        WheelMotorLeftBack.setPower(0);
        WheelMotorRightBack.setPower(0);
    }
}