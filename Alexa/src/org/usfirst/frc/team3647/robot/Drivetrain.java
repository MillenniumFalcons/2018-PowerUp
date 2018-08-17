package org.usfirst.frc.team3647.robot;


import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Drivetrain 
{
	public static WPI_TalonSRX leftSRX = new WPI_TalonSRX(Constants.leftMaster);
	public static WPI_TalonSRX rightSRX = new WPI_TalonSRX(Constants.rightMaster);
	
	
	public static VictorSPX leftSPX1 = new VictorSPX(Constants.leftSlave1);
	public static VictorSPX rightSPX1 = new VictorSPX(Constants.rightSlave1);
	public static VictorSPX leftSPX2 = new VictorSPX(Constants.leftSlave2);
	public static VictorSPX rightSPX2 = new VictorSPX(Constants.rightSlave2);
	
	public static void drivetrainInitialization()
	{
		//tankDrive(0,0);
		leftSPX1.follow(leftSRX);
		leftSPX2.follow(leftSRX);    
		rightSPX1.follow(rightSRX);
		rightSPX2.follow(rightSRX);
	}
	
	public static void tankDrive(double lYValue, double rYValue)
	{
		leftSRX.set(ControlMode.PercentOutput , lYValue);
		rightSRX.set(ControlMode.PercentOutput , rYValue);
	}
	
}
