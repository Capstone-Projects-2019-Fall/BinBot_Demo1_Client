package BinBotDemo1;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

public class VideoCap {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    VideoCapture capture;
    Mat mat = new Mat();


    VideoCap() {
        capture = new VideoCapture();
        capture.open(0);
    }

    BufferedImage getOneFrame() {
//        capture.read(mat2Image.mat);
//        return mat2Image.getImage(mat2Image.mat);

        if (capture.read(this.mat)) {
            BufferedImage img = new BufferedImage(mat.width(), mat.height(), BufferedImage.TYPE_3BYTE_BGR);
            WritableRaster raster = img.getRaster();
            DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
            byte[] data = dataBuffer.getData();
            mat.get(0, 0, data);
            return img;
        }
        return null;
    }
}
