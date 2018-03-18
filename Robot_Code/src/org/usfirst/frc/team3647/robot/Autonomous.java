package org.usfirst.frc.team3647.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import team3647ConstantsAndFunctions.AutoConstants;
import team3647ConstantsAndFunctions.Constants;
import team3647ConstantsAndFunctions.Functions;
import team3647elevator.Elevator;
import team3647elevator.ElevatorLevel;
import team3647elevator.Intake;
import team3647pistons.Forks;
import team3647pistons.Shifter;
import team3647pistons.intakeMechanism;
import team3647subsystems.Drivetrain;
import team3647subsystems.Encoders;

public class Autonomous 
{
	//Switch: Y direction: 168 inches, center of the robot
	//Switch: X direction: 55.5 inches, start far edge to end robot
	//Scale: Y direction: 324 inches, center of the robot
	//Scale: X direction: 42 inches, start far edge to end robot
	
	StopWatch yes = new StopWatch();
	static double time;
	
	static int currentState;
	static double lSSpeed, rSSpeed, adjustmentConstant, speed;
	static double []adjustmentValues = new double[2];
	static double prevLeftEncoder, prevRightEncoder, avg;
	static double requiredLeftDist, requiredRightDist;
	
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
		if(gameData.charAt(0) == 'R')
		{
			middleSideRightAuto(lValue, rValue);
		}
		else if(gameData.charAt(0) == 'L')
		{
			middleSideLeftAuto(lValue, rValue);
		}
//		if(gameData.charAt(0) == 'R')
//		{
//			rightSwicth(lValue, rValue);
//		}
		else
		{
			crossBaseline(lValue, rValue);
			System.out.println("Crossing Baseline");
			System.out.println("GameData: " + gameData);
		}
	}
	

	public static void rightTurn(double lValue, double rValue)
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
					Drivetrain.turnRight(lValue, rValue);
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
	
	public static void leftTurn(double lValue, double rValue)
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

	public static void crossBaseline(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				prevLeftEncoder = 0;
				prevRightEncoder = 0;
				if(lValue == 0 && rValue == 0)
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 2;
				}
				else
				{
					Encoders.resetEncoders();
				}
				break;
			case 1:
				if(ElevatorLevel.reachedPickUp())
				{
					Elevator.stopEleVader();
					currentState = 2;
				}
				else
				{
					Elevator.moveEleVader(.5);
				}
				break;
			case 2:
				if(!Drivetrain.reachedDistance(lValue, rValue, 13000))
				{
					avg = (lValue + rValue)/2.0;
					speed = .7;
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else
				{
					currentState = 3;
				}
				break;
			case 3:
				Drivetrain.stop();
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
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.lrStraightToScale - 1600))
				{
					avg = (lValue + rValue)/2.0;
					speed = Functions.lrStarightToScale(avg);
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else
				{
					currentState = 3;
				}
				break;
			case 3:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.lrStraightToScale))
				{
					speed = .2;
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 4;
				}
				break;
			case 4:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				lSSpeed = 0;
				if(rValue < AutoConstants.lrScaleTurn && !ElevatorLevel.reachedScale())
				{
					rSSpeed = .35;
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
					Elevator.moveEleVader(Functions.pickUpToScale(ElevatorLevel.elevatorEncoderValue));
				}
				else if(rValue >= AutoConstants.lrScaleTurn && !ElevatorLevel.reachedScale())
				{
					Drivetrain.stop();
					if(ElevatorLevel.elevatorEncoderValue < Constants.scale)
					{
						Elevator.moveEleVader(Functions.pickUpToScale(ElevatorLevel.elevatorEncoderValue));
					}
					else
					{
						Elevator.moveEleVader(-.2);
					}
						
				}
				else if(rValue < AutoConstants.scaleJankTurnToScaleRightSide && ElevatorLevel.reachedScale())
				{
					rSSpeed = .7;
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
					ElevatorLevel.maintainScalePosition();
				}
				else
				{
					Drivetrain.stop();
					ElevatorLevel.maintainScalePosition();
					Timer.delay(.2);
					currentState = 5;
				}
				break;
			case 5:
