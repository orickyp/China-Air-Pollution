package com.ricky.chinaairpollution;

import java.util.ArrayList;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView textviewAqi;
	TextView textviewAqiMsg;
	TextView textviewAqiMsg2;
	TextView textviewAqiTemp;
	TextView textviewInfo;
	TextView textviewLink;
	FrameLayout frameAqi;
	FrameLayout frameAqiMsg;
	
//	MenuItem menuStartStop;

	IntentFilter serviceIntentFilter; // For receiving message
	
	//
	ArrayList<String> list;
	//
	
	private static Intent mySericeIntent;
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		/*spinnerCategory.setSelection(PreferenceConnector.readInteger(getApplicationContext(), "aqiChooseIndex", 0));

		spinnerInterval.setSelection(PreferenceConnector.readInteger(getApplicationContext(), "intervalChooseIndex", 0));*/
		
		textviewAqi.setText(Integer.toString(PreferenceConnector.readInteger(getApplicationContext(), "aqi", 0)));
		textviewAqiMsg.setText(PreferenceConnector.readString(getApplicationContext(), "aqiMsg", null));
		textviewAqiMsg2.setText(PreferenceConnector.readString(getApplicationContext(), "updateTimeComplete", null));
		textviewAqiTemp.setText(PreferenceConnector.readString(getApplicationContext(), "aqiTemp", null));
		textviewInfo.setText("AQI Info : " + PreferenceConnector.readString(getApplicationContext(), "aqiCity", null));
		
		drawColor(PreferenceConnector.readInteger(getApplicationContext(), "frameColor", 0),
				PreferenceConnector.readInteger(getApplicationContext(), "textColor", 0));
		
		/*toggleSoundActive.setChecked(PreferenceConnector.readBoolean(getApplicationContext(), "soundActive", true));
		toggleVibrateActive.setChecked(PreferenceConnector.readBoolean(getApplicationContext(), "vibrateActive", true));*/
		
		/*if (TheService.active) {
//			mainButton.setText("Disable");
			menuStartStop.setIcon(android.R.drawable.ic_media_pause);
		}else {
//			mainButton.setText("Enable");
			menuStartStop.setIcon(android.R.drawable.ic_media_play);
		}*/
//		if (PreferenceConnector.readBoolean(getApplicationContext(), "", defValue))
		super.onResume();
	}
	
	private void initializeDefaultSetting(){
		PreferenceConnector.writeInteger(getApplicationContext(), "aqi", 0);
		PreferenceConnector.writeString(getApplicationContext(), "aqiMsg", "");
		PreferenceConnector.writeString(getApplicationContext(), "aqiTemp", "");
		PreferenceConnector.writeString(getApplicationContext(), "updateTimeComplete", "");
//		PreferenceConnector.writeInteger(getApplicationContext(), "updateTimeHour", 0); 
//		PreferenceConnector.writeInteger(getApplicationContext(), "updateTimeMinute", 0);
		
		PreferenceConnector.writeString(getApplicationContext(), "aqiUrlChoose", Constants.AQIURL);
		PreferenceConnector.writeInteger(getApplicationContext(), "aqiUrlChooseIndex", 0);
		PreferenceConnector.writeString(getApplicationContext(), "aqiCity", Constants.AQICITY);
		PreferenceConnector.writeInteger(getApplicationContext(), "aqiChoose", Constants.aqiLevel3);
		PreferenceConnector.writeInteger(getApplicationContext(), "aqiChooseIndex", 2);
		PreferenceConnector.writeString(getApplicationContext(), "ringtoneChoose", Constants.SOUND_DEFAULT);
		PreferenceConnector.writeLong(getApplicationContext(), "intervalChoose", Constants.INTERVAL_FIVE_MINUTE);
		PreferenceConnector.writeInteger(getApplicationContext(), "intervalChooseIndex", 1);
//		PreferenceConnector.writeString(getApplicationContext(), "ringtoneChooseIndex", Constants.SOUND_DEFAULT);
		PreferenceConnector.writeBoolean(getApplicationContext(), "serviceActive", false);
		PreferenceConnector.writeBoolean(getApplicationContext(), "firstRun", true);
		PreferenceConnector.writeBoolean(getApplicationContext(), "refresh", false);
		
		PreferenceConnector.writeBoolean(getApplicationContext(), "soundActive", true);
		PreferenceConnector.writeBoolean(getApplicationContext(), "vibrateActive", true);
		PreferenceConnector.writeBoolean(getApplicationContext(), "SettingNotChoose", false);
		
//		PreferenceConnector.writeBoolean(getApplicationContext(), "cityChoose", false);
		
		PreferenceConnector.writeString(getApplicationContext(), "spinnerCity", "Beijing");
		PreferenceConnector.writeString(getApplicationContext(), "spinnerCategory", "Unhealthy for Sensitive Groups : 101 - 150");
		PreferenceConnector.writeString(getApplicationContext(), "spinnerInterval", "5 minutes");
		
		refreshMainView();
		
		
		/*toggleSoundActive.setChecked(true);
		toggleVibrateActive.setChecked(true);*/
//		toggleSoundActive.setChecked(PreferenceConnector.readBoolean(getApplicationContext(), "soundActive", true));
//		toggleVibrateActive.setChecked(PreferenceConnector.readBoolean(getApplicationContext(), "vibrateActive", true));
//		
//		spinnerCategory.setSelection(PreferenceConnector.readInteger(getApplicationContext(), "aqiChooseIndex", 0));
//		spinnerInterval.setSelection(PreferenceConnector.readInteger(getApplicationContext(), "intervalChooseIndex", 0));
	}
	
