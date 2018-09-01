package team3647subsystems;

//import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.*;
import team3647ConstantsAndFunctions.Constants;

public class Drivetrain 
{
	public static double aimedRatio, currentRatio, sum;
	public static boolean withinRange;
	
	public static double initialCorrection = 0;//-.04//.085
	public static double correction = .08;
	
	public static WPI_TalonSRX leftSRX = new WPI_TalonSRX(Constants.leftMaster);
	public static WPI_TalonSRX rightSRX = new WPI_TalonSRX(Constants.rightMaster);
	
	public static VictorSPX leftSPX1 = new VictorSPX(Constants.leftSlave1);
	public static VictorSPX rightSPX1 = new VictorSPX(Constants.rightSlave1);
	public static VictorSPX leftSPX2 = new VictorSPX(Constants.leftSlave2);
	public static VictorSPX rightSPX2 = new VictorSPX(Constants.rightSlave2);
	
	public static DifferentialDrive drive = new DifferentialDrive(leftSRX, rightSRX);
	
	public static double adjustmentFactor = .88;
	
	static double []adjustmentValues = new double[2];
	
	public static void drivetrainInitialization()
	{
		//tankDrive(0,0);
		leftSPX1.follow(leftSRX);
		leftSPX2.follow(leftSRX);    
		rightSPX1.follow(rightSRX);
		rightSPX2.follow(rightSRX);
	}
	
	static double avg;

	
	public static void FRCarcadedrive(double yValue, double xValue)
	{
		drive.arcadeDrive(yValue, xValue, false);
	}
		
	public static void tankDrive(double lYValue, double rYValue)
	{
		drive.tankDrive(lYValue * .986, rYValue, false);
		//drive.tankDrive(lYValue, rYValue, false);
	}
	
