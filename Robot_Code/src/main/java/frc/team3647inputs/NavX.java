package frc.team3647inputs;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;

public class NavX
{
    AHRS AHRSnavX = new AHRS(SPI.Port.kMXP);

    public double yaw, yawUnClamped, pitch, roll;

    public void setAngle()
    {
        yaw = AHRSnavX.getYaw();
        yawUnClamped = AHRSnavX.getAngle();
        pitch = AHRSnavX.getPitch();
        roll = AHRSnavX.getRoll();
    }

    public void resetAngle()
    {
        AHRSnavX.zeroYaw();
    }

    public void testAngle()
    {
        System.out.println("Yaw: " + -yaw + "Yaw (Unclamped): " + yawUnClamped + " Pitch: " + pitch + " Roll: " + roll);
    }
}