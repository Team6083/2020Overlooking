package frc.system.auto;

public class Step extends AutoEngine {
    /* PRACTICE USE !!!!!!! */

    private static final int errAngle = 5;

    public static void loop() {
        switch (step) {
            case 0:
                eWalker.walk(10);
                leftSpeed = eWalker.getLeftSpeed();
                rightSpeed = eWalker.getRightSpeed();
                gWalker.setTargetAngle(0);
                break;
        }
    }
}