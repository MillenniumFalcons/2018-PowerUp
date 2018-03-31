package org.usfirst.frc.team3647.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.MotorSafetyHelper;
import team3647elevator.Elevator;
import team3647elevator.ElevatorLevel;
import team3647elevator.IntakeWheels;
import team3647pistons.Intake;
import team3647pistons.Compressor007;
import team3647pistons.Forks;
import team3647pistons.Shifter;
import team3647subsystems.Drivetrain;
import team3647subsystems.Encoders;
import team3647subsystems.Joysticks;

public class Robot extends IterativeRobot {

	Encoders enc;
	Joysticks joy;
	ElevatorLevel eleVader;
	MotorSafety safety;
	MotorSafetyHelper safetyChecker;

	@Override
	public void robotInit() 
	{
		try
		{
			CrashChecker.logRobotInit();
			enc = new Encoders();
			safetyChecker = new MotorSafetyHelper(safety);
			joy = new Joysticks();
			eleVader = new ElevatorLevel();
			Encoders.resetEncoders();
			ElevatorLevel.resetElevatorEncoders();
			Drivetrain.drivetrainInitialization();
			Autonomous.stopWatch.start();
		}
		catch(Throwable t)
		{
			CrashChecker.logThrowableCrash(t);
			throw t;
		}
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
			runMotorSafety();
			enc.setEncoderValues();
			eleVader.setElevatorEncoder();
		
			Autonomous.test3(Encoders.leftEncoderValue, Encoders.rightEncoderValue);
		}
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
			runPistons();
			runDrivetrain();
			runElevator();
			IntakeWheels.runIntake(joy.leftTrigger1, joy.rightTrigger1,	 false, 0, 0);
			System.out.println("LEFT SRX: " + ElevatorLevel.elevatorEncoderValue);
			//Encoders.testEncoders();
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
//		eleVader.setElevatorEncoder();
//		Shifter.runPiston(joy.buttonY);
//		runDrivetrain();
//		Elevator.moveEleVader(joy.rightJoySticky * .4);
//		ElevatorLevel.testElevatorEncoders();
//		ElevatorLevel.testBannerSensor();
//		Encoders.testEncoders();
//		runDrivetrain();
		Intake.runIntake(joy.rightBumper1);
		IntakeWheels.runIntake(joy.leftTrigger1, joy.rightTrigger1, false, 0, 0);
		enc.setEncoderValues();
		Drivetrain.FRCarcadedrive(joy.leftJoySticky, joy.rightJoyStickx);
		Encoders.testEncoders();
		//Autonomous.testTimer(joy.buttonA);
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

	public void runPistons()
	{
		Intake.runIntake(joy.rightBumper1);
		Forks.runPiston(joy.buttonX);
		Shifter.runPiston(joy.buttonY);
		//Compressor007.runCompressor();
	}
	
	public void runDrivetrain()
	{
		enc.setEncoderValues();
		if(joy.leftBumper)
		{
			Drivetrain.arcadeDrive(Encoders.leftEncoderValue, Encoders.rightEncoderValue, joy.leftJoySticky * .65, joy.rightJoyStickx * .65);
		}
		else
		{
			Drivetrain.arcadeDrive(Encoders.leftEncoderValue, Encoders.rightEncoderValue, joy.leftJoySticky, joy.rightJoyStickx);
		}
	}
	
	public void runMotorSafety()
	{
		safetyChecker.setSafetyEnabled(false);
	}
}