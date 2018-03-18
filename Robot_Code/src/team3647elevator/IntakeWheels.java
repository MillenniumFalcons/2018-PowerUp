package team3647elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import team3647ConstantsAndFunctions.Constants;

public class IntakeWheels 
{
	public static VictorSPX rightIntakeMotor = new VictorSPX(Constants.rightIntakePin);
	public static VictorSPX leftIntakeMotor = new VictorSPX(Constants.leftIntakePin);
	
	public static void runIntake(double lTrigger, double rTrigger, boolean auto, double lSpeed, double rSpeed)
	{
		if(!auto)
		{
			if(lTrigger > 0)//pickUp
			{	
				rightIntakeMotor.set(ControlMode.PercentOutput, -lTrigger*.8);
				leftIntakeMotor.set(ControlMode.PercentOutput, -lTrigger*.8);
			}
			else if(rTrigger > 0)//shoot
			{
				rightIntakeMotor.set(ControlMode.PercentOutput, rTrigger);
				leftIntakeMotor.set(ControlMode.PercentOutput, rTrigger);
			}
			else
			{
				rightIntakeMotor.set(ControlMode.PercentOutput, -.2);
				leftIntakeMotor.set(ControlMode.PercentOutput, -.2);
			}
		}
		else
		{
			rightIntakeMotor.set(ControlMode.PercentOutput, rSpeed);
			leftIntakeMotor.set(ControlMode.PercentOutput, lSpeed);
		}
	}
}
