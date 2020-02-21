package frc.system.auto;

public class AutoStep extends AutoEngine{
    private static int errAngle = 5;

    public static void loop(){
        switch(step){
            case 0:
                /* take aim */
                // vision tracking take aim
                break;
            case 1:
                /* shoot */
                // shoot 3 ball
                break;
            case 2:
                /* turn 90 */
                gWalker.setTargetAngle(90);
                gWalker.calculate(leftSpeed, rightSpeed);
                if(gWalker.getErrorAngle() < errAngle) {
                    gyro.reset();
                    nextStep();
                }
                leftSpeed = 0;
                rightSpeed = 0;
                break;
            case 3:
                /* walk to side */
                eWalker.walk(10);  // value needs to be tuned
                leftSpeed = eWalker.getLeftSpeed();
                rightSpeed = eWalker.getRightSpeed();
                gWalker.setTargetAngle(0);
                break;
            case 4:
                /* turn 90 */
                gWalker.setTargetAngle(90);
                gWalker.calculate(leftSpeed, rightSpeed);
                if(gWalker.getErrorAngle() < errAngle) {
                    gyro.reset();
                    nextStep();
                }
                leftSpeed = 0;
                rightSpeed = 0;
                break;
            case 5:
                /* walk to ball */
                // walk a distance
                // suck simultaneously
                // DISCUSS: how many balls to suck?
                break;
            case 6:
                /* turn 180 */
                gWalker.setTargetAngle(180);
                gWalker.calculate(leftSpeed, rightSpeed);
                if(gWalker.getErrorAngle() < errAngle) {
                    gyro.reset();
                    nextStep();
                }
                leftSpeed = 0;
                rightSpeed = 0;
                break;
            case 7:
                /* take aim */
                // vision tracking take aim
                break;
            case 8:
                /* shoot */
                // shoot ? balls
        }
    }
}