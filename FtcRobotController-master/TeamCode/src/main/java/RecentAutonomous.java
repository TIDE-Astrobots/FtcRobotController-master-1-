import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import HelpfulFunctions.MotorFunctions;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "Recent Autonomous V1.0 [Updated 11/14/24]")
public class RecentAutonomous extends LinearOpMode {
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
        //region: Initializing Variables
        //These variables do NOT correspond to a physical object; they are entirely digital and for coding purposes.
        float speedMultipler = 0.4f;
        ticksPerRevolution = 537.7f;
        wheelCircumference = 3.780f;

        //This section maps the variables to their corresponding motors/servos
        WheelMotorLeftFront = HelpfulFunctions.MotorFunctions.initializeMotor("WheelMotorLeftFront", hardwareMap);
        WheelMotorRightFront = HelpfulFunctions.MotorFunctions.initializeMotor("WheelMotorRightFront", hardwareMap);
        WheelMotorLeftBack = HelpfulFunctions.MotorFunctions.initializeMotor("WheelMotorLeftBack", hardwareMap);
        WheelMotorRightBack = HelpfulFunctions.MotorFunctions.initializeMotor("WheelMotorRightBack", hardwareMap);

        //This section creates an array of motors that will make some later code easier
        WheelMotors = new DcMotor[4];
        WheelMotors[0] = WheelMotorLeftFront;
        WheelMotors[1] = WheelMotorRightFront;
        WheelMotors[2] = WheelMotorLeftBack;
        WheelMotors[3] = WheelMotorRightBack;

        //This section initializes the motors attached to the chains and sets their settings
        chainLeft = hardwareMap.dcMotor.get("chainLeft");
        chainRight = hardwareMap.dcMotor.get("chainRight");
        chainRight.setDirection(DcMotorSimple.Direction.REVERSE);
        chainLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        chainRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //This section initializes the motors that control the extension arms and sets their settings
        extendoLeft = hardwareMap.dcMotor.get("extendoLeft");
        extendoRight = hardwareMap.dcMotor.get("extendoRight");
        extendoRight.setDirection(DcMotorSimple.Direction.REVERSE);
        extendoLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendoRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //endregion

        //Wait for the user to press start
        waitForStart();
        //called continuously while OpMode is active
        while(opModeIsActive()) {
            /*
            Measurements:
            Circumference = 3.780"
            float ticksPerRevolution = ((((1+(46/17))) * (1+(46/11))) * 28);
            ticksPerRevolution = 537.7
             */
            moveRobotInDirection("right", 0.25f);
        }
    }


    public void moveDistanceInInches(DcMotor[] motors, float distance) {
        //TODO: FINISH THIS FUNC
//            for(DcMotor motor : motors) {
//                //
////                motor.setTargetPosition(Math.round(ticksPerRevolution * wheelCircumference * distance));
////                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//
//            }
        }

        public void moveRobotInDirection(String direction, float speedMultipler) {
            if (direction == "forward") {
                WheelMotorLeftFront.setPower(-1 * speedMultipler);
                WheelMotorRightFront.setPower(1 * speedMultipler);
                WheelMotorLeftBack.setPower(-1 * speedMultipler);
                WheelMotorRightBack.setPower(1 * speedMultipler);
            } else if (direction == "backward") {
                WheelMotorLeftFront.setPower(1 * speedMultipler);
                WheelMotorRightFront.setPower(-1 * speedMultipler);
                WheelMotorLeftBack.setPower(1 * speedMultipler);
                WheelMotorRightBack.setPower(-1 * speedMultipler);
            } else if (direction == "left") {
                WheelMotorLeftFront.setPower(1 * speedMultipler);
                WheelMotorRightFront.setPower(1 * speedMultipler);
                WheelMotorLeftBack.setPower(1 * speedMultipler);
                WheelMotorRightBack.setPower(1 * speedMultipler);
            } else if (direction == "right") {
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