 package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.*;
import frc.team3647autos.*;
import frc.team3647pistons.*;
import frc.team3647subsystems.*;
import frc.team3647inputs.*;
import edu.wpi.first.wpilibj.CameraServer;
import org.json.*;

import jssc.SerialPort.*;
import jssc.SerialPortException;
import jssc.SerialPortList;


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
	PracDigitalPin yes; 
	
	Timer stopWatch = new Timer();
	Timer newStopWatch = new Timer();

	public static boolean pracBot = false; //

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
			yes = new PracDigitalPin();
			enc = new Encoders();
			safetyChecker = new MotorSafetyHelper(safety);
			joy = new Joysticks();
			eleVader = new Elevator();
			yes.setPin();   
			navX = new NavX();
			Elevator.resetElevatorEncoders();
			Elevator.elevatorInitialization();
			Drivetrain.drivetrainInitialization(pracBot);
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
		elevatorEncoder = true;
		bannerSensor = true;
		currentState = false;
		wristEncoder = false;
		wristLimitSwitch = false;
		wristCurrent = false;
		intakeBanner = false;
	}
	
	@Override
	public void autonomousInit() 
	{
		try 
		{
			CrashChecker.logAutoInit();
			Autonomous.initialize(enc, navX);
			// MPAutos.intialization(enc, navX);
			// MPAutos.switchSide = "Right";
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
			Wrist.setWristEncoder();
			Lights.runLights();
			Autonomous.runAuto(enc, navX);
			//MPAutos.middleSwitch(enc, navX);
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
		Lights.LightOutput(false, false, false);
		stopWatch.stop();
		stopWatch.reset();
		run = 0;
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
			runWrist();
			IntakeWheels.runIntake(joy.leftTrigger1, joy.rightTrigger1, false, 0, 0, joy.leftBumper1);
			Lights.runLights();
			runTests();
			//enc.testEncoders();
		}
		catch(Throwable t)
		{
			CrashChecker.logThrowableCrash(t);
			throw t;
		}
	}

	//boo

	@Override
	public void testInit()
	{
		try 
		{
			Lights.runLights();
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
		Drivetrain.rightSRX.set(ControlMode.PercentOutput, joy.leftJoySticky);
		Drivetrain.FRCarcadedrive(joy.leftJoySticky, joy.rightJoyStickx);
		//runDrivetrain();
		//runElevator();
		runWrist();
		//runTests();
		//Elevator.testElevatorEncoders();
		Wrist.testWristEncoder();
		//System.out.println("NavX: " + navX.yaw);
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
			if(Robot.pracBot)
			{
				Elevator.setElevatorButtons(joy.buttonA1, false, false, false);
			Elevator.setManualOverride(joy.rightJoySticky1 * 1);
			Elevator.runElevator();
			}
			else
			{
				Elevator.setElevatorButtons(joy.buttonA1, joy.buttonB1,  joy.buttonY1, joy.buttonX1);
				Elevator.setManualOverride(joy.rightJoySticky1 * 1);
				Elevator.runElevator();
			}
			
		}
		//Elevator.climbPrep(joy.buttonB);
	}

	public void runWrist()
	{
		Wrist.setWristEncoder();
		Wrist.setWristButtons(joy.dPadDown,joy.dPadSide,joy.dPadUp);
		Wrist.setManualWristOverride(joy.leftJoySticky1 * 0.45);
		Wrist.runWrist();
	}

	public void runPistonsandForks()
	{
		Intake.runIntake(joy.rightBumper1);
		Forks.runPiston(joy.buttonX);
		Shifter.runPiston(joy.buttonY);
		TiltServo.PullForks(joy.leftTrigger, joy.rightTrigger);
		Lock.runPiston(joy.buttonA);
		Compressor007.runCompressor();
	}
	
	public void runDrivetrain()
	{
		navX.setAngle();
		enc.setEncoderValues();
		if(Elevator.elevatorEncoderValue > 30000 || joy.leftBumper)
		{
			Drivetrain.newArcadeDrive(joy.leftJoySticky * Constants.driveElevatorSpeedModifier, joy.rightJoyStickx * Constants.driveElevatorSpeedModifier, navX.yaw, navX);
		}
		else
		{
			//Drivetrain.arcadeDrive(Encoders.leftEncoderValue, Encoders.rightEncoderValue, joy.leftJoySticky, joy.rightJoyStickx);
			//Drivetrain.FRCarcadedrive(joy.leftJoySticky, joy.rightJoyStickx);
			Drivetrain.newArcadeDrive(joy.leftJoySticky, joy.rightJoyStickx, navX.yaw, navX);
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
			Wrist.testWristEncoder();
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
