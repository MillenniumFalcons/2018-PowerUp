package team3647subsystems;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Servo;
//edu.wpi.first.wpilibj.PWM
//edu.wpi.first.wpilibj.SendableBase


public class TiltServo 
{
	Servo servo = new Servo(0);
	
	static double kDefaultMaxServoPWM;
	static double kDefaultMinServoPWM;
	
	public void moveUp()
	{
		if(joy)
		{
			//move speed with motors
		}
	}
	public void MoveDown()
	{
		if(joy)
		{
			double value = .5;
			servo.set(value);
			//move speed with motors
		}
	}
	
	
	
		
	}
