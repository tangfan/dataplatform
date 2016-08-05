package datamoniterserver.db;

import java.util.ArrayList;
import java.util.List;

import com.qq.bookutil.LogUtil;
import com.qq.dbclient.SimpleRowSet;
import com.qq.jutil.util.Pair;

import datamoniterserver.util.Util;


public class DataDBUtil 
{
	public static Pair<String,Integer> queryLastDau()
	{
		try 
		{
			String sql = "select time,uv from t_md_qqreader_dau_pvuv order by time desc limit 1";

			SimpleRowSet rs = DBUtil.data_db.executeQuery(sql);
			if(rs.next())
				return Pair.makePair(rs.getString("time"), rs.getInt("uv"));
		}
		catch (Exception e)
		{
			// TODO: handle exception
			LogUtil.error(e,"queryLastDau");
		}
		
		return null;
	}
	
	public static Pair<String,Integer> queryLastNew()
	{
		try 
		{
			String sql = "select time,pv from t_md_qqreader_new_read_pvuv order by time desc limit 1";

			SimpleRowSet rs = DBUtil.data_db.executeQuery(sql);
			if(rs.next())
				return Pair.makePair(rs.getString("time"), rs.getInt("pv"));
		}
		catch (Exception e)
		{
			// TODO: handle exception
			LogUtil.error(e,"queryLastNew");
		}
		
		return null;
	}
	
	public static Pair<String,Integer> queryLastQuan()
	{
		try 
		{
			String sql = "select time,uv from t_md_qqreader_quan_pvuv order by time desc limit 1";

			SimpleRowSet rs = DBUtil.data_db.executeQuery(sql);
			if(rs.next())
				return Pair.makePair(rs.getString("time"), rs.getInt("uv"));
		}
		catch (Exception e)
		{
			// TODO: handle exception
			LogUtil.error(e,"queryLastQuan");
		}
		
		return null;
	}
	

	public static List<Integer> queryLast3Dau()
	{
		List<Integer> ret = new ArrayList<Integer>();
		try 
		{
			String sql = "select time,uv from t_md_qqreader_dau_pvuv where platform = 'Android' order by time desc limit 3";

			SimpleRowSet rs = DBUtil.data_db.executeQuery(sql);
			while(rs.next()){
				ret.add(rs.getInt("uv"));
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			LogUtil.error(e,"queryLast3Dau");
		}
		
		return ret;
	}
	
	public static List<Integer> queryLast3New()
	{
		List<Integer> ret = new ArrayList<Integer>();
		try 
		{
			String sql = "select time,pv from t_md_qqreader_new_read_pvuv where platform = 'Android'  order by time desc limit 3";

			SimpleRowSet rs = DBUtil.data_db.executeQuery(sql);
			while(rs.next()){
				ret.add(rs.getInt("pv"));
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			LogUtil.error(e,"queryLast3New");
		}
		
		return ret;
	}
	
	public static List<Integer> queryLast3Quan()
	{
		List<Integer> ret = new ArrayList<Integer>();
		try 
		{
			String sql = "select time,uv from t_md_qqreader_quan_pvuv where platform = 'Android'  order by time desc limit 3";

			SimpleRowSet rs = DBUtil.data_db.executeQuery(sql);
			while(rs.next()){
				ret.add(rs.getInt("pv"));
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			LogUtil.error(e,"queryLast3Quan");
		}
		
		return ret;
	}
	

}
