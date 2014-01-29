package com.ricky.chinaairpollution;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.ricky.chinaairpollution.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ExitActivity extends Activity {
	Button btnExitOk;
	Button btnExitCancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_exit);
		
		btnExitOk = (Button) findViewById(R.id.btnExitOk);
		btnExitCancel = (Button) findViewById(R.id.btnExitCancel);
		
		btnExitOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				serviceBroadcastExit(getApplicationContext(), "Exit!");
				finish();
			}
		});
		
		btnExitCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exit, menu);
		return true;
	}
	
	private static void serviceBroadcastExit(Context context, String message) {
		// TODO Auto-generated method stub
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Type", "from-exitactivity-for-exit"));
		params.add(new BasicNameValuePair("message", message));
		Utility.sendBroadcast(context, params,
				Constants.ACTION_EXITACTIVITY_TO_MAINACTIVITY);
	}

}
