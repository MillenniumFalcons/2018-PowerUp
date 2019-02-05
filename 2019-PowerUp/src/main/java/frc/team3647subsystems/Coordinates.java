package frc.team3647subsystems;

import frc.team3647inputs.*;

public class Coordinates
{
    public static double cordX, cordY, angle;
    public static int lEnc, rEnc;

    public static void returnCords(Encoders enc, NavX gyro)
    {
        lEnc = enc.leftEncoderValue;
        rEnc = enc.rightEncoderValue;
        angle = gyro.yawUnClamped;

    }
}