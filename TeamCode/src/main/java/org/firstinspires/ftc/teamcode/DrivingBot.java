package org.firstinspires.ftc.teamcode;

import android.drm.DrmStore;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class DrivingBot {
    /* Public OpMode members. */
    public DcMotor FRdrive;
    public DcMotor FLdrive;
    public DcMotor BRdrive;
    public DcMotor BLdrive;
    public WebcamName camera;
    public DcMotor Rotate;
    public DcMotor Extend;
    public Servo Clamp;
//    public DcMotor arm;
//    public DcMotor intake;
    public RevColorSensorV3 color;
//    public Rev2mDistanceSensor distance;
//    public RevColorSensorV3 colorR;
//    public RevColorSensorV3 colorL;
//
    OpenCvCamera webcam;

    static final double COUNTS_PER_MOTOR_REV = 560;
    static final double DRIVE_GEAR_REDUCTION = 1;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 3.543;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    public int arm_start;
    public int platform_start;
//
    CapDeterminationPipeline pipeline;


    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    int nfl = 0;
    int nfr = 0;
    int nbl = 0;
    int nbr = 0;
    int sumFL = 0;
    int sumFR = 0;
    int sumBL = 0;
    int sumBR = 0;
    int[] FL = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    int[] FR = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    int[] BL = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    int[] BR = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};


    /* Constructor */
    public DrivingBot() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        FRdrive = hwMap.dcMotor.get("FRdrive");
        FLdrive = hwMap.dcMotor.get("FLdrive");
        BRdrive = hwMap.dcMotor.get("BRdrive");
        BLdrive = hwMap.dcMotor.get("BLdrive");
    //    Rotate = hwMap.dcMotor.get("Rotate");
        Extend = hwMap.dcMotor.get("Expand");
        Clamp = hwMap.servo.get("Clamp");
//
//        platform = hwMap.dcMotor.get("platform");
//        arm = hwMap.dcMotor.get("arm");
//        intake = hwMap.dcMotor.get("intake");
//        carousel = hwMap.dcMotor.get("carousel");
//
//
//        // Set motors' direction
        FRdrive.setDirection(DcMotor.Direction.FORWARD);
        FLdrive.setDirection(DcMotor.Direction.REVERSE);
        BRdrive.setDirection(DcMotor.Direction.FORWARD);
        BLdrive.setDirection(DcMotor.Direction.REVERSE);
//        platform.setDirection(DcMotor.Direction.FORWARD);
//        arm.setDirection(DcMotor.Direction.FORWARD);
//
//        // Set all motors to zero power
        FRdrive.setPower(0);
        FLdrive.setPower(0);
        BRdrive.setPower(0);
        BLdrive.setPower(0);
//
//        platform.setPower(0);
//        arm.setPower(0);
//        intake.setPower(0);
//        carousel.setPower(0);


        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        FRdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FLdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BRdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BLdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        platform.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        carousel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        // Define and initialize ALL installed servos.
//        bucket = hwMap.servo.get("bucket");
//
//        // Set sensors
//        camera = hwMap.get(WebcamName.class, "frontcam");
        color = hwMap.get(RevColorSensorV3.class, "color");
//        distance = hwMap.get(Rev2mDistanceSensor.class, "distance");
//        colorR = hwMap.get(RevColorSensorV3.class, "colorR");
//        colorL = hwMap.get(RevColorSensorV3.class, "colorL");
//
//        //camera set-up
//        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
  //      webcam = OpenCvCameraFactory.getInstance().createWebcam(camera, cameraMonitorViewId);
    //    pipeline = new CapDeterminationPipeline();
      //  webcam.setPipeline(pipeline);
//
    }


    //stop drive motors
    public void stopMoving() {
        FRdrive.setPower(0);
        FLdrive.setPower(0);
        BRdrive.setPower(0);
        BLdrive.setPower(0);
    }

    //bucket positioning
    public void inBucket() {
//        bucket.setPosition(0.84);
    }

    public void mid_Bucket() {
//        bucket.setPosition(0.58);
    }

    public void outBucket() {
//        bucket.setPosition(0.3);
    }

    //movement methods (for autos)
//    public void moveArm(double power, int position) {
//        arm.setTargetPosition(arm_start + position);
//        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        arm.setPower(power);
    //   }