//				if(Intake.bannerSensor.get())
//				{
//					Intake.shootCube();
//				}
//				else
//				{
//					Timer.delay(.3);
//					culrentState = 6;
//				}
				ElevatorLevel.maintainScalePosition();
				Intake.shootCube();
				Encoders.resetEncoders();
				Timer.delay(1);
				currentState = 6;
				break;
			case 6:
				Intake.stopIntake();
				lSSpeed = 0;
				if(Math.abs(rValue) < AutoConstants.lrBackTurnAfterSwitch && !ElevatorLevel.reachedPickUp())
				{
					rSSpeed = -.55;
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
					Elevator.moveEleVader(Functions.scaleToPickUp(ElevatorLevel.elevatorEncoderValue));
				}
				else if(Math.abs(rValue) >= AutoConstants.lrBackTurnAfterSwitch && !ElevatorLevel.reachedPickUp())
				{
					Drivetrain.stop();
					Elevator.moveEleVader(Functions.scaleToPickUp(ElevatorLevel.elevatorEncoderValue));
				}
				else if(Math.abs(rValue) < AutoConstants.lrBackTurnAfterSwitch && ElevatorLevel.reachedPickUp())
				{
					rSSpeed = -.55;
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
					Elevator.stopEleVader();
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 14;
				}
				break;
			case 7:
				ElevatorLevel.maintainPickUpPosition();
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.lrBackTurnAfterSwitch - 1500))
				{
					Drivetrain.driveBack(lValue, rValue, -.8);
				}
				else
				{
					currentState = 8;
				}
				break;
			case 8:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.lrBackTurnAfterSwitch))
				{
					Drivetrain.driveBack(lValue, rValue, -.2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 9;
				}
				break;
			case 9:
				ElevatorLevel.maintainPickUpPosition();
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				rSSpeed = 0;
				lValue = Math.abs(lValue);
				if(lValue < AutoConstants.lrbackUpToWallTurnDist)
				{
					lSSpeed = -.6;
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					currentState = 14;
				}
				break;
			case 10:
				ElevatorLevel.maintainPickUpPosition();
				Drivetrain.stop();
				Timer.delay(.1);
				currentState = 11;
				break;
			case 11:
				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedPickUp())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 12;
				}
				else
				{
					Encoders.resetEncoders();
					ElevatorLevel.maintainPickUpPosition();
				}
				break;
			case 12:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.lrStraightToCube - 1600))
				{
					Drivetrain.driveForw(lValue, rValue, .8);
				}
				else
				{
					currentState = 12;
				}
				break;
			case 13:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.lrStraightToCube))
				{
					speed = .2;
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 14;
				}
				break;
			case 14:
				ElevatorLevel.maintainPickUpPosition();
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				if(Functions.lrPickUpCube(rValue) != 0)
				{
					rSSpeed = Functions.lrPickUpCube(rValue);
					lSSpeed = rSSpeed/AutoConstants.lrCubeTurnRatio;
					Drivetrain.goStraightLeft(lValue, rValue, AutoConstants.lrCubeTurnRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 15;
				}
				break;
			case 15:
				Drivetrain.stop();
				break;
		}
	}
	
	public static void rr(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
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
					currentState = 2;
				}
				else
				{
					Elevator.moveEleVader(.5);
				}
				break;
			case 2:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rrStraightToScale - 1800))
				{
					avg = (lValue + rValue)/2.0;
					speed = Functions.rrStarightToScale(avg);
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else
				{
					currentState = 3;
				}
				break;
			case 3:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rrStraightToScale))
				{
					speed = .2;
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 4;
				}
				break;
			case 4:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				lSSpeed = 0;
				if(rValue < AutoConstants.rrScaleTurn && !ElevatorLevel.reachedScale())
				{
					rSSpeed = .7;
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
					Elevator.moveEleVader(Functions.pickUpToScale(ElevatorLevel.elevatorEncoderValue));
				}
				else if(rValue >= AutoConstants.rrScaleTurn && !ElevatorLevel.reachedScale())
				{
					Drivetrain.stop();
					if(ElevatorLevel.elevatorEncoderValue < Constants.scale)
					{
						Elevator.moveEleVader(Functions.pickUpToScale(ElevatorLevel.elevatorEncoderValue));
					}
					else
					{
						Elevator.moveEleVader(-.2);
					}
						
				}
				else if(rValue < AutoConstants.scaleJankTurnToScaleRightSide && ElevatorLevel.reachedScale())
				{
					rSSpeed = .7;
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
					ElevatorLevel.maintainScalePosition();
				}
				else
				{
					Drivetrain.stop();
					ElevatorLevel.maintainScalePosition();
					Timer.delay(.2);
					currentState = 5;
				}
				break;
			case 5:
