package com.ricky.chinaairpollution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.ricky.chinaairpollution.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
//jsoup
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
//

public class TheService extends Service {
//	public static boolean active = false;
//	public static boolean firstRun = true;
	public static Document doc;
	public static Element ele;
	private IntentFilter serviceIntentFilter; // For receiving message
//	private static Integer loop = 0;
	private static boolean looper = false;

	//notification
	Intent notificationIntent;
	Notification notification;
	//
	
	//handler
    Handler handler = new Handler();
    Timer timer = new Timer();
    //
    //runnable
    Runnable runnable;
    //
	
	//vibrator
	private static Vibrator v;
	//sound
	private static Uri uri;
	private static Ringtone r;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void initializeVariables() {
		// Intent filter
		serviceIntentFilter = new IntentFilter();
		serviceIntentFilter.addAction(Constants.ACTION_MAINACTIVITY_TO_SERVICE);
		serviceIntentFilter.addAction(Constants.ACTION_SERVICE_TO_SERVICE);
		serviceIntentFilter.addAction(Constants.ACTION_SETTINGSACTIVITY_TO_SERVICE);
		
		registerReceiver(serviceIntentReceiver, serviceIntentFilter);
		
//		PreferenceConnector.writeInteger(getApplicationContext(), "intervalLoop", 1);
		PreferenceConnector.writeBoolean(getApplicationContext(), "intervalLooper", false);
		PreferenceConnector.writeLong(getApplicationContext(), "intervalTime", Constants.INTERVAL_ONE_SECOND);

		PreferenceConnector.writeInteger(getApplicationContext(), "seconds", 0);
//		active = true;
//		firstRun = true;
		PreferenceConnector.writeBoolean(getApplicationContext(), "serviceActive", true);
		PreferenceConnector.writeBoolean(getApplicationContext(), "firstRun", true);
//		PreferenceConnector.writeBoolean(getApplicationContext(), "refresh", false);
		Log.d("test", "aa");
	}
	
	private void initializeVibrate(){
		//vibrator
		v = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
		//
	}
	private void initializeSound(){
		//sound
		InputStream myInput = getResources().openRawResource(R.raw.jingle);
	    String outFileName = Environment.getExternalStorageDirectory() + "/Ringtones/" + getResources().getResourceEntryName(R.raw.jingle) + ".mp3";
	    OutputStream myOutput;
		try {
			myOutput = new FileOutputStream(outFileName);
			
			
			// transfer bytes from the input file to the output file
		    byte[] buffer = new byte[1024];
		    int length;
		    while ((length = myInput.read(buffer)) > 0)
		    {
		        myOutput.write(buffer, 0, length);
		    }

		    // Close the streams
		    myOutput.flush();
		    myOutput.close();
		    myInput.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		File file = new File(android.os.Environment.getExternalStorageDirectory(),"/Ringtones/jingle.mp3");
		uri = Uri.parse(Uri.decode(file.getAbsolutePath()));
//				RingtoneManager.getDefaultUri(PreferenceConnector.readInteger(getApplicationContext(), "ringtoneChoose", 0));
		Log.d("test", "failed uri");
		//		r = RingtoneManager.getRingtone(getApplicationContext(), uri);
		//
	}
	
	private void changeSound(){
		File file = new File(android.os.Environment.getExternalStorageDirectory(),"/Ringtones/" + PreferenceConnector.readString(getApplicationContext(), "ringtoneChoose", "jingle.mp3"));
		uri = Uri.parse(Uri.decode(file.getAbsolutePath()));
	}
	
	private void initializeNotification(){
		notificationIntent = new Intent(TheService.this, MainActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		notificationIntent.setAction(Intent.ACTION_MAIN);
		notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);

//		notification = new Notification(R.drawable.ic_launcher, null, System.currentTimeMillis());
//		notification.flags = Notification.FLAG_AUTO_CANCEL;
//		notification.setLatestEventInfo(getApplicationContext(), (CharSequence)"Beijing Air Pollution", (CharSequence)"Load Data...", PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0));
		Notification.Builder builder = new Notification.Builder(getApplicationContext())
	        .setContentTitle(getString(R.string.app_name))
	        .setSmallIcon(R.drawable.ic_launcher)
	        .setWhen(System.currentTimeMillis())
	        .setContentText((CharSequence)"Load Data...")
	        .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0));

		notification = builder.getNotification();
		
