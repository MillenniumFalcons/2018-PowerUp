public class Wrist 
{
	public static int elevatorState, aimedElevatorState;
<<<<<<< HEAD
	public static int wristState, aimedWristState;
=======
>>>>>>> 3c6e6eeebb765fcf98c26689e0a6054304b7c792
	/*
	 * 0. Start
	 * 1. Flat
	 * 2. Sixty-Degrees
	 * 3. Facing Up
	 */

	public static boolean start, flat, sixtyDegrees, up, manualOverride, originalPositionButton;
	public static double overrideValue, speed, wristEncoder; 
<<<<<<< HEAD
	public static WPI_TalonSRX wristMotor = new WPI_TalonSRX(Constants.wristPin);
	static DigitalInput limitSwitch = new DigitalInput(Constants.wristLimitSwitch); 
	public static double wristEncoderValue = 185;
	

	public static void testWristCurrent()
	{
		System.out.println("Wrist Current: " + wristMotor.getOutputCurrent());
	}
	//Insert step 1 code below
	
	public static void setWristButtons(boolean flatButton, boolean aimButton, boolean idleButton)
	{
		flat = flatButton;
		aim = aimButton;
		idle = idleButton;
	}
	
	public static void moveWrist(double speed)
	{
		wristMotor.set(ControlMode.PercentOutput, speed);
	}
	
	
	public static void stopWrist()
	{
		moveWrist(0);
	}
	
	public static void setManualWristOverride(double jValue)
	{
		if(Math.abs(jValue) <.2 )
		{
			manualOverride = false;
		}
		else
		{
			overrideValue = jValue;
			manualOverride = true;
		}
	}
	
	public static void testWristEncoder()
	{
		System.out.println("Elevator Encoder Value: " + wristEncoderValue);
	}
	
	public static void resetWristEncoder()
	{
		wristMotor.getSensorCollection().setQuadraturePosition(0, 10);
	}
	
	public void setElevatorEncoder()
	{
		if(reachedFlat())
		{
			resetWristEncoder();
		}
		wristEncoderValue = wristMotor.getSensorCollection().getQuadraturePosition();
	}
	
	public static boolean reachedFlat(){
			if(limitSwitch.get())
			{
				return true;
			} else {
				return false;
			}
	}
	public static boolean reachedAim(){
			if(wristEncoderValue > Constants.aim - 5 && wristEncoderValue < Constants.aim + 5){
				return true;
			} else {
			return false;
			}
	}
	public static boolean reachedIdle(){
			if(wristEncoderValue > Constants.idle - 5 && wristEncoderValue < Constants.idle + 5){
				return true;
			} else {
				return false;
			}
	}
	public static void maintainAimPosition(){
		if(reachedAim()){
			stopWrist();
		} else {
			if(wristEncoderValue > Constants.aim){
				moveWrist(0.2);
			} else {
				moveWrist(-0.2);
			}
		}
	}
	public static void maintainIdlePosition(){
		if(reachedIdle()){
			stopWrist();
		} else {
			if(wristEncoderValue > Constants.idle){
				moveWrist(-0.2);
			} else {
				moveWrist(0.2);
			}
		}
	}
	
	
	public static void runWrist(){
		switch(wristState){
			case 0://starting code? big gay
			case 1://flat
				if(manualOverride){
				wristState = -1;
				} else if(flat){
				aimedWristState = 1;
				} else if(aim){
				aimedWristState = 2;
				} else if(idle){
				aimedWristState = 3;
				}
				switch(aimedWristState){
					case 1:
						if(reachedFlat()){
							stopWrist();
						} else{
							moveWrist(-.2);
						}
					case 2:
						if(reachedAim()){
							maintainAimPosition();
							wristState = 2;
						} else{
							moveWrist(.2);
						}
					case 3:
						if(reachedIdle()){
							maintainIdlePosition();
							wristState = 3;
						} else{
							moveWrist(.2);
						}
				}
			case 2://aim
				if(manualOverride){
					wristState = -1;
					} else if(flat){
					aimedWristState = 1;
					} else if(aim){
					aimedWristState = 2;
					} else if(idle){
					aimedWristState = 3;
					}
					switch(aimedWristState){
						case 1:
							if(reachedFlat()){
								stopWrist();
							} else{
								moveWrist(-.2);
							}
						case 2:
								maintainAimPosition();
						case 3:
							if(reachedIdle()){
								maintainIdlePosition();
								wristState = 3;
							} else{
								moveWrist(.2);
							}
					}
			case 3://idle
				if(manualOverride){
					wristState = -1;
					} else if(flat){
					aimedWristState = 1;
					} else if(aim){
					aimedWristState = 2;
					} else if(idle){
					aimedWristState = 3;
					}
					switch(aimedWristState){
						case 1:
							if(reachedFlat()){
								stopWrist();
							} else{
								moveWrist(-.2);
							}
						case 2:
							if(reachedAim()){
								maintainAimPosition();
								wristState = 2;
							} else{
								moveWrist(.2);
							}
						case 3:
							maintainIdlePosition();
							}
					}
						
				
	}
		

=======

	//Insert step 1 code below
	
>>>>>>> 3c6e6eeebb765fcf98c26689e0a6054304b7c792

	//Insert step 3 code below
	public static void setLimitSwitch(){
		
	}
}