//	private void initializeChoose(){
//		if (PreferenceConnector.readBoolean(getApplicationContext(), "settingNotChoose", true) == true)
//			PreferenceConnector.writeBoolean(getApplicationContext(), "settingNotChoose", false);
//	}
	
//	private void updatePreferenceInteger(String key, int value){
//		PreferenceConnector.writeInteger(getApplicationContext(), key, value);
//		initializeChoose();
//	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		
		initializeVariables();
		//delayedHide(100);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		mainButton = (Button) findViewById(R.id.main_button);
		/*toggleSoundActive = (ToggleButton) findViewById(R.id.toggleButton1);
		toggleVibrateActive = (ToggleButton) findViewById(R.id.toggleButton2);
		spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
		spinnerInterval = (Spinner) findViewById(R.id.spinnerInterval);*/
		textviewAqi = (TextView) findViewById(R.id.ttAqi);
		textviewAqiMsg = (TextView) findViewById(R.id.ttAqiMsg);
		textviewAqiMsg2 = (TextView) findViewById(R.id.ttAqiMsg2);
		textviewAqiTemp = (TextView) findViewById(R.id.ttAqiTemp);
		frameAqi = (FrameLayout) findViewById(R.id.frameAqi);
		frameAqiMsg = (FrameLayout) findViewById(R.id.frameAqiMsg);
		textviewInfo = (TextView) findViewById(R.id.textViewInfo);
//		menuStartStop = (MenuItem) findViewById(R.id.menuStartStop);
		textviewLink = (TextView) findViewById(R.id.textViewLink);		
		
		createOriData();
		
		textviewLink.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url = Constants.oriUrl;
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});
		
		if (PreferenceConnector.readBoolean(getApplicationContext(), "SettingNotChoose", true) == true){
			initializeDefaultSetting();
		}
		
		mySericeIntent = new Intent(this, TheService.class);
		
