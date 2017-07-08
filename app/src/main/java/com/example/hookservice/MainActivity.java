package com.example.hookservice;

import java.util.Iterator;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.leonnewton.databaseopre.DaoSession;
import com.leonnewton.databaseopre.Msg;
import com.leonnewton.databaseopre.MsgDao;
import com.leonnewton.databaseopre.MsgDao.Properties;

import eu.chainfire.libsuperuser.Shell;

public class MainActivity extends ActionBarActivity {
	 MsgDao msgDao;
	
	
	
    private class Startup extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog = null;
        private Context context = null;
        private boolean suAvailable = false;
        private String suVersion = null;
        private String suVersionInternal = null;
        private List<String> suResult = null;
       

        public Startup setContext(Context context) {
            this.context = context;
            return this;
        }

        @Override
        protected void onPreExecute() {
            // We're creating a progress dialog here because we want the user to wait.
            // If in your app your user can just continue on with clicking other things,
            // don't do the dialog thing.

           
        }

        @Override
        protected Void doInBackground(Void... params) {
        	
        	//start service
        	 startService(new Intent(getBaseContext(), ReceiveNative.class));
        	
        	
        	CopyFile test = new CopyFile();
        	test.copyAssetFolder(getAssets(), "binary", "/data/data/com.example.hookservice/binary");
  
        	
        	
            // Let's do some SU stuff
            suAvailable = Shell.SU.available();
            if (suAvailable) {
                suVersion = Shell.SU.version(false);
                suVersionInternal = Shell.SU.version(true);
                
                
                
                suResult = Shell.SU.run(new String[] {
                		//"mv /data/data/com.example.hookservice/binary/libleonmedia.so /data/local/libleonmedia.so",
                		//"mv /data/data/com.example.hookservice/binary/libphone.so /data/local/libphone.so",
                		//"mv /data/data/com.example.hookservice/binary/libsm.so /data/local/libsm.so",
                		//"mv /data/data/com.example.hookservice/binary/libss.so /data/local/libss.so",
                		//"mv /data/data/com.example.hookservice/binary/injector.so /data/local/injector",
                		//"chown root:root /data/local/injector",
                		//"chown root:root /data/local/libleonmedia.so",
                		//"chown root:root /data/local/libphone.so",
                		//"chown root:root /data/local/libsm.so",
                		//"chown root:root /data/local/libss.so", 
                		//"chmod 777 /data/local/injector",     
                		//"chmod 777 /data/local/libleonmedia.so",
                		//"chmod 777 /data/local/libphone.so",
                		//"chmod 777 /data/local/libsm.so",
                		//"chmod 777 /data/local/libss.so",            		
                        //"/data/local/injector"
                		
                      
                });
                
                
                
                
            }

            // This is just so you see we had a progress dialog, 
            // don't do this in production code
           

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
           		

            // output
            StringBuilder sb = (new StringBuilder()).
                    append("Root? ").append(suAvailable ? "Yes" : "No").append((char)10).
                    append("Version: ").append(suVersion == null ? "N/A" : suVersion).append((char)10).
                    append("Version (internal): ").append(suVersionInternal == null ? "N/A" : suVersionInternal).append((char)10).
                    append((char)10);
            if (suResult != null) {
                for (String line : suResult) {
                    sb.append(line).append((char)10);
                }
            }
            System.out.println(sb.toString());
        }		
    }
	
	
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        (new Startup()).setContext(this).execute();
        
        DaoSession  daoSession = App.getInstance().getDaoSession();
		msgDao = daoSession.getMsgDao();
        
        
        Button sendtext = (Button)findViewById(R.id.button1);
        sendtext.setOnClickListener(new OnClickListener(){
        	
        	public void onClick(View v) {
        	   List<Msg> sendsmsList = msgDao.queryBuilder().where(Properties.PackageName.eq("com.example.luoge.sendsms")).list();
        		//System.out.println(sendsmsList);
        		for(Msg item : sendsmsList) {
        			  System.out.println(item.getPackageName()+item.getFunctionName()+item.getTime());
        			}
        		
        	}
        	
        });
       
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
