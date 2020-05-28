package frc.system;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Robot;
import frc.robot.TheMotor;
import frc.robot.TheMotor.Modes;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;

public class ColorSense {
    private static ColorSensorV3 m_colorSensor;
    private static ColorMatch m_colorMatcher;

    private static String lastDetectedColor = "";
    private static String chooseDetectedColor = "";
    private static String gameData;
    private static String colString = "";
    private static String colorString = "";
    private static int count=0;
    private static int proximity;
    private static Color detectedColor;
    private static ColorMatchResult match;
    private static boolean coloron = false;

    private static final I2C.Port i2cPort = I2C.Port.kOnboard;
    private static final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private static final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private static final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private static final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    public static void init() {
        m_colorSensor = new ColorSensorV3(i2cPort);
        m_colorMatcher = new ColorMatch();

        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);
    }

    public static void teleop() {
        if(Robot.vicecontrol.getAButtonPressed()){
            coloron=!coloron;
        }
        if(coloron){
        detectedColor = m_colorSensor.getColor();
        match = m_colorMatcher.matchClosestColor(detectedColor);
        proximity = m_colorSensor.getProximity();
        gameData = DriverStation.getInstance().getGameSpecificMessage();

        colorString = matchResult(match);
        colString = matchResult(match);

        if (!lastDetectedColor.equals(colorString)) {
            if (match.color == kBlueTarget) {
                count++;
            }
        }

        if (count >= 7) {
            TheMotor.runColor(0);
        }

        if (Robot.vicecontrol.getPOV() == 0) {
            count = 0;
            TheMotor.setMode(Modes.colorMode);
            TheMotor.runColor(0.2);
        }

        runAssignedColor(); // run when give gameData

        lastDetectedColor = colorString;
        showDashboard();
    }
}

    private static String matchResult(ColorMatchResult match) {
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

    private static void runAssignedColor() {
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
                TheMotor.runColor(0);
            }

            if (Robot.vicecontrol.getPOV() == 90) {
                count = 0;
                TheMotor.setMode(Modes.colorMode);
                TheMotor.runColor(0.12);
            }
        }
    }

    private static void showDashboard() {
        SmartDashboard.putNumber("ColorSense/ Red", detectedColor.red);
        SmartDashboard.putNumber("ColorSense/ Green", detectedColor.green);
        SmartDashboard.putNumber("ColorSense/ Blue", detectedColor.blue);
        SmartDashboard.putNumber("ColorSense/ Confidence", match.confidence);
        SmartDashboard.putString("ColorSense/ Detected Color", colorString);
        SmartDashboard.putNumber("ColorSense/ Proximity", proximity);
        SmartDashboard.putNumber("ColorSense/ Count", count);
    }
}
