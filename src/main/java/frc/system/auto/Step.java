package frc.system.auto;

public class Step extends AutoEngine {

    public static void loop() {
        switch (step) {
            case 0:
                currentStep = "walk1";
                // eWalker.walk(10);
                // leftSpeed = eWalker.getLeftSpeed();
                // rightSpeed = eWalker.getRightSpeed();
                if (autoTimer.get() < 1) {
                    leftSpeed = 0.2;
                    rightSpeed = 0.2;
                } else {
                    leftSpeed = 0;
                    rightSpeed = 0;
                    nextStep();
                }
                gWalker.setTargetAngle(0);
                break;
            case 1:
                currentStep = "turn";
                gWalker.setTargetAngle(180);
                gWalker.calculate(leftSpeed, rightSpeed);
                if (gWalker.getErrorAngle() < 10) {

                    System.out.println("turn nextStep");
                    nextStep();
                }
                leftSpeed = 0;
                rightSpeed = 0;
                break;
            case 2:
                currentStep = "walk2";
                // eWalker.walk(10);
                // leftSpeed = eWalker.getLeftSpeed();
                // rightSpeed = eWalker.getRightSpeed();
                if (autoTimer.get() < 1) {
                    leftSpeed = 0.2;
                    rightSpeed = 0.2;
                } else {
                    leftSpeed = 0;
                    rightSpeed = 0;
                }
                break;
        }
    }
}