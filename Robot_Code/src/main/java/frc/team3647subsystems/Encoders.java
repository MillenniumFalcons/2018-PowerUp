package frc.team3647subsystems;

import frc.team3647ConstantsAndFunctions.Constants;

public class Encoders 
{
	public double leftEncoderValue, rightEncoderValue;
	
	public void setEncoderValues()
	{
		leftEncoderValue = Drivetrain.leftSRX.getSelectedSensorPosition(Constants.drivePID);
		rightEncoderValue = Drivetrain.rightSRX.getSelectedSensorPosition(Constants.drivePID);
	}
	
	public void resetEncoders()
	{
		Drivetrain.leftSRX.setSelectedSensorPosition(0, Constants.drivePID, Constants.kTimeoutMs);
		Drivetrain.rightSRX.setSelectedSensorPosition(0, Constants.drivePID, Constants.kTimeoutMs);
	}

	public void testEncodersWithDrive(boolean jValue)
	{
		testEncoders();
		if(jValue)
		{
			resetEncoders();
		}
	}
	
	public void testEncoders()
	{
		System.out.println("Left Encoder Value: " + leftEncoderValue);
		System.out.println("Right Encoder Value: " + rightEncoderValue);
	}

	public void testEncoderVelocity()
	{
		System.out.println("Left Encoder Velocity: " + Drivetrain.leftSRX.getSelectedSensorVelocity(Constants.drivePID));
		System.out.println("Right Encoder Velocity: " + Drivetrain.rightSRX.getSelectedSensorVelocity(Constants.drivePID));
	}
 	public void testEncoderCLError()
	{
		System.out.println("Left Encoder CL Error: " + Drivetrain.leftSRX.getClosedLoopError(Constants.drivePID));
		System.out.println("Right Encoder CL Error: " + Drivetrain.rightSRX.getClosedLoopError(Constants.drivePID));
	}

}
