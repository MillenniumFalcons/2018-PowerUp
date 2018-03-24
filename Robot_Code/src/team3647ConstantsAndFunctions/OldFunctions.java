package team3647ConstantsAndFunctions;

public class OldFunctions 
{
	public static double sum, speed;
	
	public static double sinx(double x)
	{
		return Math.sin(x);
	}
	
	public static double switchInitialStraight(double lValue, double rValue)
	{
		//This function would change if length of the straight distance changes
		
		sum = lValue + rValue;
		sum *= 0.000143112244898;
		sum+=.574;
		speed = sinx(sum);
		return speed;
	}
	
	public static double scaleInitialStraight(double lValue, double rValue)
	{
		//This function would change if length of the straight distance changes
		
		sum = lValue + rValue;
		sum *= 0.0000500892857143;
		sum +=.574;
		speed = sinx(sum);
		return speed;
	}
	
	public static double switchFirstCurveBigSpeed(double lValue)
	{
		//This function would change if length of the straight distance changes
		
		lValue*=0.000143112244898;
		lValue+=.574;
		speed = sinx(lValue);
		return speed;
	}
	
	public static double scaleFirstCurveBigSpeed(double lValue)
	{
		//This function would change if length of the straight distance changes
		
		lValue*=0.000383612988505;
		lValue+=.574;
		speed = sinx(lValue);
		return speed;
	}
	
	public static double middleRightBigCurveSpeed(double lValue)
	{
		//This function would change if length of the straight distance changes
		
		lValue*=0.0001737405713;
		lValue+=1.969;
		speed = sinx(lValue);
		return speed;
	}
	
	public static double stopToPickUp(double eValue)
	{
//		eValue*=0.000163448275862;
//		eValue+=1.899;
//		speed = sinx(eValue);
//		speed/=2;
//		return speed;
//		eValue*=0.000438;
//		eValue+=5.236;
//		eValue/=2;
//		speed = sinx(eValue);
//		return speed;
		eValue*=(-0.00005);
		eValue+=.3;
		return eValue;
	}
	
	public static double stopToSwitch(double eValue)
	{
//		eValue*=0.0000449333333333;
//		eValue+=2.17;
//		speed = sinx(eValue);
//		return speed;
//		eValue*=0.000286;
//		eValue*=1.855;
//		eValue/=2;
//		speed = sinx(eValue);
//		return speed;
//		eValue*=0.000161222222222;
//		eValue+=4.429;
//		eValue/=2;
//		speed = sinx(eValue);
//		return speed;
		eValue*=(-0.000046153834);
		eValue+=.8;
		return eValue;
		//.8 to .2
		
	}
	
	public static double stopToScale(double eValue)
	{
//		eValue*=0.000156090909091;
//		eValue+=2.24;
//		eValue/=2;
//		speed = sinx(eValue);
//		return speed;
//		if(eValue < Constants.sWitch)
//		{
//			return 1;
//		}
//		else
//		{
//			return switchToScale(eValue);
//		}
//		if(eValue < Constants.sWitch)
//		{
//			return 1;
//		}
//		else
//		{
//			eValue*=(-0.0000333333333333);
//			eValue+=1.2;
//			return eValue;
//			//.9 to .25
//		}
//		return .4;
		
		if(eValue < 25000)
		{
			return 1;
		}
		else
		{
			eValue*=(-0.0000352941176471);
			eValue+=1.733;
			return eValue;
			//.85 to .25
		}
	}
	
	public static double pickUpToStop(double eValue)
	{
		return -.2;
	}
	
	public static double pickUpToSwitch(double eValue)
	{
//		eValue*=0.0001435;
//		eValue+=4.589;
//		eValue/=2;
//		speed = sinx(eValue);
//		return speed;
		eValue*=(-0.000046153834);
		eValue+=.8;
		return eValue;
		//.7 to .2
	}
	
	public static double pickUpToScale(double eValue)
	{
//		eValue*=0.00012059082602;
//		eValue+=3.02059265359;
//		eValue/=2;
//		speed = sinx(eValue);
//		return speed;
//		eValue*=(-0.0000224137931034);
//		eValue+=.87;
//		return eValue;
		if(eValue < 25000)
		{
			return 1;
		}
		else
		{
			eValue*=(-0.0000352941176471);
			eValue+=1.733;
			return eValue;
			//.85 to .25
		}
		//.85 to 2
	}
	
