package team3647pistons;

import edu.wpi.first.wpilibj.Compressor;
import team3647elevator.Elevator;
import team3647subsystems.Drivetrain;

public class Compressor007 
{
	static Compressor c = new Compressor(0);
	
	public static void runCompressor()
	{
		// if(Drivetrain.leftSRX.get() == 0 && Drivetrain.rightSRX.get() == 0 && Elevator.elevatorState == Elevator.aimedElevatorState && Elevator.overrideValue == 0)
		// {
			c.setClosedLoopControl(true);
		// }
		// else
		// {
		// 	c.setClosedLoopControl(false);
		// }

	}
}
