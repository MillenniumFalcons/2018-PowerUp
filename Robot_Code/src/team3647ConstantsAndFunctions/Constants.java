package team3647ConstantsAndFunctions;

public class Constants 
{
	//Motor Pins
	public final static int leftMaster = 57;
	public final static int rightMaster = 54;
	public final static int rightSlave1 = 58;
	public final static int rightSlave2 = 59;
	public final static int leftSlave1 = 52;
	public final static int leftSlave2 = 53;
	
	//Piston Pins
	public final static int forksPinSourceA = 4;
	public final static int forksPinSourceB = 5;
	public final static int intakePinSourceA = 2;
	public final static int intakePinSourceB = 3;
	public final static int shifterPinSourceA = 1;
	public final static int shifterPinSourceB = 0;
	
	//Elevator
	public final static int rightIntakePin = 56;
	public final static int leftIntakePin = 55;
	public final static int leftElevatorMaster = 52;
	public final static int rightElevatorMaster = 62;
	public final static int leftElevatorSlave = 54;
	public final static int rightElevatorSlave = 57;
	public final static int elevatorBannerSensor = 9;
	public final static int intakeBannerSensor = 8;
	
	public static final double stop = 0;
	public static final double pickUp = 3000;
	public static final double sWitch = 13000;
	public static final double lowerScale = 36300;
	public static final double scale = 41500;

	//Wrist
	public final static int flat = 0;
	public final static int sixtyDegrees = 0;
	public final static int up = 0;
	public final static int wristPin = 0;
	public final static int wristLimitSwitch = 5;
	
	//Auto Constants
	public static final double oneCubeSwitchRightSideStraight = 8500;
	public static final double oneCubeSwitchRightSideCurve = 5300;
	public static final double oneCubeSwitchRightSideCurveRatio = 2.5;
	
	public static final double twoCubeSwitchRightSideStraight = 11000;
	public static final double twoCubeSwitchRightSideCurve = 9000;
	public static final double twoCubeSwitchRightSideCurveRatio = 2.3;
	
	public static final double twoCubeSwitchLeftSideStraight = 16000;
	public static final double twoCubeSwitchLeftSideFirstCurve = 5300;
	public static final double twoCubeSwitchLeftSideFirstCurveRatio = 2.5;
	public static final double twoCubeSwitchLeftSideStraightCrossField = 12785;
	public static final double twoCubeSwitchLeftSideSecondCurve = 5300;
	public static final double twoCubeSwitchLeftSideSecondCurveRatio = 2.5;
	
	public static final double lrandrrFirstStraightDist = 21936.6;
	public static final double lrandrrFirstTurnToScaleDist = 5400;
	public static final double lrandrrFirstTurnToScaleRatio = 3.26;
	public static final double lrandrrBackUpTurnAfterScaleDist = 5400;
	public static final double lrandrrBackUpTurnAfterScaleRatio = 3.26;
	public static final double lrandrrBackUpTurnAfterScale = 3222;
	public static final double lrandrrBackUpToWallTurnDist = 5400;
	public static final double lrandrrBackUpToWallTurnRatio = 3.26;
	public static final double lrStraightAfterWall = 	2928;
}
