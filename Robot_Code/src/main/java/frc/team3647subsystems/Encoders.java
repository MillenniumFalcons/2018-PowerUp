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

}
