package datamoniterserver.task;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.qq.bookutil.DateTimeUtil;
import com.qq.bookutil.LogUtil;
import com.qq.jutil.util.Pair;

import datamoniterserver.db.DataDBUtil;
import datamoniterserver.util.Util;

public class BagDataMoniterTask extends TimerTask{

	private static Timer timer = new Timer();
	
	static
	{
		timer.schedule(new BagDataMoniterTask() , 0, 30*1000);
	}
	
	private static Pair<String,Integer> lastDau = null;
	private static Pair<String,Integer> lastNew = null;
	private static Pair<String,Integer> lastQuan = null;
	
	public static void init(){
		LogUtil.info("BagDataMoniterTask init!!!");
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String time1 = DateTimeUtil.getDateTime5();
		
		String timehour = DateTimeUtil.getDateTime4();
		
		int min = DateTimeUtil.getMinute();
		int minh = min/10;
		int minl = min%10;
		String timemin = "00";
		if(minl < 5){
			timemin = minh + "0";
		}else{
			timemin = minh + "5";
		}
		
		boolean flag = (minl >=7 && minl<=9)||(minl >=2 && minl<= 4);
		
		String time = timehour+timemin;
		
		Pair<String,Integer> dau = DataDBUtil.queryLastDau();
		if(dau != null){
			if(lastDau != null){
				if(lastDau.first.equals(dau.first) && lastDau.second.intValue() == dau.second.intValue()){
					LogUtil.info("DauError|Stop|"+time);
					Util.mailLuffy("DauStop", "DauError|Stop|"+time);
				}else if(dau.first.compareTo(time) < 0 && flag){
					LogUtil.info("DauError|Delay|"+time);
					Util.mailLuffy("DauDelay", "DauError|Delay|"+time);
				}else if(!checkData(DataDBUtil.queryLast3Dau())){
					LogUtil.info("DauChange");
					Util.mailLuffy("DauChange", "DauChange");
				}else{
					LogUtil.debug("DauOk|"+time+"|"+dau);
				}
			}
			
			lastDau = dau;
		}
		

		Pair<String,Integer> news = DataDBUtil.queryLastNew();
		if(news != null){
			if(lastNew != null){
				if(lastNew.first.equals(news.first) && lastNew.second.intValue() == news.second.intValue()){
					LogUtil.info("NewError|Stop|"+time);
					Util.mailLuffy("NewStop", "NewError|Stop|"+time);
				}else if(news.first.compareTo(time) < 0 && flag){
					LogUtil.info("NewError|Delay|"+time);
					Util.mailLuffy("NewDelay", "NewError|Delay|"+time);
				}else if(!checkData(DataDBUtil.queryLast3New())){
					LogUtil.info("NewChange");
					Util.mailLuffy("NewChange", "NewChange");
				}else{
					LogUtil.debug("NewOk|"+time+"|"+news);
				}
			}
			
			lastNew = news;
		}


		Pair<String,Integer> quan = DataDBUtil.queryLastQuan();
		if(quan != null){
			if(lastQuan != null){
				if(lastQuan.first.equals(quan.first) && lastQuan.second.intValue() == quan.second.intValue()){
					LogUtil.info("QuanError|Stop|"+time);
					Util.mailLuffy("QuanStop", "QuanError|Stop|"+time);
				}else if(quan.first.compareTo(time) < 0 && flag){
					LogUtil.info("QuanError|Delay|"+time);
					Util.mailLuffy("QuanDelay", "QuanError|Delay|"+time);
				}else if(!checkData(DataDBUtil.queryLast3Quan())){
					LogUtil.info("QuanChange");
					Util.mailLuffy("QuanChange", "QuanChange");
				}else{
					LogUtil.debug("QuanOk|"+time+"|"+quan);
				}
			}
			
			lastQuan = quan;
		}
		
	}
	
	private static boolean checkData(List<Integer> list){
		int first =0;
		int second = 0;
		for(int i=1;i<=list.size();i++){
			if(i==2)
				first = list.get(i-1);
			if(i==3)
				second = list.get(i-1);
		}
		
		if(first < (second/2))
			return false;
		return true;
	}
	
}
