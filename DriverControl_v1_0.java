package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "Driver Control Version 1_0")
public class DriverControl_v1_0 extends LinearOpMode {

    private DcMotor leftTop_DcMotor; // port 0
    private DcMotor rightTop_DcMotor; //
    private DcMotor leftBot_DcMotor;
    private DcMotor rightBot_DcMotor;
    private double speed = 0.5;
    private double eps = 0.2;
    public void setup_robot(){
        leftTop_DcMotor = hardwareMap.get(DcMotor.class, "left_top");
        rightTop_DcMotor = hardwareMap.get(DcMotor.class, "right_top");
        leftBot_DcMotor = hardwareMap.get(DcMotor.class, "left_bot");
        rightBot_DcMotor = hardwareMap.get(DcMotor.class, "right_bot");
    }

    @Override
    public void runOpMode() {

        setup_robot();
        waitForStart();

        while(opModeIsActive())
        {




            if(gamepad1.left_stick_y< -eps && gamepad1.right_stick_y < -eps){

                leftTop_DcMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                leftBot_DcMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                rightTop_DcMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                rightBot_DcMotor.setDirection(DcMotorSimple.Direction.REVERSE);

                rightTop_DcMotor.setPower(gamepad1.right_stick_y);
                rightBot_DcMotor.setPower(0);
                leftTop_DcMotor.setPower(0);
                leftBot_DcMotor.setPower(gamepad1.left_stick_y);

            }else{


                leftTop_DcMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                leftBot_DcMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                rightTop_DcMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                rightBot_DcMotor.setDirection(DcMotorSimple.Direction.REVERSE);

                rightTop_DcMotor.setPower(gamepad1.right_stick_y);
                rightBot_DcMotor.setPower(gamepad1.right_stick_y);
                leftTop_DcMotor.setPower(gamepad1.left_stick_y);
                leftBot_DcMotor.setPower(gamepad1.left_stick_y);
            }




            telemetry.addData("left stick Y value = ", gamepad1.left_stick_y);
            telemetry.addData("right stick Y value = ", gamepad1.right_stick_y);
            telemetry.update();


        }
    }
}



