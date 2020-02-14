package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class TheMotor {
    private static WPI_VictorSPX motor;

    public static void init() {
        motor = new WPI_VictorSPX(8);
    }

    public static void run(String mode) {
        switch(mode){
            case "color":
                motor.set(ControlMode.PercentOutput, 0.15);
                break;
            case "shoot":
                motor.set(ControlMode.PercentOutput, 0.7);
                break;
            case "stop":
                motor.set(ControlMode.PercentOutput, 0);
        }
    }
}