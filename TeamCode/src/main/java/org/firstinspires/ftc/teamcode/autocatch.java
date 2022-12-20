package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

@Autonomous(name = "AutoBW")
//@Disabled
public class autocatch extends LinearOpMode {
    DrivingBot robot;

    int capPosition;
    private ElapsedTime runtime = new ElapsedTime();




    @Override
    public void runOpMode() throws InterruptedException {
        robot = new DrivingBot();
        robot.init(hardwareMap);

        //start camera
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

        //choose cap position during init
        while (!opModeIsActive()) {
            robot.setMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //start point for platform and arm as reference point
            if (robot.pipeline.isCapLeft()) {
                capPosition = 0;
                telemetry.addData("cap", 0);
            } else if (robot.pipeline.isCapCenter()) {
                capPosition = 1;
                telemetry.addData("cap", 1);
            } else {
                capPosition = 2;
                telemetry.addData("cap", 2);
            }
            telemetry.addData("v1",robot.pipeline.v1);
            telemetry.addData("v2",robot.pipeline.v2);
            telemetry.update();
            if (isStopRequested())
                break;
        }
        //start point for platform and arm as reference point
        telemetry.addData("Status", "Ready!");
        telemetry.update();


        waitForStart();


        while (opModeIsActive()) {
            switch (capPosition) {
                case 0:
                    telemetry.addData("cap", "left");
                    telemetry.update();
                    capLeft();
                    break;
                case 1:
                    telemetry.addData("cap", "middle");
                    telemetry.update();
                    capMiddle();
                    break;
                default:
                    telemetry.addData("cap", "right");
                    telemetry.update();
                    capRight();
                    break;
            }
            break;
        }
        robot.stopMoving();
    }
    private void scoremed(){
        while (robot.pipeline.v1 != 1 && robot.pipeline.v2 != 1 && isStopRequested() != true){
            robot.FRdrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.FLdrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.BRdrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.BLdrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.FRdrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.FLdrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.BRdrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.BLdrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.FLdrive.setPower(.3);
            robot.BLdrive.setPower(.3);
            robot.BRdrive.setPower(.3);
            robot.FRdrive.setPower(.3);

        }
        robot.FRdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.FLdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.BRdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.BLdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Extend.setPower(.5);
        robot.Extend.setTargetPosition(400);

    }

    private void capLeft() {
        robot.moveForwardTo(.2,2.5);
        bot_sleep(200);
        robot.strafeLeft(.3,27);
        bot_sleep(5000);
        robot.spinLeft(.2,.5);
        bot_sleep(1500);
        robot.moveForwardTo(.4,30);
        bot_sleep(5000);

    }

    private void capMiddle() {
        robot.moveForwardTo(.1,25);
        bot_sleep(200);
        robot.spinRight(.2,.75);
        bot_sleep(1000);
        robot.moveForwardTo(.5,2);
        bot_sleep(5000);
    }

    private void capRight() {
        robot.moveForwardTo(.2,2.5);
        bot_sleep(200);
        robot.strafeRight(.3,25);
        bot_sleep(5000);
        robot.spinLeft(.2,.75);
        robot.moveForwardTo(.5,22.5);
        bot_sleep(5000);
    }

    private void bot_sleep(int ms) {
        runtime.reset();
        while ((runtime.milliseconds() <= ms) && !isStopRequested()) {
        }
    }

    private void goToCarousel() {
        robot.moveBackwardTo(0.3, 3);
        robot.setMotorsMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        sleep(800);
        robot.setPower(0.3);
        sleep(200);
        while((((DcMotorEx) robot.BRdrive).getCurrent(CurrentUnit.MILLIAMPS)*5 <= 10700) && !isStopRequested()); {
            robot.setPower(0.1);
            sleep(50);
            robot.setPower(0);
            telemetry.addData("status", "stop");
            telemetry.addData("current", ((DcMotorEx) robot.BRdrive).getCurrent(CurrentUnit.MILLIAMPS)*5);
            telemetry.update();
        }
        telemetry.addData("current", ((DcMotorEx) robot.BRdrive).getCurrent(CurrentUnit.MILLIAMPS)*5);
        telemetry.update();
        robot.setPower(0);
        bot_sleep(4000);
    }

}