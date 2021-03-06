package frc.team3647autos;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.*;
import frc.robot.Constants;
import frc.team3647autos.*;
import frc.team3647pistons.*;
import frc.team3647subsystems.*;
import frc.team3647inputs.*;
import frc.robot.Robot;

public class Autonomous 
{
	/*
	0. cross
	1. chezySwitchRightFromRight
	2. chezySwitchLeftFromLeft
	3. chezyDoubleSwitchRightFromRight
	4. chezyDoubleSwitchLeftFromRight
	5. quickleftScale
	6. quickRightScale
	7. jankScale
	8. jankScaleDeIzquierda
	9. middleRightAuto
	10. middleLeftAuto
	11. middleRightAuto1
	12. middleLeftAuto1
	13. chezySwitchRightFromRight1
	14. chezySwitchLeftFromLeft1
	*/

	

    static TrajectoryFollower traj = new TrajectoryFollower();
    public static Timer stopWatch = new Timer();
    public static int currentState, autoState, runAuto, RhighValue, LhighValue;
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
		//cross between scale and switch scale from left to right
		boolean cross = false; //always false -- true sets it so that we WILL ALWAYS cross auto - stay false
		boolean cantCross = false; //true sets it so that we can't go across left and right
		boolean theyWillCross = true; //if other other team will cross from left to right (v.v.) -> True
		double side = 1; //0 = left, 1 = middle, 2 = right
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		switch(autoState)
		{
			case 0:
				if(cross)
				{
					runAuto = 0;
				}
				else 
				{
					if(Robot.pracBot)
					{
						if(side == 0)
						{
							if(gameData.charAt(0) == 'L')
							{
								runAuto = 14;
							}
							else 
							{
								runAuto = 0;
							}	
						}
						else if(side == 1)
						{
							if(gameData.charAt(0) == 'L')
							{
								runAuto = 12;
							}
							else if(gameData.charAt(0) == 'R')
							{
								runAuto = 11;
							}
							else 
							{
								runAuto = 0;
							}
						}
						else if(side == 2)
						{
							if(gameData.charAt(0) == 'R')
							{
								runAuto = 13;
							}
							else 
							{
								runAuto = 0;
							}
						}
						else 
						{
							runAuto = 0;
						}
					}
					else 
					{
						if(side == 0)
						{
							if(gameData.charAt(0) == 'L')
							{
								runAuto = 2;
							}
							else 
							{
								runAuto = 0;
							}	
						}
						else if(side == 1)
						{
							if(gameData.charAt(0) == 'L')
							{
								runAuto = 10;
							}
							else if(gameData.charAt(0) == 'R')
							{
								runAuto = 9;
							}
							else 
							{
								runAuto = 0;
							}
						}
						else if(side == 2)
						{
							if(theyWillCross)
							{
								if(gameData.charAt(1) == 'L' && gameData.charAt(0) == 'R')
								{
									runAuto = 3;
								}
								else if(gameData.charAt(0) == 'R')
								{
									runAuto = 1;
								}
								else 
								{
									runAuto = 0;
								}
							}
							else if(cantCross)
							{
								if(gameData.charAt(0) == 'R')
								{
									runAuto = 3;
								}
								else 
								{
									runAuto = 0;
								}
							}
							else 
							{
								if(gameData.charAt(0) == 'R')
								{
									runAuto = 3;
								}
								else if(gameData.charAt(0) == 'L')
								{
									runAuto = 4;
								}
								else 
								{
									runAuto = 0;
								}
							}
						}
						else 
						{
							runAuto = 0;
						}
					}
				}

				autoState = 1;
				break;
			 case 1:
				if(runAuto == 0)
				{
					cross(enc, gyro);
				}
				else if(runAuto == 1)
				{
					chezySwitchRightFromRight(enc, gyro);
				}
				else if(runAuto == 2)
				{
					chezySwitchLeftFromLeft(enc, gyro);
				}
				else if(runAuto == 3)
				{
					chezyDoubleSwitchRightFromRight(enc, gyro);
				}
				else if(runAuto == 4)
				{
					chezyDoubleSwitchLeftFromRight(enc, gyro);
				}
				// else if(runAuto == 5)
				// {
				// 	quickleftScale(enc, gyro);
				// }
				// else if(runAuto == 6)
				// {
				// 	quickRightScale(enc, gyro);
				// }
				// else if(runAuto == 7)
				// {
				// 	jankScale(enc, gyro);
				// }
				// else if(runAuto == 8)
				// {
				// 	jankScaleDeIzquierda(enc, gyro);
				// }
				else if(runAuto == 9)
				{
					middleAutoR(enc, gyro);
				}
				else if(runAuto == 10)
				{
					middleAutoL(enc, gyro);
				}
				else if(runAuto == 11)
				{
					middleAutoR2(enc, gyro);
				}
				else if(runAuto == 12)
				{
					middleAutoL2(enc, gyro);
				}
				else if(runAuto == 13)
				{
					chezySwitchRightFromRight2(enc, gyro);
				}
				else if(runAuto == 14)
				{
					chezySwitchLeftFromLeft2(enc, gyro);
				}
				else
				{
					cross(enc, gyro);
				}
				break;
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
		autoState = 0;
		enc.prevLEncoder = 0;
		enc.prevREncoder = 0;
    }

