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
                gWalker.calculate(leftSpeed, rightSpeed);
                if (gWalker.getErrorAngle() < errAngle) {
                    leftSpeed = 0;
                    rightSpeed = 0;
                    gyro.reset();
                    nextStep();
                }
                leftSpeed = 0;
                rightSpeed = 0;
                break;
            case 1:
                /* walk 131.68 inch */
                eWalker.walk(20); // value needs to be tuned
                leftSpeed = eWalker.getLeftSpeed();
                rightSpeed = eWalker.getRightSpeed();
                if (eWalker.getLeftDis() < 20 || eWalker.getRightDis() < 20) { // value needs to be tuned
                    leftSpeed = 0;
                    rightSpeed = 0;
                    gyro.reset();
                    nextStep();
                }
                break;
            case 2:
                /* take aim */
                VisionTracking.seeking();
                break;
            case 3:
                /* shoot */
                Shooting.shoot(4); // value needs to be tuned
                break;
        }
    }
}