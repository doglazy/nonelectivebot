package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class CapDeterminationPipeline extends OpenCvPipeline {
    enum ConePosition { //possibilities of cap position
        RIGHT,
        CENTER,
        LEFT
    }

    //color constants
    static final Scalar BLUE = new Scalar(0, 0, 255);
    static final Scalar GREEN = new Scalar(0, 255, 0);
    static final Scalar PURPLE = new Scalar(230, 230, 250);
    static final Scalar ORANGE = new Scalar(255, 99, 75);


    //sets points
    static final Point REGION1 = new Point (0,250);
    static final Point REGION2 = new Point (50,250);
    static final Point REGION3= new Point (100,250);
    static final Point REGION4 = new Point (150,250);
    static final Point REGION5 = new Point (200,250);
    static final Point REGION6 = new Point (250,250);
    static final Point REGION7 = new Point (300,250);
    static final Point REGION8 = new Point (350,250);
    static final Point REGION9 = new Point (400,250);
    static final Point REGION10 = new Point (450,250);
    static final Point REGION11 = new Point (500,250);
    static final Point REGION12 = new Point (550,250);
    static final Point REGION13 = new Point (590,250);
    static final Point REGIONv1 = new Point (300,200);
    static final Point REGIONv2 = new Point (300,150);
    static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(120, 230);
    static final int REGION_WIDTH = 50;
    static final int REGION_HEIGHT = 50;

    //creates points for rectangles
    Point region_pointA = new Point(
            REGION1_TOPLEFT_ANCHOR_POINT.x,
            REGION1_TOPLEFT_ANCHOR_POINT.y);
    Point region_pointB = new Point(
            REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
            REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
    Point region_pointA1 = new Point(
            REGION1.x,
            REGION1.y);
    Point region_pointB1 = new Point(
            REGION1.x + REGION_WIDTH,
            REGION1.y + REGION_HEIGHT);
    Point region_pointA2 = new Point(
            REGION2.x,
            REGION2.y);
    Point region_pointB2 = new Point(
            REGION2.x + REGION_WIDTH,
            REGION2.y + REGION_HEIGHT);
    Point region_pointA3 = new Point(
            REGION3.x,
            REGION3.y);
    Point region_pointB3 = new Point(
            REGION3.x + REGION_WIDTH,
            REGION3.y + REGION_HEIGHT);
    Point region_pointA4 = new Point(
            REGION4.x,
            REGION4.y);
    Point region_pointB4 = new Point(
            REGION4.x + REGION_WIDTH,
            REGION4.y + REGION_HEIGHT);
    Point region_pointA5 = new Point(
            REGION5.x,
            REGION5.y);
    Point region_pointB5 = new Point(
            REGION5.x + REGION_WIDTH,
            REGION5.y + REGION_HEIGHT);
    Point region_pointA6 = new Point(
            REGION6.x,
            REGION6.y);
    Point region_pointB6 = new Point(
            REGION6.x + REGION_WIDTH,
            REGION6.y + REGION_HEIGHT);
    Point region_pointA7 = new Point(
            REGION7.x,
            REGION7.y);
    Point region_pointB7 = new Point(
            REGION7.x + REGION_WIDTH,
            REGION7.y + REGION_HEIGHT);
    Point region_pointA8 = new Point(
            REGION8.x,
            REGION8.y);
    Point region_pointB8 = new Point(
            REGION8.x + REGION_WIDTH,
            REGION8.y + REGION_HEIGHT);
    Point region_pointA9 = new Point(
            REGION9.x,
            REGION9.y);
    Point region_pointB9 = new Point(
            REGION9.x + REGION_WIDTH,
            REGION9.y + REGION_HEIGHT);
    Point region_pointA10 = new Point(
            REGION10.x,
            REGION10.y);
    Point region_pointB10 = new Point(
            REGION10.x + REGION_WIDTH,
            REGION10.y + REGION_HEIGHT);
    Point region_pointA11 = new Point(
            REGION11.x,
            REGION11.y);
    Point region_pointB11 = new Point(
            REGION11.x + REGION_WIDTH,
            REGION11.y + REGION_HEIGHT);
    Point region_pointA12 = new Point(
            REGION12.x,
            REGION12.y);
    Point region_pointB12 = new Point(
            REGION12.x + REGION_WIDTH,
            REGION12.y + REGION_HEIGHT);
    Point region_pointA13 = new Point(
            REGION13.x,
            REGION13.y);
    Point region_pointB13 = new Point(
            REGION13.x + REGION_WIDTH,
            REGION13.y + REGION_HEIGHT);
    Point region_pointAv1 = new Point(
            REGIONv1.x,
            REGIONv1.y);
    Point region_pointBv1 = new Point(
            REGIONv1.x + REGION_WIDTH,
            REGIONv1.y + REGION_HEIGHT);
    Point region_pointAv2 = new Point(
            REGIONv2.x,
            REGIONv2.y);
    Point region_pointBv2 = new Point(
            REGIONv2.x + REGION_WIDTH,
            REGIONv2.y + REGION_HEIGHT);


    Mat region_Cr;
    Mat region_Cr1;
    Mat region_Cr2;
    Mat region_Cr3;
    Mat region_Cr4;
    Mat region_Cr5;
    Mat region_Cr6;
    Mat region_Cr7;
    Mat region_Cr8;
    Mat region_Cr9;
    Mat region_Cr10;
    Mat region_Cr11;
    Mat region_Cr12;
    Mat region_Cr13;
    Mat region_Crv1;
    Mat region_Crv2;
    Mat YCrCb = new Mat();
    Mat Cr = new Mat();
    int avg;
    int avg1;
    int avg2;
    int avg3;
    int avg4;
    int avg5;
    int avg6;
    int avg7;
    int avg8;
    int avg9;
    int avg10;
    int avg11;
    int avg12;
    int avg13;
    int avgv1;
    int avgv2;
    private volatile ConePosition position = ConePosition.CENTER;

    void inputToCr(Mat input) { //extracts chroma red channel for analysis
        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(YCrCb, Cr, 2);
    }

    @Override
    public void init(Mat firstFrame) {
        inputToCr(firstFrame);

        //creates 3 boxes which will be the regions for detection (one for each possible cap position)
        region_Cr = Cr.submat(new Rect(region_pointA, region_pointB));
        region_Cr1 = Cr.submat(new Rect(region_pointA1, region_pointB1));
        region_Cr2 = Cr.submat(new Rect(region_pointA2, region_pointB2));
        region_Cr3 = Cr.submat(new Rect(region_pointA3, region_pointB3));
        region_Cr4 = Cr.submat(new Rect(region_pointA4, region_pointB4));
        region_Cr5 = Cr.submat(new Rect(region_pointA5, region_pointB5));
        region_Cr6 = Cr.submat(new Rect(region_pointA6, region_pointB6));
        region_Cr7 = Cr.submat(new Rect(region_pointA7, region_pointB7));
        region_Cr8 = Cr.submat(new Rect(region_pointA8, region_pointB8));
        region_Cr9 = Cr.submat(new Rect(region_pointA9, region_pointB9));
        region_Cr10 = Cr.submat(new Rect(region_pointA10, region_pointB10));
        region_Cr11 = Cr.submat(new Rect(region_pointA11, region_pointB11));
        region_Cr12 = Cr.submat(new Rect(region_pointA12, region_pointB12));
        region_Cr13 = Cr.submat(new Rect(region_pointA13, region_pointB13));
        region_Crv1 = Cr.submat(new Rect(region_pointAv1, region_pointBv1));
        region_Crv2 = Cr.submat(new Rect(region_pointAv2, region_pointBv2));

    }


    @Override
    public Mat processFrame(Mat input) {
        inputToCr(input);

        //average Cr values in each box
        avg = (int) Core.mean(region_Cr).val[0];
        avg1 = (int) Core.mean(region_Cr1).val[0];
        avg2 = (int) Core.mean(region_Cr2).val[0];
        avg3 = (int) Core.mean(region_Cr3).val[0];
        avg4 = (int) Core.mean(region_Cr4).val[0];
        avg5 = (int) Core.mean(region_Cr5).val[0];
        avg6 = (int) Core.mean(region_Cr6).val[0];
        avg7 = (int) Core.mean(region_Cr7).val[0];
        avg8 = (int) Core.mean(region_Cr8).val[0];
        avg9 = (int) Core.mean(region_Cr9).val[0];
        avg10 = (int) Core.mean(region_Cr10).val[0];
        avg11 = (int) Core.mean(region_Cr11).val[0];
        avg12 = (int) Core.mean(region_Cr12).val[0];
        avg13 = (int) Core.mean(region_Cr13).val[0];
        avgv1 = (int) Core.mean(region_Crv1).val[0];
        avgv2 = (int) Core.mean(region_Crv2).val[0];


        //outlines box area on camera stream
        Imgproc.rectangle(
                input, // Buffer to draw on
                region_pointA, // First point which defines the rectangle
                region_pointB, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1);// Thickness of the rectangle lines
        Imgproc.rectangle(
                input, // Buffer to draw on
                region_pointA1, // First point which defines the rectangle
                region_pointB1, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1);
        Imgproc.rectangle(
                input, // Buffer to draw on
                region_pointA2, // First point which defines the rectangle
                region_pointB2, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1);
        Imgproc.rectangle(
                input, // Buffer to draw on
                region_pointA3, // First point which defines the rectangle
                region_pointB3, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1);
        Imgproc.rectangle(
                input, // Buffer to draw on
                region_pointA4, // First point which defines the rectangle
                region_pointB4, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1);
        Imgproc.rectangle(
                input, // Buffer to draw on
                region_pointA5, // First point which defines the rectangle
                region_pointB5, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1);
        Imgproc.rectangle(
                input, // Buffer to draw on
                region_pointA6, // First point which defines the rectangle
                region_pointB6, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1);
        Imgproc.rectangle(
                input, // Buffer to draw on
                region_pointA7, // First point which defines the rectangle
                region_pointB7, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1);
        Imgproc.rectangle(
                input, // Buffer to draw on
                region_pointA8, // First point which defines the rectangle
                region_pointB8, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1);
        Imgproc.rectangle(
                input, // Buffer to draw on
                region_pointA9, // First point which defines the rectangle
                region_pointB9, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1);
        Imgproc.rectangle(
                input, // Buffer to draw on
                region_pointA10, // First point which defines the rectangle
                region_pointB10, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1);
        Imgproc.rectangle(
                input, // Buffer to draw on
                region_pointA11, // First point which defines the rectangle
                region_pointB11, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1);
        Imgproc.rectangle(
                input, // Buffer to draw on
                region_pointA12, // First point which defines the rectangle
                region_pointB12, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1);
        Imgproc.rectangle(
                input, // Buffer to draw on
                region_pointA13, // First point which defines the rectangle
                region_pointB13, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1);
        Imgproc.rectangle(
                input, // Buffer to draw on
                region_pointAv1, // First point which defines the rectangle
                region_pointBv1, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1);
        Imgproc.rectangle(
                input, // Buffer to draw on
                region_pointAv2, // First point which defines the rectangle
                region_pointBv2, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1);
        if (avg1 < 110){
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointA1, // First point which defines the rectangle
                    region_pointB1, // Second point which defines the rectangle
                    ORANGE, // The color the rectangle is drawn in
                    1);
        }
        if (avg2 < 110){
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointA2, // First point which defines the rectangle
                    region_pointB2, // Second point which defines the rectangle
                    ORANGE, // The color the rectangle is drawn in
                    1);
        }
        if (avg3 < 110){
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointA3, // First point which defines the rectangle
                    region_pointB3, // Second point which defines the rectangle
                    ORANGE, // The color the rectangle is drawn in
                    1);
        }
        if (avg3 < 110){
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointA3, // First point which defines the rectangle
                    region_pointB3, // Second point which defines the rectangle
                    ORANGE, // The color the rectangle is drawn in
                    1);
        }
        if (avg4 < 110){
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointA4, // First point which defines the rectangle
                    region_pointB4, // Second point which defines the rectangle
                    ORANGE, // The color the rectangle is drawn in
                    1);
        }
        if (avg5 < 110){
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointA5, // First point which defines the rectangle
                    region_pointB5, // Second point which defines the rectangle
                    ORANGE, // The color the rectangle is drawn in
                    1);
        }
        if (avg6 < 110){
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointA6, // First point which defines the rectangle
                    region_pointB6, // Second point which defines the rectangle
                    ORANGE, // The color the rectangle is drawn in
                    1);
        }
        if (avg7 < 110){
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointA7, // First point which defines the rectangle
                    region_pointB7, // Second point which defines the rectangle
                    ORANGE, // The color the rectangle is drawn in
                    1);
        }
        if (avg8 < 110){
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointA8, // First point which defines the rectangle
                    region_pointB8, // Second point which defines the rectangle
                    ORANGE, // The color the rectangle is drawn in
                    1);
        }
        if (avg9 < 110){
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointA9, // First point which defines the rectangle
                    region_pointB9, // Second point which defines the rectangle
                    ORANGE, // The color the rectangle is drawn in
                    1);
        }
        if (avg10 < 110){
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointA10, // First point which defines the rectangle
                    region_pointB10, // Second point which defines the rectangle
                    ORANGE, // The color the rectangle is drawn in
                    1);
        }
        if (avg11 < 110){
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointA11, // First point which defines the rectangle
                    region_pointB11, // Second point which defines the rectangle
                    ORANGE, // The color the rectangle is drawn in
                    1);
        }
        if (avg12 < 110){
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointA12, // First point which defines the rectangle
                    region_pointB12, // Second point which defines the rectangle
                    ORANGE, // The color the rectangle is drawn in
                    1);
        }
        if (avg13 < 110){
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointA13, // First point which defines the rectangle
                    region_pointB13, // Second point which defines the rectangle
                    ORANGE, // The color the rectangle is drawn in
                    1);
        }
        if (avgv1 < 110){
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointAv1, // First point which defines the rectangle
                    region_pointBv1, // Second point which defines the rectangle
                    ORANGE, // The color the rectangle is drawn in
                    1);
        }
        if (avgv2 < 110){
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointAv2, // First point which defines the rectangle
                    region_pointBv2, // Second point which defines the rectangle
                    ORANGE, // The color the rectangle is drawn in
                    1);
        }

        //whichever is min, fill in box with green and assign position to according value (LEFT, CENTER, or RIGHT)
        if (avg < 120) {
            position = ConePosition.RIGHT;
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointA, // First point which defines the rectangle
                    region_pointB, // Second point which defines the rectangle
                    PURPLE, // The color the rectangle is drawn in
                    -1); // Negative thickness means solid fill

        } else if (avg < 130 && avg > 120) {
            position = ConePosition.CENTER;
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointA, // First point which defines the rectangle
                    region_pointB, // Second point which defines the rectangle
                    GREEN, // The color the rectangle is drawn in
                    -1); // Negative thickness means solid fill

        } else if (avg > 130) {
            position = ConePosition.LEFT;
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region_pointA, // First point which defines the rectangle
                    region_pointB, // Second point which defines the rectangle
                    ORANGE, // The color the rectangle is drawn in
                    -1); // Negative thickness means solid fill
        }

        return input;
    }

    public double findAvg() {return avg;}

    //to be called in auto files
    public boolean isCapLeft() {
        return position == ConePosition.LEFT;
    }
    public boolean isCapCenter() {
        return position == ConePosition.CENTER;
    }
    public boolean isCapRight() {
        return position == ConePosition.RIGHT;
    }

}