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

    private static final int RP = 0;
    private static final int AI = 0;
    private static final int SK = 9;
    private static final int ST = 10;
    private static final int PW = 12;

    public static void init() {
        RobotPower.init(RP);

        analogInput = new AnalogInput(AI);
        suck = new WPI_VictorSPX(SK);
        sent = new TalonSRX(ST);
        power = new RobotPower(PW);

        sent.getSensorCollection().setQuadraturePosition(0, 100);
    }

    public static void teleop() {
        if (analogInput.getValue() > analogDistance && !Robot.xbox.getRawButton(9) && !Robot.xbox.getRawButton(10)) {
            sent.getSensorCollection().setQuadraturePosition(0, 100);
            sent.set(ControlMode.PercentOutput, 0.2);
        }

        if (sent.getSensorCollection().getQuadraturePosition() >= distanceWantBallToMove && !Robot.xbox.getRawButton(9)
                && !Robot.xbox.getRawButton(10)) {
            sent.set(ControlMode.PercentOutput, 0);
        }

        if (Robot.xbox.getYButtonPressed()) {
            suck.set(ControlMode.PercentOutput, 0.4);
        } else if (Robot.xbox.getYButtonReleased()) {
            suck.set(ControlMode.PercentOutput, 0);
        } else if (Robot.xbox.getRawButtonPressed(9)) {
            suck.set(ControlMode.PercentOutput, -0.3);
            sent.set(ControlMode.PercentOutput, -0.3);
        } else if (Robot.xbox.getRawButtonReleased(9)) {
            suck.set(ControlMode.PercentOutput, 0);
            sent.set(ControlMode.PercentOutput, 0);
        } else if (Robot.xbox.getRawButtonPressed(10)) {
            sent.set(ControlMode.PercentOutput, 0.2);
        } else if (Robot.xbox.getRawButtonReleased(10)) {
            sent.set(ControlMode.PercentOutput, 0);
        }

        if (power.getPortCurrent() < shootAmp && power.getPortCurrent() != 0) {
            sent.set(ControlMode.PercentOutput, 0.6);
        }

        showDashboard();
    }

    public static void autonomousSent() {
        if (power.getPortCurrent() < shootAmp && power.getPortCurrent() != 0) {
            sent.set(ControlMode.PercentOutput, 0.6);
        } else {
            sent.set(ControlMode.PercentOutput, 0);
        }
    }

    private static void showDashboard() {
        SmartDashboard.putNumber("SuckSent/ Shoot motor amp", power.getPortCurrent());
        SmartDashboard.putNumber("SuckSent/ Enc distance", sent.getSensorCollection().getQuadraturePosition());
        SmartDashboard.putNumber("SuckSent/ Analog Read", analogInput.getValue());
    }
}