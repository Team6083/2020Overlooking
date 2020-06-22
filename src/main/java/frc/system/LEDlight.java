package frc.system;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class LEDlight{

  private static AddressableLED led = new AddressableLED(0);

  private static AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(60);

  private static int rainbowFirstPixelHue = 1;

  private static Timer time = new Timer();

  public static XboxController xbox = new XboxController(0);

  boolean moveDirection = true;// true means becoming more brighten while false means becoming darken

  int recordRemainLight = ledBuffer.getLength();

  int location = 0;

  int ledLevel = 0;

  int x = 0;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  public static void LEDinit() {

    led.setLength(ledBuffer.getLength());

    led.setData(ledBuffer);
    led.start();
  }

  //need to write the condition when the robot is enable or not,is in autoMode or teleopMode
  public void LEDlights() {
        if (xbox.getBButton() || xbox.getYButton()) {
          dynamicRainbow();
        } else if (xbox.getBumper(Hand.kRight) || xbox.getBumper(Hand.kLeft)) {
          accelerationLight();
        } else {
          staticRainbow();
        }
      }
    
  

  // finished
  public void dynamicRainbow() {

    for (int i = 0; i < ledBuffer.getLength(); i++) {

      final int hue = (rainbowFirstPixelHue + (i * 180 / ledBuffer.getLength())) % 180;

      ledBuffer.setHSV(i, hue, 255, 128);
    }

    rainbowFirstPixelHue += 3;
    rainbowFirstPixelHue %= 180;

    led.setData(ledBuffer);
  }

  // finished
  public void staticRainbow() {
    if (time.get() > 1) {
      time.reset();
      x = x + 20;
      x %= 180;
      for (int i = 0; i < ledBuffer.getLength(); i++) {
        ledBuffer.setHSV(i, x, 255, 128);
      }
      led.setData(ledBuffer);

    }
  }

  public void autoLEDlight() {
    if (time.get() < 9) {
      for (int i = 0; i < recordRemainLight;) {
        ledBuffer.setHSV(i, 180, 100, 100);
        ledBuffer.setHSV(i + 1, 180, 100, 100);

        if (i > 3) {
          ledBuffer.setHSV(i - 2, 0, 0, 0);
        }
        i++;
      }
      recordRemainLight -= 2;
    } else if (time.get() > 9) {
      for (int i = 0; i < ledBuffer.getLength(); i++) {
        ledBuffer.setHSV(i, 0, 100, 100);
      }
    } else if (time.get() > 11) {
      for (int i = 0; i < ledBuffer.getLength(); i++) {
        ledBuffer.setHSV(i, 60, 100, 100);
      }
    } else if (time.get() > 13) {
      for (int i = 0; i < ledBuffer.getLength(); i++) {
        ledBuffer.setHSV(i, 120, 100, 100);
      }
    } else if (time.get() > 15) {
      for (int i = 0; i < ledBuffer.getLength(); i++) {
        ledBuffer.setHSV(i, 0, 0, 0);
      }
      time.reset();
      led.setData(ledBuffer);
    }

  }

  // have been finished
  public void standbyMomentLight() {
    if (ledLevel >= 0) {
      for (int i = 0; i < ledBuffer.getLength(); i++) {
        ledBuffer.setHSV(i, 180, 100, ledLevel);
      }
      if (moveDirection) {
        ledLevel += 5;
      } else {
        ledLevel -= 5;
      }
      led.setData(ledBuffer);
    }
    if (ledLevel >= 255) {
      moveDirection = false;
    }
    if (ledLevel <= 0) {
      moveDirection = true;
    }
  }

  // have been finished
  public void accelerationLight() {
    for (int i = 0; i < ledBuffer.getLength() / 4; i++) {
      if (i % 2 == 0) {
        for (int j = 0; j < 4; j++) {
          if (i * 4 + j + location < 60) {
            ledBuffer.setHSV(i * 4 + j + location, 0, 0, 255);
          }
        }
      } else {
        for (int j = 0; j < 4; j++) {
          if (i * 4 + j + location < 60) {
            ledBuffer.setHSV(i * 4 + j + location, 0, 0, 0);
          }
        }
      }
    }
    if (location > 1) {
      ledBuffer.setHSV(location - 1, 0, 0, 0);
    }
    if (time.get() > 0.005) {
      location++;
      time.reset();
    }
    if (location > 5) {
      location = 0;
    }
    led.setData(ledBuffer);
    SmartDashboard.putNumber("t", time.get());
  }
}
