package frc.system.auto;

import frc.system.Shooting;
import frc.system.VisionTracking;

public class FacePort extends AutoEngine {
    // private static int errAngle = 5;

    public static void loop() {
        switch (step) {
            case 0:
                /* take aim */
                VisionTracking.seeking();
                break;
        }
    }
}