package frc.team3647autos;

import edu.wpi.first.wpilibj.Timer;
import frc.team3647ConstantsAndFunctions.Constants;
import frc.team3647elevator.Elevator;
import frc.team3647elevator.IntakeWheels;
import frc.team3647elevator.Wrist;
import frc.team3647pistons.Forks;
import frc.team3647pistons.Intake;
import frc.team3647pistons.Shifter;
import frc.team3647subsystems.Drivetrain;
import frc.team3647subsystems.Encoders;
import frc.team3647subsystems.NavX;

public class MiddleAutos 
{
    //Timer-Stuff
    public static Timer stopWatch = new Timer();
    
    static double lSSpeed, rSSpeed, speed, sum, rValue, lValue;
    static double currTime, prevTime, aimAngle;
    static double prevLeftEncoder, prevRightEncoder;
    
    public static int currentState;

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
    
    public static void smallBrainRightSide(Encoders enc, NavX gyro)
    {
        enc.setEncoderValues();
		gyro.setAngle();
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
					gyro.resetAngle();
					stopWatch.reset();
					//Wrist.wristMotor.getSensorCollection().setQuadraturePosition(Constants.up, 10);
				}
                break;
            case 1:
                aimAngle = 45;
                if(gyro.yaw < (aimAngle - 13))
                {
                    Drivetrain.setSpeed(.5, 0);
                }
                else if(gyro.yaw < (aimAngle - 3))
                {
                    Drivetrain.setSpeed(.3, 0);
                }
                else 
                {
                    prevLeftEncoder = enc.leftEncoderValue;
                    currentState = 2;
                }
                break;
            case 2:
                double straightDistBeforeTurn = 6000;
                lValue = enc.leftEncoderValue - prevLeftEncoder;
                if(lValue < straightDistBeforeTurn - 2000)
                {
                    Drivetrain.straight(.6, gyro.yaw, aimAngle);
                }
                else if(lValue < straightDistBeforeTurn)
                {
                    speed = slowDown(.2, .6, straightDistBeforeTurn - 2000, straightDistBeforeTurn, lValue);
                    Drivetrain.straight(speed, gyro.yaw, aimAngle);
                }
                else 
                {
                    currentState = 3;
                }
                break;
            case 3:
                aimAngle = 0;
                if(gyro.yaw > (aimAngle + 13))
                {
                    Drivetrain.setSpeed(0, .5);
                }
                else if(gyro.yaw > (aimAngle + 3))
                {
                    Drivetrain.setSpeed(0, .3);
                }
                else 
                {
                    prevRightEncoder = enc.rightEncoderValue;
                    currentState = 4;
                }
                break;
            case 4:
                rValue = enc.rightEncoderValue - prevRightEncoder;
                double straightToSwitch = 5000;
                if(rValue < straightToSwitch - 2000)
                {
                    Drivetrain.straight(.6, gyro.yaw, aimAngle);
                }
                else if(rValue < straightToSwitch)
                {
                    speed = slowDown(.2, .6, straightToSwitch - 2000, straightToSwitch, rValue);
                    Drivetrain.straight(speed, gyro.yaw, aimAngle);
                }
                else 
                {
                    Drivetrain.stop();
                    prevTime = stopWatch.get();
                    currentState = 5;
                }
                break;
            case 5:
                currTime = stopWatch.get() - prevTime;
                if(currTime > .7)
                {
                    currentState = 6;
                }
                else 
                {
                    enc.resetEncoders();
                    // shoot the cube omegalul
                }
                break;
            case 6:
                double backUp = 5000;
                rValue = Math.abs(enc.rightEncoderValue);
                if(rValue < backUp - 2000)
                {
                    Drivetrain.straight(-.6, gyro.yaw, aimAngle);
                }
                else if(rValue < backUp)
                {
                    speed = slowDown(.2, .6, backUp - 2000, backUp, rValue);
                    Drivetrain.straight(-speed, gyro.yaw, aimAngle);
                }
                else 
                {
                    prevRightEncoder = rValue;
                }
                break;
            case 7:
                aimAngle = 45;
                if(gyro.yaw < (aimAngle - 13))
                {
                    Drivetrain.setSpeed(0, -.5);
                }
                else if(gyro.yaw < (aimAngle - 3))
                {
                    Drivetrain.setSpeed(0, -.3);
                }
                else 
                {
                    prevRightEncoder = Math.abs(enc.rightEncoderValue);
                    currentState = 8;
                }
                break;
            case 8:
                double backDistBeforeTurn = 6000;
                rValue = Math.abs(enc.rightEncoderValue);
                rValue -= prevRightEncoder;
                if(rValue < backDistBeforeTurn - 2000)
                {
                    Drivetrain.straight(-.6, gyro.yaw, aimAngle);
                }
                else if(rValue < backDistBeforeTurn)
                {
                    speed = slowDown(.2, .6, backDistBeforeTurn - 2000, backDistBeforeTurn, rValue);
                    Drivetrain.straight(-speed, gyro.yaw, aimAngle);
                }
                else 
                {
                    currentState = 9;
                }
                break;
            case 9:
                aimAngle = 0;
                if(gyro.yaw > (aimAngle + 13))
                {
                    Drivetrain.setSpeed(-.5, 0);
                }
                else if(gyro.yaw > (aimAngle + 3))
                {
                    Drivetrain.setSpeed(-.3, 0);
                }
                else 
                {
                    Drivetrain.stop();
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
	}

}