package com.ricky.chinaairpollution;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.ricky.chinaairpollution.R;

import android.os.Bundle;
import android.os.Environment;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemSelectedListener;

public class SettingsActivity extends Activity {
	ToggleButton toggleSoundActive;
	ToggleButton toggleVibrateActive;
	Spinner spinnerCategory;
	Spinner spinnerInterval;
	Spinner spinnerCity;
	Spinner spinnerSound;
	LinearLayout layoutbuttonCity;
	LinearLayout layoutbuttonCategory;
	LinearLayout layoutbuttonInterval;
	LinearLayout layoutbuttonSound;
	TextView textviewCityChoose;
	TextView textviewCategoryChoose;
	TextView textviewIntervalChoose;
	TextView textviewContentSoundChoose;
	
	List<String> list;
	
//	public static Boolean cityChoose = false;
	public static int cityChooseLoop = 0;
	public static int intervalChooseLoop = 0;
	
	private void initializeDefaultSetting(){
		toggleSoundActive.setChecked(PreferenceConnector.readBoolean(getApplicationContext(), "soundActive", true));
		toggleVibrateActive.setChecked(PreferenceConnector.readBoolean(getApplicationContext(), "vibrateActive", true));
		
		spinnerCategory.setSelection(PreferenceConnector.readInteger(getApplicationContext(), "aqiChooseIndex", 0));
		spinnerInterval.setSelection(PreferenceConnector.readInteger(getApplicationContext(), "intervalChooseIndex", 0));
		spinnerSound.setSelection(list.indexOf(PreferenceConnector.readString(getApplicationContext(), "ringtoneChoose", Constants.SOUND_DEFAULT)));
	}
	
	private void initializeChoose(){
		if (PreferenceConnector.readBoolean(getApplicationContext(), "settingNotChoose", true) == true)
			PreferenceConnector.writeBoolean(getApplicationContext(), "settingNotChoose", false);
	}
	
	private void updatePreferenceInteger(String key, int value){
		PreferenceConnector.writeInteger(getApplicationContext(), key, value);
//		initializeChoose();
	}
	private void updatePreferenceLong(String key, long value){
		PreferenceConnector.writeLong(getApplicationContext(), key, value);
//		initializeChoose();
	}
	
	private void updatePreferenceString(String key, String value){
		PreferenceConnector.writeString(getApplicationContext(), key, value);
//		initializeChoose();
	}
	
/*	private void updatePreferenceBoolean(String key, boolean value){
		PreferenceConnector.writeBoolean(getApplicationContext(), key, value);
		initializeChoose();
	}*/
	
	public boolean onOptionsItemSelected(MenuItem item){
//		if(PreferenceConnector.readBoolean(getApplicationContext(), "cityChoose", true))
//			settingActivityBroadcastRefresh(getApplicationContext(),  "Refresh Data");
//	    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
//	    startActivity(Intent.FLAG_ACTIVITY_CLEAR_TOP, 0);
//	    finish();
		onBackPressed();
//		finish();
	    return true;

	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(cityChooseLoop >= 2){
			settingActivityBroadcastRefresh(getApplicationContext(),  "Refresh Data");
		}
		else if (intervalChooseLoop >= 2){
			settingActivityBroadcastRefresh(getApplicationContext(),  "Refresh Data");
		}

		cityChooseLoop = 0;

		intervalChooseLoop = 0;
		
		finish();
		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.menu_setting);
		
