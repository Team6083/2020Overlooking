package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Robot;

public class Elevate {
    private static WPI_VictorSPX Up;
    private static WPI_VictorSPX rope;
    
    public static void init() {
        Up = new WPI_VictorSPX(8);
        rope = new WPI_VictorSPX(2);
    }

    public static void teleop() {
        double speed = Robot.xbox.getTriggerAxis(Hand.kLeft) - Robot.xbox.getTriggerAxis(Hand.kRight);

        Up.set(ControlMode.PercentOutput, speed);

        if (Robot.xbox.getXButton()) {
            rope.set(ControlMode.PercentOutput, 0.5);
        } else if (Robot.xbox.getAButton()) {
            rope.set(ControlMode.PercentOutput, -0.5);
        } else {
            rope.set(ControlMode.PercentOutput, 0);
        }
    }
}