package frc.team3647elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team3647ConstantsAndFunctions.Constants;

public class Elevator 
{
	/*
	 * 0. Start
	 * 1. Stop
	 * 2. Switch
	 * 3. Scale
	 * 4. Lower Scale
	 */
	public static int aimedElevatorState, elevatorEncoderValue;
	
	public static boolean bottom, sWitch, scale, lowerScale, moving, manualOverride, originalPositionButton;
    public static double overrideValue;
	
	public static DigitalInput bannerSensor = new DigitalInput(Constants.elevatorBannerSensor); 

	public static WPI_TalonSRX leftGearboxMaster = new WPI_TalonSRX(Constants.leftGearboxSRX);
	public static WPI_TalonSRX rightGearboxSRX = new WPI_TalonSRX(Constants.rightGearboxSRX);
	public static VictorSPX leftGearboxSPX = new VictorSPX(Constants.leftGearboxSPX);
	public static VictorSPX rightGearboxSPX = new VictorSPX(Constants.rightGearboxSPX);
	
	public static boolean reachedBottom = false;
    
    public static void elevatorInitialization()
	{
		leftGearboxMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Constants.kTimeoutMs);
		leftGearboxMaster.setSensorPhase(true); //if i set to false I might not need to invert gearbox motors

		//Configure PID Values
		leftGearboxMaster.selectProfileSlot(Constants.interstagePID, 0);
		leftGearboxMaster.config_kF(Constants.carriagePID, Constants.carriageF, Constants.kTimeoutMs);
		leftGearboxMaster.config_kP(Constants.carriagePID, Constants.carriageP, Constants.kTimeoutMs);
		leftGearboxMaster.config_kI(Constants.carriagePID, Constants.carriageI, Constants.kTimeoutMs);
		leftGearboxMaster.config_kD(Constants.carriagePID, Constants.carriageD, Constants.kTimeoutMs);	
		leftGearboxMaster.config_IntegralZone(Constants.carriagePID, Constants.carriageIZone, Constants.kTimeoutMs);

		leftGearboxMaster.config_kF(Constants.interstagePID, Constants.interstageF, Constants.kTimeoutMs);		
		leftGearboxMaster.config_kP(Constants.interstagePID, Constants.interstageP, Constants.kTimeoutMs);		
		leftGearboxMaster.config_kI(Constants.interstagePID, Constants.interstageI, Constants.kTimeoutMs);		
       	leftGearboxMaster.config_kD(Constants.interstagePID, Constants.interstageD, Constants.kTimeoutMs);	
		leftGearboxMaster.config_IntegralZone(Constants.interstagePID, Constants.interstageIZone, Constants.kTimeoutMs);
		
		//Motion Magic Constants
		leftGearboxMaster.configMotionCruiseVelocity(Constants.elevatorCruiseVelocity, Constants.kTimeoutMs);
		leftGearboxMaster.configMotionAcceleration(Constants.elevatorAcceleration, Constants.kTimeoutMs);

