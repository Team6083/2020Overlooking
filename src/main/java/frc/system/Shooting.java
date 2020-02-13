package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.XBox;

public class Shooting {
    private static WPI_VictorSPX shootLeft;
    private static WPI_VictorSPX shootRight;
    private static XboxController joy;

    public static void init(XBox xbox, WPI_VictorSPX motor) {
        shootLeft = new WPI_VictorSPX(7);
        shootRight = motor;
        joy = xbox;
    }

    public static void teleop() {
        if(joy.getRawButton(7)){
            shootLeft.set(ControlMode.PercentOutput, -0.7);
            shootRight.set(ControlMode.PercentOutput, 0.7);
        } else {
            shootLeft.set(ControlMode.PercentOutput, 0);
            shootRight.set(ControlMode.PercentOutput, 0);
        }
    }
}