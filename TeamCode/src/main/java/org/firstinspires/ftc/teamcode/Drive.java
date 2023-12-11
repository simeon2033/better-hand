package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "drive22 (Blocks to Java)")
public class Drive extends LinearOpMode {

    private Servo linksAsServo;
    private Servo rechtsAsServo;
    private DcMotor linksachter;
    private DcMotor linksvoor;
    private DcMotor rechtsvoor;
    private DcMotor rechtsachter;
    private DcMotor schouderlinksAsDcMotor;
    private DcMotor schouderrechtsAsDcMotor;
    private DcMotor armrechtsAsDcMotor;
    private DcMotor armlinksAsDcMotor;
    private Servo handAsServo;

    /**
     * This function is executed when this OpMode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        double ratio;
        double speed1;
        double speed2;

        linksachter = hardwareMap.get(DcMotor.class, "linksachter");
        linksvoor = hardwareMap.get(DcMotor.class, "linksvoor");
        rechtsvoor = hardwareMap.get(DcMotor.class, "rechtsvoor");
        rechtsachter = hardwareMap.get(DcMotor.class, "rechtsachter");
        schouderlinksAsDcMotor = hardwareMap.get(DcMotor.class, "schouder links");
        schouderrechtsAsDcMotor = hardwareMap.get(DcMotor.class, "schouder rechts");
        armrechtsAsDcMotor = hardwareMap.get(DcMotor.class, "arm rechts");
        armlinksAsDcMotor = hardwareMap.get(DcMotor.class, "arm links");
        handAsServo = hardwareMap.get(Servo.class, "hand");
        linksAsServo = hardwareMap.get(Servo.class, "links");
        rechtsAsServo = hardwareMap.get(Servo.class, "rechts");

        // Put initialization blocks here.
        ratio = 0.5;
        linksAsServo.scaleRange(0.3, 0.45);
        rechtsAsServo.scaleRange(0, 0.25);
        waitForStart();
        if (opModeIsActive()) {


            linksachter.setPower(0);
            linksvoor.setPower(0);
            rechtsvoor.setPower(0);
            rechtsachter.setPower(0);

            while (opModeIsActive()) {
                // Put loop blocks here.
                if (gamepad1.a) {
                    ratio += -0.01;
                    if (ratio < 0.05) {
                        ratio = 0.05;
                    }
                }
                if (gamepad1.b) {
                    ratio += 0.01;
                    if (ratio > 1) {
                        ratio = 1;
                    }
                }
                if (!(gamepad1.left_trigger > 0.1 || gamepad1.right_trigger > 0.1)) {
                    speed1 = ratio * (gamepad1.left_stick_x * Math.sin(-45) - gamepad1.left_stick_y * Math.cos(-45));
                    speed2 = ratio * (gamepad1.left_stick_x * Math.cos(-45) + gamepad1.left_stick_y * Math.sin(-45));
                    linksachter.setPower(speed1);
                    rechtsvoor.setPower(speed1 * -1);
                    linksvoor.setPower(speed2);
                    rechtsachter.setPower(speed2 * -1);
                } else {
                    if (gamepad1.left_trigger > gamepad1.right_trigger) {
                        speed1 = -1 * ratio * gamepad1.left_trigger;
                    } else {
                        speed1 = ratio * gamepad1.right_trigger;
                    }
                    linksachter.setPower(speed1);
                    linksvoor.setPower(speed1);
                    rechtsvoor.setPower(speed1);
                    rechtsachter.setPower(speed1);
                }
                schouderlinksAsDcMotor.setPower(0.4 * gamepad2.left_stick_y);
                schouderrechtsAsDcMotor.setPower(-0.4 * gamepad2.left_stick_y);

                armlinksAsDcMotor.setPower(0.5 * gamepad2.right_stick_y);
                armrechtsAsDcMotor.setPower(-0.5 * gamepad2.right_stick_y);
                linksAsServo.setPosition(gamepad2.right_trigger * 1);
                rechtsAsServo.setPosition(Math.abs(gamepad2.right_trigger - 1));
                handAsServo.setPosition(0.5 * gamepad2.left_trigger);
                telemetry.update();
            }
        }
    }
}