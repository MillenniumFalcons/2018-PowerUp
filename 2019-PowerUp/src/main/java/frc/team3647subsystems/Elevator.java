package frc.team3647subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.*;

public class Elevator 
{
	/*
	 * 0. Start
	 * 1. Stop
	 * 2. Switch
	 * 3. Scale
	 * 4. Lower Scale
	 */
	public static int aimedElevatorState, elevatorEncoderValue, elevatorVelocity;
	
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

	public static void runElevator()
	{
		if(elevatorEncoderValue > Constants.elevatorSafetyLimit && overrideValue != 0)
		{
			currentWristState = 0;
			aimedElevatorState = -10;
		}
		else if(elevatorEncoderValue > Constants.elevatorSafetyLimit && overrideValue == 0)
		{
			currentWristState = 0;
			aimedElevatorState = -11;
		}
		else if(manualOverride)
		{
			currentWristState = 0;
			aimedElevatorState = -1;
		}
		else if(bottom)
		{
			currentWristState = 0;
			aimedElevatorState = 1;
		}
		else if(sWitch)
		{
			currentWristState = 0;
			aimedElevatorState = 2;
		}
		else if(lowerScale)
		{
			currentWristState = 0;
			aimedElevatorState = 3;
		}
		else if(scale)
		{
			currentWristState = 0;
			aimedElevatorState = 4;
		}
		switch(aimedElevatorState)
		{
			case -10:
				moveElevatorPosition(Constants.elevatorSafetyLimit);
				break;
			case -11:
				moveElevator(-0.2);
				break;
			case 0:
				moveBottom(false);
				break;
			case 1:
				moveBottom(true);
				encoderState = 0;
				break;
			case 2:
				moveSwitch();
				encoderState = 0;
				break;
			case 3:
				moveLowerScale();
				encoderState = 0;
				break;
			case 4:
				moveScale();
				encoderState = 0;
				break;
			case -1:
				if(!manualOverride)
				{
					overrideValue = 0;
				}
				moveManual(overrideValue);
				//moveElevator(overrideValue);
				break;
		}
	}
	
	public static void setElevatorEncoder()
	{
        if(reachedBottom())
		{
            resetElevatorEncoders();
		}
		elevatorEncoderValue = leftGearboxMaster.getSelectedSensorPosition(0);
		elevatorVelocity = leftGearboxMaster.getSelectedSensorVelocity(0);
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

	public static boolean reachedBottom()//false/true for comp, true/false for prac
	{
		if(!Robot.pracBot)
		{
			if(bannerSensor.get())
			{
				return false;//true for prac, false for comp
			}
			else
			{
				return true;
			}
		}
		else 
		{
			if(bannerSensor.get())
			{
				return true;//true for prac, false for comp
			}
			else
			{
				return false;
			}
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
        if(Math.abs(jValue) <.2 )
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
		switch(currentWristState)
		{
			case 0:
			if(Wrist.reachedFlat())
			{
				Wrist.stopWrist();
				currentWristState = 1;
			}
			else
			{
				Wrist.moveToFlat();
			}
			break;
			default:
			break;
		}
		leftGearboxMaster.selectProfileSlot(Constants.carriagePID, 0);
		moveElevatorPosition(Constants.sWitch);
	}
	
	public static void moveLowerScale()
	{
		switch(currentWristState)
		{
			case 0:
			if(Wrist.reachedFlat())
			{
				Wrist.stopWrist();
				currentWristState = 1;
			}
			else
			{
				Wrist.moveToFlat();
			}
			break;
			default:
			break;
		}
		leftGearboxMaster.selectProfileSlot(Constants.interstagePID, 0);
		moveElevatorPosition(Constants.lowerScale);		
	}
	public static int currentWristState = 1;

	public static void moveScale()
	{
		switch(currentWristState)
		{
			case 0:
			if(Wrist.reachedFlat())
			{
				Wrist.stopWrist();
				currentWristState = 1;
			}
			else
			{
				Wrist.moveToFlat();
			}
			break;
			default:
			break;
		}
		leftGearboxMaster.selectProfileSlot(Constants.interstagePID, 0);
		moveElevatorPosition(Constants.scale);
	}

	public static void moveScaleAuto()
	{
		leftGearboxMaster.selectProfileSlot(Constants.interstagePID, 0);
		moveElevatorPosition(Constants.scale);
	}
	
	public static void moveBottom(boolean moveWristUp)
	{
		leftGearboxMaster.selectProfileSlot(Constants.carriagePID, 0);
		if(moveWristUp)
		{
			if(IntakeWheels.getIntakeBannerSensor())//no cube
			{
				switch(currentWristState)
				{
					case 0:
						if(reachedBottom() && Wrist.reachedUp())
						{
							
							currentWristState = 1;
						}
						else
						{
							Wrist.moveUp();
						}
						break;
					case 1:

						break;
				}
			}
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

	public static double newPosition;

	// public static void moveManual(double jValue)
	// {
	// 	int currentPosition = elevatorEncoderValue;
	// 	if(jValue > 0.05)
	// 	{
	// 		newPosition = currentPosition + jValue;
	// 	}
	// 	else if(jValue < -0.05)
	// 	{
	// 		newPosition = currentPosition - jValue;
	// 	}
	// 	moveElevatorPosition(newPosition);
	// }

	public static int encoderState;
	public static int manualEncoderValue;
	public static int manualAdjustment;

	public static void moveManual(double jValue)
	{
		if(jValue > 0)
		{
			moveElevator(overrideValue * 0.65);
			manualAdjustment = 1500;
			encoderState = 0;
		}
		else if(jValue < 0)
		{
			moveElevator(overrideValue * 0.2);
			manualAdjustment = 0;
			encoderState = 0;
		}
		else
		{
			switch(encoderState)
			{
				case 0:
					manualEncoderValue = elevatorEncoderValue + manualAdjustment;
					encoderState = 1;
					break;
				case 1:
					moveElevatorPosition(manualEncoderValue);
					break;
			}
		}
	}
	
    
    public static void testElevatorEncoders()
    {
        System.out.println("Elevator Encoder Value: " + elevatorEncoderValue + "Elevator Velocity: " + elevatorVelocity);
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
