package frc.system.auto;

import frc.system.Shooting;
import frc.system.VisionTracking;

public class StartLine extends AutoEngine {
    private static final int errAngle = 5;

    public static void loop() {
        switch (step) {
            case 0:
                /* turn 85 */
                gWalker.setTargetAngle(85);
                gWalker.calculate(leftSpeed, rightSpeed); // TODO: fix
                if (gWalker.getErrorAngle() < errAngle) {
                    leftSpeed = 0;
                    rightSpeed = 0;
                    gyro.reset(); // TODO: should not reset gyro during match
                    nextStep();
                }
                leftSpeed = 0;
                rightSpeed = 0;
                break;
            case 1:
                /* walk 78.85 inch */
                eWalker.walk(78.85);
                leftSpeed = eWalker.getLeftSpeed();
                rightSpeed = eWalker.getRightSpeed();
                gWalker.setTargetAngle(0);
                if (eWalker.getLeftDis() > 10 || eWalker.getRightDis() > 10) { // TODO: set the acceptabe error
                    leftSpeed = 0;
                    rightSpeed = 0;
                    gyro.reset(); // TODO: should not reset gyro during match
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