	public static void setSpeed(double lSpeed, double rSpeed)
	{
		leftSRX.set(ControlMode.Velocity, lSpeed);
		rightSRX.set(ControlMode.Velocity, rSpeed);
	}
	
//	public static void driveForw(double lValue, double rValue, double speed)
//	{
//		if(Math.abs(lValue - rValue) < 40)
//		{
//			tankDrive(speed, speed);
//		}
//		else if(rValue > lValue)
//		{
//			if(Math.abs(lValue - rValue) < 60)
//			{
//				tankDrive(speed, speed - .07);
//			}
//			else if(Math.abs(lValue - rValue) < 80)
//			{
//				tankDrive(speed, speed - .09);
//			}
//			else if(Math.abs(lValue - rValue) < 100)
//			{
//				tankDrive(speed, speed - .11);
//			}
//			else if(Math.abs(lValue - rValue) < 120)
//			{
//				tankDrive(speed, speed - .13);
//			}
//			else if(Math.abs(lValue - rValue) < 140)
//			{
//				tankDrive(speed, speed - .15);
//			}
//			else if(Math.abs(lValue - rValue) < 160)
//			{
//				tankDrive(speed, speed - .17);
//			}
//			else if(Math.abs(lValue - rValue) < 180)
//			{
//				tankDrive(speed, speed - .19);
//			}
//			else if(Math.abs(lValue - rValue) < 200)
//			{
//				tankDrive(speed, speed - .21);
//			}
//			else if(Math.abs(lValue - rValue) < 220)
//			{
//				tankDrive(speed, speed - .23);
//			}
//			else if(Math.abs(lValue - rValue) < 240)
//			{
//				tankDrive(speed, speed - .25);
//			}
//			else
//			{
//				tankDrive(speed, speed - .35);
//			}
//		}
//		else //rValue < lValue
//		{
//			if(Math.abs(lValue - rValue) < 60)
//			{
//				tankDrive(speed - .07, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 80)
//			{
//				tankDrive(speed -.09, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 100)
//			{
//				tankDrive(speed -.11, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 120)
//			{
//				tankDrive(speed - .13, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 140)
//			{
//				tankDrive(speed - .15, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 160)
//			{
//				tankDrive(speed - .17, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 180)
//			{
//				tankDrive(speed - .19, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 200)
//			{
//				tankDrive(speed - .21, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 220)
//			{
//				tankDrive(speed - .23, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 240)
//			{
//				tankDrive(speed - .25, speed);
//			}
//			else
//			{
//				tankDrive(speed - .35, speed);
//			}
//		}
//	}
//	
//	public static void driveBack(double lValue, double rValue, double speed)
//	{
//		lValue = Math.abs(lValue);
//		rValue = Math.abs(rValue);
//		
//		if(Math.abs(lValue - rValue) < 40)
//		{
//			tankDrive(speed, (speed - initialCorrection));
//		}
//		else if(rValue > lValue)
//		{
//			if(Math.abs(lValue - rValue) < 60)
//			{
//				tankDrive(speed, speed + .07);
//			}
//			else if(Math.abs(lValue - rValue) < 80)
//			{
//				tankDrive(speed, speed + .09);
//			}
//			else if(Math.abs(lValue - rValue) < 100)
//			{
//				tankDrive(speed, speed + .11);
//			}
//			else if(Math.abs(lValue - rValue) < 120)
//			{
//				tankDrive(speed, speed + .13);
//			}
//			else if(Math.abs(lValue - rValue) < 140)
//			{
//				tankDrive(speed, speed + .15);
//			}
//			else if(Math.abs(lValue - rValue) < 160)
//			{
//				tankDrive(speed, speed + .17);
//			}
//			else if(Math.abs(lValue - rValue) < 180)
//			{
//				tankDrive(speed, speed + .19);
//			}
//			else if(Math.abs(lValue - rValue) < 200)
//			{
//				tankDrive(speed, speed + .21);
//			}
//			else if(Math.abs(lValue - rValue) < 220)
//			{
//				tankDrive(speed, speed + .23);
//			}
//			else if(Math.abs(lValue - rValue) < 240)
//			{
//				tankDrive(speed, speed + .25);
//			}
//			else
//			{
//				tankDrive(speed, speed + .35);
//			}
//		}
//		else //rValue < lValue
//		{
//			if(Math.abs(lValue - rValue) < 60)
//			{
//				tankDrive(speed + .07, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 80)
//			{
//				tankDrive(speed +.09, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 100)
//			{
//				tankDrive(speed + .11, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 120)
//			{
//				tankDrive(speed + .13, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 140)
//			{
//				tankDrive(speed + .15, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 160)
//			{
//				tankDrive(speed + .17, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 180)
//			{
//				tankDrive(speed + .19, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 200)
//			{
//				tankDrive(speed + .21, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 220)
//			{
//				tankDrive(speed + .23, speed);
//			}
//			else if(Math.abs(lValue - rValue) < 240)
//			{
//				tankDrive(speed + .25, speed);
//			}
//			else
//			{
//				tankDrive(speed + .35, speed);
//			}
//		}
//	}
	
//	public static double keepMotorInPlace(double supposedValue, double eValue)
//	{
//		if(Math.abs(supposedValue - eValue) < 50)
//		{
//			return 0;
//		}
//		else if(eValue > supposedValue)
//		{
//			return -.2;
//		}
//		else
//		{
//			return .15;
//		}
//	}
	
//	public static void  driveBack(double lValue, double rValue, double speed)
//	{
//		lValue = Math.abs(lValue);
//		rValue = Math.abs(rValue);
//		
//		if(Math.abs(lValue - rValue) < 30)
//		{
//			FRCarcadedrive(speed, -initialCorrection);
//		}
//		else if(rValue > lValue)
//		{
//			if(Math.abs(lValue - rValue) < 45)
//			{
//				FRCarcadedrive(speed, -initialCorrection - (1 * correction));
//			}
//			else if(Math.abs(lValue - rValue) < 60)
//			{
//				FRCarcadedrive(speed, -initialCorrection - (2 * correction));
//			}
//			else if(Math.abs(lValue - rValue) < 80)
//			{
//				FRCarcadedrive(speed, -initialCorrection - (3 * correction));
//			}
//			else if(Math.abs(lValue - rValue) < 100)
//			{
//				FRCarcadedrive(speed, -initialCorrection - (4 * correction));
//			}
//			else if(Math.abs(lValue - rValue) < 125)
//			{
//				FRCarcadedrive(speed, -initialCorrection - (5 * correction));
//			}
//			else if(Math.abs(lValue - rValue) < 150)
//			{
//				FRCarcadedrive(speed, -initialCorrection - (6 * correction));
//			}
//			else
//			{
//				FRCarcadedrive(speed, -initialCorrection - (8 * correction));
//			}
//		}
//		else
//		{
//			if(Math.abs(lValue - rValue) < 45)
//			{
//				FRCarcadedrive(speed, -initialCorrection + (1 * correction));
//			}
//			else if(Math.abs(lValue - rValue) < 60)
//			{
//				FRCarcadedrive(speed, -initialCorrection + (2 * correction));
//			}
//			else if(Math.abs(lValue - rValue) < 80)
//			{
//				FRCarcadedrive(speed, -initialCorrection + (3 * correction));
//			}
//			else if(Math.abs(lValue - rValue) < 100)
//			{
//				FRCarcadedrive(speed, -initialCorrection + (4 * correction));
//			}
//			else if(Math.abs(lValue - rValue) < 125)
//			{
//				FRCarcadedrive(speed, -initialCorrection + (5 * correction));
//			}
//			else if(Math.abs(lValue - rValue) < 150)
//			{
//				FRCarcadedrive(speed, -initialCorrection + (6 * correction));
//			}
//			else
//			{
//				FRCarcadedrive(speed, -initialCorrection + (8 * correction));
//			}
//		}
//	}

