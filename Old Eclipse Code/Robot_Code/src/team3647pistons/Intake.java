package team3647pistons;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import team3647ConstantsAndFunctions.Constants;

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
