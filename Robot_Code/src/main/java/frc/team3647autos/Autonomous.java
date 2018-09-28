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

    public static void jonSmallestBrain(Encoders enc, NavX navX)
    {
        enc.setEncoderValues();
        navX.setAngle();
        switch(currentState)
        {
            case 0:
                enc.resetEncoders();
                navX.resetAngle();
                System.out.println("Loading Path");
                traj.initialize();
                //traj.followPath("leftSwitchFromMiddle1", false, false);   
                //traj.followPath(WaypointPaths.middleToRightSwitch(), false);
                traj.followPath("RightMiddleToSwitch", false, false);
                //traj.followPath("StraightTenFeet", false);
               // traj.followPath("SuryaOmegaLul", false);
                //traj.followPath("StraightandLeftCurve", false);
                currentState = 1;
                break;
            case 1:
            System.out.println("Running Path (1/2)");
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
                if(traj.isFinished())
                {
                    System.out.println("Path Finished (2/2)");
                    currentState = 2;
                }
                else
                {
                    System.out.println("PATH NOT FINISHED");
                }
                break;
            case 2:
                System.out.println("CASE 2 REACHED (path finished)");
                break;
        }
    }

    // public static void rightSide2SwitchFromRightSide(Encoders enc, NavX gyro)
    // {
    //     enc.setEncoderValues();
    //     gyro.setAngle();
    //     switch(currentState)
    //     {
    //         case 0:
    //             // Zeroing all sensors
    //             if(gyro.yawUnClamped == 0 && enc.rightEncoderValue == 0 && enc.leftEncoderValue == 0)
    //             {
    //                 stopWatch.start();
    //                 currentState = 1;
    //             }
    //             else 
    //             {
    //                 gyro.resetAngle();
    //             }
    //             break;
    //         case 1:
    //             // Loading the path
    //             traj.followPath("rightSide2SwitchFromRightSide", false, false);
    //             currentState = 2;
    //             break;
    //         case 2:
    //             if(traj.isFinished())
    //             {
    //                Drivetrain.stop();
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //     }
    // }

    // public static void leftSide2SwitchFromRightSide(Encoders enc, NavX gyro)
    // {
    //     enc.setEncoderValues();
    //     gyro.setAngle();
    //     switch(currentState)
    //     {
    //         case 0:
    //             // Zeroing all sensors
    //             if(gyro.yawUnClamped == 0 && enc.rightEncoderValue == 0 && enc.leftEncoderValue == 0)
    //             {
    //                 stopWatch.start();
    //                 currentState = 1;
    //             }
    //             else 
    //             {
    //                 gyro.resetAngle();
    //             }
    //             break;
    //         case 1:
    //             // Loading the path
    //             traj.followPath("leftSide2SwitchFromRightSide", false, false);
    //             currentState = 2;
    //             break;
    //         case 2:
    //             if(traj.isFinished())
    //             {
    //                Drivetrain.stop();
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //     }
    // }

    // public static void jankOppositeScaleFromRight(Encoders enc, NavX gyro)
    // {
    //     enc.setEncoderValues();
    //     gyro.setAngle();
    //     switch(currentState)
    //     {
    //         case 0:
    //             // Zeroing all sensors
    //             if(gyro.yawUnClamped == 0 && enc.rightEncoderValue == 0 && enc.leftEncoderValue == 0)
    //             {
    //                 stopWatch.start();
    //                 currentState = 1;
    //             }
    //             else 
    //             {
    //                 gyro.resetAngle();
    //             }
    //             break;
    //         case 1:
    //             // Loading the path
    //             traj.followPath("jankOppositeScaleFromRight", false, false);
    //             currentState = 2;
    //             break;
    //         case 2:
    //             if(traj.isFinished())
    //             {
    //                Drivetrain.stop();
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //     }
    // }

    // public static void sharpOppositeScaleFromRight(Encoders enc, NavX gyro)
    // {
    //     enc.setEncoderValues();
    //     gyro.setAngle();
    //     switch(currentState)
    //     {
    //         case 0:
    //             // Zeroing all sensors
    //             if(gyro.yawUnClamped == 0 && enc.rightEncoderValue == 0 && enc.leftEncoderValue == 0)
    //             {
    //                 stopWatch.start();
    //                 currentState = 1;
    //             }
    //             else 
    //             {
    //                 gyro.resetAngle();
    //             }
    //             break;
    //         case 1:
    //             // Loading the path
    //             traj.followPath("sharpOppositeScaleFromRight", false, false);
    //             currentState = 2;
    //             break;
    //         case 2:
    //             if(traj.isFinished())
    //             {
    //                Drivetrain.stop();
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //     }
    // }

    // public static void leftSwitchFromMiddle(Encoders enc, NavX gyro)
    // {
    //     enc.setEncoderValues();
    //     gyro.setAngle();
    //     switch(currentState)
    //     {
    //         case 0:
    //             // Zeroing all sensors
    //             if(gyro.yawUnClamped == 0 && enc.rightEncoderValue == 0 && enc.leftEncoderValue == 0)
    //             {
    //                 stopWatch.start();
    //                 currentState = 1;
    //             }
    //             else 
    //             {
    //                 gyro.resetAngle();
    //             }
    //             break;
    //         case 1:
    //             // Loading the path
    //             traj.followPath("leftSwitchFromMiddle1", false, false);
    //             currentState = 2;
    //             break;
    //         case 2:
    //             if(traj.isFinished())
    //             {
    //                prevTime = stopWatch.get();
    //                currentState = 3;
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //         case 3:
    //             currTime = stopWatch.get() - prevTime;
    //             if(currTime < .4)
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             else 
    //             {
    //                 traj.followPath("leftSwitchFromMiddle2", false, false);
    //                 currentState = 4;
    //             }
    //             break;
    //         case 4:
    //             if(traj.isFinished())
    //             {
    //                 prevTime = stopWatch.get();
    //                 currentState = 5;
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //         case 5:
    //             currTime = stopWatch.get() - prevTime;
    //             if(currTime < .4)
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             else 
    //             {
    //                 traj.followPath("leftSwitchFromMiddle3", false, false);
    //                 currentState = 6;
    //             }
    //             break;
    //         case 6:
    //             if(traj.isFinished())
    //             {
    //                 prevTime = stopWatch.get();
    //                 currentState = 7;
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //         case 7:
    //             currTime = stopWatch.get() - prevTime;
    //             if(currTime < .4)
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             else 
    //             {
    //                 traj.followPath("leftSwitchFromMiddle4", false, false);
    //                 currentState = 8;
    //             }
    //             break;
    //         case 8:
    //             if(traj.isFinished())
    //             {
    //                 prevTime = stopWatch.get();
    //                 currentState = 9;
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //         case 9:
    //             currTime = stopWatch.get() - prevTime;
    //             if(currTime < .4)
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             else 
    //             {
    //                 traj.followPath("leftSwitchFromMiddle5", false, false);
    //                 currentState = 10;
    //             }
    //             break;
    //         case 10:
    //             if(traj.isFinished())
    //             {
    //                 prevTime = stopWatch.get();
    //                 currentState = 11;
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //         case 11:
    //             currTime = stopWatch.get() - prevTime;
    //             if(currTime < .4)
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             else 
    //             {
    //                 traj.followPath("leftSwitchFromMiddle2", false, false);
    //                 currentState = 12;
    //             }
    //             break;
    //         case 12:
    //             if(traj.isFinished())
    //             {
    //                 prevTime = stopWatch.get();
    //                 currentState = 13;
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //         case 13:
    //             currTime = stopWatch.get() - prevTime;
    //             if(currTime < .4)
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             else 
    //             {
    //                 traj.followPath("leftSwitchFromMiddle6", false, false);
    //                 currentState = 14;
    //             }
    //             break;
    //         case 14:
    //             if(traj.isFinished())
    //             {
    //                 prevTime = stopWatch.get();
    //                 currentState = 15;
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //         case 15:
    //             currTime = stopWatch.get() - prevTime;
    //             if(currTime < .4)
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             else 
    //             {
    //                 traj.followPath("leftSwitchFromMiddle7", false, false);
    //                 currentState = 16;
    //             }
    //             break;
    //         case 16:
    //             if(traj.isFinished())
    //             {
    //                 prevTime = stopWatch.get();
    //                 currentState = 17;
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //         case 17:
    //             currTime = stopWatch.get() - prevTime;
    //             if(currTime < .4)
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             else 
    //             {
    //                 traj.followPath("leftSwitchFromMiddle5", false, false);
    //                 currentState = 18;
    //             }
    //             break;
    //         case 18:
    //             if(traj.isFinished())
    //             {
    //                 Drivetrain.stop();
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //     }
    // }


    // public static void rightSwitchFromMiddle(Encoders enc, NavX gyro)
    // {
    //     enc.setEncoderValues();
    //     gyro.setAngle();
    //     switch(currentState)
    //     {
    //         case 0:
    //             // Zeroing all sensors
    //             if(gyro.yawUnClamped == 0 && enc.rightEncoderValue == 0 && enc.leftEncoderValue == 0)
    //             {
    //                 stopWatch.start();
    //                 currentState = 1;
    //             }
    //             else 
    //             {
    //                 gyro.resetAngle();
    //             }
    //             break;
    //         case 1:
    //             // Loading the path
    //             traj.followPath("rightSwitchFromMiddle1", false, false);
    //             currentState = 2;
    //             break;
    //         case 2:
    //             if(traj.isFinished())
    //             {
    //                prevTime = stopWatch.get();
    //                currentState = 3;
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //         case 3:
    //             currTime = stopWatch.get() - prevTime;
    //             if(currTime < .4)
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             else 
    //             {
    //                 traj.followPath("rightSwitchFromMiddle2", false, false);
    //                 currentState = 4;
    //             }
    //             break;
    //         case 4:
    //             if(traj.isFinished())
    //             {
    //                 prevTime = stopWatch.get();
    //                 currentState = 5;
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //         case 5:
    //             currTime = stopWatch.get() - prevTime;
    //             if(currTime < .4)
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             else 
    //             {
    //                 traj.followPath("rightSwitchFromMiddle3", false, false);
    //                 currentState = 6;
    //             }
    //             break;
    //         case 6:
    //             if(traj.isFinished())
    //             {
    //                 prevTime = stopWatch.get();
    //                 currentState = 7;
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //         case 7:
    //             currTime = stopWatch.get() - prevTime;
    //             if(currTime < .4)
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             else 
    //             {
    //                 traj.followPath("rightSwitchFromMiddle4", false, false);
    //                 currentState = 8;
    //             }
    //             break;
    //         case 8:
    //             if(traj.isFinished())
    //             {
    //                 prevTime = stopWatch.get();
    //                 currentState = 9;
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //         case 9:
    //             currTime = stopWatch.get() - prevTime;
    //             if(currTime < .4)
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             else 
    //             {
    //                 traj.followPath("rightSwitchFromMiddle5", false, false);
    //                 currentState = 10;
    //             }
    //             break;
    //         case 10:
    //             if(traj.isFinished())
    //             {
    //                 prevTime = stopWatch.get();
    //                 currentState = 11;
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //         case 11:
    //             currTime = stopWatch.get() - prevTime;
    //             if(currTime < .4)
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             else 
    //             {
    //                 traj.followPath("rightSwitchFromMiddle2", false, false);
    //                 currentState = 12;
    //             }
    //             break;
    //         case 12:
    //             if(traj.isFinished())
    //             {
    //                 prevTime = stopWatch.get();
    //                 currentState = 13;
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //         case 13:
    //             currTime = stopWatch.get() - prevTime;
    //             if(currTime < .4)
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             else 
    //             {
    //                 traj.followPath("rightSwitchFromMiddle6", false, false);
    //                 currentState = 14;
    //             }
    //             break;
    //         case 14:
    //             if(traj.isFinished())
    //             {
    //                 prevTime = stopWatch.get();
    //                 currentState = 15;
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //         case 15:
    //             currTime = stopWatch.get() - prevTime;
    //             if(currTime < .4)
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             else 
    //             {
    //                 traj.followPath("rightSwitchFromMiddle7", false, false);
    //                 currentState = 16;
    //             }
    //             break;
    //         case 16:
    //             if(traj.isFinished())
    //             {
    //                 prevTime = stopWatch.get();
    //                 currentState = 17;
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //         case 17:
    //             currTime = stopWatch.get() - prevTime;
    //             if(currTime < .4)
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             else 
    //             {
    //                 traj.followPath("rightSwitchFromMiddle5", false, false);
    //                 currentState = 18;
    //             }
    //             break;
    //         case 18:
    //             if(traj.isFinished())
    //             {
    //                 Drivetrain.stop();
    //             }
    //             else 
    //             {
    //                 traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
    //             }
    //             break;
    //     }
    // }
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
				//if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0 && stopWatch.get() == 0 && chechWristIdle(Wrist.wristEncoder))
				if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0 && stopWatch.get() == 0 && gyro.actualYaw == 0)
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
				//Wrist.moveUp();
				double straightDist = 18100;
				enc.dontSkip();
				if(enc.rightEncoderValue < 3000)
				{
					Drivetrain.setSpeed(.4, .4);
				}
				else if(enc.rightEncoderValue < ((2 * straightDist)/3.0))
				{
					//Wrist.moveUp();
					Drivetrain.jankStraight(gyro.yaw, .6);
				}
				else if(enc.rightEncoderValue < straightDist)
				{
					//Elevator.moveElevatorPosition(Constants.Switch);
					//moveWristDownWhileRunning();
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
				double turnDist = 9700;
				//Elevator.moveElevatorPosition(Constants.Switch);
                //moveWristDownWhileRunning();
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

					//currentState = 4;
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
    
	public static void chezyDoubleSwitchRightFromLeft(Encoders enc, NavX gyro)
	{
		enc.setEncoderValues();
		gyro.setAngle();
		enc.testEncoders();
		System.out.println(currentState);
		switch(currentState)
		{
			case 0:
				stopWatch.stop();
				//if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0 && stopWatch.get() == 0 && chechWristIdle(Wrist.wristEncoder))
				if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0 && stopWatch.get() == 0 && gyro.actualYaw == 0)
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
				//Wrist.moveUp();
				double straightDist = 18200;
				enc.dontSkip();
				if(enc.rightEncoderValue < 3000)
				{
					Drivetrain.setSpeed(.4, .4);
				}
				else if(enc.rightEncoderValue < ((2 * straightDist)/3.0))
				{
					//Wrist.moveUp();
					Drivetrain.jankStraight(gyro.yaw, .6);
				}
				else if(enc.rightEncoderValue < straightDist)
				{
					//Elevator.moveElevatorPosition(Constants.Switch);
					//moveWristDownWhileRunning();
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
				Elevator.moveSwitch();
				Wrist.moveToFlat();
				//System.out.println(gyro.actualYaw);
				//Elevator.moveElevatorPosition(Constants.Switch);
                //moveWristDownWhileRunning();
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
					//System.out.println("hbjdwhbde");
					Drivetrain.setSpeed(.4, .4);
					gyro.resetAngle();
				}
				else if(rValue < turnDist + straighttDist - 1600)
				{
				//	System.out.println("hbjdwhbde");
				Drivetrain.jankStraight(gyro.yaw, .7);
				}
				else if(rValue < turnDist + straighttDist)
				{
					//System.out.println("hbjdwhbde");
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

					//currentState = 4;
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
				double totalScaleDist = 26000;
				// if(enc.rightEncoderValue < (totalScaleDist/3.0))
				// {
				// 	//Wrist.moveUp();
				// 	speed = speedUp(.15, .8, 0, (totalScaleDist/3.0), enc.rightEncoderValue);
				// 	Drivetrain.setSpeed(speed, speed);
				// }
				// else 
				if(enc.rightEncoderValue < 3000)
				{
					Drivetrain.setSpeed(.4, .4);
				}
				else if(enc.rightEncoderValue < ((2 * totalScaleDist)/3.0))
				{
					//Elevator.moveElevatorPosition(Constants.Scale);
					//moveWristDownWhileRunning();
					Drivetrain.jankStraight(gyro.yaw, .8);
				}
				else if(enc.rightEncoderValue < totalScaleDist)
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
					prevRightEncoder = enc.rightEncoderValue;
					currentState = 3;
				}
				break;
			case 3:
				rValue = enc.rightEncoderValue - prevRightEncoder;
				//Elevator.moveElevatorPosition(Constants.Scale);
				//moveWristDownWhileRunning();
				System.out.println(rValue);
				double rotateRightDist = 3550;
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
				double spinDist = 2550;
				System.out.println(enc.rightEncoderValue);
				if(enc.rightEncoderValue < spinDist - 1300)
				{
					Drivetrain.setSpeed(-.5, .5);
					//Elevator.moveElevatorPosition(Constants.Scale);
				}
				else if(enc.rightEncoderValue < spinDist)
				{
					Drivetrain.setSpeed(-.2, .2);
					//IntakeWheels.runIntake(0, 0, true, 0, 0, false);
					//Elevator.moveBottom(false);
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
				double straightDistForCube = 8400;
				Wrist.moveToFlat();
				if(enc.rightEncoderValue < straightDistForCube - 1500)
				{
					Drivetrain.jankStraight(0, .4);
					IntakeWheels.runIntake(0, 0, true, .5, .5, false);
					Intake.openIntake();
				}
				else if(enc.rightEncoderValue < straightDistForCube)
				{
					Drivetrain.jankStraight(0, .3);
					IntakeWheels.runIntake(0, 0, true, .5, .5, false);
					Intake.openIntake();
				}
				else 
				{
					Drivetrain.stop();
					IntakeWheels.runIntake(0, 0, true, 0, 0, false);
					Intake.closeIntake();
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
