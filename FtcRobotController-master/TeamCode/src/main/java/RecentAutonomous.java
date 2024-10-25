import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "Tournament Op Mode 10/17/24")
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
                motor.setTargetPosition(Math.round(ticksPerRevolution * wheelCircumference * distance));
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
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

        //y axis : numbers 1 - 6
        //x axis: letters a - f

    public class Graph {
        public List<Vertex> graph = new ArrayList<Vertex>();
        public void addVertex(String vertexName, List<Float> vertexCoordinates, List<Vertex> vertexConnections, List<Float>connectionWeights) {
            /*
            vertexName: the name of this vertex
            vertexCoordinates: A list with two values; [x position, y position]
            vertexConnections: A list of lists used to create edges. The inner list contains:
                                [destinationName, weight]. The source is this vertex, so it is not needed here.
             */
            Vertex vertex = new Vertex(vertexName, vertexCoordinates);
            //Create list of edges
            List<Edge> connectedEdges = new ArrayList<Edge>();

            //Take information from the parameters and add them to the connectedEdges
            for(int index = 0; index < vertexConnections.size(); index++) {
                //Pull information from parameters
                Vertex connectedVertex = vertexConnections.get(index);
                float weight = connectionWeights.get(index);
                //Put information from parameters into a list
                List<Vertex> connections = new ArrayList<Vertex>();
                connections.add(vertex);
                connections.add(connectedVertex);
                //Create new edge and add it to the list
                connectedEdges.add(new Edge(weight, connections));
            }

            //Add new vertex to graph
            graph.add(vertex);
        }
    }
    public class Vertex {
        public String vertexName;
        public List<Float> coordinates;
        public List<Edge> connectedEdges;

        public Vertex(String vertexName, List<Float> vertexCoordinates) {
            this.vertexName = vertexName;
            this.coordinates = vertexCoordinates;
        }

        public void addEdge(Edge edge) {
            this.connectedEdges.add(edge);
        }

    }
    public class Edge {
        public float weight;
        public List<Vertex> connectedVertices;

        public Edge(float weight, List<Vertex> connectedVertices) {
            this.weight = weight;
            this.connectedVertices = connectedVertices;
        }
    }

    /*
    BOARD:
    A6 B6 C6 D6 E6 F6
    A5 B5 C5 D5 E5 F5
    A4 B4 C4 D4 E4 F4
    A3 B3 C3 D3 E3 F3
    A2 B2 C2 D2 E2 F2
    A1 B1 C1 D1 E1 F1


    Note that the 7's and G's are the "shadow realm" and represent the edge of the board
    Spike mark points:
        Bottom Left:
            C1, C1.5, C2
        Bottom RIght:
            D1, D1.5, D2
        Top Left:
            C6, C6.5, C7
        Top Right:
            E6, E6.5, E7

    Net Zone Postions:
        Blue: A7
        Red: G1
        TETSTSTTETSTTETSAIYDOAUID OQW

     */

    }