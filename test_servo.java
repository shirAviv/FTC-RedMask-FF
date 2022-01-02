package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "test_servo (Blocks to Java)")
public class test_servo extends LinearOpMode {

    private Servo servo_1;
    private Servo servo_0;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        servo_1 = hardwareMap.get(Servo.class, "servo_1");
        servo_0 = hardwareMap.get(Servo.class, "servo_0");

        waitForStart();
        servo_0.setPosition(1);
        sleep(1000);
    }
}
