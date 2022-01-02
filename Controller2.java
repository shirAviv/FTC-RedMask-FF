package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import  java.lang.Math;

@TeleOp(name = "controller2 (Java to Yava)")
public class Controller2 extends LinearOpMode {
    private DcMotor arm_rotator; //arm rotator 1:6 ratio
    private DcMotor arm_lifter;  //arm lifter 1:4 ratio
    private DcMotor duck; // duck
    private DcMotor pumper; //pumper

    @Override
    public void runOpMode() {
        arm_rotator = hardwareMap.get(DcMotor.class, "arm_rotator");
        duck = hardwareMap.get(DcMotor.class, "duck");
        arm_lifter = hardwareMap.get(DcMotor.class, "arm_lifter");
        pumper = hardwareMap.get(DcMotor.class, "pumper");


        arm_rotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm_rotator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm_lifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm_lifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double arm_rotator_ppr = 180;
        double arm_lifter_ppr = 180;


        waitForStart();
        while (opModeIsActive()) {
            //pumper
            while (gamepad1.a){
                duck.setDirection(DcMotorSimple.Direction.FORWARD); //pumper.setDirection(DcMotorSimple.Direction.FORWARD)
                duck.setPower(0.9); //pumper.setPower(0.9)
            }
            while (gamepad1.b){
                duck.setDirection(DcMotorSimple.Direction.REVERSE); //pumper.setDirection(DcMotorSimple.Direction.REVERSE)
                duck.setPower(0.9); //pumper.setPower(0.9)
            }
            //duck
            while (gamepad1.x){
                duck.setPower(0.9);
            }
            //arm rotator
            while ((duck.getCurrentPosition()) <= ((arm_rotator_ppr) * 1.3) && gamepad1.right_trigger > 0){
                duck.setDirection(DcMotorSimple.Direction.FORWARD); //arm_rotator.setDirection(DcMotorSimple.Direction.FORWARD)
                duck.setPower(gamepad1.right_trigger); //arm_rotator.setPower(gamsdsads epad1.right_trigger)
            }
            while (duck.getCurrentPosition() >= 0 && gamepad1.left_trigger > 0){
                duck.setDirection(DcMotorSimple.Direction.FORWARD); //arm_rotator.setDirection(DcMotorSimple.Direction.REVERSE)
                duck.setPower(-gamepad1.left_trigger); //arm_rotator.setPower(gamepad1.left_trigger)
            }
            //arm
            telemetry.addData("Current Position", duck.getCurrentPosition() );
            telemetry.addData("arm rotator ppr", arm_rotator_ppr * 1.3 );
            telemetry.update();
            arm_rotator.setPower(0);
            duck.setPower(0);
            arm_lifter.setPower(0);
            pumper.setPower(0);

        }
    }
}
