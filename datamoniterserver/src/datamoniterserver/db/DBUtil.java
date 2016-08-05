package datamoniterserver.db;

import com.qq.dbclient.DBClientWrapper;
import com.qq.dbclient.DBFactory;

public class DBUtil 
{
	public static DBClientWrapper data_db = DBFactory.getDBClientWrapper("data_db", 0, true);

}
