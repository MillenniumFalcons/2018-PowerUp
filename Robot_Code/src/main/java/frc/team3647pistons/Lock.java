package frc.team3647pistons;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import frc.team3647autos.*;
import frc.team3647pistons.*;
import frc.team3647subsystems.*;
import frc.team3647inputs.*;
import frc.robot.*;

public class Lock
{
	public static DoubleSolenoid piston = new DoubleSolenoid(Constants.lockPinSourceA, Constants.lockPinSourceB);
	
	public static void unlock() 
	{
		piston.set(DoubleSolenoid.Value.kForward);
	}
	
	public static void lock() 
	{
		piston.set(DoubleSolenoid.Value.kReverse);
	}
	
	public static void runPiston(boolean joyvalue) 
	{
		if(joyvalue)
		{
			if(piston.get() == DoubleSolenoid.Value.kReverse)
			{
				unlock();
				Timer.delay(.75);
			}
			else
			{
				lock();
				Timer.delay(.75);
			}
		}
	}
}
