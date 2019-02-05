package frc.team3647autos;

import jaci.pathfinder.*;
import frc.team3647autos.*;
import frc.team3647pistons.*;
import frc.team3647subsystems.*;
import frc.team3647inputs.*;
import frc.robot.*;

public class WaypointPaths
{
    public static Trajectory middleToRightSwitch()
    {
        Waypoint[] middleToRightSwitch = new Waypoint[]
        {
            new Waypoint(3.25, -12.9167, Pathfinder.d2r(0)), //x, y, angle (d2r converts from angles to radians)
            new Waypoint(11.75, 9, Pathfinder.d2r(0))
        };
        Trajectory.Config configPoints = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_LOW, Constants.MPTimeStep, 10, 12, Constants.maxJerk); //fit, samples, timestep, velocity, acceleration, jerk
        Trajectory trajPoints = Pathfinder.generate(middleToRightSwitch, configPoints); //generate points
        return trajPoints;
    }
}