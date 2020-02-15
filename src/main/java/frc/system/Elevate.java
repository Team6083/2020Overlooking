package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Robot;

public class Elevate {
    private static WPI_VictorSPX Up;
    private static WPI_VictorSPX rope;
    private static double speed;

    public static void init() {
        Up = new WPI_VictorSPX(8);
        rope = new WPI_VictorSPX(2);
    }

    public static void teleop() {
        if(Robot.xbox.getTriggerAxis(Hand.kLeft)>0 || Robot.xbox.getTriggerAxis(Hand.kRight)>0){
            speed = Robot.xbox.getTriggerAxis(Hand.kLeft) - Robot.xbox.getTriggerAxis(Hand.kRight);
        }

        Up.set(ControlMode.PercentOutput,speed);
        
        if (Robot.xbox.getXButton()) {
            rope.set(ControlMode.PercentOutput, 0.5);
        } else if (Robot.xbox.getAButton()) {
            rope.set(ControlMode.PercentOutput, -0.5);
        } else {
            rope.set(ControlMode.PercentOutput, 0);
        }
    }
}