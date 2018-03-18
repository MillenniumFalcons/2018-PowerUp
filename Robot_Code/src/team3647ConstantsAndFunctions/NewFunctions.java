package team3647ConstantsAndFunctions;

public class NewFunctions 
{
	public static double msrswfStraighToSwitch = 68;
	public static double msrswtotalTurnLength = 56.5486677646;
	public static double msrswsmallTurnLength = 15.7079632679;
	public static double msrswfirstTurnRatio = 3.6;
	
	public static double mslswStraighToSwitch = 30;
	public static double mslswMediumTurn = 56.5486677646;
	public static double mslswBigTurn = 97.3893722613;
	public static double mslswSmallTurn = 15.7079632679;
	public static double mslswfirstTurnRatio = 1.72222222222;
	public static double mslswsecondTurnRatio = 3.6;
	
	public static double lslscfstraight = 210;
	public static double lslscfbigTurn = 111.526539202;
	public static double lslscfsmallTurn = 62.8318530718;
	public static double lslscffirstTurnRatio = 1.775;
	
	public static double[] adjustmentValues(double lValue, double rValue, boolean yes)
	{
		double []adjustmentValues = new double[2];
		if(Math.abs(rValue- lValue) < 30)
		{
			adjustmentValues[0] = 0;
			adjustmentValues[1] = 0;
		}
		else
		{
			if(rValue > lValue)
			{
				if(Math.abs(rValue- lValue) < 90)
				{
					adjustmentValues[0] = .06;
					adjustmentValues[1] = -.06;
				}
				else if(Math.abs(rValue- lValue) < 150)
				{
					adjustmentValues[0] = .14;
					adjustmentValues[1] = -.14;
				}
				else if(Math.abs(rValue- lValue) < 200)
				{
					adjustmentValues[0] = .24;
					adjustmentValues[1] = -.24;
				}
				else
				{
					adjustmentValues[0] = .3;
					adjustmentValues[1] = -.3;
				}
			}
			else
			{
				if(Math.abs(rValue- lValue) < 90)
				{
					adjustmentValues[0] = -.06;
					adjustmentValues[1] = .06;
				}
				else if(Math.abs(rValue- lValue) < 150)
				{
					adjustmentValues[0] = -.14;
					adjustmentValues[1] = .14;
				}
				else if(Math.abs(rValue- lValue) < 200)
				{
					adjustmentValues[0] = -.24;
					adjustmentValues[1] = .24;
				}
				else
				{
					adjustmentValues[0] = -.3;
					adjustmentValues[1] = .3;
				}
			}
		}
		return adjustmentValues;
	}
	
	public static double[] goBack(double lValue, double rValue, boolean yes)
	{
		double []adjustmentValues = new double[2];
		if(Math.abs(rValue- lValue) < 30)
		{
			adjustmentValues[0] = 0;
			adjustmentValues[1] = 0;
		}
		else
		{
			if(rValue > lValue)
			{
				if(Math.abs(rValue- lValue) < 90)
				{
					adjustmentValues[0] = .06;
					adjustmentValues[1] = -.06;
				}
				else if(Math.abs(rValue- lValue) < 150)
				{
					adjustmentValues[0] = .14;
					adjustmentValues[1] = -.14;
				}
				else if(Math.abs(rValue- lValue) < 200)
				{
					adjustmentValues[0] = .24;
					adjustmentValues[1] = -.24;
				}
				else
				{
					adjustmentValues[0] = .3;
					adjustmentValues[1] = -.3;
				}
			}
			else
			{
				if(Math.abs(rValue- lValue) < 90)
				{
					adjustmentValues[0] = -.06;
					adjustmentValues[1] = .06;
				}
				else if(Math.abs(rValue- lValue) < 150)
				{
					adjustmentValues[0] = -.14;
					adjustmentValues[1] = .14;
				}
				else if(Math.abs(rValue- lValue) < 200)
				{
					adjustmentValues[0] = -.24;
					adjustmentValues[1] = .24;
				}
				else
				{
					adjustmentValues[0] = -.3;
					adjustmentValues[1] = .3;
				}
			}
		}
		return adjustmentValues;
	}
	