//		textviewLink
		startMyForGroundService();
	}
	
	private void createOriData() {
		// TODO Auto-generated method stub 
        textviewLink.setText(getString(R.string.oridata1));
        textviewLink.append(Html.fromHtml(getString(R.string.orilink1)));
        textviewLink.append(getString(R.string.oridata2));
	}

	private void initializeVariables(){
		serviceIntentFilter = new IntentFilter();
        serviceIntentFilter.addAction(Constants.ACTION_SERVICE_TO_MAINACTIVITY);
        serviceIntentFilter.addAction(Constants.ACTION_EXITACTIVITY_TO_MAINACTIVITY);
        getApplicationContext().registerReceiver(serviceIntentReceiver, serviceIntentFilter);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
				
//	    if (TheService.active) {
////			mainButton.setText("Disable");
//			menu.findItem(R.id.menuStart).setVisible(false);
//			menu.findItem(R.id.menuStop).setVisible(true);
//		    
////			item.setIcon(android.R.drawable.ic_media_pause);
//		}else {
////			mainButton.setText("Enable");
//			menu.findItem(R.id.menuStop).setVisible(false);
//			menu.findItem(R.id.menuStart).setVisible(true);
////			item.setIcon(android.R.drawable.ic_media_play);
//		}
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
    	switch (item.getItemId()) {
    		// rest of switch statement as before
	    	case R.id.menuSetting :{
	    		i = new Intent(MainActivity.this, SettingsActivity.class);
    			startActivity(i);
	    		break;
	    	}
    		case R.id.menuCredit :{
    			i = new Intent(MainActivity.this, CreditActivity.class);
    			startActivity(i);
    			break;
    		}
    		case R.id.menuExit :{
    			i = new Intent(MainActivity.this, ExitActivity.class);
    			startActivity(i);
    			break;
    		}
    		case R.id.menuRefresh :{
    			homeActivityBroadcastRefresh(getApplicationContext(),  "Refresh Data");
    			break;
    		}
    		case R.id.menuStart :{
//    			if (!TheService.active){
    			startMyForGroundService();
//    			item.setVisible(false);
    			
//    				item.setIcon(android.R.drawable.ic_media_pause);
//    			}
//    			else if(TheService.active){
//    				stopMyForGroundService();
//    				item.setIcon(android.R.drawable.ic_media_play);
//    			}
    			break;
    		}
    		case R.id.menuStop :{
    			stopMyForGroundService();
//    			item.setVisible(false);
    		}
    	}
    	invalidateOptionsMenu();
    	return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (PreferenceConnector.readBoolean(getApplicationContext(), "serviceActive", false)) {
			menu.findItem(R.id.menuStart).setVisible(false);
			menu.findItem(R.id.menuStop).setVisible(true);
		}
		else if (!PreferenceConnector.readBoolean(getApplicationContext(), "serviceActive", true)){
			menu.findItem(R.id.menuStop).setVisible(false);
			menu.findItem(R.id.menuStart).setVisible(true);
		}
		return true;
    }
	
	private void startMyForGroundService(){
		if (PreferenceConnector.readBoolean(getApplicationContext(), "serviceActive", false) == false){
			Utility.showToast(getApplicationContext(), "Service Started");
			startService(mySericeIntent);
		}
	}
	private void stopMyForGroundService(){
	    stopService(mySericeIntent);
	                          /////// is this a better approach?. stopService(new Intent(this, TheService.class));          
	                          /////// or making Intent mySericeIntent = new Intent(this, TheService.class);
	                          /////// and making start and stop methods use the same?

	                          /////// how to call stopSelf() here? or any where else? whats the best way?
	}
	
	private void drawColor(int frame, int text){
		frameAqi.setBackgroundColor(frame);
		textviewAqi.setTextColor(text);
		textviewAqiMsg.setTextColor(frame);
	}
	
	private BroadcastReceiver serviceIntentReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Bundle extras = intent.getExtras();
			int aqiCategory;
			aqiCategory = PreferenceConnector.readInteger(getApplicationContext(), "aqiCategory", 0);
			if (extras != null) {
				JSONObject obj;
				if (extras.getString("Type").equalsIgnoreCase("from-service-aqi-data")) {
					textviewAqi.setText(Integer.toString(PreferenceConnector.readInteger(getApplicationContext(), "aqi", 0)));
					textviewAqiMsg.setText(PreferenceConnector.readString(getApplicationContext(), "aqiMsg", null));
					textviewAqiMsg2.setText(PreferenceConnector.readString(getApplicationContext(), "updateTimeComplete", null));
					textviewAqiTemp.setText(PreferenceConnector.readString(getApplicationContext(), "aqiTemp", null));
					textviewInfo.setText("AQI Info : " + PreferenceConnector.readString(getApplicationContext(), "aqiCity", null));
					
					switch (aqiCategory){
						case 0 :{
							PreferenceConnector.writeInteger(getApplicationContext(), "frameColor", Constants.COLOR_WHITE);
							PreferenceConnector.writeInteger(getApplicationContext(), "textColor", Constants.COLOR_BLACK_LIGHT);
							
							drawColor(Constants.COLOR_WHITE, Constants.COLOR_GREY_DARK);
							break;
						}
						case 1 :{
							PreferenceConnector.writeInteger(getApplicationContext(), "frameColor", Constants.COLOR_GREEN);
							PreferenceConnector.writeInteger(getApplicationContext(), "textColor", Constants.COLOR_WHITE);
							
							drawColor(Constants.COLOR_GREEN, Constants.COLOR_WHITE);
							break;
						}
						case 2 :{
							PreferenceConnector.writeInteger(getApplicationContext(), "frameColor", Constants.COLOR_YELLOW);
							PreferenceConnector.writeInteger(getApplicationContext(), "textColor", Constants.COLOR_BLACK_LIGHT);
							
							drawColor(Constants.COLOR_YELLOW, Constants.COLOR_BLACK_LIGHT);
							break;
						}
						case 3 :{
							PreferenceConnector.writeInteger(getApplicationContext(), "frameColor", Constants.COLOR_ORANGE);
							PreferenceConnector.writeInteger(getApplicationContext(), "textColor", Constants.COLOR_BLACK_LIGHT);
							
							drawColor(Constants.COLOR_ORANGE, Constants.COLOR_BLACK_LIGHT);
							break;
						}
						case 4 :{
							PreferenceConnector.writeInteger(getApplicationContext(), "frameColor", Constants.COLOR_PURPLE_RED_LIGHT);
							PreferenceConnector.writeInteger(getApplicationContext(), "textColor", Constants.COLOR_WHITE);
							
							drawColor(Constants.COLOR_PURPLE_RED_LIGHT, Constants.COLOR_WHITE);
							break;
						}
						case 5 :{
							PreferenceConnector.writeInteger(getApplicationContext(), "frameColor", Constants.COLOR_PURPLE_BLUE_DARK);
							PreferenceConnector.writeInteger(getApplicationContext(), "textColor", Constants.COLOR_WHITE);
							
							drawColor(Constants.COLOR_PURPLE_BLUE_DARK, Constants.COLOR_WHITE);
							break;
						}
						case 6 :{
							PreferenceConnector.writeInteger(getApplicationContext(), "frameColor", Constants.COLOR_PURPLE_RED_DARK);
							PreferenceConnector.writeInteger(getApplicationContext(), "textColor", Constants.COLOR_WHITE);
							
							drawColor(Constants.COLOR_PURPLE_RED_DARK, Constants.COLOR_WHITE);
							break;
						}
						
					}
					//					try {
//						obj = new JSONObject(extras.getString("message"));
//						Toast.makeText(getApplicationContext(), obj.getString("key"), Toast.LENGTH_SHORT).show();
//					} catch (JSONException e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
				else if(extras.getString("Type").equalsIgnoreCase("from-exitactivity-for-exit")){
					stopMyForGroundService();
					finish();
					Utility.showToast(getApplicationContext(), "Service Closed.\nProgram Exit...");
				}
				else if (extras.getString("Type").equalsIgnoreCase("from-service-to-refresh")) {
					refreshMainView();
				}
			}
		}	
	};
	
	private void refreshMainView(){
		textviewAqi.setText("");
		frameAqi.setBackgroundColor(Constants.COLOR_WHITE);
		textviewAqiMsg.setText("Loading Data...");
		textviewAqiMsg.setTextColor(Constants.COLOR_WHITE);
		textviewAqiMsg2.setText("");
		textviewAqiTemp.setText("");
	}
	
	private static void homeActivityBroadcastRefresh(Context context, String message) {
		// TODO Auto-generated method stub
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Type", "from-activity-to-refresh"));
		params.add(new BasicNameValuePair("message", message));
		Utility.sendBroadcast(context, params,
				Constants.ACTION_MAINACTIVITY_TO_SERVICE);
	}


}