	public static void newArcadeDrive(double yValue, double xValue)
	{
		if(yValue != 0 && xValue == 0)
	 	{
			tankDrive(yValue, yValue);
	 	}
		else if(yValue == 0 && xValue == 0)
		{
			tankDrive(0, 0);
		}
		else
		{
			drive.curvatureDrive(yValue, xValue, true);
		}
	}
	
	static double drift;
	static int movingStatus, driftStatus;
//	public static void arcadeDrive(double leftEnc, double rightEnc, double yValue, double xValue)
//	{
//		
//		//Moving Status
//		//Forward = 1
//		//Backward = 2
//		//Turning = 3
//		//Stop = 4
//		
//		//Drift Status
//		//Turn = 1
//		//noTurn = 0
//		
//		double lSpeed, rSpeed;
//	 	if(yValue > 0 && xValue == 0)
//	 	{
//	 		movingStatus = 1;
//	 		if(driftStatus == 1)
//	 		{
//	 			drift++;
//	 		}
//	 		if(drift < 50 && driftStatus == 1)
//	 		{
//	 			Encoders.resetEncoders();
//	 		}
//	 		else
//	 		{
//	 			driftStatus = 0;
//	 		}
//	 	}
//	 	else if(yValue < 0 && xValue == 0)
//	 	{
//	 		movingStatus = 2;
//	 	}
//	 	else if(yValue == 0 && xValue == 0)
//	 	{
//	 		movingStatus = 4;
//	 		driftStatus = 1;
//	 	}
//	 	else
//	 	{
//	 		movingStatus = 3;
//	 		driftStatus = 1;
//	 	}
//	 		
//	 	switch(movingStatus)
//	 	{
//	 		case 1:
//	 			if(yValue < .15)
//	 			{
//	 				tankDrive(yValue, yValue);
//	 				Encoders.resetEncoders();
//	 			}
//	 			else
//	 			{
//	 				driveForw(leftEnc,rightEnc, yValue);
//	 			}
//	 			
//	 			break;
//	 		case 2:
//	 			if(yValue > -.15)
//	 			{
//		 			tankDrive(yValue, yValue);
//	 				Encoders.resetEncoders();
//	 			}
//	 			else
//	 			{
//	 				driveBack(leftEnc,rightEnc, yValue);
//	 			}
//	 			break;
//	 		case 3:
//	 			FRCarcadedrive(yValue, xValue);
//	 			Encoders.resetEncoders();
//	 			break;
//	 		case 4:
//	 			tankDrive(0, 0);
//	 			Encoders.resetEncoders();
//	 			break;
//	 	}
//	}
//	
//	public static boolean reachedDistance(double leftEnc, double rightEnc, double distance)
//	{
//		
//		avg = Math.abs(leftEnc) + Math.abs(rightEnc);
//		avg/=2;
//		if(avg<distance)
//		{
//			return false;
//		}
//		else
//		{
//			return true;
//		}
//	}
//	
//	public static boolean reachedTurnDistance(double sum, double requiredLeftDist, double requiredRightDist)
//	{
//		if(sum < requiredLeftDist + requiredRightDist)
//		{
//			return false;
//		}
//		else
//		{
//			return true;
//		}
//	}
//	
//	public static void goStraightLeft(double lValue, double rValue, double aimedRatio, double leftSpeed, double rightSpeed, double adjustment)
//	{
//		currentRatio = (((rValue)/(lValue))/aimedRatio);
//		sum = (rValue) + (lValue);
//		if(currentRatio >= .9 && currentRatio <= 1.1)
//		{
//			withinRange = true;
//		}
//		else
//		{
//			withinRange = false;
//		}
//		if(withinRange || sum < 360)
//		{
//			tankDrive(leftSpeed,rightSpeed);
//		}
//		else
//		{
//			if(currentRatio > 1.1 && currentRatio < 1.18)
//			{
//				tankDrive(leftSpeed + adjustment, (rightSpeed - adjustment));
//			}
//			else if(currentRatio > 1.18 && currentRatio < 1.25)
//			{
//				tankDrive(leftSpeed + (2*adjustment),(rightSpeed - (2*adjustment)));
//			}
//			else if(currentRatio > 1.25)
//			{
//				tankDrive(leftSpeed + (3*adjustment), (rightSpeed - (3*adjustment)));
//			}
//			else if(currentRatio < .9 && currentRatio > .82)
//			{
//				tankDrive(leftSpeed - adjustment, (rightSpeed + adjustment));
//			}
//			else if(currentRatio < .82 && currentRatio > .75)
//			{
//				tankDrive(leftSpeed - (2*adjustment), (rightSpeed + (2*adjustment)));
//			}
//			else
//			{
//				tankDrive(leftSpeed - (3*adjustment), (rightSpeed + (3*adjustment)));
//			}
//		}
//	}
//	
//	public static void goStraightRight(double lValue, double rValue, double aimedRatio, double leftSpeed, double rightSpeed, double adjustment)
//	{
//		currentRatio = (((lValue)/(rValue))/aimedRatio);
//		sum = (rValue) + (lValue);
//		if(currentRatio >= .9 && currentRatio <= 1.1)
//		{
//			withinRange = true;
//		}
//		else
//		{
//			withinRange = false;
//		}
//		if(withinRange || sum < 360)
//		{
//			tankDrive(leftSpeed,rightSpeed);
//		}
//		else
//		{
//			if(currentRatio > 1.1 && currentRatio < 1.18)
//			{
//				tankDrive(leftSpeed - adjustment,(rightSpeed + adjustment));
//			}
//			else if(currentRatio > 1.18 && currentRatio < 1.25)
//			{
//				tankDrive(leftSpeed - (2*adjustment),(rightSpeed + (2*adjustment)));
//			}
//			else if(currentRatio > 1.25)
//			{
//				tankDrive(leftSpeed - (3*adjustment),(rightSpeed + (3*adjustment)));
//			}
//			else if(currentRatio < .9 && currentRatio > .82)
//			{
//				tankDrive(leftSpeed + adjustment,(rightSpeed - adjustment));
//			}
//			else if(currentRatio < .82 && currentRatio > .75)
//			{
//				tankDrive(leftSpeed + (2*adjustment),(rightSpeed - (2*adjustment)));
//			}
//			else
//			{
//				tankDrive(leftSpeed + (3*adjustment),(rightSpeed - (3*adjustment)));
//			}
//		}
//	}
//	
//	public static void goBackLeft(double lValue, double rValue, double aimedRatio, double leftSpeed, double rightSpeed, double adjustment)
//	{
//		rValue = Math.abs(rValue);
//		lValue = Math.abs(lValue);
//		currentRatio = (((rValue)/(lValue))/aimedRatio);
//		sum = (rValue) + (lValue);
//		if(withinRange || sum < 50)
//		{
//			tankDrive(leftSpeed,rightSpeed);
//		}
//		else
//		{
//			if(currentRatio > 1.1 && currentRatio < 1.18)
//			{
//				tankDrive(leftSpeed - adjustment, (rightSpeed + adjustment));
//			}
//			else if(currentRatio > 1.18 && currentRatio < 1.25)
//			{
//				tankDrive(leftSpeed - (2*adjustment), (rightSpeed + (2*adjustment)));
//			}
//			else if(currentRatio > 1.25)
//			{
//				tankDrive(leftSpeed - (3*adjustment), (rightSpeed + (3*adjustment)));
//			}
//			else if(currentRatio < .9 && currentRatio > .82)
//			{
//				tankDrive(leftSpeed + adjustment, (rightSpeed - adjustment));
//			}
//			else if(currentRatio < .82 && currentRatio > .75)
//			{
//				tankDrive(leftSpeed + (2*adjustment), (rightSpeed - (2*adjustment)));
//			}
//			else
//			{
//				tankDrive(leftSpeed + (3*adjustment), (rightSpeed - (3*adjustment)));
//			}
//		}
//	}
//	
//	public static void goBackRight(double lValue, double rValue, double aimedRatio, double leftSpeed, double rightSpeed, double adjustment)
//	{
//		rValue = Math.abs(rValue);
//		lValue = Math.abs(lValue);
//		currentRatio = (((lValue)/(rValue))/aimedRatio);
//		sum = (rValue) + (lValue);
//		if(withinRange || sum < 50)
//		{
//			tankDrive(leftSpeed,rightSpeed);
//		}
//		else
//		{
//			if(currentRatio > 1.1 && currentRatio < 1.18)
//			{
//				tankDrive(leftSpeed + adjustment,(rightSpeed - adjustment));
//			}
//			else if(currentRatio > 1.18 && currentRatio < 1.25)
//			{
//				tankDrive(leftSpeed + (2*adjustment), (rightSpeed - (2*adjustment)));
//			}
//			else if(currentRatio > 1.25)
//			{	
//				tankDrive(leftSpeed + (3*adjustment), (rightSpeed - (3*adjustment)));
//			}
//			else if(currentRatio < .9 && currentRatio > .82)
//			{
//				tankDrive(leftSpeed - adjustment, (rightSpeed + adjustment));
//			}
//			else if(currentRatio < .82 && currentRatio > .75)
//			{
//				tankDrive(leftSpeed - (2*adjustment), (rightSpeed + (2*adjustment)));
//			}
//			else
//			{
//				tankDrive(leftSpeed - (3*adjustment), (rightSpeed + (3*adjustment)));
//			}
//		}
//	}
	
