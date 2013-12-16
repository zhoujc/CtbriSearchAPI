package com.ctbri.srcapi.tool;

import javax.servlet.http.HttpServletRequest;

public class Tool {


	  
	  public static String getInitialValue(HttpServletRequest req, String strKey, String strDefaultValue) 
	  {
		    String strValue = req.getParameter(strKey);
		    if ((strValue == null) || (strValue.length() == 0)) {
		      strValue = strDefaultValue;
		    }
		    return strValue;
	  }
	  
	//×Ö·û´®×ª»»Îªint
	public static int getIntFormString(String xstr, int defaultNum) {
	    int t = defaultNum;
	    try {
	        t = Integer.parseInt(xstr);
	    } catch (Exception e) {
	    }
	    return t;
	}
	
	public static double getDoubleFormString(String str, double defaultvalue)
	{
		double t = defaultvalue;
		try {
			t = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block

		}
		return t;
	}
}
