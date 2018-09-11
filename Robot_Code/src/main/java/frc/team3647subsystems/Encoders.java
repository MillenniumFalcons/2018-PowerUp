package frc.team3647Subsystems;
import frc.team3647ConstantsAndFunctions.*;

public class Encoders 
{
	public int leftEncoderValue, rightEncoderValue, leftEncoderVelocity, rightEncoderVelocity, leftEncoderCLError, rightEncoderCLError;
	
	public void setEncoderValues()
	{
		leftEncoderValue = Drivetrain.leftSRX.getSelectedSensorPosition(Constants.drivePID);
		rightEncoderValue = Drivetrain.rightSRX.getSelectedSensorPosition(Constants.drivePID);

		leftEncoderVelocity = Drivetrain.leftSRX.getSelectedSensorVelocity(Constants.drivePID);
		rightEncoderVelocity = Drivetrain.rightSRX.getSelectedSensorVelocity(Constants.drivePID);
		
		leftEncoderCLError = Drivetrain.leftSRX.getClosedLoopError(Constants.drivePID);
		rightEncoderCLError = Drivetrain.rightSRX.getClosedLoopError(Constants.drivePID);
	}
	
	public void resetEncoders()
	{
		Drivetrain.leftSRX.setSelectedSensorPosition(0, Constants.drivePID, Constants.kTimeoutMs);
		Drivetrain.rightSRX.setSelectedSensorPosition(0, Constants.drivePID, Constants.kTimeoutMs);
	}
	
	public void testEncoders()
	{
		System.out.println("Left Encoder Value: " + leftEncoderValue);
		System.out.println("Right Encoder Value: " + rightEncoderValue);
	}

	public void testEncoderVelocity()
	{
		System.out.println("Left Encoder Velocity: " + leftEncoderVelocity);
		System.out.println("Right Encoder Velocity: " + rightEncoderVelocity);
	}

	public void testEncoderCLError()
	{
		System.out.println("Left Encoder CL Error: " + leftEncoderCLError);
		System.out.println("Right Encoder CL Error: " + rightEncoderCLError);
	}

}
