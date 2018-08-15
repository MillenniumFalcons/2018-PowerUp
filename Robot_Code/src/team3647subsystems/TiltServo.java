package team3647subsystems;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;



public class TiltServo 
{
	public static Servo servo = new Servo(0);
	public static VictorSPX tiltSRX = new VictorSPX(0);
	public static void PullForks(double leftTrigger, double rightTrigger)
	{
		if(leftTrigger > 0)
		{
			//servo.set(.5);
			//Timer.delay(.3);
			//servo.set(0);
			tiltSRX.set(ControlMode.PercentOutput, -leftTrigger); 
		}
		else if(rightTrigger > 0)
		{
			
			tiltSRX.set(ControlMode.PercentOutput, rightTrigger);
		}
		else
		{
			tiltSRX.set(ControlMode.PercentOutput, 0);
		}
	}		
}
