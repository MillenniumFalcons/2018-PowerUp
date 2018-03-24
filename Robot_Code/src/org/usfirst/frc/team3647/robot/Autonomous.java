package org.usfirst.frc.team3647.robot;

import edu.wpi.first.wpilibj.Timer;
import team3647ConstantsAndFunctions.Constants;
import team3647ConstantsAndFunctions.Functions;
import team3647elevator.Elevator;
import team3647elevator.ElevatorLevel;
import team3647elevator.IntakeWheels;
import team3647pistons.Forks;
import team3647pistons.Shifter;
import team3647pistons.Intake;
import team3647subsystems.Drivetrain;
import team3647subsystems.Encoders;

public class Autonomous 
{
	//Timer-Stuff
	static Timer stopWatch = new Timer();
	static double time;
	
	//Other variables for auto
	static double prevLeftEncoder, prevRightEncoder;
	static int currentState;
	static double lSSpeed, rSSpeed, speed;
	
	public static void initialize()
	{
		Drivetrain.stop();
		Forks.lockTheForks();
		Shifter.lowGear();
		Intake.closeIntake();
		Encoders.resetEncoders();
		IntakeWheels.runIntake(0, 0, true, 0, 0);
		Elevator.stopEleVader();
		Elevator.elevatorState = 0;
		prevLeftEncoder = 0;
		prevRightEncoder = 0;
		currentState = 0;
		time = 0;
	}
	
