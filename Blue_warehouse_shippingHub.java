package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;



@Autonomous(name="blue_warehouse_shippingHub" , group = "blue")
public class Blue_warehouse_shippingHub extends LinearOpMode {


    private DcMotor leftFront; // port 0
    private DcMotor rightFront; //
    private DcMotor leftRear;
    private DcMotor rightRear;
    private DcMotor arm_lift;
    private DcMotor suction;
    private DcMotor duck;
    private CRServo lift;
    private CRServo shipping_element;
    private ElapsedTime runtime = new ElapsedTime();


    //============================== ROBOT SETTING ===========================

    final double diameter = 9.6; // wheel diameter in cm
    final double PPR = 537.7; // 312RPM DC motor
    final double lifter_PPR = 3895.9; //43 RPM motor.



    //=========================== ROBOT SETTING End===========================

    public int convert_cm_to_pulses(double distance)
    {

        double wheel_circumference	=	diameter * Math.PI;
        //	(360 / wheel_circumference)
        telemetry.addData(" wheel_circumference", wheel_circumference);


        double distance_in_pulses =(PPR/wheel_circumference)*distance;
        telemetry.addData(" distance_in_pulses", distance_in_pulses);
        telemetry.update();

        return (int)distance_in_pulses;
    }

    public void setup_robot(){

        leftFront = hardwareMap.get(DcMotor.class, "left_top");

        rightFront = hardwareMap.get(DcMotor.class, "right_top");

        leftRear = hardwareMap.get(DcMotor.class, "left_bot");

        rightRear = hardwareMap.get(DcMotor.class, "right_bot");

        //arm_rotator = hardwareMap.get(DcMotor.class, "arm_rotator");

        arm_lift = hardwareMap.get(DcMotor.class, "arm_lift");

        suction = hardwareMap.get(DcMotor.class, "suction");

        duck = hardwareMap.get(DcMotor.class, "duck");

        lift = hardwareMap.get(CRServo.class, "lift");

        telemetry.clearAll();

        telemetry.setAutoClear(false);

        //arm_extender.setDirection(DcMotorSimple.Direction.REVERSE);
        //arm_extender.setPower(1);
        //sleep(500);
        //arm_extender.setPower(0);

    }


