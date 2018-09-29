package frc.team3647autos;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.*;
import frc.robot.Constants;
import frc.team3647autos.*;
import frc.team3647pistons.*;
import frc.team3647subsystems.*;
import frc.team3647inputs.*;

public class Autonomous 
{
    static TrajectoryFollower traj = new TrajectoryFollower();
    public static Timer stopWatch = new Timer();
    public static int currentState;
    public static double time;
	static double currTime, prevTime;

    static double prevLeftEncoder, prevRightEncoder;
	static double newLenc, newRenc, oldLenc, oldRenc;
	static double lSSpeed, rSSpeed, speed, sum, rValue, lValue;

    public static void moveWristDownWhileRunning()
	{
		if(Wrist.reachedFlat())
		{
			Wrist.moveWrist(0);
		}
		else
		{
			Wrist.moveToFlat();
		}
	}

	public static void intakeCube()
	{
		Wrist.moveToFlat();
		Intake.openIntake();
		IntakeWheels.runIntake(0, 0, true, .5, .4, false);
	}

	public static double slowDown(double lowSpeed, double highSpeed, double lowEnc, double highEnc, double currentEnc)
	{
		double yDiff = highSpeed - lowSpeed;
		double xDiff = highEnc - lowEnc;
		double slope = -yDiff / xDiff;
		double b = highSpeed - (slope * lowEnc);
		double returnValue = (slope * currentEnc) + b;
		return returnValue;
	}

	public static double speedUp(double lowSpeed, double highSpeed, double lowEnc, double highEnc, double currentEnc)
	{
		double yDiff = highSpeed - lowSpeed;
		double xDiff = highEnc - lowEnc;
		double slope = yDiff / xDiff;
		double b = lowSpeed - (slope * lowEnc);
		double returnValue = (slope * currentEnc) + b;
		return returnValue;
	}

	public static boolean checkWristIdle(double wValue)
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

	public static void runAuto(Encoders enc, NavX gyro)
	{
		boolean cross = false;
		boolean cantCross = false;
		boolean theyCanCross = false; 
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(cross)
		{
			cross(enc, gyro);
		}
		else 
		{
			if(gameData.charAt(1) == 'R')
			{
				jankScale(enc, gyro);
			}
			else 
			{
				if(gameData.charAt(0) == 'R')
				{
					if(theyCanCross)
					{
						cross(enc, gyro);
					}
					else 
					{
						chezyDoubleSwitchRightFromRight(enc, gyro);
					}	
				}
				else 
				{
					if(cantCross)
					{
						cross(enc, gyro);
					}
					else 
					{
						chezyDoubleSwitchLeftFromRight(enc, gyro);
					}
				}
			}
		}
	}

    public static void initialize(Encoders enc, NavX navX)
    {
        enc.resetEncoders();
        navX.resetAngle();
        traj.initialize();
        Drivetrain.setToBrake();
        Drivetrain.stop();
		currentState = 0;
		enc.prevLEncoder = 0;
		enc.prevREncoder = 0;
    }