	public static void test1(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.start();
				currentState = 1;
				break;
			case 1:
				time = stopWatch.get();
				if(lValue == 0 && rValue == 0 && time >= 2)
				{
					
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 2;
				}
				else if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 2;
				}
				else
				{
					Elevator.moveEleVader(-.23);
					Encoders.resetEncoders();
				}
				break;
			case 2:
				if(ElevatorLevel.reachedPickUp())
				{
					currentState = 3;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToPickUp(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 3:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, 5000))
				{
					Drivetrain.driveForw(lValue, rValue, .74);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 7000))
				{
					Drivetrain.driveForw(lValue, rValue, .3);
				}
				else
				{
					currentState = 4;
				}
				break;
			case 4:
				ElevatorLevel.maintainPickUpPosition();
				Drivetrain.stop();
				break;
		}
	}
	
	public static void test2(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.start();
				currentState = 1;
				break;
			case 1:
				time = stopWatch.get();
				if(lValue == 0 && rValue == 0 && time >= 2)
				{
					
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 2;
				}
				else if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 2;
				}
				else
				{
					Elevator.moveEleVader(-.23);
					Encoders.resetEncoders();
				}
				break;
			case 2:
				if(ElevatorLevel.reachedPickUp())
				{
					currentState = 3;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToPickUp(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 3:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, 5000))
				{
					Drivetrain.driveBack(lValue, rValue, .74);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 7000))
				{
					Drivetrain.driveBack(lValue, rValue, .3);
				}
				else
				{
					currentState = 4;
				}
				break;
			case 4:
				ElevatorLevel.maintainPickUpPosition();
				Drivetrain.stop();
				break;
		}
	}
	
	public static void test3(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.start();
				currentState = 1;
				break;
			case 1:
				time = stopWatch.get();
				if(lValue == 0 && rValue == 0 && time >= 2)
				{
					
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 2;
					stopWatch.stop();
				}
				else if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 2;
					stopWatch.stop();
				}
				else
				{
					Elevator.moveEleVader(-.23);
					Encoders.resetEncoders();
				}
				break;
			case 2:
				if(ElevatorLevel.reachedPickUp())
				{
					currentState = 3;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToPickUp(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 3:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, 5000))
				{
					Drivetrain.driveForw(lValue, rValue, .74);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 7000))
				{
					Drivetrain.driveForw(lValue, rValue, .34);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 4;
				}
				break;
			case 4:
				ElevatorLevel.maintainPickUpPosition();
				lValue -= prevLeftEncoder;
				rValue -= prevRightEncoder;
				double dist = 5400;
				double ratio = 3.26;
				if(rValue < dist)
				{
					rSSpeed = Functions.test3Turn(rValue, dist);
					lSSpeed = rSSpeed/ratio;
					Drivetrain.goStraightLeft(lValue, rValue, ratio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					currentState = 5;
				}
				break;
			case 5:
				ElevatorLevel.maintainPickUpPosition();
				Drivetrain.stop();
				break;
		}
	}
	
	public static void test4(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.start();
				currentState = 1;
				break;
			case 1:
				time = stopWatch.get();
				if(lValue == 0 && rValue == 0 && time >= 2)
				{
					
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 2;
					stopWatch.stop();
				}
				else if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 2;
					stopWatch.stop();
				}
				else
				{
					Elevator.moveEleVader(-.23);
					Encoders.resetEncoders();
				}
				break;
			case 2:
				if(ElevatorLevel.reachedPickUp())
				{
					currentState = 3;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToPickUp(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 3:
				ElevatorLevel.maintainPickUpPosition();
				double dist = 5400;
				double ratio = 3.26;
				if(Math.abs(rValue) < dist)
				{
					rSSpeed = Functions.test4Turn(rValue, dist);
					lSSpeed = rSSpeed/ratio;
					Drivetrain.goBackLeft(lValue, rValue, ratio, lSSpeed, rSSpeed, .6);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 4;
				}
				break;
			case 4:
				ElevatorLevel.maintainPickUpPosition();
				lValue -= prevLeftEncoder;
				rValue -= prevRightEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, 5000))
				{
					Drivetrain.driveBack(lValue, rValue, -.74);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 7000))
				{
					Drivetrain.driveBack(lValue, rValue, -.34);
				}
				else
				{
					currentState = 5;
				}
				break;
			case 5:
				ElevatorLevel.maintainPickUpPosition();
				Drivetrain.stop();
				break;
		}
	}
	
	public static void test5(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.start();
				currentState = 1;
				break;
			case 1:
				time = stopWatch.get();
				if(lValue == 0 && rValue == 0 && time >= 2)
				{
					
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 2;
					stopWatch.stop();
				}
				else if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 2;
					stopWatch.stop();
				}
				else
				{
					Elevator.moveEleVader(-.23);
					Encoders.resetEncoders();
				}
				break;
			case 2:
				if(ElevatorLevel.reachedPickUp())
				{
					currentState = 3;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToPickUp(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 3:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, 5000))
				{
					Drivetrain.driveBack(lValue, rValue, -.74);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 7000))
				{
					Drivetrain.driveBack(lValue, rValue, -.34);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 4;
				}
				break;
			case 4:
				ElevatorLevel.maintainPickUpPosition();
				lValue -= prevLeftEncoder;
				rValue -= prevRightEncoder;
				double dist = 5400;
				double ratio = 3.26;
				if(Math.abs(lValue) < dist)
				{
					lSSpeed = Functions.test5Turn(lValue, dist);
					rSSpeed = lSSpeed/ratio;
					Drivetrain.goBackRight(lValue, rValue, ratio, lSSpeed, rSSpeed, .6);
				}
				else
				{
					currentState = 5;
				}
				break;
			case 5:
				ElevatorLevel.maintainPickUpPosition();
				Drivetrain.stop();
				break;
		}
	}
	
	public static void rr(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.start();
				currentState = 1;
				break;
			case 1:
				time = stopWatch.get();
				if(lValue == 0 && rValue == 0 && time >= 2)
				{
					
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 2;
					stopWatch.stop();
				}
				else if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 2;
					stopWatch.stop();
				}
				else
				{
					Elevator.moveEleVader(-.23);
					Encoders.resetEncoders();
				}
				break;
			case 2:
				if(ElevatorLevel.reachedPickUp())
				{
					currentState = 3;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToPickUp(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 3:
				Functions.lrandrrElevatorForFirstScale(lValue, rValue, ElevatorLevel.elevatorEncoderValue);
				if(Functions.lrandrrSpeedForFirstScale(lValue, rValue, Constants.lrandrrFirstStraightDist) != 0)
				{
					Drivetrain.driveForw(lValue, rValue, Functions.lrandrrSpeedForFirstScale(lValue, rValue, Constants.lrandrrFirstStraightDist));
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 4;
				}
				break;
		}
	}
	
}