	public static void stop()
	{
		tankDrive(0,0);
	}
	
	public static void testDrivetrainCurrent()
	{
		System.out.println("Left Motor Current: " + leftSRX.getOutputCurrent());
		System.out.println("Right Motor Current:" + rightSRX.getOutputCurrent());
	}
	
	public static void enableCurrentLimiting(double amps)
	{
		leftSRX.enableCurrentLimit(true);
		rightSRX.enableCurrentLimit(true);
//		leftSRX.configPeakCurrentLimit(amps, 10);
//		rightSRX.configPeakCurrentLimit(amps, 10);
	}
	
	public static void setToBrake()
	{
		leftSRX.setNeutralMode(NeutralMode.Brake);
		rightSRX.setNeutralMode(NeutralMode.Brake);
		leftSPX1.setNeutralMode(NeutralMode.Brake);
		leftSPX2.setNeutralMode(NeutralMode.Brake);
		rightSPX1.setNeutralMode(NeutralMode.Brake);
		rightSPX2.setNeutralMode(NeutralMode.Brake);
	}
	
	public static void setToCoast()
	{
		leftSRX.setNeutralMode(NeutralMode.Coast);
		rightSRX.setNeutralMode(NeutralMode.Coast);
		leftSPX1.setNeutralMode(NeutralMode.Coast);
		leftSPX2.setNeutralMode(NeutralMode.Coast);
		rightSPX1.setNeutralMode(NeutralMode.Coast);
		rightSPX2.setNeutralMode(NeutralMode.Coast);
	}
}
