package frc.system;

import edu.wpi.first.wpilibj.Timer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import org.team6083.lib.util.XBoxController;

public class Elevate {
    private static WPI_VictorSPX Motor1;
    private static WPI_VictorSPX Motor2;
    private static XBoxController xbox;
    private static Timer time;

    public static void init(final XBoxController joy) {
        Motor1 = new WPI_VictorSPX(5);
        Motor2 = new WPI_VictorSPX(6);
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

    public static void RunNSec(WPI_VictorSPX motor, int n) {
        time.reset();
        time.start();
        if (time.get() < n) {
            motor.set(ControlMode.PercentOutput, 0.5);
        } else {
            motor.set(ControlMode.PercentOutput, 0);
        }
    }
}