		//set up follows and invert
        rightGearboxSRX.follow(leftGearboxMaster);
        rightGearboxSPX.follow(leftGearboxMaster);
        leftGearboxSPX.follow(leftGearboxMaster);
        rightGearboxSRX.setInverted(true);
		rightGearboxSPX.setInverted(true);
		leftGearboxMaster.setInverted(true);
		leftGearboxSPX.setInverted(true);
	}

	public static void setElevatorEncoder()
	{
        if(reachedBottom())
		{
            resetElevatorEncoders();
		}
		elevatorEncoderValue = Elevator.leftGearboxMaster.getSensorCollection().getQuadraturePosition();
	}
	
	public static void resetElevatorEncoders()
	{
        Elevator.leftGearboxMaster.getSensorCollection().setQuadraturePosition(0, 10);
	}
	
    public static void stopElevator()
    {
        leftGearboxMaster.stopMotor();
    }
    
    public static void moveElevator(double speed)
    {
        leftGearboxMaster.set(ControlMode.PercentOutput, speed);
    }
    
    public static void moveElevatorPosition(double position)
    {
        leftGearboxMaster.set(ControlMode.MotionMagic, position);
    }

	public static boolean reachedBottom()
	{
        if(bannerSensor.get())
		{
            return false;
		}
		else
		{
            return true;
		}
	}
    
	public static void setElevatorButtons(boolean bottomButton, boolean switchButton, boolean scaleButton, boolean LSButton)
	{
        bottom = bottomButton;
		sWitch = switchButton;
		lowerScale = LSButton;
		scale = scaleButton;
	}
	
	public static void setManualOverride(double jValue)
	{
        if(Math.abs(jValue) <.1 )
		{
            manualOverride = false;
		}
		else
		{
            overrideValue = jValue;
			manualOverride = true;
		}
	}
    	
	public static void moveSwitch()
	{
		leftGearboxMaster.selectProfileSlot(Constants.carriagePID, 0);
		//Wrist.moveToFlat();
		moveElevatorPosition(Constants.sWitch);
	}
	
	public static void moveLowerScale()
	{
		leftGearboxMaster.selectProfileSlot(Constants.interstagePID, 0);
		//Wrist.moveToFlat();
		moveElevatorPosition(Constants.lowerScale);		
	}
	
	public static void moveScale()
	{
		leftGearboxMaster.selectProfileSlot(Constants.interstagePID, 0);
		//Wrist.moveToFlat();
		moveElevatorPosition(Constants.scale);
	}
	
	public static void moveBottom(boolean moveWristUp)
	{
		leftGearboxMaster.selectProfileSlot(Constants.carriagePID, 0);
		if(moveWristUp)
		{
			if(IntakeWheels.getIntakeBannerSensor())//no cube
			{	
				//Wrist.wristMotor.selectProfileSlot(Constants.noCubePID, 0);
				//Wrist.moveUp();
			}
			if(reachedBottom())
			{
				resetElevatorEncoders();
				stopElevator();
			}
			else
			{
				moveElevator(-0.4);
			}
		}
		else
		{
			if(reachedBottom())
			{
				resetElevatorEncoders();
				stopElevator();
			}
			else
			{
				moveElevator(-0.3);
			}
		}
	}
	
	public static void runElevator()
	{
		if(manualOverride)
		{
			aimedElevatorState = -1;
		}
		if(bottom)
		{
			aimedElevatorState = 1;
		}
		else if(sWitch)
		{
			aimedElevatorState = 2;
		}
		else if(lowerScale)
		{
			aimedElevatorState = 3;
		}
		else if(scale)
		{
			aimedElevatorState = 4;
		}
        switch(aimedElevatorState)
		{
			case 0:
				moveBottom(false);
			break;
			case 1:
				moveBottom(true);
			break;
			case 2:
				moveSwitch();
			break;
			case 3:
				moveLowerScale();
			break;
			case 4:
				moveScale();
			break;
			case -1:
				if(!manualOverride)
				{
					overrideValue = 0;
				}
				moveElevator(overrideValue);
			break;
        }
    }
    
    public static void testElevatorEncoders()
    {
        System.out.println("Elevator Encoder Value: " + elevatorEncoderValue);
    }
    
    public static void testBannerSensor()
    {
        if(reachedBottom())
        {
            System.out.println("Banner Sensor Triggered!");
        }
        else
        {
            System.out.println("Banner Sensor Not Triggered!");
        }
    }

    public static void testElevatorCurrent()
    {
        System.out.println("Right Elevator Current:" + leftGearboxMaster.getOutputCurrent());
	}
	
	public static boolean reachedSwitch()
	{
		if(elevatorEncoderValue > Constants.sWitch - 2000 && elevatorEncoderValue < Constants.sWitch + 2000)
		{
			return true;
		}
		else
		{
			return false; 
		}
	}
	
	public static boolean reachedLowerScale()
	{
		if(elevatorEncoderValue > Constants.lowerScale - 2000 && elevatorEncoderValue < Constants.lowerScale + 2500)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean reachedScale()
	{
		if(elevatorEncoderValue > Constants.scale - 2000 && elevatorEncoderValue < Constants.scale + 3000)
		{
			return true;
		}
		else
		{
			return false; 
		}
	}
	
	public static boolean reachedClimb()
	{
		if(elevatorEncoderValue > Constants.climb - 2000 && elevatorEncoderValue < Constants.climb + 2200)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
