
package team3647pistons;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;


public class Forks
{
	public static DoubleSolenoid piston = new DoubleSolenoid(4,5);
	
	public static void forks() 
	{
		piston.set(DoubleSolenoid.Value.kForward);
	}
	public static void notForks() 
	{
		piston.set(DoubleSolenoid.Value.kReverse);
	}
	public static void runPistons(boolean joyvalue) 
	{
		if(joyvalue)
		{
			if(piston.get() == DoubleSolenoid.Value.kReverse)
			{
				forks();
				Timer.delay(.75);
			}
			else
			{
				notForks();
				Timer.delay(.75);
			}
		}
	}
}

