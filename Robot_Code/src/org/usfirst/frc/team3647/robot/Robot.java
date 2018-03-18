package org.usfirst.frc.team3647.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.MotorSafetyHelper;
import team3647elevator.Elevator;
import team3647elevator.ElevatorLevel;
import team3647elevator.Intake;
import team3647pistons.intakeMechanism;
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
	CameraServer yayt;

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
//			yayt = CameraServer.getInstance();
//			yayt.startAutomaticCapture("cam1", 1);
			Encoders.resetEncoders();
			ElevatorLevel.resetElevatorEncoders();
			Drivetrain.drivetrainInitialization();
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
			Autonomous.runAuto(Encoders.leftEncoderValue, Encoders.rightEncoderValue);
			
			//.middleSideLeftAuto(Encoders.leftEncoderValue, Encoders.rightEncoderValue);
			//Autonomous.rightSideBigJank(Encoders.leftEncoderValue, Encoders.rightEncoderValue);
		}
	}
	
	@Override
	public void teleopInit()
	{
		Forks.notForks();
		Shifter.Shifted();
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
		eleVader.setElevatorEncoder();
		Shifter.runPiston(joy.buttonY);
		runDrivetrain();
		Elevator.moveEleVader(joy.rightJoySticky * .4);
		ElevatorLevel.testElevatorEncoders();
		System.out.println(ElevatorLevel.bannerSensor.get());
		Encoders.testEncoders();
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
			Intake.runIntake(joy.rightTrigger1, joy.leftTrigger1);
		}
	}

	public void runPistons()
	{
		//Clamps.runPiston(joy.buttonA);
//		if(Drivetrain.leftSRX.get() == 0 && Drivetrain.rightSRX.get() == 0 && Elevator.elevatorState == Elevator.aimedElevatorState && Elevator.aimedElevatorState!=-1)
//		{
			intakeMechanism.runIntake(joy.rightBumper1);
			Forks.runPiston(joy.buttonX);
			Shifter.runPiston(joy.buttonY);
		//}
		
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