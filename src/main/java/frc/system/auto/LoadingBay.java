package frc.system.auto;

import frc.system.Shooting;
import frc.system.VisionTracking;

public class LoadingBay extends AutoEngine {
    private static int errAngle = 5;

    public static void loop() {
        switch (step) {
            case 0:
                /* turn 80 */
                gWalker.setTargetAngle(80);
                gWalker.calculate(leftSpeed, rightSpeed); //TODO: fix
                if (gWalker.getErrorAngle() < errAngle) {
                    leftSpeed = 0;
                    rightSpeed = 0;
                    gyro.reset(); //TODO: should not reset gyro during match
                    nextStep();
                }
                leftSpeed = 0;
                rightSpeed = 0;
                break;
            case 1:
                /* walk 131.68 inch */
                eWalker.walk(131.68);
                leftSpeed = eWalker.getLeftSpeed();
                rightSpeed = eWalker.getRightSpeed();
                if (eWalker.getLeftDis() < 20 || eWalker.getRightDis() < 20) { //TODO: set the acceptabe error
                    leftSpeed = 0;
                    rightSpeed = 0;
                    gyro.reset(); //TODO: should not reset gyro during match
                    nextStep();
                }
                break;
            case 2:
                /* take aim */
                VisionTracking.seeking();
                break;
        }
    }
}