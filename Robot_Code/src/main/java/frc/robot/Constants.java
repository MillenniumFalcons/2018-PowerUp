package frc.robot;

public class Constants 
{
	//Drive Motor Pins
	public final static int leftMaster = 57;
	public final static int rightMaster = 54;
	public final static int rightSlave1 = 58;
	public final static int rightSlave2 = 59;
	public final static int leftSlave1 = 52;
	public final static int leftSlave2 = 53;

	//Drive PID Constants
	public final static int kTimeoutMs = 10;  //Universal Constant
	public final static int drivePID = 0;
	public final static double lDrivekF = 0.65;
	public final static double lDrivekP = 1;//1.75
	public final static double lDrivekI = 0;
	public final static double lDrivekD = 0;
	public final static double rDrivekF = 0.64;
	public final static double rDrivekP = 1;//1.75
	public final static double rDrivekI = 0;
	public final static double rDrivekD = 0;
	public final static double velocityConstant = 1550;

	//PracBot
	public final static double lDrivekF1 = 0.66;
	public final static double lDrivekP1 = 1;
	public final static double lDrivekI1 = 0;
	public final static double lDrivekD1 = 0;
	public final static double rDrivekF1 = 0.67;
	public final static double rDrivekP1 = 1;
	public final static double rDrivekI1 = 0;
	public final static double rDrivekD1 = 0;
	public final static double velocityConstant1 = 1550;

	public final static double driveElevatorSpeedModifier = 0.6;
	//Piston Pins
	public final static int forksPinSourceA = 4;
	public final static int forksPinSourceB = 5;
	public final static int intakePinSourceA = 2;
	public final static int intakePinSourceB = 3;
	public final static int shifterPinSourceA = 1;
	public final static int shifterPinSourceB = 0;
	public final static int lockPinSourceA = 6;
	public final static int lockPinSourceB = 7;
	
	//Intake Pins
	public final static int rightIntakePin = 56;
	public final static int leftIntakePin = 55;
	public final static int intakeBannerSensor = 8;
	public final static double poopyShoot = 0.38;
	
	//Elevator Pins
	public final static int leftGearboxSRX = 52;
	public final static int rightGearboxSRX = 62;
	public final static int leftGearboxSPX = 54;
	public final static int rightGearboxSPX = 57;
	public final static int elevatorBannerSensor = 9;
		//Levels
	public static final int bottom = 0;
	public static final int sWitch = 16000;
	public static final int lowerScale = 38000;
	public static final int scale = 45000;
	public static final int climb = 11000;
	public static final int elevatorSafetyLimit = 45000;

	// Elevator PID Values
	//Motion Magic Values
	public final static int elevatorCruiseVelocity = 8000;
	public final static int elevatorAcceleration = 20000;
	//carriage only
	public final static int carriagePID = 0;
	public final static double carriageF = 0.1705;
	public final static double carriageP = 0.2;
	public final static double carriageI = 0;
	public final static double carriageD = 20;
	public final static int carriageIZone = 0;
	//interstage
	public final static int interstagePID = 1;
	public final static double interstageF = 0.1705;
	public final static double interstageP = 0.4;
	public final static double interstageI = 0;
	public final static double interstageD = 50;
	public final static int interstageIZone = 0;
	
	//Wrist Pins
	public final static int wristPin = 0;
	public final static int wristLimitSwitch = 2;
		//Levels
	public final static int flat = 0;
	public final static int aim = 400;
	public final static int up = 650;
	public final static int kWristSafetyLimit = 700;

	//Wrist PID Values
		//Motion Magic Values
	public final static int wristCruiseVelocity = 250;
	public final static int wristAcceleration = 1000;
		//cube PID
	public final static int cubePID = 1;
	public final static double cubeF = 3;//5;
	public final static double cubeP = 2.8;
	public final static double cubeI = 0;
	public final static double cubeD = 200;
	public final static int cubeIZone = 0;
		//nocube PID
	public final static int noCubePID = 0;
	public final static double noCubeF = 1;
	public final static double noCubeP = 3;
	public final static double noCubeI = 0;
	public final static double noCubeD = 300;
	public final static int noCubeIZone = 0;

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

		//Pathfinder Constants
	public final static double wheelDiameter = 0.378;//.416666;
		//Used for waypoint generation method
	public final static double wheelBase = 25.2;
	public final static double maxVelocity = 15.5; // also used for csv file method
	public final static double maxAcceleration = 120;
	public final static double maxJerk = 2400;
	public final static double MPTimeStep = 0.02;
		//PID Values
	public final static double PFTurnkP = 0.8;
	public final static double PFkP = 0.005;//3048; //0.19; //P Gain
	public final static double PFkI = 0; //I Gain (not used)
	public final static double PFkD = 0; //D Gain
	public final static double PFkV = 1 / maxVelocity ; //Feed Forward (1/max velocity)
	public final static double PFkA = 0; //Acceleration Gain

		//turn code constants
	public final static double turnkP = 0;
	public final static double turnkI = 0;
	public final static double turnkD = 0;
	public final static double turnkF = 0;


	//Auto Constants
	public final static double shootCubeTime = 0.5;
	public final static double elevatorTimeout = 1;
	public final static double autoIntakeSpeed = 1;
	public final static double autoShootSpeed = -1;
	public final static double scaleAutoElevatorUpDistance = 19800; //18ft * 12in * 1440 / (5in * 3.14)

}
