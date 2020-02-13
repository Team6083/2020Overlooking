package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.Robot;

public class SuckSent {
    private static AnalogInput analog;
    private static WPI_VictorSPX suck;
    private static TalonSRX sent;

    public static void init() {
        suck = new WPI_VictorSPX(4);
        sent = new TalonSRX(10);
    }

    public static void teleop() {
        suck.set(ControlMode.PercentOutput, Robot.xbox.getRawAxis(3)*0.5);
        sent.set(ControlMode.PercentOutput, Robot.xbox.getRawAxis(3)*0.5);

        analog.getValue();
        analog.getVoltage();
    }
}