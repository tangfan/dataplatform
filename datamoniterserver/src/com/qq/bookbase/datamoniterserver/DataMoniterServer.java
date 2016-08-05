package com.qq.bookbase.datamoniterserver;

import com.qq.taf.server.Application;
import com.qq.taf.server.ServerConfig;

import datamoniterserver.task.BagDataMoniterTask;

public class DataMoniterServer extends Application {

	@Override
	protected void initialize() throws Throwable {
		super.addConfig(ServerConfig.serverName + ".conf");
		addServant(DataMoniterServantImpl.class, ServerConfig.application+"."+ServerConfig.serverName+".DataMoniterServant");
		BagDataMoniterTask.init();//
	}
	
	public static void main(String[] args) {
		new DataMoniterServer().start();
	}

}
