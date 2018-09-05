package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.team3647ConstantsAndFunctions.Constants;
import frc.team3647ConstantsAndFunctions.Functions;
import frc.team3647elevator.Elevator;
import frc.team3647elevator.IntakeWheels;
import frc.team3647elevator.Wrist;
import frc.team3647pistons.Forks;
import frc.team3647pistons.Intake;
import frc.team3647pistons.Shifter;
import frc.team3647subsystems.Drivetrain;
import frc.team3647subsystems.Encoders;
import edu.wpi.first.wpilibj.DriverStation;

public class Autonomous 
{
	//Timer-Stuff
	public static com.sun.glass.ui.Timer stopWatch = new Timer();
	static double time;
	
	//Other variables for auto
	static double prevLeftEncoder, prevRightEncoder;
	static int currentState;
	static double lSSpeed, rSSpeed, speed, sum;
	static int b;
	
	static double oldLenc, oldRenc, newLenc, oldrEnc;

	static int [] differences = new int[10];

	public static void testEncodersWithoutSkipping(Encoders encObj)
	{
		encObj.setEncoderValues();
		newRenc = encObj.rightEncoderValue;
		newLenc = encObj.leftEncoderValue;
		if(newRenc - oldRenc != 0)
		{
			System.out.println("Right Encoder Skip Value: " + (newRenc - oldRenc));
		}
		if(newLenc - oldLenc != 0)
		{
			System.out.println("Left Encoder Skip Value: " + (newLenc - oldLenc));
		}
		oldRenc = encObj.rightEncoderValue;
		oldLenc = encObj.leftEncoderValue;
	}

	public static void testRotate(Encoders encObj)
	{
		switch(currentState)
		{
			case 0:
				currentState = 1;
				break;
			case 1:
				enc.setEncoderValues();
				if(encObj.leftEncoderValue > 6000 || encObj.rightEncoderValue > 6000)
				{
					Drivetrain.tankDrive(.7, .7);
				}
				else if(encObj.leftEncoderValue > 8000 || encObj.rightEncoderValue > 8000)
				{
					Drivetrain.tankDrive(.3, .3);
				}
				else
				{
				
					Drivetrain.stop();
					currentState = 2;
				}
				break;
			case 2:
				encObj.resetEncoders();
				Timer.delay(1);
				currentState = 3;
				break;
			case 3:
				if(encObj.leftEncoderValue > 2000 || encObj.rightEncoderValue > 2000)
				{
					Drivetrain.tankDrive(-.5, .5);
				}
				else if(encObj.leftEncoderValue > 3000 || encObj.rightEncoderValue > 3000)
				{
					Drivetrain.tankDrive(-.3, .3);
				}
				else
				{
					currentState = 4;
				}
				break;
			case 4:
				encObj.resetEncoders();
				Timer.delay(1);
				currentState = 5;
				break;
			case 5:
				if(encObj.leftEncoderValue > 6000 || encObj.rightEncoderValue > 6000)
				{
					Drivetrain.tankDrive(.7, .7);
				}
				else if(encObj.leftEncoderValue > 8000 || encObj.rightEncoderValue > 8000)
				{
					Drivetrain.tankDrive(.3, .3);
				}
				else
				{
				
					Drivetrain.stop();
				}
				break;
		}
	}

	public static void countSkip(boolean button, Encoders encObj)
	{
		speed = 1;
		switch(currentState)
		{
			case 0:
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 1;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 1:
				time = stopWatch.get();
				if(button)
				{
					currentState = 2; 
				}
				encObj.setEncoderValues();
				newRenc = encObj.rightEncoderValue;
				newLenc = encObj.leftEncoderValue;
				if(time < 1.2)
				{
					Drivetrain.tankDrive(speed, speed);
				}
				else
				{
					Drivetrain.tankDrive(speed, speed);
					if(newRenc - oldRenc != 0)
					{
						System.out.println("Right Encoder Skip Value: " + (newRenc - oldRenc));
					}
					if(newLenc - oldLenc != 0)
					{
						System.out.println("Left Encoder Skip Value: " + (newLenc - oldLenc));
					}
				}
				oldRenc = encObj.rightEncoderValue;
				oldLenc = encObj.leftEncoderValue;
				break;
			case 2:
				Drivetrain.stop();
				break;
			}
	}

