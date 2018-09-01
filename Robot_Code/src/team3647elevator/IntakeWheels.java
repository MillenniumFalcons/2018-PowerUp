package team3647elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import team3647ConstantsAndFunctions.Constants;
import team3647pistons.Intake;

public class IntakeWheels 
{
	public static VictorSPX rightIntakeMotor = new VictorSPX(Constants.rightIntakePin);
	public static VictorSPX leftIntakeMotor = new VictorSPX(Constants.leftIntakePin);
	public static DigitalInput bannerSensor = new DigitalInput(Constants.intakeBannerSensor);
	
	public static void runIntake(double lTrigger, double rTrigger, boolean auto, double lSpeed, double rSpeed)
	{
		if(!auto)
		{
			if(lTrigger > 0)//shoot
			{	
				rightIntakeMotor.set(ControlMode.PercentOutput, -lTrigger *1);
				leftIntakeMotor.set(ControlMode.PercentOutput, -lTrigger *1);
			}
			else if(rTrigger > 0)//intake
			{
				rightIntakeMotor.set(ControlMode.PercentOutput, rTrigger*0.8);
				leftIntakeMotor.set(ControlMode.PercentOutput, rTrigger*.7);
			}
			else
			{
				rightIntakeMotor.set(ControlMode.PercentOutput, 0);
				leftIntakeMotor.set(ControlMode.PercentOutput, 0);
			}
		
		}
		else
		{
			rightIntakeMotor.set(ControlMode.PercentOutput, rSpeed);
			leftIntakeMotor.set(ControlMode.PercentOutput, lSpeed);
		}
	}
	
	public static void shoot(double speed)
	{
		runIntake(0, 0, true, speed, speed);
	}
	
	public static void pickUp(double speed)
	{
		runIntake(0, 0, true, -speed, -speed);
	}
	
	public static void manuallyRunIntake(double lSpeed, double rSpeed)
	{
		runIntake(0, 0, true, lSpeed, rSpeed);
	}
	
	public static boolean getIntakeBannerSensor()
	{
		return bannerSensor.get();
	}
	public static void testBannerSensor()
	{
		if(getIntakeBannerSensor())
		{
			System.out.println("Intake Banner = True");
		} 
		else
		{
			System.out.println("Intake Banner = False");
		}
	}
}

