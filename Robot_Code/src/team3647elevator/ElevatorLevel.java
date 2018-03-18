package team3647elevator;

import edu.wpi.first.wpilibj.DigitalInput;
import team3647ConstantsAndFunctions.Constants;
import team3647ConstantsAndFunctions.Functions;
import team3647subsystems.Drivetrain;

public class ElevatorLevel 
{
	public static double elevatorEncoderValue;
	
	public static DigitalInput bannerSensor = new DigitalInput(9); 
	
	public void setElevatorEncoder()
	{
		if(reachedStop())
		{
			resetElevatorEncoders();
		}
		elevatorEncoderValue = Elevator.leftElevator.getSensorCollection().getQuadraturePosition();
	}
	
	public static void resetElevatorEncoders()
	{
		Elevator.leftElevator.getSensorCollection().setQuadraturePosition(0, 10);
	}
	
	public static void testElevatorEncoders()
	{
		System.out.println("Elevator Encoder Value: " + elevatorEncoderValue);
	}
	
	public static boolean reachedStop()
	{
		if(bannerSensor.get())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public static boolean reachedPickUp()
	{
		if(elevatorEncoderValue > Constants.pickUp - 1000 && elevatorEncoderValue < Constants.pickUp + 3000)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean reachedSwitch()
	{
		if(elevatorEncoderValue > Constants.sWitch - 2000 && elevatorEncoderValue < Constants.sWitch + 2000)
		{
			return true;
		}
		else
		{
			return false; 
		}
	}
	
	public static boolean reachedLowerScale()
	{
		if(elevatorEncoderValue > Constants.lowerScale - 2500 && elevatorEncoderValue < Constants.lowerScale + 2500)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean reachedScale()
	{
		if(elevatorEncoderValue > Constants.scale - 3000 && elevatorEncoderValue < Constants.scale + 3000)
		{
			return true;
		}
		else
		{
			return false; 
		}
	}
	
	public static void maintainPickUpPosition()
	{
		if(reachedPickUp())
		{
			Elevator.stopEleVader();
		}
		else
		{
			if(elevatorEncoderValue > Constants.pickUp)
			{
				Elevator.moveEleVader(Functions.switchToPickUp(elevatorEncoderValue));
			}
			else
			{
				Elevator.moveEleVader(Functions.stopToPickUp(elevatorEncoderValue));
			}
		}
	}
	
	public static void maintainSwitchPosition()
	{
		if(reachedSwitch())
		{
			Elevator.moveEleVader(.06);
		}
		else
		{
			if(elevatorEncoderValue > Constants.sWitch)
			{
				Elevator.moveEleVader(Functions.lowerScaleToSwitch(elevatorEncoderValue));
			}
			else
			{
				Elevator.moveEleVader(Functions.pickUpToSwitch(elevatorEncoderValue));
			}
		}
	}
	
	public static void maintainScalePosition()
	{
		if(reachedScale())
		{
			Elevator.moveEleVader(.13);
		}
		else
		{
			if(elevatorEncoderValue > Constants.scale)
			{
				Elevator.moveEleVader(-.2);
			}
			else
			{
				Elevator.moveEleVader(Functions.lowerScaleToScale(elevatorEncoderValue));
			}
		}
	}
	
	
	

}
