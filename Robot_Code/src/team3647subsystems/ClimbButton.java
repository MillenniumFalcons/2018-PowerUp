package team3647subsystems;

import team3647elevator.Wrist;
import team3647pistons.Lock;
import team3647pistons.Shifter;

public class ClimbButton {
	
	public static int buttonState;
	
	public static void climb(boolean joyValue)
	{
		switch(buttonState)
		{
		case 0:
			if(joyValue)
			{
				buttonState = 1;
			}
			break;
		case 1:
			Wrist.moveToUp();
			//need elevator zero
			buttonState = 2;
			break;
		case 2:
			Lock.lock();
			buttonState = 3;
			break;
		case 3:
			//go to climb height
			buttonState = 4;
			break;
		case 4:
			Shifter.lowGear();
			break;
		}
	}

}