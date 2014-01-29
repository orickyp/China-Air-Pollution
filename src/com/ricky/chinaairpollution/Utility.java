package com.ricky.chinaairpollution;

import java.util.ArrayList;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Utility {
	
	public static void showToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		
		if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
//                Log.e("Network", "Network type: " + networkInfo.getTypeName() +
//                        " Network subtype: " + networkInfo.getSubtypeName());
            }
            else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
//                Log.e("Network", "Network type: " + networkInfo.getTypeName() +
//                        " Network subtype: " + networkInfo.getSubtypeName());
            }
            else if (networkInfo.getType() == ConnectivityManager.TYPE_WIMAX) {
//                Log.e("Network", "Network type: " + networkInfo.getTypeName() +
//                        " Network subtype: " + networkInfo.getSubtypeName());
            }
            else {
//            	Log.e("Network", "Network type: " + networkInfo.getTypeName() +
//                        " Network subtype: " + networkInfo.getSubtypeName());
            }
            return true;
        }
		else {
//			Log.e("Network", "Network connection lost");
			return false;
		}
	}
	
	public static boolean isOnline(Intent intent) {
		NetworkInfo networkInfo = (NetworkInfo)intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            	Log.e("Network", "Connection changed to " + networkInfo.getTypeName());
//                Log.e("Network", "Network type: " + networkInfo.getTypeName() +
//                        " Network subtype: " + networkInfo.getSubtypeName());
            }
            else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            	Log.e("Network", "Connection changed to " + networkInfo.getTypeName());
//                Log.e("Network", "Network type: " + networkInfo.getTypeName() +
//                        " Network subtype: " + networkInfo.getSubtypeName());
            }
            else if (networkInfo.getType() == ConnectivityManager.TYPE_WIMAX) {
            	Log.e("Network", "Connection changed to " + networkInfo.getTypeName());
//                Log.e("Network", "Network type: " + networkInfo.getTypeName() +
//                        " Network subtype: " + networkInfo.getSubtypeName());
            }
            else {
            	Log.e("Network", "Connection changed to " + networkInfo.getTypeName());
//            	Log.e("Network", "Network type: " + networkInfo.getTypeName() +
//                        " Network subtype: " + networkInfo.getSubtypeName());
            }
            return true;
        }
        else {
            Log.e("Network", "Network connection lost");
            return false;
        }
	}
	
	
	public static void sendBroadcast(Context context, ArrayList<NameValuePair> params, String action) {
		Intent broadcastIntent = new Intent();
		Bundle extras = new Bundle();
		for(NameValuePair p : params) {
    		extras.putString(p.getName(), p.getValue());
    	}
		broadcastIntent.putExtras(extras);
        broadcastIntent.setAction(action);            
        context.sendBroadcast(broadcastIntent);
	}
	

}
