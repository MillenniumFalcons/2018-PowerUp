package frc.team3647autos;

import edu.wpi.first.wpilibj.*;
import frc.robot.Constants;
import frc.team3647autos.*;
import frc.team3647pistons.*;
import frc.team3647subsystems.*;
import frc.team3647inputs.*;


public class MPAutos
{
    static TrajectoryFollower traj = new TrajectoryFollower();
    public static Timer stopWatch = new Timer();
    public static int currentState;
    public static double time;
    static double currTime, prevTime;

    public static void intialization(Encoders enc, NavX navX)
    {
        enc.resetEncoders();
        navX.resetAngle();
        traj.initialize();
        Drivetrain.setToBrake();
        Drivetrain.stop();
        Intake.closeIntake();
        IntakeWheels.runIntake(0, 0, true, .15, .15, false);
        currentState = 0;
    }

    public static String switchSide = "Right";

    public static void middleSwitch(Encoders enc, NavX navX)
    {
        enc.setEncoderValues();
        navX.setAngle();
        switch(currentState)
        {
            case 0: //init
                if(enc.leftEncoderValue == 0 && enc.rightEncoderValue == 0 && navX.yawUnClamped == 0 && stopWatch.get() == 0)
                {
                    stopWatch.start();
                    System.out.println("Loading first path");
                    System.out.println("Finish Loaded");
                    Elevator.currentWristState = 0;
                    currentState = 1;
                }
                else
                {
                    enc.resetEncoders();
                    stopWatch.reset();
                    navX.resetAngle();
                    Wrist.wristMotor.setSelectedSensorPosition(Constants.up, 1, Constants.kTimeoutMs);
                    System.out.println("zeroing");
                }
                break;
            case 1: //zero elevator
                Wrist.moveUp();
                if(Elevator.reachedBottom)
                {
                    Elevator.stopElevator();
                    traj.followPath(switchSide + "MiddleToSwitch0", false, false);
                    currentState = 2;
                }
                else if(stopWatch.get() > 1)
                {
                    Elevator.stopElevator();
                    traj.followPath(switchSide + "MiddleToSwitch0", false, false);
                    currentState = 2;
                }
                else
                {
                    Elevator.moveBottom(false);
                }
                break;
            case 2: //go to switch and raise elevator
                if(traj.isFinished())
                {
                    stopWatch.reset();
                    stopWatch.start();
                    currentState = 3;
                }
                else
                {
                    Elevator.moveSwitch();
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
                }
                break;
            case 3: //maintain elevator height and drop cube
                time = stopWatch.get();
                Elevator.moveSwitch();
                if(time < Constants.shootCubeTime)
                {
                    IntakeWheels.runIntake(0, 0, true, 1, 1, false);
                }
                else
                {
                    stopWatch.reset();
                    stopWatch.start();
                    enc.resetEncoders();
                    traj.followPath(switchSide + "MiddleToSwitch1", true, false);
                    currentState = 4;
                }
                break;
            case 4: //move elevator and wrist down and back up
                IntakeWheels.runIntake(0, 0, true, 0, 0, false);
                Elevator.moveBottom(false);
                Wrist.moveToFlat();
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
                if(traj.isFinished())
                {
                    stopWatch.reset();
                    stopWatch.start();
                    enc.resetEncoders();
                    traj.followPath("MiddleToSwitch2", false, false);
                    currentState = 5;
                }
                break;
            case 5: //move to intake second cube and run intake
                Wrist.moveToFlat();
                IntakeWheels.runIntake(0, 0, true, 0.8, 0.7, false);
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
                if(enc.leftEncoderValue > 3000 * 0.8) //if done with 80% of straight path, close intake
                {
                    Intake.closeIntake();
                }
                else
                {
                    Intake.openIntake();
                }
                if(traj.isFinished())
                {
                    stopWatch.reset();
                    stopWatch.start();
                    traj.followPath("MiddleToSwitch3", true, false);
                    currentState = 6;
                }
            case 6: //backup and move wrist up
                IntakeWheels.runIntake(0, 0, true, 0, 0, false);
                Wrist.moveUp();
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
                if(traj.isFinished())
                {
                    stopWatch.reset();
                    stopWatch.start();
                    enc.resetEncoders();
                    traj.followPath(switchSide + "MiddleToSwitch4", false, false);
                    currentState = 7;
                }
                break;
            case 7: //go to switch and raise elevator
                Wrist.moveToFlat();
                Elevator.moveSwitch();
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
                if(traj.isFinished())
                {
                    stopWatch.reset();
                    stopWatch.start();
                    currentState = 8;
                }
                break;
            case 8: //maintain elevator height and drop cube
                Elevator.moveSwitch();
                IntakeWheels.runIntake(0, 0, true, .75, .75, false);
                break;
        }
    }

    public static void soloPath(Encoders enc, NavX navX)
    {
        enc.setEncoderValues();
        navX.setAngle();
        switch(currentState)
        {
            case 0:
                enc.resetEncoders();
                navX.resetAngle();
                System.out.println("Loading Path");
                traj.initialize();
                //traj.followPath("leftSwitchFromMiddle1", false, false);   
                //traj.followPath(WaypointPaths.middleToRightSwitch(), false);
                traj.followPath("RightMiddleToSwitch", false, false);
                //traj.followPath("StraightTenFeet", false);
               // traj.followPath("SuryaOmegaLul", false);
                //traj.followPath("StraightandLeftCurve", false);
                currentState = 1;
                break;
            case 1:
            System.out.println("Running Path (1/2)");
                traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, navX.yawUnClamped);
                if(traj.isFinished())
                {
                    System.out.println("Path Finished (2/2)");
                    currentState = 2;
                }
                else
                {
                    System.out.println("PATH NOT FINISHED");
                }
                break;
            case 2:
                System.out.println("CASE 2 REACHED (path finished)");
                break;
        }
    }
}