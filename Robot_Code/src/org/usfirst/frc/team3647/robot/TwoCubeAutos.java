package org.usfirst.frc.team3647.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import team3647ConstantsAndFunctions.Functions;
import team3647elevator.Elevator;
import team3647elevator.ElevatorLevel;
import team3647elevator.Intake;
import team3647pistons.intakeMechanism;
import team3647subsystems.Drivetrain;
import team3647subsystems.Encoders;

public class TwoCubeAutos 
{
	static int currentState;
	static double lSSpeed, rSSpeed, adjustmentConstant, speed;
	static double []adjustmentValues = new double[2];
	static double prevLeftEncoder, prevRightEncoder, avg;
	static double requiredLeftDist, requiredRightDist;
	
	public static void rr(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				prevLeftEncoder = 0;
				prevRightEncoder = 0;
				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 1;
				}
				else
				{
					Encoders.resetEncoders();
					Elevator.moveEleVader(-.25);
				}
				break;
			case 1:
				if(ElevatorLevel.reachedPickUp())
				{
					Elevator.stopEleVader();
					currentState = 11;
				}
				else
				{
					Elevator.moveEleVader(.5);
				}
				break;
			case 2:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, 20000))
				{
					avg = (lValue + rValue)/2.0;
					if(avg < 2000)
					{
						speed = .6;
					}
					else if(avg < 3500)
					{
						speed = .7;
					}
					else if(avg < 5000)
					{
						speed = .85;
					}
					else if(avg < 15000)
					{
						speed = 1;
					}
					else if(avg < 17000)
					{
						speed = .85;
					}
					else
					{
						speed = .6;
					}
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 23520))
				{
					speed = .35;
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else
				{
					currentState = 3;
				}
				break;
			case 3:
				ElevatorLevel.maintainPickUpPosition();
				Drivetrain.stop();
				Timer.delay(.3);
				currentState = 4;
				break;
			case 4:
				ElevatorLevel.maintainPickUpPosition();
				if(lValue == 0 && rValue == 0)
				{
					currentState = 5;
				}
				else
				{
					Encoders.resetEncoders();
				}
				break;
			case 5:
				avg = (Math.abs(lValue) + Math.abs(rValue))/2.0;
				if(avg < 3200)
				{
					Drivetrain.turnLeft(lValue, rValue);
				}
				else
				{
					currentState = 6;
				}
				break;
			case 6:
				ElevatorLevel.maintainPickUpPosition();
				Drivetrain.stop();
				Timer.delay(.3);
				currentState = 7;
				break;
			case 7:
				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedScale())
				{
					ElevatorLevel.maintainScalePosition();
					currentState = 8;
				}
				else
				{
					Drivetrain.stop();
					Encoders.resetEncoders();
				}
				break;
			case 8:
				ElevatorLevel.maintainScalePosition();
				if(avg < 2000)
				{
					Drivetrain.driveForw(lValue, rValue, .5);
				}
				else
				{
					currentState = 9;
				}
				break;
			case 9:
				ElevatorLevel.maintainScalePosition();
				Drivetrain.stop();
				Timer.delay(.3);
				currentState = 10;
				break;
			case 10:
				ElevatorLevel.maintainScalePosition();
				if(lValue == 0 && rValue == 0)
				{
					currentState = 11;
				}
				else
				{
					Encoders.resetEncoders();
				}
				break;
			case 11:
				avg = (Math.abs(lValue) + Math.abs(rValue))/2.0;
				if(ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
				}
				else
				{
					Elevator.moveEleVader(Functions.scaleToStop(ElevatorLevel.elevatorEncoderValue));
				}
				if(avg < 3200)
				{
					Drivetrain.turnLeft(lValue, rValue);
				}
				else
				{
					currentState = 12;
				}
				break;
			case 12:
				Drivetrain.stop();
				Timer.delay(.3);
				currentState = 13;
				break;
			case 13:
				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					currentState = 14;
				}
				else
				{
					Drivetrain.stop();
					Encoders.resetEncoders();
					Elevator.moveEleVader(Functions.scaleToStop(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 14:
				if(!Drivetrain.reachedDistance(lValue, rValue, 3000))
				{
					Drivetrain.driveForw(lValue, rValue, .74);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 3500))
				{
					Drivetrain.driveForw(lValue, rValue, .3);
				}
				else
				{
					currentState = 15;
				}
				break;
			case 15:
				Drivetrain.stop();
				Timer.delay(.3);
				currentState = 16;
				break;
			case 16:
				if(lValue == 0 && rValue == 0)
				{
					currentState = 17;
				}
				else
				{
					Encoders.resetEncoders();
				}
				break;
			case 17:
				avg = (Math.abs(lValue) + Math.abs(rValue))/2.0;
				if(ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
				}
				else
				{
					Elevator.moveEleVader(Functions.scaleToStop(ElevatorLevel.elevatorEncoderValue));
				}
				if(avg < 1600)
				{
					Drivetrain.turnRight(lValue, rValue);
				}
				else
				{
					currentState = 18;
				}
				break;
			case 18:
				Drivetrain.stop();
				Timer.delay(.3);
				currentState = 19;
				break;
			case 19:
				if(lValue == 0 && rValue == 0)
				{
					currentState = 20;
				}
				else
				{
					Encoders.resetEncoders();
				}
				break;
			case 20:
				Intake.pickUpCube();
				intakeMechanism.openIntake();
				if(!Drivetrain.reachedDistance(lValue, rValue, 4000))
				{
					Drivetrain.driveForw(lValue, rValue, .65);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 4700))
				{
					Drivetrain.driveForw(lValue, rValue, .3);
				}
				else
				{
					currentState = 21;
				}
				break;
			case 21:
				intakeMechanism.closeIntake();
				Drivetrain.stop();
				Timer.delay(.3);
				Encoders.resetEncoders();
				Timer.delay(.2);
				currentState =22;
				break;
		}
	}
	
	public static void lr(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				prevLeftEncoder = 0;
				prevRightEncoder = 0;
				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 1;
				}
				else
				{
					Encoders.resetEncoders();
					Elevator.moveEleVader(-.25);
				}
				break;
			case 1:
				if(ElevatorLevel.reachedPickUp())
				{
					Elevator.stopEleVader();
					currentState = 11;
				}
				else
				{
					Elevator.moveEleVader(.5);
				}
				break;
			case 2:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, 20000))
				{
					avg = (lValue + rValue)/2.0;
					if(avg < 2000)
					{
						speed = .6;
					}
					else if(avg < 3500)
					{
						speed = .7;
					}
					else if(avg < 5000)
					{
						speed = .85;
					}
					else if(avg < 15000)
					{
						speed = 1;
					}
					else if(avg < 17000)
					{
						speed = .85;
					}
					else
					{
						speed = .6;
					}
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 23520))
				{
					speed = .35;Drivetrain.driveForw(lValue, rValue, speed);
				}
				else
				{
					currentState = 3;
				}
				break;
			case 3:
				ElevatorLevel.maintainPickUpPosition();
				Drivetrain.stop();
				Timer.delay(.3);
				currentState = 4;
				break;
			case 4:
				ElevatorLevel.maintainPickUpPosition();
				if(lValue == 0 && rValue == 0)
				{
					currentState = 5;
				}
				else
				{
					Encoders.resetEncoders();
				}
				break;
			case 5:
				avg = (Math.abs(lValue) + Math.abs(rValue))/2.0;
				if(avg < 3200)
				{
					Drivetrain.turnLeft(lValue, rValue);
				}
				else
				{
					currentState = 6;
				}
				break;
			case 6:
				ElevatorLevel.maintainPickUpPosition();
				Drivetrain.stop();
				Timer.delay(.3);
				currentState = 7;
				break;
			case 7:
				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedScale())
				{
					ElevatorLevel.maintainScalePosition();
					currentState = 8;
				}
				else
				{
					Drivetrain.stop();
					Encoders.resetEncoders();
				}
				break;
			case 8:
				ElevatorLevel.maintainScalePosition();
				if(avg < 2000)
				{
					Drivetrain.driveForw(lValue, rValue, .5);
				}
				else
				{
					currentState = 9;
				}
				break;
			case 9:
				ElevatorLevel.maintainScalePosition();
				Drivetrain.stop();
				Timer.delay(.3);
				currentState = 10;
				break;
			case 10:
				ElevatorLevel.maintainScalePosition();
				if(lValue == 0 && rValue == 0)
				{
					currentState = 11;
				}
				else
				{
					Encoders.resetEncoders();
				}
				break;
			case 11:
				avg = (Math.abs(lValue) + Math.abs(rValue))/2.0;
				if(ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
				}
				else
				{
					Elevator.moveEleVader(Functions.scaleToStop(ElevatorLevel.elevatorEncoderValue));
				}
				if(avg < 3200)
				{
					Drivetrain.turnLeft(lValue, rValue);
				}
				else
				{
					currentState = 12;
				}
				break;
			case 12:
				Drivetrain.stop();
				Timer.delay(.3);
				currentState = 13;
				break;
			case 13:
				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					currentState = 14;
				}
				else
				{
					Drivetrain.stop();
					Encoders.resetEncoders();
					Elevator.moveEleVader(Functions.scaleToStop(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 14:
				if(!Drivetrain.reachedDistance(lValue, rValue, 3000))
				{
					Drivetrain.driveForw(lValue, rValue, .74);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 3500))
				{
					Drivetrain.driveForw(lValue, rValue, .3);
				}
				else
				{
					currentState = 15;
				}
				break;
			case 15:
				Drivetrain.stop();
				Timer.delay(.3);
				currentState = 16;
				break;
			case 16:
				if(lValue == 0 && rValue == 0)
				{
					currentState = 17;
				}
				else
				{
					Encoders.resetEncoders();
				}
				break;
			case 17:
				avg = (Math.abs(lValue) + Math.abs(rValue))/2.0;
				if(ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
				}
				else
				{
					Elevator.moveEleVader(Functions.scaleToStop(ElevatorLevel.elevatorEncoderValue));
				}
				if(avg < 3200)
				{
					Drivetrain.turnRight(lValue, rValue);
				}
				else
				{
					currentState = 18;
				}
				break;
			case 18:
				Drivetrain.stop();
				Timer.delay(.3);
				currentState = 19;
				break;
			case 19:
				if(lValue == 0 && rValue == 0)
				{
					currentState = 20;
				}
				else
				{
					Encoders.resetEncoders();
				}
				break;
			case 20:
				if(!Drivetrain.reachedDistance(lValue, rValue, 20000))
				{
					avg = (lValue + rValue)/2.0;
					if(avg < 2000)
					{
						speed = .6;
					}
					else if(avg < 3500)
					{
						speed = .7;
					}
					else if(avg < 5000)
					{
						speed = .85;
					}
					else if(avg < 15000)
					{
						speed = 1;
					}
					else if(avg < 17000)
					{
						speed = .85;
					}
					else
					{
						speed = .6;
					}
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 23520))
				{
					speed = .35;
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else
				{
					currentState = 21;
				}
				break;
			case 21:
				Drivetrain.stop();
				Timer.delay(.3);
				currentState = 22;
				break;
			case 22:
				ElevatorLevel.maintainPickUpPosition();
				if(lValue == 0 && rValue == 0)
				{
					currentState = 23;
				}
				else
				{
					Encoders.resetEncoders();
				}
				break;
			case 23:
				avg = (Math.abs(lValue) + Math.abs(rValue))/2.0;
				if(avg < 3200)
				{
					Drivetrain.turnLeft(lValue, rValue);
				}
				else
				{
					currentState = 24;
				}
				break;
			case 24:
				Drivetrain.stop();
				Timer.delay(.3);
				currentState = 25;
				break;
			case 25:
				if(lValue == 0 && rValue == 0)
				{
					currentState = 26;
				}
				else
				{
					Drivetrain.stop();
					Encoders.resetEncoders();
				}
				break;
			case 26:
				Intake.pickUpCube();
				intakeMechanism.openIntake();
				if(avg < 2000)
				{
					Drivetrain.driveForw(lValue, rValue, .5);
				}
				else
				{
					currentState = 27;
				}
				break;
			case 27:
				Drivetrain.stop();
				intakeMechanism.closeIntake();
				break;
		}
	}
	
	public static void ll(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				prevLeftEncoder = 0;
				prevRightEncoder = 0;
				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 1;
				}
				else
				{
					Encoders.resetEncoders();
					Elevator.moveEleVader(-.25);
				}
				break;
		}
	}
	
	
	public static void testScaleRightSide(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				prevLeftEncoder = 0;
				prevRightEncoder = 0;
				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 1;
				}
				else
				{
					Encoders.resetEncoders();
					Elevator.moveEleVader(-.25);
				}
				break;
			case 1:
				avg = (Math.abs(lValue) + Math.abs(rValue))/2.0;
				if(avg < 3200)
				{
					Drivetrain.turnLeft(lValue, rValue);
				}
				else
				{
					currentState = 2;
				}
				break;
			case 2:
				Drivetrain.stop();
				Timer.delay(.3);
				currentState = 3;
				break;
			case 3:
				Drivetrain.stop();
				break;
		}
	}
	

	
	public static void initialize()
	{
		Encoders.resetEncoders();
		Intake.stopIntake();
		Elevator.stopEleVader();
		intakeMechanism.closeIntake();
		Elevator.elevatorState = 0;
		prevLeftEncoder = 0;
		prevRightEncoder = 0;
		currentState = 0;
	}
}
