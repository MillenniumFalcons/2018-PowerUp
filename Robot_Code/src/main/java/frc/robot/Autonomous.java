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

public class Autonomous 
{
	//Timer-Stuff
	public static Timer stopWatch = new Timer();
	static double time;
	
	//Other variables for auto
	static double prevLeftEncoder, prevRightEncoder;
	static int currentState;
	static double lSSpeed, rSSpeed, speed, sum, rValue, lValue;
	static double currTime, prevTime;
	static int b;

	public static void moveWristDownWhileRunning()
	{
		if(Wrist.reachedFlat())
		{
			Wrist.moveWrist(0);
		}
		else
		{
			Wrist.moveWrist(-.15);
		}
	}

	public static void intakeCube()
	{
		if(Wrist.reachedFlat())
		{
			Wrist.moveWrist(0);
			Intake.openIntake();
			IntakeWheels.runIntake(0, 0, true, .4, .4, false);
		}
		else 
		{
			Wrist.moveWrist(-.3);
			Intake.openIntake();
		}
	}

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

	//8.88 inches max on x, y min 310 inches
	public static void jankScale(Encoders enc)
	{
		enc.setEncoderValues();
		switch(currentState)
		{
			case 0:
				//if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0 && stopWatch.get() == 0 && chechWristIdle(Wrist.wristEncoder))
				if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0 && stopWatch.get() == 0)
				{
					stopWatch.start();
					currentState = 3;
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
					currentState = 3;
				}
				else if(stopWatch.get() > 1.5)
				{
					stopWatch.stop();
					Elevator.stopElevator();
					currentState = 3;
				}
				else
				{
					Elevator.moveElevator(-.3);
				}
				break;
			case 3:
				stopWatch.stop();//
				double totalScaleDist = 19000;
				if(enc.rightEncoderValue < 500)
				{
					//wristMotor.set(ControlMode.Position, Constants.up);
					Drivetrain.setSpeed(.4, .4);
				}
				else if(enc.rightEncoderValue < 1500)
				{
					//wristMotor.set(ControlMode.Position, Constants.up);
					Drivetrain.setSpeed(.62, .62);
				}
				else if(enc.rightEncoderValue < 10000)
				{
					//Elevator.moveElevatorPosition(Constants.Scale);
					//moveWristDownWhileRunning();
					Drivetrain.setSpeed(.9, .9);
				}
				else if(enc.rightEncoderValue < totalScaleDist - 2300)
				{
					//Elevator.moveElevatorPosition(Constants.Scale);
					//moveWristDownWhileRunning();
					Drivetrain.setSpeed(.73, .73);
				}
				else if(enc.rightEncoderValue < totalScaleDist)
				{
					//Elevator.moveElevatorPosition(Constants.Scale);
					//moveWristDownWhileRunning();
					Drivetrain.setSpeed(.3, .3);
				}
				else
				{
					//Elevator.moveElevatorPosition(Constants.Scale);
					//moveWristDownWhileRunning();
					prevLeftEncoder = enc.leftEncoderValue;
					prevRightEncoder = enc.rightEncoderValue;
					currentState = 4;
				}
				break;
			case 4:
				rValue = enc.rightEncoderValue - prevRightEncoder;
				lValue = enc.leftEncoderValue - prevLeftEncoder;
				//Elevator.moveElevatorPosition(Constants.Scale);
				//moveWristDownWhileRunning();
				double rotateRightDist = 3000;
				if(rValue < rotateRightDist - 1300)
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
				stopWatch.stop();
				//Elevator.moveElevatorPosition(Constants.Scale);
				if(stopWatch.get() == 0 && enc.rightEncoderValue == 0)
				{
					currentState = 6;
					stopWatch.start();
				}
				else
				{
					stopWatch.reset();
					enc.resetEncoders();
				}
				break;
			case 6:
				//Elevator.moveElevatorPosition(Constants.Scale);
				if(stopWatch.get() < .1)
				{
					
				}
				else if(stopWatch.get() < .6)
				{
					IntakeWheels.runIntake(0, 0, true, -1, -1, false);
					enc.resetEncoders();
				}
				else 
				{
					currentState = 7;
				}
				break;
			case 7:
				double spinDist = 3000;
				if(enc.rightEncoderValue < spinDist - 1000)
				{
					Drivetrain.setSpeed(-.5, .5);
					//Elevator.moveElevatorPosition(Constants.Scale);
				}
				else if(enc.rightEncoderValue < spinDist)
				{
					Drivetrain.setSpeed(-.25, .25);
					//Elevator.moveBottom(false);
				}
				else
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 8;
				}
				break;
			case 8:
				currTime = stopWatch.get() - prevTime;
				double straightDistForCube = 5000;
				if(currTime < .35)
				{
					//Elevator.moveBottom(false);
				}
				else 
				{
					currentState = 9;
				}
				break;
		}
	}

	public static void chezyDoubleSwitchRightFromRight(Encoders enc)
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
				//Wrist.wristMotor.set(ControlMode.Position, Constants.up);
				double straightDist = 10000;
				if(enc.rightEncoderValue < 500)
				{
					//wristMotor.set(ControlMode.Position, Constants.up);
					Drivetrain.setSpeed(.4, .4);
				}
				else if(enc.rightEncoderValue < 1500)
				{
					//wristMotor.set(ControlMode.Position, Constants.up);
					Drivetrain.setSpeed(.62, .62);
				}
				else if(enc.rightEncoderValue < straightDist - 3000)
				{
					//Elevator.moveElevatorPosition(Constants.Switch);
					//moveWristDownWhileRunning();
					Drivetrain.setSpeed(.8, .8);
				}
				else if(enc.rightEncoderValue < straightDist)
				{
					//Elevator.moveElevatorPosition(Constants.Switch);
					//moveWristDownWhileRunning();
					Drivetrain.setSpeed(.3, .3);
				}
				else 
				{
					prevRightEncoder = enc.rightEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				double firstTurn = 3000;
				double straightForAWhile = 2000;
				double secondTurn = 3200;
				//Elevator.moveElevatorPosition(Constants.Switch);
				//moveWristDownWhileRunning();
				rValue = enc.rightEncoderValue - prevRightEncoder;
				if(rValue < firstTurn - 1000)
				{
					Drivetrain.setSpeed(0, .42);
				}
				else if(rValue < firstTurn)
				{
					Drivetrain.setSpeed(0, .3);
				}
				else if(rValue < firstTurn + straightForAWhile)
				{
					Drivetrain.setSpeed(.35, .35);
				}
				else if(rValue < firstTurn + straightForAWhile + secondTurn)
				{
					Drivetrain.setSpeed(0, .3);
				}
				else 
				{
					Drivetrain.stop();
					currentState = 4;
				}
				break;
			case 4:
				stopWatch.stop();
				//Elevator.moveElevatorPosition(Constants.Switch);
				if(stopWatch.get() == 0)
				{
					currentState = 5;
					stopWatch.start();
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 5:
				//Elevator.moveElevatorPosition(Constants.Switch);
				if(stopWatch.get() < .6)
				{
					IntakeWheels.runIntake(0, 0, true, -.8, -.8, false);
				}
				else if(enc.rightEncoderValue != 0)
				{
					enc.resetEncoders();
				}
				else 
				{
					stopWatch.stop();
					stopWatch.reset();
					currentState = 6;
				}
				break;
			case 6:
				double backUpDist = 2000;
				Elevator.moveElevator(-.3);
				rValue = Math.abs(enc.rightEncoderValue);
				if(rValue < backUpDist)
				{
					Drivetrain.setSpeed(-.35, -.35);
				}
				else 
				{
					Drivetrain.stop();
					currentState = 7;
				}
				break;
			case 7:
				stopWatch.stop();
				//Elevator.moveBottom(false);
				if(stopWatch.get() == 0)
				{
					prevRightEncoder = enc.rightEncoderValue;
					currentState = 8;
					stopWatch.start();
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 8:
				if(stopWatch.get() < .6)
				{
					//Elevator.moveBottom(false);
				}
				else 
				{
					stopWatch.stop();
					currentState = 9;
				}
				break;
			case 9:
				stopWatch.stop();
				Wrist.moveWrist(-.3);
				double deliverDist = 3000;
				rValue = enc.rightEncoderValue - prevRightEncoder;
				if(rValue < backUpDist)
				{
					Drivetrain.setSpeed(.4, .4);
					intakeCube();
				}
				else if(rValue > backUpDist && stopWatch.get() != 0)
				{
					intakeCube();
					Drivetrain.stop();
					stopWatch.reset();
				}
				else 
				{
					intakeCube();
					Drivetrain.stop();
					currentState = 10;
					stopWatch.start();
				}
				break;
			case 10:
				if(stopWatch.get() < .2)
				{
					intakeCube();
					enc.resetEncoders();
				}
				else if(stopWatch.get() < .4)
				{
					Intake.closeIntake();
					IntakeWheels.runIntake(0, 0, true, .25, .25, false);
					enc.resetEncoders();
				}
				else 
				{
					IntakeWheels.runIntake(0, 0, true, .12, .12, false);
					stopWatch.stop();
					currentState = 11;
				}
				break;
			case 11:
				rValue = Math.abs(enc.rightEncoderValue);
				if(enc.rightEncoderValue < 2000)
				{
					Drivetrain.setSpeed(-.35, -.35);
				}
				else 
				{
					Drivetrain.stop();
					currentState = 12;
				}
				break;
			case 12:
				stopWatch.stop();
				if(stopWatch.get() == 0)
				{
					currentState = 13;
					stopWatch.start();
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 13:
				//Elevator.moveElevatorPosition(Constants.Switch);
				if(stopWatch.get() > 1 && enc.rightEncoderValue == 0)
				{
					stopWatch.stop();
					currentState = 14;
				}
				else 
				{
					enc.resetEncoders();
				}
				break;
			case 14:
				//Elevator.moveElevatorPosition(Constants.Switch);
				if(enc.rightEncoderValue < 2000)
				{
					Drivetrain.setSpeed(.35, .35);
				}
				else if(enc.rightEncoderValue > 2000)
				{
					Drivetrain.stop();
					IntakeWheels.runIntake(0, 0, true, -.8, -.8, false);
				}
				break;
		}
	}

	public static void chezyDoubleSwitchLeftFromRight(Encoders enc)
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
				//Wrist.wristMotor.set(ControlMode.Position, Constants.up);
				double straightDist = 10000;
				if(enc.rightEncoderValue < 500)
				{
					//wristMotor.set(ControlMode.Position, Constants.up);
					Drivetrain.setSpeed(.4, .4);
				}
				else if(enc.rightEncoderValue < 1500)
				{
					//wristMotor.set(ControlMode.Position, Constants.up);
					Drivetrain.setSpeed(.62, .62);
				}
				else if(enc.rightEncoderValue < straightDist - 3000)
				{
					//Elevator.moveElevatorPosition(Constants.Switch);
					//moveWristDownWhileRunning();
					Drivetrain.setSpeed(.8, .8);
				}
				else if(enc.rightEncoderValue < straightDist)
				{
					//Elevator.moveElevatorPosition(Constants.Switch);
					//moveWristDownWhileRunning();
					Drivetrain.setSpeed(.3, .3);
				}
				else 
				{
					prevRightEncoder = enc.rightEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				double firstTurn = 3000;
				double straightForAWhile = 2000;
				double secondTurn = 3200;
				//Elevator.moveElevatorPosition(Constants.Switch);
				//moveWristDownWhileRunning();
				rValue = enc.rightEncoderValue - prevRightEncoder;
				if(rValue < firstTurn - 1000)
				{
					Drivetrain.setSpeed(0, .42);
				}
				else if(rValue < firstTurn)
				{
					Drivetrain.setSpeed(0, .3);
				}
				else if(rValue < firstTurn + 2000)
				{
					Drivetrain.setSpeed(.35, .35);
				}
				else if(rValue < firstTurn + straightForAWhile - 2000)
				{
					Drivetrain.setSpeed(.74, .74);
				}
				else if(rValue < firstTurn + straightForAWhile)
				{
					Drivetrain.setSpeed(.3, .3);
				}
				else if(rValue < firstTurn + straightForAWhile + secondTurn)
				{
					Drivetrain.setSpeed(0, .3);
				}
				else 
				{
					Drivetrain.stop();
					currentState = 4;
				}
				break;
			case 4:
				stopWatch.stop();
				//Elevator.moveElevatorPosition(Constants.Switch);
				if(stopWatch.get() == 0)
				{
					currentState = 5;
					stopWatch.start();
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 5:
				//Elevator.moveElevatorPosition(Constants.Switch);
				if(stopWatch.get() < .6)
				{
					IntakeWheels.runIntake(0, 0, true, -.8, -.8, false);
				}
				else if(enc.rightEncoderValue != 0)
				{
					enc.resetEncoders();
				}
				else 
				{
					stopWatch.stop();
					stopWatch.reset();
					currentState = 6;
				}
				break;
			case 6:
				double backUpDist = 2000;
				Elevator.moveElevator(-.3);
				rValue = Math.abs(enc.rightEncoderValue);
				if(rValue < backUpDist)
				{
					Drivetrain.setSpeed(-.35, -.35);
				}
				else 
				{
					Drivetrain.stop();
					currentState = 7;
				}
				break;
			case 7:
				stopWatch.stop();
				//Elevator.moveBottom(false);
				if(stopWatch.get() == 0)
				{
					prevRightEncoder = enc.rightEncoderValue;
					currentState = 8;
					stopWatch.start();
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 8:
				if(stopWatch.get() < .6)
				{
					//Elevator.moveBottom(false);
				}
				else 
				{
					stopWatch.stop();
					currentState = 9;
				}
				break;
			case 9:
				stopWatch.stop();
				Wrist.moveWrist(-.3);
				double deliverDist = 3000;
				rValue = enc.rightEncoderValue - prevRightEncoder;
				if(rValue < backUpDist)
				{
					Drivetrain.setSpeed(.4, .4);
					intakeCube();
				}
				else if(rValue > backUpDist && stopWatch.get() != 0)
				{
					intakeCube();
					Drivetrain.stop();
					stopWatch.reset();
				}
				else 
				{
					intakeCube();
					Drivetrain.stop();
					currentState = 10;
					stopWatch.start();
				}
				break;
			case 10:
				if(stopWatch.get() < .2)
				{
					intakeCube();
					enc.resetEncoders();
				}
				else if(stopWatch.get() < .4)
				{
					Intake.closeIntake();
					IntakeWheels.runIntake(0, 0, true, .25, .25, false);
					enc.resetEncoders();
				}
				else 
				{
					IntakeWheels.runIntake(0, 0, true, .12, .12, false);
					stopWatch.stop();
					currentState = 11;
				}
				break;
			case 11:
				rValue = Math.abs(enc.rightEncoderValue);
				if(enc.rightEncoderValue < 2000)
				{
					Drivetrain.setSpeed(-.35, -.35);
				}
				else 
				{
					Drivetrain.stop();
					currentState = 12;
				}
				break;
			case 12:
				stopWatch.stop();
				if(stopWatch.get() == 0)
				{
					currentState = 13;
					stopWatch.start();
				}
				else
				{
					stopWatch.reset();
				}
				break;
			case 13:
				//Elevator.moveElevatorPosition(Constants.Switch);
				if(stopWatch.get() > 1 && enc.rightEncoderValue == 0)
				{
					stopWatch.stop();
					currentState = 14;
				}
				else 
				{
					enc.resetEncoders();
				}
				break;
			case 14:
				//Elevator.moveElevatorPosition(Constants.Switch);
				if(enc.rightEncoderValue < 2000)
				{
					Drivetrain.setSpeed(.35, .35);
				}
				else if(enc.rightEncoderValue > 2000)
				{
					Drivetrain.stop();
					IntakeWheels.runIntake(0, 0, true, -.8, -.8, false);
				}
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
					currentState = 3;
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
					currentState = 3;
				}
				else if(stopWatch.get() > 1.5)
				{
					stopWatch.stop();
					Elevator.stopElevator();
					currentState = 3;
				}
				else
				{
					Elevator.moveElevator(-.3);
				}
				break;
			case 3:
				stopWatch.stop();//
				double totalScaleDist = 19000;
				if(enc.rightEncoderValue < 500)
				{
					//wristMotor.set(ControlMode.Position, Constants.up);
					Drivetrain.setSpeed(.4, .4);
				}
				else if(enc.rightEncoderValue < 1500)
				{
					//wristMotor.set(ControlMode.Position, Constants.up);
					Drivetrain.setSpeed(.62, .62);
				}
				else if(enc.rightEncoderValue < 10000)
				{
					//Elevator.moveElevatorPosition(Constants.Scale);
					//moveWristDownWhileRunning();
					Drivetrain.setSpeed(.9, .9);
				}
				else if(enc.rightEncoderValue < totalScaleDist - 2300)
				{
					//Elevator.moveElevatorPosition(Constants.Scale);
					//moveWristDownWhileRunning();
					Drivetrain.setSpeed(.73, .73);
				}
				else if(enc.rightEncoderValue < totalScaleDist)
				{
					//Elevator.moveElevatorPosition(Constants.Scale);
					//moveWristDownWhileRunning();
					Drivetrain.setSpeed(.3, .3);
				}
				else
				{
					//Elevator.moveElevatorPosition(Constants.Scale);
					//moveWristDownWhileRunning();
					prevLeftEncoder = enc.leftEncoderValue;
					prevRightEncoder = enc.rightEncoderValue;
					currentState = 4;
				}
				break;
			case 4:
				rValue = enc.rightEncoderValue - prevRightEncoder;
				lValue = enc.leftEncoderValue - prevLeftEncoder;
				//Elevator.moveElevatorPosition(Constants.Scale);
				//moveWristDownWhileRunning();
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
				//moveWristDownWhileRunning();
				stopWatch.stop();
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
				//moveWristDownWhileRunning();
				if(stopWatch.get() < .55)
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

	public static void runAuto()
	{
		String gameData;
		gameData = "RLR";
		int priorityForSwitch = 0; //0 if we dont care, any other number means we only go for switch not scale
		boolean cross = false; 
		boolean teamateHasOpponentScale = false;

		if(cross)
		{
			cross();
		}
		else
		{
			if(gameData.charAt(1) == 'R' && gameData.charAt(0) == 'L')
			{
				// doubleScale();
			}
			else if(gameData.charAt(1) == 'R' && gameData.charAt(0) == 'R')
			{
				// doubleScale();
			}
			else if(gameData.charAt(1) == 'L' && gameData.charAt(0) == 'R')
			{
				// rightSide2Cube();
			}
			else if(gameData.charAt(1) == 'L' && gameData.charAt(0) == 'R')
			{
				// leftSide2Cube();
			}
			else
			{
				cross();
			}
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