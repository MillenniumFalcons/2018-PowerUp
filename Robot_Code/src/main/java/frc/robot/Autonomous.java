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

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DriverStation;

public class Autonomous 
{
	//Timer-Stuff
	public static Timer stopWatch = new Timer();
	static double time;
	
	//Other variables for auto
	static double prevLeftEncoder, prevRightEncoder;
	static int currentState;
	static double lSSpeed, rSSpeed, speed, sum, rValue, lValue;
	static int b;

	public static boolean chechWristIdle(double wValue)
	{
		double up = Constants.up;
		if(wValue > up - 50 || wValue < up + 50)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	static double oldLenc, oldRenc, newLenc, newRenc;

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

	public static void doubleSwitch(Encoders enc)
	{
		enc.setEncoderValues();
		switch(currentState)
		{
			case 0:
				//if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0 && stopWatch.get() == 0 && chechWristIdle(Wrist.wristEncoder))
				if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0 && stopWatch.get() == 0)
				{
					stopWatch.start();
					currentState = 2;
				}
				else
				{
					enc.resetEncoders();
					stopWatch.reset();
					//Wrist.wristMotor.getSensorCollection().setQuadraturePosition(Constants.up, 10);
				}
				break;
			case 1:
				//
				Wrist.wristMotor.set(ControlMode.Position, Constants.up);
				if(Elevator.elevatorEncoderValue == 0)
				{
					stopWatch.stop();
					Elevator.stopElevator();
					currentState = 2;
				}
				else if(stopWatch.get() > 1.5)
				{
					stopWatch.stop();
					Elevator.stopElevator();
					currentState = 2;
				}
				else
				{
					Elevator.moveElevator(-.3);
				}
				break;
			case 2:
				
				break;
		}
	}

	//8.88 inches max on x, y min 310 inches
	public static void chezyRightScale(Encoders enc)
	{
		enc.setEncoderValues();
		switch(currentState)
		{
			case 0:
				//if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0 && stopWatch.get() == 0 && chechWristIdle(Wrist.wristEncoder))
				if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0 && stopWatch.get() == 0)
				{
					stopWatch.start();
					currentState = 2;
				}
				else
				{
					enc.resetEncoders();
					stopWatch.reset();
					//Wrist.wristMotor.getSensorCollection().setQuadraturePosition(Constants.up, 10);
				}
				break;
			case 1:
				//
				Wrist.wristMotor.set(ControlMode.Position, Constants.up);
				if(Elevator.elevatorEncoderValue == 0)
				{
					stopWatch.stop();
					Elevator.stopElevator();
					currentState = 2;
				}
				else if(stopWatch.get() > 1.5)
				{
					stopWatch.stop();
					Elevator.stopElevator();
					currentState = 2;
				}
				else
				{
					Elevator.moveElevator(-.3);
				}
				break;
			case 3:
				//wristMotor.set(ControlMode.Position, Constants.up);
				double totalScaleDist = 19000;
				if(enc.rightEncoderValue < 500)
				{
					Drivetrain.setSpeed(.4, .4);
				}
				else if(enc.rightEncoderValue < 1500)
				{
					Drivetrain.setSpeed(.62, .62);
				}
				else if(enc.rightEncoderValue < 10000)
				{
					//Elevator.moveElevatorPosition(Constants.Scale);
					Drivetrain.setSpeed(.9, .9);
				}
				else if(enc.rightEncoderValue < totalScaleDist - 2300)
				{
					//Elevator.moveElevatorPosition(Constants.Scale);
					Drivetrain.setSpeed(.73, .73);
				}
				else if(enc.rightEncoderValue < totalScaleDist)
				{
					//Elevator.moveElevatorPosition(Constants.Scale);
					Drivetrain.setSpeed(.3, .3);
				}
				else
				{
					//Elevator.moveElevatorPosition(Constants.Scale);
					prevLeftEncoder = enc.leftEncoderValue;
					prevRightEncoder = enc.rightEncoderValue;
					currentState = 4;
				}
				break;
			case 4:
				rValue = enc.rightEncoderValue - prevRightEncoder;
				lValue = enc.leftEncoderValue - prevLeftEncoder;
				//Elevator.moveElevatorPosition(Constants.Scale);
				double rotateRightDist = 4000;
				if(rValue < rotateRightDist - 1600)
				{
					Drivetrain.setSpeed(0, .5);
				}
				else if(rValue < rotateRightDist)
				{
					Drivetrain.setSpeed(0, .3);
				}
				else
				{
					Drivetrain.stop();
					currentState = 5;
				}
				break;
			case 5:
				//Elevator.moveElevatorPosition(Constants.Scale);
				if(stopWatch.get() == 0)
				{
					currentState = 6;
					stopWatch.start();
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 6:
				//Elevator.moveElevatorPosition(Constants.Scale);
				if(stopWatch.get() < .4)
				{
					if(Wrist.reachedFlat())
					{
						Wrist.moveWrist(0);
					}
					else
					{
						Wrist.moveWrist(-.3);
					}
				}
				else if(stopWatch.get() < .55)
				{
					IntakeWheels.runIntake(0, 0, true, -.8, -.8, false);
				}
				else
				{
					if(enc.rightEncoderValue == 0 && enc.rightEncoderValue == 0)
					{
						stopWatch.stop();
						currentState = 7;
					}
					else 
					{
						enc.resetEncoders();
					}
				}
				break;
			case 7:
				//Elevator.moveElevatorPosition(Constants.Scale);
				rValue = Math.abs(enc.rightEncoderValue);
				if(rValue < 3000)
				{
					Drivetrain.setSpeed(-.5, -.5);
				}
				else if(rValue < 4000)
				{
					Drivetrain.setSpeed(-.3, -.3);
				}
				else 
				{
					Drivetrain.stop();
					currentState = 8;
				}
				break;
			case 8:
				if(Elevator.reachedBottom())
				{
					Elevator.stopElevator();
				}
				else
				{
					Elevator.moveElevator(-.3);
				}
				break;
		}
	}

