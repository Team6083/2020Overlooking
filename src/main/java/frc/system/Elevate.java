package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Robot;

public class Elevate {
    private static WPI_VictorSPX up;
    private static WPI_VictorSPX rope;

    private static final int Up = 8;
    private static final int Rope = 2;

    public static void init() {
        up = new WPI_VictorSPX(Up);
        rope = new WPI_VictorSPX(Rope);
    }

    public static void teleop() {
        double speed = Robot.xbox.getTriggerAxis(Hand.kLeft) - Robot.xbox.getTriggerAxis(Hand.kRight);

        up.set(ControlMode.PercentOutput, speed);

        if (Robot.xbox.getXButton()) {
            rope.set(ControlMode.PercentOutput, 0.5);
        } else if (Robot.xbox.getAButton()) {
            rope.set(ControlMode.PercentOutput, -0.5);
        } else {
            rope.set(ControlMode.PercentOutput, 0);
        }
    }
}