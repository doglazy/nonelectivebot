package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;

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
        robot.webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened() {
                robot.webcam.startStreaming(640, 360, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("camera", "failed");
                telemetry.update();
            }
        });
//        correctextend();
        int intextend = robot.Extend.getCurrentPosition();
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                if (gamepad1.right_bumper == true){
                    telemetry.addData("RB", "yes");
                }
                robot.FRdrive.setDirection(DcMotor.Direction.REVERSE);
                // robot.FLdrive.setDirection(DcMotor.Direction.REVERSE);
                robot.FRdrive.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x)*.5);
                robot.FLdrive.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x)*.5);
                robot.BRdrive.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x)*.5);
                robot.BLdrive.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x)*.5);
                telemetry.addData("argb", robot.color.argb());
                telemetry.addData("current", ((DcMotorEx) robot.Extend).getCurrent(CurrentUnit.MILLIAMPS)*5);
                telemetry.addData("actaulspeedBL", robot.BLdrive.getPower());
                telemetry.addData("actaulspeedBR", robot.BRdrive.getPower());
                telemetry.addData("actaulspeedFL", robot.FLdrive.getPower());
                telemetry.addData("actaulspeedFR", robot.FRdrive.getPower());
                telemetry.addData("controler", gamepad1.left_stick_y);

                if (gamepad2.right_trigger >= .3){
                    robot.Extend.setPower(-gamepad2.right_trigger);
                    //  robot.Extend.setTargetPosition(-50 + intextend);
                    //    robot.Extend.setPower(.3);
                    //  robot.Extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    //  correctextend();
                    //  intextend = robot.Extend.getCurrentPosition();
                }
                if (gamepad2.left_trigger >= .3){
                    robot.Extend.setPower(gamepad2.left_trigger);
                    // robot.Extend.setTargetPosition(-200 + intextend);
                    //  robot.Extend.setPower(.3);
                    // robot.Extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
                if ((gamepad2.left_trigger < .3) && (gamepad2.right_trigger < .3)){
                    robot.Extend.setPower(0);
                }

                if (gamepad2.a == true){
                    robot.Clamp.setPosition(.1);
                    telemetry.addData("a", "yes");
                }
                if (gamepad2.b == true){
                    robot.Clamp.setPosition(.4);
                    telemetry.addData("b", "yes");
                }
                telemetry.update();
                while(opModeIsActive() && gamepad1.right_trigger >= .3){
                    telemetry.addData("v1",robot.pipeline.v1);
                    telemetry.addData("v2",robot.pipeline.v2);
                    telemetry.update();
                }
                while (opModeIsActive() && gamepad1.right_bumper == true){
                    robot.FRdrive.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x);
                    robot.FLdrive.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x);
                    robot.BRdrive.setPower(gamepad1.left_stick_y - gamepad1.left_stick_x);
                    robot.BLdrive.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x);
                    if (gamepad1.a == true){
                        robot.Clamp.setPosition(.20);
                        telemetry.addData("a", "yes");
                    }
                    if (gamepad1.b == true){
                        robot.Clamp.setPosition(.4);
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
