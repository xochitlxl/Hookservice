package com.example.hookservice;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.PendingIntent;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class HookService implements IXposedHookLoadPackage {
	
	
	private static boolean issocketcreated = false;
	
	LocalSocket socketSMS;
	LocalSocket socketMedia;
	LocalSocket socketLocation;
	LocalSocket socketContact;
	LocalSocket ss;
	
	PrintStream printStream;
	int tryConnect = 0;
	
	private static final String SOCKET_ADDRESS = "SENDDATA";
	
	private boolean startsocket(boolean thdcreated, final String msg, final String socketname) {
		
		
		try{
			 ss = new LocalSocket();
             ss.connect(new LocalSocketAddress(socketname));
           	 printStream = new PrintStream(ss.getOutputStream());
           	 printStream.print(msg+'\n');
           	 printStream.flush( );
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		try{
			printStream.close();
			ss.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
        
        
        
        
		
		return false;
		
	}
	
	
	
	
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
    	
    	
    	
    	
    	findAndHookMethod("com.android.internal.telephony.RIL", lpparam.classLoader, "sendSMS", String.class, String.class,  Message.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called before the clock was updated by the original method
            	    long timecalled = System.currentTimeMillis();
            
            	    String packageName = lpparam.packageName;
            	    
            		int CallingPid = Binder.getCallingPid();
            		int CallingUid = Binder.getCallingUid();
            		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	                String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
            		Log.d("hook-ioctl", currentDateTime + " RIL Pid: " + Integer.toString(CallingPid) + " Uid: " + Integer.toString(CallingUid));
            		
            		String sendMsg = packageName+"-"+"Rilsendsms"+"-"+CallingPid+"-"+CallingUid+"-"+timecalled ;
            		issocketcreated = startsocket(issocketcreated, sendMsg,"SENDDATA");
            	
            }
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called after the clock was updated by the original method
            }
    });
    	
    	
    	
    	
    	
    	
    	  
    	
    	
    	
    	
    	
    	
    	
    	
    	
//=========================================================================    	
    		 
    		 findAndHookMethod("android.os.BinderProxy", lpparam.classLoader, "transact", int.class, Parcel.class, Parcel.class, int.class, new XC_MethodHook() {
    	            @Override
    	            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
    	                // this will be called before the clock was updated by the original method
    	            	long timecalled = System.currentTimeMillis();
    	            	
    	            	int CallingPid = Binder.getCallingPid();
                		int CallingUid = Binder.getCallingUid();
                		String packageName = lpparam.packageName;
                		
    	            	IBinder binder = (IBinder) param.thisObject;
    	            	String descriptor = (binder == null ? null : binder.getInterfaceDescriptor());
    	            	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    	                String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
    	                
    	                
    	                if(descriptor.equals("com.android.internal.telephony.ISms"))   {
    	                	
    	                	String sendMsg = packageName+"-"+"BinderProxyISms"+"-"+CallingPid+"-"+CallingUid+"-"+timecalled ;
    	            		issocketcreated = startsocket(issocketcreated, sendMsg, "SENDDATA"); 	
    	                	Log.d("hook-ioctl", currentDateTime + " BinderProxy.Isms " + descriptor + "Pid: " + CallingPid + " Uid: " + CallingUid);
    	                
    	                }
    	                
    	                
    	                if(descriptor.equals("android.location.ILocationManager"))
    	                	Log.d("hook-ioctl", currentDateTime + " BinderProxy.ILocationManager " + descriptor + "Pid: " + CallingPid + " Uid: " + CallingUid);
    	          
    	                
    	                if(descriptor.equals("android.media.IMediaPlayerService"))
    	                	Log.d("hook-ioctl", currentDateTime + " BinderProxy.IMediaPlayerService " + descriptor + "Pid: " + CallingPid + " Uid: " + CallingUid);
    	                
    	                
    	                
    	            }
    	            @Override
    	            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
    	                // this will be called after the clock was updated by the original method
    	            }
    	    });	 
    		 
    	          
    	
    	
    	
 //===========================================================================================   	

        findAndHookMethod("android.os.ServiceManager", lpparam.classLoader, "getService", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called before the clock was updated by the original method
            	long timecalled = System.currentTimeMillis();
            	
	           
                 
            	
            	
            	if(param.args[0].toString().equals("isms")){
            		
            		
            		int CallingPid = Binder.getCallingPid();
            		int CallingUid = Binder.getCallingUid();
            		String packageName = lpparam.packageName;
            		
            		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	                String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
            		Log.d("hook-ioctl",currentDateTime + " getservice Isms Pid: " + Integer.toString(CallingPid) + " Uid: " + Integer.toString(CallingUid));
            		
            		String sendMsg = packageName+"-"+"getServiceisms"+"-"+CallingPid+"-"+CallingUid+"-"+timecalled ;
            		issocketcreated = startsocket(issocketcreated, sendMsg,"SENDDATA");
            	}
            	
            	
            	if(param.args[0].toString().equals("location")){
            		int CallingPid = Binder.getCallingPid();
            		int CallingUid = Binder.getCallingUid();
            		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	                String currentDateTime = dateFormat.format(new Date()); // Find todays date
            		Log.d("hook-ioctl",currentDateTime + " getservice location Pid: " + Integer.toString(CallingPid) + " Uid: " + Integer.toString(CallingUid));
            		
            		
            	}
            	
            	           	
            	
            }
            
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called after the clock was updated by the original method
            }
    });
        