		startForeground(Constants.SERVICE_NOTIFICATION_ID, notification);
	}
	
	private boolean checkRange(int x, int y){
		if (x >= y) return true;
		else return false;
	}
	private boolean checkRangeLess(int x, int y){
		if (x < y) return true;
		else return false;
	}
	
	private void updateNotification(){
		int updateAqi = PreferenceConnector.readInteger(getApplicationContext(), "aqi", 0);
		String updateAqiMsg = PreferenceConnector.readString(getApplicationContext(), "aqiMsg", "Loading Data...");
		String updateAqiCity = PreferenceConnector.readString(getApplicationContext(), "aqiCity", "");
//		notification.setLatestEventInfo(getApplicationContext(), (CharSequence) "Beijing Air Pollution", Integer.toString(updateAqi) + " - " + (CharSequence) updateAqiMsg, PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0));	

//		notification.when = PreferenceConnector.readLong(getApplicationContext(), "updateTime", 0);
//		Log.e("test", String.valueOf(PreferenceConnector.readInteger(getApplicationContext(), "updateTime", 0)));
		
		
		
		Notification.Builder builder = new Notification.Builder(getApplicationContext())
	        .setContentTitle(getString(R.string.app_name))
	        .setSmallIcon(R.drawable.ic_launcher)
	        .setWhen(PreferenceConnector.readLong(getApplicationContext(), "updateTime", 0))
	        .setContentText(
	        		updateAqiCity + 
	        		" : " +
	        		updateAqi + 
	        		" - " + 
	        		updateAqiMsg)
	        .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0));
	    notification = builder.getNotification();
	    
