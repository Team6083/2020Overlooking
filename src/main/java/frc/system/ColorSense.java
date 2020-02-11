package frc.system;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
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
    static int count = 0;

    private static final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private static final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private static final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private static final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    static XboxController joy;
    static VictorSP vicl1 = new VictorSP(1);

    public static void init(XboxController stick) {
        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);
        joy = stick;
    }

    public static void teleopInit() {
        count = 0;
    }

    public static void teleop() {
            String gameData, colString="";
            String colorString = "";
            final Color detectedColor = m_colorSensor.getColor();
            final ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
            final int proximity = m_colorSensor.getProximity();

            gameData = DriverStation.getInstance().getGameSpecificMessage();
            
            colorString = matchResult(match);
            colString=matchResult(match);
            SmartDashboard.putNumber("Red", detectedColor.red);
            SmartDashboard.putNumber("Green", detectedColor.green);
            SmartDashboard.putNumber("Blue", detectedColor.blue);
            SmartDashboard.putNumber("Confidence", match.confidence);
            SmartDashboard.putString("Detected Color", colorString);
            SmartDashboard.putNumber("Proximity", proximity);

            if (!lastDetectedColor.equals(colorString)) {
                if (match.color == kBlueTarget) {
                    count++;
                }
            }

                if (count >= 7) {
                    vicl1.set(0);
                } 
            if (joy.getRawButtonPressed(1)){
                vicl1.set(0.15);
            }

            SmartDashboard.putNumber("count", count);

            lastDetectedColor = colorString;

            
            if (gameData.length() > 0) {
                switch (gameData.charAt(0)) {
                case 'B': // Blue case code
                    chooseDetectedColor = "Red";
                    break;
                case 'G': // Green case code
                    chooseDetectedColor = "Yellow";
                    break;
                case 'R': // Red case code 
                    chooseDetectedColor = "Blue";
                    break;
                case 'Y': // Yellow case code
                    chooseDetectedColor = "Green";
                    break;
                default: // This is corrupt data
                }
            if (chooseDetectedColor.equals(colString)) {
                    vicl1.set(0);
                } 

            if (joy.getRawButtonPressed(2)) {
                count=0;
                vicl1.set(0.15);
            }
        }
        

   
    }

    public static String matchResult(final ColorMatchResult match) {
        if (match.color == kBlueTarget) {
            return "Blue";
        } else if (match.color == kRedTarget) {
            return "Red";
        } else if (match.color == kGreenTarget) {
            return "Green";
        } else if (match.color == kYellowTarget) {
            return "Yellow";
        } else {
            return "Unknown";
        }
    }

  
        
      


        

  
        
    }
