package frc.team3647autos;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.*;
import frc.team3647autos.*;
import frc.team3647pistons.*;
import frc.team3647subsystems.*;
import frc.team3647inputs.*;

public class Autonomous 
{
    static TrajectoryFollower traj = new TrajectoryFollower();
    public static Timer stopWatch = new Timer();
    public static int currentState;
    public static double time;
    static double currTime, prevTime;

    public static void initialize(Encoders enc, NavX navX)
    {
        enc.resetEncoders();
        navX.resetAngle();
        traj.initialize();
        Drivetrain.setToBrake();
        Drivetrain.stop();
        currentState = 0;
    }

    public static void rightSide2SwitchFromRightSide(Encoders enc, NavX gyro)
    {
        enc.setEncoderValues();
        gyro.setAngle();
        switch(currentState)
        {
            case 0:
                // Zeroing all sensors
                if(gyro.yawUnClamped == 0 && enc.rightEncoderValue == 0 && enc.leftEncoderValue == 0)
                {
                    stopWatch.start();
                    currentState = 1;
                }
                else 
                {
                    gyro.resetAngle();
                }
                break;
            case 1:
                // Loading the path
                traj.followPath("rightSide2SwitchFromRightSide", false, false);
                currentState = 2;
                break;
            case 2:
                if(traj.isFinished())
                {
                   Drivetrain.stop();
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
        }
    }

    public static void leftSide2SwitchFromRightSide(Encoders enc, NavX gyro)
    {
        enc.setEncoderValues();
        gyro.setAngle();
        switch(currentState)
        {
            case 0:
                // Zeroing all sensors
                if(gyro.yawUnClamped == 0 && enc.rightEncoderValue == 0 && enc.leftEncoderValue == 0)
                {
                    stopWatch.start();
                    currentState = 1;
                }
                else 
                {
                    gyro.resetAngle();
                }
                break;
            case 1:
                // Loading the path
                traj.followPath("leftSide2SwitchFromRightSide", false, false);
                currentState = 2;
                break;
            case 2:
                if(traj.isFinished())
                {
                   Drivetrain.stop();
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
        }
    }

    public static void jankOppositeScaleFromRight(Encoders enc, NavX gyro)
    {
        enc.setEncoderValues();
        gyro.setAngle();
        switch(currentState)
        {
            case 0:
                // Zeroing all sensors
                if(gyro.yawUnClamped == 0 && enc.rightEncoderValue == 0 && enc.leftEncoderValue == 0)
                {
                    stopWatch.start();
                    currentState = 1;
                }
                else 
                {
                    gyro.resetAngle();
                }
                break;
            case 1:
                // Loading the path
                traj.followPath("jankOppositeScaleFromRight", false, false);
                currentState = 2;
                break;
            case 2:
                if(traj.isFinished())
                {
                   Drivetrain.stop();
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
        }
    }

    public static void sharpOppositeScaleFromRight(Encoders enc, NavX gyro)
    {
        enc.setEncoderValues();
        gyro.setAngle();
        switch(currentState)
        {
            case 0:
                // Zeroing all sensors
                if(gyro.yawUnClamped == 0 && enc.rightEncoderValue == 0 && enc.leftEncoderValue == 0)
                {
                    stopWatch.start();
                    currentState = 1;
                }
                else 
                {
                    gyro.resetAngle();
                }
                break;
            case 1:
                // Loading the path
                traj.followPath("sharpOppositeScaleFromRight", false, false);
                currentState = 2;
                break;
            case 2:
                if(traj.isFinished())
                {
                   Drivetrain.stop();
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
        }
    }

    public static void leftSwitchFromMiddle(Encoders enc, NavX gyro)
    {
        enc.setEncoderValues();
        gyro.setAngle();
        switch(currentState)
        {
            case 0:
                // Zeroing all sensors
                if(gyro.yawUnClamped == 0 && enc.rightEncoderValue == 0 && enc.leftEncoderValue == 0)
                {
                    stopWatch.start();
                    currentState = 1;
                }
                else 
                {
                    gyro.resetAngle();
                }
                break;
            case 1:
                // Loading the path
                traj.followPath("leftSwitchFromMiddle1", false, false);
                currentState = 2;
                break;
            case 2:
                if(traj.isFinished())
                {
                   prevTime = stopWatch.get();
                   currentState = 3;
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
            case 3:
                currTime = stopWatch.get() - prevTime;
                if(currTime < .4)
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                else 
                {
                    traj.followPath("leftSwitchFromMiddle2", false, false);
                    currentState = 4;
                }
                break;
            case 4:
                if(traj.isFinished())
                {
                    prevTime = stopWatch.get();
                    currentState = 5;
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
            case 5:
                currTime = stopWatch.get() - prevTime;
                if(currTime < .4)
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                else 
                {
                    traj.followPath("leftSwitchFromMiddle3", false, false);
                    currentState = 6;
                }
                break;
            case 6:
                if(traj.isFinished())
                {
                    prevTime = stopWatch.get();
                    currentState = 7;
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
            case 7:
                currTime = stopWatch.get() - prevTime;
                if(currTime < .4)
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                else 
                {
                    traj.followPath("leftSwitchFromMiddle4", false, false);
                    currentState = 8;
                }
                break;
            case 8:
                if(traj.isFinished())
                {
                    prevTime = stopWatch.get();
                    currentState = 9;
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
            case 9:
                currTime = stopWatch.get() - prevTime;
                if(currTime < .4)
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                else 
                {
                    traj.followPath("leftSwitchFromMiddle5", false, false);
                    currentState = 10;
                }
                break;
            case 10:
                if(traj.isFinished())
                {
                    prevTime = stopWatch.get();
                    currentState = 11;
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
            case 11:
                currTime = stopWatch.get() - prevTime;
                if(currTime < .4)
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                else 
                {
                    traj.followPath("leftSwitchFromMiddle2", false, false);
                    currentState = 12;
                }
                break;
            case 12:
                if(traj.isFinished())
                {
                    prevTime = stopWatch.get();
                    currentState = 13;
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
            case 13:
                currTime = stopWatch.get() - prevTime;
                if(currTime < .4)
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                else 
                {
                    traj.followPath("leftSwitchFromMiddle6", false, false);
                    currentState = 14;
                }
                break;
            case 14:
                if(traj.isFinished())
                {
                    prevTime = stopWatch.get();
                    currentState = 15;
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
            case 15:
                currTime = stopWatch.get() - prevTime;
                if(currTime < .4)
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                else 
                {
                    traj.followPath("leftSwitchFromMiddle7", false, false);
                    currentState = 16;
                }
                break;
            case 16:
                if(traj.isFinished())
                {
                    prevTime = stopWatch.get();
                    currentState = 17;
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
            case 17:
                currTime = stopWatch.get() - prevTime;
                if(currTime < .4)
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                else 
                {
                    traj.followPath("leftSwitchFromMiddle5", false, false);
                    currentState = 18;
                }
                break;
            case 18:
                if(traj.isFinished())
                {
                    Drivetrain.stop();
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
        }
    }


    public static void rightSwitchFromMiddle(Encoders enc, NavX gyro)
    {
        enc.setEncoderValues();
        gyro.setAngle();
        switch(currentState)
        {
            case 0:
                // Zeroing all sensors
                if(gyro.yawUnClamped == 0 && enc.rightEncoderValue == 0 && enc.leftEncoderValue == 0)
                {
                    stopWatch.start();
                    currentState = 1;
                }
                else 
                {
                    gyro.resetAngle();
                }
                break;
            case 1:
                // Loading the path
                traj.followPath("rightSwitchFromMiddle1", false, false);
                currentState = 2;
                break;
            case 2:
                if(traj.isFinished())
                {
                   prevTime = stopWatch.get();
                   currentState = 3;
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
            case 3:
                currTime = stopWatch.get() - prevTime;
                if(currTime < .4)
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                else 
                {
                    traj.followPath("rightSwitchFromMiddle2", false, false);
                    currentState = 4;
                }
                break;
            case 4:
                if(traj.isFinished())
                {
                    prevTime = stopWatch.get();
                    currentState = 5;
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
            case 5:
                currTime = stopWatch.get() - prevTime;
                if(currTime < .4)
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                else 
                {
                    traj.followPath("rightSwitchFromMiddle3", false, false);
                    currentState = 6;
                }
                break;
            case 6:
                if(traj.isFinished())
                {
                    prevTime = stopWatch.get();
                    currentState = 7;
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
            case 7:
                currTime = stopWatch.get() - prevTime;
                if(currTime < .4)
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                else 
                {
                    traj.followPath("rightSwitchFromMiddle4", false, false);
                    currentState = 8;
                }
                break;
            case 8:
                if(traj.isFinished())
                {
                    prevTime = stopWatch.get();
                    currentState = 9;
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
            case 9:
                currTime = stopWatch.get() - prevTime;
                if(currTime < .4)
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                else 
                {
                    traj.followPath("rightSwitchFromMiddle5", false, false);
                    currentState = 10;
                }
                break;
            case 10:
                if(traj.isFinished())
                {
                    prevTime = stopWatch.get();
                    currentState = 11;
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
            case 11:
                currTime = stopWatch.get() - prevTime;
                if(currTime < .4)
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                else 
                {
                    traj.followPath("rightSwitchFromMiddle2", false, false);
                    currentState = 12;
                }
                break;
            case 12:
                if(traj.isFinished())
                {
                    prevTime = stopWatch.get();
                    currentState = 13;
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
            case 13:
                currTime = stopWatch.get() - prevTime;
                if(currTime < .4)
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                else 
                {
                    traj.followPath("rightSwitchFromMiddle6", false, false);
                    currentState = 14;
                }
                break;
            case 14:
                if(traj.isFinished())
                {
                    prevTime = stopWatch.get();
                    currentState = 15;
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
            case 15:
                currTime = stopWatch.get() - prevTime;
                if(currTime < .4)
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                else 
                {
                    traj.followPath("rightSwitchFromMiddle7", false, false);
                    currentState = 16;
                }
                break;
            case 16:
                if(traj.isFinished())
                {
                    prevTime = stopWatch.get();
                    currentState = 17;
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
            case 17:
                currTime = stopWatch.get() - prevTime;
                if(currTime < .4)
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                else 
                {
                    traj.followPath("rightSwitchFromMiddle5", false, false);
                    currentState = 18;
                }
                break;
            case 18:
                if(traj.isFinished())
                {
                    Drivetrain.stop();
                }
                else 
                {
                    traj.runPath(enc.leftEncoderValue, enc.rightEncoderValue, gyro.yawUnClamped);
                }
                break;
        }
    }

    
}