//	    Global.gMAqiChoose = PreferenceConnector.readInteger(getApplicationContext(), "aqiChoose", 0);
	    
	    if (checkRange(updateAqi, PreferenceConnector.readInteger(getApplicationContext(), "aqiChoose", 0))){
	    
	    	if (PreferenceConnector.readBoolean(getApplicationContext(), "soundActive", true) == true)
	    		notification.sound = uri;
	    	if (PreferenceConnector.readBoolean(getApplicationContext(), "vibrateActive", true) == true)
	    		notification.defaults |= Notification.DEFAULT_VIBRATE;
		}
	    
		startForeground(Constants.SERVICE_NOTIFICATION_ID, notification);
	}
	
	private BroadcastReceiver serviceIntentReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle extras = intent.getExtras();
			// TODO Auto-generated method stub
			
			if (extras != null) {
				if (extras.getString("Type").equalsIgnoreCase("from-activity-to-refresh")) {
					PreferenceConnector.writeInteger(getApplicationContext(), "aqi", 0);
					PreferenceConnector.writeString(getApplicationContext(), "aqiMsg", "Load Data...");
					PreferenceConnector.writeString(getApplicationContext(), "aqiTemp", "");

					PreferenceConnector.writeString(getApplicationContext(), "updateTimeComplete", "");
//					PreferenceConnector.writeInteger(getApplicationContext(), "updateTimeHour", 0); 
//					PreferenceConnector.writeInteger(getApplicationContext(), "updateTimeMinute", 0);
					
					handler.removeCallbacks(runnable);
//					updateSchedule();
//					long tempInterval = PreferenceConnector.readLong(getApplicationContext(), "intervalChoose", 0);
      			  	PreferenceConnector.writeLong(getApplicationContext(), "intervalTime", Constants.INTERVAL_ONE_SECOND);
      				PreferenceConnector.writeBoolean(getApplicationContext(), "intervalLooper", false);
					handler.post(runnable);
					AsyncTask<String, String, Integer> mmAsyncTask = new mAsyncTask().execute();
//					PreferenceConnector.writeBoolean(getApplicationContext(), "refresh", true);
//					loop = PreferenceConnector.readInteger(getApplicationContext(), "intervalChoose", 1);
//					firstRun = true;
//					JSONObject obj;
//					try {
//						obj = new JSONObject(extras.getString("message"));
						
//						Toast.makeText(getApplicationContext(), obj.getString("nim"), Toast.LENGTH_SHORT).show();
//						Toast.makeText(getApplicationContext(), obj.getString("password"), Toast.LENGTH_SHORT).show();
//						socket.emit("send-login", extras.getString("message"));
					
					//send something to server
//					socket.emit("send-login-data", extras.getString("message"));
//					sendServiceBroadcastLogin();
//					} catch (JSONException e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					updateNotification();

					serviceBroadcastRefresh(getApplicationContext(), "Refresh Data");

					Utility.showToast(getApplicationContext(), "Refreshing AQI Info.");
				}
				else if (extras.getString("Type").equalsIgnoreCase("from-service-failed")){
//					JSONObject obj;
					try {
//						obj = new JSONObject(extras.getString("message"));
						Toast.makeText(getApplicationContext(), extras.getString("message"), Toast.LENGTH_LONG).show();
//						Utility.showToast(getApplicationContext(), extras.getString("message"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (extras.getString("Type").equalsIgnoreCase("from-setting-sound")){
//					JSONObject obj;
					try {
//						obj = new JSONObject(extras.getString("message"));
						changeSound();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	};
	
	private static void serviceBroadcastFailed(Context context, String message) {
		// TODO Auto-generated method stub
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Type", "from-service-failed"));
		params.add(new BasicNameValuePair("message", message));
		Utility.sendBroadcast(context, params,
				Constants.ACTION_SERVICE_TO_SERVICE);
	}
	private static void serviceBroadcastAqiData(Context context, String message) {
		// TODO Auto-generated method stub
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Type", "from-service-aqi-data"));
		params.add(new BasicNameValuePair("message", message));
		Utility.sendBroadcast(context, params,
				Constants.ACTION_SERVICE_TO_MAINACTIVITY);
	}
	private static void serviceBroadcastRefresh(Context context, String message) {
		// TODO Auto-generated method stub
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Type", "from-service-to-refresh"));
		params.add(new BasicNameValuePair("message", message));
		Utility.sendBroadcast(context, params,
				Constants.ACTION_SERVICE_TO_MAINACTIVITY);
	}

	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//	    startForeground(1, new Notification());
		
	     /*public void notifUpdate(){
	 		note.setLatestEventInfo(this, "Global.i",
	                 Integer.toString(Global.i), i);
	 		mgr.notify(1, note);
	 	};    */
		
//		active = true;
//		firstRun = true;
		
		initializeNotification();
		
		initializeVariables();
		initializeVibrate();
		initializeSound();
	           
//	    int seconds = 0;
//		Calendar c;
//		c = Calendar.getInstance(); 
//		seconds = c.get(Calendar.SECOND); //2 00
//		PreferenceConnector.writeInteger(getApplicationContext(), "seconds", seconds);
//	    timer.cancel();
//		  timer.schedule(task, 0, PreferenceConnector.readLong(getApplicationContext(), "intervalTime", Constants.INTERVAL_FIVE_MINUTE));
	    runnable = new Runnable() {       
	         @Override
	         public void run() {
//	           handler.post(new Runnable() {
//	              public void run() {
	            	  try{
		            	  
//		            	  notifUpdate();
//		            	  Log.d("test", "1");
//		            	  Utility.showToast(getApplicationContext(), Integer.toString(Global.i));
//		            	  AsyncTask<Void, Void, Void> mmAsyncTask = new mAsyncTask().execute();	                  	  
		            	  
		            	  if (PreferenceConnector.readBoolean(getApplicationContext(), "firstRun", true) 
//		            			  || PreferenceConnector.readBoolean(getApplicationContext(), "refresh", true)
		            			  ) {
	            			  AsyncTask<String, String, Integer> mmAsyncTask = new mAsyncTask().execute();
	            			  if (PreferenceConnector.readBoolean(getApplicationContext(), "firstRun", true)){
		            			  PreferenceConnector.writeBoolean(getApplicationContext(), "firstRun", false);
	            			  }
//	            			  if (PreferenceConnector.readBoolean(getApplicationContext(), "refresh", true)){
//		            			  PreferenceConnector.writeBoolean(getApplicationContext(), "refresh", false);
//	            			  }
	            			  
	            		  }
		            	  else{
	            			  
		            		  looper = PreferenceConnector.readBoolean(getApplicationContext(), "intervalLooper", false);
	            			  if (looper == false){
//				            	  loop = PreferenceConnector.readInteger(getApplicationContext(), "intervalLoop", 0);
				            	  //2
	            				  int seconds = 0;
	            				  Calendar c;
	            				  c = Calendar.getInstance(); 
	            				  seconds = c.get(Calendar.SECOND); //2 00
				            	  if (seconds == 0){
//			            			  PreferenceConnector.writeBoolean(getApplicationContext(), "firstRun", true);
			            			  AsyncTask<String, String, Integer> mmAsyncTask = new mAsyncTask().execute();
			            			  long tempInterval = PreferenceConnector.readLong(getApplicationContext(), "intervalChoose", Constants.INTERVAL_ONE_SECOND);
			            			  PreferenceConnector.writeLong(getApplicationContext(), "intervalTime", tempInterval);

			            			  PreferenceConnector.writeBoolean(getApplicationContext(), "intervalLooper", true);
			            			  
				            	  }
		            		  }
	            			  else if (looper == true){
		            			  AsyncTask<String, String, Integer> mmAsyncTask = new mAsyncTask().execute();
	            			  }
	            		  }
	            	  }
	            	  catch(Exception e){
	            		  
	            	  }

//	              }	              
//             });
	            	  handler.postDelayed(runnable, PreferenceConnector.readLong(getApplicationContext(), "intervalTime", Constants.INTERVAL_ONE_SECOND));
           }
     };
     handler.postDelayed(runnable, PreferenceConnector.readLong(getApplicationContext(), "intervalTime", Constants.INTERVAL_ONE_SECOND));
//     if (looper == false){
//    	 timer.schedule(task, 0, PreferenceConnector.readLong(getApplicationContext(), "intervalTime", Constants.INTERVAL_ONE_SECOND));
//     }
//     else if (looper == true){
//    	 timer.cancel();
//    	 timer.schedule(task, 0, PreferenceConnector.readLong(getApplicationContext(), "intervalTime", Constants.INTERVAL_ONE_SECOND));
//     }
     
	      
	      
	      ////// will do all my stuff here on in the method onStart() or onCreat()?
	
     return START_STICKY;    ///// which return is better to keep the service running untill explicitly killed. contrary to system kill.
	                              ///// http://developer.android.com/reference/android/app/Service.html#START_FLAG_REDELIVERY
	
	      //notes:-//  if you implement onStartCommand() to schedule work to be done asynchronously or in another thread, 
	      //then you may want to use START_FLAG_REDELIVERY to have the system re-deliver an Intent for you so that it does not get lost if your service is killed while processing it	  	
	}
	
	@Override
	  public void onDestroy() {
//		active = false;
//		firstRun = true;
	    stop();
	  }
	
	public void stop(){
	      //if running
	      // stop
	      // make vars as false
	      // do some stopping stuff
		PreferenceConnector.writeBoolean(getApplicationContext(), "firstRun", true);
		PreferenceConnector.writeBoolean(getApplicationContext(), "serviceActive", false);
//		PreferenceConnector.writeBoolean(getApplicationContext(), "refresh", false);
		PreferenceConnector.writeBoolean(getApplicationContext(), "intervalLooper", false);
		PreferenceConnector.writeLong(getApplicationContext(), "intervalTime", Constants.INTERVAL_ONE_SECOND);
//		active = false;
//		firstRun = true;
	    stopForeground(true);
//	    onDestroy();
	     /////// how to call stopSelf() here? or any where else? whats the best way?
	
	}
	
	private class mAsyncTask extends AsyncTask<String, String, Integer>{
		private int mAqi;
		private String mAqiMsg;
		private String mAqiTemp;
		private int mAqiCategory;
		
//		private void initiateVibrate (){
//			//
//			PreferenceConnector.writeBoolean(getApplicationContext(), "vibrateActive", true);
//		}
		
//		private void initiateSound (){
//			//sound
//			
//	        
//	        //
//	        PreferenceConnector.writeBoolean(getApplicationContext(), "soundActive", true);
//		}
        
		private int checkAqiCategory(int x){
			
			if (checkRangeLess(x, Constants.aqiLevel2)){
				return 1;
			}
			else if (checkRangeLess(x, Constants.aqiLevel3)){
				return 2;
			}
			else if (checkRangeLess(x, Constants.aqiLevel4)){
				return 3;
			}
			else if (checkRangeLess(x, Constants.aqiLevel5)){
				return 4;
			}
			else if (checkRangeLess(x, Constants.aqiLevel6)){
				return 5;
			}
			else if (checkRangeLess(Constants.aqiLevel6, x)){
				return 6;
			}
			return 0;
		}
		
		@Override
		protected Integer doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
//			Global.i += 1;
			try {
				doc = Jsoup.connect(PreferenceConnector.readString(getApplicationContext(), "aqiUrlChoose", Constants.AQIURL)).get();
//				Log.d("test", "failedURL");
				
//				ele = doc.select("div.aqi").first();
//				String maqi = doc.getElementById("aqi").text();
				Element mAqiTempt;			
				mAqiMsg = doc.select("div.aqimsg").get(0).text();
				try{
					mAqiTemp = doc.select("div[style=font-size:15px;]").get(0).text();
				}
				catch(Exception e){
					mAqiTemp = doc.select("div[style=font-size:12px;]").get(0).text();
				}
				
				
				
//				String[] parts = mAqiTempt.html().split("<b />"); // Jsoup transforms <b> to <b />.
//				for (String part : parts) {
//				    int colon = part.indexOf(':');
//				    if (colon > -1) {
//				        mAqiTemp = part.substring(colon + 1).trim();
//				    }
//				}
				
				if (mAqiMsg.equalsIgnoreCase("no data")){
					mAqiCategory = 0;
					mAqi = 0;
				}else {
					mAqi = Integer.parseInt(doc.select("div.aqi").get(0).text());
					mAqiCategory = checkAqiCategory(mAqi);
				}
				
				PreferenceConnector.writeInteger(getApplicationContext(), "aqi", mAqi);
				PreferenceConnector.writeInteger(getApplicationContext(), "aqiCategory", mAqiCategory);
//				PreferenceConnector.writeBoolean(getApplicationContext(), "aqiChoose", true);

				PreferenceConnector.writeString(getApplicationContext(), "aqiTemp", mAqiTemp);
				PreferenceConnector.writeString(getApplicationContext(), "aqiMsg", mAqiMsg);
//				PreferenceConnector.writeBoolean(getApplicationContext(), "aqiMsgChoose", true);
//				Global.gAqiMsg = PreferenceConnector.readString(getApplicationContext(), "aqiMsg", null);
				
				Log.d("test", "aqi");
//				Log.d("test", "2");
				
				long millis = System.currentTimeMillis();
				
//				int second = (int) (millis / 1000) % 60 ;
//				int minute = (int) ((millis / (1000*60)) % 60);
//				int hour   = (int) ((millis / (1000*60*60)) % 24);
				
	        	Calendar c2 = Calendar.getInstance();
				c2.setTimeInMillis(millis);
				String updateTimeComplete = new SimpleDateFormat("EEE, dd MMM yyyy, hh:mm a").format(c2.getTime()); //EEE,dd MMM yyyy HH:mm 
//				int hour = c2.get(Calendar.HOUR_OF_DAY);
//				int minute = c2.get(Calendar.MINUTE);
				
				PreferenceConnector.writeString(getApplicationContext(), "updateTimeComplete", updateTimeComplete);
//				PreferenceConnector.writeInteger(getApplicationContext(), "updateTimeHour", hour);
//				PreferenceConnector.writeInteger(getApplicationContext(), "updateTimeMinute", minute);
				
				PreferenceConnector.writeLong(getApplicationContext(), "updateTime", millis);
				
//				JSONObject obj = new JSONObject((String)arg2[0]);
//				PreferenceConnector.writeString(getApplicationContext(), "nim", obj.getString("nim"));
//				PreferenceConnector.writeString(getApplicationContext(), "password", obj.getString("password"));
//				Global.nim = obj.getString("nim");
//				Global.password = obj.getString("password");
//				Utility.showToast(getApplicationContext(), obj.getString("nim") + " Connected");
				serviceBroadcastAqiData(getApplicationContext(), "Load Data Complete");
				
				updateNotification();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				Log.d("test", "failedURL2");
//				Utility.showToast(getApplicationContext(), "Connection Failed.");
//				e.printStackTrace();
				serviceBroadcastFailed(getApplicationContext(), "Connection problem, please check the internet connection and then" +
						" tap the refresh button.");
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			try {
//			if (mAqi >= Global.gMAqiChoose){
//				Global.gVibrateActive = PreferenceConnector.readBoolean(getApplicationContext(), "vibrateActive", true);
				// Vibrate for 300 milliseconds
//				v.vibrate(1000);
//				
//				if (r.isPlaying())
//					r.stop();
//				
//				try {
//					//play notif sound
//			        r.play();
//			        
//			    } catch (Exception e) {}
				
				/*updateNotification();*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				Log.d("test", "failedURL2");
//				Utility.showToast(getApplicationContext(), "Update Failed.");
//				e.printStackTrace();
				serviceBroadcastFailed(getApplicationContext(), "Notification update failed, please tap the refresh button.");
			}
				
//				Log.d("test", "failed onpostexecute");
//			}
				
		}
	};
	
	
}

