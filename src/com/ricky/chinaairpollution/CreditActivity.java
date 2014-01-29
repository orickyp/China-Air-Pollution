package com.ricky.chinaairpollution;

import com.ricky.chinaairpollution.R;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CreditActivity extends Activity {
	Button buttonClose;
	TextView ttCredit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_credit);
		
		ttCredit = (TextView) findViewById(R.id.ttCredit);
		
        ttCredit.setText(getString(R.string.about_content1_2));
//        ttCredit.append(Html.fromHtml(getString(R.string.about_content_link1)));
//        ttCredit.append(getString(R.string.about_content2));
        ttCredit.append(Html.fromHtml(getString(R.string.about_content_link2)));
        ttCredit.append(getString(R.string.about_content3));
        ttCredit.append(Html.fromHtml(getString(R.string.about_content_link3)));
        ttCredit.setMovementMethod(LinkMovementMethod.getInstance());
		
		buttonClose = (Button) findViewById(R.id.buttonClose);
		buttonClose.setOnClickListener(new View.OnClickListener(){

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
		getMenuInflater().inflate(R.menu.credit, menu);
		return true;
	}

}
