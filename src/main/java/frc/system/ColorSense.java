package frc.system;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;


import com.revrobotics.ColorMatch;

public class ColorSense {
    private static final I2C.Port i2cPort = I2C.Port.kOnboard;
    private static final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    private static final ColorMatch m_colorMatcher = new ColorMatch();

    static String lastDetectedColor = "";
    static String chooseDetectedColor = "";

    private static final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private static final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private static final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private static final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    static Joystick joy = new Joystick(0);
    static VictorSP vicl1 = new VictorSP(1);

    public static void init() {
        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);
    }

    public static void teleopInit() {
        count = 0;
    }

    static int count = 0;

    public static void teleop() {
        if (joy.getRawButton(1)) {
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

            if (joy.getRawButton(2)) {
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
                    colString = "Unknown";
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
}