package frc.team3647subsystems;

import frc.team3647ConstantsAndFunctions.*;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;

public class NavX
{
    public AHRS AHRSnavX;

    public double yaw, pitch, roll;

    public void navXInitialize()
    {
        AHRSnavX = new AHRS(SPI.Port.kMXP);
    }

    public void getAngle()
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