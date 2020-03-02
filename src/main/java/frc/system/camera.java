package frc.system;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

public class camera{
    public static UsbCamera usbcam;

    public static void init(){
        usbcam = CameraServer.getInstance().startAutomaticCapture();
    }

}