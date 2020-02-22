package frc.system;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;

import org.team6083.lib.RobotPower;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class VisionTracking {
  private static boolean m_LimelightHasValidTarget = false;
  private static double m_LimelightDriveCommand = 0.0;
  private static double m_LimelightSteerCommand = 0.0;
  private static double steering_adjust = 0.0;

  private static PIDController PID_controller;
  private static Timer time;

  // this variable should be adjust by the target area detected in the best place
  // of the robot
  static final double DESIRED_TARGET_Y_AXIS = 0.5; // Area of the target when the robot reaches the wall

  static final double MAX_DRIVE = 0.7; // Simple speed limit so we don't drive too fast
  static final double MAX_STEER = 0.7;

  static boolean getButtonPressed = false;

  public static void init() {
    time = new Timer();
    PID_controller = new PIDController(0.15, 0.01, 0);
    setCamMode(1);
    setLEDMode(0);
  }

  public static void teleop() {
    setLEDMode(1);
    SmartDashboard.putNumber("Robot voltage", RobotPower.getRobotVoltage());
    SmartDashboard.putBoolean("A Vision tracking button pressed: ", getButtonPressed);
    if (Robot.xbox.getStartButtonPressed()) {
      getButtonPressed = !getButtonPressed;
    }
    if (getButtonPressed) {
      setCamMode(0);
      setLEDMode(3);
      Update_Limelight_Tracking();
      DriveBase.track(m_LimelightDriveCommand, m_LimelightSteerCommand, false);
      if (detectIfTrackingFinished()) {
        getButtonPressed = false;
        setLEDMode(2);
      }
    }
  }

  public static void Update_Limelight_Tracking() {

    double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    // double ta =
    // NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

    if (tv < 1.0) {
      m_LimelightHasValidTarget = false;
      m_LimelightDriveCommand = 0.0;
      m_LimelightSteerCommand = 0.0;
      return;
    }

    m_LimelightHasValidTarget = true;

    // wpilib function use to adjust the robot to aiming target
    steering_adjust = -PID_controller.calculate(tx, 0.0);

    // use MAX_DRIVE to limit robot turning speed
    if (steering_adjust > 0) {
      if (steering_adjust > MAX_DRIVE) {
        steering_adjust = MAX_STEER;
      }
    } else if (steering_adjust < -MAX_DRIVE) {
      steering_adjust = -MAX_STEER;
    }

    double drive_cmd = PID_controller.calculate(ty, DESIRED_TARGET_Y_AXIS);

    // use MAX_DRIVE to limit robot forward speed
    if (drive_cmd > 0) {
      if (drive_cmd > MAX_DRIVE) {
        drive_cmd = MAX_DRIVE;
      }
    } else if (drive_cmd < -MAX_DRIVE) {
      drive_cmd = -MAX_DRIVE;
    }

    m_LimelightSteerCommand = steering_adjust;
    m_LimelightDriveCommand = drive_cmd;

  }

  public static void seeking() {
    setCamMode(0);
    setLEDMode(3);
    boolean automaticShootingFinished = false;
    double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);

    if (tv == 0.0) {
      // We don't see the target, seek for the target by spinning in place at a safe
      // speed.
      steering_adjust = 0.3;
      m_LimelightDriveCommand = 0.0;
      DriveBase.track(m_LimelightDriveCommand, m_LimelightSteerCommand, false);
    } else {
      Update_Limelight_Tracking();
      DriveBase.track(m_LimelightDriveCommand, m_LimelightSteerCommand, false);
      if (detectIfTrackingFinished()) {
        setLEDMode(2);
        setCamMode(1);
        Shooting.shoot(2);
        automaticShootingFinished = true;
      }
    }
    SmartDashboard.putBoolean("whether automatic shooting finished", automaticShootingFinished);
  }

  /**
   * 
   * @return whether the tracking finished or not
   */
  public static boolean detectIfTrackingFinished() {
    boolean detectedFinished = false;
    if (m_LimelightSteerCommand < 0.01 && m_LimelightDriveCommand < 0.01) {
      time.start();
      if (m_LimelightSteerCommand > 0.01 || m_LimelightDriveCommand > 0.01) {
        time.reset();
      }
      if (time.get() > 0.5) {
        detectedFinished = true;
      }
    } else {
      detectedFinished = false;
    }
    return detectedFinished;
  }

  /**
   * 0 use the LED Mode set in the current pipeline 1 force off 2 force blink 3
   * force on
   * 
   * @param ModeNumber use to set LED mode
   */

  public static void setLEDMode(int ModeNumber) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(ModeNumber);
  }

  /**
   * 0 Vision processor 1 Driver Camera (Increases exposure, disables vision
   * processing)
   * 
   * @param CamNumber use to set Cam mode
   */

  public static void setCamMode(int CamNumber) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(CamNumber);
  }

  /**
   * know whether the camera have detect the target or not 1 means has detect
   * valid target 0 means hasn't detect valid target
   */
  public static double getValidTarget() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
  }

  public static double getHorizontalOffset() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
  }

  public static double getVerticalOffset() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
  }

  public static double getTargetArea() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
  }

  /**
   * 
   * @return Skew or rotation (-90 degrees to 0 degrees)
   */
  public static double getRotation() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ts").getDouble(0);
  }

  /**
   * The pipeline’s latency contribution (ms) Add at least 11ms for image capture
   * latency.
   */
  public static double getLatencyContribution() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tl").getDouble(0);
  }
}
