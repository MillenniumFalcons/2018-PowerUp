package org.usfirst.frc.team3647.robot;

import edu.wpi.first.wpilibj.DriverStation;
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
	public static Timer stopWatch = new Timer();
	static double time;
	
	//Other variables for auto
	static double prevLeftEncoder, prevRightEncoder;
	static int currentState;
	static double lSSpeed, rSSpeed, speed, sum;
	static int b;
	
	public static void runAuto(double lValue, double rValue)
	{
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
//		if(gameData.charAt(0) == 'L' && gameData.charAt(1) == 'L')
//		{
//			crossBaseline(lValue, rValue);
//		}
//		else if(gameData.charAt(0) == 'R' && gameData.charAt(1) == 'L')
//		{
//			rightSwicth(lValue, rValue);
//		}
//		else if(gameData.charAt(0) == 'R' && gameData.charAt(1) == 'R')
//		{
//			rightScale(lValue, rValue);
//		}
//		else if(gameData.charAt(0) == 'L' && gameData.charAt(1) == 'R')
//		{
//			rightScale(lValue, rValue);
//		}
		if(gameData.charAt(1) == 'R' && gameData.charAt(0) == 'R')
		{
			rrScaleFirstSwitchSecond(lValue, rValue);
		}
		else if(gameData.charAt(1) == 'R' && gameData.charAt(0) == 'L')
		{
			lrScaleFirstSwitchSecond(lValue, rValue);
		}
		else if(gameData.charAt(0) == 'R')
		{
			rightSwitch1Cube(lValue, rValue);
		}
		else if(gameData.charAt(0) == 'L')
		{
			leftSwitch2Cube(lValue, rValue);
		}
//		if(gameData.charAt(1) == 'R')
//		{
//			rightScale(lValue, rValue);
//		}
//		else if(gameData.charAt(0) == 'R')
//		{
//			rightSwicth(lValue, rValue);
//		}
//		if(gameData.charAt(0) == 'L')
//		{
//			leftSwitch1Cube(lValue, rValue);
//		}
		else
		{
			cross(lValue, rValue);
		}
	}
	
	public static void cross(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.stop();
				stopWatch.reset();
				stopWatch.start();
				IntakeWheels.pickUp(.2);
				currentState = 1;
				break;
			case 1:
				time = stopWatch.get();
				if(lValue == 0 && rValue == 0 && time >= 2)
				{
					stopWatch.stop();
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					stopWatch.reset();
					currentState = 2;
					
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
				if(!Drivetrain.reachedDistance(lValue, rValue, 10000))
				{
					Drivetrain.driveForw(lValue, rValue, .74);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 11000))
				{
					Drivetrain.driveForw(lValue, rValue, .3);
				}
				else
				{
					Drivetrain.stop();
				}
				break;
		}
	}
	
	public static void testDatScaleCurve(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.stop();
				stopWatch.reset();
				stopWatch.start();
				IntakeWheels.pickUp(.2);
				currentState = 1;
				break;
			case 1:
				time = stopWatch.get();
				if(lValue == 0 && rValue == 0 && time >= 2)
				{
					stopWatch.stop();
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					stopWatch.reset();
					currentState = 8;
					
				}
				else if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 8;
					stopWatch.stop();
				}
				else
				{
					Elevator.moveEleVader(-.23);
					Encoders.resetEncoders();
				}
				break;
			case 2:
				if(!Drivetrain.reachedDistance(lValue, rValue, 7000))
				{
					Drivetrain.driveForw(lValue, rValue, .74);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 8200))
				{
					Drivetrain.driveForw(lValue, rValue, .3);
				}
				else
				{
					stopWatch.stop();
					stopWatch.reset();
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 4;
				}
				break;
			case 4:
				rValue -= prevRightEncoder;
				lSSpeed = Drivetrain.keepMotorInPlace(prevLeftEncoder, lValue);
				rSSpeed = .5;
				double dist = 1700;
				if(rValue < dist)
				{
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					stopWatch.start();
					currentState = 6;
				}
				break;
			case 6:
				if(stopWatch.get() < .6)
				{
					
				}
				else
				{
					stopWatch.stop();
					stopWatch.reset();
					prevRightEncoder = rValue;
					currentState = 7;
				}
				break;
			case 7:
				rValue -= prevRightEncoder;
				lSSpeed = Drivetrain.keepMotorInPlace(prevLeftEncoder, lValue);
				rSSpeed = -.7;
				dist = 2700;
				rValue = Math.abs(rValue);
				if(rValue < dist)
				{
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 99;
				}
				break;
			case 8:
