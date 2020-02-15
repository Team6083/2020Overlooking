package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class SuckSent {
    private static WPI_VictorSPX suck;
    private static TalonSRX sent;


    private static AnalogPotentiometer analogPotentiometer;
    private static Encoder encoder;
    private static double distanceWantBallToMove = 10;//this variable need to be tune

    public static void init() {
        analogPotentiometer = new AnalogPotentiometer(0, 180, 30);
        encoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
        suck = new WPI_VictorSPX(9);
        sent = new TalonSRX(10);
    }

    public static void teleop() {
        encoder.setDistancePerPulse(0.15);//this parameter need to be measured

        if(analogPotentiometer.get() > 35){
            sent.set(ControlMode.PercentOutput, 0.3);
            if(encoder.getDistance() >= distanceWantBallToMove){
                sent.set(ControlMode.PercentOutput, 0);
          }
        }
        
        if(Robot.xbox.getYButton()){
            suck.set(ControlMode.PercentOutput, 0.5);
        }
        else{
            suck.set(ControlMode.PercentOutput,0);
        }
        

        if(Robot.xbox.getRawButton(6)){
            sent.set(ControlMode.PercentOutput, 0.4);
        }
        else{
            sent.set(ControlMode.PercentOutput, 0);
        }

        SmartDashboard.putNumber("Total distance",encoder.getDistance());
        SmartDashboard.putNumber("Anolog of Potentiometer", analogPotentiometer.get());


        /*if(analog.getValue() > analogLenth) {
            sent.set(ControlMode.PercentOutput, 0.5);
        }*/

        // analog.getValue();
        // analog.getVoltage();
    }
}