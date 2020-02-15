package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Robot;
import frc.robot.TheMotor;

public class Shooting {
    private static WPI_VictorSPX shootLeft;

    public static void init() {
        shootLeft = new WPI_VictorSPX(7);
    }

    public static void teleop() {
        if(Robot.xbox.getBButton()){
            shootLeft.set(ControlMode.PercentOutput, 0.7);
            TheMotor.run("shoot");
        } else {
            shootLeft.set(ControlMode.PercentOutput, 0);
            TheMotor.run("stop");
        }
    }
}