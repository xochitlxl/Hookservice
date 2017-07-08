package com.example.hookservice;

import android.util.Log;

public class CheckMedia {
	
	private static final String TAG = "SocketService";  
	
	public static final String MediaStart = "Mediastart";
	public static final String IMediaRecorder = "MediaIR";
	public static final String GetAudioFlinger = "audioflinger";
	public static final String IAudioRecord = "AudioIR";
	public static final String getMinBufferSize = "getMinBufferSize";
	
	private App app;
	
	public static CheckMedia instanceCheckMedia;
	
	 public CheckMedia(){
	    	
	    	instanceCheckMedia = this;
	    	app = App.getInstance();
	    }
	
	
	public void checkMediaStatus(String functionName, String packageName) {
		
		if(functionName !=null && packageName != null) {
			
			
			
			  if(functionName.equalsIgnoreCase(IMediaRecorder)) {
				  if(app.IMediaRecorder.get(packageName) == null) {
					  app.IMediaRecorder.put(packageName, 1);
				  }else {
					  int temp = app.IMediaRecorder.get(packageName);
					  app.IMediaRecorder.put(packageName, temp+1);
				  }
				  		
				  
			  
			  }else if(functionName.equalsIgnoreCase(MediaStart)) {
				  				  
				  if(app.MediaStart.get(packageName) == null) {
					  app.MediaStart.put(packageName, 1);
				  }else {
					  int temp = app.MediaStart.get(packageName);
					  app.MediaStart.put(packageName, temp+1);
				  }		  			  
				  
			  }else if(functionName.equalsIgnoreCase(GetAudioFlinger)) {
  				  
				  	if(app.getaudioflinger.get(packageName) == null) {
				  		app.getaudioflinger.put(packageName, 1);
				  	}else {
				  		int temp = app.getaudioflinger.get(packageName);
				  		app.getaudioflinger.put(packageName, temp+1);
			  }		  			  
  
				  	
				  	
				  	
				  	int temp = app.getaudioflinger.get(packageName);
				  	
				  	
				  	if(temp == 2 && app.IMediaRecorder.get(packageName) != null &&
				  			app.IMediaRecorder.get(packageName) != 0) {
			  		
				  		 final String packageNamefinal = packageName;
					        Thread t = new Thread() {  
				                @Override 
				                public void run() {  
				                	
				                    try {
				                        Thread.sleep(1000);
				                        updateStateMedia(packageNamefinal); 

				                    } catch (InterruptedException e) { }
				               
				               }
				            };  
				            t.start();  	
				  	}else if(temp == 2 && app.IAudioRecord.get(packageName) != null &&
				  			app.IAudioRecord.get(packageName) != 0){
				  		
				  		 final String packageNamefinal = packageName;
					        Thread t = new Thread() {  
				                @Override 
				                public void run() {  
				                	
				                    try {
				                        Thread.sleep(1000);
				                        updateStateAudio(packageNamefinal); 

				                    } catch (InterruptedException e) { }
				               
				               }
				            };  
				            t.start();  	
	  		
				  		
				  	}
	  	
				  	
			  }else if(functionName.equalsIgnoreCase(getMinBufferSize)) {
	  
				  if(app.getMinBufferSize.get(packageName) == null) {
					  app.getMinBufferSize.put(packageName, 1);
				  }else {
					  int temp = app.getMinBufferSize.get(packageName);
					  app.getMinBufferSize.put(packageName, temp+1);
				  		}		  			  

			  }else if(functionName.equalsIgnoreCase(IAudioRecord)) {
				  if(app.IAudioRecord.get(packageName) == null) {
					  app.IAudioRecord.put(packageName, 1);
				  }else {
					  int temp = app.IAudioRecord.get(packageName);
					  app.IAudioRecord.put(packageName, temp+1);
				  }
	  
			  
			  }
			
			
			
			
			
			
			
			
		}
		
		
		
		Log.v(TAG,"checkMediaStatus: " + functionName + packageName);	 
	}
	
	
//=====================================================================
	
	
	 private void updateStateMedia(String packageName) {
		 
		 
		 int IMediaRecordercheck = app.IMediaRecorder.get(packageName);
		 int MediaStartcheck = app.MediaStart.get(packageName);
		 int GetAudioFlingercheck = app.getaudioflinger.get(packageName);
		 
		 
		 
		 if(        MediaStartcheck == 1 && 
				 IMediaRecordercheck == 1 &&
				 GetAudioFlingercheck == 2) {
			 		 
			 		  Log.v(TAG,"ALL Mark OK: " + packageName + IMediaRecordercheck + MediaStartcheck 
			 				  + GetAudioFlingercheck);	
			 		  app.IMediaRecorder.put(packageName, 0);
			 		  app.MediaStart.put(packageName, 0);
			 		  app.getaudioflinger.put(packageName, 0);
		 		  			 		  
			 	 }
			 	 else  {
			 		
			 		  Log.v(TAG,"ALL Mark not OK: " + packageName +
			 				  app.IMediaRecorder.get(packageName) + 
			 				  app.MediaStart.get(packageName) +
			 				  app.getaudioflinger.get(packageName) );
			 		  
			 		  app.IMediaRecorder.put(packageName, 0);
			 		  app.MediaStart.put(packageName, 0);
			 		  app.getaudioflinger.put(packageName, 0);
		 				  
			 		
			 	 }
			 	 

	 }
//=================================================================================	
	
	 private void updateStateAudio(String packageName) {
		 
		 
		 int IAudioRecordercheck = app.IAudioRecord.get(packageName);
		 int GetAudioFlingercheck = app.getaudioflinger.get(packageName);
		 int getMinBufferSizecheck = app.getMinBufferSize.get(packageName);
		 
		 
		 
		 if(      getMinBufferSizecheck == 1 && 
				 IAudioRecordercheck    == 1 &&
				  GetAudioFlingercheck ==  2) {
			 		 
			 		  Log.v(TAG,"ALL Mark OK: " + packageName + IAudioRecordercheck + getMinBufferSizecheck 
			 				  + GetAudioFlingercheck);	
			 		  app.IAudioRecord.put(packageName, 0);
			 		  app.getaudioflinger.put(packageName, 0);
			 		  app.getMinBufferSize.put(packageName, 0);
		 		  			 		  
			 	 }
			 	 else  {
			 		
			 		  Log.v(TAG,"ALL Mark not OK: " + packageName +
			 				  app.IAudioRecord.get(packageName) + 
			 				  app.getaudioflinger.get(packageName) +
			 				  app.getMinBufferSize.get(packageName) );
			 		  
			 		  app.IAudioRecord.put(packageName, 0);
			 		  app.getaudioflinger.put(packageName, 0);
			 		  app.getMinBufferSize.put(packageName, 0);
		 				  
			 		
			 	 }
			 	 

	 }
//================================================================================	 
	 
	 
	 
	

}
