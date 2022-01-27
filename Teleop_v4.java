package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;




@TeleOp(name="Final_TeleOp_V1.4", group="Test 1")

public class Teleop_v4 extends LinearOpMode {
		
		private DcMotor leftFront; // port 0
		private DcMotor rightFront; //
		private DcMotor leftRear;
		private DcMotor rightRear;
		private DcMotor arm_rotator;
		private DcMotor arm_lift;
		private DcMotor suction;
		private DcMotor duck;
		private CRServo arm_extender;
		private CRServo loader;
		
		double speed1 = 0.6;
		boolean counter = false;
		
		private double servo_steps = 0.01 ; 
		private double servo_counter = 0 ;

		private double speed = 0.5;
		private double eps = 0.2;
		//ppr
		private double arm_rotator_ppr = 537.7;
		private double arm_lift_ppr = 1992;
 
		private ElapsedTime runtime = new ElapsedTime();


		public void setup_robot(){
		 
				leftFront = hardwareMap.get(DcMotor.class, "left_top"); 
		
				rightFront = hardwareMap.get(DcMotor.class, "right_top"); 
		
				leftRear = hardwareMap.get(DcMotor.class, "left_bot"); 
		
				rightRear = hardwareMap.get(DcMotor.class, "right_bot"); 

				arm_rotator = hardwareMap.get(DcMotor.class, "arm_rotator"); 
		
				arm_lift = hardwareMap.get(DcMotor.class, "arm_lift"); 
		
				suction = hardwareMap.get(DcMotor.class, "suction"); 
		
				duck = hardwareMap.get(DcMotor.class, "duck");
		
				telemetry.addData("Reset Arm Extender... ", "");
		
				sleep(1000);
		 
				arm_extender = hardwareMap.get(CRServo.class, "arm_extender");
				loader = hardwareMap.get(CRServo.class, "loader");

 
		 }
	 
		

		

		@Override
		public void runOpMode() {

				setup_robot();

				waitForStart();
				runtime.reset();

				// run until the end of the match (driver presses STOP)
				while (opModeIsActive()) {
						
						double r = Math.hypot(gamepad2.left_stick_x, gamepad2.left_stick_y);
						double robotAngle = Math.atan2(gamepad2.left_stick_y, gamepad2.left_stick_x) - Math.PI / 4;
						double rightX = gamepad2.right_stick_x;
						final double v1 = r * Math.cos(robotAngle) + rightX;
						final double v2 = r * Math.sin(robotAngle) - rightX;
						final double v3 = r * Math.sin(robotAngle) + rightX;
						final double v4 = r * Math.cos(robotAngle) - rightX;


						leftFront.setPower(v1);
						rightFront.setPower(v2*(-1));
						leftRear.setPower(v3);
						rightRear.setPower(v4*(-1));
						
						
						// Show the elapsed game time and wheel power.
						// telemetry.addData("Status", "Run Time: " + runtime.toString());
						// telemetry.addData("R	= ", r);
						// telemetry.addData("Motor left front	= ", v1);
						// telemetry.addData("Motor right front	= ", v2);
						// telemetry.addData("Motor left rear	= ", v3);
						// telemetry.addData("Motor right rear	= ", v4);
						// telemetry.update();
						
						//gamepad2
						//suction
						if (gamepad2.left_trigger > 0.2){
								suction.setDirection(DcMotorSimple.Direction.FORWARD); 
								suction.setPower(0.7); 
						}
						else if (gamepad2.right_trigger > 0.2){
								suction.setDirection(DcMotorSimple.Direction.REVERSE); 
								suction.setPower(0.9);
						}
						else{
								suction.setPower(0);
						}
		//game pad 1
				//ducks
						if (gamepad1.dpad_right){
								duck.setDirection(DcMotorSimple.Direction.FORWARD); 
								duck.setPower(0.9); 
						}
						else if (gamepad1.dpad_left){
								duck.setDirection(DcMotorSimple.Direction.REVERSE);
								duck.setPower(0.9); 
						}
						else{
								duck.setPower(0);
						}
								
						 //arm lift	
						if (gamepad1.left_stick_y < 0 || gamepad1.right_stick_y < 0){
								arm_lift.setDirection(DcMotorSimple.Direction.FORWARD); 
								arm_lift.setPower(0.4); 
						}
						else if ((gamepad1.left_stick_y > 0) || (gamepad1.right_stick_y > 0)){
								arm_lift.setDirection(DcMotorSimple.Direction.REVERSE); 
								arm_lift.setPower(0.4);
						}
						else{
								arm_lift.setPower(0);
						}
						
						//arm rotate
						if (gamepad1.right_trigger > 0.2){
								arm_rotator.setDirection(DcMotorSimple.Direction.FORWARD); 
								arm_rotator.setPower(0.3); 
						}
						else if (gamepad1.left_trigger > 0.2){
								arm_rotator.setDirection(DcMotorSimple.Direction.REVERSE); 
								arm_rotator.setPower(0.3);
						}
						else{
								arm_rotator.setPower(0);
						}
						
						// arm extender
						if (gamepad1.right_bumper) {
								arm_extender.setDirection(DcMotorSimple.Direction.FORWARD);
								
								arm_extender.setPower(1);
								telemetry.addData("arm extender	= ", arm_extender.getPower());
								telemetry.update();
						}
						else if(gamepad1.left_bumper){
								arm_extender.setDirection(DcMotorSimple.Direction.REVERSE);
								
								arm_extender.setPower(1);
								telemetry.addData("-arm extender	= ", arm_extender.getPower());
								telemetry.update();
		
						}
						else{
								arm_extender.setPower(0);
								
						}
						//loader
						if (gamepad1.a){
								loader.setDirection(DcMotorSimple.Direction.FORWARD);

								loader.setPower(0.8); 
								telemetry.addData("loader up	= ", loader.getPower());
								telemetry.update();
						
						}
						else if (gamepad1.b){
								loader.setDirection(DcMotorSimple.Direction.REVERSE);
								loader.setPower(0.8);
								telemetry.addData("loader down	= ", loader.getPower());
								telemetry.update();
						
						} else {
								loader.setPower(0);
						}
						
				}
		}
}
