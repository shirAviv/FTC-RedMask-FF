package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "test_ela_0 (Blocks to Java)")
public class test_ela extends LinearOpMode {

    private DcMotor left_bot;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        waitForStart();
        left_bot = hardwareMap.get(DcMotor.class, "left_bot");
        double speed = 0.1;

        while(opModeIsActive()) {
            if (gamepad1.left_trigger>0) {
                left_bot.setPower(speed);
                left_bot.setDirection(DcMotorSimple.Direction.FORWARD);
            }
            if(gamepad1.right_trigger > 0){
                left_bot.setPower(speed);
                left_bot.setDirection(DcMotorSimple.Direction.REVERSE);
            }
            else{
                left_bot.setPower(0);
            }
            if(gamepad1.x){
                if(speed<=0.9){
                    speed+=0.1;
                    telemetry.addData("speed=",speed);
                    telemetry.update();
                }
            }
            if(gamepad1.b){
                if(speed >= 0.2){
                    speed-=0.1;
                }
            }
        }
    }
}
