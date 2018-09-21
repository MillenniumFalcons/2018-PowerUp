package frc.team3647pistons;

import frc.team3647autos.*;
import frc.team3647pistons.*;
import frc.team3647subsystems.*;
import frc.team3647inputs.*;
import frc.robot.*;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Intake 
{
	public static DoubleSolenoid piston = new DoubleSolenoid(Constants.intakePinSourceA, Constants.intakePinSourceB);
	
	public static void openIntake()
	{
		piston.set(DoubleSolenoid.Value.kForward);
	}
	
	public static void closeIntake()
	{
		piston.set(DoubleSolenoid.Value.kReverse);
	}
	
	public static void runIntake(boolean joyValue)
	{
		if(joyValue)
		{
			openIntake();
		}
		else
		{
			closeIntake();
		}
	}
}