		toggleSoundActive = (ToggleButton) findViewById(R.id.toggleButton1);
		toggleVibrateActive = (ToggleButton) findViewById(R.id.toggleButton2);
		spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
		spinnerInterval = (Spinner) findViewById(R.id.spinnerInterval);
		spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
		spinnerSound = (Spinner) findViewById(R.id.spinnerContentSound);
//		buttonSettingClose = (Button) findViewById(R.id.buttonSettingClose);
		layoutbuttonCity = (LinearLayout) findViewById(R.id.layoutbuttonCity);
		layoutbuttonCategory = (LinearLayout) findViewById(R.id.layoutbuttonCategory);
		layoutbuttonInterval = (LinearLayout) findViewById(R.id.layoutbuttonInterval);
		layoutbuttonSound = (LinearLayout) findViewById(R.id.layoutbuttonSound);
		textviewCityChoose = (TextView) findViewById(R.id.tCityChoose);
		textviewCategoryChoose = (TextView) findViewById(R.id.tAqiLevelChoose);
		textviewIntervalChoose = (TextView) findViewById(R.id.tIntervalChoose);
		textviewContentSoundChoose = (TextView) findViewById(R.id.tContentSoundChoose);
		
		File sd = new File(Environment.getExternalStorageDirectory() + "/Ringtones");
	    File[] sdDirList = sd.listFiles();
	    // String[] filenames = getApplicationContext().fileList();
//	    List<File> list = new ArrayList<File>();
	    list = new ArrayList<String>();
	    
	    int i = 0;
	    for(int x = 0; x < sdDirList.length; x++)
	    {
	        if(sdDirList[x].getName().endsWith(".mp3") || 
	        		sdDirList[x].getName().endsWith(".ogg") || 
	        		sdDirList[x].getName().endsWith(".wav"))
	        {
	            // add it to your array adapter
	        	String[] a = sdDirList[x].getName().split("/");
	        	list.add(a[0]);
//	        	list.add(sdDirList[x]);
	        }
	    }
	    
	    ArrayAdapter<String> filenameAdapter = new ArrayAdapter<String>(this, 
	    		android.R.layout.simple_dropdown_item_1line, list);
//	    ArrayAdapter<File> filenameAdapter = new ArrayAdapter<File>(this,
//	            android.R.layout.simple_dropdown_item_1line, list);
	    spinnerSound.setAdapter(filenameAdapter);
		
				
		if (PreferenceConnector.readBoolean(getApplicationContext(), "SettingNotChoose", true) == true){
			initializeDefaultSetting();
		}
		
