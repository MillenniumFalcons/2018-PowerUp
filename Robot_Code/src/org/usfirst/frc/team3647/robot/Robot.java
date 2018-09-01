package org.usfirst.frc.team3647.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.MotorSafetyHelper;
import team3647elevator.Elevator;
import team3647elevator.ElevatorLevel;
import team3647elevator.IntakeWheels;
import team3647elevator.Wrist;
import team3647pistons.Intake;
import team3647pistons.Lock;
import team3647pistons.Compressor007;
import team3647pistons.Forks;
import team3647pistons.Shifter;
import team3647subsystems.Drivetrain;
import team3647subsystems.Encoders;
import team3647subsystems.Joysticks;
import team3647subsystems.Lights;
import team3647subsystems.TiltServo;


public class Robot extends IterativeRobot 
{
	//Objects
	Encoders enc;
	Joysticks joy;
	ElevatorLevel eleVader;
	MotorSafety safety;
	MotorSafetyHelper safetyChecker;
	CameraServer server;
	
	boolean broke = true;

	//Test Variables
	boolean driveEncoders, driveCurrent, elevatorCurrent, elevatorEncoder, bannerSensor, currentState;

	@Override
	public void robotInit() 
	{
		try
		{
			CrashChecker.logRobotInit();
//			server = CameraServer.getInstance();
//			server.startAutomaticCapture("cam0", 0);
			enc = new Encoders();
			safetyChecker = new MotorSafetyHelper(safety);
			joy = new Joysticks();
			eleVader = new ElevatorLevel();
			Encoders.resetEncoders();
			ElevatorLevel.resetElevatorEncoders();
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
	}
	
	@Override
	public void autonomousInit() 
	{
		try 
		{
			CrashChecker.logAutoInit();
			Autonomous.initialize();
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
			//runMotorSafety();
			updateJoysticks();
			enc.setEncoderValues();
			eleVader.setElevatorEncoder();
			Autonomous.leftSide2Cube(Encoders.leftEncoderValue, Encoders.rightEncoderValue);
			//Autonomous.runAuto(Encoders.leftEncoderValue, Encoders.rightEncoderValue);
			//runTests();
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
		Elevator.elevatorState = 0;
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
			//runElevator();
			runWrist();
			IntakeWheels.runIntake(joy.leftTrigger1, joy.rightTrigger1, false, 0, 0);
			Lights.LightOutput(false,false,false);
			runTests();
			System.out.println(Wrist.limitSwitchValue);
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
			CrashChecker.logAutoInit();
			Autonomous.initialize();
			
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
//		updateJoysticks();
//		enc.setEncoderValues();
//		eleVader.setElevatorEncoder();
//		runPistonsandForks();
//		IntakeWheels.runIntake(joy.leftTrigger1, joy.rightTrigger1, false, 0, 0);
//		Elevator.moveEleVader(joy.rightJoySticky * .4);
//		Drivetrain.tankDrive(joy.leftJoySticky, joy.leftJoySticky);
//		Lights.runLights();
//		runTests();
		runMotorSafety();
		updateJoysticks();
		enc.setEncoderValues();
		eleVader.setElevatorEncoder();
		Autonomous.test(Encoders.leftEncoderValue, Encoders.rightEncoderValue, joy.buttonA);
//		Elevator.moveEleVader(joy.rightJoySticky * 1);
//		Shifter.runPiston(joy.buttonY);
	}
	
	
	public void updateJoysticks()
	{
		joy.setMainContollerValues();
		joy.setCoDriverContollerValues();
	}
	
	public void runElevator()
	{
		eleVader.setElevatorEncoder();
		if(Shifter.piston.get() == DoubleSolenoid.Value.kReverse)
		{
			Elevator.moveEleVader(joy.rightJoySticky1 * 1);
		}
		else
		{
			Elevator.setElevatorButtons(joy.buttonA1, false, joy.buttonB1,  joy.buttonY1, joy.buttonX1);
			Elevator.setManualOverride(joy.rightJoySticky1 * .6);
			Elevator.runDarthVader();
		}
	}
	
	public void runWrist()
	{
		Wrist.setLimitSwitch();
		Wrist.resetWristEncoders();
		Wrist.setWristButtons(joy.dPadDown, joy.dPadSide, joy.dPadUp);
		Wrist.setManualOverride(joy.rightJoySticky1);
		Wrist.runWrist();
	}

	public void runPistonsandForks()
	{
		Intake.runIntake(joy.rightBumper1);
		Forks.runPiston(joy.buttonX);
		Shifter.runPiston(joy.buttonY);
		TiltServo.PullForks(joy.leftTrigger, joy.rightTrigger);
		//Lock.runPiston(joyvalue);
		//Compressor007.runCompressor();
	}
	
	public void runDrivetrain()
	{
		//enc.setEncoderValues();
		if(joy.leftBumper)
		{
			Drivetrain.arcadeDrive(Encoders.leftEncoderValue, Encoders.rightEncoderValue, joy.leftJoySticky * .45, joy.rightJoyStickx * .5);
		}
		else
		{
			Drivetrain.newArcadeDrive(joy.leftJoySticky, joy.rightJoyStickx);
//			if(broke)
//			{
//				Drivetrain.tankDrive(joy.leftJoySticky, joy.leftJoySticky);
//			}
//			else
//			{
//				Drivetrain.arcadeDrive(Encoders.leftEncoderValue, Encoders.rightEncoderValue, joy.leftJoySticky, joy.rightJoyStickx);
//			}
			
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
			Encoders.testEncoders();
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
			ElevatorLevel.testBannerSensor();
		}
		if(currentState)
		{
			System.out.println(Autonomous.currentState);
		}
		if(elevatorEncoder)
		{
			ElevatorLevel.testElevatorEncoders();
		}
	}
}