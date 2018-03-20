package team3647elevator;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import team3647ConstantsAndFunctions.Constants;
import team3647ConstantsAndFunctions.Functions;

public class Elevator 
{
	public static int elevatorState;
	/*
	 * 0. Start
	 * 1. Stop
	 * 2. PickUp
	 * 3. Switch
	 * 4. Scale
	 * 5. Lower Scale
	 */
	public static int aimedElevatorState;
	
	public static boolean stop, pickUp, sWitch, scale, lowerScale, moving, manualOverride, originalPositionButton;
	public static double overrideValue;
	
	public static WPI_TalonSRX leftElevator = new WPI_TalonSRX(Constants.leftElevatorMaster);
	public static WPI_TalonSRX rightElevator = new WPI_TalonSRX(Constants.rightElevatorMaster);
	
	public static VictorSPX leftElevatorSPX = new VictorSPX(Constants.leftElevatorSlave);
	public static VictorSPX rightElevatorSPX = new VictorSPX(Constants.rightElevatorSlave);
	
	public static DifferentialDrive elevatorDrive = new DifferentialDrive(Constants.leftElevatorMaster, Constants.rightElevatorMaster);
	
	public static void moveEleVader(double speed)
	{
		elevatorDrive.tankDrive(-speed, speed, false);
//		leftElevatorSPX.set(ControlMode.PercentOutput, -speed);
//		rightElevatorSPX.set(ControlMode.PercentOutput, -speed);
		leftElevatorSPX.follow(leftElevator);
		rightElevatorSPX.follow(rightElevator);
	}
	
	public static void stopEleVader()
	{
		moveEleVader(0);
	}
	
