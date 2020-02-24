package frc.system.auto;

import frc.system.VisionTracking;

public class AutoStep extends AutoEngine {
    private static final int errAngle = 5;

    public static void loop(int angle, double dis) {
        switch (step) {
            case 0:
                /* turn "angle" */
                currentStep = "turn";
                gWalker.setTargetAngle(angle);
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
                gWalker.setTargetAngle(0);
                if (eWalker.getLeftDis() > dis || eWalker.getRightDis() > dis) {
                    nextStep();
                }
                break;
            case 2:
                /* take aim and shoot */
                currentStep = "aim & shoot";
                VisionTracking.seeking();
                break;
        }
    }
}