//========================================================================================        
        findAndHookMethod("android.telephony.SmsManager", lpparam.classLoader, "sendTextMessage", String.class, String.class, String.class,  PendingIntent.class , PendingIntent.class, new XC_MethodHook() {
           
        	
        	
        	@Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called before the clock was updated by the original method
            	
        		    long timecalled = System.currentTimeMillis();
            	
            	    System.out.println(lpparam.packageName);
            	    System.out.println(lpparam.processName);
            	    String packageName = lpparam.packageName;
            	    
            		int CallingPid = Binder.getCallingPid();
            		int CallingUid = Binder.getCallingUid();
            		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	                String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
            		Log.d("hook-ioctl", currentDateTime + " sendTextMessage Pid: " + Integer.toString(CallingPid) + " Uid: " + Integer.toString(CallingUid));
            		
            		
            		String sendMsg = packageName+"-"+"sendTextMessage"+"-"+CallingPid+"-"+CallingUid+"-"+timecalled ;
            		issocketcreated = startsocket(issocketcreated, sendMsg,"SENDDATA");
            		
            		
          
            			
            			
            			
            	   
            }
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called after the clock was updated by the original method
            }
    });
        
        
        
        findAndHookMethod("android.location.LocationManager", lpparam.classLoader, "getLastKnownLocation", String.class,  new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called before the clock was updated by the original method
            	
            	
            	
            		int CallingPid = Binder.getCallingPid();
            		int CallingUid = Binder.getCallingUid();
            		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	                String currentDateTime = dateFormat.format(new Date()); // Find todays date
            		Log.d("hook-ioctl", currentDateTime + " getLastKnownLocation Pid: " + Integer.toString(CallingPid) + " Uid: " + Integer.toString(CallingUid));
            		
            		
            	
            }
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called after the clock was updated by the original method
            }
    });
        
        
 //==========================================================================================================       
        
        findAndHookMethod("com.android.server.LocationManagerService", lpparam.classLoader, "getLastLocation", Class.forName("android.location.LocationRequest"),  String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called before the clock was updated by the original method
            	
            	
            	
            		int CallingPid = Binder.getCallingPid();
            		int CallingUid = Binder.getCallingUid();
            		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	                String currentDateTime = dateFormat.format(new Date()); // Find todays date
            		Log.d("hook-ioctl", currentDateTime + " LocationManagerService getLastLocation Pid: " + Integer.toString(CallingPid) + " Uid: " + Integer.toString(CallingUid));
            		
            		
            	
            }
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called after the clock was updated by the original method
            }
    });
        
        
 //==========================================================================================       
        
        
        findAndHookMethod("android.media.MediaRecorder", lpparam.classLoader, "start",  new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called before the clock was updated by the original method
            	    long timecalled = System.currentTimeMillis();
            	    String packageName = lpparam.packageName;
            	    
            	    
            	    
            	
            		int CallingPid = Binder.getCallingPid();
            		int CallingUid = Binder.getCallingUid();
            		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	                String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
            		Log.d("hook-ioctl", currentDateTime + " MediaRecorder start Pid: " + Integer.toString(CallingPid) + " Uid: " + Integer.toString(CallingUid));
            		
            		String sendMsg = packageName+"-"+"Mediastart"+"-"+CallingPid+"-"+CallingUid+"-"+timecalled ;
            		issocketcreated = startsocket(issocketcreated, sendMsg, "SENDDATAMedia");
            		
            	
            }
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called after the clock was updated by the original method
            }
    });
        
        
//=================================================================================================        
        
        findAndHookMethod("android.media.AudioRecord", lpparam.classLoader, "getMinBufferSize", int.class, int.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called before the clock was updated by the original method
            	    long timecalled = System.currentTimeMillis();
            	    String packageName = lpparam.packageName;
            	    
            	    
            	    
            	
            		int CallingPid = Binder.getCallingPid();
            		int CallingUid = Binder.getCallingUid();
            		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	                String currentDateTime = dateFormat.format(new Date(timecalled)); // Find todays date
            		Log.d("hook-ioctl", currentDateTime + "AudioRecord getMinBufferSize Pid: " + Integer.toString(CallingPid) + " Uid: " + Integer.toString(CallingUid));
            		
            		String sendMsg = packageName+"-"+"getMinBufferSize"+"-"+CallingPid+"-"+CallingUid+"-"+timecalled ;
            		issocketcreated = startsocket(issocketcreated, sendMsg, "SENDDATAMedia");
            		
            	
            }
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called after the clock was updated by the original method
            }
    });    
        
  //==================================================================================================      
        
        
        
        
        
        
    }
}