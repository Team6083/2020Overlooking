package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.team6083.lib.RobotPower;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class SuckSent {
    private static WPI_VictorSPX suck;
    private static TalonSRX sent;
    private static RobotPower power;

    private static AnalogInput analogInput;
    private static double distanceWantBallToMove = 10;// this variable need to be tune
    private static int analogDistance = 35;

    public static void init() {
        RobotPower.init(0);

        analogInput = new AnalogInput(0);
        suck = new WPI_VictorSPX(9);
        sent = new TalonSRX(10);
        power = new RobotPower(12);
    }

    public static void teleop() {

        if (analogInput.getValue() > analogDistance) {
            sent.set(ControlMode.PercentOutput, 0.3);
        }

        if (sent.getSensorCollection().getPulseWidthPosition() >= distanceWantBallToMove) {
            sent.set(ControlMode.PercentOutput, 0);
        }

        if (Robot.xbox.getYButton()) {
            suck.set(ControlMode.PercentOutput, 0.5);
        } else {
            suck.set(ControlMode.PercentOutput, 0);
        }

        SmartDashboard.putNumber("shoot motor voltage", power.getPortCurrent());

        SmartDashboard.putNumber("Total distance", sent.getSensorCollection().getPulseWidthPosition());

        /*
         * if(analog.getValue() > analogLenth) { sent.set(ControlMode.PercentOutput,
         * 0.5); }
         */

        // analog.getValue();
        // analog.getVoltage();
    }
}