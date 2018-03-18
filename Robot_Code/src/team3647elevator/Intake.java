package team3647elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;

public class Intake 
{
	public static VictorSPX rightIntakeMotor = new VictorSPX(56);
	public static VictorSPX leftIntakeMotor = new VictorSPX(55);
	
//	public static DigitalInput bannerSensor = new DigitalInput(99); 
	
	public static void runIntake(double lTrigger, double rTrigger)
	{
		if(lTrigger > 0)
		{	
			rightIntakeMotor.set(ControlMode.PercentOutput, -lTrigger*.8);
			leftIntakeMotor.set(ControlMode.PercentOutput, -lTrigger*.8);
		}
		else if(rTrigger > 0)
		{
			rightIntakeMotor.set(ControlMode.PercentOutput, rTrigger);
			leftIntakeMotor.set(ControlMode.PercentOutput, rTrigger);
		}
		else
		{
			rightIntakeMotor.set(ControlMode.PercentOutput, -.1);
			leftIntakeMotor.set(ControlMode.PercentOutput, -.1);
		}
			
		
	}
	
	public static void shootCube()
	{
		runIntake(0, 1);
	}
	
	public static void stopIntake()
	{
		runIntake(0, 0);
	}
	
	public static void pickUpCube()
	{
		runIntake(1, 0);
	}
}
