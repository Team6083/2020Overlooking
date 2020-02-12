package frc.system;

import edu.wpi.first.wpilibj.VictorSP;
import org.team6083.lib.util.XBoxController;

public class Elevate {
    private static VictorSP Motor1;
    private static VictorSP Motor2;
    private static XBoxController xbox;

    public static void init(XBoxController joy){
        Motor1 = new VictorSP(5);
        Motor2 = new VictorSP(6);
        xbox = joy;
    }

    public static void teleop(){
        if(xbox.getRawButton(3)){
            Motor1.set(0.5);
            Motor2.set(0.5);
        } else if(xbox.getRawButton(4)) {
            Motor1.set(-0.5);
            Motor2.set(-0.5);
        } else {
            Motor1.set(0);
            Motor2.set(0);
        }
    }
}