	public static double switchToStop(double eValue)
	{
		if(eValue > 5000)
		{
			return -.4;
		}
		else
		{
			return - .18;
		}
	}
	
	public static double switchToPickUp(double eValue)
	{
		if(eValue > 5000)
		{
			return -.35;
		}
		else
		{
			return - .16;
		}
	}
	
	public static double switchToScale(double eValue)//
	{
//		eValue*=(-0.0000285714285714);
//		eValue+=1.057;
//		return eValue;
		if(eValue < 20000)
		{
			return 1;
		}
		else
		{
			eValue*=(-0.0000352941176471);
			eValue+=1.733;
			return eValue;
			//.85 to .25
		}
		//.8 to .2
	}
	
	public static double stopToLowerScale(double eValue)
	{
		if(eValue<Constants.sWitch)
		{
			return 1;
		}
		else
		{
			eValue*=(-0.0000230769230769);
			eValue+=1.031;
			return eValue;
		}
	}
	
	public static double pickUpToLowerScale(double eValue)
	{
		if(eValue<Constants.sWitch)
		{
			return 1;
		}
		else
		{
			eValue*=(-0.0000230769230769);
			eValue+=1.031;
			return eValue;
		}
	}
	
	public static double switchToLowerScale(double eValue)
	{
		eValue*=(-0.0000230769230769);
		eValue+=1.031;
		return eValue;
	}
	
	public static double scaleToLowerScale(double eValue)
	{
		return -.2;
	}
	
	public static double lowerScaleToScale(double eValue)
	{
		if(eValue < 40000)
		{
			return .4;
		}
		else
		{
			return .2;
		}
	}
	
	public static double lowerScaleToSwitch(double eValue)
	{
		return -.28;
	}
	
	public static double lowerScaleToPickUp(double eValue)
	{
		if(eValue > Constants.sWitch)
		{
			return -.5;
		}
		else
		{
			return -.15;
		}
	}
	
	public static double lowerScaleToStop(double eValue)
	{
		if(eValue > Constants.sWitch)
		{
			return -.5;
		}
		else
		{
			return -.17;
		}
	}
	
	public static double scaleToStop(double eValue)//
	{
		if(eValue > Constants.sWitch)
		{
			return -.5;
		}
		else
		{
			return -.17;
		}
			
	}
	
	public static double scaleToPickUp(double eValue)//
	{
		if(eValue > Constants.sWitch)
		{
			return -.5;
		}
		else
		{
			return -.15;
		}
	}
	
	public static double scaleToSwitch(double eValue)
	{
//		eValue*=-0.000142629639014;
//		eValue+=7.56718530718;
//		eValue/=2;
//		speed = sinx(eValue);
//		return -speed;
		return -.32;
	}
	
	// new auto functions
	public static double middleRBigTurn(double eValue)//
	{
		eValue*=0.000232226080487;
		eValue+=4.429;
		eValue/=2;
		speed = sinx(eValue);
		return speed;
	}
	
	public static double middleRStraighttoSwitch(double eValue)//
	{
		eValue*=0.000799102322763;
		eValue +=1.0471975512;
		eValue/=2;
		speed = sinx(eValue);
		return speed;
	}
	
	public static double middleLBigTurn(double eValue)//
	{
		eValue*=0.000167455541031;
		eValue+=4.429;
		eValue/=2;
		speed = sinx(eValue);
		return speed;
	}
	
	public static double middleLStraighttoSwitch(double eValue)//
	{
		eValue*=0.000226;
		eValue +=4.996;
		eValue/=2;
		speed = sinx(eValue);
		return speed;
	}
	
	public static double straightForSwitch(double eValue)//
	{
		eValue*=0.000487444444444;
		eValue += 1.287;
		eValue/=2;
		speed = sinx(eValue);
		return speed;
	}
	
	public static double BigTurnSwitch(double eValue)//
	{
		eValue*=0.00050651060639;
		eValue += 1.855;
		eValue/=2;
		speed = sinx(eValue);
		speed*=1.42;
		return speed;
	}
	
	public static double straightForScale(double eValue)//
	{
		eValue*=0.000143541666667;
		eValue += 1.551;
		eValue/=2;
		speed = sinx(eValue);
		return speed;
	}
	
