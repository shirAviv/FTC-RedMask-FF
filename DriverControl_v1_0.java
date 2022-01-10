package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "Driver Control Version 1_0")
public class DriverControl_v1_0 extends LinearOpMode {

    private DcMotor leftTop_DcMotor; // port 0
    private DcMotor rightTop_DcMotor; //
    private DcMotor leftBot_DcMotor;
    private DcMotor rightBot_DcMotor;
    private DcMotor arm_rotator;
    private DcMotor arm_lift;
    private DcMotor suction;
    private DcMotor duck;
    private Servo arm_extender;
    private double servo_steps = 0.01 ;
    private double servo_counter = 0 ;

    private double speed = 0.5;
    private double eps = 0.2;


    public void setup_robot(){

        leftTop_DcMotor = hardwareMap.get(DcMotor.class, "left_top");

        rightTop_DcMotor = hardwareMap.get(DcMotor.class, "right_top");

        leftBot_DcMotor = hardwareMap.get(DcMotor.class, "left_bot");

        rightBot_DcMotor = hardwareMap.get(DcMotor.class, "right_bot");

        arm_rotator = hardwareMap.get(DcMotor.class, "arm_rotator");

        arm_lift = hardwareMap.get(DcMotor.class, "arm_lift");

        suction = hardwareMap.get(DcMotor.class, "suction");

        duck = hardwareMap.get(DcMotor.class, "duck");

        telemetry.addData("Reset Arm Extender... ", "");

        sleep(1000);

        arm_extender = hardwareMap.get(Servo.class, "arm_extender");

        //resetServo();

        telemetry.addLine();

        telemetry.addData("Servo Arm Extender reset complete V1", arm_extender.getPosition());

        telemetry.addData("SERVO Port: ", arm_extender.getPortNumber());

        telemetry.addLine();

        telemetry.update();

    }

    public void resetServo(){

        arm_extender.setPosition(0);



    }

    @Override
    public void runOpMode() {

        setup_robot();
        waitForStart();
        telemetry.addData("left stick Y value = ", gamepad1.left_stick_y);
        telemetry.addData("right stick Y value = ", gamepad1.right_stick_y);
        telemetry.update();


        while(opModeIsActive())
        {


            if(gamepad1.left_stick_y< 0 ){
                telemetry.addData("up", gamepad1.left_stick_y);
                telemetry.update();

                leftTop_DcMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                leftBot_DcMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                rightTop_DcMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                rightBot_DcMotor.setDirection(DcMotorSimple.Direction.FORWARD);

                rightTop_DcMotor.setPower(0.5);
                rightBot_DcMotor.setPower(0.5);
                leftTop_DcMotor.setPower(0.5);
                leftBot_DcMotor.setPower(0.5);


            }

            // if (gamepad1.left_stick_y> 0 ){
            // telemetry.addData("down", gamepad1.left_stick_y);
            // telemetry.update();

            // leftTop_DcMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            // leftBot_DcMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            // rightTop_DcMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            // rightBot_DcMotor.setDirection(DcMotorSimple.Direction.REVERSE);

            // rightTop_DcMotor.setPower(0.5);
            // rightBot_DcMotor.setPower(0.5);
            // leftTop_DcMotor.setPower(0.5);
            // leftBot_DcMotor.setPower(0.5);

            // }
            // if(gamepad1.left_stick_x> 0 ){
            // telemetry.addData("right", gamepad1.left_stick_x);
            // telemetry.update();

            // leftTop_DcMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            // leftBot_DcMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            // rightTop_DcMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            // rightBot_DcMotor.setDirection(DcMotorSimple.Direction.REVERSE);

            // rightTop_DcMotor.setPower(0.5);
            // rightBot_DcMotor.setPower(0.5);
            // leftTop_DcMotor.setPower(0.5);
            // leftBot_DcMotor.setPower(0.5);
            // }
            if(gamepad1.left_stick_x< 0 ){
                telemetry.addData("left", gamepad1.left_stick_x);
                telemetry.update();

                leftTop_DcMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                leftBot_DcMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                rightTop_DcMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                rightBot_DcMotor.setDirection(DcMotorSimple.Direction.FORWARD);

                // rightTop_DcMotor.setPower(0.5);
                // rightBot_DcMotor.setPower(0.5);
                // leftTop_DcMotor.setPower(0.5);
                // leftBot_DcMotor.setPower(0.5);
            }

            if (gamepad1.left_stick_x==0 && gamepad1.left_stick_y==0) {
                rightTop_DcMotor.setPower(0);
                rightBot_DcMotor.setPower(0);
                leftTop_DcMotor.setPower(0);
                leftBot_DcMotor.setPower(0);

            }
            if(gamepad1.y) {
                arm_lift.setPower(0.8);

            }
            else if(gamepad1.a){
                arm_lift.setPower(-0.8);
            }
            else{
                arm_lift.setPower(0);
            }
            if(gamepad1.x){
                arm_rotator.setPower(0.4);
            }
            else if(gamepad1.b){
                arm_rotator.setPower(-0.4);

            }
            else{
                arm_rotator.setPower(0);
            }

            if(gamepad1.right_trigger > 0){
                duck.setPower(0.8);
            }

            else if(gamepad1.left_trigger > 0){
                duck.setPower(-0.8);

            }
            else{
                duck.setPower(0);
            }
            if (gamepad1.dpad_up){
                suction.setPower(0.4);

            }
            else if (gamepad1.dpad_down){
                suction.setPower(-0.8);
            }
            else {
                suction.setPower(0);
            }
            // telemetry.addData("left stick Y value = ", gamepad1.left_stick_y);
            // telemetry.addData("right stick Y value = ", gamepad1.right_stick_y);
            // telemetry.update();
            if (gamepad1.dpad_left) {

                if(servo_counter > 2){

                    servo_counter = 2;

                }else{

                    servo_counter = arm_extender.getPosition() + servo_steps ;

                    arm_extender.setPosition(servo_counter);

                    sleep(10);
                }


            }
            else if(gamepad1.dpad_right){

                if(servo_counter < 0){

                    servo_counter = 0;


                }else{

                    servo_counter = arm_extender.getPosition() - servo_steps ;

                    arm_extender.setPosition(servo_counter);

                    sleep(10);
                }

            }
            telemetry.addData("SERVO POS: ", arm_extender.getPosition());

            telemetry.update();
        }
    }
}










