package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.XBox;

public class SuckSent {
    private static WPI_VictorSPX suck;
    private static TalonSRX sent;
    private static XBox joy;

    public static void init(XBox xbox) {
        suck = new WPI_VictorSPX(4);
        sent = new TalonSRX(10);
        joy = xbox;
    }

    public static void teleop() {
        suck.set(ControlMode.PercentOutput, joy.getRawAxis(3)*0.5);

        if(joy.getRawButton(8)){
            sent.set(ControlMode.PercentOutput, 0.3);
        }
    }
}