	public static void cross(Encoders enc, NavX gyro)
	{
		enc.setEncoderValues();
		System.out.println(currentState);
		//.testEncoders();
		switch(currentState)
		{
			case 0:
				stopWatch.stop();
				if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0)
				{
					stopWatch.start();
					currentState = 2;
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
					prevTime = stopWatch.get();
					currentState = 2;
				}
				else if(stopWatch.get() > 1)
				{
					Elevator.stopElevator();
					prevTime = stopWatch.get();
					currentState = 2;
				}
				else
				{
					Elevator.moveElevator(-.3);
				}
				break;
			case 2:
				currTime = stopWatch.get() - prevTime;
				if(enc.leftEncoderValue < 9500 || currTime < 1.5)
				{
					System.out.println(99);
					Drivetrain.setSpeed(.6, .6);
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


	// 151.5 inches in y, 55.5 inches in x
	public static void chezySwitchRightFromRight(Encoders enc, NavX gyro)
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
				//Wrist.moveUp();
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
				double straightDist = 11000;
				enc.dontSkip();
				Wrist.moveToFlat();
				Elevator.moveElevatorPosition(Constants.sWitch);
				if(enc.leftEncoderValue < 3000)
				{
					Drivetrain.setSpeed(.4, .4);
					//Wrist.moveUp();
				}
				else if(enc.leftEncoderValue < ((2 * straightDist)/3.0))
				{
					Drivetrain.jankStraight(gyro.yaw, .6);
					moveWristDownWhileRunning();
				}
				else if(enc.leftEncoderValue < straightDist)
				{
					speed = slowDown(.15, .6, ((2 * straightDist)/3.0), straightDist, enc.leftEncoderValue);
					Drivetrain.jankStraight(gyro.yaw, speed);
					moveWristDownWhileRunning();
				}
				else 
				{
					prevRightEncoder = enc.leftEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				double turnDist = 5300;
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
				if(currTime < .3)
				{

				}
				else if(currTime < 1.3)
				{
					IntakeWheels.runIntake(0, 0, true, -.9, -.9, false);
				}
				else 
				{
					IntakeWheels.runIntake(0, 0, true, -.9, -.9, false);
				}
				break;
		
		}
	}

	public static void chezySwitchRightFromRight2(Encoders enc, NavX gyro)
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
				//Wrist.moveUp();
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
				double straightDist = 11000;
				enc.dontSkip();
				Wrist.moveToFlat();
				Elevator.moveElevatorPosition(Constants.sWitch);
				if(enc.leftEncoderValue < 3000)
				{
					Drivetrain.setSpeed(.4, .4);
					//Wrist.moveUp();
				}
				else if(enc.leftEncoderValue < ((2 * straightDist)/3.0))
				{
					Drivetrain.jankStraight(gyro.yaw, .6);
					moveWristDownWhileRunning();
				}
				else if(enc.leftEncoderValue < straightDist)
				{
					speed = slowDown(.15, .6, ((2 * straightDist)/3.0), straightDist, enc.leftEncoderValue);
					Drivetrain.jankStraight(gyro.yaw, speed);
					moveWristDownWhileRunning();
				}
				else 
				{
					prevRightEncoder = enc.leftEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				double turnDist = 5300;
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
				if(currTime < .3)
				{

				}
				else if(currTime < 1.3)
				{
					IntakeWheels.runIntake(0, 0, true, -.9, -.9, false);
				}
				else 
				{
					IntakeWheels.runIntake(0, 0, true, -.9, -.9, false);
				}
				break;
		
		}
	}

