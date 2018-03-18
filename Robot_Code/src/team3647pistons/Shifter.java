package team3647pistons;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;

public class Shifter 
{
	//Double Pistons
	
		public static DoubleSolenoid piston = new DoubleSolenoid(1,0);
		
		public static void Shifted()
		{
			piston.set(DoubleSolenoid.Value.kForward);
		}
		
		public static void notShifted()
		{
			piston.set(DoubleSolenoid.Value.kReverse);
			
		}
		
		public static void runPiston(boolean joyValue)
		{
			if(joyValue)
			{
				if(piston.get() == DoubleSolenoid.Value.kReverse)
				{
					Shifted();
					Timer.delay(.75);
				}
				else
				{
					notShifted();
					Timer.delay(.75);
				}
			}
		}	
}