	public static double[] goStraight(double lValue, double rValue)
	{
		double []adjustmentValues = new double[2];
		if(Math.abs(rValue- lValue) < .2)
		{
			adjustmentValues[0] = 0;
			adjustmentValues[1] = 0;
		}
		else
		{
			if(rValue > lValue)
			{
				if(Math.abs(rValue- lValue) < .6)
				{
					adjustmentValues[0] = .06;
					adjustmentValues[1] = -.06;
				}
				else if(Math.abs(rValue- lValue) < 1)
				{
					adjustmentValues[0] = .14;
					adjustmentValues[1] = -.14;
				}
				else if(Math.abs(rValue- lValue) < 1.5)
				{
					adjustmentValues[0] = .24;
					adjustmentValues[1] = -.24;
				}
				else
				{
					adjustmentValues[0] = .3;
					adjustmentValues[1] = -.3;
				}
			}
			else
			{
				if(Math.abs(rValue- lValue) < .6)
				{
					adjustmentValues[0] = -.06;
					adjustmentValues[1] = .06;
				}
				else if(Math.abs(rValue- lValue) < 1)
				{
					adjustmentValues[0] = -.14;
					adjustmentValues[1] = .14;
				}
				else if(Math.abs(rValue- lValue) < 1.5)
				{
					adjustmentValues[0] = -.24;
					adjustmentValues[1] = .24;
				}
				else
				{
					adjustmentValues[0] = -.3;
					adjustmentValues[1] = .3;
				}
			}
		}
		return adjustmentValues;
	}
	
	public static double msrswfLeftSpeed(double lValue, double rValue, int step)
	{
		if(step == 1)
		{
			if(lValue < msrswtotalTurnLength)
			{
				lValue*=(-0.012378717796);
				lValue+=.9;
				return lValue;
			}
			else
			{
				return 0;
			}
		}
		else if(step == 2)
		{
			if(lValue < msrswfStraighToSwitch)
			{
				lValue*=(-0.00808823529412);
				lValue+=.9;
				return lValue;
			}
			else
			{
				return 0;
			}
		}
		else
		{
			return 0;
		}
	}
	
	public static double msrswfRightSpeed(double lValue, double rValue, int step)
	{
		if(step == 1)
		{
			if(rValue < msrswsmallTurnLength && lValue < msrswtotalTurnLength)
			{
				rValue =msrswfLeftSpeed(lValue, rValue, step)/msrswfirstTurnRatio;
				return rValue;
			}
			else if(rValue < msrswsmallTurnLength)
			{
				return .2;
			}
			else if(rValue < msrswtotalTurnLength)
			{
				rValue*=(-0.012378717796);
				rValue+=.9;
				return rValue;
			}
			else
			{
				return 0;
			}
		}
		else if(step == 2)
		{
			if(rValue < msrswfStraighToSwitch)
			{
				rValue*=(-0.00808823529412);
				rValue+=.9;
				rValue*=.85;
				return rValue;
			}
			else
			{
				return 0;
			}
		}
		else
		{
			return 0;
		}
	}
	
	public static double[] msrswfadjustment(double lValue, double rValue, int step)
	{
		double []adjustmentValues = new double[2];
		double ratio;
		if(step == 1)
		{
			if(rValue < msrswsmallTurnLength && lValue < msrswtotalTurnLength)
			{
				ratio = lValue/rValue;
				if(ratio <= (msrswfirstTurnRatio + .1) && ratio >= (msrswfirstTurnRatio - .1))
				{
					adjustmentValues[0] = 0;
					adjustmentValues[1] = 0;
				}
				else
				{
					if(ratio >= (msrswfirstTurnRatio + .1))
					{
						adjustmentValues[0] = -.2;
						adjustmentValues[1] = .1;
					}
					else//<3.5
					{
						adjustmentValues[0] = .2;
						adjustmentValues[1] = -.1;
					}
				}
			}
			else
			{
				adjustmentValues[0] = 0;
				adjustmentValues[1] = 0;
			}
		}
		else if(step == 2)
		{
			if(Math.abs(rValue - lValue) < .2)
			{
				adjustmentValues[0] = 0;
				adjustmentValues[1] = 0;
			}
			else if(lValue > rValue)
			{
				if(Math.abs(rValue - lValue) < .6)
				{
					adjustmentValues[0] = -.1;
					adjustmentValues[1] = .1;
				}
				else
				{
					adjustmentValues[0] = -.2;
					adjustmentValues[1] = .2;
				}
			}
			else if(lValue < rValue)
			{
				if(Math.abs(rValue - lValue) < .6)
				{
					adjustmentValues[0] = .1;
					adjustmentValues[1] = -.1;
				}
				else
				{
					adjustmentValues[0] = .2;
					adjustmentValues[1] = -.2;
				}
			}
			else
			{
				adjustmentValues[0] = 0;
				adjustmentValues[1] = 0;
			}
		}
		else
		{
			adjustmentValues[0] = 0;
			adjustmentValues[1] = 0;
		}
		return adjustmentValues;
	}
	
