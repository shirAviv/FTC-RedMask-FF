package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "test_encoder (Blocks to Java)")
public class test_encoder extends LinearOpMode {

    private DcMotor right_top;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        int y;

        right_top = hardwareMap.get(DcMotor.class, "right_top");
        right_top.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        right_top.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        y = 538;
        right_top.setTargetPosition(right_top.getCurrentPosition() + y);
        right_top.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right_top.setPower(0.1);
        while (right_top.isBusy()){

        }
        right_top.setPower(0);

    }
}
