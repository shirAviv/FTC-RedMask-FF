package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Final_TeleOp_V1.3", group="RedMask")
@Disabled
public class Drive_example_Noam extends LinearOpMode {
		
		private DcMotor leftFront; // port 0
		private DcMotor rightFront; //
		private DcMotor leftRear;
		private DcMotor rightRear;
		private ElapsedTime runtime = new ElapsedTime();


		public void setup_robot(){
		 
				leftFront = hardwareMap.get(DcMotor.class, "left_top"); 
		
				rightFront = hardwareMap.get(DcMotor.class, "right_top"); 
		
				leftRear = hardwareMap.get(DcMotor.class, "left_bot"); 
		
				rightRear = hardwareMap.get(DcMotor.class, "right_bot"); 
		 }
	 
		

		

		@Override
		public void runOpMode() {

				setup_robot();

				waitForStart();
				runtime.reset();

				// run until the end of the match (driver presses STOP)
				while (opModeIsActive()) {
						
						
						/*used to return the square root of the sum of
						squares of the specified arguments
						without intermediate overflow or underflow.*/
						double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
						
						
						/*converts the specified rectangular coordinates (x, y)
						 into polar coordinates (r, θ) and returns the angle theta (θ)*/
						double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
						
						
						double rightX = gamepad1.right_stick_x;
						final double v1 = r * Math.cos(robotAngle) + rightX;
						final double v2 = r * Math.sin(robotAngle) - rightX;
						final double v3 = r * Math.sin(robotAngle) + rightX;
						final double v4 = r * Math.cos(robotAngle) - rightX;
						
					 


						leftFront.setPower(v1*(-1));
						rightFront.setPower(v2);
						leftRear.setPower(v3*(-1));
						rightRear.setPower(v4);
						
						
						// Show the elapsed game time and wheel power.
						telemetry.addData("Status", "Run Time: " + runtime.toString());
						telemetry.addData("R	= ", r);
						telemetry.addData("Motor left front	= ", v1);
						telemetry.addData("Motor right front	= ", v2);
						telemetry.addData("Motor left rear	= ", v3);
						telemetry.addData("Motor right rear	= ", v4);
						telemetry.update();
				}
		}
}
