package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Robot;

public class Shooting {
    private static WPI_VictorSPX shootLeft;
    private static WPI_VictorSPX shootRight;

    public static void init() {
        shootLeft = new WPI_VictorSPX(7);
        shootRight = Robot.motor;
    }

    public static void teleop() {
        if(Robot.xbox.getRawButton(7)){
            shootLeft.set(ControlMode.PercentOutput, -0.7);
            shootRight.set(ControlMode.PercentOutput, 0.7);
        } else {
            shootLeft.set(ControlMode.PercentOutput, 0);
            shootRight.set(ControlMode.PercentOutput, 0);
        }
    }
}