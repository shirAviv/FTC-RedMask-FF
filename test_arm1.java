package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "test_arm1 (Blocks to Java)")
public class test_arm1 extends LinearOpMode {

    private DcMotor left_bot;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        left_bot = hardwareMap.get(DcMotor.class, "left_bot");
        double speed = 0.1;
        waitForStart();
        while(opModeIsActive()) {
            if (gamepad1.left_trigger>0) {
                left_bot.setPower(0.3);
                left_bot.setDirection(DcMotorSimple.Direction.FORWARD);
            }

            else{
                left_bot.setPower(0);
            }
        }
    }

}