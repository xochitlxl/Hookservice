package com.example.hookservice;

import android.util.Log;

public class CheckSms {
	
	public static final String SENDTEXTMESSAGKEY = "sendTextMessage";
	public static final String GETSERVICEISMSKEY = "getServiceisms";
	public static final String BINDERPROXYISMSKEY = "BinderProxyISms";
	public static final String PHONENATIVE = "PhoneISms";
	public static final String SMNATIVE = "SMisms";
	
	private static final String TAG = "SocketService";  
	private App app;
	
    public static CheckSms instanceCheckSms;
    
    public CheckSms() {
    	
    	instanceCheckSms = this;
    	app = App.getInstance();
    }
    
    
    public  static CheckSms getinstance() {
    	
    	return instanceCheckSms;
    }
	
	
	
	//--------------get sms status and send to check-----------------
	  public void checkSmsStatus(String functionName, String packageName) {
		  
		  //Log.v(TAG,"checkSmsStatus: " + functionName + packageName);	 
		  if(functionName !=null && packageName != null) {
			  
			  if(functionName.equalsIgnoreCase(PHONENATIVE)) {
				  if(app.phoneNative.get(packageName) == null) {
					  app.phoneNative.put(packageName, 1);
				  }else {
					  int temp = app.phoneNative.get(packageName);
					  app.phoneNative.put(packageName, temp+1);
				  }
				  
				  
				  final String packageNamefinal = packageName;
			        Thread t = new Thread() {  
		                @Override 
		                public void run() {  
		                	
		                    try {
		                        Thread.sleep(1000);
		                        updateStateSms(packageNamefinal); 

		                    } catch (InterruptedException e) { }
		               
		               }
		            };  
		            t.start();  
				  
			  
			  }else if(functionName.equalsIgnoreCase(SMNATIVE)) {
				  
				  
				  if(app.SMNativeSMS.get(packageName) == null) {
					  app.SMNativeSMS.put(packageName, 1);
				  }else {
					  int temp = app.SMNativeSMS.get(packageName);
					  app.SMNativeSMS.put(packageName, temp+1);
				  }
				  
				  			  
				  
			  }else if(functionName.equalsIgnoreCase(BINDERPROXYISMSKEY) ) {
				  
			   if(app.BinderProxyISms.get(packageName) == null)
				       app.BinderProxyISms.put(packageName, 1);
			   else {
				  int temp = app.BinderProxyISms.get(packageName);
			      app.BinderProxyISms.put(packageName, temp+1);
				  }		   

			
			   
		   }else if(functionName.equalsIgnoreCase(SENDTEXTMESSAGKEY) ){
			   
			   if(app.sendTextMark.get(packageName) == null)
			       app.sendTextMark.put(packageName, 1);
		   else {
			  int temp = app.sendTextMark.get(packageName);
		      app.sendTextMark.put(packageName, temp+1);
			  }		   
		
			   
	  
		   }else if(functionName.equalsIgnoreCase(GETSERVICEISMSKEY)) {
			   
			   if(app.getServiceSmsMark.get(packageName) == null)
			       app.getServiceSmsMark.put(packageName, 1);
		   else {
			  int temp = app.getServiceSmsMark.get(packageName);	
		      app.getServiceSmsMark.put(packageName, temp+1);
			  }		   
		
		   }

		  
		 }  
		  
		  
	  }
		
		
	//----------check sms status---------- 	
		
	  private void updateStateSms(String packageName) {
	 	 
	 	 Log.v(TAG,"updateStateSms: "  + packageName);	 
	 	 int phoneNativecheck = app.phoneNative.get(packageName);
	 	 int BinderProxyISmscheck = app.BinderProxyISms.get(packageName);
	 	 int sendTextMarkcheck = app.sendTextMark.get(packageName);
	 	// int SMNativeSMScheck = app.SMNativeSMS.get(packageName);
	 	 int getServiceSmsMarkcheck = app.getServiceSmsMark.get(packageName);
	 	 
	 	 int SMNativeSMScheck =1;
	 	 
	 	 if(phoneNativecheck == BinderProxyISmscheck &&
	 	    BinderProxyISmscheck == sendTextMarkcheck &&
	 	    sendTextMarkcheck == SMNativeSMScheck &&
	 	    SMNativeSMScheck == getServiceSmsMarkcheck) {
	 		 
	 		  Log.v(TAG,"ALL Mark OK: " + packageName + phoneNativecheck + SMNativeSMScheck +
	 				  BinderProxyISmscheck + sendTextMarkcheck
	 				  + getServiceSmsMarkcheck);	
	 		  app.getServiceSmsMark.put(packageName, 0);
	 		  app.BinderProxyISms.put(packageName, 0);
	 		  app.sendTextMark.put(packageName, 0);
	 		  app.phoneNative.put(packageName, 0);
	 		  app.SMNativeSMS.put(packageName, 0);
	 		  
	 		  
	 	 }
	 	 else  {
	 		
	 		  Log.v(TAG,"ALL Mark not OK: " + packageName +
	 				  app.BinderProxyISms.get(packageName) + 
	 				  app.sendTextMark.get(packageName) +
	 				  app.phoneNative.get(packageName) +
	 				  app.SMNativeSMS.get(packageName) +
	 				  app.getServiceSmsMark.get(packageName));	
	 		  		  app.getServiceSmsMark.put(packageName, 0);
	 		  		  app.BinderProxyISms.put(packageName, 0);
	 		  		  app.sendTextMark.put(packageName, 0);
	 		  		  app.phoneNative.put(packageName, 0);
	 		  		  app.SMNativeSMS.put(packageName, 0);
	 		
	 	 }
	 	 
	 	 
	  }
	 	
	 //---------------------------------------------	
	
	
	
	
	
	
	

}
