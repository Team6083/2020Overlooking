package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Robot;

public class Elevate {
    private static WPI_VictorSPX Up1;
    private static WPI_VictorSPX Up2;

    public static void init() {
        Up1 = new WPI_VictorSPX(8);
        Up2 = new WPI_VictorSPX(2);
    }

    public static void teleop() {
        if (Robot.xbox.getTriggerAxis(Hand.kRight)>0) {
            Up1.set(ControlMode.PercentOutput, 0.5);
        } else if (Robot.xbox.getTriggerAxis(Hand.kLeft)>0) {
            Up1.set(ControlMode.PercentOutput, -0.5);
        } else if (Robot.xbox.getXButton()) {
            Up2.set(ControlMode.PercentOutput, 0.5);
        } else if (Robot.xbox.getAButton()) {
            Up2.set(ControlMode.PercentOutput, -0.5);
        } else {
            Up1.set(ControlMode.PercentOutput, 0);
            Up2.set(ControlMode.PercentOutput, 0);
        }
    }
}