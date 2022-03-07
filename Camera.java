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
import org.openftc.easyopencv.OpenCvPipeline;

@TeleOp(name = "camera_1_9", group = "RedMask")
public class Camera extends LinearOpMode {
    OpenCvCamera webcam;

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


        //while (opModeIsActive()) {
        webcam.openCameraDeviceAsync(
                new OpenCvCamera.AsyncCameraOpenListener() {
                    @Override
                    public void onOpened() {
                        webcam.startStreaming(640, 480);
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
        //}


       // while (!isStarted() && !isStopRequested()) {
        while(0==0) {
            telemetry.addData("Realtime analysis", pipeline.getAnalysis());
            telemetry.addData("position 1: ", position_1);
            telemetry.addData("position 2: ", position_2);
            telemetry.addData("position 3: ", position_3);
            telemetry.addData("FPS = ", webcam.getFps());
            telemetry.addData("FPS = ", webcam.getFrameCount());
            telemetry.update();

            // Don't burn CPU cycles busy-looping in this sample
            sleep(50);


            String snapshotAnalysis = pipeline.getAnalysis();


            /*
             * Show that snapshot on the telemetry
             */
//        telemetry.addData("Snapshot post-START analysis", snapshotAnalysis);
//        telemetry.update();

            boolean pos = pipeline.getLast_position();
//        telemetry.addData("2: ", pos);
//        telemetry.update();

            switch (snapshotAnalysis) {
                case "LEFT": {
                    /* Your autonomous code */
                    telemetry.addData("1: ", "in left");
                    telemetry.update();
                    break;
                }

                case "RIGHT": {
                    /* Your autonomous code */
                    telemetry.addData("1: ", "in right");
                    telemetry.update();
                    break;
                }

                case "CENTER": {
                    telemetry.addData("2: ", "in center");
                    telemetry.update();
                    /* Your autonomous code*/
                    break;
                }

                case "NONE": {
                    telemetry.addData("Opps: ", "default");
                    telemetry.update();
                    break;

                }
            }
        }//====>>
            /* You wouldn't have this in your autonomous, this is just to prevent the sample from ending */
          //  while (!isStopRequested()) {
                // Don't burn CPU cycles busy-looping in this sample
          //      sleep(50);
            //}



        //telemetry.update();


//        }
//        webcam.stopStreaming();
    }

    class ff_detector extends OpenCvPipeline {
        //private final double[] hslThresholdHue = {0.0, 34.09556313993175};
        //private final double[] hslThresholdSaturation = {110.07194244604317, 248.47269624573377};
        //private final double[] hslThresholdLuminance = {59.62230215827338, 255.0};
        private final double[] hslThresholdHue = {6.474820143884892, 17.201365187713304};
        private final double[] hslThresholdSaturation = {135.0356137566533, 250.62211042962218};
        private final double[] hslThresholdLuminance = {155.93525179856115, 226.71501706484642};

        public boolean getLast_position() {
            return last_position;
        }

        boolean last_position = true;


        @Override
        public Mat processFrame(Mat input) {
            telemetry.addData("1: ", "in camera process frame");
            telemetry.update();


            Mat filtered = new Mat();


            hslThreshold(input, hslThresholdHue, hslThresholdSaturation, hslThresholdLuminance, filtered);

            Mat out = new Mat();

            if (Core.countNonZero(filtered.submat(new Rect(115, 120, 120, 220))) > 100) {
                position_1 = true;

            } else if (Core.countNonZero(filtered.submat(new Rect(334, 110, 135, 215))) > 100) {
                position_2 = true;
            } else if (Core.countNonZero(filtered.submat(new Rect(515, 120, 120, 215))) > 100) {
                position_3 = true;
            }

            mask(input, filtered, out);
            last_position = position_1 || position_2 || position_3;

            input.release();
            filtered.release();

            return out;
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

