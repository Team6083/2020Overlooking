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
    private static double distanceWantBallToMove = 3000;// this variable need to be tune
    private static int analogDistance = 100;
    private static double shootAmp = 3.7;

    public static void init() {
        RobotPower.init(0);

        analogInput = new AnalogInput(0);
        suck = new WPI_VictorSPX(9);
        sent = new TalonSRX(10);
        power = new RobotPower(12);

        sent.getSensorCollection().setQuadraturePosition(0, 100);
    }

    public static void teleop() {
        if (analogInput.getValue() > analogDistance) {
            sent.getSensorCollection().setQuadraturePosition(0, 100);
            sent.set(ControlMode.PercentOutput, 0.2);
        }

        
        if (sent.getSensorCollection().getQuadraturePosition() >= distanceWantBallToMove) {
            sent.set(ControlMode.PercentOutput, 0);
        }

        if (Robot.xbox.getYButton()) {
            suck.set(ControlMode.PercentOutput, 0.5);
        } else {
            suck.set(ControlMode.PercentOutput, 0);
        }

        if(power.getPortCurrent() < shootAmp && power.getPortCurrent() != 0) {
            sent.set(ControlMode.PercentOutput, 0.6);
        }

        SmartDashboard.putNumber("shoot motor amp", power.getPortCurrent()); // 3.4

        SmartDashboard.putNumber("Total distance", sent.getSensorCollection().getQuadraturePosition());

        SmartDashboard.putNumber("Analog Read", analogInput.getValue());

        /*
         * if(analog.getValue() > analogLenth) { sent.set(ControlMode.PercentOutput,
         * 0.5); }
         */

        // analog.getValue();
        // analog.getVoltage();
    }
}