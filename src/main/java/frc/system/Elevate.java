package frc.system;

import edu.wpi.first.wpilibj.Timer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import org.team6083.lib.util.XBoxController;

public class Elevate {
    private static VictorSPX Motor1;
    private static VictorSPX Motor2;
    private static XBoxController xbox;
    private static Timer time;

    public static void init(final XBoxController joy) {
        Motor1 = new VictorSPX(5);
        Motor2 = new VictorSPX(6);
        xbox = joy;
        time = new Timer();
    }

    public static void teleop() {
        if (xbox.getRawButtonPressed(3)) {
            RunNSec(Motor1, 3);
            RunNSec(Motor2, 3);
            time.stop();
        }
    }

    public static void RunNSec(VictorSPX motor, int n) {
        time.reset();
        time.start();
        if (time.get() < n) {
            motor.set(ControlMode.PercentOutput, 0.5);
        } else {
            motor.set(ControlMode.PercentOutput, 0);
        }
    }
}