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
    private static double distanceWantBallToMove = 4000;// this variable need to be tune
    private static int analogDistance = 100;
    private static double shootAmp = 5.1;

    public static void init() {
        RobotPower.init(0);

        analogInput = new AnalogInput(0);
        suck = new WPI_VictorSPX(9);
        sent = new TalonSRX(10);
        power = new RobotPower(12);

        sent.getSensorCollection().setQuadraturePosition(0, 100);
    }

    public static void teleop() {
        if (analogInput.getValue() > analogDistance && !Robot.xbox.getRawButton(9)) {
            sent.getSensorCollection().setQuadraturePosition(0, 100);
            sent.set(ControlMode.PercentOutput, 0.2);
        }

        if (sent.getSensorCollection().getQuadraturePosition() >= distanceWantBallToMove && !Robot.xbox.getRawButton(9)) {
            sent.set(ControlMode.PercentOutput, 0);
        }

        if (Robot.xbox.getYButton()) {
            suck.set(ControlMode.PercentOutput, 0.6);
        }else if (Robot.xbox.getRawButtonPressed(9)){
            suck.set(ControlMode.PercentOutput, -0.4);
            sent.set(ControlMode.PercentOutput, -0.4);
        }
        else if(Robot.xbox.getRawButtonReleased(9)) {
            suck.set(ControlMode.PercentOutput, 0);
            sent.set(ControlMode.PercentOutput, 0);
        }else {
            suck.set(ControlMode.PercentOutput, 0);
        }

        if (power.getPortCurrent() < shootAmp && power.getPortCurrent() != 0) {
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