	// 151.5 inches in y, 55.5 inches in x
	public static void chezySwitchLeftFromLeft(Encoders enc, NavX gyro)
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
				//Wrist.moveUp();
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
				double straightDist = 11000;
				enc.dontSkip();
				Wrist.moveToFlat();
				Elevator.moveElevatorPosition(Constants.sWitch);
				if(enc.leftEncoderValue < 3000)
				{
					Drivetrain.setSpeed(.4, .4);
					//Wrist.moveUp();
				}
				else if(enc.leftEncoderValue < ((2 * straightDist)/3.0))
				{
					Drivetrain.jankStraight(gyro.yaw, .6);
					moveWristDownWhileRunning();
				}
				else if(enc.leftEncoderValue < straightDist)
				{
					speed = slowDown(.15, .6, ((2 * straightDist)/3.0), straightDist, enc.leftEncoderValue);
					Drivetrain.jankStraight(gyro.yaw, speed);
					moveWristDownWhileRunning();
				}
				else 
				{
					prevLeftEncoder = enc.leftEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				double turnDist = 5300;
				Elevator.moveElevatorPosition(Constants.sWitch);
                moveWristDownWhileRunning();
                lValue = enc.leftEncoderValue - prevLeftEncoder;
                if(lValue < turnDist - 2000)
                {
                    Drivetrain.setSpeed(.58, 0);
                }
				else if(lValue < turnDist)
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
				currTime = stopWatch.get() - prevTime;
				Elevator.moveElevatorPosition(Constants.sWitch);
				if(currTime < .3)
				{

				}
				else if(currTime < 1.3)
				{
					IntakeWheels.runIntake(0, 0, true, -.9, -.9, false);
				}
				else 
				{
					IntakeWheels.runIntake(0, 0, true, -.9, -.9, false);
				}
				break;
		
		}
	}

	public static void chezySwitchLeftFromLeft2(Encoders enc, NavX gyro)
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
				//Wrist.moveUp();
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
				double straightDist = 11000;
				enc.dontSkip();
				Wrist.moveToFlat();
				Elevator.moveElevatorPosition(Constants.sWitch);
				if(enc.leftEncoderValue < 3000)
				{
					Drivetrain.setSpeed(.4, .4);
					//Wrist.moveUp();
				}
				else if(enc.leftEncoderValue < ((2 * straightDist)/3.0))
				{
					Drivetrain.jankStraight(gyro.yaw, .6);
					moveWristDownWhileRunning();
				}
				else if(enc.leftEncoderValue < straightDist)
				{
					speed = slowDown(.15, .6, ((2 * straightDist)/3.0), straightDist, enc.leftEncoderValue);
					Drivetrain.jankStraight(gyro.yaw, speed);
					moveWristDownWhileRunning();
				}
				else 
				{
					prevLeftEncoder = enc.leftEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				double turnDist = 5300;
				Elevator.moveElevatorPosition(Constants.sWitch);
                moveWristDownWhileRunning();
                lValue = enc.leftEncoderValue - prevLeftEncoder;
                if(lValue < turnDist - 2000)
                {
                    Drivetrain.setSpeed(.58, 0);
                }
				else if(lValue < turnDist)
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
				currTime = stopWatch.get() - prevTime;
				Elevator.moveElevatorPosition(Constants.sWitch);
				if(currTime < .3)
				{

				}
				else if(currTime < 1.3)
				{
					IntakeWheels.runIntake(0, 0, true, -.9, -.9, false);
				}
				else 
				{
					IntakeWheels.runIntake(0, 0, true, -.9, -.9, false);
				}
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
				//Wrist.moveUp();
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
				double straightDist = 17450;
				enc.dontSkip();
				if(enc.leftEncoderValue < 3000)
				{
					Drivetrain.setSpeed(.4, .4);
					//Wrist.moveUp();
				}
				else if(enc.leftEncoderValue < ((2 * straightDist)/3.0))
				{
					Drivetrain.jankStraight(gyro.yaw, .6);
					//Wrist.moveUp();
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
				double turnDist = 11050;
				Elevator.moveElevatorPosition(Constants.sWitch);
                moveWristDownWhileRunning();
                rValue = enc.rightEncoderValue - prevRightEncoder;
                if(rValue < turnDist - 2000)
                {
                    Drivetrain.setSpeed(0, .5);
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
				double backUpDist = 1400;
				Elevator.moveBottom(false);
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
				}
				else 
				{
					currentState = 7;
				}
				break;
			case 7:
				double deliverDist = 3300;
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
				if(currTime < .35)
				{
					intakeCube();
					enc.resetEncoders();
				}
				else if(currTime < 1)
				{
					Intake.closeIntake();
					IntakeWheels.runIntake(0, 0, true, .45, .45, false);
					 enc.resetEncoders();
				}
				else 
				{
					IntakeWheels.runIntake(0, 0, true, .2, .2, false);
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
				if(enc.leftEncoderValue < 2500)
				{
					Drivetrain.setSpeed(.35, .35);
				}
				else if(enc.leftEncoderValue > 2500)
				{
					Drivetrain.stop();
					IntakeWheels.runIntake(0, 0, true, -1, -.75, false);
				}
				break;
		}
	}
	
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
				//Wrist.moveUp();
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
				//Wrist.moveUp();
				double straightDist = 18100;
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
				double turnDist = 5220;
				double straighttDist = 13020;
				double secondTurn = 5960;
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
						IntakeWheels.runIntake(0, 0, true, -.75, -1, false);
					}
				}
				else 
				{
					currentState = 5;
				}
				break;
			case 5:
				double backUpDist = 1400;
				Elevator.moveBottom(false);
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
				}
				else 
				{
					currentState = 7;
				}
				break;
			case 7:
				double deliverDist = 3100;
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
				if(currTime < .35)
				{
					intakeCube();
					enc.resetEncoders();
				}
				else if(currTime < 1)
				{
					Intake.closeIntake();
					IntakeWheels.runIntake(0, 0, true, .45, .45, false);
					 enc.resetEncoders();
				}
				else 
				{
					IntakeWheels.runIntake(0, 0, true, .2, .2, false);
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
					IntakeWheels.runIntake(0, 0, true, -.75, -1, false);
				}
				break;
		}
	}
	

	//307.15 icnhes, 10 inches max
	public static void quickleftScale(Encoders enc, NavX gyro)
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
				//Wrist.moveUp();
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
				double totalScaleDist = 26600;
				enc.dontSkip();
				if(enc.leftEncoderValue < 3000)
				{
					Drivetrain.jankStraight(gyro.yaw, .4);
				}
				else if(enc.leftEncoderValue < ((2 * totalScaleDist)/3.0))
				{
					Elevator.moveElevatorPosition(Constants.sWitch);
					moveWristDownWhileRunning();
					Drivetrain.jankStraight(gyro.yaw, .9);
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
					prevLeftEncoder = enc.leftEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				Elevator.moveElevatorPosition(Constants.scale);
				double rotateDist = 5070;
				lValue = enc.leftEncoderValue - prevLeftEncoder;
                if(lValue < rotateDist - 2000)
                {
                    Drivetrain.setSpeed(.58, 0);
                }
				else if(lValue < rotateDist)
                {
                    Drivetrain.setSpeed(.3, 0);
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
				enc.resetEncoders();
				currTime = stopWatch.get() - prevTime;
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
					// currentState = 5;
				}
				break;
			case 5:
				lValue = Math.abs(enc.leftEncoderValue);
				Elevator.moveElevatorPosition(Constants.scale);
				if(lValue < 3500)
				{
					Drivetrain.setSpeed(-.4, -.4);
				}
				else 
				{
					Drivetrain.stop();
				}
				break;
		}
	}

	//307.15 icnhes, 10 inches max
	public static void quickleftScale1(Encoders enc, NavX gyro)
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
				//Wrist.moveUp();
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
				double totalScaleDist = 26600;
				enc.dontSkip();
				if(enc.leftEncoderValue < 3000)
				{
					Drivetrain.jankStraight(gyro.yaw, .4);
				}
				else if(enc.leftEncoderValue < ((2 * totalScaleDist)/3.0))
				{
					Elevator.moveElevatorPosition(Constants.sWitch);
					moveWristDownWhileRunning();
					Drivetrain.jankStraight(gyro.yaw, .9);
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
					prevLeftEncoder = enc.leftEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				Elevator.moveElevatorPosition(Constants.scale);
				double rotateDist = 5070;
				lValue = enc.leftEncoderValue - prevLeftEncoder;
                if(lValue < rotateDist - 2000)
                {
                    Drivetrain.setSpeed(.58, 0);
                }
				else if(lValue < rotateDist)
                {
                    Drivetrain.setSpeed(.3, 0);
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
				enc.resetEncoders();
				currTime = stopWatch.get() - prevTime;
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
					// currentState = 5;
				}
				break;
			case 5:
				lValue = Math.abs(enc.leftEncoderValue);
				Elevator.moveElevatorPosition(Constants.scale);
				if(lValue < 3500)
				{
					Drivetrain.setSpeed(-.4, -.4);
				}
				else 
				{
					Drivetrain.stop();
				}
				break;
		}
	}

	public static void quickRightScale(Encoders enc, NavX gyro)
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
				//Wrist.moveUp();
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
				double totalScaleDist = 26600;
				enc.dontSkip();
				if(enc.leftEncoderValue < 3000)
				{
					Drivetrain.jankStraight(gyro.yaw, .4);
				}
				else if(enc.leftEncoderValue < ((2 * totalScaleDist)/3.0))
				{
					Elevator.moveElevatorPosition(Constants.sWitch);
					moveWristDownWhileRunning();
					Drivetrain.jankStraight(gyro.yaw, .9);
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
					prevLeftEncoder = enc.leftEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				Elevator.moveElevatorPosition(Constants.scale);
				double rotateDist = 5070;
				rValue = enc.rightEncoderValue - prevRightEncoder;
                if(rValue < rotateDist - 2000)
                {
                    Drivetrain.setSpeed(0, .58);
                }
				else if(rValue < rotateDist)
                {
                    Drivetrain.setSpeed(0, .3);
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
				enc.resetEncoders();
				currTime = stopWatch.get() - prevTime;
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
					// currentState = 5;
				}
				break;
			case 5:
				lValue = Math.abs(enc.leftEncoderValue);
				Elevator.moveElevatorPosition(Constants.scale);
				if(lValue < 3500)
				{
					Drivetrain.setSpeed(-.4, -.4);
				}
				else 
				{
					Drivetrain.stop();
				}
				break;
		}
	}

	public static void quickRightScale1(Encoders enc, NavX gyro)
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
				//Wrist.moveUp();
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
				double totalScaleDist = 26600;
				enc.dontSkip();
				if(enc.leftEncoderValue < 3000)
				{
					Drivetrain.jankStraight(gyro.yaw, .4);
				}
				else if(enc.leftEncoderValue < ((2 * totalScaleDist)/3.0))
				{
					Elevator.moveElevatorPosition(Constants.sWitch);
					moveWristDownWhileRunning();
					Drivetrain.jankStraight(gyro.yaw, .9);
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
					prevLeftEncoder = enc.leftEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				Elevator.moveElevatorPosition(Constants.scale);
				double rotateDist = 5070;
				rValue = enc.rightEncoderValue - prevRightEncoder;
                if(rValue < rotateDist - 2000)
                {
                    Drivetrain.setSpeed(0, .58);
                }
				else if(rValue < rotateDist)
                {
                    Drivetrain.setSpeed(0, .3);
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
				enc.resetEncoders();
				currTime = stopWatch.get() - prevTime;
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
					// currentState = 5;
				}
				break;
			case 5:
				lValue = Math.abs(enc.leftEncoderValue);
				Elevator.moveElevatorPosition(Constants.scale);
				if(lValue < 3500)
				{
					Drivetrain.setSpeed(-.4, -.4);
				}
				else 
				{
					Drivetrain.stop();
				}
				break;
		}
	}

	//307.15 icnhes, 10 inches max
	/*public static void quickRightScale(Encoders enc, NavX gyro)
	{
		enc.setEncoderValues();
		gyro.setAngle();
		enc.testEncoders();
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
				//Wrist.moveUp();
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
				if(enc.leftEncoderValue < 3000)
				{
					Drivetrain.setSpeed(gyro.yaw, .4);
				}
				else
				{
					currentState = 254;
				} 
				break;
			case 254:
				double totalScaleDist = 25300;
				enc.dontSkip();
				if(enc.leftEncoderValue < ((2 * totalScaleDist)/3.0))
				{
					Elevator.moveElevatorPosition(Constants.sWitch);
					moveWristDownWhileRunning();
					Drivetrain.jankStraight(gyro.yaw, .6);
				}
				else if(enc.leftEncoderValue < totalScaleDist)
				{
					Elevator.moveElevatorPosition(Constants.scale);
					moveWristDownWhileRunning();
					speed = slowDown(.15, .6, ((2 * totalScaleDist)/3.0), totalScaleDist, enc.leftEncoderValue);
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
				Elevator.moveElevatorPosition(Constants.scale);
				double rotateDist = 5700;
				rValue = enc.rightEncoderValue - prevRightEncoder;
                if(rValue < rotateDist - 2000)
                {
                    Drivetrain.setSpeed(0, .58);
                }
				else if(rValue < rotateDist)
                {
                    Drivetrain.setSpeed(0, .3);
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
				enc.resetEncoders();
				currTime = stopWatch.get() - prevTime;
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
					currentState = 5;
				}
				break;
			case 5:
				lValue = Math.abs(enc.leftEncoderValue);
				Elevator.moveElevatorPosition(Constants.scale);
				if(lValue < 3500)
				{
					Drivetrain.setSpeed(-.4, -.4);
				}
				else 
				{
					Drivetrain.stop();
				}
				break;
		}
	}*/
	
	//8.88 inches max on x, y min 310 inches
	public static void jankScale(Encoders enc, NavX gyro)
	{
		enc.setEncoderValues();
		gyro.setAngle();
		System.out.println(gyro.yaw);
		enc.testEncoders();
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
				if(enc.leftEncoderValue < 3000)
				{
					Drivetrain.setSpeed(.4, .4);
				}
				else
				{
					currentState = 254;
				} 
				break;
			case 254:
				double totalScaleDist = 23000;
				enc.dontSkip();
				if(enc.leftEncoderValue < ((2 * totalScaleDist)/3.0))
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
				double rotateRightDist = 3700;
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
				double reqAngle = 200;
				if(gyro.actualYaw > reqAngle + 30)
				{
					Drivetrain.setSpeed(-.5, .5);
					Elevator.moveElevatorPosition(Constants.scale);
				}
				else if(gyro.actualYaw > reqAngle)
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
					Drivetrain.jankStraightNotFirst(gyro.actualYaw, .5, 200);
					IntakeWheels.runIntake(0, 0, true, .5, .5, false);
					Intake.openIntake();
				}
				else if(enc.leftEncoderValue < straightDistForCube)
				{
					Drivetrain.jankStraightNotFirst(gyro.actualYaw, .3, 200);
					IntakeWheels.runIntake(0, 0, true, .5, .5, false);
					Intake.openIntake();
				}
				else 
				{
					Drivetrain.stop();
					IntakeWheels.runIntake(0, 0, true, .2, .2, false);
					Intake.closeIntake();
					prevTime = stopWatch.get();
					//currentState = 10;
				}
				break;
			case 10:
				currTime = stopWatch.get() - prevTime;
				enc.resetEncoders();
				gyro.resetAngle();
				if(currTime < .6)
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
					//Wrist.moveUp();
					Drivetrain.jankStraight(0, -.4);
				}
				else if(lValue < backDistForSecondCube)
				{
					//Wrist.moveUp();
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
				double spinDist = 2550;
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
				if(currTime < .6)
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
					//Wrist.moveUp();
					Drivetrain.jankStraight(0, -.4);
				}
				else if(lValue < backDistForSecondScale)
				{
					//Wrist.moveUp();
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
				//Wrist.moveUp();
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
				double totalScaleDist = 25700;
				Drivetrain.rightSRX.setSelectedSensorPosition(enc.leftEncoderValue, Constants.drivePID, Constants.kTimeoutMs);
				// if(enc.rightEncoderValue < (totalScaleDist/3.0))
				// {
				// 	////Wrist.moveUp();
				// 	speed = speedUp(.15, .8, 0, (totalScaleDist/3.0), enc.rightEncoderValue);
				// 	Drivetrain.setSpeed(speed, speed);
				// }
				// else 
				if(enc.leftEncoderValue < 3000)
				{
					Drivetrain.setSpeed(.4, .4);
				}
				else if(enc.leftEncoderValue < ((2 * totalScaleDist)/3.0))
				{
					Elevator.moveElevatorPosition(Constants.sWitch);
					moveWristDownWhileRunning();
					speed = slowDown(.15, .8, ((2 * totalScaleDist)/3.0), totalScaleDist, enc.leftEncoderValue);
					Drivetrain.jankStraight(gyro.yaw, speed);
				}
				else if(enc.leftEncoderValue < totalScaleDist)
				{
					Elevator.moveElevatorPosition(Constants.scale);
					moveWristDownWhileRunning();
					speed = slowDown(.15, .8, ((2 * totalScaleDist)/3.0), totalScaleDist, enc.rightEncoderValue);
					Drivetrain.jankStraight(gyro.yaw, speed);
				}
				else
				{
					Elevator.moveElevatorPosition(Constants.scale);
					moveWristDownWhileRunning();
					prevLeftEncoder = enc.leftEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				lValue = enc.leftEncoderValue - prevLeftEncoder;
				Elevator.moveElevatorPosition(Constants.scale);
				moveWristDownWhileRunning();
				System.out.println(lValue);
				double rotateLeftDist = 3500;
				if(lValue < rotateLeftDist - 1300)
				{
					Drivetrain.setSpeed(0.5, 0);
				}
				else if(lValue < rotateLeftDist)
				{
					Drivetrain.setSpeed(0.2, 0);
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
				System.out.println(enc.leftEncoderValue);
				if(enc.leftEncoderValue < spinDist - 1300)
				{
					Drivetrain.setSpeed(.5, -.5);
					Elevator.moveElevatorPosition(Constants.scale);
				}
				else if(enc.leftEncoderValue < spinDist)
				{
					Drivetrain.setSpeed(.2, -.2);
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
				enc.resetEncoders();
				gyro.resetAngle();
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
				if(currTime < .6)
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
					//Wrist.moveUp();
					Drivetrain.jankStraight(0, -.4);
				}
				else if(lValue < backDistForSecondCube)
				{
					//Wrist.moveUp();
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
				lValue = Math.abs(enc.leftEncoderValue);
				Elevator.moveElevatorPosition(Constants.scale);
				if(lValue < spinDist - 1300)
				{
					Drivetrain.setSpeed(-.5, .5);
					
				}
				else if(lValue < spinDist)
				{
					Drivetrain.setSpeed(-.2, .2);
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
				System.out.println(enc.leftEncoderValue);
				Wrist.moveToFlat();
				if(enc.leftEncoderValue < spinDist - 1300)
				{
					Drivetrain.setSpeed(.5, -.5);
					Elevator.moveElevatorPosition(Constants.scale);
				}
				else if(enc.leftEncoderValue < spinDist)
				{
					Drivetrain.setSpeed(.2, -.2);
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
				if(currTime < .6)
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
					//Wrist.moveUp();
					Drivetrain.jankStraight(0, -.4);
				}
				else if(lValue < backDistForSecondScale)
				{
					//Wrist.moveUp();
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
				lValue = Math.abs(enc.leftEncoderValue);
				Elevator.moveElevatorPosition(Constants.scale);
				if(lValue < spinDist - 1300)
				{
					Drivetrain.setSpeed(-.5, .5);
					
				}
				else if(lValue < spinDist)
				{
					Drivetrain.setSpeed(-.2, .2);
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

	public static void middleAutoR(Encoders enc, NavX gyro)
	{
		enc.setEncoderValues();
		gyro.setAngle();
		switch(currentState)
		{
			case 0:
			stopWatch.stop();
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
				double straightDist = 1000;
				Elevator.moveElevatorPosition(Constants.sWitch);
				if(enc.leftEncoderValue < 1650)
				{
					Drivetrain.jankStraight(gyro.yaw,.4);
				}
				else 
				{
					prevLeftEncoder = enc.leftEncoderValue;
					prevTime = stopWatch.get();
					currentState = 3;
				}
				break;
			case 3:
				double rightTurnDist = 2500;
				currTime = stopWatch.get() - prevTime;
				Elevator.moveElevatorPosition(Constants.sWitch);
                lValue = enc.leftEncoderValue - prevLeftEncoder;
                if(currTime < .7)
                {
                    Drivetrain.setSpeed(0.5, 0.2);
                }
				else 
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					prevRightEncoder = enc.rightEncoderValue;
					currentState = 4;
				}
				break;
			case 4:
				//leftTurnDist = 2500;
				rValue = enc.rightEncoderValue - prevRightEncoder;
				Elevator.moveElevatorPosition(Constants.sWitch);
				currTime = stopWatch.get() - prevTime;
                if(currTime < .7)
                {
                    Drivetrain.setSpeed(0.2, 0.5);
                }
				else 
				{
					Drivetrain.stop();
					prevLeftEncoder = enc.leftEncoderValue;
					prevTime = stopWatch.get();
					currentState = 5;
				}
				break;
			case 5:
				straightDist = 1500;
				currTime = stopWatch.get() - prevTime;
				Elevator.moveElevatorPosition(Constants.sWitch);
				lValue = enc.leftEncoderValue - prevLeftEncoder;
				if(currTime < 0.2)
				{

					Drivetrain.jankStraight(gyro.yaw, .4);
				}
				else 
				{
					prevRightEncoder = enc.leftEncoderValue;
					Drivetrain.stop();
					IntakeWheels.manuallyRunIntake(-.32, -.32);
					//currentState = 6;
				}
				break;
		}
	}
	public static void middleAutoL(Encoders enc, NavX gyro)
	{
		enc.setEncoderValues();
		gyro.setAngle();
		System.out.println(gyro.yaw);
		switch(currentState)
		{
			case 0:
				stopWatch.stop();
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
				double straightDist = 1800;
				Elevator.moveElevatorPosition(Constants.sWitch);
				if(enc.leftEncoderValue < straightDist)
				{
					Drivetrain.jankStraight(gyro.yaw,.4);
				}
				else 
				{
					prevLeftEncoder = enc.leftEncoderValue;
					prevTime = stopWatch.get();
					currentState = 3;
				}
				break;
			case 3:
				currTime = stopWatch.get() - prevTime;
				System.out.println(currTime);
				lValue = enc.leftEncoderValue - prevLeftEncoder;
				Elevator.moveElevatorPosition(Constants.sWitch);
                if(currTime < .66)
                {
                    Drivetrain.setSpeed(0.1, 0.58);
                }
				else 
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					prevRightEncoder = enc.rightEncoderValue;
					currentState = 4;
				}
				break;
			case 4:
				straightDist = 1500;
				currTime = stopWatch.get() - prevTime;
				lValue = enc.leftEncoderValue - prevLeftEncoder;
				Elevator.moveElevatorPosition(Constants.sWitch);
				if(currTime < .478)
				{

					Drivetrain.setSpeed(.4,.4);
				}
				else 
				{
					prevRightEncoder = enc.leftEncoderValue;
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 5;
				}
				break;
			case 5:
				//leftTurnDist = 2500;
				rValue = enc.rightEncoderValue - prevRightEncoder;
				Elevator.moveElevatorPosition(Constants.sWitch);
				currTime = stopWatch.get() - prevTime;
                if(currTime < .66)
                {
                    Drivetrain.setSpeed(0.613, 0.15);
                }
				else 
				{
					Drivetrain.stop();
					prevLeftEncoder = enc.leftEncoderValue;
					prevTime = stopWatch.get();
					currentState = 6;
				}
				break;
			case 6:
				straightDist = 1500;
				currTime = stopWatch.get() - prevTime;
				Elevator.moveElevatorPosition(Constants.sWitch);
				lValue = enc.leftEncoderValue - prevLeftEncoder;
				if(currTime < 0.23)
				{

					Drivetrain.jankStraight(gyro.yaw, .4);
				}
				else 
				{
					IntakeWheels.manuallyRunIntake(-.9, -.9);
					prevRightEncoder = enc.leftEncoderValue;
					Drivetrain.stop();
				}
				break;
		}
	}

	public static void middleAutoR2(Encoders enc, NavX gyro)
	{
		enc.setEncoderValues();
		gyro.setAngle();
		System.out.println(gyro.yaw);
		switch(currentState)
		{
			case 0:
			stopWatch.stop();
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
				double straightDist = 1000;
				Elevator.moveElevatorPosition(Constants.sWitch);
				if(enc.leftEncoderValue < 1700)
				{
					Drivetrain.jankStraight(gyro.yaw,.4);
				}
				else 
				{
					prevLeftEncoder = enc.leftEncoderValue;
					prevTime = stopWatch.get();
					currentState = 3;
				}
				break;
			case 3:
				//double rightTurnDist = 2500;
				currTime = stopWatch.get() - prevTime;
				Elevator.moveElevatorPosition(Constants.sWitch);
				System.out.println(currTime);
                lValue = enc.leftEncoderValue - prevLeftEncoder;
                if(currTime < .7)
                {
                    Drivetrain.setSpeed(0.5, 0.2);
                }
				else 
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					prevRightEncoder = enc.rightEncoderValue;
					currentState = 4;
				}
				break;
			case 4:
				//leftTurnDist = 2500;
				rValue = enc.rightEncoderValue - prevRightEncoder;
				Elevator.moveElevatorPosition(Constants.sWitch);
				System.out.println(currTime);
				currTime = stopWatch.get() - prevTime;
                if(currTime < .7)
                {
                    Drivetrain.setSpeed(0.2, 0.48);
                }
				else 
				{
					Drivetrain.stop();
					prevLeftEncoder = enc.leftEncoderValue;
					prevTime = stopWatch.get();
					currentState = 5;
				}
				break;
			case 5:
				straightDist = 1500;
				Elevator.moveElevatorPosition(Constants.sWitch);
				currTime = stopWatch.get() - prevTime;
				lValue = enc.leftEncoderValue - prevLeftEncoder;
				if(currTime < 0.13)
				{
					Drivetrain.jankStraight(gyro.yaw, .4);
				}
				else 
				{
					prevRightEncoder = enc.leftEncoderValue;
					Drivetrain.stop();
					IntakeWheels.manuallyRunIntake(-.32, -.32);
					//currentState = 6;
				}
				break;
		}
	}
	public static void middleAutoL2(Encoders enc, NavX gyro)
	{
		enc.setEncoderValues();
		gyro.setAngle();
		System.out.println(gyro.yaw);
		switch(currentState)
		{
			case 0:
			stopWatch.stop();
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
				double straightDist = 1800;
				Elevator.moveElevatorPosition(Constants.sWitch);
				if(enc.leftEncoderValue < straightDist)
				{
					Drivetrain.jankStraight(gyro.yaw, .4);
				}
				else 
				{
					prevLeftEncoder = enc.leftEncoderValue;
					prevTime = stopWatch.get();
					currentState = 3;
				}
				break;
			case 3:
				currTime = stopWatch.get() - prevTime;
				Elevator.moveElevatorPosition(Constants.sWitch);
				System.out.println(currTime);
                lValue = enc.leftEncoderValue - prevLeftEncoder;
                if(currTime < .66)
                {
                    Drivetrain.setSpeed(0.1, 0.58);
                }
				else 
				{
					Drivetrain.stop();
					prevTime = stopWatch.get();
					prevRightEncoder = enc.rightEncoderValue;
					currentState = 4;
				}
				break;
			case 4:
				straightDist = 1500;
				Elevator.moveElevatorPosition(Constants.sWitch);
				currTime = stopWatch.get() - prevTime;
				lValue = enc.leftEncoderValue - prevLeftEncoder;
				if(currTime < .342)
				{

					Drivetrain.setSpeed(.4,.4);
				}
				else 
				{
					prevRightEncoder = enc.leftEncoderValue;
					Drivetrain.stop();
					prevTime = stopWatch.get();
					currentState = 5;
				}
				break;
			case 5:
				//leftTurnDist = 2500;
				rValue = enc.rightEncoderValue - prevRightEncoder;
				Elevator.moveElevatorPosition(Constants.sWitch);
				System.out.println(currTime);
				currTime = stopWatch.get() - prevTime;
                if(currTime < .66)
                {
                    Drivetrain.setSpeed(0.613, 0.15);
                }
				else 
				{
					Drivetrain.stop();
					prevLeftEncoder = enc.leftEncoderValue;
					prevTime = stopWatch.get();
					currentState = 6;
				}
				break;
			case 6:
				straightDist = 1500;
				currTime = stopWatch.get() - prevTime;
				lValue = enc.leftEncoderValue - prevLeftEncoder;
				Elevator.moveElevatorPosition(Constants.sWitch);
				if(currTime < 0.23)
				{

					Drivetrain.jankStraight(gyro.yaw, .4);
				}
				else 
				{
					IntakeWheels.manuallyRunIntake(-.32, -.32);
					prevRightEncoder = enc.leftEncoderValue;
					Drivetrain.stop();
				}
				break;
		}
	}

}