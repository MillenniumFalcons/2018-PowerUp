package team3647subsystems;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;



public class TiltServo 
{
	public static Servo servo = new Servo(0);
	public static WPI_TalonSRX tiltSRX = new WPI_TalonSRX(60);
	public static void PullForks(double leftTrigger, double rightTrigger)
	{
		if(leftTrigger > 0)
		{
			servo.set(.5);
			Timer.delay(.3);
			servo.set(0);
			tiltSRX.set(-leftTrigger); 
		}
		else if(rightTrigger > 0)
		{
			
			tiltSRX.set(rightTrigger);
		}
		else
		{
			tiltSRX.set(0);
		}
	}		
}