	public static void cross(Encoders enc, NavX gyro)
	{
		switch(currentState)
		{
			case 0:
				stopWatch.stop();
				if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0 && stopWatch.get() == 0 && checkWristIdle(Wrist.wristEncoderValue) && gyro.actualYaw == 0)
				{
					stopWatch.start();
					currentState = 1;
				}
				else
				{
					enc.resetEncoders();
                    stopWatch.reset();
                    gyro.resetAngle();
					Wrist.wristMotor.setSelectedSensorPosition(Constants.up, Constants.cubePID, Constants.kTimeoutMs);
				}
				break;
			case 1:
				//
				Wrist.moveUp();
				if(Elevator.elevatorEncoderValue == 0)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else if(stopWatch.get() > 1)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else
				{
					Elevator.moveElevator(-.3);
				}
				break;
			case 2:
				if(enc.leftEncoderValue < 9000)
				{
					Drivetrain.setSpeed(.4, .4);
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

    public static void chezyDoubleSwitchRightFromRight(Encoders enc, NavX gyro)
	{
		enc.setEncoderValues();
		gyro.setAngle();
		enc.testEncoders();
		System.out.println(currentState);
		switch(currentState)
		{
			case 0:
				stopWatch.stop();
				IntakeWheels.runIntake(0, 0, true, .12, .12, false);
				if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0 && stopWatch.get() == 0 && checkWristIdle(Wrist.wristEncoderValue) && gyro.actualYaw == 0)
				{
					stopWatch.start();
					currentState = 1;
				}
				else
				{
					enc.resetEncoders();
                    stopWatch.reset();
                    gyro.resetAngle();
					Wrist.wristMotor.setSelectedSensorPosition(Constants.up, Constants.cubePID, Constants.kTimeoutMs);
				}
				break;
			case 1:
				//
				Wrist.moveUp();
				if(Elevator.elevatorEncoderValue == 0)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else if(stopWatch.get() > 1)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else
				{
					Elevator.moveElevator(-.3);
				}
				break;
			case 2:
				double straightDist = 17800;
				enc.dontSkip();
				if(enc.leftEncoderValue < 3000)
				{
					Drivetrain.setSpeed(.4, .4);
					Wrist.moveUp();
				}
				else if(enc.leftEncoderValue < ((2 * straightDist)/3.0))
				{
					Drivetrain.jankStraight(gyro.yaw, .6);
					Wrist.moveUp();
				}
				else if(enc.leftEncoderValue < straightDist)
				{
					Elevator.moveElevatorPosition(Constants.sWitch);
					moveWristDownWhileRunning();
					speed = slowDown(.15, .6, ((2 * straightDist)/3.0), straightDist, enc.leftEncoderValue);
					Drivetrain.jankStraight(gyro.yaw, speed);

				}
				else 
				{
					prevRightEncoder = enc.leftEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				double turnDist = 10150;
				Elevator.moveElevatorPosition(Constants.sWitch);
                moveWristDownWhileRunning();
                rValue = enc.rightEncoderValue - prevRightEncoder;
                if(rValue < turnDist - 2000)
                {
                    Drivetrain.setSpeed(0, .55);
                }
				else if(rValue < turnDist)
                {
                    Drivetrain.setSpeed(0, .3);
                }
				else 
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 4;
				}
				break;
			case 4:
				currTime = stopWatch.get() - prevTime;
				Elevator.moveElevatorPosition(Constants.sWitch);
				if(currTime < .7)
				{
					enc.resetEncoders();
					if(currTime > .2)
					{
						IntakeWheels.runIntake(0, 0, true, -1, -.75, false);
					}
				}
				else 
				{
					currentState = 5;
				}
				break;
			case 5:
				double backUpDist = 2000;
				Elevator.moveBottom(false);
				Wrist.moveUp();
				lValue = Math.abs(enc.leftEncoderValue);
				if(lValue < backUpDist)
				{
					Drivetrain.setSpeed(-.35, -.35);
				}
				else 
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 6;
				}
				break;
			case 6:
				currTime = stopWatch.get() - prevTime;
				if(currTime < .6)
				{
					enc.resetEncoders();
					Elevator.moveBottom(false);
					Wrist.moveUp();
				}
				else 
				{
					currentState = 7;
				}
				break;
			case 7:
				double deliverDist = 3500;
				lValue = enc.leftEncoderValue;
				if(lValue < deliverDist)
				{
					Drivetrain.setSpeed(.4, .4);
					intakeCube();
				}
				else 
				{
					intakeCube();
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 8;
				}
				break;
			case 8:
				currTime = stopWatch.get() - prevTime;
				if(currTime < .2)
				{
					intakeCube();
					enc.resetEncoders();
				}
				else if(currTime < .4)
				{
					Intake.closeIntake();
					IntakeWheels.runIntake(0, 0, true, .25, .25, false);
					 enc.resetEncoders();
				}
				else 
				{
					IntakeWheels.runIntake(0, 0, true, .12, .12, false);
					currentState = 9;
				}
				break;
			case 9:
				lValue = Math.abs(enc.leftEncoderValue);
				if(lValue < 1800)
				{
					Drivetrain.setSpeed(-.35, -.35);
				}
				else 
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 10;
				}
				break;
			case 10:
				currTime = stopWatch.get() - prevTime;
				Elevator.moveElevatorPosition(Constants.sWitch);
				Wrist.moveToFlat();
				if(currTime > 1 && enc.leftEncoderValue == 0)
				{
					currentState = 11;
				}
				else 
				{
					enc.resetEncoders();
				}
				break;
			case 11:
				Elevator.moveElevatorPosition(Constants.sWitch);
				Wrist.moveToFlat();
				if(enc.leftEncoderValue < 2000)
				{
					Drivetrain.setSpeed(.35, .35);
				}
				else if(enc.leftEncoderValue > 2000)
				{
					Drivetrain.stop();
					IntakeWheels.runIntake(0, 0, true, -1, -.75, false);
				}
				break;
		}
	}

	/*
			case 2:
				Wrist.moveUp();
				double straightDist = 18200;
				enc.dontSkip();
				if(enc.rightEncoderValue < 3000)
				{
					Drivetrain.setSpeed(.4, .4);
				}
				else if(enc.rightEncoderValue < ((2 * straightDist)/3.0))
				{
					Drivetrain.jankStraight(gyro.yaw, .6);
				}
				else if(enc.rightEncoderValue < straightDist)
				{
					//Elevator.moveElevatorPosition(Constants.Switch);
					speed = slowDown(.15, .6, ((2 * straightDist)/3.0), straightDist, enc.rightEncoderValue);
					Drivetrain.jankStraight(gyro.yaw, speed);
				}
				else 
				{
					
					prevRightEncoder = enc.rightEncoderValue;
					enc.testEncoders();
					System.out.println(gyro.yaw);
					currentState = 3;
				}
				break;
			case 3:
				double turnDist = 5070;
				double straighttDist = 11720;
				double secondTurn = 5760;
				Elevator.moveElevatorPosition(Constants.sWitch);
                moveWristDownWhileRunning();
                rValue = enc.rightEncoderValue - prevRightEncoder;
                if(rValue < turnDist - 2000)
                {
                    Drivetrain.setSpeed(0, .58);
                }
				else if(rValue < turnDist)
                {
                    Drivetrain.setSpeed(0, .3);
				}
				else if(rValue < turnDist + 3000)
				{
					Drivetrain.setSpeed(.4, .4);
					gyro.resetAngle();
				}
				else if(rValue < turnDist + straighttDist - 1600)
				{
				Drivetrain.jankStraight(gyro.yaw, .7);
				}
				else if(rValue < turnDist + straighttDist)
				{
					Drivetrain.jankStraight(gyro.yaw, .3);
				}
				else if(rValue < turnDist + straighttDist + secondTurn - 2000)
                {
                    Drivetrain.setSpeed(0, .58);
                }
				else if(rValue < turnDist + straighttDist + secondTurn)
                {
                    Drivetrain.setSpeed(0, .3);
				}
				else 
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 4;
				}
				break;
	*/
	
	public static void chezyDoubleSwitchLeftFromRight(Encoders enc, NavX gyro)
	{
		enc.setEncoderValues();
		gyro.setAngle();
		enc.testEncoders();
		System.out.println(currentState);
		switch(currentState)
		{
			case 0:
			IntakeWheels.runIntake(0, 0, true, .12, .12, false);
				stopWatch.stop();
				if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0 && stopWatch.get() == 0 && checkWristIdle(Wrist.wristEncoderValue) && gyro.actualYaw == 0)
				{
					stopWatch.start();
					currentState = 1;
				}
				else
				{
					enc.resetEncoders();
                    stopWatch.reset();
                    gyro.resetAngle();
					Wrist.wristMotor.setSelectedSensorPosition(Constants.up, Constants.cubePID, Constants.kTimeoutMs);
				}
				break;
			case 1:
				//
				Wrist.moveUp();
				if(Elevator.elevatorEncoderValue == 0)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else if(stopWatch.get() > 1)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else
				{
					Elevator.moveElevator(-.3);
				}
				break;
			case 2:
				Wrist.moveUp();
				double straightDist = 18200;
				enc.dontSkip();
				if(enc.leftEncoderValue < 3000)
				{
					Drivetrain.setSpeed(.4, .4);
				}
				else if(enc.leftEncoderValue < ((2 * straightDist)/3.0))
				{
					Drivetrain.jankStraight(gyro.yaw, .6);
				}
				else if(enc.leftEncoderValue < straightDist)
				{
					speed = slowDown(.15, .6, ((2 * straightDist)/3.0), straightDist, enc.rightEncoderValue);
					Drivetrain.jankStraight(gyro.yaw, speed);
				}
				else 
				{
					
					prevRightEncoder = enc.leftEncoderValue;
					enc.testEncoders();
					System.out.println(gyro.yaw);
					currentState = 3;
				}
				break;
			case 3:
				double turnDist = 5070;
				double straighttDist = 11720;
				double secondTurn = 5760;
				Elevator.moveElevatorPosition(Constants.sWitch);
                moveWristDownWhileRunning();
                rValue = enc.rightEncoderValue - prevRightEncoder;
                if(rValue < turnDist - 2000)
                {
                    Drivetrain.setSpeed(0, .58);
                }
				else if(rValue < turnDist)
                {
                    Drivetrain.setSpeed(0, .3);
				}
				else if(rValue < turnDist + 3000)
				{
					Drivetrain.setSpeed(.4, .4);
					gyro.resetAngle();
				}
				else if(rValue < turnDist + straighttDist - 1600)
				{
				Drivetrain.jankStraight(gyro.yaw, .7);
				}
				else if(rValue < turnDist + straighttDist)
				{
					Drivetrain.jankStraight(gyro.yaw, .3);
				}
				else if(rValue < turnDist + straighttDist + secondTurn - 2000)
                {
                    Drivetrain.setSpeed(0, .58);
                }
				else if(rValue < turnDist + straighttDist + secondTurn)
                {
                    Drivetrain.setSpeed(0, .3);
				}
				else 
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 4;
				}
				break;
			case 4:
				currTime = stopWatch.get() - prevTime;
				Elevator.moveElevatorPosition(Constants.sWitch);
				if(currTime < .7)
				{
					enc.resetEncoders();
					if(currTime > .2)
					{
						IntakeWheels.runIntake(0, 0, true, -1, -.75, false);
					}
				}
				else 
				{
					currentState = 5;
				}
				break;
			case 5:
				double backUpDist = 2000;
				Elevator.moveBottom(false);
				Wrist.moveUp();
				lValue = Math.abs(enc.leftEncoderValue);
				if(lValue < backUpDist)
				{
					Drivetrain.setSpeed(-.35, -.35);
				}
				else 
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 6;
				}
				break;
			case 6:
				currTime = stopWatch.get() - prevTime;
				if(currTime < .6)
				{
					enc.resetEncoders();
					Elevator.moveBottom(false);
					Wrist.moveUp();
				}
				else 
				{
					currentState = 7;
				}
				break;
			case 7:
				double deliverDist = 3500;
				lValue = enc.leftEncoderValue;
				if(lValue < deliverDist)
				{
					Drivetrain.setSpeed(.4, .4);
					intakeCube();
				}
				else 
				{
					intakeCube();
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 8;
				}
				break;
			case 8:
				currTime = stopWatch.get() - prevTime;
				if(currTime < .2)
				{
					intakeCube();
					enc.resetEncoders();
				}
				else if(currTime < .4)
				{
					Intake.closeIntake();
					IntakeWheels.runIntake(0, 0, true, .25, .25, false);
					 enc.resetEncoders();
				}
				else 
				{
					IntakeWheels.runIntake(0, 0, true, .12, .12, false);
					currentState = 9;
				}
				break;
			case 9:
				lValue = Math.abs(enc.leftEncoderValue);
				if(lValue < 1800)
				{
					Drivetrain.setSpeed(-.35, -.35);
				}
				else 
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 10;
				}
				break;
			case 10:
				currTime = stopWatch.get() - prevTime;
				Elevator.moveElevatorPosition(Constants.sWitch);
				Wrist.moveToFlat();
				if(currTime > 1 && enc.leftEncoderValue == 0)
				{
					currentState = 11;
				}
				else 
				{
					enc.resetEncoders();
				}
				break;
			case 11:
				Elevator.moveElevatorPosition(Constants.sWitch);
				Wrist.moveToFlat();
				if(enc.leftEncoderValue < 2000)
				{
					Drivetrain.setSpeed(.35, .35);
				}
				else if(enc.leftEncoderValue > 2000)
				{
					Drivetrain.stop();
					IntakeWheels.runIntake(0, 0, true, -1, -.75, false);
				}
				break;
		}
    }
	
	
	

    public static void chezyDoubleSwitchLeftFromLeft(Encoders enc)
	{
		enc.setEncoderValues();
		System.out.println(currentState);
		switch(currentState)
		{
			case 0:
				stopWatch.stop();
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
				Wrist.moveUp();
				if(Elevator.elevatorEncoderValue == 0)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else if(stopWatch.get() > 1.5)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else
				{
					Elevator.moveElevator(-.3);
				}
				break;
			case 2:
				
				//Wrist.moveUp();
				double straightDist = 10000;
				if(enc.rightEncoderValue < (straightDist/3.0))
				{
					//Wrist.moveUp();
					speed = speedUp(.25, .6, 0, (straightDist/3.0), enc.rightEncoderValue);
					Drivetrain.setSpeed(speed, speed);
				}
				else if(enc.rightEncoderValue < ((2 * straightDist)/3.0))
				{
					//Wrist.moveUp();
					Drivetrain.setSpeed(.6, .6);
				}
				else if(enc.rightEncoderValue < straightDist)
				{
					//Elevator.moveElevatorPosition(Constants.Switch);
					//moveWristDownWhileRunning();
					speed = slowDown(.15, .6, ((2 * straightDist)/3.0), straightDist, enc.rightEncoderValue);
					Drivetrain.setSpeed(speed, speed);
				}
				else 
				{
					
					prevRightEncoder = enc.rightEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				double firstTurn = 6050;
				double straightForAWhile = 0;
				double secondTurn = 3580;
				//Elevator.moveElevatorPosition(Constants.Switch);
				//moveWristDownWhileRunning();
				rValue = enc.rightEncoderValue - prevRightEncoder;
				if(rValue < firstTurn + secondTurn - 1500)
				{
					Drivetrain.setSpeed(0.42, 0);
				}
				else if(rValue < firstTurn + secondTurn)
				{
					Drivetrain.setSpeed(0.3, 0);
				}
				else 
				{
				//	Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 4;
				}
				break;
			case 4:
				currTime = stopWatch.get() - prevTime;
				//Elevator.moveElevatorPosition(Constants.Switch);
				if(currTime < .4)
				{
					enc.resetEncoders();
					if(currTime > .2)
					{
						//IntakeWheels.runIntake(0, 0, true, -.8, -.8, false);
					}
				}
				else 
				{
					currentState = 5;
				}
				break;
			case 5:
				double backUpDist = 2000;
				//Elevator.moveElevator(-.3);
				rValue = Math.abs(enc.rightEncoderValue);
				if(rValue < backUpDist)
				{
					Drivetrain.setSpeed(-.35, -.35);
				}
				else 
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 6;
				}
				break;
			case 6:
				currTime = stopWatch.get() - prevTime;
				if(currTime < .6)
				{
					//Elevator.moveBottom(false);
				}
				else 
				{
					prevRightEncoder = enc.rightEncoderValue;
					currentState = 7;
				}
				break;
			case 7:
				//Wrist.moveWrist(-.3);
				double deliverDist = 3000;
				rValue = enc.rightEncoderValue - prevRightEncoder;
				if(rValue < deliverDist)
				{
					Drivetrain.setSpeed(.4, .4);
					//intakeCube();
				}
				else if(rValue > deliverDist && stopWatch.get() != 0)
				{
				//	intakeCube();
					Drivetrain.stop();
				}
				else 
				{
					//intakeCube();
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 8;
				}
				break;
			case 8:
				currTime = stopWatch.get() - prevTime;
				if(currTime < .2)
				{
					//intakeCube();
					enc.resetEncoders();
				}
				else if(currTime < .4)
				{
					// Intake.closeIntake();
					// IntakeWheels.runIntake(0, 0, true, .25, .25, false);
					 enc.resetEncoders();
				}
				else 
				{
				//	IntakeWheels.runIntake(0, 0, true, .12, .12, false);
					currentState = 9;
				}
				break;
			case 9:
				rValue = Math.abs(enc.rightEncoderValue);
				if(enc.rightEncoderValue < 2000)
				{
					Drivetrain.setSpeed(-.35, -.35);
				}
				else 
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 10;
				}
				break;
			case 10:
				currTime = stopWatch.get() - prevTime;
				//Elevator.moveElevatorPosition(Constants.Switch);
				if(currTime > 1 && enc.rightEncoderValue == 0)
				{
					currentState = 11;
				}
				else 
				{
					enc.resetEncoders();
				}
				break;
			case 11:
				//Elevator.moveElevatorPosition(Constants.Switch);
				if(enc.rightEncoderValue < 2000)
				{
					Drivetrain.setSpeed(.35, .35);
				}
				else if(enc.rightEncoderValue > 2000)
				{
					Drivetrain.stop();
					//IntakeWheels.runIntake(0, 0, true, -.8, -.8, false);
				}
				break;
		}
	}
	
	//8.88 inches max on x, y min 310 inches
	public static void jankScale(Encoders enc, NavX gyro)
	{
		enc.setEncoderValues();
		gyro.setAngle();
		switch(currentState)
		{
			case 0:
			IntakeWheels.runIntake(0, 0, true, .12, .12, false);
				stopWatch.stop();
				if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0 && stopWatch.get() == 0 && checkWristIdle(Wrist.wristEncoderValue) && gyro.actualYaw == 0)
				{
					stopWatch.start();
					currentState = 1;
				}
				else
				{
					enc.resetEncoders();
                    stopWatch.reset();
                    gyro.resetAngle();
					Wrist.wristMotor.setSelectedSensorPosition(Constants.up, Constants.cubePID, Constants.kTimeoutMs);
				}
				break;
			case 1:
				//
				Wrist.moveUp();
				if(Elevator.elevatorEncoderValue == 0)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else if(stopWatch.get() > 1)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else
				{
					Elevator.moveElevator(-.3);
				}
				break;
			case 2:
				double totalScaleDist = 26000;
				Drivetrain.rightSRX.setSelectedSensorPosition(enc.leftEncoderValue, Constants.drivePID, Constants.kTimeoutMs);
				if(enc.leftEncoderValue < 3000)
				{
					Drivetrain.setSpeed(.4, .4);
				}
				else if(enc.leftEncoderValue < ((2 * totalScaleDist)/3.0))
				{
					Elevator.moveElevatorPosition(Constants.sWitch);
					moveWristDownWhileRunning();
					Drivetrain.jankStraight(gyro.yaw, .8);
				}
				else if(enc.leftEncoderValue < totalScaleDist)
				{
					Elevator.moveElevatorPosition(Constants.scale);
					moveWristDownWhileRunning();
					speed = slowDown(.15, .8, ((2 * totalScaleDist)/3.0), totalScaleDist, enc.leftEncoderValue);
					Drivetrain.jankStraight(gyro.yaw, speed);
				}
				else
				{
					Elevator.moveElevatorPosition(Constants.scale);
					moveWristDownWhileRunning();
					prevRightEncoder = enc.leftEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				rValue = enc.rightEncoderValue - prevRightEncoder;
				Elevator.moveElevatorPosition(Constants.scale);
				moveWristDownWhileRunning();
				double rotateRightDist = 3500;
				if(rValue < rotateRightDist - 1300)
				{
					Drivetrain.setSpeed(0, .5);
				}
				else if(rValue < rotateRightDist)
				{
					Drivetrain.setSpeed(0, .2);
				}
				else
				{
					prevTime = stopWatch.get();
					currentState = 4;
					Drivetrain.stop();
				}
				break;
			case 4:
				Elevator.moveElevatorPosition(Constants.scale);
				currTime = stopWatch.get() - prevTime;
				enc.resetEncoders();
				// if(Drivetrain.stopped())
				// {
				// 	prevTime = stopWatch.get();
				// 	currentState = 5;
				// }
				// else 
				if(currTime < .3)
				{

				}
				else if(currTime < .8)
				{
					IntakeWheels.runIntake(0, 0, true, -.9, -.9, false);
				}
				else
				{
					prevTime = stopWatch.get();
					currentState = 6;
				}
				break;
			case 6:
				double spinDist = 2550;
				System.out.println(enc.rightEncoderValue);
				if(enc.rightEncoderValue < spinDist - 1300)
				{
					Drivetrain.setSpeed(-.5, .5);
					Elevator.moveElevatorPosition(Constants.scale);
				}
				else if(enc.rightEncoderValue < spinDist)
				{
					Drivetrain.setSpeed(-.2, .2);
					IntakeWheels.runIntake(0, 0, true, 0, 0, false);
					Elevator.moveElevatorPosition(Constants.scale);
				}
				else
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 7;
				}
				break;
			case 7:
				currTime = stopWatch.get() - prevTime;
				Drivetrain.stop();
				enc.resetEncoders();
				gyro.resetAngle();
				// if(Drivetrain.stopped())
				// {
				// 	prevTime = stopWatch.get();
				// 	//Elevator.moveBottom(false);
				// 	currentState = 8;
				// }
				// else 
				if(currTime < .7)
				{
					Elevator.moveBottom(false);
				}
				else 
				{
					currentState = 9;
				}
				break;
			case 9:
				double straightDistForCube = 8400;
				Elevator.moveBottom(false);
				Wrist.moveToFlat();
				if(enc.leftEncoderValue < straightDistForCube - 1500)
				{
					Drivetrain.jankStraight(0, .4);
					IntakeWheels.runIntake(0, 0, true, .5, .5, false);
					Intake.openIntake();
				}
				else if(enc.leftEncoderValue < straightDistForCube)
				{
					Drivetrain.jankStraight(0, .3);
					IntakeWheels.runIntake(0, 0, true, .5, .5, false);
					Intake.openIntake();
				}
				else 
				{
					Drivetrain.stop();
					IntakeWheels.runIntake(0, 0, true, .2, .2, false);
					Intake.closeIntake();
					prevTime = stopWatch.get();
					currentState = 10;
				}
				break;
			case 10:
				currTime = stopWatch.get() - prevTime;
				enc.resetEncoders();
				gyro.resetAngle();
				if(currTime < .4)
				{
					
				}
				else 
				{
					currentState = 11;
				}
				break;
			case 11:
				lValue = Math.abs(enc.leftEncoderValue);
				double backDistForSecondCube = 8400;
				if(lValue < backDistForSecondCube - 1500)
				{
					Wrist.moveUp();
					Drivetrain.jankStraight(0, -.4);
				}
				else if(lValue < backDistForSecondCube)
				{
					Wrist.moveUp();
					Drivetrain.jankStraight(0, -.3);
				}
				else 
				{
					Drivetrain.stop();
					Intake.closeIntake();
					prevTime = stopWatch.get();
					currentState = 12;
				}
				break;
			case 12:
				currTime = stopWatch.get() - prevTime;
				enc.resetEncoders();
				gyro.resetAngle();
				Wrist.moveToFlat();
				Elevator.moveElevatorPosition(Constants.scale);
				if(currTime < .7)
				{
					
				}
				else 
				{
					currentState = 13;
				}
				break;
			case 13:
				spinDist = 2550;
				Wrist.moveToFlat();
				rValue = Math.abs(enc.rightEncoderValue);
				Elevator.moveElevatorPosition(Constants.scale);
				if(rValue < spinDist - 1300)
				{
					Drivetrain.setSpeed(.5, -.5);
					
				}
				else if(rValue < spinDist)
				{
					Drivetrain.setSpeed(.2, -.2);
				}
				else
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 14;
				}
				break;
			case 14:
				currTime = stopWatch.get() - prevTime;
				Elevator.moveElevatorPosition(Constants.scale);
				Wrist.moveToFlat();
				enc.resetEncoders();
				if(currTime < .25)
				{

				}
				else if(currTime < .4)
				{
					IntakeWheels.runIntake(0, 0, true, -.9, -.9, false);
				}
				else 
				{
					currentState = 15;
				}
				break;
			case 15: 
				spinDist = 2100;
				System.out.println(enc.rightEncoderValue);
				Wrist.moveToFlat();
				if(enc.rightEncoderValue < spinDist - 1300)
				{
					Drivetrain.setSpeed(-.5, .5);
					Elevator.moveElevatorPosition(Constants.scale);
				}
				else if(enc.rightEncoderValue < spinDist)
				{
					Drivetrain.setSpeed(-.2, .2);
					IntakeWheels.runIntake(0, 0, true, 0, 0, false);
					Elevator.moveElevatorPosition(Constants.scale);
				}
				else
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 16;
				}
				break;
			case 16:
				currTime = stopWatch.get() - prevTime;
				Elevator.moveBottom(false);
				enc.resetEncoders();
				gyro.resetAngle();
				if(currTime < .4)
				{

				}
				else 
				{
					currentState = 17;
				}
				break;
			case 17:
				double secondCube = 9900;
				Elevator.moveBottom(false);
				Wrist.moveToFlat();
				if(enc.leftEncoderValue < secondCube - 1500)
				{
					Drivetrain.jankStraight(0, .4);
					IntakeWheels.runIntake(0, 0, true, .5, .5, false);
					Intake.openIntake();
				}
				else if(enc.leftEncoderValue < secondCube)
				{
					Drivetrain.jankStraight(0, .3);
					IntakeWheels.runIntake(0, 0, true, .5, .5, false);
					Intake.openIntake();
				}
				else 
				{
					Drivetrain.stop();
					IntakeWheels.runIntake(0, 0, true, .2, .2, false);
					Intake.closeIntake();
					prevTime = stopWatch.get();
					currentState = 99;
				}
				break;
			case 99:
				currTime = stopWatch.get() - prevTime;
				gyro.resetAngle();
				enc.resetEncoders();
				if(currTime < .4)
				{

				}
				else 
				{
					currentState = 18;
				}
				break;
			case 18:
				lValue = Math.abs(enc.leftEncoderValue);
				double backDistForSecondScale = 9900;
				if(lValue < backDistForSecondScale - 1500)
				{
					Wrist.moveUp();
					Drivetrain.jankStraight(0, -.4);
				}
				else if(lValue < backDistForSecondScale)
				{
					Wrist.moveUp();
					Drivetrain.jankStraight(0, -.3);
				}
				else 
				{
					Drivetrain.stop();
					Intake.closeIntake();
					prevTime = stopWatch.get();
					currentState = 19;
				}
				break;
			case 19:
				currTime = stopWatch.get() - prevTime;
				enc.resetEncoders();
				gyro.resetAngle();
				Wrist.moveToFlat();
				Elevator.moveElevatorPosition(Constants.scale);
				if(currTime < .7)
				{
					
				}
				else 
				{
					currentState = 20;
				}
				break;
			case 20:
				spinDist = 2100;
				Wrist.moveToFlat();
				rValue = Math.abs(enc.rightEncoderValue);
				Elevator.moveElevatorPosition(Constants.scale);
				if(rValue < spinDist - 1300)
				{
					Drivetrain.setSpeed(.5, -.5);
					
				}
				else if(rValue < spinDist)
				{
					Drivetrain.setSpeed(.2, -.2);
				}
				else
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 21;
				}
				break;
			case 21:
				currTime = stopWatch.get() - prevTime;
				Elevator.moveElevatorPosition(Constants.scale);
				Wrist.moveToFlat();
				enc.resetEncoders();
				gyro.resetAngle();
				if(currTime < .25)
				{

				}
				else if(currTime < .4)
				{
					IntakeWheels.runIntake(0, 0, true, -.9, -.9, false);
				}
				else 
				{
					currentState = 22;
				}
				break;
			case 22:
				Elevator.moveElevatorPosition(Constants.scale);
				rValue = Math.abs(enc.rightEncoderValue);
				if(rValue < 4000)
				{
					Drivetrain.jankStraight(0, speed);
				}
				else 
				{
					IntakeWheels.runIntake(0, 0, true, 0, 0, false);
					Drivetrain.stop();
				}
				break;
		}
	}

	public static void jankScaleDeIzquierda(Encoders enc, NavX gyro)
	{
		enc.setEncoderValues();
		gyro.setAngle();
		//System.out.println(currentState);
		switch(currentState)
		{
			case 0:
				stopWatch.stop();
				//if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0 && stopWatch.get() == 0 && chechWristIdle(Wrist.wristEncoder))
				if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0 && stopWatch.get() == 0 && gyro.yaw == 0)
				{
					stopWatch.start();
					currentState = 2;
				}
				else
				{
					enc.resetEncoders();
					stopWatch.reset();
					gyro.resetAngle();
					//Wrist.wristMotor.getSensorCollection().setQuadraturePosition(Constants.up, 10);
				}
				break;
			case 1:
				//
				Wrist.moveUp();
				if(Elevator.elevatorEncoderValue == 0)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else if(stopWatch.get() > 1.5)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else
				{
					Elevator.moveElevator(-.3);
				}
				break;
			case 2:
				double totalScaleDist = 11400;
				// if(enc.rightEncoderValue < (totalScaleDist/3.0))
				// {
				// 	//Wrist.moveUp();
				// 	speed = speedUp(.15, .8, 0, (totalScaleDist/3.0), enc.rightEncoderValue);
				// 	Drivetrain.setSpeed(speed, speed);
				// }
				// else 
				if(enc.leftEncoderValue < 2000)
				{
					Drivetrain.setSpeed(.4, .4);
				}
				else if(enc.leftEncoderValue < ((2 * totalScaleDist)/3.0))
				{
					//Elevator.moveElevatorPosition(Constants.Scale);
					//moveWristDownWhileRunning();
					Drivetrain.jankStraight(gyro.yaw, .8);
				}
				else if(enc.leftEncoderValue < totalScaleDist)
				{
					//Elevator.moveElevatorPosition(Constants.Scale);
					//moveWristDownWhileRunning();
					speed = slowDown(.15, .8, ((2 * totalScaleDist)/3.0), totalScaleDist, enc.rightEncoderValue);
					Drivetrain.jankStraight(gyro.yaw, speed);
				}
				else
				{
					//Elevator.moveElevatorPosition(Constants.Scale);
					//moveWristDownWhileRunning();
					prevLeftEncoder = enc.leftEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				//lValue = enc.leftEncoderValue;
				//Elevator.moveElevatorPosition(Constants.Scale);
				//moveWristDownWhileRunning();
				System.out.println(lValue);
				double rotateLeftDist = 3500;
				if(lValue < rotateLeftDist - 1300)
				{
					Drivetrain.setSpeed(0.6, 0);
				}
				else if(lValue < rotateLeftDist)
				{
					Drivetrain.setSpeed(0.3, 0);
				}
				else
				{
					prevTime = stopWatch.get();
					currentState = 4;
					Drivetrain.stop();
				}
				break;
			case 929:
				Drivetrain.stop();
				break;
			case 4:
				//Elevator.moveElevatorPosition(Constants.Scale);
				currTime = stopWatch.get() - prevTime;
				enc.resetEncoders();
				// if(Drivetrain.stopped())
				// {
				// 	prevTime = stopWatch.get();
				// 	currentState = 5;
				// }
				// else 
				if(currTime < .8)
				{

				}
				else
				{
					prevTime = stopWatch.get();
					currentState = 6;
				}
				break;
			case 5:
				currTime = stopWatch.get() - prevTime;
				//Elevator.moveElevatorPosition(Constants.Scale);
				if(currTime < .3)
				{
					//IntakeWheels.runIntake(0, 0, true, -1, -1, false);
					enc.resetEncoders();
				}
				else 
				{
					currentState = 6;
				}
				break;
			case 6:
				double spinDist = 1900;
				System.out.println(enc.leftEncoderValue);
				if(enc.leftEncoderValue < spinDist - 1300)
				{
					Drivetrain.setSpeed(.5, -.5);
					//Elevator.moveElevatorPosition(Constants.Scale);
				}
				else if(enc.leftEncoderValue < spinDist)
				{
					Drivetrain.setSpeed(.25, -.25);
					//IntakeWheels.runIntake(0, 0, true, 0, 0, false);
					//Elevator.moveBottom(false);
				}
				else
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 929;
				}
				break;
			case 7:
				currTime = stopWatch.get() - prevTime;
				enc.resetEncoders();
				if(Drivetrain.stopped())
				{
					prevTime = stopWatch.get();
					//Elevator.moveBottom(false);
					currentState = 8;
				}
				else if(currTime < .4)
				{
					//Elevator.moveBottom(false);
				}
				else 
				{
					currentState = 9;
				}
				break;
			case 8:
				currTime = stopWatch.get() - prevTime;
				//Elevator.moveBottom(false);
				if(currTime > .1)
				{
					currentState = 9;
				}
				break;
			case 9:
				double straightDistForCube = 5000;
				if(enc.leftEncoderValue < straightDistForCube - 1500)
				{
					Drivetrain.setSpeed(.4, .4);
				}
				else if(enc.leftEncoderValue < straightDistForCube)
				{
					Drivetrain.setSpeed(.3, .3);

				}
				break;
		}
	}

    public static void chezyDoubleSwitchRightFromLeft(Encoders enc)
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
				Wrist.moveUp();
				if(Elevator.elevatorEncoderValue == 0)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else if(stopWatch.get() > 1.5)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else
				{
					Elevator.moveElevator(-.3);
				}
				break;
			case 2:
				//Wrist.moveUp();
				double straightDist = 10000;
				if(enc.rightEncoderValue < 500)
				{
					//Wrist.moveUp();
					Drivetrain.setSpeed(.4, .4);
				}
				else if(enc.rightEncoderValue < 1500)
				{
					//Wrist.moveUp();
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
					Drivetrain.setSpeed(0.42, 0);
				}
				else if(rValue < firstTurn)
				{
					Drivetrain.setSpeed(0.3, 0);
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
					Drivetrain.setSpeed(0.3, 0);
				}
				else 
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 4;
				}
				break;
			case 4:
				currTime = stopWatch.get() - prevTime;
				//Elevator.moveElevatorPosition(Constants.Switch);
				if(currTime < .4)
				{
					enc.resetEncoders();
					if(currTime > .2)
					{
						IntakeWheels.runIntake(0, 0, true, -.8, -.8, false);
					}
				}
				else 
				{
					currentState = 5;
				}
				break;
			case 5:
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
					prevTime = stopWatch.get();
					currentState = 6;
				}
				break;
			case 6:
				currTime = stopWatch.get() - prevTime;
				if(currTime < .6)
				{
					//Elevator.moveBottom(false);
				}
				else 
				{
					prevRightEncoder = enc.rightEncoderValue;
					currentState = 7;
				}
				break;
			case 7:
				Wrist.moveWrist(-.3);
				double deliverDist = 3000;
				rValue = enc.rightEncoderValue - prevRightEncoder;
				if(rValue < deliverDist)
				{
					Drivetrain.setSpeed(.4, .4);
					intakeCube();
				}
				else if(rValue > deliverDist && stopWatch.get() != 0)
				{
					intakeCube();
					Drivetrain.stop();
				}
				else 
				{
					intakeCube();
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 8;
				}
				break;
			case 8:
				currTime = stopWatch.get() - prevTime;
				if(currTime < .2)
				{
					intakeCube();
					enc.resetEncoders();
				}
				else if(currTime < .4)
				{
					Intake.closeIntake();
					IntakeWheels.runIntake(0, 0, true, .25, .25, false);
					enc.resetEncoders();
				}
				else 
				{
					IntakeWheels.runIntake(0, 0, true, .12, .12, false);
					currentState = 9;
				}
				break;
			case 9:
				rValue = Math.abs(enc.rightEncoderValue);
				if(enc.rightEncoderValue < 2000)
				{
					Drivetrain.setSpeed(-.35, -.35);
				}
				else 
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 10;
				}
				break;
			case 10:
				currTime = stopWatch.get() - prevTime;
				//Elevator.moveElevatorPosition(Constants.Switch);
				if(currTime > 1 && enc.rightEncoderValue == 0)
				{
					currentState = 11;
				}
				else 
				{
					enc.resetEncoders();
				}
				break;
			case 11:
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
				Wrist.moveUp();
				if(Elevator.elevatorEncoderValue == 0)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else if(stopWatch.get() > 1.5)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else
				{
					Elevator.moveElevator(-.3);
				}
				break;
			case 2:
				double totalScaleDist = 19000;
				if(enc.rightEncoderValue < 500)
				{
					//Wrist.moveUp();
					Drivetrain.setSpeed(.4, .4);
				}
				else if(enc.rightEncoderValue < 1500)
				{
					//Wrist.moveUp();
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
					prevRightEncoder = enc.rightEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				rValue = enc.rightEncoderValue - prevRightEncoder;
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
					prevTime = stopWatch.get();
					currentState = 4;
				}
				break;
			case 4:
				//Elevator.moveElevatorPosition(Constants.Scale);
				//moveWristDownWhileRunning();
				currTime = stopWatch.get() - prevTime;
				if(currTime < .55)
				{
					IntakeWheels.runIntake(0, 0, true, -.8, -.8, false);
				}
				else
				{
					IntakeWheels.runIntake(0, 0, true, 0, 0, false);
					if(enc.rightEncoderValue == 0 && enc.rightEncoderValue == 0)
					{
						currentState = 5;
					}
					else 
					{
						enc.resetEncoders();
					}
				}
				break;
			case 5:
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
					currentState = 6;
				}
				break;
			case 6:
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

    public static void chezyLeftScale(Encoders enc)
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
				Wrist.moveUp();
				if(Elevator.elevatorEncoderValue == 0)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else if(stopWatch.get() > 1.5)
				{
					Elevator.stopElevator();
					currentState = 2;
				}
				else
				{
					Elevator.moveElevator(-.3);
				}
				break;
			case 2:
				double totalScaleDist = 19000;
				if(enc.rightEncoderValue < 500)
				{
					//Wrist.moveUp();
					Drivetrain.setSpeed(.4, .4);
				}
				else if(enc.rightEncoderValue < 1500)
				{
					//Wrist.moveUp();
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
					prevRightEncoder = enc.rightEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				rValue = enc.rightEncoderValue - prevRightEncoder;
				//Elevator.moveElevatorPosition(Constants.Scale);
				//moveWristDownWhileRunning();
				double rotateRightDist = 4000;
				if(rValue < rotateRightDist - 1600)
				{
					Drivetrain.setSpeed(0.5, 0);
				}
				else if(rValue < rotateRightDist)
				{
					Drivetrain.setSpeed(.3, 0);
				}
				else
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 4;
				}
				break;
			case 4:
				//Elevator.moveElevatorPosition(Constants.Scale);
				//moveWristDownWhileRunning();
				currTime = stopWatch.get() - prevTime;
				if(currTime < .55)
				{
					IntakeWheels.runIntake(0, 0, true, -.8, -.8, false);
				}
				else
				{
					IntakeWheels.runIntake(0, 0, true, 0, 0, false);
					if(enc.rightEncoderValue == 0 && enc.rightEncoderValue == 0)
					{
						currentState = 5;
					}
					else 
					{
						enc.resetEncoders();
					}
				}
				break;
			case 5:
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
					currentState = 6;
				}
				break;
			case 6:
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
}
