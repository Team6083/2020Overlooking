package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Robot;
import frc.robot.TheMotor;
import frc.robot.TheMotor.Modes;

public class Shooting {
    private static WPI_VictorSPX shootLeft;
    private static final int SL = 7;

    public static void init() {
        shootLeft = new WPI_VictorSPX(SL);
    }

    public static void teleop() {
        if (Robot.xbox.getBButton()) {
            TheMotor.setMode(Modes.shootMode);
            shootLeft.set(ControlMode.PercentOutput, 0.65);
            TheMotor.shoot(-0.65);
        } else {
            shootLeft.set(ControlMode.PercentOutput, 0);
            TheMotor.shoot(0);
        }
    }

    public static void shoot(boolean doShoot) {
        if (doShoot) {
            TheMotor.setMode(Modes.shootMode);
            shootLeft.set(ControlMode.PercentOutput, 0.65);
            TheMotor.shoot(-0.65);
        } else {
            shootLeft.set(ControlMode.PercentOutput, 0);
            TheMotor.shoot(0);
        }
    }
}