	public static void runAuto()
	{
		String gameData;
		gameData = "RLR";
		int priorityForSwitch = 0; //0 if we dont care, any other number means we only go for switch not scale
		boolean cross = false;

		if(cross)
		{
			cross();
		}
		else
		{
			if(gameData.charAt(1) == 'R' && gameData.charAt(0) == 'L')
			{
				doubleScale();
			}
			else if(gameData.charAt(1) == 'R' && gameData.charAt(0) == 'R')
			{
				doubleScale();
			}
			else if(gameData.charAt(1) == 'L' && gameData.charAt(0) == 'R')
			{
				rightSide2Cube();
			}
			else if(gameData.charAt(1) == 'L' && gameData.charAt(0) == 'R')
			{
				leftSide2Cube();
			}
			else
			{
				cross();
			}
		}
	}
	
	// Cheese autos
	
	//212 inches in y, 78.66 inches in x
	public static void rightSide2Cube()
	{
		switch(currentState)
		{
			case 0:
				IntakeWheels.runIntake(0, 0, true, .12, .12);
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 4;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 1:
				time = stopWatch.get();
				if(Elevator.elevatorEncoderValue == 0 && Wrist.wristEncoderValue == 0)
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 3;
					Elevator.stopElevator();
					Wrist.stopWrist();
				}
				else if(time > 1.4)
				{
					Elevator.resetElevatorEncoders();
					Wrist.resetWristEncoder();
					Elevator.stopElevator();
					Wrist.stopWrist();
				}
				else if(time > 1.5)
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 3;
				}
				else
				{
					Elevator.moveElevator(-.3);
					Wrist.moveToFlat();
				}
				break;
			case 99:
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 2;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 2:
				if(Elevator.reachedSwitch())
				{
					currentState = 3;
				}
				else if(stopWatch.get() > 2)
				{
					currentState = 3;
				}
				else 
				{
					Elevator.moveElevatorPosition(Constants.sWitch);
				}
				break;
			case 3:
				Elevator.moveElevatorPosition(Constants.sWitch);
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 4;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 4:
				time = stopWatch.get();
				lSSpeed = Functions.rightSide2Cube(time, false);
				rSSpeed = Functions.rightSide2Cube(time, true);
				Wrist.moveToFlat();
				Drivetrain.tankDrive(lSSpeed, rSSpeed);
				break;
		}
	}

	public static void rightSide1Cube()
	{
		switch(currentState)
		{
			case 0:
				IntakeWheels.runIntake(0, 0, true, .12, .12);
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 4;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 4:
				time = stopWatch.get();
				Wrist.moveToFlat();
				Elevator.moveElevatorPosition(Constants.sWitch);
				if(time < .3)
				{
					speed = .5;
					Drivetrain.tankDrive(speed, speed);
				}
				else if(time < 1.3)
				{
					speed = .68;
					Drivetrain.tankDrive(speed, speed);
				}
				else if(time < 1.5)
				{
					speed = .2;
					Drivetrain.tankDrive(speed, speed);
				}
				else if(time < 2.2)
				{
					Drivetrain.tankDrive(.9, .2);
				}
				else if(time < 2.6)
				{
					IntakeWheels.runIntake(0, 0, true, -.5, -.5);
					Drivetrain.tankDrive(0, 0);
				}
				else 
				{
					IntakeWheels.runIntake(0, 0, true, 0, 0);
					Drivetrain.tankDrive(0, 0);
				}
				break;
		}
	}
	
	public static void leftSide2Cube()
	{
		switch(currentState)
		{
			case 0:
				IntakeWheels.runIntake(0, 0, true, .12, .12);
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 4;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 1:
				time = stopWatch.get();
				if(Elevator.elevatorEncoderValue == 0 && Wrist.wristEncoderValue == 0)
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 3;
					Elevator.stopElevator();
					Wrist.stopWrist();
				}
				else if(time > 1.4)
				{
					Elevator.resetElevatorEncoders();
					Wrist.resetWristEncoder();
					Elevator.stopElevator();
					Wrist.stopWrist();
				}
				else if(time > 1.5)
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 3;
				}
				else
				{
					Elevator.moveElevator(-.3);
					Wrist.moveToFlat();
				}
				break;
			case 99:
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 2;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 2:
				if(Elevator.reachedSwitch())
				{
					currentState = 3;
				}
				else if(stopWatch.get() > 2)
				{
					currentState = 3;
				}
				else 
				{
					Elevator.moveElevatorPosition(Constants.sWitch);
				}
				break;
			case 3:
				Elevator.moveElevatorPosition(Constants.sWitch);
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 4;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 4:
				time = stopWatch.get();
				lSSpeed = Functions.leftSide2Cube(time, false);
				rSSpeed = Functions.leftSide2Cube(time, true);
				Wrist.moveToFlat();
				Drivetrain.tankDrive(lSSpeed, rSSpeed);
				break;
		}
	}

	public static void doubleScale()
	{
		switch(currentState)
		{
			case 0:
				IntakeWheels.runIntake(0, 0, true, .12, .12);
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 4;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 4:
				time = stopWatch.get();
				lSSpeed = Functions.doubleScale(time, false);
				rSSpeed = Functions.doubleScale(time, true);
				Wrist.moveToFlat();
				Drivetrain.tankDrive(lSSpeed, rSSpeed);
				break;
		}
	}

	public static void rightScaleRightSwitch()
	{
		switch(currentState)
		{
			case 0:
				IntakeWheels.runIntake(0, 0, true, .12, .12);
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 4;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 1:
				time = stopWatch.get();
				if(Elevator.elevatorEncoderValue == 0 && Wrist.wristEncoderValue == 0)
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 3;
					Elevator.stopElevator();
					Wrist.stopWrist();
				}
				else if(time > 1.4)
				{
					Elevator.resetElevatorEncoders();
					Wrist.resetWristEncoder();
					Elevator.stopElevator();
					Wrist.stopWrist();
				}
				else if(time > 1.5)
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 3;
				}
				else
				{
					Elevator.moveElevator(-.3);
					Wrist.moveToFlat();
				}
				break;
			case 99:
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 3;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 2:
				if(Elevator.reachedSwitch())
				{
					currentState = 3;
				}
				else if(stopWatch.get() > 2)
				{
					currentState = 3;
				}
				else 
				{
					Elevator.moveElevatorPosition(Constants.sWitch);
				}
				break;
			case 3:
				Elevator.moveElevatorPosition(Constants.sWitch);
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 4;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 4:
				time = stopWatch.get();
				lSSpeed = Functions.rightScaleRightSwitch(time, false);
				rSSpeed = Functions.rightScaleRightSwitch(time, true);
				Drivetrain.tankDrive(lSSpeed, rSSpeed);
				break;
		}
	}

	public static void rightScaleLeftSwitch()
	{
		switch(currentState)
		{
			case 0:
				IntakeWheels.runIntake(0, 0, true, .12, .12);
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 4;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 1:
				time = stopWatch.get();
				if(Elevator.elevatorEncoderValue == 0 && Wrist.wristEncoderValue == 0)
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 3;
					Elevator.stopElevator();
					Wrist.stopWrist();
				}
				else if(time > 1.4)
				{
					Elevator.resetElevatorEncoders();
					Wrist.resetWristEncoder();
					Elevator.stopElevator();
					Wrist.stopWrist();
				}
				else if(time > 1.5)
				{
					stopWatch.stop();
					stopWatch.reset();
					stopWatch.start();
					currentState = 3;
				}
				else
				{
					Elevator.moveElevator(-.3);
					Wrist.moveToFlat();
				}
				break;
			case 99:
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 2;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 2:
				if(Elevator.reachedSwitch())
				{
					currentState = 3;
				}
				else if(stopWatch.get() > 2)
				{
					currentState = 3;
				}
				else 
				{
					Elevator.moveElevatorPosition(Constants.sWitch);
				}
				break;
			case 3:
				Elevator.moveElevatorPosition(Constants.sWitch);
				stopWatch.reset();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 4;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 4:
				time = stopWatch.get();
				lSSpeed = Functions.rightScaleLeftSwitch(time, false);
				rSSpeed = Functions.rightScaleLeftSwitch(time, true);
				Drivetrain.tankDrive(lSSpeed, rSSpeed);
				break;
		}
	}

	public static void cross()
	{
		switch(currentState)
		{
			case 0:
				stopWatch.stop();
				time = stopWatch.get();
				if(time == 0)
				{
					stopWatch.start();
					currentState = 1;
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 1:
				if(stopWatch.get() < 1.8)
				{
					Drivetrain.tankDrive(.7, .7);
				}
				else
				{
					Drivetrain.tankDrive(0, 0);
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
		enc.resetEncoders();
		IntakeWheels.runIntake(0, 0, true, 0, 0);
		Elevator.stopElevator();
		Elevator.aimedElevatorState = 0;
		Drivetrain.setToBrake();
		prevLeftEncoder = 0;
		prevRightEncoder = 0;
		currentState = 0;
		time = 0;
		stopWatch.start();
	}
}