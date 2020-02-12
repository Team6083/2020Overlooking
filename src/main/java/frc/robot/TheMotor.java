package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class TheMotor extends WPI_VictorSPX{
    public TheMotor(int port){
        super(port);
    }
}