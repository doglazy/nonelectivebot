package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import static java.lang.Math.abs;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOPMM", group = "LinearOpMode")
public class firsttele extends LinearOpMode {
    DrivingBot robot;
    public void correctextend(){
        while((((DcMotorEx) robot.Extend).getCurrent(CurrentUnit.MILLIAMPS)*5 <= 10700) && !isStopRequested()); {
            robot.Extend.setPower(0.1);
            telemetry.addData("status", "stop");
            telemetry.addData("current", ((DcMotorEx) robot.BRdrive).getCurrent(CurrentUnit.MILLIAMPS)*5);
            telemetry.update();
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new DrivingBot();
        robot.init(hardwareMap);
//        correctextend();
        int intextend = robot.Extend.getCurrentPosition();
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                robot.FRdrive.setDirection(DcMotor.Direction.FORWARD);
                // robot.FLdrive.setDirection(DcMotor.Direction.REVERSE);
                robot.FRdrive.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x);
                robot.FLdrive.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x));
                robot.BRdrive.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x);
                robot.BLdrive.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x));
                telemetry.addData("red", robot.color.red());
                telemetry.addData("blue", robot.color.blue());
                telemetry.addData("green", robot.color.green());
                telemetry.addData("current", ((DcMotorEx) robot.Extend).getCurrent(CurrentUnit.MILLIAMPS)*5);
                telemetry.addData("actaulspeedBL", robot.BLdrive.getPower());
                telemetry.addData("actaulspeedBR", robot.BRdrive.getPower());
                telemetry.addData("actaulspeedFL", robot.FLdrive.getPower());
                telemetry.addData("actaulspeedFR", robot.FRdrive.getPower());

                if (gamepad1.right_trigger >= .3){
                    robot.Extend.setPower(-gamepad1.right_trigger);
                  //  robot.Extend.setTargetPosition(-50 + intextend);
                  //    robot.Extend.setPower(.3);
                  //  robot.Extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                  //  correctextend();
                  //  intextend = robot.Extend.getCurrentPosition();
                }
                if (gamepad1.left_trigger >= .3){
                    robot.Extend.setPower(gamepad1.left_trigger);
                   // robot.Extend.setTargetPosition(-200 + intextend);
                  //  robot.Extend.setPower(.3);
                   // robot.Extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
                if ((gamepad1.left_trigger < .3) && (gamepad1.right_trigger < .3)){
                    robot.Extend.setPower(0);
                }

                if (gamepad1.a == true){
                    robot.Clamp.setPosition(.00);
                    telemetry.addData("a", "yes");
                }
                if (gamepad1.b == true){
                    robot.Clamp.setPosition(.3);
                    telemetry.addData("b", "yes");
                }
                while (opModeIsActive() && gamepad1.right_bumper == true){
                    robot.FRdrive.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x);
                    robot.FLdrive.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x);
                    robot.BRdrive.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x);
                    robot.BLdrive.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x);
                    if (gamepad1.a == true){
                        robot.Clamp.setPosition(-.20);
                        telemetry.addData("a", "yes");
                    }
                    if (gamepad1.b == true){
                        robot.Clamp.setPosition(.75);
                        telemetry.addData("b", "yes");
                    } /*
                    if (gamepad2.a == true){
                        robot.Rotate.setTargetPosition(50);
                    }
                    if (gamepad2.b == true){
                        robot.Rotate.setTargetPosition(80);
                    }
                    if (gamepad2.right_trigger >= .3){
                        robot.Extend.setPower(gamepad2.right_trigger);
                    }
                    if (gamepad2.left_trigger >= .3){
                        robot.Extend.setPower(gamepad2.left_trigger);
                    } */
                }
                //telemetry.addData("motordL", robot.FLdrive.getCurrentPosition());
                telemetry.addData("leststickX", gamepad1.left_stick_x);
                telemetry.addData("rightsticky", gamepad1.left_stick_y);
                telemetry.update();
            }
        }
    }
}
