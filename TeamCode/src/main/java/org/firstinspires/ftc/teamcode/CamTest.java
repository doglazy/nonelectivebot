package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;


@TeleOp(name="Camera Test", group="Linear Opmode")
//@Disabled
public class CamTest extends LinearOpMode {
    // Declare OpMode members.
    DrivingBot robot;
    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() {
        robot = new DrivingBot();
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //start camera
        robot.webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
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

        telemetry.addData("Status", "camera started!");

        while (!opModeIsActive()) {
            telemetry.addData("avg", robot.pipeline.findAvg());
            telemetry.addData("coneposC", robot.pipeline.isCapCenter());
            telemetry.addData("coneposR", robot.pipeline.isCapRight());
            telemetry.addData("coneposL", robot.pipeline.isCapLeft());
            telemetry.update();

            if (isStopRequested()) {
                break;
            }
        }

        // Wait for the game to start (driver presses PLAY)
        waitForStart();




        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.addData("avg", robot.pipeline.findAvg());
            telemetry.addData("coneposC", robot.pipeline.isCapCenter());
            telemetry.addData("coneposR", robot.pipeline.isCapRight());
            telemetry.addData("coneposL", robot.pipeline.isCapLeft());
            telemetry.update();

        }

        robot.webcam.closeCameraDevice();
    }
}