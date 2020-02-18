/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.system.Auto;
import frc.system.ColorSense;
import frc.system.DriveBase;
import frc.system.Elevate;
import frc.system.Shooting;
import frc.system.SuckSent;
import frc.system.VisionTracking;
import frc.system.auto.AutoEngine;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static XBox xbox;
  public static TheMotor motor;
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  

  @Override
  public void robotInit() {
    // xbox = new XBox(0);
    // TheMotor.init();
    // ColorSense.init();
    DriveBase.init();
    // Elevate.init();
    // Shooting.init();
    // VisionTracking.init();
    // SuckSent.init();
    AutoEngine.init();
  }

  @Override
  public void autonomousInit() {
    // Auto.autonomousInit();
    AutoEngine.start();
  }

  @Override
  public void autonomousPeriodic() {
    // VisionTracking.seeking();
    // Auto.autonomousPeriodic();
    AutoEngine.loop();
  }

  @Override
  public void teleopInit() {
    ColorSense.teleopInit();
  }



  @Override
  public void teleopPeriodic() {
    ColorSense.teleop();
    DriveBase.teleop();
    Elevate.teleop();
    Shooting.teleop();
    VisionTracking.teleop();
    SuckSent.teleop();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