		layoutbuttonCity.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				spinnerCity.performClick();
			}
		});
		
		layoutbuttonCategory.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				spinnerCategory.performClick();
			}
		});
		
		layoutbuttonInterval.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				spinnerInterval.performClick();
			}
		});
		
		layoutbuttonSound.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				spinnerSound.performClick();
			}
		});
		
		toggleSoundActive.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (toggleSoundActive.isChecked())
					PreferenceConnector.writeBoolean(getApplicationContext(), "soundActive", true);
				else
					PreferenceConnector.writeBoolean(getApplicationContext(), "soundActive", false);
				
			}
		});
		
		toggleVibrateActive.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (toggleVibrateActive.isChecked())
					PreferenceConnector.writeBoolean(getApplicationContext(), "vibrateActive", true);
				else
					PreferenceConnector.writeBoolean(getApplicationContext(), "vibrateActive", false);
				
			}
		});
						
		spinnerCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view1, int pos, long id){
				switch (spinnerCategory.getSelectedItemPosition()){
					case 0 :{
						updatePreferenceInteger("aqiChoose", Constants.aqiLevel1);
						updatePreferenceInteger("aqiChooseIndex", 0);
						/*PreferenceConnector.writeInteger(getApplicationContext(), "aqiChoose", Constants.aqiLevel1);
						PreferenceConnector.writeInteger(getApplicationContext(), "aqiChooseIndex", 0);*/
						break;
					}
					case 1 :{
						updatePreferenceInteger("aqiChoose", Constants.aqiLevel2);
						updatePreferenceInteger("aqiChooseIndex", 1);
						/*PreferenceConnector.writeInteger(getApplicationContext(), "aqiChoose", Constants.aqiLevel2);
						PreferenceConnector.writeInteger(getApplicationContext(), "aqiChooseIndex", 1);*/
						break;
					}
					case 2 :{
						updatePreferenceInteger("aqiChoose", Constants.aqiLevel3);
						updatePreferenceInteger("aqiChooseIndex", 2);
						/*PreferenceConnector.writeInteger(getApplicationContext(), "aqiChoose", Constants.aqiLevel3);
						PreferenceConnector.writeInteger(getApplicationContext(), "aqiChooseIndex", 2);*/
						break;
					}
					case 3 :{
						updatePreferenceInteger("aqiChoose", Constants.aqiLevel4);
						updatePreferenceInteger("aqiChooseIndex", 3);
						/*PreferenceConnector.writeInteger(getApplicationContext(), "aqiChoose", Constants.aqiLevel4);
						PreferenceConnector.writeInteger(getApplicationContext(), "aqiChooseIndex", 3);*/
						break;
					}
					case 4 :{
						updatePreferenceInteger("aqiChoose", Constants.aqiLevel5);
						updatePreferenceInteger("aqiChooseIndex", 4);
						/*PreferenceConnector.writeInteger(getApplicationContext(), "aqiChoose", Constants.aqiLevel5);
						PreferenceConnector.writeInteger(getApplicationContext(), "aqiChooseIndex", 4);*/
						break;
					}
					case 5 :{
						updatePreferenceInteger("aqiChoose", Constants.aqiLevel6);
						updatePreferenceInteger("aqiChooseIndex", 5);
						/*PreferenceConnector.writeInteger(getApplicationContext(), "aqiChoose", Constants.aqiLevel6);
						PreferenceConnector.writeInteger(getApplicationContext(), "aqiChooseIndex", 5);*/
						break;
					}
				}
				initializeChoose();
				PreferenceConnector.writeString(getApplicationContext(), "spinnerCategory", spinnerCategory.getSelectedItem().toString());
				textviewCategoryChoose.setText(spinnerCategory.getSelectedItem().toString());
				
			}
			@Override
            public void onNothingSelected(AdapterView<?> arg1)
            {
//                Log.i("AAA","Nothing S0");
            }
			
		});
		
		spinnerInterval.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch (spinnerInterval.getSelectedItemPosition()){
					case 0 :{
						updatePreferenceLong("intervalChoose", Constants.INTERVAL_ONE_MINUTE);
						updatePreferenceInteger("intervalChooseIndex", 0);
//						PreferenceConnector.writeInteger(getApplicationContext(), "intervalChoose", Constants.INTERVAL_ONE_MINUTE);
						break;
					}
					case 1 :{
						updatePreferenceLong("intervalChoose", Constants.INTERVAL_FIVE_MINUTE);
						updatePreferenceInteger("intervalChooseIndex", 1);
//						PreferenceConnector.writeInteger(getApplicationContext(), "intervalChoose", Constants.INTERVAL_FIVE_MINUTE);
						break;
					}
					case 2 :{
						updatePreferenceLong("intervalChoose", Constants.INTERVAL_FIFTEEN_MINUTE);
						updatePreferenceInteger("intervalChooseIndex", 2);
//						PreferenceConnector.writeInteger(getApplicationContext(), "intervalChoose", Constants.INTERVAL_FIFTEEN_MINUTE);
						break;
					}
					case 3 :{
						updatePreferenceLong("intervalChoose", Constants.INTERVAL_THIRTY_MINUTE);
						updatePreferenceInteger("intervalChooseIndex", 3);
//						PreferenceConnector.writeInteger(getApplicationContext(), "intervalChoose", Constants.INTERVAL_THIRTY_MINUTE);
						break;
					}
					case 4 :{
						updatePreferenceLong("intervalChoose", Constants.INTERVAL_ONE_HOUR);
						updatePreferenceInteger("intervalChooseIndex", 4);
//						PreferenceConnector.writeInteger(getApplicationContext(), "intervalChoose", Constants.INTERVAL_ONE_HOUR);
						break;
					}
					case 5 :{
						updatePreferenceLong("intervalChoose", Constants.INTERVAL_THREE_HOUR);
						updatePreferenceInteger("intervalChooseIndex", 5);
//						PreferenceConnector.writeInteger(getApplicationContext(), "intervalChoose", Constants.INTERVAL_THREE_HOUR);
						break;
					}
					case 6 :{
						updatePreferenceLong("intervalChoose", Constants.INTERVAL_ONE_DAY);
						updatePreferenceInteger("intervalChooseIndex", 6);
//						PreferenceConnector.writeInteger(getApplicationContext(), "intervalChoose", Constants.INTERVAL_ONE_DAY);
						break;
					}
				}
				initializeChoose();
				PreferenceConnector.writeString(getApplicationContext(), "spinnerInterval", spinnerInterval.getSelectedItem().toString());
				textviewIntervalChoose.setText(spinnerInterval.getSelectedItem().toString());
				intervalChooseLoop += 1;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		spinnerCity.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String choosen = spinnerCity.getSelectedItem().toString();
				if (choosen.equalsIgnoreCase("beijing")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_BEIJING);
					updatePreferenceString("aqiCity", Constants.AQICITY_BEIJING);
					updatePreferenceInteger("aqiUrlChooseIndex", 0);
				}
				else if (choosen.equalsIgnoreCase("changzhou")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_CHANGZHOU);
					updatePreferenceString("aqiCity", Constants.AQICITY_CHANGZHOU);
					updatePreferenceInteger("aqiUrlChooseIndex", 1);
				}
				else if (choosen.equalsIgnoreCase("chengdu")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_CHENGDU);
					updatePreferenceString("aqiCity", Constants.AQICITY_CHENGDU);
					updatePreferenceInteger("aqiUrlChooseIndex", 2);
				}
				else if (choosen.equalsIgnoreCase("foshan")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_FOSHAN);
					updatePreferenceString("aqiCity", Constants.AQICITY_FOSHAN);
					updatePreferenceInteger("aqiUrlChooseIndex", 3);
				}
				else if (choosen.equalsIgnoreCase("guangzhou")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_GUANGZHOU);
					updatePreferenceString("aqiCity", Constants.AQICITY_GUANGZHOU);
					updatePreferenceInteger("aqiUrlChooseIndex", 4);
				}
				else if (choosen.equalsIgnoreCase("hangzhou")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_HANGZHOU);
					updatePreferenceString("aqiCity", Constants.AQICITY_HANGZHOU);
					updatePreferenceInteger("aqiUrlChooseIndex", 5);
				}
				else if (choosen.equalsIgnoreCase("hefei")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_HEFEI);
					updatePreferenceString("aqiCity", Constants.AQICITY_HEFEI);
					updatePreferenceInteger("aqiUrlChooseIndex", 6);
				}
				else if (choosen.equalsIgnoreCase("hongkong")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_HONGKONG);
					updatePreferenceString("aqiCity", Constants.AQICITY_HONGKONG);
					updatePreferenceInteger("aqiUrlChooseIndex", 7);
				}
				else if (choosen.equalsIgnoreCase("huaian")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_HUAIAN);
					updatePreferenceString("aqiCity", Constants.AQICITY_HUAIAN);
					updatePreferenceInteger("aqiUrlChooseIndex", 8);
				}
				else if (choosen.equalsIgnoreCase("huzhou")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_HUZHOU);
					updatePreferenceString("aqiCity", Constants.AQICITY_HUZHOU);
					updatePreferenceInteger("aqiUrlChooseIndex", 9);
				}
				else if (choosen.equalsIgnoreCase("jiaxing")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_JIAXING);
					updatePreferenceString("aqiCity", Constants.AQICITY_JIAXING);
					updatePreferenceInteger("aqiUrlChooseIndex", 10);
				}
				else if (choosen.equalsIgnoreCase("lianyungang")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_LIANYUNGANG);
					updatePreferenceString("aqiCity", Constants.AQICITY_LIANYUNGANG);
					updatePreferenceInteger("aqiUrlChooseIndex", 11);
				}
				else if (choosen.equalsIgnoreCase("linyi")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_LINYI);
					updatePreferenceString("aqiCity", Constants.AQICITY_LINYI);
					updatePreferenceInteger("aqiUrlChooseIndex", 12);
				}
				else if (choosen.equalsIgnoreCase("maanshan")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_MAANSHAN);
					updatePreferenceString("aqiCity", Constants.AQICITY_MAANSHAN);
					updatePreferenceInteger("aqiUrlChooseIndex", 13);
				}
				else if (choosen.equalsIgnoreCase("nanjing")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_NANJING);
					updatePreferenceString("aqiCity", Constants.AQICITY_NANJING);
					updatePreferenceInteger("aqiUrlChooseIndex", 14);
				}
				else if (choosen.equalsIgnoreCase("nantong")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_NANTONG);
					updatePreferenceString("aqiCity", Constants.AQICITY_NANTONG);
					updatePreferenceInteger("aqiUrlChooseIndex", 15);
				}
				else if (choosen.equalsIgnoreCase("ningbo")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_NINGBO);
					updatePreferenceString("aqiCity", Constants.AQICITY_NINGBO);
					updatePreferenceInteger("aqiUrlChooseIndex", 16);
				}
				else if (choosen.equalsIgnoreCase("qingdao")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_QINGDAO);
					updatePreferenceString("aqiCity", Constants.AQICITY_QINGDAO);
					updatePreferenceInteger("aqiUrlChooseIndex", 17);
				}
				else if (choosen.equalsIgnoreCase("rizhao")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_RIZHAO);
					updatePreferenceString("aqiCity", Constants.AQICITY_RIZHAO);
					updatePreferenceInteger("aqiUrlChooseIndex", 18);
				}
				else if (choosen.equalsIgnoreCase("shanghai")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_SHANGHAI);
					updatePreferenceString("aqiCity", Constants.AQICITY_SHANGHAI);
					updatePreferenceInteger("aqiUrlChooseIndex", 19);
				}
				else if (choosen.equalsIgnoreCase("shaoxing")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_SHAOXING);
					updatePreferenceString("aqiCity", Constants.AQICITY_SHAOXING);
					updatePreferenceInteger("aqiUrlChooseIndex", 20);
				}
				else if (choosen.equalsIgnoreCase("shenzhen")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_SHENZHEN);
					updatePreferenceString("aqiCity", Constants.AQICITY_SHENZHEN);
					updatePreferenceInteger("aqiUrlChooseIndex", 21);
				}
				else if (choosen.equalsIgnoreCase("suqian")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_SUQIAN);
					updatePreferenceString("aqiCity", Constants.AQICITY_SUQIAN);
					updatePreferenceInteger("aqiUrlChooseIndex", 22);
				}
				else if (choosen.equalsIgnoreCase("taizhoushi")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_TAIZHOUSHI);
					updatePreferenceString("aqiCity", Constants.AQICITY_TAIZHOUSHI);
					updatePreferenceInteger("aqiUrlChooseIndex", 23);
				}
				else if (choosen.equalsIgnoreCase("weifang")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_WEIFANG);
					updatePreferenceString("aqiCity", Constants.AQICITY_WEIFANG);
					updatePreferenceInteger("aqiUrlChooseIndex", 24);
				}
				else if (choosen.equalsIgnoreCase("wuhu")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_WUHU);
					updatePreferenceString("aqiCity", Constants.AQICITY_WUHU);
					updatePreferenceInteger("aqiUrlChooseIndex", 25);
				}
				else if (choosen.equalsIgnoreCase("wuxi")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_WUXI);
					updatePreferenceString("aqiCity", Constants.AQICITY_WUXI);
					updatePreferenceInteger("aqiUrlChooseIndex", 26);
				}
				else if (choosen.equalsIgnoreCase("xuzhou")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_XUZHOU);
					updatePreferenceString("aqiCity", Constants.AQICITY_XUZHOU);
					updatePreferenceInteger("aqiUrlChooseIndex", 27);
				}
				else if (choosen.equalsIgnoreCase("yancheng")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_YANCHENG);
					updatePreferenceString("aqiCity", Constants.AQICITY_YANCHENG);
					updatePreferenceInteger("aqiUrlChooseIndex", 28);
				}
				else if (choosen.equalsIgnoreCase("yangzhou")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_YANGZHOU);
					updatePreferenceString("aqiCity", Constants.AQICITY_YANGZHOU);
					updatePreferenceInteger("aqiUrlChooseIndex", 29);
				}
				else if (choosen.equalsIgnoreCase("zaozhuang")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_ZAOZHUANG);
					updatePreferenceString("aqiCity", Constants.AQICITY_ZAOZHUANG);
					updatePreferenceInteger("aqiUrlChooseIndex", 30);
				}
				else if (choosen.equalsIgnoreCase("zhenjiang")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_ZHENJIANG);
					updatePreferenceString("aqiCity", Constants.AQICITY_ZHENJIANG);
					updatePreferenceInteger("aqiUrlChooseIndex", 31);
				}
				else if (choosen.equalsIgnoreCase("zhoushan")){
					updatePreferenceString("aqiUrlChoose", Constants.AQIURL_ZHOUSHAN);
					updatePreferenceString("aqiCity", Constants.AQICITY_ZHOUSHAN);
					updatePreferenceInteger("aqiUrlChooseIndex", 32);
				}
				
				initializeChoose();
				PreferenceConnector.writeString(getApplicationContext(), "spinnerCity", spinnerCity.getSelectedItem().toString());
				textviewCityChoose.setText(spinnerCity.getSelectedItem().toString());
				
				cityChooseLoop += 1;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
			    
		spinnerSound.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String choosen = spinnerSound.getSelectedItem().toString();
				PreferenceConnector.writeString(getApplicationContext(), "ringtoneChoose", choosen);
				settingBroadcastSound(getApplicationContext(), "Update sound");
				textviewContentSoundChoose.setText(choosen);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
