package frc.robot;

import edu.wpi.first.wpilibj.*;
import frc.team3647elevator.*;
import frc.team3647pistons.*;
import frc.team3647subsystems.*;



public class Robot extends IterativeRobot 
{
	//Objects
	Encoders enc;
	Joysticks joy;
	NavX navX;
	Elevator eleVader;
	Wrist wrist;
	MotorSafety safety;
	MotorSafetyHelper safetyChecker;
	CameraServer server;
	
	Timer stopWatch = new Timer();
	Timer newStopWatch = new Timer();
	int run = 0;
	double prevLeftEncoder = 0, prevRightEncoder = 0;

	//Test Variables
	boolean driveEncoders, driveCurrent, elevatorCurrent, elevatorEncoder, bannerSensor, currentState, wristEncoder, wristLimitSwitch, wristCurrent, intakeBanner, navXAngle;

	@Override
	public void robotInit() 
	{
		try
		{
			CrashChecker.logRobotInit();
			enc = new Encoders();
			safetyChecker = new MotorSafetyHelper(safety);
			joy = new Joysticks();
			eleVader = new Elevator();
			navX = new NavX();
			Elevator.resetElevatorEncoders();
			Elevator.elevatorInitialization();
			Drivetrain.drivetrainInitialization();
			setTests();
			Wrist.configWristPID();
			
		}
		catch(Throwable t)
		{
			CrashChecker.logThrowableCrash(t);
			throw t;
		}
	}
	
	public void setTests()
	{
		driveEncoders = false;
		driveCurrent = false;
		elevatorCurrent = false;
		elevatorEncoder = false;
		bannerSensor = false;
		currentState = false;
		wristEncoder = false;
		wristLimitSwitch = false;
		wristCurrent = false;
		intakeBanner = false;
	}
	
	@Override
	public void autonomousInit() 
	{
		System.out.println(Autonomous.currentState);
		try 
		{
			CrashChecker.logAutoInit();
			Autonomous.initialize(enc);
		}
		catch(Throwable t)
		{
			CrashChecker.logThrowableCrash(t);
			throw t;
		}	
	}

	@Override
	public void autonomousPeriodic() 
	{
		while(DriverStation.getInstance().isAutonomous() && !DriverStation.getInstance().isDisabled())
		{
			Elevator.setElevatorEncoder();
			Wrist.setLimitSwitch();
			Wrist.setWristEncoder();
			Autonomous.chezyDoubleSwitchRightFromRight(enc);
		}
	}
	
	@Override
	public void disabledPeriodic()
	{
		Drivetrain.setToCoast();
	}
	
	@Override
	public void teleopInit()
	{
		Drivetrain.setToCoast();
		Forks.lockTheForks();
		Shifter.lowGear();
		Lock.unlock();
		//Elevator.aimedElevatorState = 0;
		Wrist.aimedWristState = 0;
		stopWatch.stop();
		stopWatch.reset();
		run = 0;
		Wrist.configWristPID();
		//Wrist.aimedElevatorState = 1;
		//ClimbButton.buttonState = 1;
		navX.resetAngle();
	}
	
	@Override
	public void teleopPeriodic() 
	{
		try 
		{
			CrashChecker.logTeleopPeriodic();
			updateJoysticks();
			runMotorSafety();
			runPistonsandForks();
			runDrivetrain();
			runElevator();
			enc.testEncodersWithDrive(joy.buttonA);
			IntakeWheels.runIntake(joy.leftTrigger1, joy.rightTrigger1, false, 0, 0, joy.leftBumper1);
			runWrist();
			Lights.runLights();
			runTests();
		}
		catch(Throwable t)
		{
			CrashChecker.logThrowableCrash(t);
			throw t;
		}
	}

	@Override
	public void testInit()
	{
		try 
		{
			Autonomous.initialize(enc);
			CrashChecker.logAutoInit();
			navX.resetAngle();
		}
		catch(Throwable t)
		{
			CrashChecker.logThrowableCrash(t);
			throw t;
		}	
	}
	

	@Override
	public void testPeriodic() 
	{
		updateJoysticks();
		runDrivetrain();
		System.out.println("NavX: " + navX.yaw);
	}
	
	
	public void updateJoysticks()
	{
		joy.setMainContollerValues();
		joy.setCoDriverContollerValues();
	}
	
	public void runElevator()
	{

		Elevator.setElevatorEncoder();
		if(Shifter.piston.get() == DoubleSolenoid.Value.kReverse)
		{
			Elevator.moveElevator(joy.rightJoySticky1 * 1);
		}
		else
		{
			Elevator.setElevatorButtons(joy.buttonA1, joy.buttonB1,  joy.buttonY1, joy.buttonX1);
			Elevator.setManualOverride(joy.rightJoySticky1 * .6);
			Elevator.runElevator();
		}
		//Elevator.climbPrep(joy.buttonB);
	}

	public void runWrist()
	{
		Wrist.setWristEncoder();
		Wrist.setWristButtons(joy.dPadDown,joy.dPadSide,joy.dPadUp);
		Wrist.setManualWristOverride(joy.leftJoySticky1 * 0.45);
		Wrist.runWrist();
		Wrist.setLimitSwitch();
	}

	public void runPistonsandForks()
	{
		Intake.runIntake(joy.rightBumper1);
		Forks.runPiston(joy.buttonX);
		Shifter.runPiston(joy.buttonY);
		TiltServo.PullForks(joy.leftTrigger, joy.rightTrigger);
		//Lock.runPiston(joy.buttonA);
		Compressor007.runCompressor();
	}
	
	public void runDrivetrain()
	{
		navX.setAngle();
		enc.setEncoderValues();
		if(joy.leftBumper)
		{
			//Drivetrain.arcadeDrive(enc.leftEncoderValue, enc.rightEncoderValue, joy.leftJoySticky * .6, joy.rightJoyStickx * .6);
		}
		else
		{
			//Drivetrain.arcadeDrive(Encoders.leftEncoderValue, Encoders.rightEncoderValue, joy.leftJoySticky, joy.rightJoyStickx);
			//Drivetrain.FRCarcadedrive(joy.leftJoySticky, joy.rightJoyStickx);
			//Drivetrain.runMEATDrivetrain(joy.leftJoySticky, joy.rightJoyStickx);
			Drivetrain.newArcadeDrive(joy.rightJoyStickx, joy.leftJoySticky);
		}
	}
	
	public void runMotorSafety()
	{
		safetyChecker.setSafetyEnabled(false);
	}
	
	public void runTests()
	{
		if(driveEncoders)
		{
			enc.testEncoders();
		}
		if(driveCurrent)
		{
			Drivetrain.testDrivetrainCurrent();
		}
		if(elevatorCurrent)
		{
			Elevator.testElevatorCurrent();
		}
		if(bannerSensor)
		{
			Elevator.testBannerSensor();
		}
		if(currentState)
		{
			//System.out.println(Autonomous.currentState);
		}
		if(elevatorEncoder)
		{
			Elevator.testElevatorEncoders();
		}
		if(wristEncoder)
		{
			wrist.testWristEncoder();
		}
		if(wristCurrent)
		{
			Wrist.testWristCurrent();
		}
		if(wristLimitSwitch)
		{
			Wrist.testLimitSwitch();
		}
		if(intakeBanner)
		{
			IntakeWheels.testBannerSensor();
		}
	}
}
