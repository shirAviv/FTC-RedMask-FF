package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

@TeleOp(name = "camera_1_9", group = "RedMask")
public class Camera extends LinearOpMode {
    OpenCvWebcam webcam;

    boolean position_1 = false;
    boolean position_2 = false;
    boolean position_3 = false;


    ff_detector pipeline;


    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam"));


        pipeline = new ff_detector();
        webcam.setPipeline(pipeline);

        webcam.setMillisecondsPermissionTimeout(2500);

        //while (opModeIsActive()) {
        webcam.openCameraDeviceAsync(
                new OpenCvCamera.AsyncCameraOpenListener() {
                    @Override
                    public void onOpened() {
                        webcam.startStreaming(640, 360, OpenCvCameraRotation.UPRIGHT);
                        telemetry.addData("1: ", "in opened");
                        telemetry.addData("FPS = ", webcam.getFps());
                        telemetry.update();
                        sleep(100);
                    }


                    @Override
                    public void onError(int errorCode) {
                    }

                }

        );

        waitForStart();
        //}


       // while (!isStarted() && !isStopRequested()) {
        while(opModeIsActive()) {
            telemetry.addData("Frame count", webcam.getFrameCount());
            telemetry.addData("Fps", webcam.getFps());
//            telemetry.addData("Frame count", webcam.getFrameCount());
//            telemetry.addData("position 1: ", position_1);
//            telemetry.addData("position 2: ", position_2);
//            telemetry.addData("position 3: ", position_3);
//            telemetry.addData("FPS = ", webcam.getFps());
//            telemetry.addData("FPS = ", webcam.getFrameCount());
            int test=pipeline.getTest();
            boolean pos=pipeline.getLast_position();
            telemetry.addData("test",test);
            telemetry.addData("pos", pos);
            telemetry.addData("pos1", position_1);
            telemetry.addData("pos2", position_2);
            telemetry.addData("pos3", position_3);
            telemetry.update();



            // Don't burn CPU cycles busy-looping in this sample
            sleep(500);
            webcam.closeCameraDevice();


//            String snapshotAnalysis = pipeline.getAnalysis();


            /*
             * Show that snapshot on the telemetry
             */
//        telemetry.addData("Snapshot post-START analysis", snapshotAnalysis);
//        telemetry.update();

        }//====>>
            /* You wouldn't have this in your autonomous, this is just to prevent the sample from ending */
          //  while (!isStopRequested()) {
                // Don't burn CPU cycles busy-looping in this sample
          //      sleep(50);
            //}




    }

    class ff_detector extends OpenCvPipeline {
        //private final double[] hslThresholdHue = {0.0, 34.09556313993175};
        //private final double[] hslThresholdSaturation = {110.07194244604317, 248.47269624573377};
        //private final double[] hslThresholdLuminance = {59.62230215827338, 255.0};
        /*
        private final double[] hslThresholdHue = {6.474820143884892, 17.201365187713304};
        private final double[] hslThresholdSaturation = {135.0356137566533, 250.62211042962218};
        private final double[] hslThresholdLuminance = {155.93525179856115, 226.71501706484642};
         */
        private final double[] hslThresholdHue = {0, 255};
        private final double[] hslThresholdSaturation = {0 , 255};
        private final double[] hslThresholdLuminance = {0, 255};


        public boolean getLast_position() {
            return last_position;
        }

        boolean last_position = true;
        Mat filtered;
        Mat out;
        int test=5;

        public int getTest()
        {
            return test;
        }


        @Override
        public Mat processFrame(Mat input) {
            telemetry.addData("1: ", "in camera process frame");
            telemetry.update();


             filtered= new Mat();


            hslThreshold(input, hslThresholdHue, hslThresholdSaturation, hslThresholdLuminance, filtered);

            out = new Mat();
            Mat rec1 = filtered.submat(new Rect(115, 120, 120, 220));
            Mat rec2 = filtered.submat(new Rect(334, 110, 135, 215));
            Mat rec3= filtered.submat(new Rect(515, 120, 120, 215));
            int num_pixels_non_zero= Core.countNonZero(rec1);
            telemetry.addData("2: ", num_pixels_non_zero);
            telemetry.update();

            if (num_pixels_non_zero > 100) {
                position_1 = true;

            } else if (Core.countNonZero(rec2) > 100) {
                position_2 = true;
            } else if (Core.countNonZero(rec3) > 100) {
                position_3 = true;
            }

            mask(input, filtered, out);
            last_position = position_1 || position_2 || position_3;

            rec1.release();
            rec2.release();
            rec3.release();
            out.release();
            filtered.release();

            return input;
//             return input;
        }

        private void hslThreshold(Mat input, double[] hue, double[] sat, double[] lum,
                                  Mat out) {
            Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2HLS);
            Core.inRange(out, new Scalar(hue[0], lum[0], sat[0]),
                    new Scalar(hue[1], lum[1], sat[1]), out);
        }

        /**
         * Filter out an area of an image using a binary mask.
         *
         * @param input  The image on which the mask filters.
         * @param mask   The binary image that is used to filter.
         * @param output The image in which to store the output.
         */
        private void mask(Mat input, Mat mask, Mat output) {
            mask.convertTo(mask, CvType.CV_8UC1);
            Core.bitwise_xor(output, output, output);
            input.copyTo(output, mask);
        }


        public String getAnalysis() {
            if (position_1){
                return "LEFT";
            }else if (position_2){
                return "CENTER";
            }else if (position_3){
                return "RIGHT";
            }else{
                return "NONE";
            }
            //return "RIGHT";
        }
    }


}

