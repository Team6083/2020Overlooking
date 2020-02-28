package frc.system.auto;

import org.team6083.lib.auto.EncoderWalker;
import org.team6083.lib.auto.GyroWalker;
import org.team6083.lib.auto.EncoderWalker.Mode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Gyroson;
import frc.system.DriveBase;

public class AutoEngine {
    protected static Gyroson gyro;
    protected static GyroWalker gWalker;
    protected static Encoder leftEnc, rightEnc;
    protected static EncoderWalker eWalker;
    protected static Timer autoTimer;
    protected static SendableChooser<String> chooser;
    protected static SmartDashboard dashboard;

    protected static String currentStep;
    protected static int step;
    protected static double leftSpeed, rightSpeed;
    protected static double disPerPulse = 0.05236;
    protected static boolean isAiming;

    
    protected static final String kDoNothing = "Do Nothing";
    protected static final String kPort = "Port";
    protected static final String kStartLine = "Start Line";
    protected static final String kLoaingBay = "Loading Bay";
    protected static String autoSelected;

    public static void init() {
        gyro = new Gyroson(SPI.Port.kMXP);
        gWalker = new GyroWalker(gyro);
        leftEnc = new Encoder(6, 7);
        rightEnc = new Encoder(8, 9);
        eWalker = new EncoderWalker(leftEnc, rightEnc, Mode.Both);
        autoTimer = new Timer();
        chooser = new SendableChooser<String>();
        
        gyro.calibrate();
        while(gyro.isCalibrating());
        gyro.enableBoardlevelYawReset(true);
        gyro.reset();
        leftEnc.setReverseDirection(true);
        rightEnc.setReverseDirection(false);
        leftEnc.setDistancePerPulse(disPerPulse);
        rightEnc.setDistancePerPulse(disPerPulse);

        chooser.setDefaultOption("Do Nothing", kDoNothing);
        chooser.addOption("Port", kPort);
        chooser.addOption("Start Line", kStartLine);
        chooser.addOption("Loading Bay", kLoaingBay);
        SmartDashboard.putData("Auto Choice", chooser);
    }

    public static void start() {
        autoSelected = chooser.getSelected();

        step = 0;
		leftSpeed = 0;
        rightSpeed = 0;
        
		leftEnc.reset();
        rightEnc.reset();
        autoTimer.reset();
        autoTimer.start();
        gWalker.setTargetAngle(0);
        gWalker.setPID(0.021, 0, 0.0015);
    }

    public static void loop() {
        switch(autoSelected) {
            case kPort:
                AutoStep.loop(0,0);
                break;
            case kStartLine:
                AutoStep.loop(85, 78.85);
                break;
            case kLoaingBay:
                AutoStep.loop(80, 131.68);
                break;
            case kDoNothing:
            default:
                currentStep = "DoNothing";
			    leftSpeed = 0;
			    rightSpeed = 0;
                gWalker.setTargetAngle(0);
                break;
        }

        gWalker.calculate(leftSpeed, rightSpeed);
		leftSpeed = gWalker.getLeftPower();
        rightSpeed = gWalker.getRightPower();
        if(!isAiming){
            DriveBase.directControl(leftSpeed, -rightSpeed);
        }
        
        SmartDashboard.putString("CurrentStep", currentStep);
		SmartDashboard.putNumber("Current Angle", gWalker.getCurrentAngle());
		SmartDashboard.putNumber("Target Angle", gWalker.getTargetAngle());
		SmartDashboard.putNumber("Error Angle", gWalker.getErrorAngle());
		SmartDashboard.putNumber("Left Dis", eWalker.getLeftDis());
		SmartDashboard.putNumber("Right Dis", eWalker.getRightDis());
		SmartDashboard.putNumber("Timer", autoTimer.get());
    }

    protected static void nextStep() {
		System.out.println("Finish step:"+currentStep+"("+step+")");
		autoTimer.stop();
		autoTimer.reset();
		autoTimer.start();
		leftEnc.reset();
        rightEnc.reset();
        leftSpeed = 0;
        rightSpeed = 0;
		step++;
	}
}