	public static void chezyRightSwitch(Encoders enc)
	{
		enc.setEncoderValues();
		switch(currentState)
		{
			case 0:
				if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0)
				{
					currentState = 1;
				}
				else
				{
					enc.resetEncoders();
				}
				break;
			case 1:
				if(enc.leftEncoderValue < 10000 && enc.rightEncoderValue < 10000)
				{
					speed = .7;
				}
				else if(enc.leftEncoderValue < 10500 && enc.rightEncoderValue < 10500 && enc.leftEncoderValue > 10000 && enc.rightEncoderValue > 10000)
				{
					speed = .2;
				}
				else
				{
					currentState = 2;
					enc.resetEncoders();
				}
				break;
			case 2:
				if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0)
				{
					rSSpeed = .2;
					lSSpeed = .7;
				}
				else
				{
					
				}
		}
	}


	public static void testRotate(Encoders encObj)
	{
		encObj.setEncoderValues();
		switch(currentState)
		{
			case 0:
				encObj.setEncoderValues();
				if(encObj.leftEncoderValue == 0 && encObj.rightEncoderValue == 0)
				{
					currentState = 1;
				}
				else
				{
					encObj.resetEncoders();
				}
				
				break;
			case 1:
				encObj.setEncoderValues();
				if(encObj.leftEncoderValue < 6000 || encObj.rightEncoderValue < 6000)
				{
					Drivetrain.tankDrive(.7, .7);
				}
				else if(encObj.leftEncoderValue < 8000 || encObj.rightEncoderValue < 8000)
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
				encObj.setEncoderValues();
				if(encObj.leftEncoderValue == 0 && encObj.rightEncoderValue == 0)
				{
					stopWatch.start();
					currentState = 99;
				}
				else
				{
					encObj.resetEncoders();
				}
				break;
			case 99:
				if(stopWatch.get() > 1)
				{
					currentState = 3;
				}
				break;
			case 3:
				encObj.setEncoderValues();
				System.out.println(encObj.leftEncoderValue);
				if(Math.abs(encObj.leftEncoderValue) < 2000)
				{
					Drivetrain.tankDrive(-.5, .5);
				}
				else if(Math.abs(encObj.leftEncoderValue) < 3000)
				{
					Drivetrain.tankDrive(-.3, .3);
				}
				else
				{
					currentState = 6;
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
					currentState = 6;
					Drivetrain.stop();
				}
				break;
			case 6:
				Drivetrain.stop();
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
				if(time < .5)
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
				IntakeWheels.runIntake(0, 0, true, .12, .12, false);
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
				IntakeWheels.runIntake(0, 0, true, .12, .12, false);
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
					IntakeWheels.runIntake(0, 0, true, -.5, -.5, false);
					Drivetrain.tankDrive(0, 0);
				}
				else 
				{
					IntakeWheels.runIntake(0, 0, true, 0, 0, false);
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
				IntakeWheels.runIntake(0, 0, true, .12, .12, false);
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
				IntakeWheels.runIntake(0, 0, true, .12, .12, false);
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
				IntakeWheels.runIntake(0, 0, true, .12, .12, false);
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
				IntakeWheels.runIntake(0, 0, true, .12, .12, false);
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
	public static void initialize(Encoders enc)
	{
		stopWatch.stop();
		stopWatch.reset();
		Drivetrain.stop();
		Forks.lockTheForks();
		Shifter.lowGear();
		Intake.closeIntake();
		enc.resetEncoders();
		IntakeWheels.runIntake(0, 0, true, 0, 0, false);
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