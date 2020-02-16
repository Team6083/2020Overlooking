package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class TheMotor {
    private static WPI_VictorSPX motor;
    public static Modes mode;

    public static void init() {
        motor = new WPI_VictorSPX(8);
        mode = Modes.shootMode;
    }

    public static enum Modes{
        colorMode,
        shootMode
    }

    public static void runColor(double speed) {
        if(mode == Modes.colorMode){
            motor.set(ControlMode.PercentOutput, speed);
        }
    }

    public static void shoot(double speed) {
        if(mode == Modes.shootMode){
            motor.set(ControlMode.PercentOutput, speed);
        }
    }

    public static void setMode(Modes m){
        mode = m;
    }
}