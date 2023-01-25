package com.app.hstapp;

import java.util.LinkedHashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.tasks.ForgotPasswordTask;
import com.app.tasks.LoginAsyncTask;
import com.app.utils.HSTApp;
import com.app.utils.PreferenceManager;
import com.app.utils.Utility;
import com.app.utils.HSTApp.TrackerName;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * This activity is for login of user 
 */
public class SplashLoginActivity extends Activity {

	Button btnLogin;
	TextView notRegistered;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_login);
		
		btnLogin = (Button) findViewById(R.id.btnSplashLogin);
		Tracker t = ((HSTApp) getApplication()).getTracker(
	            TrackerName.APP_TRACKER);
        t.setScreenName(this.getClass().getName());
        t.send(new HitBuilders.AppViewBuilder().build());
		TelephonyManager telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = telephonyManager.getDeviceId();
		PreferenceManager.setDeviceId(this, deviceId);
		if (!Utility.isNetworkAvailable(this)) {
			Log.v("TTT", " not isnetAvailable");
			new AlertDialog.Builder(this)
					.setMessage("Internet connection is not available Whould u like to ON Internet?")
					.setCancelable(false)
					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int which) {
									dialog.cancel();
									Intent intent = new Intent(Settings.ACTION_SETTINGS);
									startActivity(intent);
//									System.exit(0);
								}
							})
					.setNegativeButton("No",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int which) {
									dialog.cancel();
//									System.exit(0);
								}
							}).show();
		}
		Log.v("Login",PreferenceManager.isRegistered(SplashLoginActivity.this) + "TTT"+getResources().getString(R.color.green));
		
	}
	
	public void toLoginPage(View view) {
		if (view.getId() == R.id.btnSplashLogin) {
			Intent intent  = new Intent(this,LoginActivity.class);
			startActivity(intent);
		}
	}
	public void notRegistered(View view) {
		if (view.getId() == R.id.notRegisteredButton) {
			Intent intent  = new Intent(this,LoginActivity.class);
			startActivity(intent);
		}
	}

}
