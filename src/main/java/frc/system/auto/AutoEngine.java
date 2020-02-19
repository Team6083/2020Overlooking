package frc.system.auto;

import org.team6083.lib.auto.EncoderWalker;
import org.team6083.lib.auto.GyroWalker;
import org.team6083.lib.auto.EncoderWalker.Mode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Gyroson;
import frc.system.DriveBase;

public class AutoEngine {
    protected static Gyroson gyro;
    protected static GyroWalker gWalker;
    protected static Encoder leftEnc, rightEnc;
    protected static EncoderWalker eWalker;
    protected static Timer autoTimer;

    protected static String currentStep;
    protected static int step;
    protected static double leftSpeed, rightSpeed;

    public static void init() {
        gyro = new Gyroson(SPI.Port.kMXP);
        gWalker = new GyroWalker(gyro);
        leftEnc = new Encoder(0, 1);
        rightEnc = new Encoder(8, 9);
        eWalker = new EncoderWalker(leftEnc, rightEnc, Mode.Both);
        autoTimer = new Timer();
        
        gyro.calibrate();
        while(gyro.isCalibrating());
        gyro.enableBoardlevelYawReset(true);
        gyro.reset();
        leftEnc.setReverseDirection(true);
        rightEnc.setReverseDirection(false);
    }

    public static void start() {
        step = 0;
		leftSpeed = 0;
		rightSpeed = 0;
		leftEnc.reset();
        rightEnc.reset();
        autoTimer.reset();
        autoTimer.start();
        gWalker.setTargetAngle(0);
        gWalker.setPID(0.002, 0.0005, 0);
    }

    public static void loop() {
        Step.loop();

        gWalker.calculate(leftSpeed, rightSpeed);
		leftSpeed = gWalker.getLeftPower();
		rightSpeed = gWalker.getRightPower();
		
        DriveBase.directControl(leftSpeed, -rightSpeed);
        
        SmartDashboard.putString("CurrentStep", currentStep);
        SmartDashboard.putNumber("Timer", autoTimer.get());
        SmartDashboard.putNumber("errorAngle", gWalker.getErrorAngle());
    }

    protected static void nextStep() {
		System.out.println("Finish step:"+currentStep+"("+step+")");
		autoTimer.stop();
		autoTimer.reset();
		autoTimer.start();
		leftEnc.reset();
        rightEnc.reset();
		step++;
	}
}