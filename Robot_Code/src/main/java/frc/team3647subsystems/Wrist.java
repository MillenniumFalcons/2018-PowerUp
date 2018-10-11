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
	public static DigitalInput limitSwitch = new DigitalInput(Constants.wristLimitSwitch); 
	
	public static int elevatorState;
	/*
	* 0. Start
	* 1. Flat
	* 2. Sixty-Degrees
	* 3. Facing Up
	*/
	
	public static boolean start, flat, aim, up, manualOverride, originalPositionButton;
	public static double overrideValue, speed; 
	public static int wristEncoderValue, wristEncoderVelocity, wristEncoderCCL;
	
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
	
	
	public static void testLimitSwitch()
	{
		if(reachedFlat())
		{
			System.out.println("Limit switch triggered");
		}
		else
		{
			System.out.println("Limit switch not triggered");
		}
	}
	
	public static void setWristEncoder()
	{
		if(reachedFlat())
		{
			resetWristEncoder();
		}
		wristEncoderValue = wristMotor.getSelectedSensorPosition(0);
		wristEncoderVelocity = wristMotor.getSelectedSensorVelocity(0);
		wristEncoderCCL = wristMotor.getClosedLoopError(0);
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
		if(limitSwitch.get())
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
			System.out.println("reached flat");
			stopWrist();
			resetWristEncoder();
		}
		else
		{
			//moveWrist(-0.2);
			if(IntakeWheels.getIntakeBannerSensor())
			{
				moveWrist(-0.4);
			}
			else
			{
				moveWrist(-0.1);
			}
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

	public static int encoderState;
	public static int manualEncoderValue;
	public static int manualAdjustment;

	public static void moveManual(double jValue)
	{
		if(jValue > 0)
		{
			moveWrist(overrideValue * 0.65);
			manualAdjustment = 50;
			encoderState = 0;
		}
		else if(jValue < 0)
		{
			moveWrist(overrideValue * 0.2);
			manualAdjustment = 0;
			encoderState = 0;
		}
		else
		{
			switch(encoderState)
			{
				case 0:
					manualEncoderValue = wristEncoderValue + manualAdjustment;
					encoderState = 1;
					break;
				case 1:
					moveWristPosition(manualEncoderValue);
					break;
			}
		}
	}
	
	public static void runWrist()
	{
		if(wristEncoderValue > Constants.kWristSafetyLimit && overrideValue != 0)
		{
			aimedWristState = -3;
		}
		else if(wristEncoderValue > Constants.kWristSafetyLimit && overrideValue == 0)
		{
			aimedWristState = -2;
		}
		else if(manualOverride)
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
		else if(Elevator.currentWristState == 0) //this code makes wrist run when it is at flat -- need to fix
		{
			aimedWristState = -10;
		}
		switch(aimedWristState)
		{
			case -10:
				//System.out.println("case -10");
				break;
			case -3:
				moveWristPosition(Constants.kWristSafetyLimit);
				break;
			case -2:
				moveWrist(-0.15);
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
