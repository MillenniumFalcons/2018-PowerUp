package frc.team3647subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;

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

	public static boolean start, flat, aim, up, manualOverride, originalPositionButton, limitSwitchState;
	public static double overrideValue, speed; 

	//Insert step 1 code below
	
	public static void moveWrist(double speed)
	{
		wristMotor.set(ControlMode.PercentOutput, speed);
	}
	
	public static void stopWrist()
	{
		wristMotor.stopMotor();
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
		wristMotor.config_IntegralZone(Constants.noCubePID, Constants.noCubeIZone, Constants.kTimeoutMs);

		wristMotor.config_kF(Constants.cubePID, Constants.cubeF, Constants.kTimeoutMs);		
		wristMotor.config_kP(Constants.cubePID, Constants.cubeP, Constants.kTimeoutMs);		
		wristMotor.config_kI(Constants.cubePID, Constants.cubeI, Constants.kTimeoutMs);		
		wristMotor.config_kD(Constants.cubePID, Constants.cubeD, Constants.kTimeoutMs);		
		wristMotor.config_IntegralZone(Constants.cubePID, Constants.cubeIZone, Constants.kTimeoutMs);

		//Config Motion Magic
		wristMotor.configMotionAcceleration(Constants.wristAcceleration, Constants.kTimeoutMs);
		wristMotor.configMotionCruiseVelocity(Constants.wristCruiseVelocity, Constants.kTimeoutMs);

	}
	
	public static DigitalInput limitSwitch = new DigitalInput(Constants.wristLimitSwitch); 
	
	public static void setLimitSwitch()
	{
		limitSwitchState = limitSwitch.get();
	}
	
	public static void testLimitSwitch()
	{
		if(limitSwitchState)
		{
			System.out.println("Limit switch triggered");
		}
		else
		{
			System.out.println("Limit switch not triggered");
		}
	}
	
	public static int wristEncoderValue, wristEncoderVelocity;
	
	public static void setWristEncoder()
	{
		if(reachedFlat())
		{
			resetWristEncoder();
		}
		wristEncoderValue = wristMotor.getSelectedSensorPosition(0);
		wristEncoderVelocity = wristMotor.getSelectedSensorVelocity(0);
	}
	public static void resetWristEncoder()
	{
		wristMotor.getSensorCollection().setQuadraturePosition(0, 10);
	}
	
	public static void testWristCurrent()
	{
		System.out.println("Wrist Current: " + wristMotor.getOutputCurrent());
	}

	public static void testWristEncoder()
	{
		System.out.println("Wrist Encoder Value: " + wristEncoderValue + "Wrist Velocity: " + wristEncoderVelocity);
	}
	
	public static void setWristButtons(boolean flatButton, boolean aimButton, boolean upButton)
	{
		flat = flatButton;
		aim = aimButton;
		up = upButton;
	}
	
	public static boolean reachedFlat()
	{
		if(limitSwitchState)
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
	public static boolean reachedAim()
	{
		//System.out.println(wristEncoderValue);
		if(wristEncoderValue > Constants.aim - 100 && wristEncoderValue < Constants.aim + 150)
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
		if(wristEncoderValue > Constants.up - 100 && wristEncoderValue < Constants.up + 50)
		{
			return true;
		}
		else
		{
			return false; 
		}
	}
	
	public static void moveToFlat()
	{
		if(reachedFlat())
		{
			stopWrist();
			resetWristEncoder();
		}
		else
		{
			moveWrist(-.22);
		}
	}

	public static void setManualWristOverride(double jValue)
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

	public static void moveWristPosition(double position)
	{
		wristMotor.set(ControlMode.MotionMagic, position);
	}

	public static void moveAim()
	{
		moveWristPosition(Constants.aim);
	}
	
	public static void moveUp()
	{
		moveWristPosition(Constants.up);
	}
	public static void runWrist()
	{
		int wristPID;
		if(IntakeWheels.getIntakeBannerSensor())//no cube
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
		else if(Elevator.currentWristState == 0)
		{
			aimedWristState = -10;
		}
		switch(aimedWristState)
		{
			case -10:
				break;
			case 1:
				moveToFlat();
				break;
			case 2:
				moveAim();
				break;
			case 3:
				moveUp();
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
