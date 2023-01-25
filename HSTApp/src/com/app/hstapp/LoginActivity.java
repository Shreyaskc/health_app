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
public class LoginActivity extends Activity {
	EditText editEnterPassword;
	LinearLayout layoutEnterPassword;
	Button btnRegistration,btnLogin;
	TextView txtForgotPassword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Tracker t = ((HSTApp) getApplication()).getTracker(
	            TrackerName.APP_TRACKER);
        t.setScreenName(this.getClass().getName());
        t.send(new HitBuilders.AppViewBuilder().build());
		TelephonyManager telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = telephonyManager.getDeviceId();
		PreferenceManager.setDeviceId(this, deviceId);
		
		editEnterPassword = (EditText) findViewById(R.id.editEnterPassword);
		editEnterPassword.setTypeface(Typeface.DEFAULT);
		editEnterPassword.setTransformationMethod(new PasswordTransformationMethod());
		
		layoutEnterPassword = (LinearLayout) findViewById(R.id.layoutEnterPassword);
		btnRegistration = (Button) findViewById(R.id.btnRegistration);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
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
		Log.v("Login",PreferenceManager.isRegistered(LoginActivity.this) + "TTT"+getResources().getString(R.color.green));
		if(PreferenceManager.isRegistered(LoginActivity.this)){
			btnRegistration.setVisibility(View.GONE);
			btnLogin.setVisibility(View.VISIBLE);
			txtForgotPassword.setVisibility(View.VISIBLE);
			layoutEnterPassword.setVisibility(View.VISIBLE);
			if(PreferenceManager.isLogin(LoginActivity.this)){
				Intent intent = new Intent(LoginActivity.this,MemberActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
//				new GetAppDetail(LoginActivity.this, PreferenceManager.getGroupId(LoginActivity.this)).execute();
			}
		}else{
			btnRegistration.setVisibility(View.VISIBLE);
			btnLogin.setVisibility(View.GONE);
			txtForgotPassword.setVisibility(View.GONE);
			layoutEnterPassword.setVisibility(View.GONE);
//			Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
//			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
//			startActivity(intent);
		}
		
		btnRegistration.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				finish();
				Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
				LoginActivity.this.startActivity(intent);
			}
		});
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Tracker t = ((HSTApp) getApplication()).getTracker(
			            TrackerName.APP_TRACKER);
		        t.send(new HitBuilders.EventBuilder()
		        		.setCategory(getClass().getName())
		        		.setAction("Login Click")
			            .setLabel(getString(R.string.login))
			            .build());
				validateInputs();
			}
		});
		txtForgotPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new ForgotPasswordTask(LoginActivity.this).execute();
			}
		});
	}
	
	/**
	 * To validate the controls 
	 * */
	private void validateInputs() {
		int i = 0;
		if(editEnterPassword.getText().toString().equals("")){
			i++;
			layoutEnterPassword.setBackgroundResource(R.drawable.validation_background);
		}else{
			layoutEnterPassword.setBackgroundResource(R.drawable.normalfield_back);
		}
		
		if(i<=0){
			LinkedHashMap<String, Object> postData = new LinkedHashMap<String, Object>();
			postData.put("Password", editEnterPassword.getText().toString());
			new LoginAsyncTask(LoginActivity.this, postData).execute();
		}else{
			Utility.showAlert("Please fill details", LoginActivity.this);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(PreferenceManager.isRegistered(LoginActivity.this)){
			btnRegistration.setVisibility(View.GONE);
			btnLogin.setVisibility(View.VISIBLE);
			txtForgotPassword.setVisibility(View.VISIBLE);
			layoutEnterPassword.setVisibility(View.VISIBLE);
		}else{
			btnRegistration.setVisibility(View.VISIBLE);
			btnLogin.setVisibility(View.GONE);
			txtForgotPassword.setVisibility(View.GONE);
			layoutEnterPassword.setVisibility(View.GONE);
		}
	}
}
