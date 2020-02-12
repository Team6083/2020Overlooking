package frc.system;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import org.team6083.lib.util.XBoxController;

public class Elevate {
    private static VictorSP Motor1;
    private static VictorSP Motor2;
    private static XBoxController xbox;
    private static Timer time;

    public static void init(final XBoxController joy) {
        Motor1 = new VictorSP(5);
        Motor2 = new VictorSP(6);
        xbox = joy;
        time = new Timer();
    }

    public static void teleop() {
        if (xbox.getRawButtonPressed(3)) {
            RunNSec(Motor1, 3);
            RunNSec(Motor2, 3);
        }
    }

    public static void RunNSec(VictorSP motor, int n) {
        time.reset();
        time.start();
        if (time.get() < n) {
            motor.set(0.5);
        } else {
            motor.set(0);
        }
    }
}