	public static double BigTurnScale(double eValue)
	{
		eValue*=0.000150299705657;
		eValue += 4.732;
		eValue/=2;
		speed = sinx(eValue);
		return speed;
	}
	
	
	//JANK
	public static double straightInitialRightSideJank(double eValue)
	{
		if(eValue < 10000)
		{
			return 1;
		}
		else
		{
			return .8;
		}
		/*
		if(eValue < 2000)
		{
			return .6;
		}
		else if(eValue < 3500)
		{
			return .7;
		}
		else if(eValue < 5000)
		{
			return .9;
		}
		else if(eValue < 16000)
		{
			return 1;
		}
		else
		{
			return .8;
		}
		 */
		//.8 to .6
	}
	
	public static double backUpAfterFirstCubeRightSideJank(double eValue)
	{
		eValue*=(-0.0000577795654239);
		eValue+=.9;
		return -eValue;
	}
	
//	public static double uTurnForFirstCubeRightSideBigJank(double eValue)
//	{
//		if(eValue<= AutoConstants.UTurntoFirstCubeJankRightSideRatio - 1800)
//		{
//			eValue*=(-0.0000388888888889);
//			eValue+=1;
//			return -eValue;
//		}
//		else if(eValue<= AutoConstants.UTurntoFirstCubeJankRightSideRatio)
//		{
//			return .45;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double backUpAfterPickUpFirstCubeRightSideBigJank(double eValue, boolean sign)
//	{
//		if(sign)
//		{
//			if(eValue<= AutoConstants.halfCircleTurnForCubesRightSideJank - 1400)
//			{
//				eValue*=(-0.0000625);
//				eValue+=.9;
//				return eValue;
//			}
//			else if(eValue<= AutoConstants.halfCircleTurnForCubesRightSideJank)
//			{
//				return .5;
//			}
//			else
//			{
//				return 0;
//			}
//		}
//		else
//		{
//			if(eValue<= AutoConstants.halfCircleTurnForCubesRightSideJank - 1400)
//			{
//				eValue*=(-0.0000625);
//				eValue+=.9;
//				return -eValue;
//			}
//			else if(eValue<= AutoConstants.halfCircleTurnForCubesRightSideJank)
//			{
//				return -.5;
//			}
//			else
//			{
//				return -0;
//			}
//		}
//	}
//	
//	public static double firstTurnSpeedForMSlSW(double eValue)
//	{
//		if(eValue < AutoConstants.firstBigTurnMSLSW - 3000)
//		{
//			return .9;
//		}
//		if(eValue < AutoConstants.firstBigTurnMSLSW - 2000)
//		{
//			return .65;
//		}
//		else if(eValue < AutoConstants.firstBigTurnMSLSW)
//		{
//			return .3;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double secondTurnSpeedForMSlSW(double eValue)
//	{
//		if(eValue < AutoConstants.secondBigTurnMSLSW - 2500)
//		{
//			return .8;
//		}
//		if(eValue < AutoConstants.secondBigTurnMSLSW - 2000)
//		{
//			return .7;
//		}
//		else if(eValue < AutoConstants.secondBigTurnMSLSW)
//		{
//			return .6;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double firstTurnSpeedForMSRSW(double eValue)
//	{
//		if(eValue < AutoConstants.firstBigTurnMSRSW - 2500)
//		{
//			return .9;
//		}
//		if(eValue < AutoConstants.firstBigTurnMSRSW - 1400)
//		{
//			return .65;
//		}
//		else if(eValue < AutoConstants.firstBigTurnMSRSW)
//		{
//			return .3;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double secondTurnSpeedForMSRSW(double eValue)
//	{
//		if(eValue < AutoConstants.secondBigOneWheelCurveMSRSW - 2000)
//		{
//			return .8;
//		}
//		else if(eValue < AutoConstants.secondBigOneWheelCurveMSRSW - 1440)
//		{
//			return .5;
//		}
//		else if(eValue < AutoConstants.secondBigOneWheelCurveMSRSW)
//		{
//			return .3;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double frontLeftTurn8Speed(double eValue)
//	{
//		if(eValue < AutoConstants.frontLeftTurn8Dist - 1500)
//		{
//			return .8;
//		}
//		else if(eValue < AutoConstants.frontLeftTurn8Dist)
//		{
//			return .6;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double frontRightTurn8Speed(double eValue)
//	{
//		if(eValue < AutoConstants.frontRightTurn8Dist - 1500)
//		{
//			return .8;
//		}
//		else if(eValue < AutoConstants.frontRightTurn8Dist)
//		{
//			return .6;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double frontRightTurn6Speed(double eValue)
//	{
//		if(eValue < AutoConstants.frontRightTurn6Dist - 1500)
//		{
//			return .8;
//		}
//		else if(eValue < AutoConstants.frontRightTurn6Dist)
//		{
//			return .6;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double frontLeftTurn6Speed(double eValue)
//	{
//		if(eValue < AutoConstants.frontLeftTurn6Dist - 1500)
//		{
//			return .8;
//		}
//		else if(eValue < AutoConstants.frontLeftTurn6Dist)
//		{
//			return .6;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double backUpRightTurnSpeed(double eValue)
//	{
//		if(eValue < AutoConstants.backRightTurnDist - 1500)
//		{
//			return -.8;
//		}
//		else if(eValue < AutoConstants.backRightTurnDist)
//		{
//			return -.6;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	//rr functions
//	
//	public static double rrStarightToScale(double eValue)
//	{
//		if(eValue < 1000)
//		{
//			return .6;
//		}
//		else if(eValue < 2000)
//		{
//			return .8;
//		}
//		else if(eValue < 6000)
//		{
//			return 1;
//		}
//		else  if(eValue < 7000)
//		{
//			return .8;
//		}
//		else
//		{
//			return .7;
//		}
//		/*
//		if(eValue < 2000)
//		{
//			return .6;
//		}
//		else if(eValue < 3500)
//		{
//			return .7;
//		}
//		else if(eValue < 5000)
//		{
//			return .9;
//		}
//		else if(eValue < 16000)
//		{
//			return 1;
//		}
//		else
//		{
//			return .8;
//		}
//		 */
//		//.8 to .6
//	}
//	
//	public static double rrPickUpCube(double eValue)
//	{
//		if(eValue < AutoConstants.rrCubeTurn - 2400)
//		{
//			return .5;
//		}
//		else if(eValue < AutoConstants.rrCubeTurn)
//		{
//			return .25;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//
//	//rr functions
//	
//		public static double lrStarightToScale(double eValue)
//		{
//			if(eValue < 1000)
//			{
//				return .6;
//			}
//			else if(eValue < 2000)
//			{
//				return .8;
//			}
//			else if(eValue < 7000)
//			{
//				return 1;
//			}
//			else
//			{
//				return .8;
//			}
//			/*
//			if(eValue < 2000)
//			{
//				return .6;
//			}
//			else if(eValue < 3500)
//			{
//				return .7;
//			}
//			else if(eValue < 5000)
//			{
//				return .9;
//			}
//			else if(eValue < 16000)
//			{
//				return 1;
//			}
//			else
//			{
//				return .8;
//			}
//			 */
//			//.8 to .6
//		}
//		
//		public static double lrPickUpCube(double eValue)
//		{
//			if(eValue < AutoConstants.rrCubeTurn - 1500)
//			{
//				return .8;
//			}
//			else if(eValue < AutoConstants.rrCubeTurn)
//			{
//				return .5;
//			}
//			else
//			{
//				return 0;
//			}
//		}
//		
//	public static double llStraightBeforeTurn(double eValue)
//	{
//		if(eValue < 1000)
//		{
//			return .6;
//		}
//		else if(eValue < 2500)
//		{
//			return .8;
//		}
//		else if(eValue < 6500)
//		{
//			return 1;
//		}
//		else if(eValue < 7500)
//		{
//			return .8;
//		}
//		else
//		{
//			return .7;
//		}
//	}
//	
//	public static double llFirstTurn(double eValue)
//	{
//		if(eValue < AutoConstants.llFirstTurn - 1500)
//		{
//			return .8;
//		}
//		else if(eValue < AutoConstants.llFirstTurn)
//		{
//			return .5;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double llcrossFieldDist(double eValue)
//	{
//		if(eValue < 1000)
//		{
//			return .6;
//		}
//		else if(eValue < 2500)
//		{
//			return .8;
//		}
//		else if(eValue < 6500)
//		{
//			return 1;
//		}
//		else if(eValue < 7500)
//		{
//			return .8;
//		}
//		else
//		{
//			return .7;
//		}
//	}
//	
//	public static double llFirstTurnToScale(double eValue)
//	{
//		if(eValue < AutoConstants.llFirstTurnToScale - 1500)
//		{
//			return .8;
//		}
//		else if(eValue < AutoConstants.llFirstTurnToScale)
//		{
//			return .5;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double llSecondTurnToScale(double eValue)
//	{
//		if(eValue < AutoConstants.llSecondTurnToScale - 1500)
//		{
//			return .8;
//		}
//		else if(eValue < AutoConstants.llSecondTurnToScale)
//		{
//			return .5;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double llFirstTurnAfterScale(double eValue)
//	{
//		if(eValue < AutoConstants.llFirstTurnAfterScale - 1500)
//		{
//			return .8;
//		}
//		else if(eValue < AutoConstants.llFirstTurnAfterScale)
//		{
//			return .5;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double llSecondTurnAfterScale(double eValue)
//	{
//		if(eValue < AutoConstants.llSecondTurnAfterScale - 1500)
//		{
//			return .8;
//		}
//		else if(eValue < AutoConstants.llSecondTurnAfterScale)
//		{
//			return .5;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double llPickUpCubeTurn(double eValue)
//	{
//		if(eValue < AutoConstants.llPickUpCubeTurn - 1500)
//		{
//			return .8;
//		}
//		else if(eValue < AutoConstants.llPickUpCubeTurn)
//		{
//			return .5;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double rlStraightBeforeTurn(double eValue)
//	{
//		if(eValue < 1000)
//		{
//			return .6;
//		}
//		else if(eValue < 2500)
//		{
//			return .8;
//		}
//		else if(eValue < 6500)
//		{
//			return 1;
//		}
//		else if(eValue < 7500)
//		{
//			return .8;
//		}
//		else
//		{
//			return .7;
//		}
//	}
//	
//	public static double rlFirstTurn(double eValue)
//	{
//		if(eValue < AutoConstants.rlFirstTurn - 1500)
//		{
//			return .8;
//		}
//		else if(eValue < AutoConstants.rlFirstTurn)
//		{
//			return .5;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double rlcrossFieldDist(double eValue)
//	{
//		if(eValue < 1000)
//		{
//			return .6;
//		}
//		else if(eValue < 2500)
//		{
//			return .8;
//		}
//		else if(eValue < 6500)
//		{
//			return 1;
//		}
//		else if(eValue < 7500)
//		{
//			return .8;
//		}
//		else
//		{
//			return .7;
//		}
//	}
//	
//	public static double rlFirstTurnToScale(double eValue)
//	{
//		if(eValue < AutoConstants.rlFirstTurnToScale - 1500)
//		{
//			return .8;
//		}
//		else if(eValue < AutoConstants.rlFirstTurnToScale)
//		{
//			return .5;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double rlSecondTurnToScale(double eValue)
//	{
//		if(eValue < AutoConstants.rlSecondTurnToScale - 1500)
//		{
//			return .8;
//		}
//		else if(eValue < AutoConstants.rlSecondTurnToScale)
//		{
//			return .5;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double rlFirstTurnAfterScale(double eValue)
//	{
//		if(eValue < AutoConstants.rlFirstTurnAfterScale - 1500)
//		{
//			return .8;
//		}
//		else if(eValue < AutoConstants.rlFirstTurnAfterScale)
//		{
//			return .5;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double rlSecondTurnAfterScale(double eValue)
//	{
//		if(eValue < AutoConstants.rlSecondTurnAfterScale - 1500)
//		{
//			return .8;
//		}
//		else if(eValue < AutoConstants.rlSecondTurnAfterScale)
//		{
//			return .5;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
//	public static double rlStraightBeforeSwitch(double eValue)
//	{
//		if(eValue < 1000)
//		{
//			return .6;
//		}
//		else if(eValue < 2500)
//		{
//			return .8;
//		}
//		else if(eValue < 6500)
//		{
//			return 1;
//		}
//		else if(eValue < 7500)
//		{
//			return .8;
//		}
//		else
//		{
//			return .7;
//		}
//	}
//	
//	
//	public static double rlPickUpCubeTurn(double eValue)
//	{
//		if(eValue < AutoConstants.rlPickUpCubeTurn - 1500)
//		{
//			return .8;
//		}
//		else if(eValue < AutoConstants.rlPickUpCubeTurn)
//		{
//			return .5;
//		}
//		else
//		{
//			return 0;
//		}
//	}
//	
}
