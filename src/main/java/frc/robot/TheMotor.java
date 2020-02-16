package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TheMotor {
    private static WPI_VictorSPX motor;
    public static Modes mode;

    public static void init() {
        motor = new WPI_VictorSPX(1);
        mode = Modes.shootMode;
    }

    public static enum Modes{
        colorMode,
        shootMode
    }

    public static void runColor(double speed) {
        if(mode == Modes.colorMode){
            SmartDashboard.putString("TheMotor now running", "color");
            motor.set(ControlMode.PercentOutput, speed);
        }
    }

    public static void shoot(double speed) {
        if(mode == Modes.shootMode){
            SmartDashboard.putString("TheMotor now running", "shoot");
            motor.set(ControlMode.PercentOutput, speed);
        }
    }

    public static void setMode(Modes m){
        mode = m;
    }
}