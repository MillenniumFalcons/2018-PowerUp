package frc.team3647inputs;

import com.kauailabs.navx.*;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.*;

public class NavX
{
    AHRS navX = new AHRS(SPI.Port.kMXP);

    public double yaw, yawUnClamped, pitch, roll, actualYaw, b;

    public void setAngle()
    {
        yaw = navX.getYaw();
        yawUnClamped = navX.getAngle();
        pitch = navX.getPitch();
        roll = navX.getRoll();
        setActualYaw();
    }

    public void resetAngle()
    {
        navX.zeroYaw();
    }

    public void testAngle()
    {
        System.out.println("Yaw: " + -yaw + "Yaw (Unclamped): " + yawUnClamped + " Pitch: " + pitch + " Roll: " + roll);
    }

    public void setActualYaw()
    {
        if(yaw < 0)
        {
            b = Math.abs(yaw);
            actualYaw = 360 - b;
        }
        else
        {
            actualYaw = yaw;
        }
    }
}