//				rValue -= prevRightEncoder;
//				lValue -= prevLeftEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, 2400))
				{
					Drivetrain.driveBack(lValue, rValue, -.4);
				}
				else
				{
					currentState = 9;
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
				}
				break;
			case 9:
				lValue -= prevLeftEncoder;
				rSSpeed = Drivetrain.keepMotorInPlace(prevRightEncoder, rValue);
				lSSpeed = -.5;
				dist = 4780;
				lValue = Math.abs(lValue);
				if(lValue < dist)
				{
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					stopWatch.start();
					currentState = 99;
				}
				break;
			case 99:
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				System.out.println("lValue: " + lValue);
				System.out.println("rValue: " + rValue);
				if(!Drivetrain.reachedDistance(lValue, rValue, 3000))
				{
					Drivetrain.driveBack(lValue, rValue, -.3);
				}
				else
				{
					currentState = 100;
				}
				break;
			case 100:
				Drivetrain.stop();
				break;
		}
	}
	
	public static void testCurve(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.stop();
				stopWatch.reset();
				stopWatch.start();
				IntakeWheels.pickUp(.2);
				currentState = 1;
				break;
			case 1:
				time = stopWatch.get();
				if(lValue == 0 && rValue == 0 && time >= 2)
				{
					stopWatch.stop();
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					stopWatch.reset();
					currentState = 2;
					
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
				if(!Drivetrain.reachedDistance(lValue, rValue, 5000))
				{
					Drivetrain.driveForw(lValue, rValue, .7);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 8200))
				{
					Drivetrain.driveForw(lValue, rValue, .4);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 3;
				}
				break;
			case 3:
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				if(Functions.twoCubeSwitchLeftSideFirstCurve(rValue, 5300) != 0)
				{
					rSSpeed = Functions.twoCubeSwitchLeftSideFirstCurve(rValue, 5300);
					lSSpeed = rSSpeed/2.5;
					Drivetrain.goStraightLeft(lValue, rValue, 2.5, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 4;
				}
				break;
			case 4:
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, 3000))
				{
					Drivetrain.driveForw(lValue, rValue, .5);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 5000))
				{
					Drivetrain.driveForw(lValue, rValue, .6);
				}
				else
				{
					currentState = 5;
				}
				break;
			case 5:
				Drivetrain.stop();
				break;
		}
	}
	
	public static void testPID(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.stop();
				stopWatch.reset();
				stopWatch.start();
				IntakeWheels.pickUp(.2);
				currentState = 1;
				break;
			case 1:
				time = stopWatch.get();
				if(lValue == 0 && rValue == 0 && time >= 2)
				{
					stopWatch.stop();
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					stopWatch.reset();
					currentState = 2;
					
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
				if(!Drivetrain.reachedDistance(lValue, rValue, 8500))
				{
					Drivetrain.tankDrive(.7, .7);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 10000))
				{
					Drivetrain.tankDrive(.4, .4);
				}
				else
				{
					//120 inches
					Encoders.testEncoders();
					currentState = 3;
				}
				break;
			case 3:
				Drivetrain.stop();
				break;
		}
	}
	
	public static void shootRight(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.stop();
				stopWatch.reset();
				stopWatch.start();
				IntakeWheels.pickUp(.2);
				currentState = 1;
				break;
			case 1:
				time = stopWatch.get();
				if(lValue == 0 && rValue == 0 && time >= 2)
				{
					stopWatch.stop();
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					stopWatch.reset();
					currentState = -1;
					
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
				stopWatch.reset();
				if(ElevatorLevel.reachedPickUp())
				{
					currentState = 3;
				}
				else
				{
					Elevator.moveEleVader(.4);
				}
				break;
		}
	}
	
	public static void initialize()
	{
		stopWatch.stop();
		stopWatch.reset();
		Drivetrain.stop();
		Forks.lockTheForks();
		Shifter.lowGear();
		Intake.closeIntake();
		Encoders.resetEncoders();
		IntakeWheels.runIntake(0, 0, true, 0, 0);
		Elevator.stopEleVader();
		Elevator.elevatorState = 0;
		Drivetrain.setToBrake();
		prevLeftEncoder = 0;
		prevRightEncoder = 0;
		currentState = 0;
		time = 0;
	}
	
	public static void testTurnLeft(double lValue, double rValue)
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
					currentState = 3;
					stopWatch.stop();
				}
				else if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 3;
					stopWatch.stop();
				}
				else
				{
					Elevator.moveEleVader(-.23);
					Encoders.resetEncoders();
				}
				break;
			case 3:
				if(!Drivetrain.reachedDistance(lValue, rValue, 2000))
				{
					Drivetrain.driveForw(lValue, rValue, .5);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 6000))
				{
					Drivetrain.driveForw(lValue, rValue, .8);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 7500))
				{
					Drivetrain.driveForw(lValue, rValue, .5);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					Encoders.testEncoders();
					currentState = 4;
				}
				break;
			case 4:
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				double dist = 5300;
				double ratio = 2.3;
				if(Functions.testCurve(rValue, dist) != 0)
				{
					rSSpeed = Functions.testCurve(rValue, dist);
					lSSpeed = rSSpeed/ratio;
					Drivetrain.goStraightLeft(lValue, rValue, ratio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 6;
				}
				break;
			case 5:
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, 6000))
				{
					Drivetrain.driveForw(lValue, rValue, .8);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 7500))
				{
					Drivetrain.driveForw(lValue, rValue, .3);
				}
				else
				{
					currentState = 6;
				}
				break;
			case 6:
				Drivetrain.stop();
				break;
		}
	}
	
	public static void testScale(double lValue, double rValue)
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
					currentState = 3;
					stopWatch.stop();
				}
				else if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 3;
					stopWatch.stop();
				}
				else
				{
					Elevator.moveEleVader(-.23);
					Encoders.resetEncoders();
				}
				break;
			case 3:
				if(!Drivetrain.reachedDistance(lValue, rValue, 2000))
				{
					Drivetrain.driveForw(lValue, rValue, .5);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 6000))
				{
					Drivetrain.driveForw(lValue, rValue, .8);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 7500))
				{
					Drivetrain.driveForw(lValue, rValue, .5);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 4;
				}
				break;
			case 4:
				stopWatch.reset();
				rValue -= prevRightEncoder;
				lSSpeed = Drivetrain.keepMotorInPlace(prevLeftEncoder, lValue);
				rSSpeed = .6;
				double dist = 2000;
				if(rValue < dist)
				{
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					currentState = 5;
				}
				break;
			case 5:
				Drivetrain.stop();
				stopWatch.start();
				currentState = 6;
				break;
			case 6:
				if(stopWatch.get() > .6)
				{
					stopWatch.stop();
					currentState = 7;
					prevRightEncoder = rValue;
				}
				break;
			case 7:
				stopWatch.reset();
				rValue -= prevRightEncoder;
				lSSpeed = Drivetrain.keepMotorInPlace(prevLeftEncoder, lValue);
				rSSpeed = -.6;
				dist = 2000;
				rValue = Math.abs(rValue);
				if(rValue < dist)
				{
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 8;
				}
				break;
			case 8:
				Drivetrain.stop();
				break;
		}
	}
	
	public static void testBackRight(double lValue, double rValue)
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
					currentState = 3;
					stopWatch.stop();
				}
				else if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 3;
					stopWatch.stop();
				}
				else
				{
					Elevator.moveEleVader(-.23);
					Encoders.resetEncoders();
				}
				break;
			case 3:
				if(!Drivetrain.reachedDistance(lValue, rValue, 2000))
				{
					Drivetrain.driveBack(lValue, rValue, -.5);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 3000))
				{
					Drivetrain.driveBack(lValue, rValue, -.4);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 4;
				}
				break;
			case 4:
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				double dist = 5200;
				double ratio = 2.3;
				if(Functions.testCurve(lValue, dist) != 0)
				{
					lSSpeed = Functions.testCurve(lValue, dist);
					rSSpeed = lSSpeed/ratio;
					Drivetrain.goBackRight(lValue, rValue, ratio, -lSSpeed, -rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 6;
				}
				break;
			case 5:
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, 2200))
				{
					Drivetrain.driveBack(lValue, rValue, -.6);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 3000))
				{
					Drivetrain.driveBack(lValue, rValue, -.2);
				}
				else
				{
					currentState = 6;
				}
				break;
			case 6:
				Drivetrain.stop();
				break;
		}
	}
	
	public static void rightSwitch1Cube(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.start();
				IntakeWheels.pickUp(.2);
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
				if(ElevatorLevel.reachedStop())
				{
					currentState = 3;
				}
				else if(ElevatorLevel.elevatorEncoderValue == 0)
				{
					currentState = 3;
				}
				else
				{
					ElevatorLevel.resetElevatorEncoders();
				}
				break;
			case 3:
				stopWatch.reset();
				if(ElevatorLevel.reachedSwitch())
				{
					currentState = 4;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToSwitch(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 4:
				ElevatorLevel.maintainSwitchPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, Constants.oneCubeSwitchRightSideStraight))
				{
					sum = (rValue +lValue)/2.0;
					Drivetrain.driveForw(lValue, rValue, Functions.oneCubeSwitchRightSideStraight(sum, Constants.oneCubeSwitchRightSideStraight));
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 5;
				}
				break;
			case 5:
				ElevatorLevel.maintainSwitchPosition();
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				if(Functions.oneCubeSwitchRightSideCurve(rValue, Constants.oneCubeSwitchRightSideCurve) != 0)
				{
					rSSpeed = Functions.oneCubeSwitchRightSideCurve(rValue, Constants.oneCubeSwitchRightSideCurve);
					lSSpeed = rSSpeed/Constants.oneCubeSwitchRightSideCurveRatio;
					Drivetrain.goStraightLeft(lValue, rValue, Constants.oneCubeSwitchRightSideCurveRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					stopWatch.start();
					currentState = 6;
				}
				break;
			case 6:
				Drivetrain.stop();
				ElevatorLevel.maintainSwitchPosition();
				if(stopWatch.get() > 2)
				{
					IntakeWheels.manuallyRunIntake(0, 0);
				}
				else
				{
					IntakeWheels.shoot(.5);
				}
				break;
		}
	}
	
	public static void leftSwitch1Cube(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.start();
				IntakeWheels.pickUp(.2);
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
				if(ElevatorLevel.reachedStop())
				{
					currentState = 3;
				}
				else if(ElevatorLevel.elevatorEncoderValue == 0)
				{
					currentState = 3;
				}
				else
				{
					ElevatorLevel.resetElevatorEncoders();
				}
				break;
			case 3:
				stopWatch.reset();
				if(ElevatorLevel.reachedSwitch())
				{
					currentState = 4;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToSwitch(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 4:
				ElevatorLevel.maintainSwitchPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, Constants.oneCubeSwitchRightSideStraight))
				{
					sum = (rValue +lValue)/2.0;
					Drivetrain.driveForw(lValue, rValue, Functions.oneCubeSwitchRightSideStraight(sum, Constants.oneCubeSwitchRightSideStraight));
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 5;
				}
				break;
			case 5:
				ElevatorLevel.maintainSwitchPosition();
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				if(Functions.oneCubeSwitchRightSideCurve(lValue, Constants.oneCubeSwitchRightSideCurve) != 0)
				{
					lSSpeed = Functions.oneCubeSwitchRightSideCurve(lValue, Constants.oneCubeSwitchRightSideCurve);
					rSSpeed = lSSpeed/Constants.oneCubeSwitchRightSideCurveRatio;
					Drivetrain.goStraightRight(lValue, rValue, Constants.oneCubeSwitchRightSideCurveRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					stopWatch.start();
					currentState = 6;
				}
				break;
			case 6:
				Drivetrain.stop();
				ElevatorLevel.maintainSwitchPosition();
				if(stopWatch.get() > 2)
				{
					IntakeWheels.manuallyRunIntake(0, 0);
				}
				else
				{
					IntakeWheels.shoot(.5);
				}
				break;
		}
	}
	
	public static void rightSwitch2Cube(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.start();
				IntakeWheels.pickUp(.2);
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
				if(ElevatorLevel.reachedStop())
				{
					currentState = 3;
				}
				else if(ElevatorLevel.elevatorEncoderValue == 0)
				{
					currentState = 3;
				}
				else
				{
					ElevatorLevel.resetElevatorEncoders();
				}
				break;
			case 3:
				stopWatch.reset();
				if(ElevatorLevel.reachedSwitch())
				{
					currentState = 4;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToSwitch(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 4:
				ElevatorLevel.maintainSwitchPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, Constants.twoCubeSwitchRightSideStraight))
				{
					sum = (rValue +lValue)/2.0;
					Drivetrain.driveForw(lValue, rValue, Functions.twoCubeSwitchRightSideStraight(sum, Constants.twoCubeSwitchRightSideStraight));
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 5;
				}
				break;
			case 5:
				ElevatorLevel.maintainSwitchPosition();
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
//				if(Functions.twoCubeSwitchRightSideCurve(rValue, Constants.twoCubeSwitchRightSideCurve) != 0)
//				{
//					rSSpeed = Functions.twoCubeSwitchRightSideCurve(rValue, Constants.twoCubeSwitchRightSideCurve);
//					lSSpeed = rSSpeed/Constants.twoCubeSwitchRightSideCurveRatio;
//					Drivetrain.goStraightLeft(lValue, rValue, Constants.twoCubeSwitchRightSideCurveRatio, lSSpeed, rSSpeed, .06);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder = rValue;
//					stopWatch.start();
//					currentState = 6;
//				}
				if(rValue < 9000 && lValue < 2800)
				{
					if(rValue < 3000)
					{
						rSSpeed = .5;
					}
					else if (rValue < 6000)
					{
						rSSpeed = .6;
					}
					else
					{
						rSSpeed = .4;
					}
					if(lValue < 930)
					{
						lSSpeed = .16;
					}
					else if (lValue < 1860)
					{
						lSSpeed = .2;
					}
					else
					{
						lSSpeed = .16;
					}
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else if(rValue > 9000 && lValue < 2800)
				{
					rSSpeed = 0;
					lSSpeed = .27;
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else if(rValue < 9000 && lValue > 2800)
				{
					rSSpeed = .4;
					lSSpeed = 0;
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					stopWatch.start();
					currentState = 6;
				}
				break;
			case 6:
				Drivetrain.stop();
				ElevatorLevel.maintainSwitchPosition();
				if(stopWatch.get() > 1.2 && lValue == 0 && rValue == 0)
				{
					IntakeWheels.manuallyRunIntake(0, 0);
					stopWatch.stop();
					stopWatch.reset();
					currentState = 7;
				}
				else
				{
					IntakeWheels.manuallyRunIntake(1, .5);
					Encoders.resetEncoders();
				}
				break;
			case 7:
				if(stopWatch.get() == 0)
				{
					stopWatch.start();
					currentState = 8;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 8:
				Intake.openIntake();
				if(ElevatorLevel.reachedStop() || stopWatch.get() > 2)
				{
					Elevator.stopEleVader();
					stopWatch.stop();
					stopWatch.reset();
					currentState = 8;
				}
				else
				{
					Elevator.moveEleVader(Functions.switchToStop(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 9:
				if(!Drivetrain.reachedDistance(lValue, rValue, 2000))
				{
					Drivetrain.driveForw(lValue, rValue, .4);
					IntakeWheels.pickUp(1);
				}
				else
				{
					Drivetrain.stop();
					stopWatch.stop();
					currentState = 9;
				}
				break;
			case 10:
				Intake.closeIntake();
				Encoders.resetEncoders();
				if(stopWatch.get() > .8)
				{
					currentState = 11;
				}
				break;
			case 11:
				stopWatch.stop();
				stopWatch.reset();
				if(!Drivetrain.reachedDistance(lValue, rValue, 1500))
				{
					Drivetrain.driveBack(lValue, rValue, -.4);
					IntakeWheels.pickUp(.2);
				}
				else
				{
					Drivetrain.stop();
					currentState = 12;
				}
				break;
			case 12:
				if(ElevatorLevel.reachedSwitch())
				{
					stopWatch.start();
					currentState = 13;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToSwitch(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 13:
				ElevatorLevel.maintainSwitchPosition();
				if(stopWatch.get() > .4)
				{
					IntakeWheels.manuallyRunIntake(1, .5);
				}
				else if(stopWatch.get() > .8)
				{
					currentState = 14;
				}
				break;
			case 14:
				ElevatorLevel.maintainSwitchPosition();
				IntakeWheels.manuallyRunIntake(0, 0);
				break;
		}
	}
	
	public static void leftSwitch2Cube(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.start();
				IntakeWheels.pickUp(.2);
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
				if(ElevatorLevel.reachedStop())
				{
					currentState = 3;
				}
				else if(ElevatorLevel.elevatorEncoderValue == 0)
				{
					currentState = 3;
				}
				else
				{
					ElevatorLevel.resetElevatorEncoders();
				}
				break;
			case 3:
				stopWatch.reset();
				if(ElevatorLevel.reachedSwitch())
				{
					currentState = 4;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToSwitch(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 4:
				ElevatorLevel.maintainSwitchPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, Constants.twoCubeSwitchLeftSideStraight))
				{
					sum = (rValue +lValue)/2.0;
					if(sum < (Constants.twoCubeSwitchLeftSideStraight - 1400))
					{
						Drivetrain.driveForw(lValue, rValue, .7);
					}
					else
					{
						Drivetrain.driveForw(lValue, rValue, .4);
					}
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 5;
				}
				break;
			case 5:
				ElevatorLevel.maintainSwitchPosition();
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				if(Functions.twoCubeSwitchLeftSideFirstCurve(rValue, Constants.twoCubeSwitchLeftSideFirstCurve) != 0)
				{
					rSSpeed = Functions.twoCubeSwitchLeftSideFirstCurve(rValue, Constants.twoCubeSwitchLeftSideFirstCurve);
					lSSpeed = rSSpeed/Constants.twoCubeSwitchLeftSideFirstCurveRatio;
					Drivetrain.goStraightLeft(lValue, rValue, Constants.twoCubeSwitchLeftSideFirstCurveRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					lValue+=prevLeftEncoder;
					rValue+=prevRightEncoder;
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 99;
				}
				break;
			case 99:
				ElevatorLevel.maintainSwitchPosition();
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, Constants.twoCubeSwitchLeftSideStraightCrossField))
				{
					sum = (rValue +lValue)/2.0;
					Drivetrain.driveForw(lValue, rValue, Functions.twoCubeSwitchLeftSideStraightCrossField(sum, Constants.twoCubeSwitchLeftSideStraightCrossField));
				}
				else
				{
					lValue+=prevLeftEncoder;
					rValue+=prevRightEncoder;
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 100;
				}
				break;
			case 100:
				ElevatorLevel.maintainSwitchPosition();
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				if(Functions.twoCubeSwitchLeftSideSecondCurve(rValue, Constants.twoCubeSwitchLeftSideSecondCurve) != 0)
				{
					rSSpeed = Functions.twoCubeSwitchLeftSideFirstCurve(rValue, Constants.twoCubeSwitchLeftSideSecondCurve);
					lSSpeed = rSSpeed/Constants.twoCubeSwitchLeftSideSecondCurveRatio;
					Drivetrain.goStraightLeft(lValue, rValue, Constants.twoCubeSwitchLeftSideSecondCurveRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					lValue+=prevLeftEncoder;
					rValue+=prevRightEncoder;
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 6;
				}
				break;
			case 6:
				Drivetrain.stop();
				ElevatorLevel.maintainSwitchPosition();
				if(stopWatch.get() > 1.2 && lValue == 0 && rValue == 0)
				{
					IntakeWheels.manuallyRunIntake(0, 0);
					stopWatch.stop();
					stopWatch.reset();
					currentState = 7;
				}
				else
				{
					IntakeWheels.manuallyRunIntake(.3, .7);
					Encoders.resetEncoders();
				}
				break;
			case 7:
				if(stopWatch.get() == 0)
				{
					stopWatch.start();
					currentState = 8;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 8:
				Intake.openIntake();
				if(ElevatorLevel.reachedStop() || stopWatch.get() > 2)
				{
					Elevator.stopEleVader();
					stopWatch.stop();
					stopWatch.reset();
					currentState = 8;
				}
				else
				{
					Elevator.moveEleVader(Functions.switchToStop(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 9:
				if(!Drivetrain.reachedDistance(lValue, rValue, 2000))
				{
					Drivetrain.driveForw(lValue, rValue, .4);
					IntakeWheels.pickUp(1);
				}
				else
				{
					Drivetrain.stop();
					stopWatch.stop();
					currentState = 9;
				}
				break;
			case 10:
				Intake.closeIntake();
				Encoders.resetEncoders();
				if(stopWatch.get() > .8)
				{
					currentState = 11;
				}
				break;
			case 11:
				stopWatch.stop();
				stopWatch.reset();
				if(!Drivetrain.reachedDistance(lValue, rValue, 1500))
				{
					Drivetrain.driveBack(lValue, rValue, -.4);
					IntakeWheels.pickUp(.2);
				}
				else
				{
					Drivetrain.stop();
					currentState = 12;
				}
				break;
			case 12:
				if(ElevatorLevel.reachedSwitch())
				{
					stopWatch.start();
					currentState = 13;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToSwitch(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 13:
				ElevatorLevel.maintainSwitchPosition();
				if(stopWatch.get() > .4)
				{
					IntakeWheels.manuallyRunIntake(.5, 1);
				}
				else if(stopWatch.get() > .8)
				{
					currentState = 14;
				}
				break;
			case 14:
				ElevatorLevel.maintainSwitchPosition();
				IntakeWheels.manuallyRunIntake(0, 0);
				break;
		}
	}
	
	public static void testTurnRight(double lValue, double rValue)
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
					currentState = 3;
					stopWatch.stop();
				}
				else if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 3;
					stopWatch.stop();
				}
				else
				{
					Elevator.moveEleVader(-.23);
					Encoders.resetEncoders();
				}
				break;
			case 3:
				if(!Drivetrain.reachedDistance(lValue, rValue, 2200))
				{
					Drivetrain.driveForw(lValue, rValue, .6);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 3000))
				{
					Drivetrain.driveForw(lValue, rValue, .2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 4;
				}
				break;
			case 4:
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				double dist = 5300;
				double ratio = 2.3;
				if(Functions.testCurve(rValue, dist) != 0)
				{
					lSSpeed = Functions.testCurve(rValue, dist);
					rSSpeed = lSSpeed/ratio;
					Drivetrain.goStraightRight(lValue, rValue, ratio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 6;
				}
				break;
			case 5:
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, 2200))
				{
					Drivetrain.driveForw(lValue, rValue, .6);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 3000))
				{
					Drivetrain.driveForw(lValue, rValue, .2);
				}
				else
				{
					currentState = 6;
				}
				break;
			case 6:
				Drivetrain.stop();
				break;
		}
	}
	
	public static void testS(double lValue, double rValue)
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
					currentState = 3;
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
				stopWatch.reset();
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, 6500))
				{
					Drivetrain.driveForw(lValue, rValue, .8);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 8000))
				{
					Drivetrain.driveForw(lValue, rValue, .3);
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
				rValue -= prevRightEncoder;
				lSSpeed = Drivetrain.keepMotorInPlace(prevLeftEncoder, lValue);
				rSSpeed = .7;
				// 5000 and 2.3
				double dist = 4000;
				if(rValue < dist)
				{
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					currentState = 5;
				}
				break;
			case 5:
				ElevatorLevel.maintainPickUpPosition();
				Drivetrain.stop();
				Encoders.resetEncoders();
				stopWatch.start();
				currentState = 6;
				break;
			case 6:
				ElevatorLevel.maintainPickUpPosition();
				if(lValue == 0 && rValue == 0 && stopWatch.get() > 1)
				{
					stopWatch.stop();
					currentState = 7;
				}
				else
				{
					Encoders.resetEncoders();
				}
				break;
			case 7:
				ElevatorLevel.maintainPickUpPosition();
				lSSpeed = Drivetrain.keepMotorInPlace(0, lValue);
				rSSpeed = -.7;
				// 5000 and 2.3
				rValue = Math.abs(rValue);
				dist = 4000;
				if(rValue < dist)
				{
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					currentState = 8;
				}
				break;
			case 8:
				ElevatorLevel.maintainPickUpPosition();
				lValue -= prevLeftEncoder;
				rValue -= prevRightEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, 4000))
				{
					Drivetrain.driveBack(lValue, rValue, -.7);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 5500))
				{
					Drivetrain.driveBack(lValue, rValue, -.3);
				}
				else
				{
					currentState = 9;
				}
				break;
			case 10:
				ElevatorLevel.maintainPickUpPosition();
				Drivetrain.stop();
				break;
		}
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
					currentState = 3;
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
					Elevator.moveEleVader(.4);
				}
				break;
			case 3:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, 9500))
				{
					Drivetrain.tankDrive(.8, .8);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 11000))
				{
					Drivetrain.tankDrive(.5, .5);
				}
				else
				{
					Encoders.testEncoders();
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
					Drivetrain.driveBack(lValue, rValue, -.74);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 7000))
				{
					Drivetrain.driveBack(lValue, rValue, -.3);
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
					currentState = 3;
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
				if(!Drivetrain.reachedDistance(lValue, rValue, 8000))
				{
					Drivetrain.driveForw(lValue, rValue, .8);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 9500))
				{
					Drivetrain.driveForw(lValue, rValue, .3);
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
				rValue -= prevRightEncoder;
				lSSpeed = Drivetrain.keepMotorInPlace(prevLeftEncoder, lValue);
				rSSpeed = .7;
				// 5000 and 2.3
				double dist = 4000;
				if(rValue < dist)
				{
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
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
	
	public static void test4(double lValue, double rValue)//no need
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
				lSSpeed = Drivetrain.keepMotorInPlace(0, lValue);
				rSSpeed = -.7;
				// 5000 and 2.3
				rValue = Math.abs(rValue);
				double dist = 4000;
				if(rValue < dist)
				{
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					currentState = 5;
				}
				break;
			case 4:
				ElevatorLevel.maintainPickUpPosition();
				lValue -= prevLeftEncoder;
				rValue -= prevRightEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, 4000))
				{
					Drivetrain.driveBack(lValue, rValue, -.7);
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
				if(!Drivetrain.reachedDistance(lValue, rValue, 3000))
				{
					Drivetrain.driveBack(lValue, rValue, -.6);
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
			case 6:
				lValue -= prevLeftEncoder;
				rValue -= prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, 3000))
				{
					Drivetrain.driveBack(lValue, rValue, -.6);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 4000))
				{
					Drivetrain.driveBack(lValue, rValue, -.3);
				}
				else
				{
					currentState = 5;
				}
				break;
		}
	}
	
	public static void test6(double lValue, double rValue)
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
				if(!Drivetrain.reachedDistance(lValue, rValue, 4000))
				{
					Drivetrain.driveBack(lValue, rValue, -.6);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 5;
				}
				break;
			case 5:
				ElevatorLevel.maintainPickUpPosition();
				lValue -= prevLeftEncoder;
				rValue -= prevRightEncoder;
				double dist1 = 5400;
				double ratio1 = 3.26;
				if(Math.abs(lValue) < dist1)
				{
					lSSpeed = Functions.test5Turn(lValue, dist1);
					rSSpeed = lSSpeed/ratio1;
					Drivetrain.goBackRight(lValue, rValue, ratio1, lSSpeed, rSSpeed, .6);
				}
				else
				{
					currentState = 6;
				}
				break;
			case 6:
				ElevatorLevel.maintainPickUpPosition();
				Drivetrain.stop();
				break;
		}
	}
	
	public static void test7(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.start();
				IntakeWheels.pickUp(.2);
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
				if(ElevatorLevel.reachedStop())
				{
					currentState = 100;
				}
				else if(ElevatorLevel.elevatorEncoderValue == 0)
				{
					currentState = 100;
				}
				else
				{
					ElevatorLevel.resetElevatorEncoders();
				}
				break;
			case 100:
				stopWatch.reset();
				if(ElevatorLevel.reachedPickUp())
				{
					currentState = 3;
				}
				else
				{
					Elevator.moveEleVader(.4);
				}
				break;
			case 3:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, 2000))
				{
					Drivetrain.driveForw(lValue, rValue, .74);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 2800))
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
	
	public static void rrScaleFirstSwitchSecond(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.start();
				IntakeWheels.pickUp(.2);
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
				if(ElevatorLevel.reachedStop())
				{
					currentState = 254;
				}
				else if(ElevatorLevel.elevatorEncoderValue == 0)
				{
					currentState = 254;
				}
				else
				{
					ElevatorLevel.resetElevatorEncoders();
				}
				break;
			case 254:
				stopWatch.reset();
				if(ElevatorLevel.reachedPickUp())
				{
					currentState = 3;
				}
				else
				{
					Elevator.moveEleVader(.23);
				}
				break;
			case 3:
				Functions.lrandrrElevatorForFirstScale(lValue, rValue, ElevatorLevel.elevatorEncoderValue, 1);
				if(Functions.lrandrrSpeedForFirstScale(lValue, rValue, 21500) != 0)
				{
					Drivetrain.driveForw(lValue, rValue, Functions.lrandrrSpeedForFirstScale(lValue, rValue, 21500));
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 4;
				}
				break;
			case 4:
				Functions.lrandrrElevatorForFirstScale(lValue, rValue, ElevatorLevel.elevatorEncoderValue, 1);
				rValue -= prevRightEncoder;
				lSSpeed = Drivetrain.keepMotorInPlace(prevLeftEncoder, lValue);
				rSSpeed = .52;
				double dist = 1700;
				stopWatch.stop();
				stopWatch.reset();
				if(rValue < dist)
				{
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					stopWatch.start();
					currentState = 5;
				}
				break;
			case 5:
				Drivetrain.stop();
				if(ElevatorLevel.reachedScale() || stopWatch.get() > 1)
				{
					ElevatorLevel.maintainScalePosition();
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 6;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToScale(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 6:
				ElevatorLevel.maintainScalePosition();
				if(stopWatch.get() < .6)
				{
					IntakeWheels.shoot(.7);
				}
				else
				{
					stopWatch.stop();
					stopWatch.reset();
					prevRightEncoder = rValue;
					prevLeftEncoder = lValue;
					currentState = 7;
				}
				break;
			case 7:
				ElevatorLevel.maintainScalePosition();
				rValue -= prevRightEncoder;
				lSSpeed = Drivetrain.keepMotorInPlace(prevLeftEncoder, lValue);
				rSSpeed = -.7;
				dist = 2700;
				rValue = Math.abs(rValue);
				if(rValue < dist)
				{
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					prevLeftEncoder = Encoders.leftEncoderValue;
					prevRightEncoder = Encoders.rightEncoderValue;
					currentState = 99;
				}
				break;
			case 99:
				Functions.moveElevatorToStop(ElevatorLevel.elevatorEncoderValue);
				lValue -= prevLeftEncoder;
				rValue -= prevRightEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, 1600))
				{
					Drivetrain.driveBack(lValue, rValue, -.4);
				}
				else
				{
					stopWatch.stop();
					stopWatch.reset();
					lValue+= prevLeftEncoder;
					rValue+= prevLeftEncoder;
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					
					currentState = 8;
	
				}
				break;
			case 8:
				Functions.moveElevatorToStop(ElevatorLevel.elevatorEncoderValue);
				lValue -= prevLeftEncoder;
				rSSpeed = Drivetrain.keepMotorInPlace(prevRightEncoder, rValue);
				lSSpeed = -.5;
				dist = 4780;
				stopWatch.stop();
				stopWatch.reset();
				lValue = Math.abs(lValue);
				if(lValue < dist)
				{
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					stopWatch.start();
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 1000;
				}
				break;
			case 1000:
				Functions.moveElevatorToStop(ElevatorLevel.elevatorEncoderValue);
				if(stopWatch.get() < .5)
				{
					Drivetrain.stop();
				}
				else if(stopWatch.get() < .8)
				{
					Drivetrain.stop();
					Encoders.resetEncoders();
				}
				else
				{
					stopWatch.stop();
					stopWatch.reset();
					currentState = 12;
				}
				break;
			case 1001:
				rValue -= prevRightEncoder;
				lSSpeed = Drivetrain.keepMotorInPlace(prevLeftEncoder, lValue);
				rSSpeed = .7;
				dist = 2600;
				stopWatch.stop();
				stopWatch.reset();
				if(rValue < dist)
				{
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 1002;
				}
				break;
			case 1002:
				lValue -= prevLeftEncoder;
				rValue -= prevRightEncoder;
				Intake.openIntake();
				IntakeWheels.pickUp(1);
				if(!Drivetrain.reachedDistance(lValue, rValue, 3000))
				{
					Drivetrain.driveForw(lValue, rValue, .4);
				}
				else
				{
					currentState = 1003;
					Drivetrain.stop();
					stopWatch.start();
				}
				break;
			case 1003:
				Intake.closeIntake();
				Encoders.resetEncoders();
				if(stopWatch.get() > .8)
				{
					currentState = 1004;
				}
				break;
			case 1004:
				stopWatch.stop();
				stopWatch.reset();
				if(!Drivetrain.reachedDistance(lValue, rValue, 1500))
				{
					Drivetrain.driveBack(lValue, rValue, -.4);
					IntakeWheels.pickUp(.2);
				}
				else
				{
					Drivetrain.stop();
					currentState = 1005;
				}
				break;
			case 1005:
				if(ElevatorLevel.reachedSwitch())
				{
					stopWatch.start();
					currentState = 1006;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToSwitch(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 1006:
				ElevatorLevel.maintainSwitchPosition();
				if(stopWatch.get() > .4)
				{
					IntakeWheels.manuallyRunIntake(1, .5);
				}
				else if(stopWatch.get() > .8)
				{
					currentState = 15;
				}
				break;
			case 9:
				Functions.moveElevatorToStop(ElevatorLevel.elevatorEncoderValue);
				lValue -= prevLeftEncoder;
				rValue -= prevRightEncoder;
				stopWatch.stop();
				stopWatch.reset();
				if(Functions.lrandrrBackUpToWallTurn(lValue, 6500) != 0)
				{
					lSSpeed = Functions.lrandrrBackUpToWallTurn(lValue, 6500);
					rSSpeed = lSSpeed/2.3;
					Drivetrain.goBackRight(lValue, rValue, 2.3, lSSpeed, rSSpeed, .06);
				}
				else
				{
					currentState = 15;
				}
				break;
			case 10:
				Functions.moveElevatorToStop(ElevatorLevel.elevatorEncoderValue);
				Drivetrain.stop();
				stopWatch.start();
				Encoders.resetEncoders();
				currentState = 11;
				break;
			case 11:
				Functions.moveElevatorToStop(ElevatorLevel.elevatorEncoderValue);
				if(stopWatch.get() > .5)
				{
					currentState = 12;
				}
				else
				{
					Encoders.resetEncoders();
				}
				break;
			case 12:
				Functions.moveElevatorToStop(ElevatorLevel.elevatorEncoderValue);
				if(!Drivetrain.reachedDistance(lValue, rValue, 1800))
				{
					Drivetrain.driveForw(lValue, rValue, .4);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 13;
				}
				break;
			case 13:
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				dist = 5300;
				double ratio = 2.5;
				if(Functions.testCurve(rValue, dist) != 0)
				{
					rSSpeed = Functions.testCurve(rValue, dist);
					lSSpeed = rSSpeed/ratio;
					Drivetrain.goStraightLeft(lValue, rValue, ratio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					rValue += prevRightEncoder;
					lValue += prevLeftEncoder;
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 14;
				}
				break;
			case 14:
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, 2000))
				{
					Intake.openIntake();
					IntakeWheels.pickUp(.8);
					Drivetrain.driveForw(lValue, rValue, .6);
				}
				else
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 1003;
				}
				break;
			case 15:
				Drivetrain.stop();
				ElevatorLevel.maintainSwitchPosition();
				break;
		}
	}
	
	public static void lrScaleFirstSwitchSecond(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.start();
				IntakeWheels.pickUp(.2);
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
				if(ElevatorLevel.reachedStop())
				{
					currentState = 254;
				}
				else if(ElevatorLevel.elevatorEncoderValue == 0)
				{
					currentState = 254;
				}
				else
				{
					ElevatorLevel.resetElevatorEncoders();
				}
				break;
			case 254:
				stopWatch.reset();
				if(ElevatorLevel.reachedPickUp())
				{
					currentState = 3;
				}
				else
				{
					Elevator.moveEleVader(.23);
				}
				break;
			case 3:
				Functions.lrandrrElevatorForFirstScale(lValue, rValue, ElevatorLevel.elevatorEncoderValue, 1);
				if(Functions.lrandrrSpeedForFirstScale(lValue, rValue, 21500) != 0)
				{
					Drivetrain.driveForw(lValue, rValue, Functions.lrandrrSpeedForFirstScale(lValue, rValue, 21500));
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 4;
				}
				break;
			case 4:
				Functions.lrandrrElevatorForFirstScale(lValue, rValue, ElevatorLevel.elevatorEncoderValue, 1);
				rValue -= prevRightEncoder;
				lSSpeed = Drivetrain.keepMotorInPlace(prevLeftEncoder, lValue);
				rSSpeed = .52;
				double dist = 1700;
				stopWatch.stop();
				stopWatch.reset();
				if(rValue < dist)
				{
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					stopWatch.start();
					currentState = 5;
				}
				break;
			case 5:
				Drivetrain.stop();
				if(ElevatorLevel.reachedScale() || stopWatch.get() > 1)
				{
					ElevatorLevel.maintainScalePosition();
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 6;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToScale(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 6:
				ElevatorLevel.maintainScalePosition();
				if(stopWatch.get() < .6)
				{
					IntakeWheels.shoot(.7);
				}
				else
				{
					stopWatch.stop();
					stopWatch.reset();
					prevRightEncoder = rValue;
					prevLeftEncoder = lValue;
					currentState = 7;
				}
				break;
			case 7:
				ElevatorLevel.maintainScalePosition();
				rValue -= prevRightEncoder;
				lSSpeed = Drivetrain.keepMotorInPlace(prevLeftEncoder, lValue);
				rSSpeed = -.7;
				dist = 2700;
				rValue = Math.abs(rValue);
				if(rValue < dist)
				{
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					prevLeftEncoder = Encoders.leftEncoderValue;
					prevRightEncoder = Encoders.rightEncoderValue;
					currentState = 99;
				}
				break;
			case 99:
				Functions.moveElevatorToStop(ElevatorLevel.elevatorEncoderValue);
				lValue -= prevLeftEncoder;
				rValue -= prevRightEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, 1600))
				{
					Drivetrain.driveBack(lValue, rValue, -.4);
				}
				else
				{
					stopWatch.stop();
					stopWatch.reset();
					lValue+= prevLeftEncoder;
					rValue+= prevLeftEncoder;
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 8;
					
				}
				break;
			case 8:
				Functions.moveElevatorToStop(ElevatorLevel.elevatorEncoderValue);
				lValue -= prevLeftEncoder;
				rSSpeed = Drivetrain.keepMotorInPlace(prevRightEncoder, rValue);
				lSSpeed = -.5;
				dist = 4780;
				stopWatch.stop();
				stopWatch.reset();
				lValue = Math.abs(lValue);
				if(lValue < dist)
				{
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					stopWatch.start();
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 1000;
				}
				break;
			case 1000:
				Functions.moveElevatorToStop(ElevatorLevel.elevatorEncoderValue);
				if(stopWatch.get() < .5)
				{
					Drivetrain.stop();
				}
				else if(stopWatch.get() < .8)
				{
					Drivetrain.stop();
					Encoders.resetEncoders();
				}
				else
				{
					stopWatch.stop();
					stopWatch.reset();
					currentState = 12;
				}
				break;
			case 1001:
				rValue -= prevRightEncoder;
				lSSpeed = Drivetrain.keepMotorInPlace(prevLeftEncoder, lValue);
				rSSpeed = .7;
				dist = 2600;
				stopWatch.stop();
				stopWatch.reset();
				if(rValue < dist)
				{
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 1002;
				}
				break;
			case 1002:
				lValue -= prevLeftEncoder;
				rValue -= prevRightEncoder;
				Intake.openIntake();
				IntakeWheels.pickUp(1);
				if(!Drivetrain.reachedDistance(lValue, rValue, 3000))
				{
					Drivetrain.driveForw(lValue, rValue, .4);
				}
				else
				{
					currentState = 1003;
					Drivetrain.stop();
					stopWatch.start();
				}
				break;
			case 1003:
				Intake.closeIntake();
				Encoders.resetEncoders();
				if(stopWatch.get() > .8)
				{
					currentState = 1004;
				}
				break;
			case 1004:
				stopWatch.stop();
				stopWatch.reset();
				if(!Drivetrain.reachedDistance(lValue, rValue, 1500))
				{
					Drivetrain.driveBack(lValue, rValue, -.4);
					IntakeWheels.pickUp(.2);
				}
				else
				{
					Drivetrain.stop();
					currentState = 1005;
				}
				break;
			case 1005:
				if(ElevatorLevel.reachedSwitch())
				{
					stopWatch.start();
					currentState = 1006;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToSwitch(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 1006:
				ElevatorLevel.maintainSwitchPosition();
				if(stopWatch.get() > .4)
				{
					IntakeWheels.manuallyRunIntake(1, .5);
				}
				else if(stopWatch.get() > .8)
				{
					currentState = 15;
				}
				break;
			case 9:
				Functions.moveElevatorToStop(ElevatorLevel.elevatorEncoderValue);
				lValue -= prevLeftEncoder;
				rValue -= prevRightEncoder;
				stopWatch.stop();
				stopWatch.reset();
				if(Functions.lrandrrBackUpToWallTurn(lValue, 6500) != 0)
				{
					lSSpeed = Functions.lrandrrBackUpToWallTurn(lValue, 6500);
					rSSpeed = lSSpeed/2.3;
					Drivetrain.goBackRight(lValue, rValue, 2.3, lSSpeed, rSSpeed, .06);
				}
				else
				{
					currentState = 15;
				}
				break;
			case 10:
				Functions.moveElevatorToStop(ElevatorLevel.elevatorEncoderValue);
				Drivetrain.stop();
				stopWatch.start();
				Encoders.resetEncoders();
				currentState = 11;
				break;
			case 11:
				Functions.moveElevatorToStop(ElevatorLevel.elevatorEncoderValue);
				if(stopWatch.get() > .5)
				{
					currentState = 12;
				}
				else
				{
					Encoders.resetEncoders();
				}
				break;
			case 12:
				Functions.moveElevatorToStop(ElevatorLevel.elevatorEncoderValue);
				if(!Drivetrain.reachedDistance(lValue, rValue, 2000))
				{
					Drivetrain.driveForw(lValue, rValue, .6);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 10800))
				{
					Drivetrain.driveForw(lValue, rValue, .8);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 12000))
				{
					Drivetrain.driveForw(lValue, rValue, .3);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 13;
				}
				break;
			case 13:
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				dist = 5300;
				double ratio = 2.5;
				if(Functions.testCurve(rValue, dist) != 0)
				{
					rSSpeed = Functions.testCurve(rValue, dist);
					lSSpeed = rSSpeed/ratio;
					Drivetrain.goStraightLeft(lValue, rValue, ratio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					rValue += prevRightEncoder;
					lValue += prevLeftEncoder;
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 14;
				}
				break;
			case 14:
				rValue -= prevRightEncoder;
				lValue -= prevLeftEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, 2000))
				{
					Intake.openIntake();
					IntakeWheels.pickUp(.8);
					Drivetrain.driveForw(lValue, rValue, .6);
				}
				else
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 1003;
				}
				break;
			case 15:
				Drivetrain.stop();
				ElevatorLevel.maintainSwitchPosition();
				break;
		}
	}
	
}