//    public void movePlatform(int position) {
//        platform.setTargetPosition(platform_start + position);
//        platform.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        ((DcMotorEx) platform).setVelocity(600);
    //   }

    public void moveForwardTo(double power, double inches) {
        int position = inchesToPosition(inches);
        FL(FL, -position );
        FR(FR, -position);
        BR(BR, -position );
        BL(BL, -position);
        startMovingToPositionMC(power);
    }

    public void moveBackwardTo(double power, double inches) {
        int position = inchesToPosition(inches);
        FL(FL, position );
        FR(FR, position);
        BR(BR, position );
        BL(BL, position);
        startMovingToPositionMC(power);
    }

    public void spinLeft(double power, double inches) {
        int position = inchesToPosition(inches);
        FL(FL, -position );
        FR(FR, position);
        BR(BR, position );
        BL(BL, -position);
        startMovingToPositionMC(power);
    }

    public void spinRight(double power, double inches) {
        int position = inchesToPosition(inches);
        FL(FL, position );
        FR(FR, -position);
        BR(BR, -position );
        BL(BL, position);
        startMovingToPositionMC(power);
    }
    public void strafeRight(double power, double inches) {
        int position = inchesToPosition(inches);
        FL(FL, -position );
        FR(FR, position);
        BR(BR, -position );
        BL(BL,position);
        startMovingToPositionMC(power);
    }
    public void strafeLeft(double power, double inches) {
        int position = inchesToPosition(inches);
        FL(FL, position );
        FR(FR, -position);
        BR(BR, position );
        BL(BL, -position);
        startMovingToPositionMC(power);
    }


    protected int expandFL() {
        int i;
        sumFL = 0;
        for (i = 0; i < FL.length; i++) {
            sumFL += FL[i];
        }
        return sumFL;
    }
    protected int expandFR() {
        int i;
        sumFR = 0;
        for (i = 0; i < FR.length; i++) {
            sumFR += FR[i];
        }
        return sumFR;
    }
    protected int expandBL() {
        int i;
        sumBL = 0;
        for (i = 0; i < BL.length; i++) {
            sumBL += BL[i];
        }
        return sumBL;
    }
    protected int expandBR() {
        int i;
        sumBR = 0;
        for (i = 0; i < BR.length; i++) {
            sumBR += BR[i];
        }
        return sumBR;
    }

    private void startMovingToPositionMC(double power) {
        setMotorPositionMC();
        setPower(power);
        setMotorsMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    private void setMotorPositionMC() {
        expandFL();
        expandFR();
        expandBL();
        expandBR();
        FLdrive.setTargetPosition(sumFL);
        BLdrive.setTargetPosition(sumBL);
        FRdrive.setTargetPosition(sumFR);
        BRdrive.setTargetPosition(sumBR);
    }

    public void setMotorsMode(DcMotor.RunMode mode) {
        FRdrive.setMode(mode);
        FLdrive.setMode(mode);
        BRdrive.setMode(mode);
        BLdrive.setMode(mode);
    }


    public boolean isRobotMoving() {
        return FLdrive.isBusy() || FRdrive.isBusy() ||
                BLdrive.isBusy() || BRdrive.isBusy();}
//

        /*if (FLDriveMoving)
            FLDriveMoving = !isAtTargetPosition(FLdrive.getCurrentPosition(), FLdrive.getTargetPosition());
        if (FRDriveMoving)
            FRDriveMoving = !isAtTargetPosition(FRdrive.getCurrentPosition(), FRdrive.getTargetPosition());
        if (BLDriveMoving)
            BLDriveMoving = !isAtTargetPosition(BLdrive.getCurrentPosition(), BLdrive.getTargetPosition());
        if (BRDriveMoving)
            BRDriveMoving = !isAtTargetPosition(BRdrive.getCurrentPosition(), BRdrive.getTargetPosition());

        return FLDriveMoving || FRDriveMoving || BLDriveMoving || BRDriveMoving;
         */
    // }

    private boolean isAtTargetPosition(int current, int target) {
        return Math.abs(Math.abs(current) - Math.abs(target)) < 1.2;
    }



    public int[] FL(int FL[], int DeltaXR){
        FL[nfl] = DeltaXR;
        nfl++;
        return FL;
    }
    public int[] FR(int FR[], int DeltaXR){
        FR[nfr] = DeltaXR;
        nfr++;
        return FR;
    }
    public int[] BR(int BR[], int DeltaXR){
        BR[nbr] = DeltaXR;
        nbr++;
        return BR;
    }
    public int[] BL(int BL[], int DeltaXR){
        BL[nbl] = DeltaXR;
        nbl++;
        return BL;
    }


 /*   public void correctPos(){
        int correctionleft = (BLdrive.getCurrentPosition() - sumY);
        int correctionright = (BRdrive.getCurrentPosition() - sumX);
        AddDataX(RightOG, correctionright);
        AddDataY(LeftOG, correctionleft);
    }
*/
    private int inchesToPosition(double inches) {
        return (int) (inches * COUNTS_PER_INCH);
    }

    public void setPower(double power) {
        FLdrive.setPower(power);
        BLdrive.setPower(power);
        FRdrive.setPower(power);
        BRdrive.setPower(power);
    }


}