	public static void setElevatorButtons(boolean stopButton, boolean pickUpButton, boolean switchButton, boolean scaleButton, boolean LSButton)
	{
		stop = stopButton;
		pickUp = pickUpButton;
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

	public static void runDarthVader()
	{
		switch(elevatorState)
		{
			case 0://start
				if(manualOverride)
				{
					elevatorState = -1;
				}
				else if(ElevatorLevel.reachedStop())
				{
					stopEleVader();
					aimedElevatorState = 1;
					elevatorState = 1;
				}
				else
				{
					moveEleVader(-.2);
				}
				break;
			case 1://stop
				if(manualOverride)
				{
					aimedElevatorState = -1;
				}
				else if(stop)
				{
					aimedElevatorState = 1;
				}
				else if(pickUp)
				{
					aimedElevatorState = 2;
				}
				else if(sWitch)
				{
					aimedElevatorState = 3;
				}
				else if(scale)
				{
					aimedElevatorState = 4;
				}
				else if(lowerScale)
				{
					aimedElevatorState = 5;
				}
				switch(aimedElevatorState)
				{
					case 1:
						if(ElevatorLevel.reachedStop())
						{
							stopEleVader();
						}
						else
						{
							moveEleVader(-.2);
						}
						break;
					case 2:
						if(ElevatorLevel.reachedPickUp())
						{
							ElevatorLevel.maintainPickUpPosition();
							elevatorState = 2;
						}
						else
						{
							moveEleVader(Functions.stopToPickUp(ElevatorLevel.elevatorEncoderValue));
						}
						break;
					case 3:
						if(ElevatorLevel.reachedSwitch())
						{
							ElevatorLevel.maintainSwitchPosition();
							elevatorState = 3;
						}
						else
						{
							moveEleVader(Functions.stopToSwitch(ElevatorLevel.elevatorEncoderValue));
						}
						break;
					case 4:
						if(ElevatorLevel.reachedScale())
						{
							ElevatorLevel.maintainScalePosition();
							elevatorState = 4;
						}
						else
						{
							moveEleVader(Functions.stopToScale(ElevatorLevel.elevatorEncoderValue));
						}
						break;
					case 5:
						if(ElevatorLevel.reachedLowerScale())
						{
							ElevatorLevel.maintainLowerScalePosition();
							elevatorState = 5;
						}
						else
						{
							moveEleVader(Functions.stopToLowerScale(ElevatorLevel.elevatorEncoderValue));
						}
						break;
					case -1:
						elevatorState = -1;
						break;
				}
				break;
			case 2://pickup
				if(manualOverride)
				{
					aimedElevatorState = -1;
				}
				else if(stop)
				{
					aimedElevatorState = 1;
				}
				else if(pickUp)
				{
					aimedElevatorState = 2;
				}
				else if(sWitch)
				{
					aimedElevatorState = 3;
				}
				else if(scale)
				{
					aimedElevatorState = 4;
				}
				else if(lowerScale)
				{
					aimedElevatorState = 5;
				}
				switch(aimedElevatorState)
				{
					case 1:
						if(ElevatorLevel.reachedStop())
						{
							stopEleVader();
							elevatorState = 1;
						}
						else
						{
							moveEleVader(Functions.pickUpToStop(ElevatorLevel.elevatorEncoderValue));//
						}
						break;
					case 2:
						ElevatorLevel.maintainPickUpPosition();
						break;
					case 3:
						if(ElevatorLevel.reachedSwitch())
						{
							ElevatorLevel.maintainSwitchPosition();
							elevatorState = 3;
						}
						else
						{
							moveEleVader(Functions.pickUpToSwitch(ElevatorLevel.elevatorEncoderValue));//
						}
						break;
					case 4:
						if(ElevatorLevel.reachedScale())
						{
							ElevatorLevel.maintainScalePosition();
							elevatorState = 4;
						}
						else
						{
							moveEleVader(Functions.pickUpToScale(ElevatorLevel.elevatorEncoderValue));//
						}
						break;
					case 5:
						if(ElevatorLevel.reachedLowerScale())
						{
							ElevatorLevel.maintainLowerScalePosition();
							elevatorState = 5;
						}
						else
						{
							moveEleVader(Functions.pickUpToLowerScale(ElevatorLevel.elevatorEncoderValue));
						}
						break;
					case -1:
						elevatorState = -1;
						break;
				}
				break;
			case 3:
				if(manualOverride)
				{
					aimedElevatorState = -1;
				}
				else if(stop)
				{
					aimedElevatorState = 1;
					originalPositionButton = false;
				}
				else if(pickUp)
				{
					aimedElevatorState = 2;
					originalPositionButton = false;
				}
				else if(sWitch)
				{
					aimedElevatorState = 3;
					originalPositionButton = true;
				}
				else if(scale)
				{
					aimedElevatorState = 4;
					originalPositionButton = false;
				}
				else if(lowerScale)
				{
					aimedElevatorState = 5;
				}
				switch(aimedElevatorState)
				{
					case 1:
						if(ElevatorLevel.reachedStop())
						{
							stopEleVader();
							elevatorState = 1;
						}
						else
						{
							moveEleVader(Functions.switchToStop(ElevatorLevel.elevatorEncoderValue));
						}
						break;
					case 2:
						if(ElevatorLevel.reachedPickUp())
						{
							ElevatorLevel.maintainPickUpPosition();
							elevatorState = 2;
						}
						else
						{
							moveEleVader(Functions.switchToPickUp(ElevatorLevel.elevatorEncoderValue));
						}
						
						break;
					case 3:
						ElevatorLevel.maintainSwitchPosition();
						break;
					case 4:
						if(ElevatorLevel.reachedScale())
						{
							ElevatorLevel.maintainScalePosition();
							elevatorState = 4;
						}
						else
						{
							moveEleVader(Functions.switchToScale(ElevatorLevel.elevatorEncoderValue));//
						}
						break;
					case 5:
						if(ElevatorLevel.reachedLowerScale())
						{
							ElevatorLevel.maintainLowerScalePosition();
							elevatorState = 5;
						}
						else
						{
							moveEleVader(Functions.switchToLowerScale(ElevatorLevel.elevatorEncoderValue));
						}
						break;
					case -1:
						elevatorState = -1;
						break;
				}
				break;
			case 4:
				if(manualOverride)
				{
					aimedElevatorState = -1;
				}
				else if(stop)
				{
					aimedElevatorState = 1;
					originalPositionButton = false;
				}
				else if(pickUp)
				{
					aimedElevatorState = 2;
					originalPositionButton = false;
				}
				else if(sWitch)
				{
					aimedElevatorState = 3;
					originalPositionButton = false;
				}
				else if(scale)
				{
					aimedElevatorState = 4;
					originalPositionButton = true;
				}
				else if(lowerScale)
				{
					aimedElevatorState = 5;
				}
				switch(aimedElevatorState)
				{
					case 1:
						if(ElevatorLevel.reachedStop())
						{
							stopEleVader();
							elevatorState = 1;
						}
						else
						{
							moveEleVader(Functions.scaleToStop(ElevatorLevel.elevatorEncoderValue));//
						}
						break;
					case 2:
						if(ElevatorLevel.reachedPickUp())
						{
							ElevatorLevel.maintainPickUpPosition();
							elevatorState = 2;
						}
						else
						{
							moveEleVader(Functions.scaleToSwitch(ElevatorLevel.elevatorEncoderValue));//
						}
						
						break;
					case 3:
						if(ElevatorLevel.reachedSwitch())
						{
							ElevatorLevel.maintainSwitchPosition();
							elevatorState = 3;
						}
						else
						{
							moveEleVader(Functions.scaleToSwitch(ElevatorLevel.elevatorEncoderValue));//
						}
						break;
					case 4:
						ElevatorLevel.maintainScalePosition();
						break;
					case 5:
						if(ElevatorLevel.reachedLowerScale())
						{
							ElevatorLevel.maintainLowerScalePosition();
							elevatorState = 5;
						}
						else
						{
							moveEleVader(Functions.scaleToLowerScale(ElevatorLevel.elevatorEncoderValue));
						}
						break;
					case -1:
						elevatorState = -1;
						break;
				}
				break;
			case 5:
				if(manualOverride)
				{
					aimedElevatorState = -1;
				}
				else if(stop)
				{
					aimedElevatorState = 1;
					originalPositionButton = false;
				}
				else if(pickUp)
				{
					aimedElevatorState = 2;
					originalPositionButton = false;
				}
				else if(sWitch)
				{
					aimedElevatorState = 3;
					originalPositionButton = false;
				}
				else if(scale)
				{
					aimedElevatorState = 4;
					originalPositionButton = true;
				}
				else if(lowerScale)
				{
					aimedElevatorState = 5;
				}
				switch(aimedElevatorState)
				{
					case 1:
						if(ElevatorLevel.reachedStop())
						{
							stopEleVader();
							elevatorState = 1;
						}
						else
						{
							moveEleVader(Functions.lowerScaleToStop(ElevatorLevel.elevatorEncoderValue));//
						}
						break;
					case 2:
						if(ElevatorLevel.reachedPickUp())
						{
							ElevatorLevel.maintainPickUpPosition();
							elevatorState = 2;
						}
						else
						{
							moveEleVader(Functions.lowerScaleToSwitch(ElevatorLevel.elevatorEncoderValue));//
						}
						
						break;
					case 3:
						if(ElevatorLevel.reachedSwitch())
						{
							ElevatorLevel.maintainSwitchPosition();
							elevatorState = 3;
						}
						else
						{
							moveEleVader(Functions.lowerScaleToSwitch(ElevatorLevel.elevatorEncoderValue));//
						}
						break;
					case 4:
						if(ElevatorLevel.reachedScale())
						{
							ElevatorLevel.maintainScalePosition();
							elevatorState = 3;
						}
						else
						{
							moveEleVader(Functions.lowerScaleToScale(ElevatorLevel.elevatorEncoderValue));//
						}
						break;
					case 5:
						if(ElevatorLevel.reachedLowerScale())
						{
							ElevatorLevel.maintainLowerScalePosition();
						}
						else
						{
							if(ElevatorLevel.elevatorEncoderValue > Constants.lowerScale)
							{
								moveEleVader(Functions.scaleToLowerScale(ElevatorLevel.elevatorEncoderValue));//
							}
							else
							{
								moveEleVader(Functions.switchToLowerScale(ElevatorLevel.elevatorEncoderValue));//
							}
						}
						break;
					case -1:
						elevatorState = -1;
						break;
				}
				break;
			case -1:
				if(stop || pickUp || sWitch || scale)
				{
					if(stop)
					{
						if(ElevatorLevel.reachedStop())
						{
							ElevatorLevel.resetElevatorEncoders();
							elevatorState = 1;
							aimedElevatorState = 1;
						}
						else if(ElevatorLevel.elevatorEncoderValue >= 0 && ElevatorLevel.elevatorEncoderValue < Constants.pickUp)
						{
							elevatorState = 1;
							aimedElevatorState = 1;
						}
						else if(ElevatorLevel.elevatorEncoderValue >= Constants.pickUp && ElevatorLevel.elevatorEncoderValue < Constants.sWitch)
						{
							elevatorState = 2;
							aimedElevatorState = 1;
						}
						else if(ElevatorLevel.elevatorEncoderValue >= Constants.sWitch && ElevatorLevel.elevatorEncoderValue < Constants.scale)
						{
							elevatorState = 3;
							aimedElevatorState = 1;
						}
						else
						{
							elevatorState = 4;
							aimedElevatorState = 1;
						}	
					}
					else if(pickUp)
					{
						if(ElevatorLevel.reachedStop())
						{
							ElevatorLevel.resetElevatorEncoders();
							elevatorState = 1;
							aimedElevatorState = 2;
						}
						else if(ElevatorLevel.elevatorEncoderValue >= 0 && ElevatorLevel.elevatorEncoderValue < Constants.pickUp)
						{
							elevatorState = 1;
							aimedElevatorState = 2;
						}
						else if(ElevatorLevel.elevatorEncoderValue >= Constants.pickUp && ElevatorLevel.elevatorEncoderValue < Constants.sWitch)
						{
							elevatorState = 2;
							aimedElevatorState = 2;
						}
						else if(ElevatorLevel.elevatorEncoderValue >= Constants.sWitch && ElevatorLevel.elevatorEncoderValue < Constants.scale)
						{
							elevatorState = 3;
							aimedElevatorState = 2;
						}
						else
						{
							elevatorState = 4;
							aimedElevatorState = 2;
						}
					}
					else if(sWitch)
					{
						if(ElevatorLevel.reachedStop())
						{
							ElevatorLevel.resetElevatorEncoders();
							elevatorState = 1;
							aimedElevatorState = 3;
						}
						else if(ElevatorLevel.elevatorEncoderValue >= 0 && ElevatorLevel.elevatorEncoderValue < Constants.pickUp)
						{
							elevatorState = 1;
							aimedElevatorState = 3;
						}
						else if(ElevatorLevel.elevatorEncoderValue >= Constants.pickUp && ElevatorLevel.elevatorEncoderValue < Constants.sWitch)
						{
							elevatorState = 2;
							aimedElevatorState = 3;
						}
						else if(ElevatorLevel.elevatorEncoderValue >= Constants.sWitch && ElevatorLevel.elevatorEncoderValue < Constants.scale)
						{
							elevatorState = 3;
							aimedElevatorState = 3;
						}
						else
						{
							elevatorState = 4;
							aimedElevatorState = 3;
						}
					}
					else if(scale)
					{
						if(ElevatorLevel.reachedStop())
						{
							ElevatorLevel.resetElevatorEncoders();
							elevatorState = 1;
							aimedElevatorState = 4;
						}
						else if(ElevatorLevel.elevatorEncoderValue >= 0 && ElevatorLevel.elevatorEncoderValue < Constants.pickUp)
						{
							elevatorState = 1;
							aimedElevatorState = 4;
						}
						else if(ElevatorLevel.elevatorEncoderValue >= Constants.pickUp && ElevatorLevel.elevatorEncoderValue < Constants.sWitch)
						{
							elevatorState = 2;
							aimedElevatorState = 4;
						}
						else if(ElevatorLevel.elevatorEncoderValue >= Constants.sWitch && ElevatorLevel.elevatorEncoderValue < Constants.scale)
						{
							elevatorState = 3;
							aimedElevatorState = 4;
						}
						else
						{
							elevatorState = 4;
							aimedElevatorState = 4;
						}
					}
					else
					{
						elevatorState = -1;
						aimedElevatorState = -1;
					}
				}
				else
				{
					if(!manualOverride)
					{
						overrideValue = 0;
					}
					moveEleVader(overrideValue);
				}
				break;
		}
	}
	
}