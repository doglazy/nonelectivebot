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

@Autonomous(name = "autocatch")
//@Disabled
public class autocatch extends LinearOpMode {
    DrivingBot robot;
    CapDeterminationPipeline cap;

    int ConePosition;
    int red;
    int green;
    int blue;
    private ElapsedTime runtime = new ElapsedTime();




    @Override
    public void runOpMode() throws InterruptedException {
        robot = new DrivingBot();
        robot.init(hardwareMap);
        cap = new CapDeterminationPipeline();

        //start camera

        //choose cap position during init
        while (!opModeIsActive()) {
            robot.setMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //start point for platform and arm as reference point
            telemetry.update();
            if (isStopRequested())
                break;
        }
        //start point for platform and arm as reference point
        telemetry.addData("Status", "Ready!");
        telemetry.update();


        waitForStart();

        bot_sleep(2500);
        red = robot.color.red();
        green = robot.color.green();
        blue = robot.color.blue();
        telemetry.addData("red", red);
        telemetry.addData("blue", blue);
        telemetry.addData("green", green);
        telemetry.update();
        bot_sleep(1500);

        while (opModeIsActive()) {
            if ((red >= blue)&&(blue <= 85)&&(red >= 90)){
                telemetry.addData("red", red);
                telemetry.update();
                bot_sleep(500);
                robot.strafeLeft(.6,24);
                bot_sleep(6500);
                robot.moveForwardTo(.6,10);
                bot_sleep(3000);
            }
            if ((red <= 85)&&(blue >= red)&&(blue>=90)){
                telemetry.addData("blue", blue);
                telemetry.update();
                bot_sleep(500);
                robot.moveForwardTo(.6,15);
                bot_sleep(6500);
                robot.strafeRight(.6,3.5);
                bot_sleep(3000);
            }
            if ((red <=83)&&(green <= 83)&&(blue <= 83)){
               telemetry.addData("black", red);
               telemetry.update();
               bot_sleep(500);
               robot.strafeRight(.6,28);
               bot_sleep(6500);
               robot.moveForwardTo(.6,10);
               bot_sleep(3000);
            }
            if ((red <=83)&&(green >= 83)&&(blue <= 83)){
                telemetry.addData("blue", blue);
                telemetry.update();
                bot_sleep(500);
                robot.moveForwardTo(.6,15);
                bot_sleep(6500);
                robot.strafeRight(.6,3.5);
                bot_sleep(3000);
            }
            break;
        }
        robot.stopMoving();
    }
    private void capLeft(){

    }

    private void capMiddle() {
       robot.strafeRight(1,15);
       telemetry.addData("yes",1);
       telemetry.addData("trgtposfl", robot.FLdrive.getTargetPosition() );
       telemetry.addData("flmode", robot.FLdrive.getMode() );
       telemetry.addData("flpow", robot.FLdrive.getPower());
       telemetry.update();
        bot_sleep(2000);
        robot.moveForwardTo(1,15);
        bot_sleep(1000);
        robot.strafeLeft(1,15);
        bot_sleep(500);

    }

    private void capRight() {
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
        bot_sleep(800);
    }

}