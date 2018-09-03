package frc.team3647ConstantsAndFunctions;

import frc.team3647elevator.Elevator;
import frc.team3647elevator.IntakeWheels;
import frc.team3647pistons.Intake;

public class Functions 
{
	static double avg;
	static boolean reached;
	
	// Stuff for cheese autos
	
	//212 inches in y, 78.66 inches in x
	public static double rightSide2Cube(double time, boolean right)
	{
		double straightTime = 2.2;
		double turnTime = 1.4;
		double shootTime = .7;
		double secondCubeSecured = straightTime + turnTime + shootTime + 2;
		double backUp = .65;
		if(right)
		{
			if(time < .3)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .5;
			}
			else if(time < straightTime - .2)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .68;
			}
			else if(time < straightTime)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .2;
			}
			else if(time < straightTime + turnTime - .15)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .9;
			}
			else if(time < straightTime + turnTime)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .58;
			}
			else if(time < straightTime + turnTime + shootTime)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				IntakeWheels.runIntake(0, 0, true, -1, -1);
				return 0;
			}
			else if(time < straightTime + turnTime + shootTime + .8)
			{
				Intake.openIntake();
				IntakeWheels.runIntake(0, 0, true, 0, 0);
				if(Elevator.reachedBottom())
				{
					Elevator.moveElevator(0);
				}
				else
				{
					Elevator.moveElevator(-0.4);
				}
				return 0;
			} 
			else if(time < straightTime + turnTime + shootTime + 1.4)
			{
				Intake.openIntake();
				IntakeWheels.runIntake(0, 0, true, .5, .5);
				if(Elevator.reachedBottom())
				{
					Elevator.moveElevator(0);
				}
				else
				{
					Elevator.moveElevator(-0.4);
				}
				return .4;
			}
			else if(time < straightTime + turnTime + shootTime + 1.7)
			{
				Intake.closeIntake();
				IntakeWheels.runIntake(0, 0, true, .3, .3);
				return 0;
			}
			else if(time < secondCubeSecured)
			{
				Intake.closeIntake();
				IntakeWheels.runIntake(0, 0, true, 0, 0);
				return 0;
			}
			else if(time < secondCubeSecured + backUp)
			{
				return -.5;
			}
			else if(time < secondCubeSecured + backUp + 1.2)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return 0;
			}
			else if(time < secondCubeSecured + backUp + 1.7)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .3;
			}
			else if(time < secondCubeSecured + backUp + 2.1)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				IntakeWheels.runIntake(0, 0, true, -1, -1);
				return 0;
			}
			else
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				IntakeWheels.runIntake(0, 0, true, 0, 0);
				return 0;
			}
		}
		else
		{
			if(time < .3)
			{
				return .5;
			}
			else if(time < straightTime - .2)
			{
				return .7;
			}
			else if(time < straightTime)
			{
				return .2;
			}
			else if(time < straightTime + turnTime)
			{
				return 0;
			}
			else if(time < straightTime + turnTime + shootTime + .8)
			{
				return 0;
			}
			else if(time < straightTime + turnTime + shootTime + 1.4)
			{
				return .4;
			}
			else if(time < straightTime + turnTime + shootTime + 1.7)
			{
				return 0;
			}
			else if(time < secondCubeSecured)
			{
				return 0;
			}
			else if(time < secondCubeSecured + backUp)
			{
				return -.5;
			}
			else if(time < secondCubeSecured + backUp + 1.2)
			{
				return 0;
			}
			else if(time < secondCubeSecured + backUp + 1.7)
			{
				return .3;
			}
			else
			{
				return 0;
			}
		}
		
	}

	
	public static double leftSide2Cube(double time, boolean right)
	{
		double straightTime = 2.34;
		double turnTime = 1.04;
		double secondStraight = 2.1;
		double turnToSecondCube = 1.03;
		double shootTime = .7;
		double secondCubeSecured = straightTime + turnTime + secondStraight + turnToSecondCube + shootTime + 2;
		double backUp = .5;
		if(right)
		{
			if(time < .3)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .4;
			}
			else if(time < straightTime - .4)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .6;
			}
			else if(time < straightTime)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .2;
			}
			else if(time < straightTime + turnTime)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .6;
			}
			else if(time < straightTime + turnTime + secondStraight - .1)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .5;
			}
			else if(time < straightTime + turnTime + secondStraight)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .4;
			}
			else if(time < straightTime + turnTime + secondStraight + turnToSecondCube)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .5;
			}
			else if(time < straightTime + turnTime + secondStraight + turnToSecondCube + shootTime)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				IntakeWheels.runIntake(0, 0, true, -.5, -.9);
				return 0;
			}
			else if(time < straightTime + turnTime + secondStraight + turnToSecondCube + shootTime + .8)
			{
				Intake.openIntake();
				IntakeWheels.runIntake(0, 0, true, 0, 0);
				if(Elevator.reachedBottom())
				{
					Elevator.moveElevator(0);
				}
				else
				{
					Elevator.moveElevator(-0.4);
				}
				return 0;
			}
			else if(time < straightTime + turnTime + secondStraight + turnToSecondCube + shootTime + 1.4)
			{
				Intake.openIntake();
				IntakeWheels.runIntake(0, 0, true, .5, .5);
				if(Elevator.reachedBottom())
				{
					Elevator.moveElevator(0);
				}
				else
				{
					Elevator.moveElevator(-0.4);
				}
				return .4;
			}
			else if(time < straightTime + turnTime + secondStraight + turnToSecondCube + shootTime + 1.7)
			{
				Intake.closeIntake();
				IntakeWheels.runIntake(0, 0, true, .3, .3);
				return 0;
			}
			else if(time < secondCubeSecured)
			{
				Intake.closeIntake();
				IntakeWheels.runIntake(0, 0, true, .12, .12);
				return 0;
			}
			else if(time < secondCubeSecured + backUp)
			{
				return -.5;
			}
			else if(time < secondCubeSecured + backUp + 1.2)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return 0;
			}
			else if(time < secondCubeSecured + backUp + 1.7)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .3;
			}
			else if(time < secondCubeSecured + backUp + 2.1)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				IntakeWheels.runIntake(0, 0, true, -.9, -.5);
				return 0;
			}
			else
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				IntakeWheels.runIntake(0, 0, true, 0, 0);
				return 0;
			}
		}
		else
		{
			if(time < .3)
			{
				return .4;
			}
			else if(time < straightTime - .4)
			{
				return .6;
			}
			else if(time < straightTime)
			{
				return .2;
			}
			else if(time < straightTime + turnTime - .15)
			{
				return .15;
			}
			else if(time < straightTime + turnTime + secondStraight - .1)
			{
				return .5;
			}
			else if(time < straightTime + turnTime + secondStraight)
			{
				return .4;
			}
			else if(time < straightTime + turnTime + secondStraight + turnToSecondCube)
			{
				return 0;
			}
			else if(time < straightTime + turnTime + secondStraight + turnToSecondCube + shootTime)
			{
				return 0;
			}
			else if(time < straightTime + turnTime + secondStraight + turnToSecondCube + shootTime + .8)
			{
				return 0;
			}
			else if(time < straightTime + turnTime + secondStraight + turnToSecondCube + shootTime + 1.4)
			{
				return .4;
			}
			else if(time < straightTime + turnTime + secondStraight + turnToSecondCube + shootTime + 1.7)
			{
				return 0;
			}
			else if(time < secondCubeSecured)
			{
				return 0;
			}
			else if(time < secondCubeSecured + backUp)
			{
				return -.5;
			}
			else if(time < secondCubeSecured + backUp + 1.2)
			{
				return 0;
			}
			else if(time < secondCubeSecured + backUp + 1.7)
			{
				return .3;
			}
			else if(time < secondCubeSecured + backUp + 2.1)
			{
				return 0;
			}
			else
			{
				return 0;
			}
		}
	}
	
	public static double rightScaleRightSwitch(double time, boolean right)
	{
		double straightTime = 3.6;
		double turnToScaleUsingRightMotor = .5;
		double stopAtScale = 1;
		double firstScale = straightTime + turnToScaleUsingRightMotor + stopAtScale + turnToScaleUsingRightMotor - .04;
		double goBackForSecondCube = .7;
		double turnBackUsingLeftMotor = .615;
		double timeToPickUpSecondCube = firstScale + goBackForSecondCube + turnBackUsingLeftMotor + 1;
		double straightToSecondSube = .86;
		double turnToSecondCube = 1.03;
		double secondCubeSecured = timeToPickUpSecondCube + straightTime + turnToSecondCube + .7;
		double backUp = .5;
		if(right)
		{
			if(time < .3)
			{
				IntakeWheels.runIntake(0, 0, true, .12, .12);
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .5;
			}
			else if(time < straightTime - .54)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				return .74;
			}
			else if(time < straightTime)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				return .2;
			}
			else if(time < straightTime + turnToScaleUsingRightMotor)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				return .5;
			}
			else if(time < straightTime + turnToScaleUsingRightMotor + stopAtScale)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				IntakeWheels.runIntake(0, 0, true, -.5, -.5);
				return 0;
			}
			else if(time < firstScale)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				IntakeWheels.runIntake(0, 0, true, 0, 0);
				return -.5;
			}
			else if(time < firstScale + goBackForSecondCube)
			{
				if(Elevator.reachedBottom())
				{
					Elevator.moveElevator(0);
				}
				else
				{
					Elevator.moveElevator(-0.4);
				}
				return -.5;
			}
			else if(time < firstScale + goBackForSecondCube + turnBackUsingLeftMotor)
			{
				if(Elevator.reachedBottom())
				{
					Elevator.moveElevator(0);
				}
				else
				{
					Elevator.moveElevator(-0.4);
				}
				return 0;
			}
			else if(time < timeToPickUpSecondCube)
			{
				if(Elevator.reachedBottom())
				{
					Elevator.moveElevator(0);
				}
				else
				{
					Elevator.moveElevator(-0.4);
				}
				return 0;
			}
			else if(time < timeToPickUpSecondCube + .3)
			{
				Intake.openIntake();
				return .2;
			}
			else if(time < timeToPickUpSecondCube + straightToSecondSube)
			{
				return .5;
			}
			else if(time < timeToPickUpSecondCube + straightToSecondSube + turnToSecondCube)
			{
				IntakeWheels.runIntake(0, 0, true, .5, .5);
				return .5;
			}
			else if(time < timeToPickUpSecondCube + straightTime + turnToSecondCube + .3)
			{
				IntakeWheels.runIntake(0, 0, true, .5, .5);
				return 0;
			}
			else if(time < timeToPickUpSecondCube + straightTime + turnToSecondCube + .3)
			{
				IntakeWheels.runIntake(0, 0, true, .3, .3);
				Intake.closeIntake();
				return 0;
			}
			else if(time < secondCubeSecured)
			{
				IntakeWheels.runIntake(0, 0, true, .12, .12);
				Intake.closeIntake();
				return 0;
			}
			else if(time < secondCubeSecured + backUp)
			{
				return -.5;
			}
			else if(time < secondCubeSecured + backUp + 1.2)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return 0;
			}
			else if(time < secondCubeSecured + backUp + 1.7)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .3;
			}
			else if(time < secondCubeSecured + backUp + 2.1)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				IntakeWheels.runIntake(0, 0, true, -.9, -.5);
				return 0;
			}
			else
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				IntakeWheels.runIntake(0, 0, true, 0, 0);
				return 0;
			}
		}
		else
		{
			if(time < .3)
			{
				return .5;
			}
			else if(time < straightTime - .54)
			{
				return .74;
			}
			else if(time < straightTime)
			{
				return .2;
			}
			else if(time < straightTime + turnToScaleUsingRightMotor)
			{
				return 0;
			}
			else if(time < straightTime + turnToScaleUsingRightMotor + stopAtScale)
			{
				return 0;
			}
			else if(time < firstScale)
			{
				return 0;
			}
			else if(time < firstScale + goBackForSecondCube)
			{
				return -.5;
			}
			else if(time < firstScale + goBackForSecondCube + turnBackUsingLeftMotor - .2)
			{
				return -.7;
			}
			else if(time < firstScale + goBackForSecondCube + turnBackUsingLeftMotor)
			{
				return -.5;
			}
			else if(time < timeToPickUpSecondCube)
			{
				return 0;
			}
			else if(time < timeToPickUpSecondCube + .3)
			{
				return .2;
			}
			else if(time < timeToPickUpSecondCube + straightToSecondSube)
			{
				return .5;
			}
			else if(time < timeToPickUpSecondCube + straightToSecondSube + turnToSecondCube)
			{
				return 0;
			}
			else if(time < timeToPickUpSecondCube + straightTime + turnToSecondCube + .3)
			{
				return 0;
			}
			else if(time < secondCubeSecured + backUp)
			{
				return -.5;
			}
			else if(time < secondCubeSecured + backUp + 1.2)
			{
				return 0;
			}
			else if(time < secondCubeSecured + backUp + 1.7)
			{
				return .3;
			}
			else if(time < secondCubeSecured + backUp + 2.1)
			{
				return 0;
			}
			else
			{
				return 0;
			}
		}
	}

	public static double doubleScale(double time, boolean right)
	{
		double straightTime = 3.1;
		double turnToScaleUsingRightMotor = .5;
		double stopAtScale = 1;
		double firstScale = straightTime + turnToScaleUsingRightMotor + stopAtScale + turnToScaleUsingRightMotor - .04;
		double goBackForSecondCube = .3;
		double turnBackUsingLeftMotor = .63;
		double timeToPickUpSecondCube = firstScale + goBackForSecondCube + turnBackUsingLeftMotor + 1;
		double straightToSecondSube = .78;
		double turnToSecondCube = 1.2;
		double secondCubeSecured = timeToPickUpSecondCube + straightTime + turnToSecondCube + .7;
		double backUp = .5;
		if(right)
		{
			if(time < .3)
			{
				IntakeWheels.runIntake(0, 0, true, .12, .12);
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .5;
			}
			else if(time < straightTime - .54)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				return .74;
			}
			else if(time < straightTime)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				return .2;
			}
			else if(time < straightTime + turnToScaleUsingRightMotor)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				return .5;
			}
			else if(time < straightTime + turnToScaleUsingRightMotor + stopAtScale)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				IntakeWheels.runIntake(0, 0, true, -.5, -.5);
				return 0;
			}
			else if(time < firstScale)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				IntakeWheels.runIntake(0, 0, true, 0, 0);
				return -.5;
			}
			else if(time < firstScale + goBackForSecondCube)
			{
				if(Elevator.reachedBottom())
				{
					Elevator.moveElevator(0);
				}
				else
				{
					Elevator.moveElevator(-0.4);
				}
				return -.5;
			}
			else if(time < firstScale + goBackForSecondCube + turnBackUsingLeftMotor)
			{
				if(Elevator.reachedBottom())
				{
					Elevator.moveElevator(0);
				}
				else
				{
					Elevator.moveElevator(-0.4);
				}
				return 0;
			}
			else if(time < timeToPickUpSecondCube)
			{
				if(Elevator.reachedBottom())
				{
					Elevator.moveElevator(0);
				}
				else
				{
					Elevator.moveElevator(-0.4);
				}
				return 0;
			}
			else if(time < timeToPickUpSecondCube + .3)
			{
				Intake.openIntake();
				return .2;
			}
			else if(time < timeToPickUpSecondCube + straightToSecondSube)
			{
				return .5;
			}
			else if(time < timeToPickUpSecondCube + straightToSecondSube + turnToSecondCube)
			{
				IntakeWheels.runIntake(0, 0, true, .5, .5);
				return .5;
			}
			else if(time < timeToPickUpSecondCube + straightTime + turnToSecondCube + .3)
			{
				IntakeWheels.runIntake(0, 0, true, .5, .5);
				return 0;
			}
			else if(time < timeToPickUpSecondCube + straightTime + turnToSecondCube + .3)
			{
				IntakeWheels.runIntake(0, 0, true, .3, .3);
				Intake.closeIntake();
				return 0;
			}
			else if(time < secondCubeSecured)
			{
				IntakeWheels.runIntake(0, 0, true, .12, .12);
				Intake.closeIntake();
				return 0;
			}
			else if(time < secondCubeSecured + turnToSecondCube)
			{
				return -.5;
			}
			else if(time < secondCubeSecured + turnToSecondCube + straightToSecondSube)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				return -.5;
			}
			else if(time < secondCubeSecured + turnToSecondCube + straightToSecondSube + 1)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				return 0;
			}
			else if(time < secondCubeSecured + turnToSecondCube + straightToSecondSube + 1 + turnBackUsingLeftMotor)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				return 0;
			}
			else if(time < secondCubeSecured + turnToSecondCube + straightToSecondSube + 1 + turnBackUsingLeftMotor + goBackForSecondCube)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				return .6;
			}
			else if(time < secondCubeSecured + turnToSecondCube + straightToSecondSube + 1 + turnBackUsingLeftMotor + goBackForSecondCube + turnToScaleUsingRightMotor + .1)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				return .53;
			}
			else if(time < secondCubeSecured + turnToSecondCube + straightToSecondSube + 1 + turnBackUsingLeftMotor + goBackForSecondCube + turnToScaleUsingRightMotor + .1 + stopAtScale)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				IntakeWheels.runIntake(0, 0, true, -.5, -.5);
				return 0;
			}
			else if(time < secondCubeSecured + turnToSecondCube + straightToSecondSube + 1 + turnBackUsingLeftMotor + goBackForSecondCube + turnToScaleUsingRightMotor + .1 + stopAtScale + turnToScaleUsingRightMotor)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				IntakeWheels.runIntake(0, 0, true, 0, 0);
				return -.5;
			}
			else
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				IntakeWheels.runIntake(0, 0, true, 0, 0);
				return 0;
			}
		}
		else
		{
			if(time < .3)
			{
				return .5;
			}
			else if(time < straightTime - .54)
			{
				return .74;
			}
			else if(time < straightTime)
			{
				return .2;
			}
			else if(time < straightTime + turnToScaleUsingRightMotor)
			{
				return 0;
			}
			else if(time < straightTime + turnToScaleUsingRightMotor + stopAtScale)
			{
				return 0;
			}
			else if(time < firstScale)
			{
				return 0;
			}
			else if(time < firstScale + goBackForSecondCube)
			{
				return -.5;
			}
			else if(time < firstScale + goBackForSecondCube + turnBackUsingLeftMotor - .2)
			{
				return -.7;
			}
			else if(time < firstScale + goBackForSecondCube + turnBackUsingLeftMotor)
			{
				return -.5;
			}
			else if(time < timeToPickUpSecondCube)
			{
				return 0;
			}
			else if(time < timeToPickUpSecondCube + .3)
			{
				return .2;
			}
			else if(time < timeToPickUpSecondCube + straightToSecondSube)
			{
				return .5;
			}
			else if(time < timeToPickUpSecondCube + straightToSecondSube + turnToSecondCube)
			{
				return 0;
			}
			else if(time < timeToPickUpSecondCube + straightTime + turnToSecondCube + .3)
			{
				return 0;
			}
			else if(time < secondCubeSecured)
			{
				return 0;
			}
			else if(time < secondCubeSecured + turnToSecondCube)
			{
				return 0;
			}
			else if(time < secondCubeSecured + turnToSecondCube + straightToSecondSube)
			{
				return -.5;
			}
			else if(time < secondCubeSecured + turnToSecondCube + straightToSecondSube + 1)
			{
				return 0;
			}
			else if(time < secondCubeSecured + turnToSecondCube + straightToSecondSube + 1 + turnBackUsingLeftMotor - .2)
			{
				return .7;
			}
			else if(time < secondCubeSecured + turnToSecondCube + straightToSecondSube + 1 + turnBackUsingLeftMotor)
			{
				return .5;
			}
			else if(time < secondCubeSecured + turnToSecondCube + straightToSecondSube + 1 + turnBackUsingLeftMotor + goBackForSecondCube)
			{
				return .6;
			}
			else if(time < secondCubeSecured + turnToSecondCube + straightToSecondSube + 1 + turnBackUsingLeftMotor + goBackForSecondCube + turnToScaleUsingRightMotor + .1)
			{
				return 0;
			}
			else if(time < secondCubeSecured + turnToSecondCube + straightToSecondSube + 1 + turnBackUsingLeftMotor + goBackForSecondCube + turnToScaleUsingRightMotor + .1 + stopAtScale)
			{
				return 0;
			}
			else
			{
				return 0;
			}
		}
	}
	
	public static double rightScaleLeftSwitch(double time, boolean right)
	{
		double straightTime = 3.6;
		double turnToScaleUsingRightMotor = .5;
		double stopAtScale = 1;
		double firstScale = straightTime + turnToScaleUsingRightMotor + stopAtScale + turnToScaleUsingRightMotor - .04;
		double goBackForSecondCube = .7;
		double turnBackUsingLeftMotor = .615;
		double timeToPickUpSecondCube = firstScale + goBackForSecondCube + turnBackUsingLeftMotor + 1;
		double straightToSecondSube = 2.8;
		double turnToSecondCube = 1.03;
		double secondCubeSecured = timeToPickUpSecondCube + straightTime + turnToSecondCube + .7;
		double backUp = .5;
		if(right)
		{
			if(time < .3)
			{
				IntakeWheels.runIntake(0, 0, true, .12, .12);
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .5;
			}
			else if(time < straightTime - .54)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				return .74;
			}
			else if(time < straightTime)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				return .2;
			}
			else if(time < straightTime + turnToScaleUsingRightMotor)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				return .5;
			}
			else if(time < straightTime + turnToScaleUsingRightMotor + stopAtScale)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				IntakeWheels.runIntake(0, 0, true, -.5, -.5);
				return 0;
			}
			else if(time < firstScale)
			{
				Elevator.moveElevatorPosition(Constants.scale);
				IntakeWheels.runIntake(0, 0, true, 0, 0);
				return -.5;
			}
			else if(time < firstScale + goBackForSecondCube)
			{
				if(Elevator.reachedBottom())
				{
					Elevator.moveElevator(0);
				}
				else
				{
					Elevator.moveElevator(-0.4);
				}
				return -.5;
			}
			else if(time < firstScale + goBackForSecondCube + turnBackUsingLeftMotor)
			{
				if(Elevator.reachedBottom())
				{
					Elevator.moveElevator(0);
				}
				else
				{
					Elevator.moveElevator(-0.4);
				}
				return 0;
			}
			else if(time < timeToPickUpSecondCube)
			{
				if(Elevator.reachedBottom())
				{
					Elevator.moveElevator(0);
				}
				else
				{
					Elevator.moveElevator(-0.4);
				}
				return 0;
			}
			else if(time < timeToPickUpSecondCube + .3)
			{
				return .2;
			}
			else if(time < timeToPickUpSecondCube + straightToSecondSube - .6)
			{
				return .7;
			}
			else if(time < timeToPickUpSecondCube + straightToSecondSube)
			{
				return .4;
			}
			else if(time < timeToPickUpSecondCube + straightToSecondSube + turnToSecondCube)
			{
				Intake.openIntake();
				IntakeWheels.runIntake(0, 0, true, .5, .5);
				return .5;
			}
			else if(time < timeToPickUpSecondCube + straightTime + turnToSecondCube + .3)
			{
				IntakeWheels.runIntake(0, 0, true, .5, .5);
				return 0;
			}
			else if(time < timeToPickUpSecondCube + straightTime + turnToSecondCube + .3)
			{
				IntakeWheels.runIntake(0, 0, true, .3, .3);
				Intake.closeIntake();
				return 0;
			}
			else if(time < secondCubeSecured)
			{
				IntakeWheels.runIntake(0, 0, true, .12, .12);
				Intake.closeIntake();
				return 0;
			}
			else if(time < secondCubeSecured + backUp)
			{
				return -.5;
			}
			else if(time < secondCubeSecured + backUp + 1.2)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return 0;
			}
			else if(time < secondCubeSecured + backUp + 1.7)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				return .3;
			}
			else if(time < secondCubeSecured + backUp + 2.1)
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				IntakeWheels.runIntake(0, 0, true, -.9, -.5);
				return 0;
			}
			else
			{
				Elevator.moveElevatorPosition(Constants.sWitch);
				IntakeWheels.runIntake(0, 0, true, 0, 0);
				return 0;
			}
		}
		else
		{
			if(time < .3)
			{
				return .5;
			}
			else if(time < straightTime - .54)
			{
				return .74;
			}
			else if(time < straightTime)
			{
				return .2;
			}
			else if(time < straightTime + turnToScaleUsingRightMotor)
			{
				return 0;
			}
			else if(time < straightTime + turnToScaleUsingRightMotor + stopAtScale)
			{
				return 0;
			}
			else if(time < firstScale)
			{
				return 0;
			}
			else if(time < firstScale + goBackForSecondCube)
			{
				return -.5;
			}
			else if(time < firstScale + goBackForSecondCube + turnBackUsingLeftMotor - .2)
			{
				return -.7;
			}
			else if(time < firstScale + goBackForSecondCube + turnBackUsingLeftMotor)
			{
				return -.5;
			}
			else if(time < timeToPickUpSecondCube)
			{
				return 0;
			}
			else if(time < timeToPickUpSecondCube + .3)
			{
				return .2;
			}
			else if(time < timeToPickUpSecondCube + straightToSecondSube)
			{
				return .5;
			}
			else if(time < timeToPickUpSecondCube + straightToSecondSube + turnToSecondCube)
			{
				return 0;
			}
			else if(time < timeToPickUpSecondCube + straightTime + turnToSecondCube + .3)
			{
				return 0;
			}
			else if(time < secondCubeSecured + backUp)
			{
				return -.5;
			}
			else if(time < secondCubeSecured + backUp + 1.2)
			{
				return 0;
			}
			else if(time < secondCubeSecured + backUp + 1.7)
			{
				return .3;
			}
			else if(time < secondCubeSecured + backUp + 2.1)
			{
				return 0;
			}
			else
			{
				return 0;
			}
		}
	}
	
	public static double stopToPickUp(double eValue)
	{
		eValue*=(-0.00005);
		eValue+=.3;
		return eValue;
	}
	
	public static double stopToSwitch(double eValue)
	{
		eValue*=(-0.000046153834);
		eValue+=.8;
		return eValue;
		//.8 to .2	
	}
	
	public static double stopToScale(double eValue)
	{
		
		if(eValue < 25000)
		{
			return 1;
		}
		else
		{
			eValue*=(-0.0000352941176471);
			eValue+=1.733;
			return eValue;
			//.85 to .25
		}
	}
	
	public static double pickUpToStop(double eValue)
	{
		return -.2;
	}
	
	public static double pickUpToSwitch(double eValue)
	{
		eValue*=(-0.000046153834);
		eValue+=.8;
		return eValue;
		//.7 to .2
	}
	
	public static double pickUpToScale(double eValue)
	{
		
		if(eValue < 25000)
		{
			return 1;
		}
		else
		{
			eValue*=(-0.0000352941176471);
			eValue+=1.733;
			return eValue;
			//.85 to .25
		}
	}
	
	public static double switchToStop(double eValue)
	{
		if(eValue > 5000)
		{
			return -.4;
		}
		else
		{
			return - .18;
		}
	}
	
	public static double switchToPickUp(double eValue)
	{
		if(eValue > 5000)
		{
			return -.35;
		}
		else
		{
			return - .16;
		}
	}
	
	public static double switchToScale(double eValue)//
	{
		if(eValue < 20000)
		{
			
			return 1;
		}
		else
		{
			eValue*=(-0.0000352941176471);
			eValue+=1.733;
			
			return eValue;
			//.85 to .25
		}
		
		//.8 to .2
	}
	
	public static double stopToLowerScale(double eValue)
	{
		if(eValue<Constants.sWitch)
		{
			return 1;
		}
		else
		{
			eValue*=(-0.0000230769230769);
			eValue+=1.031;
			return eValue;
		}
	}
	
	public static double pickUpToLowerScale(double eValue)
	{
		if(eValue<Constants.sWitch)
		{
			return 1;
		}
		else
		{
			eValue*=(-0.0000230769230769);
			eValue+=1.031;
			return eValue;
		}
	}
	
	public static double switchToLowerScale(double eValue)
	{
		eValue*=(-0.0000230769230769);
		eValue+=1.031;
		return eValue;
	}
	
	public static double scaleToLowerScale(double eValue)
	{
		return -.2;
	}
	
	public static double lowerScaleToScale(double eValue)
	{
		
		if(eValue < 40000)
		{
			return .4;
		}
		else
		{
			return .2;
		}
	}
	
	public static double lowerScaleToSwitch(double eValue)
	{
		return -.28;
	}
	
	public static double lowerScaleToPickUp(double eValue)
	{
		if(eValue > Constants.sWitch)
		{
			return -.5;
		}
		else
		{
			return -.15;
		}
	}
	
	public static double lowerScaleToStop(double eValue)
	{
		if(eValue > Constants.sWitch)
		{
			return -.5;
		}
		else
		{
			return -.17;
		}
	}
	
	public static double scaleToStop(double eValue)//
	{
		if(eValue > Constants.sWitch)
		{
			return -.5;
		}
		else
		{
			return -.17;
		}
	}
	
	public static double scaleToPickUp(double eValue)//
	{
		if(eValue > Constants.sWitch)
		{
			return -.5;
		}
		else
		{
			return -.15;
		}
	}
	
	public static double scaleToSwitch(double eValue)
	{
		return -.32;
	}
	
	public static double test1and3Straight(double lValue, double rValue, double dist)
	{
		avg = (lValue + rValue)/2.0;
		if(avg < 1800)
		{
			return .6;
		}
		else if(avg < 4000)
		{
			return .74;
		}
		else if(avg < 7000)
		{
			return .87;
		}
		else if(avg < 10000)
		{
			return 1;
		}
		else if(avg < (dist -2000))
		{
			return .74;
		}
		else if(avg < dist)
		{
			return .34;
		}
		else
		{
			return 0;
		}
		//assuming distance 17000
	}
	
	
	public static double test3Turn(double eValue, double dist)
	{
		if(eValue < (dist - 2000))
		{
			return .7;
		}
		else if(eValue < dist)
		{
			return .5;
		}
		else
		{
			return 0;
		}
	}
	
	public static double test4Turn(double eValue, double dist)
	{
		eValue = Math.abs(eValue);
		if(eValue < (dist - 2000))
		{
			return -.7;
		}
		else if(eValue < dist)
		{
			return -.5;
		}
		else
		{
			return 0;
		}
	}
	
	public static double test5Turn(double eValue, double dist)
	{
		eValue = Math.abs(eValue);
		if(eValue < (dist - 2000))
		{
			return -.7;
		}
		else if(eValue < dist)
		{
			return -.5;
		}
		else
		{
			return 0;
		}
	}
	
	public static double testCurve(double eValue, double dist)
	{
		eValue = Math.abs(eValue);
		if(eValue < (dist - 1000))
		{
			return .6;
		}
		else if(eValue < dist)
		{
			return .3;
		}
		else
		{
			return 0;
		}
	}
	
	//Competition Stuff
	public static double oneCubeSwitchRightSideStraight(double sum, double dist)
	{
		if(sum < 2000)
		{
			return .6;
		}
		else if(sum < (dist-1500))
		{
			return .8;
		}
		else if(sum < dist)
		{
			return .4;
		}
		else
		{
			return 0;
		}
	}
	
	public static double oneCubeSwitchRightSideCurve(double eValue, double dist)
	{
		eValue = Math.abs(eValue);
		if(eValue < (dist - 1000))
		{
			return .6;
		}
		else if(eValue < dist)
		{
			return .3;
		}
		else
		{
			return 0;
		}
	}
	
	public static double twoCubeSwitchRightSideStraight(double sum, double dist)
	{
		if(sum < 2000)
		{
			return .4;
		}
		else if(sum < (dist-2500))
		{
			return .6;
		}
		else if(sum < (dist-1500))
		{
			return .45;
		}
		else if(sum < dist)
		{
			return .3;
		}
		else
		{
			return 0;
		}
	}
	
	public static double twoCubeSwitchRightSideCurve(double eValue, double dist)
	{
		eValue = Math.abs(eValue);
		if(eValue < 1500)
		{
			return .5;
		}
		else if(eValue < (dist - 2000))
		{
			return .6;
		}
		else if(eValue < dist)
		{
			return .3;
		}
		else
		{
			return 0;
		}
	}
	
	public static double twoCubeSwitchLeftSideFirstCurve(double eValue, double dist)
	{
		if(eValue < (dist - 2000))
		{
			return .4;
		}
		else if(eValue < (dist))
		{
			return .3;
		}
		else
		{
			return 0;
		}
	}
	
	public static double twoCubeSwitchLeftSideStraightCrossField(double eValue, double dist)
	{
		if(eValue < 3000)
		{
			return .5;
		}
		else if(eValue < (dist-1500))
		{
			return .7;
		}
		else if(eValue < (dist))
		{
			return .4;
		}
		else
		{
			return 0;
		}
	}
	
	public static double twoCubeSwitchLeftSideSecondCurve(double eValue, double dist)
	{
		if(eValue < (dist - 2000))
		{
			return .4;
		}
		else if(eValue < (dist))
		{
			return .3;
		}
		else
		{
			return 0;
		}
	}
	
