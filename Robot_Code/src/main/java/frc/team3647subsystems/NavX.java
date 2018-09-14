package frc.team3647subsystems;

import frc.team3647ConstantsAndFunctions.*;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;

public class NavX
{
    public double yaw, pitch, roll;

    public AHRS AHRSnavX = new AHRS(SPI.Port.kMXP);

    public void setAngle()
    {
        yaw = AHRSnavX.getYaw();
        pitch = AHRSnavX.getPitch();
        roll = AHRSnavX.getRoll();
    }

    public void resetAngle()
    {
        AHRSnavX.zeroYaw();
    }

    public void testAngle()
    {
        System.out.println("Yaw: " + yaw + " Pitch: " + pitch + " Roll: " + roll);
    }
}