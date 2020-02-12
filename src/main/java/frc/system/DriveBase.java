package frc.system;

import org.team6083.lib.drive.DifferentialDrive;
import org.team6083.lib.util.XBoxController;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class DriveBase {
    private static DifferentialDrive drive;
    private static WPI_VictorSPX leftMotor1;
    private static WPI_VictorSPX leftMotor2;
    private static WPI_VictorSPX rightMotor1;
    private static WPI_VictorSPX rightMotor2;
    private static XBoxController input;

    public static final int lm1 = 0;
    public static final int lm2 = 1;
    public static final int rm1 = 2;
    public static final int rm2 = 3;

    public static void init(XBoxController controller) {
        leftMotor1 = new WPI_VictorSPX(lm1);
        leftMotor2 = new WPI_VictorSPX(lm2);
        rightMotor1 = new WPI_VictorSPX(rm1);
        rightMotor2 = new WPI_VictorSPX(rm2);
        drive = new DifferentialDrive(leftMotor1,leftMotor2,rightMotor1,rightMotor2);
        input = controller;
    }

    public static void teleop() {
        drive.tankDrive(input);
    }
}