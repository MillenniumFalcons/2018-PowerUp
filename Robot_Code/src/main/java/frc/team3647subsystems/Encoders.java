package frc.team3647subsystems;

public class Encoders 
{
	public double leftEncoderValue, rightEncoderValue;
	
	public void setEncoderValues()
	{
		leftEncoderValue = Drivetrain.leftSRX.getSensorCollection().getQuadraturePosition();
		rightEncoderValue = -Drivetrain.rightSRX.getSensorCollection().getQuadraturePosition();
	}
	
	public void resetEncoders()
	{
		Drivetrain.leftSRX.getSensorCollection().setQuadraturePosition(0, 10);
		Drivetrain.rightSRX.getSensorCollection().setQuadraturePosition(0, 10);
	}
	
	public void testEncoders()
	{
		System.out.println("Left Encoder Value: " + leftEncoderValue);
		System.out.println("Right Encoder Value: " + rightEncoderValue);
	}

}