	public static double mslsswfLeftSpeed(double lValue, double rValue, int step)
	{
		if(step == 2)
		{
			if(lValue < mslswMediumTurn && rValue < mslswBigTurn)
			{
				lValue =mslsswfRightSpeed(lValue, rValue, step)/mslswfirstTurnRatio;
				return lValue;
			}
			else if(lValue < mslswMediumTurn)
			{
				return .2;
			}
			else
			{
				return 0;
			}
		}
		else if(step == 3)
		{
			if(lValue < mslswMediumTurn)
			{
				lValue*=(-0.00530516476973);
				lValue+=.7;
				return lValue;
			}
			else
			{
				return 0;
			}
		}
		else if(step == 4)
		{
			if(lValue<mslswStraighToSwitch)
			{
				lValue*=(-0.0166666666667);
				lValue+=.8;
				return lValue;
			}
			else 
			{
				return 0;
			}
		}
		else
		{
			return 0;
		}
	}
	
	public static double mslsswfRightSpeed(double lValue, double rValue, int step)
	{
		if(step == 2)
		{
			if(rValue < mslswBigTurn)
			{
				rValue*=(-0.00410722433785);
				rValue+=.8;
				return rValue;
				//.8 to .4
			}
			else
			{
				return 0;
			}
		}
		else if(step == 3)
		{
			if(rValue< mslswSmallTurn && lValue < mslswMediumTurn)
			{
				rValue = mslsswfLeftSpeed(lValue, rValue, step)/mslswsecondTurnRatio;
				rValue*=.85;
				return rValue;
			}
			else if(rValue< mslswSmallTurn)
			{
				return .2;
			}
			else
			{
				return 0;
			}
		}
		else if(step == 4)
		{
			if(rValue<mslswStraighToSwitch)
			{
				rValue*=(-0.0166666666667);
				rValue+=.8;
				rValue*=.9;
				return rValue;
			}
			else 
			{
				return 0;
			}
		}
		else
		{
			return 0;
		}
	}
	
	public static double[] mslsswfadjustment(double lValue, double rValue, int step)
	{
		double []adjustmentValues = new double[2];
		double ratio;
		
		if(step == 2) 
		{
			if(lValue < mslswMediumTurn && rValue < mslswBigTurn)
			{
				ratio = rValue/lValue;
				if(ratio <= (mslswfirstTurnRatio + .1) && ratio >= (mslswfirstTurnRatio - .1))
				{
					adjustmentValues[0] = 0;
					adjustmentValues[1] = 0;
				}
				else
				{
					if(ratio >= (mslswfirstTurnRatio + .1))
					{
						adjustmentValues[0] = .1;
						adjustmentValues[1] = -.2;
					}
					else
					{
						adjustmentValues[0] = -.1;
						adjustmentValues[1] = .2;
					}
				}
			}
			else
			{
				adjustmentValues[0] = 0;
				adjustmentValues[1] = 0;
			}
		}
		else if(step == 3)
		{
			if(rValue < mslswSmallTurn && lValue < mslswMediumTurn)
			{
				ratio = lValue/rValue;
				if(ratio <= (mslswsecondTurnRatio + .1) && ratio >= (mslswsecondTurnRatio - .1))
				{
					adjustmentValues[0] = 0;
					adjustmentValues[1] = 0;
				}
				else
				{
					if(ratio >= (mslswsecondTurnRatio + .1))
					{
						adjustmentValues[0] = -.2;
						adjustmentValues[1] = .1;
					}
					else//<3.5
					{
						adjustmentValues[0] = .2;
						adjustmentValues[1] = -.1;
					}
				}
			}
			else
			{
				adjustmentValues[0] = 0;
				adjustmentValues[1] = 0;
			}
		}
		else if(step == 4)
		{
			if(Math.abs(rValue - lValue) < .2)
			{
				adjustmentValues[0] = 0;
				adjustmentValues[1] = 0;
			}
			else if(lValue > rValue)
			{
				if(Math.abs(rValue - lValue) < .6)
				{
					adjustmentValues[0] = -.1;
					adjustmentValues[1] = .1;
				}
				else
				{
					adjustmentValues[0] = -.2;
					adjustmentValues[1] = .2;
				}
			}
			else if(lValue < rValue)
			{
				if(Math.abs(rValue - lValue) < .6)
				{
					adjustmentValues[0] = .1;
					adjustmentValues[1] = -.1;
				}
				else
				{
					adjustmentValues[0] = .2;
					adjustmentValues[1] = -.2;
				}
			}
			else
			{
				adjustmentValues[0] = 0;
				adjustmentValues[1] = 0;
			}
		}
		else
		{
			adjustmentValues[0] = 0;
			adjustmentValues[1] = 0;
		}
		
		return adjustmentValues;
	}
	
	public static double lslscfStraightSpeed(double lValue, double rValue)
	{
		double avg = (lValue + rValue)/2.0;
		avg*=(-0.00214285714286);
		avg+=.9;
		return avg;
		//.9 to .45
	}
	
	public static double lslscfBigTurnSpeed(double lValue)
	{
		lValue*=(-0.00358659026687);
		lValue+=.7;
		return lValue;
		//.7 to .3
	}
	
	
	

}
