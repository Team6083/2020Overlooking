package frc.system.auto;

import frc.system.Shooting;
import frc.system.VisionTracking;

public class FacePort extends AutoEngine{
    private static int errAngle = 5;

    public static void loop(){
        switch(step){
            case 0:
                /* take aim */
                VisionTracking.seeking();
                break;
            case 1:
                /* shoot */
                Shooting.shoot(4);  // value needs to be tuned
                break;
            case 2:
                /* turn 85 */
                gWalker.setTargetAngle(85);
                gWalker.calculate(leftSpeed, rightSpeed);
                if(gWalker.getErrorAngle() < errAngle) {
                    gyro.reset();
                    nextStep();
                }
                leftSpeed = 0;
                rightSpeed = 0;
                break;
            case 3:
                /* walk 78.85 inch to side */
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