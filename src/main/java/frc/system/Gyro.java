package frc.system;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.team6083.lib.auto.GyroWalker;

import edu.wpi.first.wpilibj.SpeedController;

public class Gyro {

    AHRS tol=new AHRS(SPI.Port.kMXP);
  GyroWalker gyroWalker = new GyroWalker(tol);
  private static WPI_VictorSPX leftMotor1;
   leftMotor1 = new WPI_VictorSPX(lm1);

  SpeedController leftMotor1 = new VictorSP(2);
  SpeedController l_motor2 = new VictorSP(3);
  SpeedController r_motor1 = new VictorSP(0);
  SpeedController r_motor2 = new VictorSP(1);
  DifferentialDrive drive = new DifferentialDrive(l_motor1, l_motor2, r_motor1, r_motor2);

  public static void init(){
    tol.reset();
  }

  public static void teleop() {
    tol.getRoll();
    tol.getYaw();
    gyroWalker.getTargetAngle();
    gyroWalker.getErrorAngle();


    
    gyroWalker.setPID(0.001, 0, 0);
    gyroWalker.setTargetAngle(90); 
    gyroWalker.calculate(0.1, 0.1);
    
    final double leftSpeed = gyroWalker.getLeftPower();
    final double rightSpeed = gyroWalker.getRightPower();
    
    drive.directControl(-leftSpeed, -rightSpeed);

    SmartDashboard.putNumber("tolx",tol.getRoll());
    SmartDashboard.putNumber("toly", tol.getYaw());
    SmartDashboard.putNumber("error", gyroWalker.getErrorAngle());
    SmartDashboard.putNumber("target",  gyroWalker.getTargetAngle());
  }

} 
