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



    Mat region_Cr;
    Mat YCrCb = new Mat();
    Mat Cr = new Mat();
    int avg;

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

    }


    @Override
    public Mat processFrame(Mat input) {
        inputToCr(input);

        //average Cr values in each box
        avg = (int) Core.mean(region_Cr).val[0];


        //outlines box area on camera stream
        Imgproc.rectangle(
                input, // Buffer to draw on
                region_pointA, // First point which defines the rectangle
                region_pointB, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1); // Thickness of the rectangle lines


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