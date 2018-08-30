package team3647elevator;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.DigitalInput;
import team3647ConstantsAndFunctions.Constants;

public class Wrist 
{
	
	public static int wristState;
	public static int aimedWristState;

	public static WPI_TalonSRX wristMotor = new WPI_TalonSRX(Constants.wristPin);
	
	public static int elevatorState, aimedElevatorState;
	/*
	 * 0. Start
	 * 1. Flat
	 * 2. Sixty-Degrees
	 * 3. Facing Up
	 */

	public static boolean start, flat, aim, up, manualOverride, originalPositionButton;
	public static double overrideValue, speed, wristEncoder; 

	//Insert step 1 code below
	
	public static void moveWrist(double speed)
	{
		wristMotor.set(ControlMode.PercentOutput, speed);
	}
	
	public static void stopWrist()
	{
		moveWrist(0);
	}
	
	public static void configWristPID()
	{
		wristMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Constants.kTimeoutMs);
		wristMotor.setSensorPhase(true);
		wristMotor.selectProfileSlot(Constants.cubePID, 0);
		wristMotor.config_kF(Constants.noCubePID, Constants.noCubeF, Constants.kTimeoutMs);
		wristMotor.config_kP(Constants.noCubePID, Constants.noCubeP, Constants.kTimeoutMs);
		wristMotor.config_kI(Constants.noCubePID, Constants.noCubeI, Constants.kTimeoutMs);
		wristMotor.config_kD(Constants.noCubePID, Constants.noCubeD, Constants.kTimeoutMs);	

		wristMotor.config_kF(Constants.cubePID, Constants.cubeF, Constants.kTimeoutMs);		
		wristMotor.config_kP(Constants.cubePID, Constants.cubeP, Constants.kTimeoutMs);		
		wristMotor.config_kI(Constants.cubePID, Constants.cubeI, Constants.kTimeoutMs);		
		wristMotor.config_kD(Constants.cubePID, Constants.cubeD, Constants.kTimeoutMs);		
	}
	
	public static DigitalInput limitSwitch = new DigitalInput(Constants.wristLimitSwitch); 
	
	public static void setLimitSwitch()
	{
		flat = limitSwitch.get();
	}
	
	public static void testLimitSwitch()
	{
		if(flat)
		{
			System.out.println("Limit switch triggered");
		}
		else
		{
			System.out.println("Limit switch not triggered");
		}
	}
	
	public static double wristEncoderValue;
	
	public void setWristEncoders()
	{
		if(flat)
		{
			resetWristEncoders();
		}
		wristEncoderValue = wristMotor.getSensorCollection().getQuadraturePosition();
	}
	
	public static void resetWristEncoders()
	{
		wristMotor.getSensorCollection().getQuadraturePosition(0, 10);
	}
	
	public static void testWristEncoders()
	{
		System.out.println("Wrist Encoder Value: " + wristEncoderValue);
	}
	
	public static void setWristButtons(boolean flatButton, boolean aimButton, boolean upButton)
	{
		flat = flatButton;
		aim = aimButton;
		up = upButton;
	}
	
	public static boolean reachedFlat()
	{
		return flat;
	}
	
	public static boolean reachedAim()
	{
		if(wristEncoder > Constants.aim - 50 && wristEncoder < Constants.aim + 50)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean reachedUp()
	{
		if(wristEncoder > Constants.up - 100 && wristEncoder < Constants.up + 100)
		{
			return true;
		}
		else
		{
			return false; 
		}
	}
	
	public static void moveToAim() // can be used for maintain as well
	{
		wristMotor.set(ControlMode.Position, Constants.aim);
	}
	
	public static void moveToUp()
	{
		wristMotor.set(ControlMode.Position, Constants.up);
	}
	
	public static void moveToFlat()
	{
		if(flat)
		{
			stopWrist();
		}
		else
		{
			moveWrist(-.2);
		}
	}
	
	public static void runWrist()
	{
		int wristPID;
		if(!IntakeWheels.getIntakeBannerSenor())//no cube
		{
			wristPID = Constants.noCubePID;
		}
		else
		{
			wristPID = Constants.cubePID;
		}
		wristMotor.selectProfileSlot(wristPID, 0);
		if(manualOverride)
		{
			aimedWristState = -1;
		}
		else if(flat)
		{
			aimedWristState = 1;
		}
		else if(aim)
		{
			aimedWristState = 2;
		}
		else if (up)
		{
			aimedWristState = 3;
		}
		switch(aimedWristState)
		{
			case 1:
				moveToFlat();
				break;
			case 2:
				moveToAim();
				break;
			case 3:
				moveToUp();
				break;
			case -1:
				if(!manualOverride)
				{
					overrideValue = 0;
				}
				moveWrist(overrideValue);
				break;
			}
		
	}
	
}

