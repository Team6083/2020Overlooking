package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Robot;

public class Elevate {
    private static WPI_VictorSPX Up1;
    private static WPI_VictorSPX Up2;

    public static void init() {
        Up1 = new WPI_VictorSPX(1);
        Up2 = new WPI_VictorSPX(2);
    }

    public static void teleop() {
        if (Robot.xbox.getRawButton(3)) {
            Up1.set(ControlMode.PercentOutput, 0.5);
        } else if (Robot.xbox.getRawButton(4)) {
            Up1.set(ControlMode.PercentOutput, -0.5);
        } else if (Robot.xbox.getRawButton(5)) {
            Up2.set(ControlMode.PercentOutput, 0.5);
        } else if (Robot.xbox.getRawButton(6)) {
            Up2.set(ControlMode.PercentOutput, -0.5);
        } else {
            Up1.set(ControlMode.PercentOutput, 0);
            Up2.set(ControlMode.PercentOutput, 0);
        }
    }
}