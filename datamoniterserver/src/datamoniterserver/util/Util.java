package datamoniterserver.util;

import com.qq.jagent.AlerterUtil;

public class Util {
	  public static void mailLuffy(String title,String info)
		{
	    	AlerterUtil.mailAlarm(title, info, "p_qqzhang");
		}
}
