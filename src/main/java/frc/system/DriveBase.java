package frc.system;

import org.team6083.lib.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class DriveBase {
    public static DifferentialDrive drive;
    // private static WPI_VictorSPX leftMotor1;
    // private static WPI_VictorSPX leftMotor2;
    // private static WPI_VictorSPX rightMotor1;
    // private static WPI_VictorSPX rightMotor2;
    private static VictorSP leftMotor1;
    private static VictorSP leftMotor2;
    private static VictorSP rightMotor1;
    private static VictorSP rightMotor2;

    public static final int lm1 = 4;
    public static final int lm2 = 6;
    public static final int rm1 = 3;
    public static final int rm2 = 5;

    public static void init() {
        // leftMotor1 = new WPI_VictorSPX(lm1);
        // leftMotor2 = new WPI_VictorSPX(lm2);
        // rightMotor1 = new WPI_VictorSPX(rm1);
        // rightMotor2 = new WPI_VictorSPX(rm2);
        leftMotor1 = new VictorSP(0);
        leftMotor2 = new VictorSP(1);
        rightMotor1 = new VictorSP(2);
        rightMotor2 = new VictorSP(3);
        drive = new DifferentialDrive(leftMotor1,leftMotor2,rightMotor1,rightMotor2);
    }

    public static void teleop() {
        drive.tankDrive(Robot.xbox);
    }

    public static void track(double speed, double rotation, boolean input){
       drive.arcadeDrive(speed,rotation,input);
    }

    public static void directControl(double left, double right) {
        drive.directControl(left, right);
    }
}