//		if(PreferenceConnector.readBoolean(getApplicationContext(), "cityChoose", true))
//			settingActivityBroadcastRefresh(getApplicationContext(),  "Refresh Data");
//		finish();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		spinnerCity.setSelection(PreferenceConnector.readInteger(getApplicationContext(), "aqiUrlChooseIndex", 0));
		
		spinnerCategory.setSelection(PreferenceConnector.readInteger(getApplicationContext(), "aqiChooseIndex", 0));

		spinnerInterval.setSelection(PreferenceConnector.readInteger(getApplicationContext(), "intervalChooseIndex", 0));
		spinnerSound.setSelection(list.indexOf(PreferenceConnector.readString(getApplicationContext(), "ringtoneChoose", Constants.SOUND_DEFAULT)));
		toggleSoundActive.setChecked(PreferenceConnector.readBoolean(getApplicationContext(), "soundActive", true));
		toggleVibrateActive.setChecked(PreferenceConnector.readBoolean(getApplicationContext(), "vibrateActive", true));
		
		textviewCityChoose.setText(PreferenceConnector.readString(getApplicationContext(), "spinnerCity", "Default city"));
		textviewCategoryChoose.setText(PreferenceConnector.readString(getApplicationContext(), "spinnerCategory", "Default category"));
		textviewIntervalChoose.setText(PreferenceConnector.readString(getApplicationContext(), "spinnerInterval", "Default interval"));
		textviewContentSoundChoose.setText(PreferenceConnector.readString(getApplicationContext(), "ringtoneChoose", "Default ringtone"));
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	private static void settingActivityBroadcastRefresh(Context context, String message) {
		// TODO Auto-generated method stub
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Type", "from-activity-to-refresh"));
		params.add(new BasicNameValuePair("message", message));
		Utility.sendBroadcast(context, params,
				Constants.ACTION_SETTINGSACTIVITY_TO_SERVICE);
	}
	private static void settingBroadcastSound(Context context, String message) {
		// TODO Auto-generated method stub
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Type", "from-setting-sound"));
		params.add(new BasicNameValuePair("message", message));
		Utility.sendBroadcast(context, params,
				Constants.ACTION_SETTINGSACTIVITY_TO_SERVICE);
	}
}