    public void drive(String direction, int distance , double speed, String pumping){
				/*
						Direction:

						1.foward --> move the robot foward or F
						2.reverse -- > move the robot backward or B
						3.left --> move the robot left however it's not make a turn OR L
						4.right --> move the robot right however it's not make a turn

						distance measured in cm -- that's mean centimeters and can not be a negative number

						speed - value from 0 to 1 , can not be a negative number



				*/


        int position;



        if (direction == "forward" || direction == "F"){
            rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
            rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
            leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
            leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        else if (direction == "reverse" || direction == "B"){
            rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
            rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
            leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
            leftRear.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        else if (direction == "right" || direction == "R"){
            rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
            rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
            leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
            leftRear.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        else if (direction == "left" || direction == "L"){
            rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
            rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
            leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
            leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        }


        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //distance should be a postive number
        distance = Math.abs(distance);

        position =	convert_cm_to_pulses(distance);

        rightFront.setTargetPosition(position);
        rightRear.setTargetPosition(position);
        leftFront.setTargetPosition(position);
        leftRear.setTargetPosition(position);

        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rightFront.setPower(speed);
        rightRear.setPower(speed);
        leftFront.setPower(speed);
        leftRear.setPower(speed);
        if (pumping != null) {
            if (pumping == "in") {
                suction.setDirection(DcMotorSimple.Direction.FORWARD);
                suction.setPower(0.9);
            } else if (pumping == "out") {
                suction.setDirection(DcMotorSimple.Direction.REVERSE);
                suction.setPower(0.9);
            }
        }


        while ((rightFront.getCurrentPosition() < position) || (rightRear.getCurrentPosition() < position) || (leftRear.getCurrentPosition() < position)|| (leftFront.getCurrentPosition() < position)){

        }
        suction.setPower(0);

    }

    /*
    public void arm_rotation(String direction, double degrees ,double speed){

				/*
						Direction:

						1.right --> rotate the arm right
						2.left --> rotate the arm left


        if (direction == "right" || direction== "R"){
            arm_rotator.setDirection(DcMotorSimple.Direction.FORWARD);
            telemetry.addData("ARM ROTATOR: Moving Right to ", degrees +"degrees");
        }
        else if (direction == "left" || direction=="L"){
            arm_rotator.setDirection(DcMotorSimple.Direction.REVERSE);
            telemetry.addData("ARM ROTATOR: Moving Left to ", degrees +"degrees");

        }
        telemetry.update();

        double degrees_in_pulses = PPR/360;
        degrees = (degrees_in_pulses * degrees)*6;
        arm_rotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm_rotator.setTargetPosition((int)(degrees)); //
        arm_rotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm_rotator.setPower(speed);

        while (arm_rotator.getCurrentPosition() < degrees){

        }




    }*/


    public void arm_lift_auto(int position ,String direction)
    {
        //43 RPM motor
        double  height = 0;
        arm_lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        if (direction == "up") {
            arm_lift.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        else if (direction == "down"){
            arm_lift.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        arm_lift.setPower(0.7);

        if (position == 1) {
                double degrees_in_pulses = lifter_PPR / 360;
                height = (degrees_in_pulses * 180) * 4;
        }

        /*else if (direction == "down"){
            arm_lift.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        double degrees_in_pulses = lifter_PPR/360;
        degrees = (degrees_in_pulses * degrees)*4;

         */
        arm_lift.setTargetPosition((int)height);

        //arm_lift.setTargetPosition((int)(PPR * precent) * 4); //
        arm_lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        while (arm_lift.getCurrentPosition() < height){

        }
    }



    public void throw_out(String direction , float time , double speed){
        telemetry.addLine();
        telemetry.addData("throe out","");
        telemetry.update();
        /*
         direction --> in or out   OR   I for in  and O for out
         time in seconds!
         speed between 0 to 1
         */


        if (direction == "in" || direction == "I"){
            lift.setDirection(DcMotorSimple.Direction.FORWARD);

        }
        if (direction == "out" || direction == "O" ){
            lift.setDirection(DcMotorSimple.Direction.REVERSE);

        }

        suction.setPower(speed);
        //wait x seconds
        sleep((long) (time * 1000));
        suction.setPower(0);
    }





    /*
    3 Main Functions:
    To Lift The Arm Up Or Down:
        arm_lift_auto(direction: "up" or "down", degrees: double, speed: double)
    To Drive:
        drive(direction: "F", "B", "R" or "L", distance: int, speed: double)
    To Rotate The Arm:
        arm_rotation(direction: "L" or "R", degrees: double, speed: double)
    To Use The Sucking Mechanism:
        pumping(direction: "in" or "out", time: seconds, speed: double);
     */
    public void runOpMode() {

        //init the HW's robot
        setup_robot();

        //while play button had pressed
        waitForStart();
        //Reset the timer
        runtime.reset();






        //===================================== PROGRAM ================================
        //pumping a cube from the start
        drive("L", 44, 0.65, null);


        drive("B",60,0.65, null);
        arm_lift_auto(1 , "up");
        throw_out("out",1,1); //throw out the cube to the shipping hub



        //===============================================================================

        drive("L", 65, 0.65, null); // go back to the wall
        arm_lift_auto(1,"down"); //down the arm

        drive("F", 175, 0.65, "in");// go to the wearhouse and in parallel pump in element
        sleep(1300);

    }
}
    /*
    alliance_starting point_duck/preloaded_parking

        alliance = red/blue
        starting point = near the storage (storage) / near the warehouse (warehouse)
        duck/preloaded or both
        parking = storage/warehouse
        1150=bottom level position for arm lift to drop cube
        1750=mid level position for arm lift to drop cube
        1950=top level position for arm lift to drop cube
        back wheel distance from shipping hub to drop cubes- 15 cm

     */