package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import org.team6083.lib.util.XBoxController;

public class Elevate {
    private static WPI_VictorSPX Up1;
    private static WPI_VictorSPX Up2;
    private static XBoxController xbox;

    public static void init(final XBoxController joy) {
        Up1 = new WPI_VictorSPX(5);
        Up2 = new WPI_VictorSPX(6);
        xbox = joy;
    }

    public static void teleop() {
        if (xbox.getRawButton(3)) {
            Up1.set(ControlMode.PercentOutput, 0.5);
        } else if (xbox.getRawButton(4)) {
            Up1.set(ControlMode.PercentOutput, -0.5);
        } else if (xbox.getRawButton(5)) {
            Up2.set(ControlMode.PercentOutput, 0.5);
        } else if (xbox.getRawButton(6)) {
            Up2.set(ControlMode.PercentOutput, 0.5);
        } else {
            Up1.set(ControlMode.PercentOutput, 0);
            Up2.set(ControlMode.PercentOutput, 0);
        }
    }
}