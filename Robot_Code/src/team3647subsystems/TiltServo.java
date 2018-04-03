package team3647subsystems;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
//edu.wpi.first.wpilibj.PWM
//edu.wpi.first.wpilibj.SendableBase


public class TiltServo 
{
	public static Servo servo = new Servo(0);
	public static WPI_TalonSRX upDownSRX = new WPI_TalonSRX(60);
	public static void PullForks()
	{
		double speed;
		if(leftTrigger)
		{
			speed = .2;
			upDownSRX.set(speed); //Direction?
		}
		else if(rightTrigger)
		{
			servo.set(.5);
			Timer.delay(3);
			servo.set(0);
			speed = .2;
			upDownSRX.set(speed); //Direction?
		}
		else
		{
			speed = 0;
			upDownSRX.ser(speed);
		}
	}
	
	
	
		
	}
