package team3647ConstantsAndFunctions;

public class Functions 
{
	public static double stopToPickUp(double eValue)
	{
		eValue*=(-0.00005);
		eValue+=.3;
		return eValue;
	}
	
	public static double stopToSwitch(double eValue)
	{
		eValue*=(-0.000046153834);
		eValue+=.8;
		return eValue;
		//.8 to .2	
	}
	
	public static double stopToScale(double eValue)
	{
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
		eValue*=(-0.000046153834);
		eValue+=.8;
		return eValue;
		//.7 to .2
	}
	
	public static double pickUpToScale(double eValue)
	{
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
		return -.32;
	}
	
	public static double test3Turn(double eValue, double dist)
	{
		if(eValue < (dist - 2000))
		{
			return .7;
		}
		else if(eValue < dist)
		{
			return .5;
		}
		else
		{
			return 0;
		}
	}
	
	public static double test4Turn(double eValue, double dist)
	{
		eValue = Math.abs(eValue);
		if(eValue < (dist - 2000))
		{
			return -.7;
		}
		else if(eValue < dist)
		{
			return -.5;
		}
		else
		{
			return 0;
		}
	}
	
	public static double test5Turn(double eValue, double dist)
	{
		eValue = Math.abs(eValue);
		if(eValue < (dist - 2000))
		{
			return -.7;
		}
		else if(eValue < dist)
		{
			return -.5;
		}
		else
		{
			return 0;
		}
	}

}
