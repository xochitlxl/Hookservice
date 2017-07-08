package com.example.hookservice;





import android.R.integer;
import android.util.Log;

import com.leonnewton.databaseopre.DaoSession;
import com.leonnewton.databaseopre.Msg;
import com.leonnewton.databaseopre.MsgDao;
import com.example.hookservice.CheckSms;



public class SavetoDatabase {
	
	
	public static final String SENDTEXTMESSAGKEY = "sendTextMessage";
	public static final String GETSERVICEISMSKEY = "getServiceisms";
	public static final String BINDERPROXYISMSKEY = "BinderProxyISms";
	public static final String PHONENATIVE = "PhoneISms";
	public static final String SMNATIVE = "SMisms";
	
	private static final String TAG = "SocketService";  
	private App app;
	DaoSession daoSession;
	MsgDao msgDao;
	CheckSms checkSms;
	CheckMedia checkMedia;
	
	
	
	String FunctionName = null;
	int    Pid = 0;
	long   time_long = 0;
	
	
	public SavetoDatabase() {
		// TODO Auto-generated constructor stub
		app = App.getInstance();
		daoSession = App.getInstance().getDaoSession();
		msgDao = daoSession.getMsgDao();
		checkSms = new CheckSms();	
		checkMedia = new CheckMedia();
	}
	
	
//--------处理收到的socket消息	
	public void processmsg(String msg, String source) {
	 String[] parts ;
	 String packageName = null;
     String functionName = null;     
	 String pid = null; 
	 String uid = null;
	 String time = null;	
			 
			 
		
	
		
	//处理传送过来的日志信息	
	 if(msg.indexOf("-") != -1) {	
		parts = msg.split("-");
		packageName = parts[0];
		functionName = parts[1]; 
		pid = parts[2]; 
		uid = parts[3];
		time =  parts[4];
		//Log.v(TAG,"processmsg get: " + msg);		
	 }else {			
	    Log.v(TAG,"processmsg error get: " + msg);	 
	 }
	 
	 if(Integer.parseInt(pid) != 0 || Integer.parseInt(uid) != 0) {
	   Msg databaseMsg = new Msg();
	   databaseMsg.setPackageName(packageName);
	   databaseMsg.setFunctionName(functionName);
	   databaseMsg.setPid(pid);
	   databaseMsg.setUid(uid);
	   databaseMsg.setTime(time);
	   msgDao.insert(databaseMsg);
	   
	   if(source.equalsIgnoreCase("SMS"))
		   checkSms.checkSmsStatus(functionName, packageName);
	   
	   if(source.equalsIgnoreCase("Media"))
		   checkMedia.checkMediaStatus(functionName, packageName);
	   
	   
	   
	 }   
	   
	   
}


	

}
