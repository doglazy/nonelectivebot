package org.firstinspires.ftc.teamcode;

import android.graphics.Paint;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class CapDeterminationPipeline extends OpenCvPipeline {

    enum CapPosition { //possibilities of cap position
        RIGHT,
        CENTER,
        LEFT
    }

    //color constants
    static final Scalar BLUE = new Scalar(0, 0, 255);
    static final Scalar RED = new Scalar(255, 0, 0);
    static final Scalar BLACK = new Scalar(0,0,0);
    static final Scalar GREEN = new Scalar(0, 255, 0);

    //sets points
    static final Point REGION2_TOPLEFT_ANCHOR_POINT = new Point(255, 220);
    static final int REGION_WIDTH = 20;
    static final int REGION_HEIGHT = 20;

    //creates points for rectangles
    Point region2_pointA = new Point(
            REGION2_TOPLEFT_ANCHOR_POINT.x,
            REGION2_TOPLEFT_ANCHOR_POINT.y);
    Point region2_pointB = new Point(
            REGION2_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
            REGION2_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);



    Mat region2_Cr;
    Mat YCrCb = new Mat();
    Mat G = new Mat();
    Mat R = new Mat();
    Mat B = new Mat();
    int avgb;
    int avgr;
    int avgg;

    private volatile CapPosition position = CapPosition.CENTER;

    void extractgreen(Mat input) { //extracts chroma red channel for analysis
        Core.extractChannel(input, G, 1);
    }
    void extractred(Mat input) { //extracts chroma red channel for analysis
        Core.extractChannel(input, R, 0);
    }
    void extractblue(Mat input) { //extracts chroma red channel for analysis
        Core.extractChannel(input, R, 2);
    }

    @Override
    public void init(Mat firstFrame) {
        //creates 3 boxes which will be the regions for detection (one for each possible cap position)
        G = G.submat(new Rect(region2_pointA, region2_pointB));
      //  R = R.submat(new Rect(region2_pointA, region2_pointB));
       // B = B.submat(new Rect(region2_pointA, region2_pointB));
    }

    @Override
    public Mat processFrame(Mat input) {
        extractgreen(input);

        //average Cr values in each box
        avgg = (int) Core.mean(G).val[0];
        //outlines box area on camera stream
        Imgproc.rectangle(
                input, // Buffer to draw on
                region2_pointA, // First point which defines the rectangle
                region2_pointB, // Second point which defines the rectangle
                BLUE, // The color the rectangle is drawn in
                1); // Thickness of the rectangle lines

        //find min Cr value out of the 3 boxes -> the min will be where the orange part of cap is detected
        int min = avgb;

        //whichever is min, fill in box with green and assign position to according value (LEFT, CENTER, or RIGHT)
        if ((avgb >= 150)&&(avgr<70)) {
            Imgproc.rectangle(
                    input, // Buffer to draw on
                    region2_pointA, // First point which defines the rectangle
                    region2_pointB, // Second point which defines the rectangle
                    BLUE, // The color the rectangle is drawn in
                    -1); // Negative thickness means solid fill
        }
        else if ( (avgb < 70) && (avgr >=150 ) ){
            Imgproc.rectangle(input,region2_pointA,region2_pointB,RED,-1);
        }
        else if ((avgb <= 50)&& (avgr <= 50)){  Imgproc.rectangle(input,region2_pointA,region2_pointB,BLUE,-1);

        }

        return input;
    }

    public double getavgb(){
        return avgb;
    }

    //to be called in auto files
    public boolean isCapLeft() {
        return position == CapPosition.LEFT;
    }
    public boolean isCapCenter() {
        return position == CapPosition.CENTER;
    }
    public boolean isCapRight() {
        return position == CapPosition.RIGHT;
    }

}