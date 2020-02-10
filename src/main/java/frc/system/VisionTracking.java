package frc.system;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.controller.PIDController;

public class VisionTracking{
    private boolean m_LimelightHasValidTarget = false;
    private double m_LimelightDriveCommand = 0.0;
    private double m_LimelightSteerCommand = 0.0;
    private double steering_adjust = 0.0;

    private PIDController PID_controller;

    // this variable should be adjust by the target area detected in the best place of the robot
    final double DESIRED_TARGET_Y_AXIS = 0.5;        // Area of the target when the robot reaches the wall

    final double MAX_DRIVE = 0.5;                   // Simple speed limit so we don't drive too fast
    final double MAX_STEER = 0.5;  

    public void Update_Limelight_Tracking()
    {

            double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
            double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
            double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
            //double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

            if (tv < 1.0)
            {
            m_LimelightHasValidTarget = false;
            m_LimelightDriveCommand = 0.0;
            m_LimelightSteerCommand = 0.0;
            return;
            }

            m_LimelightHasValidTarget = true;

            //wpilib function use to adjust the robot to aiming target
            steering_adjust = -PID_controller.calculate(tx, 0.0);

            //use MAX_DRIVE to limit robot turning speed
            if(steering_adjust > 0){
            if (steering_adjust > MAX_DRIVE)
            {
                steering_adjust = MAX_STEER;
            }
            }
            else if (steering_adjust < -MAX_DRIVE){
                steering_adjust = -MAX_STEER;
            }

            double drive_cmd = -PID_controller.calculate(ty,DESIRED_TARGET_Y_AXIS );

            //use MAX_DRIVE to limit robot forward speed
            if(drive_cmd > 0){
            if (drive_cmd > MAX_DRIVE)
            {
                drive_cmd = MAX_DRIVE;
            }
            }
            else if(drive_cmd < -MAX_DRIVE){
                drive_cmd = -MAX_DRIVE;
            }

            m_LimelightSteerCommand = steering_adjust;
            m_LimelightDriveCommand = drive_cmd;

    }

    public void seeking(){
        double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);

        if (tv == 0.0)
        {
          // We don't see the target, seek for the target by spinning in place at a safe speed.
          steering_adjust = 0.3;
          m_LimelightDriveCommand = 0.0;
        }
        else
        {
          Update_Limelight_Tracking();
        }
        
        m_LimelightSteerCommand = steering_adjust;
      }

    /**
     * 0	use the LED Mode set in the current pipeline
     * 1	force off
     * 2	force blink
     * 3	force on
     * @param ModeNumber use to set LED mode
     */

      public void setLEDMode(int ModeNumber){
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(ModeNumber);
      }

    /**
     * 0	Vision processor
     * 1	Driver Camera (Increases exposure, disables vision processing)
     * @param CamNumber use to set Cam mode
     */

      public void setCamMode(int CamNumber){
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(CamNumber);
      }
    
      /**
       * know whether the camera have detect the target or not
       * 1 means has detect valid target
       * 0 means hasn't detect valid target
       */
      public double getValidTarget(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
      }
    
      public double getHorizontalOffset(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
      }
    
      public double getVerticalOffset(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
      }
    
      public double getTargetArea(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
      }
    
      /**
       * 
       * @return Skew or rotation (-90 degrees to 0 degrees)
       */
      public double getRotation(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ts").getDouble(0);
      }
    
      /**
       *  The pipeline’s latency contribution (ms) Add at least 11ms for image capture latency.
       */
      public double getLatencyContribution(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tl").getDouble(0);
      }  
}
