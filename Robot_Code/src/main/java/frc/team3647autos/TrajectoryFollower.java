package frc.team3647autos;


import jaci.pathfinder.*;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import frc.team3647ConstantsAndFunctions.Constants;
import frc.team3647subsystems.Drivetrain;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.File;

public class TrajectoryFollower
{
    Trajectory leftTrajectory, rightTrajectory;
    Trajectory adjustedLeftTrajectory, adjustedRightTrajectory;
    Trajectory preReverseR, preReverseL;
    

    EncoderFollower right = new EncoderFollower();
    EncoderFollower left = new EncoderFollower();
    int adjustedREncoder, adjustedlEncoder;
    int reverseModifier;
    boolean finalReverse;
    double angleAdjustment;
    
    public void runPath(int lEncoder, int rEncoder, double navXAngle)
    {
        if(!finalReverse)
        {
            adjustedREncoder = rEncoder;
            adjustedlEncoder = lEncoder;
            reverseModifier = 1;
            angleAdjustment= 0;
        }
        else
        {
            adjustedlEncoder = -rEncoder;
            adjustedREncoder = -lEncoder;
            reverseModifier = -1;
            angleAdjustment = 180;
        }
        
        //set follower values
        double rValue = right.calculate(adjustedREncoder);
        double lValue = left.calculate(adjustedlEncoder);
            
        //navX gyro code
        double gyroHeading = -1*navXAngle + angleAdjustment; //invert since RHR
        double desiredHeading = Pathfinder.r2d(right.getHeading());
        double headingDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        double turn = 0.8 * (-1.0/80.0) * headingDifference;
        double rPower = rValue + turn;
        double lPower = lValue - turn;

        if(finalReverse)
        {
            Drivetrain.setPercentOutput(rPower*reverseModifier, lPower*reverseModifier); //with gyro
        }
        else
        {
            Drivetrain.setPercentOutput(lPower, rPower);
        }
        //set output
        //Drivetrain.setPercentOutput(lValue, rValue); //no gyro
        //Drivetrain.setPercentOutput(lPower, rPower);

        SmartDashboard.putNumber("target left speed", lValue);
        SmartDashboard.putNumber("target right speed", rValue);
        SmartDashboard.putNumber("left encoder value", lEncoder);
        SmartDashboard.putNumber("right encoder value", rEncoder);
    }

    public void followPath(String path, boolean backward)
    {
        if(backward)
        {
            rightTrajectory = Pathfinder.readFromCSV(new File("/home/lvuser/paths/" + path + "_left_Jaci.csv"));
            leftTrajectory = Pathfinder.readFromCSV(new File("/home/lvuser/paths/" + path + "_right_Jaci.csv"));
        }
        else
        {
            rightTrajectory = Pathfinder.readFromCSV(new File("/home/lvuser/paths/" + path + "_right_Jaci.csv"));
            leftTrajectory = Pathfinder.readFromCSV(new File("/home/lvuser/paths/" + path + "_left_Jaci.csv"));
        }

        finalReverse = backward;
        
        right.setTrajectory(rightTrajectory);
        left.setTrajectory(leftTrajectory);
    }

    public void followPath(Trajectory trajPoints, boolean backward)
    {
        // Trajectory.Config configPoints = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_LOW, Constants.MPTimeStep, maxVelocity, maxAcceleration, Constants.maxJerk);
        // Trajectory trajPoints = Pathfinder.generate(points, configPoints);

        TankModifier tankModifier = new TankModifier(trajPoints);
        tankModifier.modify(Constants.wheelBase);

        if(backward)
        {
            leftTrajectory = tankModifier.getRightTrajectory();
            rightTrajectory = tankModifier.getLeftTrajectory();
        }
        else
        {
            leftTrajectory = tankModifier.getLeftTrajectory();
            rightTrajectory = tankModifier.getRightTrajectory();
        }

        finalReverse = backward;

        right.setTrajectory(rightTrajectory);
        left.setTrajectory(leftTrajectory);
    }

    public void initialize()
    {
        right.configureEncoder(0, 1440, Constants.wheelDiameter);//first arg set to 0 since encoders reset just before
        left.configureEncoder(0, 1440, Constants.wheelDiameter);

        //set PID values
        right.configurePIDVA(Constants.rPFkP, Constants.rPFkI, Constants.rPFkD, Constants.rPFkV, Constants.rPFkA);
        left.configurePIDVA(Constants.lPFkP, Constants.lPFkI, Constants.lPFkD, Constants.lPFkV, Constants.lPFkA);
    }

    public boolean isFinished()
    {
        return left.isFinished() && right.isFinished();
    }
}


// public void runPath(int lEncoder, int rEncoder, double navXAngle, boolean reverse)
// {
//     //set follower values
//         double rValue = right.calculate(rEncoder);
//         double lValue = left.calculate(lEncoder);
        
//     //navX gyro code
//     double gyroHeading = -navXAngle; //invert since RHR
//     double desiredHeading = Pathfinder.r2d(right.getHeading());
//     double headingDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
//     double turn = 0.8 * (-1.0/80.0) * headingDifference;
//     double rPower = rValue + turn;
//     double lPower = lValue - turn;

//     //set output
//     //Drivetrain.setPercentOutput(lValue, rValue); //no gyro
//     Drivetrain.setPercentOutput(lPower, rPower); //with gyro

//     SmartDashboard.putNumber("target left speed", lValue);
//     SmartDashboard.putNumber("target right speed", rValue);
//     SmartDashboard.putNumber("left encoder value", lEncoder);
//     SmartDashboard.putNumber("right encoder value", rEncoder);
// }
