package lib;

public class InputSanitizer 
{
	public static int stringToInt(String str, int defaultInt)
	{
		int intFromStr = defaultInt;
		String clearedinput = "";
		for(int i = 0; i<str.length();i++)
		{
			switch (str.charAt(i)) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				clearedinput += str.charAt(i);
				break;

			default:
				break;
			}
		}
		if(!clearedinput.equals(""))
		{
			int tmp = Integer.valueOf(clearedinput);
			if(tmp>0)
			{
				intFromStr = tmp;
			}
		}
		return intFromStr;
	}
	public static float stringToFloat(String str, float defaultFloat)
	{
		float floatFromStr = defaultFloat;
		String clearedinput = "";
		for(int i = 0; i<str.length();i++)
		{
			switch (str.charAt(i)) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				clearedinput += str.charAt(i);
				break;
			case ',':
			case '.':
				clearedinput += ".";
				break;

			default:
				break;
			}
		}
		if(!clearedinput.equals(""))
		{
			float tmp = Float.valueOf(clearedinput);
			if(tmp>0)
			{
				floatFromStr = tmp;
			}
		}
		return floatFromStr;
	}
}
