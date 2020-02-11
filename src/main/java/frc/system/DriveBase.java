package frc.system;

import org.team6083.lib.drive.DifferentialDrive;
import org.team6083.lib.drive.inputs.TankInput;
import org.team6083.lib.util.XBoxController;

import edu.wpi.first.wpilibj.VictorSP;

public class DriveBase {
    private static DifferentialDrive drive;
    private static VictorSP leftMotor1;
    private static VictorSP leftMotor2;
    private static VictorSP rightMotor1;
    private static VictorSP rightMotor2;
    private static TankInput input;

    public static void init() {
        leftMotor1 = new VictorSP(0);
        leftMotor2 = new VictorSP(1);
        rightMotor1 = new VictorSP(2);
        rightMotor2 = new VictorSP(3);
        drive = new DifferentialDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);
        input = new XBoxController(0);
    }

    public static void teleop() {
        drive.tankDrive(input);
    }
}