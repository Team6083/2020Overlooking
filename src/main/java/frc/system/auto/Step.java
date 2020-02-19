package frc.system.auto;

public class Step extends AutoEngine {
    private static final int errAngle = 5;

    public static void loop() {
        switch (step) {
            case 0:
                currentStep = "walk1";
                // eWalker.walk(10);
                // leftSpeed = eWalker.getLeftSpeed();
                // rightSpeed = eWalker.getRightSpeed();
                if (autoTimer.get() < 2) {
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
                currentStep = "turn1";
                gWalker.setTargetAngle(79);
                gWalker.calculate(leftSpeed, rightSpeed);
                if (gWalker.getErrorAngle() < errAngle) {
                    gyro.reset();
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
                gWalker.setTargetAngle(0);
                gWalker.calculate(leftSpeed, rightSpeed);
                if (autoTimer.get() < 2) {
                    leftSpeed = 0.2;
                    rightSpeed = 0.2;
                } else {
                    leftSpeed = 0;
                    rightSpeed = 0;
                    gyro.reset();
                    nextStep();
                }
                break;
            case 3:
                currentStep = "turn2";
                gWalker.setTargetAngle(76);
                gWalker.calculate(leftSpeed, rightSpeed);
                if (gWalker.getErrorAngle() < errAngle) {
                    gyro.reset();
                    nextStep();
                }
                leftSpeed = 0;
                rightSpeed = 0;
                break;
            case 4:
                currentStep = "walk3";
                gWalker.setTargetAngle(0);
                gWalker.calculate(leftSpeed, rightSpeed);
                if (autoTimer.get() < 2) {
                    leftSpeed = 0.2;
                    rightSpeed = 0.2;
                } else {
                    System.out.println("walk3 go next");
                    leftSpeed = 0;
                    rightSpeed = 0;
                    gyro.reset();
                }
                break;
        }
    }
}