//				if(Intake.bannerSensor.get())
//				{
//					Intake.shootCube();
//				}
//				else
//				{
//					Timer.delay(.3);
//					currentState = 6;
//				}
				ElevatorLevel.maintainScalePosition();
				Intake.shootCube();
				Encoders.resetEncoders();
				Timer.delay(1);
				currentState = 6;
				break;
			case 6:
				Intake.stopIntake();
				lSSpeed = 0;
				if(Math.abs(rValue) < AutoConstants.rrBackTurnAfterSwitch && !ElevatorLevel.reachedPickUp())
				{
					rSSpeed = -.55;
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
					Elevator.moveEleVader(Functions.scaleToPickUp(ElevatorLevel.elevatorEncoderValue));
				}
				else if(Math.abs(rValue) >= AutoConstants.rrBackTurnAfterSwitch && !ElevatorLevel.reachedPickUp())
				{
					Drivetrain.stop();
					Elevator.moveEleVader(Functions.scaleToPickUp(ElevatorLevel.elevatorEncoderValue));
				}
				else if(Math.abs(rValue) < AutoConstants.rrBackTurnAfterSwitch && ElevatorLevel.reachedPickUp())
				{
					rSSpeed = -.55;
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
					Elevator.stopEleVader();
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 7;
				}
				break;
			case 7:
				ElevatorLevel.maintainPickUpPosition();
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rrBackTurnAfterSwitch - 1500))
				{
					Drivetrain.driveBack(lValue, rValue, -.8);
				}
				else
				{
					currentState = 8;
				}
				break;
			case 8:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rrBackTurnAfterSwitch))
				{
					Drivetrain.driveBack(lValue, rValue, -.2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 9;
				}
				break;
			case 9:
				ElevatorLevel.maintainPickUpPosition();
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				rSSpeed = 0;
				lValue = Math.abs(lValue);
				if(lValue < AutoConstants.rrbackUpToWallTurnDist)
				{
					lSSpeed = -.6;
					Drivetrain.tankDrive(lSSpeed, rSSpeed);
				}
				else
				{
					currentState = 19;
				}
				break;
			case 10:
				ElevatorLevel.maintainPickUpPosition();
				Drivetrain.stop();
				Timer.delay(.35);
				currentState = 11;
				break;
			case 11:
				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedPickUp())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 12;
				}
				else
				{
					Encoders.resetEncoders();
					ElevatorLevel.maintainPickUpPosition();
				}
				break;
			case 12:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rrStraightToCube - 1600))
				{
					Drivetrain.driveForw(lValue, rValue, .8);
				}
				else
				{
					currentState = 13;
				}
				break;
			case 13:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rrStraightToCube))
				{
					speed = .2;
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 14;
				}
				break;
			case 14:
				ElevatorLevel.maintainPickUpPosition();
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				if(Functions.rrPickUpCube(rValue) != 0)
				{
					rSSpeed = Functions.rrPickUpCube(rValue);
					lSSpeed = rSSpeed/AutoConstants.rrCubeTurnRatio;
					Drivetrain.goStraightLeft(lValue, rValue, AutoConstants.rrCubeTurnRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 15;
				}
				break;
			case 15:
				ElevatorLevel.maintainPickUpPosition();
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, 2000))
				{
					Intake.pickUpCube();
					speed = .7;
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else
				{
					Drivetrain.stop();
					currentState = 16;
				}
				break;
			case 16:
				Timer.delay(.2);
				Encoders.resetEncoders();
				Timer.delay(.1);
				currentState = 17;
				break;
			case 17:
				avg = (lValue + rValue)/2.0;
				if(avg < 2000 && !ElevatorLevel.reachedSwitch())
				{
					Elevator.moveEleVader(Functions.stopToSwitch(ElevatorLevel.elevatorEncoderValue));
					Drivetrain.driveForw(lValue, rValue, .5);
				}
				else if(avg < 2000 && ElevatorLevel.reachedSwitch())
				{
					Drivetrain.driveForw(lValue, rValue, .5);
					Elevator.stopEleVader();
				}
				else if(avg > 2000 && !ElevatorLevel.reachedSwitch())
				{
					Drivetrain.stop();
					Elevator.moveEleVader(Functions.stopToSwitch(ElevatorLevel.elevatorEncoderValue));
				}
				else
				{
					Drivetrain.stop();
					Elevator.stopEleVader();
					currentState = 18;
				}
				break;
			case 18:
				Intake.shootCube();
				Timer.delay(1);
				currentState = 19;
				break;
			case 19:
				Intake.stopIntake();
				Drivetrain.stop();
				Elevator.stopEleVader();
				break;
		}
	}
	

	public static void middleSideLeftAuto(double lValue, double rValue)//switch
	{
		//Before Straight: Make sure X is 72 inches to the left
		//After Straight: Make sure Y is 140 inches at end.
		
		switch(currentState)
		{
			

			case 99:
				if(ElevatorLevel.reachedPickUp())
				{
					Intake.runIntake(0, .3);
					Elevator.stopEleVader();
					currentState = 1;
				}
				else
				{
					Elevator.moveEleVader(.3);
				}
				break;
			case 1:
				ElevatorLevel.maintainPickUpPosition();
				if(Functions.firstTurnSpeedForMSlSW(rValue) == 0)
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 2;
				}
				else
				{
					rSSpeed = Functions.firstTurnSpeedForMSlSW(rValue);
					lSSpeed = rSSpeed/AutoConstants.ratioForFirstTurnMSLSW;
					Drivetrain.goStraightLeft(lValue, rValue, AutoConstants.ratioForFirstTurnMSLSW, lSSpeed, rSSpeed, .06);
				}
				break;
			case 2:
				ElevatorLevel.maintainPickUpPosition();
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				System.out.println(lValue);
				if(Functions.secondTurnSpeedForMSlSW(lValue) == 0)
				{
					currentState = 3;
				}
				else
				{
					lSSpeed = Functions.secondTurnSpeedForMSlSW(rValue);
					rSSpeed = lSSpeed/AutoConstants.ratioForSecondTurnMSLSW;
					Drivetrain.goStraightRight(lValue, rValue, AutoConstants.ratioForSecondTurnMSLSW, lSSpeed, rSSpeed, .06);
				}
				break;
			case 3:
				Drivetrain.stop();
				if(ElevatorLevel.reachedSwitch())
				{
					Elevator.stopEleVader();
					Encoders.resetEncoders();
					Timer.delay(.2);
					currentState = 4;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToSwitch(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 4:
				ElevatorLevel.maintainSwitchPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.straightForSwitchMSLSW))
				{
					Drivetrain.driveForw(lValue, rValue, .7);
				}
				else
				{
					Drivetrain.stop();
					Timer.delay(.5);
					currentState = 5;
				}
				break;
			case 5:		
				Intake.shootCube();
				Drivetrain.stop();
				break;
		}
	}
	
	public static void rightScale(double lValue, double rValue)
	{
		switch(currentState)
		{
			//Check after curves: 36 in at the end of all the turns
			//At end around 140 inches
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
					Intake.stopIntake();
					Elevator.stopEleVader();
					currentState = 2;
				}
				else
				{
					Elevator.moveEleVader(.5);
				}
				break;
			case 2:
				ElevatorLevel.maintainPickUpPosition();
				avg = (Math.abs(lValue) + Math.abs(rValue))/2.0;
				if(avg < 23520)
				{
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
					else if(avg < 20000)
					{
						speed = .85;
					}
					else
					{
						speed = .6;
					}
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else
				{
					currentState = 3;
				}
				break;
			case 3:
				Drivetrain.stop();
				ElevatorLevel.maintainPickUpPosition();
				Timer.delay(.34);
				ElevatorLevel.maintainPickUpPosition();
				Encoders.resetEncoders();
				Timer.delay(.2);
				ElevatorLevel.maintainPickUpPosition();
				currentState = 4;
				break;
			case 4:
				ElevatorLevel.maintainPickUpPosition();
				avg = (Math.abs(lValue) + Math.abs(rValue))/2.0;
				if(avg < 2300)
				{
					Drivetrain.turnLeft(lValue, rValue);
				}
				else
				{
					Drivetrain.stop();
					Timer.delay(.3);
					currentState = 5;
				}
				break;
			case 5:
				if(ElevatorLevel.reachedScale())
				{
					ElevatorLevel.maintainScalePosition();
					Timer.delay(.2);
					currentState = 6;
				}
				else
				{
					Elevator.moveEleVader(Functions.pickUpToScale(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 6:
				ElevatorLevel.maintainScalePosition();
				Intake.shootCube();
				break;
		}
		
	}
	
	public static void rightSwicth(double lValue, double rValue)
	{
		switch(currentState)
		{
			//Check after curves: 36 in at the end of all the turns
			//At end around 140 inches
			case 0:
				prevLeftEncoder = 0;
				prevRightEncoder = 0;
				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop() && ElevatorLevel.elevatorEncoderValue == 0)
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
					Intake.pickUpCube();
					currentState = 2;
				}
				else
				{
					Elevator.moveEleVader(.3);
				}
				break;
			case 2:
				ElevatorLevel.maintainPickUpPosition();
				avg = (Math.abs(lValue) + Math.abs(rValue))/2.0;
				if(!Drivetrain.reachedDistance(lValue, rValue, 10000))
				{
					avg = (lValue + rValue)/2.0;
					speed = .7;
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else if(!Drivetrain.reachedDistance(lValue, rValue, 11760))
				{
					avg = (lValue + rValue)/2.0;
					speed = .4;
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else
				{
					currentState = 3;
				}
				break;
			case 3:
				Drivetrain.stop();
				ElevatorLevel.maintainPickUpPosition();
				Timer.delay(.34);
				ElevatorLevel.maintainPickUpPosition();
				Encoders.resetEncoders();
				Timer.delay(.2);
				ElevatorLevel.maintainPickUpPosition();
				currentState = 4;
				break;
			case 4:
				ElevatorLevel.maintainPickUpPosition();
				avg = (Math.abs(lValue) + Math.abs(rValue))/2.0;
				if(avg < 2500)
				{
					Drivetrain.turnLeft(lValue, rValue);
				}
				else
				{
					Drivetrain.stop();
					Timer.delay(.3);
					currentState = 5;
				}
				break;
			case 5:
				if(ElevatorLevel.reachedSwitch())
				{
					ElevatorLevel.maintainSwitchPosition();
					Encoders.resetEncoders();
					Timer.delay(.2);
					currentState = 6;
				}
				else
				{
					Elevator.moveEleVader(Functions.pickUpToSwitch(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 6:
				if(rValue < 1600)
				{
					ElevatorLevel.maintainSwitchPosition();
					Drivetrain.driveForw(lValue, rValue, .5);
				}
				else
				{
					ElevatorLevel.maintainSwitchPosition();
					Intake.shootCube();
					Drivetrain.stop();
					
				}
				
				break;
		}
	}
	
	public static void middleSideRightAuto(double lValue, double rValue)//switch
	{
		switch(currentState)
		{
			case 0:
				yes.start();
				currentState = 100;
				break;
			case 100:
				time = yes.getDuration();
				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop() && ElevatorLevel.elevatorEncoderValue == 0)
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 99;
				}
				else if(lValue == 0 && rValue == 0 && time >=2)
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					Timer.delay(.2);
					currentState = 99;
				}
				else
				{
					Encoders.resetEncoders();
					Elevator.moveEleVader(-.2);
				}
				break;
			case 99:
				if(ElevatorLevel.reachedPickUp())
				{
					Elevator.stopEleVader();
					Intake.runIntake(0, .3);
					currentState = 1;
				}
				else
				{
					Elevator.moveEleVader(.3);
				}
				break;
			case 1:
				ElevatorLevel.maintainPickUpPosition();
				if(Functions.firstTurnSpeedForMSRSW(lValue) == 0)
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 2;
				}
				else
				{
					lSSpeed = Functions.firstTurnSpeedForMSRSW(lValue);
					rSSpeed = lSSpeed/AutoConstants.ratioForFirstTurnMSRSW;
					Drivetrain.goStraightRight(lValue, rValue, AutoConstants.ratioForFirstTurnMSRSW, lSSpeed, rSSpeed, .06);
				}
				break;
			case 2:
				ElevatorLevel.maintainPickUpPosition();
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				if(rValue < AutoConstants.secondBigOneWheelCurveMSRSW)
				{
					Drivetrain.tankDrive(0, Functions.secondTurnSpeedForMSRSW(rValue));
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 3;
				}
				break;
			case 3:
				Drivetrain.stop();
				if(ElevatorLevel.reachedSwitch())
				{
					Elevator.stopEleVader();
					Encoders.resetEncoders();
					Timer.delay(.2);
					currentState = 4;
				}
				else
				{
					Elevator.moveEleVader(Functions.stopToSwitch(ElevatorLevel.elevatorEncoderValue));
				}
				break;
			case 4:
				ElevatorLevel.maintainSwitchPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.straightForSwitchMSRSW))
				{
					Drivetrain.driveForw(lValue, rValue, .74);
				}
				else
				{
					currentState = 5;
				}
				break;
			case 5:
				Drivetrain.stop();
				Timer.delay(.2);
				Intake.shootCube();
				Timer.delay(1);
				currentState = 5;
				break;
			case 6:
				Drivetrain.stop();
				Intake.stopIntake();
				break;
		}
	}
	
	public static void ll(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
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
					currentState = 2;
				}
				else
				{
					Elevator.moveEleVader(.5);
				}
				break;
			case 2:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.llStraightBeforeTurn - 1600))
				{
					avg = (lValue + rValue)/2.0;
					Drivetrain.driveForw(lValue, rValue, Functions.llStraightBeforeTurn(avg));
				}
				else
				{
					currentState = 3;
				}
				break;
			case 3:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.llStraightBeforeTurn))
				{
					avg = (lValue + rValue)/2.0;
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
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(Functions.llFirstTurn(rValue) != 0)
				{
					rSSpeed = Functions.llFirstTurn(rValue);
					lSSpeed = rSSpeed/AutoConstants.llFirstTurnRatio;
					Drivetrain.goStraightLeft(lValue, rValue, AutoConstants.llFirstTurnRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 5;
				}
				break;
			case 5:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.llcrossFieldDist - 1600))
				{
					avg = (lValue + rValue)/2.0;
					Drivetrain.driveForw(lValue, rValue, Functions.llcrossFieldDist(avg));
				}
				else
				{
					currentState = 6;
				}
				break;
			case 6:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.llStraightBeforeTurn))
				{
					avg = (lValue + rValue)/2.0;
					Drivetrain.driveForw(lValue, rValue, .2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 7;
				}
				break;
			case 7:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(Functions.llFirstTurnToScale(rValue) != 0)
				{
					lSSpeed = Functions.llFirstTurnToScale(lValue);
					rSSpeed = lSSpeed/AutoConstants.llFirstTurnToScaleRatio;
					Drivetrain.goStraightRight(lValue, rValue, AutoConstants.llFirstTurnToScaleRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 8;
				}
				break;
			case 8:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.llStraightBeforeSecond - 1600))
				{
					Drivetrain.driveForw(lValue, rValue, .74);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 9;
				}
				break;
			case 9:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.llStraightBeforeSecond))
				{
					Drivetrain.driveForw(lValue, rValue, .2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 10;
				}
				break;
			case 10:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(Functions.llSecondTurnToScale(rValue) != 0)
				{
					lSSpeed = Functions.llSecondTurnToScale(lValue);
					rSSpeed = lSSpeed/AutoConstants.llSecondTurnToScaleRatio;
					Drivetrain.goStraightRight(lValue, rValue, AutoConstants.llSecondTurnToScaleRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 11;
				}
				break;
			case 11:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.llStraightToScale - 1600))
				{
					Drivetrain.driveForw(lValue, rValue, .74);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 12;
				}
				break;
			case 12:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.llStraightToScale))
				{
					Drivetrain.driveForw(lValue, rValue, .2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 13;
				}
				break;
			case 13:
				Drivetrain.stop();
				Timer.delay(2);
				currentState = 14;
				break;
			case 14:
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.llBackUpAfterScale - 1600))
				{
					Drivetrain.driveBack(lValue, rValue, -.74);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 15;
				}
				break;
			case 15:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.llBackUpAfterScale))
				{
					Drivetrain.driveBack(lValue, rValue, -.2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 16;
				}
				break;
			case 16:
				Drivetrain.stop();
				Timer.delay(.2);
				Encoders.resetEncoders();
				Timer.delay(.1);
				currentState = 17;
				break;
			case 17:
				ElevatorLevel.maintainPickUpPosition();
				if(Functions.llFirstTurnAfterScale(lValue) != 0)
				{
					lSSpeed = Functions.llFirstTurnAfterScale(lValue);
					rSSpeed = lSSpeed/AutoConstants.llFirstTurnAfterScaleRatio;
					Drivetrain.goStraightRight(lValue, rValue, AutoConstants.llFirstTurnAfterScaleRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 18;
				}
				break;
			case 18:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.llFirstStraightAfterScale - 1600))
				{
					Drivetrain.driveForw(lValue, rValue, .74);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 19;
				}
				break;
			case 19:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.llFirstStraightAfterScale))
				{
					Drivetrain.driveForw(lValue, rValue, .2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 20;
				}
				break;
			case 20:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(Functions.llSecondTurnAfterScale(rValue) != 0)
				{
					rSSpeed = Functions.llSecondTurnAfterScale(rValue);
					lSSpeed = rSSpeed/AutoConstants.llSecondTurnAfterScaleRatio;
					Drivetrain.goStraightLeft(lValue, rValue, AutoConstants.llSecondTurnAfterScaleRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 21;
				}
				break;
			case 21:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.llStraightBeforeSwitch))
				{
					Drivetrain.driveForw(lValue, rValue, .3);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 22;
				}
				break;
			case 22:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(Functions.llPickUpCubeTurn(lValue) != 0)
				{
					lSSpeed = Functions.llPickUpCubeTurn(lValue);
					rSSpeed = lSSpeed/AutoConstants.llPickUpCubeTurnRatio;
					Drivetrain.goStraightRight(lValue, rValue, AutoConstants.llPickUpCubeTurnRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 23;
				}
				break;
			case 23:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				Intake.pickUpCube();
				intakeMechanism.openIntake();
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.llPickUpCubeStraight))
				{
					Drivetrain.driveForw(lValue, rValue, .6);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 24;
				}
				break;
			case 24:
				intakeMechanism.closeIntake();
				Intake.stopIntake();
				Elevator.stopEleVader();
				Drivetrain.stop();
				break;
		}
	}

	public static void rl(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
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
					currentState = 2;
				}
				else
				{
					Elevator.moveEleVader(.5);
				}
				break;
			case 2:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlStraightBeforeTurn - 1600))
				{
					avg = (lValue + rValue)/2.0;
					Drivetrain.driveForw(lValue, rValue, Functions.rlStraightBeforeTurn(avg));
				}
				else
				{
					currentState = 3;
				}
				break;
			case 3:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlStraightBeforeTurn))
				{
					avg = (lValue + rValue)/2.0;
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
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(Functions.rlFirstTurn(rValue) != 0)
				{
					rSSpeed = Functions.rlFirstTurn(rValue);
					lSSpeed = rSSpeed/AutoConstants.rlFirstTurnRatio;
					Drivetrain.goStraightLeft(lValue, rValue, AutoConstants.rlFirstTurnRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 5;
				}
				break;
			case 5:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlcrossFieldDist - 1600))
				{
					avg = (lValue + rValue)/2.0;
					Drivetrain.driveForw(lValue, rValue, Functions.rlcrossFieldDist(avg));
				}
				else
				{
					currentState = 6;
				}
				break;
			case 6:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlStraightBeforeTurn))
				{
					avg = (lValue + rValue)/2.0;
					Drivetrain.driveForw(lValue, rValue, .2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 7;
				}
				break;
			case 7:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(Functions.rlFirstTurnToScale(rValue) != 0)
				{
					lSSpeed = Functions.rlFirstTurnToScale(lValue);
					rSSpeed = lSSpeed/AutoConstants.rlFirstTurnToScaleRatio;
					Drivetrain.goStraightRight(lValue, rValue, AutoConstants.rlFirstTurnToScaleRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 8;
				}
				break;
			case 8:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlStraightBeforeSecond - 1600))
				{
					Drivetrain.driveForw(lValue, rValue, .74);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 9;
				}
				break;
			case 9:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlStraightBeforeSecond))
				{
					Drivetrain.driveForw(lValue, rValue, .2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 10;
				}
				break;
			case 10:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(Functions.rlSecondTurnToScale(rValue) != 0)
				{
					lSSpeed = Functions.rlSecondTurnToScale(lValue);
					rSSpeed = lSSpeed/AutoConstants.rlSecondTurnToScaleRatio;
					Drivetrain.goStraightRight(lValue, rValue, AutoConstants.rlSecondTurnToScaleRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 11;
				}
				break;
			case 11:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlStraightToScale - 1600))
				{
					Drivetrain.driveForw(lValue, rValue, .74);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 12;
				}
				break;
			case 12:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlStraightToScale))
				{
					Drivetrain.driveForw(lValue, rValue, .2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 13;
				}
				break;
			case 13:
				Drivetrain.stop();
				Timer.delay(2);
				currentState = 14;
				break;
			case 14:
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlBackUpAfterScale - 1600))
				{
					Drivetrain.driveBack(lValue, rValue, -.74);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 15;
				}
				break;
			case 15:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlBackUpAfterScale))
				{
					Drivetrain.driveBack(lValue, rValue, -.2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 16;
				}
				break;
			case 16:
				Drivetrain.stop();
				Timer.delay(.2);
				Encoders.resetEncoders();
				Timer.delay(.1);
				currentState = 17;
				break;
			case 17:
				ElevatorLevel.maintainPickUpPosition();
				if(Functions.rlFirstTurnAfterScale(lValue) != 0)
				{
					lSSpeed = Functions.rlFirstTurnAfterScale(lValue);
					rSSpeed = lSSpeed/AutoConstants.rlFirstTurnAfterScaleRatio;
					Drivetrain.goStraightRight(lValue, rValue, AutoConstants.rlFirstTurnAfterScaleRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 18;
				}
				break;
			case 18:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlFirstStraightAfterScale - 1600))
				{
					Drivetrain.driveForw(lValue, rValue, .74);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 19;
				}
				break;
			case 19:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlFirstStraightAfterScale))
				{
					Drivetrain.driveForw(lValue, rValue, .2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 20;
				}
				break;
			case 20:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(Functions.rlSecondTurnAfterScale(rValue) != 0)
				{
					rSSpeed = Functions.rlSecondTurnAfterScale(rValue);
					lSSpeed = rSSpeed/AutoConstants.rlSecondTurnAfterScaleRatio;
					Drivetrain.goStraightLeft(lValue, rValue, AutoConstants.rlSecondTurnAfterScaleRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 21;
				}
				break;
			case 21:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlStraightBeforeSwitch - 1600))
				{
					avg = (lValue + rValue)/2.0;
					Drivetrain.driveForw(lValue, rValue, Functions.rlStraightBeforeSwitch(avg));
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 22;
				}
				break;
			case 22:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlStraightBeforeSwitch))
				{
					Drivetrain.driveForw(lValue, rValue, .2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 23;
				}
				break;
			case 23:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(Functions.rlPickUpCubeTurn(lValue) != 0)
				{
					lSSpeed = Functions.rlPickUpCubeTurn(lValue);
					rSSpeed = lSSpeed/AutoConstants.rlPickUpCubeTurnRatio;
					Drivetrain.goStraightRight(lValue, rValue, AutoConstants.rlPickUpCubeTurnRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 23;
				}
				break;
			case 24:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				Intake.pickUpCube();
				intakeMechanism.openIntake();
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlPickUpCubeStraight))
				{
					Drivetrain.driveForw(lValue, rValue, .6);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 24;
				}
				break;
			case 25:
				intakeMechanism.closeIntake();
				Intake.stopIntake();
				Elevator.stopEleVader();
				Drivetrain.stop();
				break;
		}
	}
	
	//Test Functions
	public static void test2ForSameSide(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
				{
					Elevator.stopEleVader();
					ElevatorLevel.resetElevatorEncoders();
					currentState = 6;
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
					currentState = 6;
				}
				else
				{
					Elevator.moveEleVader(.5);
				}
				break;
			case 2:
				ElevatorLevel.maintainPickUpPosition();
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rrBackTurnAfterSwitch - 1500))
				{
					Drivetrain.driveBack(lValue, rValue, -.8);
				}
				else
				{
					currentState = 3;
				}
				break;
			case 3:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rrBackTurnAfterSwitch))
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
				ElevatorLevel.maintainPickUpPosition();
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				rSSpeed = 0;
				lValue = Math.abs(lValue);
				if(lValue < AutoConstants.rrbackUpToWallTurnDist)
				{
					lSSpeed = -.6;
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
				Timer.delay(.3);
				Encoders.resetEncoders();
				Timer.delay(.2);
				currentState = 9;
				break;
			case 6:
				//ElevatorLevel.maintainPickUpPosition();
				Encoders.testEncoders();
				if(!Drivetrain.reachedDistance(lValue, rValue, 3000))
				{
					Drivetrain.driveForw(lValue, rValue, .8);
				}
				{
					currentState = 8;
				}
				break;
			case 7:
				//ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rrStraightToCube))
				{
					speed = .2;
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 8;
				}
				break;
			case 8:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				//ElevatorLevel.maintainPickUpPosition();
				Intake.pickUpCube();
				intakeMechanism.openIntake();
				if(Functions.rrPickUpCube(rValue) != 0)
				{
					rSSpeed = Functions.rrPickUpCube(rValue);
					lSSpeed = rSSpeed/AutoConstants.rrCubeTurnRatio;
					Drivetrain.goStraightLeft(lValue, rValue, AutoConstants.rrCubeTurnRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 10;
				}
				break;
			case 9:
				ElevatorLevel.maintainPickUpPosition();
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				Intake.pickUpCube();
				intakeMechanism.openIntake();
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlPickUpCubeStraight))
				{
					Drivetrain.driveForw(lValue, rValue, .6);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 10;
				}
				break;
			case 10:
				intakeMechanism.closeIntake();
				Intake.stopIntake();
				Elevator.stopEleVader();
				Drivetrain.stop();
				System.out.println("L");
				break;
		}
	}
	
	public static void test3ForSameSide(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
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
					currentState = 2;
				}
				else
				{
					Elevator.moveEleVader(.5);
				}
				break;
			case 2:
				ElevatorLevel.maintainPickUpPosition();
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rrBackTurnAfterSwitch - 1500))
				{
					Drivetrain.driveBack(lValue, rValue, -.8);
				}
				else
				{
					currentState = 3;
				}
				break;
			case 3:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rrBackTurnAfterSwitch))
				{
					Drivetrain.driveBack(lValue, rValue, -.2);
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
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				rSSpeed = 0;
				lValue = Math.abs(lValue);
				if(lValue < AutoConstants.lrbackUpToWallTurnDist)
				{
					lSSpeed = -.6;
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
				Timer.delay(.3);
				Encoders.resetEncoders();
				Timer.delay(.2);
				currentState = 6;
				break;
			case 6:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.lrStraightToCube - 1600))
				{
					Drivetrain.driveForw(lValue, rValue, .8);
				}
				else
				{
					currentState = 7;
				}
				break;
			case 7:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.lrStraightToCube))
				{
					speed = .2;
					Drivetrain.driveForw(lValue, rValue, speed);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 8;
				}
				break;
			case 8:
				ElevatorLevel.maintainPickUpPosition();
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				Intake.pickUpCube();
				intakeMechanism.openIntake();
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.lrPickUpCubeStraight))
				{
					Drivetrain.driveForw(lValue, rValue, .6);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 9;
				}
				break;
			case 9:
				intakeMechanism.closeIntake();
				Intake.stopIntake();
				Elevator.stopEleVader();
				Drivetrain.stop();
				break;
		}
	}
	
	public static void test1ForOppositeSide(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
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
					currentState = 2;
				}
				else
				{
					Elevator.moveEleVader(.5);
				}
				break;
			case 2:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.llStraightBeforeTurn - 1600))
				{
					avg = (lValue + rValue)/2.0;
					Drivetrain.driveForw(lValue, rValue, Functions.llStraightBeforeTurn(avg));
				}
				else
				{
					currentState = 3;
				}
				break;
			case 3:
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.llStraightBeforeTurn))
				{
					avg = (lValue + rValue)/2.0;
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
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(Functions.llFirstTurn(rValue) != 0)
				{
					rSSpeed = Functions.llFirstTurn(rValue);
					lSSpeed = rSSpeed/AutoConstants.llFirstTurnRatio;
					Drivetrain.goStraightLeft(lValue, rValue, AutoConstants.llFirstTurnRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 5;
				}
				break;
			case 5:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, 5000))
				{
					avg = (lValue + rValue)/2.0;
					Drivetrain.driveForw(lValue, rValue, .8);
				}
				else
				{
					currentState = 6;
				}
				break;
			case 6:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, 6500))
				{
					avg = (lValue + rValue)/2.0;
					Drivetrain.driveForw(lValue, rValue, .2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 7;
				}
				break;
			case 7:
				intakeMechanism.closeIntake();
				Intake.stopIntake();
				Elevator.stopEleVader();
				Drivetrain.stop();
				break;
		}
	}
	
	public static void test2ForOppositeSide(double lValue, double rValue)
	{
		switch(currentState)
		{
			case 0:
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
					currentState = 5;
				}
				else
				{
					Elevator.moveEleVader(.5);
				}
				break;
			case 5:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlcrossFieldDist - 1600))
				{
					avg = (lValue + rValue)/2.0;
					Drivetrain.driveForw(lValue, rValue, Functions.rlcrossFieldDist(avg));
				}
				else
				{
					currentState = 6;
				}
				break;
			case 6:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlStraightBeforeTurn))
				{
					avg = (lValue + rValue)/2.0;
					Drivetrain.driveForw(lValue, rValue, .2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 7;
				}
				break;
			case 7:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(Functions.rlFirstTurnToScale(rValue) != 0)
				{
					lSSpeed = Functions.rlFirstTurnToScale(lValue);
					rSSpeed = lSSpeed/AutoConstants.rlFirstTurnToScaleRatio;
					Drivetrain.goStraightRight(lValue, rValue, AutoConstants.rlFirstTurnToScaleRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 8;
				}
				break;
			case 8:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlStraightBeforeSecond - 1600))
				{
					Drivetrain.driveForw(lValue, rValue, .74);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 9;
				}
				break;
			case 9:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlStraightBeforeSecond))
				{
					Drivetrain.driveForw(lValue, rValue, .2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 10;
				}
				break;
			case 10:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(Functions.rlSecondTurnToScale(rValue) != 0)
				{
					lSSpeed = Functions.rlSecondTurnToScale(lValue);
					rSSpeed = lSSpeed/AutoConstants.rlSecondTurnToScaleRatio;
					Drivetrain.goStraightRight(lValue, rValue, AutoConstants.rlSecondTurnToScaleRatio, lSSpeed, rSSpeed, .06);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 11;
				}
				break;
			case 11:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlStraightToScale - 1600))
				{
					Drivetrain.driveForw(lValue, rValue, .74);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 12;
				}
				break;
			case 12:
				lValue-=prevLeftEncoder;
				rValue-=prevRightEncoder;
				ElevatorLevel.maintainPickUpPosition();
				if(!Drivetrain.reachedDistance(lValue, rValue, AutoConstants.rlStraightToScale))
				{
					Drivetrain.driveForw(lValue, rValue, .2);
				}
				else
				{
					prevLeftEncoder = lValue;
					prevRightEncoder = rValue;
					currentState = 13;
				}
				break;
		}
	}
	
	public static void initialize()
	{
		Drivetrain.stop();
		Forks.notForks();
		Shifter.Shifted();
		intakeMechanism.closeIntake();
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
