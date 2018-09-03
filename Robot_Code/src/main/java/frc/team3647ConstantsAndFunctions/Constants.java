package frc.team3647ConstantsAndFunctions;

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
	public final static int lockPinSourceA = 6;
	public final static int lockPinSourceB = 7;
	
	//Elevator
	public final static int rightIntakePin = 56;
	public final static int leftIntakePin = 55;
	public final static int leftGearboxSRX = 52;
	public final static int rightGearboxSRX = 62;
	public final static int leftGearboxSPX = 54;
	public final static int rightGearboxSPX = 57;
	public final static int elevatorBannerSensor = 9;
	public final static int intakeBannerSensor = 8;
	
	public static final double bottom = 0;
	public static final double sWitch = 13000;
	public static final double lowerScale = 38000;
	public static final double scale = 45000;
	public static final double climb = 11000;
	
	// Elevator PID Values
		//carriage only
	public final static int carriagePID = 0;
	public final static double carriageF = 0;
	public final static double carriageP = 0.2;
	public final static double carriageI = 0;
	public final static double carriageD = 20;
		//interstage
	public final static int interstagePID = 1;
	public final static double interstageF = 0;
	public final static double interstageP = 0.4;
	public final static double interstageI = 0;
	public final static double interstageD = 50;
	//Wrist
	public final static int flat = 0;
	public final static int aim = 500;
	public final static int up = 700;
	public final static int wristPin = 0;
	public final static int wristLimitSwitch = 2;
	
	public final static int cubePID = 1;
	public final static double cubeF = 0;
	public final static double cubeP = 1;
	public final static double cubeI = 0;
	public final static double cubeD = 50;
	public final static int noCubePID = 0;
	public final static double noCubeF = .1;
	public final static double noCubeP = .45;
	public final static double noCubeI = 0;
	public final static double noCubeD = 50;
	
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
	
	//PID Constants
	public final static int kTimeoutMs = 10;
	public final static int drivePID = 0;
	public final static boolean kSensorPhase = true;
	public final static boolean kMotorInvert = false;
	public final static double lDrivekF = 0.577966;
	public final static double lDrivekP = 0;
	public final static double lDrivekI = 0;
	public final static double lDrivekD = 0;
	public final static double rDrivekF = 0.571189;
	public final static double rDrivekP = 0;
	public final static double rDrivekI = 0;
	public final static double rDrivekD = 0;
	public final static double velocityConstant = 1770;
	public final static double deadZone = 0.05;

}
