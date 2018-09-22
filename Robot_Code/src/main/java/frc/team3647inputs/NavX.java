package frc.team3647inputs;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;

public class NavX
{
    AHRS AHRSnavX = new AHRS(SPI.Port.kMXP);

    public double yaw, yawUnClamped, pitch, roll, actualYaw;

    public void setAngle()
    {
        yaw = AHRSnavX.getYaw();
        yawUnClamped = AHRSnavX.getAngle();
        pitch = AHRSnavX.getPitch();
        roll = AHRSnavX.getRoll();
        setActualYaw();
    }

    public void resetAngle()
    {
        AHRSnavX.zeroYaw();
    }

    public void testAngle()
    {
        System.out.println("Yaw: " + -yaw + "Yaw (Unclamped): " + yawUnClamped + " Pitch: " + pitch + " Roll: " + roll);
    }

    public void setActualYaw()
    {
        if(yaw < 0)
        {
            actualYaw = 360 - yaw;
        }
        else
        {
            actualYaw = yaw;
        }
    }
}