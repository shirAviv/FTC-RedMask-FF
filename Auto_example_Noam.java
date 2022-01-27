package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Test Autonomous v1.0", group="RedMask")
public class Auto_example_Noam extends LinearOpMode {


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
		private ElapsedTime runtime = new ElapsedTime();
		
		
		
		//============================== ROBOT SETTING ===========================
		
		final double diameter = 9.6; // wheel diameter in cm
		final double PPR = 537.7; 
		final double lifter_PPR = 3895.9;
		
		
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
				
				arm_rotator = hardwareMap.get(DcMotor.class, "arm_rotator"); 
		
				arm_lift = hardwareMap.get(DcMotor.class, "arm_lift"); 
		
				suction = hardwareMap.get(DcMotor.class, "suction"); 
		
				duck = hardwareMap.get(DcMotor.class, "duck");
				
				arm_extender = hardwareMap.get(CRServo.class, "arm_extender");
				
				loader = hardwareMap.get(CRServo.class, "loader");
		
		 }
		 
		public void drive(String direction, int distance , double speed){
				
				/*
						Direction: 
						
						1.foward --> move the robot foward
						2.reverse -- > move the robot backward
						3.left --> move the robot left however it's not make a turn 
						4.right --> move the robot right however it's not make a turn 
						
						distance measured in cm -- mean centimeters and can not be a negative number
						
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
				else if (direction == "left" || direction == "L"){
						rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
						rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
						leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
						leftRear.setDirection(DcMotorSimple.Direction.FORWARD);
				}
				else if (direction == "right" || direction == "R"){
						rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
						rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
						leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
						leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
				}
				
				
				rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				
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
				
				while (rightFront.getCurrentPosition() < position){
						
				}
		}
		
		public void arm_rotation(String direction, double precent ,double speed){
				
				/*
						Direction:
						
						1.right --> rotate the arm right
						2.left --> rotate the arm left 
				*/
				
				if (direction == "right"){
						arm_rotator.setDirection(DcMotorSimple.Direction.FORWARD);
				}
				else if (direction == "left"){
						arm_rotator.setDirection(DcMotorSimple.Direction.REVERSE);
						
				}
				arm_rotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				arm_rotator.setTargetPosition((int)(PPR * precent) * 6); // 
				arm_rotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
				arm_rotator.setPower(speed);
		}
		
		public void arm_lifter_auto(String direction, double precent , double speed){
			arm_lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				if (direction == "up"){
						arm_lift.setDirection(DcMotorSimple.Direction.FORWARD);
				}
				else if (direction == "down"){
						arm_lift.setDirection(DcMotorSimple.Direction.REVERSE);
				}
				arm_lift.setTargetPosition((int)(PPR * precent) * 4); // 
				arm_lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
				arm_lift.setPower(speed);
		}
		public void sucking(){
				suction.setDirection(DcMotorSimple.Direction.FORWARD);
				suction.setPower(0.9);
		}
		public void arm_lift_level(int position_of_duck){
			if (position_of_duck == 1){
				arm_lifter_auto("up" , 0.7 ,0.2);	
			}
			else if (position_of_duck == 2){
				arm_lifter_auto("up" , 0.9 ,0.2);	
			}
			else if (position_of_duck == 3){
				arm_lifter_auto("up" , 1.1 ,0.2);	
			}
		}
		public void runOpMode() {

				//init the HW's robot
				setup_robot();
				
				//while play button had pressed 
				waitForStart();
				
				arm_lift_level (1);
				//direction , distance in cm , power
				
				/*drive("F" , 90 , 0.3);
				drive("B" , 90 , 0.3);
				drive("R" , 90 , 0.3);
				drive("L" , 90 , 0.3);
				*/
				//arm_rotation("left" , 0.25 , 0.2);
				//arm_lifter_auto("down", 0.1 , 0.2);
				
				
				//=========================== MOTOR SETTING =======================
				/*
				int position; 
				
				//motor directions
				rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
				rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
				leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
				leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
				
				//stop the motor and reset the encodders --> RESET ENCODERS
				rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				
				position =	convert_cm_to_pulses(30); 
			 
				
				rightFront.setTargetPosition(position);
				rightRear.setTargetPosition(position);
				leftFront.setTargetPosition(position);
				leftRear.setTargetPosition(position);
				
				
				rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
				rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
				leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
				leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
				
	 
				
				
				rightFront.setPower(0.3);
				rightRear.setPower(0.3);
				leftFront.setPower(0.3); 
				leftRear.setPower(0.3);
				*/
				/*
				while (rightFront.getCurrentPosition() < position){
						
				}
				
				
				position = convert_cm_to_pulses(30);
				
				rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
				rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
				leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
				leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
				
				//stop the motor and reset the encodders --> RESET ENCODERS
				rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				
				
				rightFront.setTargetPosition(position);
				rightRear.setTargetPosition(position);
				leftFront.setTargetPosition(position);
				leftRear.setTargetPosition(position);
				
				
				rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
				rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
				leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
				leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
				
	 
				
				
				rightFront.setPower(0.3);
				rightRear.setPower(0.3);
				leftFront.setPower(0.3); 
				leftRear.setPower(0.3);
				
				*/
				
				//======================== MOTOR SETTING End=======================
			 
				
			 
			 
			 
			 
			 
			 
			 
			 
				// reset the timer
				runtime.reset();
				
				// run until the end of the match (driver presses STOP)
				while (opModeIsActive()) {
						
				// position = rightFront.getCurrentPosition();
				
				
				//telemetry.addData("Encoder Position", position);
				
				
				//telemetry.update();
				
				}
		}
						

		 
		 
}
	 

