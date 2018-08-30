package team3647elevator;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Wrist {
	
	public static int wristState;
	public static int aimedWristState;

	public static WPI_TalonSRX = new wristMotor;
	
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
	
	public static DigitalInput bannerSensor = new DigitalInput(Constants.elevatorBannerSensor); 
	
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
	
	public static boolean reachedFlat()
	{
		if(limitSwitch.get())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public static boolean reachedAim()
	{
		if(wristEncoder > aim - 50 && wristEncoder < aim + 150)
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
		if(wristEncoder > aim - 3000 && wristEncoder < up + 3000)
		{
			return true;
		}
		else
		{
			return false; 
		}
	}
	
	public static void maintainAimPosition()
	{
		if(reachedAim())
		{
			stopWrist();
		}
		else
		{
			if(wristEncoderValue > aim)
			{
				moveWrist(-.1);
			}
			else
			{
				moveWrist(.1);
			}
		}
	}
	
	public static void maintainUpPosition()
	{
		if(reachedUp())
		{
			stopWrist();
		}
		else
		{
			if(wristEncoderValue > up)
			{
				moveWrist(-.1);
			}
			else
			{
				moveWrist(.1);
			}
		}
	}
	
	// start, flat, aim, up, manualOverride, originalPositionButton;
	
	public static void runWrist()
	{
		switch(wristState)
		{
		case 0://start
			if(manualOverride)
			{
				wristState = -1;
			}
			else if(flat)
			{
				aimedWristState = 1;
				wristState = 1;
			}
			else
			{
				moveWrist(-.2);
			}
			break;
		case 1:
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
			else if(up)
			{
				aimedWristState = 3;
			}
			switch(aimedWristState)
			{
				case 1:
					if(reachedFlat())
					{
						stopWrist();
						wristState = 1;
					}
					else
					{
						moveWrist(-.2);
					}
					break;
				case 2:
					if(reachedAim())
					{
						maintainAimPosition();
						wristState = 2;
					}
					else
					{
						if(wristEncoderValue > aim)
						{
							moveWrist(-.2);
						}
						else
						{
							moveWrist(.1);
						}
					}
					break;
				case 3:
					if(reachedUp())
					{
						maintainUpPosition();
						wristState = 3;
					}
					else {
						if(wristEncoderValue > up)
						{
							moveWrist(-.2);
						}
						else
						{
							moveWrist(.1);
						}
					}
					break;
				case -1:
					wristState = -1;
					break;
				}
				break;
		case 2:
			/* if(manualOverride) //this is just copy and paste from case 1
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
			else if(up)
			{
				aimedWristState = 3;
			}
			switch(aimedWristState)
			{
				case 1:
					if(reachedFlat())
					{
						stopWrist();
						wristState = 1;
					}
					else
					{
						moveWrist(-.2);
					}
					break;
				case 2:
					if(reachedAim())
					{
						maintainAimPosition();
						wristState = 2;
					}
					else
					{
						if(wristEncoderValue > aim)
						{
							moveWrist(-.2);
						}
						else
						{
							moveWrist(.1);
						}
					}
					break;
				case 3:
					if(reachedUp())
					{
						maintainUpPosition();
						wristState = 3;
					}
					else {
						if(wristEncoderValue > up)
						{
							moveWrist(-.2);
						}
						else
						{
							moveWrist(.1);
						}
					}
					break;
				case -1:
					wristState = -1;
					break;
				}
				break; */
		}
	}
	
}

