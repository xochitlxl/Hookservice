package com.example.hookservice;

import java.util.HashMap;

import org.apache.http.cookie.SM;
import org.greenrobot.greendao.database.Database;

import android.app.Application;

import com.leonnewton.databaseopre.*;
import com.leonnewton.databaseopre.DaoMaster.DevOpenHelper;

public class App extends Application{
	
	private DaoSession daoSession;
	private static App instance;
	private DaoMaster daomaster;
	
	
	
//=============================SMS Mark =============================	
	public HashMap<String, Integer> sendTextMark;
	public HashMap<String, Integer> getServiceSmsMark;
	public HashMap<String, Integer> BinderProxyISms;
	public HashMap<String, Integer> phoneNative;
	public HashMap<String, Integer> SMNativeSMS;
	

//==============================Media Mark===========================
	public HashMap<String, Integer>  MediaStart;
	public HashMap<String, Integer>  IMediaRecorder;
	public HashMap<String, Integer>  getaudioflinger;
	public HashMap<String, Integer>  IAudioRecord;
	public HashMap<String, Integer>  getMinBufferSize;
	
	
	
	
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		
		DevOpenHelper helper = new DevOpenHelper(this,"msg.db");
		Database db = helper.getWritableDb();
		daomaster = new DaoMaster(db);
		daoSession = daomaster.newSession();	
		
		//daomaster.dropAllTables(daoSession.getDatabase(), true);
		//daomaster.createAllTables(daoSession.getDatabase(), true);
		
		//================sms========================
		 sendTextMark = new HashMap<String, Integer>();
		 getServiceSmsMark = new HashMap<String, Integer>();
		 BinderProxyISms = new HashMap<String, Integer>();
		 phoneNative = new HashMap<String, Integer>();
		 SMNativeSMS = new HashMap<String, Integer>();
		 

		//================media=======================
		 MediaStart = new HashMap<String, Integer>();
		 IMediaRecorder = new HashMap<String, Integer>();
		 getaudioflinger = new HashMap<String, Integer>();
		 getMinBufferSize = new HashMap<String, Integer>();
		 IAudioRecord = new HashMap<String, Integer>();
	}
	
	
	public DaoSession getDaoSession() {
		
		return daoSession;

	}
	
	
	
	
	
	public static App getInstance() {
		
		return instance;
	}
	
	
	

}