//	public static void lrandrrElevatorForFirstScale(double lValue, double rValue, double eValue, double step)
//	{
//		if(step == 1)
//		{
//			reached = false;
//			avg = (lValue + rValue)/2.0;
//			if(avg < 10000)
//			{
//				if(eValue < 8000)
//				{
//					Elevator.moveEleVader(.6);
//				}
//				else if(!ElevatorLevel.reachedSwitch())
//				{
//					Elevator.moveEleVader(.3);
//				}
//				else
//				{
//					ElevatorLevel.maintainSwitchPosition();
//				}
//			}
//			else
//			{
//				Elevator.moveEleVader(Functions.switchToScale(ElevatorLevel.elevatorEncoderValue));
//			}
//		}
//	}
//	
//	public static double lrandrrSpeedForFirstScale(double lValue, double rValue, double dist)
//	{
//		avg = (lValue + rValue)/2.0;
//		if(avg < 2000)
//		{
//			return .6;
//		}
//		else if(avg < 5000)
//		{
//			return .74;
//		}
//		else if(avg < 14000)
//		{
//			return .9;
//		}
//		else if(avg < (dist -1200))
//		{
//			return .7;
//		}
//		else if(avg < dist)
//		{
//			return .3;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double lrandrrFirstTurnToScale(double eValue, double dist)
//	{
//		if(eValue < (dist - 2000))
//		{
//			return .7;
//		}
//		else if(eValue < dist)
//		{
//			return .5;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double lrandrrBackUpTurnAfterScale(double eValue, double dist)
//	{
//		eValue = Math.abs(eValue);
//		if(eValue < (dist - 2000))
//		{
//			return -.7;
//		}
//		else if(eValue < dist)
//		{
//			return -.5;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static void moveElevatorToStop(double eValue)
//	{
//		if(!ElevatorLevel.reachedStop())
//		{
//			Elevator.moveEleVader(scaleToStop(eValue));
//		}
//		else
//		{
//			Elevator.stopEleVader();
//		}
//	}
//	
//	public static double lrandrrBackUpToWallTurn(double eValue, double dist)
//	{
//		eValue = Math.abs(eValue);
//		if(eValue < (dist - 2000))
//		{
//			return -.6;
//		}
//		else if(eValue < dist)
//		{
//			return -.4;
//		}
//		else
//		{
//			return 0;
//		}
//	}

}