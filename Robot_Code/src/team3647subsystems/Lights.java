package team3647subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import team3647elevator.IntakeWheels;
import team3647pistons.Shifter;

public class Lights 
{
	static DigitalOutput one = new DigitalOutput(5);
	static DigitalOutput two = new DigitalOutput(6);
	static DigitalOutput three = new DigitalOutput(7);
 
	public static void LightOutput(boolean input1, boolean input2, boolean input3)
	{
		one.set(input1);
		two.set(input2);
		three.set(input3);
	}
	
	public static void runLights()
	{
		if(!IntakeWheels.getIntakeBannerSenor())
		{
			LightOutput(true, false, false);
		}
		else if(Shifter.piston.get() == DoubleSolenoid.Value.kReverse)
		{
			LightOutput(false, true, false);
		}
		else if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue)
		{
			LightOutput(false, false, true);
		}
		else if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red)
		{
			LightOutput(true, true, false);
		}
		else
		{
			LightOutput(false, false, false);
		}
	}
  /*
  Code Combo One - Have Cube:
  input1 = true;
  input2 = false;
  input3 = false;
  
  Code Combo Two - Elevator Low Gear:
  input1 = false;
  input2 = true;
  input3 = false;
  
  Code Combo Three - Red Alliance Idle:
  input1 = false;
  input2 = false;
  input3 = true;
  
  Code Combo Four - Blue Alliance Idle:
  input1 = true;
  input2 = true;
  input3 = false;
  
  Code Combo Five - Default:
  input1 = false;
  input2 = false;
  input3 = false;
  
  */
}