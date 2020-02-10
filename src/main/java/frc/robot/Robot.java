/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;


import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;


import com.revrobotics.ColorMatch;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();

  String lastDetectedColor = "";
  String chooseDetectedColor = "";

  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  Joystick joy = new Joystick(0);
  VictorSP vicl0 = new VictorSP(0);
  VictorSP vicl1 = new VictorSP(1);
  VictorSP vicl2 = new VictorSP(2);
  VictorSP vicl3 = new VictorSP(3);

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    count = 0;
  }


  int count = 0;

  @Override
  public void teleopPeriodic() {
    if (joy.getRawButton(1)){
      Color detectedColor = m_colorSensor.getColor();
      String colorString = "";
      ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

      if (match.color == kBlueTarget) {
        colorString = "Blue";
      } else if (match.color == kRedTarget) {
        colorString = "Red";
      } else if (match.color == kGreenTarget) {
        colorString = "Green";
      } else if (match.color == kYellowTarget) {
        colorString = "Yellow";
      } else {
        colorString = "Unknown";
      }

      SmartDashboard.putNumber("Red", detectedColor.red);
      SmartDashboard.putNumber("Green", detectedColor.green);
      SmartDashboard.putNumber("Blue", detectedColor.blue);
      SmartDashboard.putNumber("Confidence", match.confidence);
      SmartDashboard.putString("Detected Color", colorString);
      int proximity = m_colorSensor.getProximity();

      SmartDashboard.putNumber("Proximity", proximity);

      
        if (!lastDetectedColor.equals(colorString)) {

          if (match.color == kBlueTarget) {
            count++;
          }
        }

          if (count >= 7) {
          vicl1.set(0);
        } else {
          vicl1.set(0.15);
        }
      
    
    SmartDashboard.putNumber("count", count);

    lastDetectedColor = colorString;
    
    if(joy.getRawButton(2)){
      String gameData;
      gameData = DriverStation.getInstance().getGameSpecificMessage();
      Color detectedColor2 = m_colorSensor.getColor();
      String colString;
      ColorMatchResult match2 = m_colorMatcher.matchClosestColor(detectedColor2);
      if (match2.color == kBlueTarget) {
        colString = "Blue";
      } else if (match2.color == kRedTarget) {
        colString = "Red";
      } else if (match2.color == kGreenTarget) {
        colString = "Green";
      } else if (match2.color == kYellowTarget) {
        colString = "Yellow";
      } else {
        colString= "Unknown";
      }
  
      if (gameData.length() > 0) {
  
        switch (gameData.charAt(0)) {
          case 'B':// Blue case code
           chooseDetectedColor = "Red";
            break;
          case 'G':// Green case code
           chooseDetectedColor ="Yellow";
            break;
          case 'R':// Red case code
           chooseDetectedColor  = "Blue";
            break;
          case 'Y':// Yellow case code
            chooseDetectedColor = "Green";
            break;
          default:// This is corrupt data
            break;
          }
  
        if(!chooseDetectedColor .equals(colString)){
          vicl1.set(0.15);
        } 
        
        else{
          vicl1.set(0);
        }
  
      } else {
        // Code for no data received yet
      }
  
    }
    }

      String gameData;
      gameData = DriverStation.getInstance().getGameSpecificMessage();

    if (joy.getRawButton(2)){
      Color detectedColor = m_colorSensor.getColor();
      String colorString;
      ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
      if (match.color == kBlueTarget) {
        colorString = "Blue";
      } else if (match.color == kRedTarget) {
        colorString = "Red";
      } else if (match.color == kGreenTarget) {
        colorString = "Green";
      } else if (match.color == kYellowTarget) {
        colorString = "Yellow";
      } else {
        colorString = "Unknown";
      }
  
      if (gameData.length() > 0) {
  
        switch (gameData.charAt(0)) {
          case 'B':// Blue case code
           chooseDetectedColor = "Red";
            break;
          case 'G':// Green case code
           chooseDetectedColor ="Yellow";
            break;
          case 'R':// Red case code
           chooseDetectedColor  = "Blue";
            break;
          case 'Y':// Yellow case code
            chooseDetectedColor = "Green";
            break;
          default:// This is corrupt data
            break;
          }
  
        if(!chooseDetectedColor .equals(colorString)){
          vicl1.set(0.15);
        } 
        
        else{
          vicl1.set(0);
        }
  
      } else {
        // Code for no data received yet
      }
  
    }
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
