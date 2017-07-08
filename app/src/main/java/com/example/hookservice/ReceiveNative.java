package com.example.hookservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.os.IBinder;
import android.util.Log;

public class ReceiveNative extends Service{
	
	
	  private LocalServerSocket mServerSocketSms = null;  
	  private LocalServerSocket mServerSocketMedia = null;
	  private LocalServerSocket mServerSocketLocation = null;
	  private LocalServerSocket mServerSocketContact = null;
	  
	  SavetoDatabase savedata;
	  private static final String TAG = "SocketService";  
	  private static final String SOCKET_ADDRESS_SMS = "SENDDATA";
	  private static final String SOCKET_ADDRESS_Media = "SENDDATAMedia";
	  private static final String SOCKET_ADDRESS_Location = "SENDDATALocation";
	  private static final String SOCKET_ADDRESS_Contact = "SENDDATAContact";
	  
	   @Override
	   public IBinder onBind(Intent intent) {
	      return null;
	   }
		
	   @Override
	   public int onStartCommand(Intent intent, int flags, int startId) {
	      // Let it continue running until it is stopped.
	      
		  Log.d("hook-ioctl","service started");
		  savedata = new SavetoDatabase();
		  StartSocket(); 
		   
	      return START_STICKY;
	   }

	   @Override
	   public void onDestroy() {
	      super.onDestroy();
	      
	   }
	   
	   
//--------开始监听端口---------	   
	   private void StartSocket() {
		   
		   try {  
	            mServerSocketSms = new LocalServerSocket(SOCKET_ADDRESS_SMS);  
	            mServerSocketMedia = new LocalServerSocket(SOCKET_ADDRESS_Media);
	            mServerSocketLocation = new LocalServerSocket(SOCKET_ADDRESS_Location);
	            mServerSocketContact = new LocalServerSocket(SOCKET_ADDRESS_Contact);
	            
	        } catch (IOException e) {  
	            Log.v(TAG, "in onCreate, making server socket: " + e);  
	            return;  
	        }  
	        
//-------------------------SMS socket---------------------------------------	      	        
	        Thread t = new Thread() {  
	            @Override 
	            public void run() {  
	            	LocalSocket socket = null;  	              	                
	                while(true) {
	                    try {  
	                        Log.v(TAG, "SMS Waiting for connection...");  
	                        socket = mServerSocketSms.accept();  
	                        
	                        //Log.v(TAG, "Got SMS socket: " + socket);  
	                        if (socket != null) {  
	                            startEchoThread(socket,"SMS");  
	                        } else {  
	                            return;  // socket shutdown?  
	                        }  
	                    } catch (IOException e) {  
	                        Log.v(TAG, "in SMS accept: " + e);  
	                    }  	                
	                }    	                    
	            }  
	        };  
	    t.start();  
//---------------------end socket----------------------------------------------	    
	
	    
	    
 //-------------------------media socket---------------------------------------	      	        
        Thread t_media = new Thread() {  
            @Override 
            public void run() {  
            	LocalSocket socket = null;  	              	                
                while(true) {
                    try {  
                        Log.v(TAG, "Media Waiting for connection...");  
                        socket = mServerSocketMedia.accept();  
                        
                        //Log.v(TAG, "Got Media socket: " + socket);  
                        if (socket != null) {  
                            startEchoThread(socket,"Media");  
                        } else {  
                            return;  // socket shutdown?  
                        }  
                    } catch (IOException e) {  
                        Log.v(TAG, "in Media accept: " + e);  
                    }  	                
                }    	                    
            }  
        };  
    t_media.start();  
//---------------------end socket----------------------------------------------	    
	    
	    
	    
    
    //-------------------------Location socket---------------------------------------	      	        
    Thread t_Location = new Thread() {  
        @Override 
        public void run() {  
        	LocalSocket socket = null;  	              	                
            while(true) {
                try {  
                    Log.v(TAG, "Location Waiting for connection...");  
                    socket = mServerSocketLocation.accept();  
                    
                    //Log.v(TAG, "Got Location socket: " + socket);  
                    if (socket != null) {  
                        startEchoThread(socket,"Location");  
                    } else {  
                        return;  // socket shutdown?  
                    }  
                } catch (IOException e) {  
                    Log.v(TAG, "in Location accept: " + e);  
                }  	                
            }    	                    
        }  
    };  
     t_Location.start();  
//---------------------end socket----------------------------------------------	       
    
	    

     
     //-------------------------Contact socket---------------------------------------	      	        
     Thread t_Contact = new Thread() {  
         @Override 
         public void run() {  
         	LocalSocket socket = null;  	              	                
             while(true) {
                 try {  
                     Log.v(TAG, "Contact Waiting for connection...");  
                     socket = mServerSocketContact.accept();  
                     
                     //Log.v(TAG, "Got Contact socket: " + socket);  
                     if (socket != null) {  
                         startEchoThread(socket,"Contact");  
                     } else {  
                         return;  // socket shutdown?  
                     }  
                 } catch (IOException e) {  
                     Log.v(TAG, "in Contact accept: " + e);  
                 }  	                
             }    	                    
         }  
     };  
      t_Contact.start();  
 //---------------------end socket----------------------------------------------	        
     
     
     
     
     
	    
	    
	   }
	   
	   
	   
	   
//------------为每一个连接创建一个socket---------------------	   
	   private void startEchoThread(final LocalSocket socket, final String source) {  
	        Thread t = new Thread() {  
	        	
	                @Override 
	                public void run() {  
	                    try {  
	                    	InputStream   is = socket.getInputStream();   
	                    	InputStreamReader   isr = new InputStreamReader(is);  
	                    	BufferedReader    reader = new BufferedReader(isr);  
	                      
	                    	
	                    	String msg = "null";
	                    		       
	                    		try{
	                    			
	                    			msg = reader.readLine();
	                    			
	                    	    
	                    	    if(msg != null) {
	                    	    	 Log.v(TAG, "get msg: " + msg );
	                    	    	
	                    	    	 if(msg.indexOf("FromNative") != -1) {
	                    	    		 msg = dealWithNative(msg);
	                    	    	 }
	                    	    	 
	                    	    	
	                    	    	savedata.processmsg(msg,source);
	                    	    }
	                    	    
	                    	    
	                    	    isr.close();
	                    		is.close();
	                    		socket.close();
	                    	    
	                    	}catch(Exception e) {
	                    		Log.v(TAG, "in while loop" ); 
	                    		e.printStackTrace();
	                    		isr.close();
	                    		is.close();
	                    		socket.close();               	    		
	                    		
	                    		}
	                    	    
	                    	 
	                    	     
                           
	                          
	                    } catch (IOException e) {  
	                        Log.v(TAG, "in echo thread loop: " + e.getMessage());  
	                    }  
	                }  
	            };  
	        t.start();  
	    }  
	   
//-----------------------deal with native--------------------------------------------
	 private String dealWithNative(String msg) {
		 if(msg.indexOf("FromNative") != -1) {	
			    String[] parts = msg.split("-");
				String packageName = parts[0];
				String functionName = parts[1]; 
				String pid = parts[2]; 
				String uid = parts[3];
				String time =  parts[4];
				
				 
				  
				  ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);//获取系统的ActivityManager服务
				  for (ActivityManager.RunningAppProcessInfo appProcess : am.getRunningAppProcesses()){
				      if(appProcess.pid == Integer.parseInt(pid)){
				    	  packageName = appProcess.processName;
				          break;
				      }
				      
				      if(appProcess.uid == Integer.parseInt(uid)){
				    	  packageName = appProcess.processName;
				          break;
				      }
				      
				      
				      
				  }
				  
			 msg = packageName + "-" + functionName + "-" + pid + "-" + uid + "-" +time;
				
						
			 }else {			
			    Log.v(TAG,"dealWithNative get: " + msg);	 
			 }
		 
		 
		 return msg;
	 }
	   

}
