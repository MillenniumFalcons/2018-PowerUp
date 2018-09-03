package frc.team3647pistons;

import frc.team3647ConstantsAndFunctions.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;

public class Shifter 
{
	public static DoubleSolenoid piston = new DoubleSolenoid(Constants.shifterPinSourceA, Constants.shifterPinSourceB);
		
	public static void lowGear()
	{
		piston.set(DoubleSolenoid.Value.kForward);
	}
		
	public static void highGear()
	{
		piston.set(DoubleSolenoid.Value.kReverse);		
	}
	
	public static void runPiston(boolean joyValue)
	{
		if(joyValue)
		{
			if(piston.get() == DoubleSolenoid.Value.kReverse)
			{
				lowGear();
				Timer.delay(.75);	
			}
			else
			{
				highGear();
				Timer.delay(.75);
			}			
		}
	}	
}