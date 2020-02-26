package frc.system.auto;

import frc.system.Shooting;
import frc.system.SuckSent;
import frc.system.VisionTracking;

public class AutoStep extends AutoEngine {
    private static final int errAngle = 5;

    public static void loop(int angle, double dis) {
        switch (step) {
            case 0:
                /* turn "angle" */
                currentStep = "turn";
                gWalker.setTargetAngle(angle);
                gWalker.calculate(leftSpeed, rightSpeed);
                if (gWalker.getErrorAngle() < errAngle) {
                    nextStep();
                }
                leftSpeed = 0;
                rightSpeed = 0;
                break;
            case 1:
                /* walk "dis" inch */
                currentStep = "walk";
                eWalker.walk(dis);
                leftSpeed = eWalker.getLeftSpeed();
                rightSpeed = eWalker.getRightSpeed();
                if (eWalker.getLeftDis() > dis || eWalker.getRightDis() > dis) {
                    nextStep();
                }
                break;
            case 2:
                /* take aim */
                currentStep = "aim";
                if (VisionTracking.detectIfTrackingFinished()) {
                    nextStep();
                } else {
                    VisionTracking.seeking();
                }
                break;
            case 3:
                /* shoot */
                currentStep = "shoot";
                leftSpeed = 0;
                rightSpeed = 0;
                Shooting.shoot(autoTimer.get() < 5);
                SuckSent.autonomousSent();
        }
    }
}