package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.team6083.lib.RobotPower;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class SuckSent {
    private static WPI_VictorSPX suck;
    private static TalonSRX sent;
    private static RobotPower power;

    private static Timer timer = new Timer();
    private static boolean suckStarted = false;
    private static boolean analogSentStarted = false;

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
        suckStarted = false;
        analogSentStarted = false;
    }

    public static void teleop() {

        if (Robot.xbox.getYButtonPressed()) {
            if (suckStarted) {
                suckStarted = false;
                timer.stop();
                timer.reset();
            } else {
                suckStarted = true;
                timer.start();
            }
        }

        if (timer.get() > 5) {
            suckStarted = false;
            timer.stop();
            timer.reset();
        }

        if (Robot.xbox.getRawButton(9)) {
            suck.set(ControlMode.PercentOutput, -0.5);
        } else if (suckStarted) {
            suck.set(ControlMode.PercentOutput, 0.4);
        } else {
            suck.set(ControlMode.PercentOutput, 0);
        }

        if (analogSentStarted && analogInput.getValue() <= analogDistance && suckStarted) {
            suckStarted = false;
            timer.stop();
            timer.reset();
        }

        if (analogInput.getValue() > analogDistance && !Robot.xbox.getRawButton(9)) {
            analogSentStarted = true;
            sent.getSensorCollection().setQuadraturePosition(0, 100);
        }

        if (Robot.xbox.getRawButton(9)) {
            sent.set(ControlMode.PercentOutput, -0.3);
        } else if (Robot.xbox.getRawButton(10)) {
            sent.set(ControlMode.PercentOutput, 0.2);
        } else if (power.getPortCurrent() < shootAmp && power.getPortCurrent() != 0) {
            sent.set(ControlMode.PercentOutput, 0.6);
        } else if (analogSentStarted && sent.getSensorCollection().getQuadraturePosition() < distanceWantBallToMove) {
            sent.set(ControlMode.PercentOutput, 0.2);
        } else {
            sent.set(ControlMode.PercentOutput, 0);
        }

        if (sent.getSensorCollection().getQuadraturePosition() >= distanceWantBallToMove) {
            analogSentStarted = false;
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