// package frc.system;

// import org.team6083.lib.auto.GyroWalker;

// import edu.wpi.first.wpilibj.SPI;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.robot.Gyroson;
// /**
//  * Gyro6083
//  */
// public class Auto{
//   private  static Gyroson tol = new Gyroson(SPI.Port.kMXP);
//   private static GyroWalker gyroWalker = new GyroWalker(tol);
    
//   public static void autonomousInit()  {
//     tol.calibrate();
//     while(tol.isCalibrating());
//     tol.enableBoardlevelYawReset(true);
//     tol.reset();
//   }

//   public static void autonomousPeriodic(){
//     gyroWalker.setPID(0.015, 0, 0);
//     gyroWalker.setTargetAngle(0);
//     gyroWalker.setTargetAngle(0);
//     gyroWalker.calculate(0, 0);

//     double leftSpeed = gyroWalker.getLeftPower();
//     double rightSpeed = gyroWalker.getRightPower();

//     DriveBase.drive.directControl(leftSpeed , -rightSpeed);

//     SmartDashboard.putNumber("error", gyroWalker.getErrorAngle());
//     SmartDashboard.putNumber("target", gyroWalker.getTargetAngle());
//     SmartDashboard.putNumber("current", gyroWalker.getCurrentAngle());
//     SmartDashboard.putNumber("current source", tol.getYaw());
//     SmartDashboard.putNumber("left", leftSpeed);
//     SmartDashboard.putNumber("right", rightSpeed);
//   }
// }