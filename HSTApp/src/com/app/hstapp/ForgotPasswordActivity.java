package com.app.hstapp;

import java.util.LinkedHashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tasks.ResetPasswordTask;
import com.app.utils.HSTApp;
import com.app.utils.HSTApp.TrackerName;
import com.app.utils.Utility;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * This activity is for forgot password
 */
public class ForgotPasswordActivity extends Activity {
	EditText  editSecurityAnswer, editEnterPassword,
			editReEnterPassword;
	public LinearLayout  layoutSecurityAnswer,
			layoutForgetPassword, layoutResetPassword, layoutEnterPassword,
			layoutReEnterPassword;
	
	TextView txtCreateSecurityQuestions;
	String securityQuestion,answer;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getIntent() != null){
			securityQuestion = getIntent().getStringExtra("SecurityQuestion");
			answer = getIntent().getStringExtra("Answer");
		}
		setContentView(R.layout.forgot_password);
		Tracker t = ((HSTApp) getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		// Set screen name.
		// Where path is a String representing the screen name.
		t.setScreenName(this.getClass().getName());
		// Send a screen view.
		t.send(new HitBuilders.AppViewBuilder().build());
		
		
		txtCreateSecurityQuestions = (TextView) findViewById(R.id.txtCreateSecurityQuestions);
		txtCreateSecurityQuestions.setText(securityQuestion);
		

		editSecurityAnswer = (EditText) findViewById(R.id.editSecurityAnswer);
		
		layoutSecurityAnswer = (LinearLayout) findViewById(R.id.layoutSecurityAnswer);
		layoutForgetPassword = (LinearLayout) findViewById(R.id.layoutForgetPassword);
		layoutResetPassword = (LinearLayout) findViewById(R.id.layoutResetPassword);
		layoutResetPassword.setVisibility(View.GONE);
		layoutForgetPassword.setVisibility(View.VISIBLE);
		Button btnCancel = (Button) findViewById(R.id.btnCancel);
		Button btnSubmit = (Button) findViewById(R.id.btnSubmit);

		// Reset Controls
		Button btnResetCancel = (Button) findViewById(R.id.btnResetCancel);
		Button btnResetSubmit = (Button) findViewById(R.id.btnResetSubmit);
		editEnterPassword = (EditText) findViewById(R.id.editEnterPassword);
		editReEnterPassword = (EditText) findViewById(R.id.editReEnterPassword);
		editEnterPassword.setTypeface(Typeface.DEFAULT);
		editEnterPassword
				.setTransformationMethod(new PasswordTransformationMethod());
		editReEnterPassword.setTypeface(Typeface.DEFAULT);
		editReEnterPassword
				.setTransformationMethod(new PasswordTransformationMethod());
		layoutEnterPassword = (LinearLayout) findViewById(R.id.layoutEnterPassword);
		layoutReEnterPassword = (LinearLayout) findViewById(R.id.layoutReEnterPassword);
		
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				validateInputs();
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ForgotPasswordActivity.this,
						LoginActivity.class);
				startActivity(intent);
			}
		});
		btnResetCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				layoutResetPassword.setVisibility(View.GONE);
				layoutForgetPassword.setVisibility(View.VISIBLE);
			}
		});
		btnResetSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Tracker t = ((HSTApp) getApplication())
						.getTracker(TrackerName.APP_TRACKER);
				// Build and send an Event.
				t.send(new HitBuilders.EventBuilder()
						.setCategory("ForgotPasswordActivity")
						.setAction("ResetSubmit Click")
						.setLabel(getString(R.string.reset_password)).build());
				validatePasswords();
			}
		});
	}

	/**
	 * To validate the controls
	 */
	private void validatePasswords() {
		int i = 0;
		if (editEnterPassword.getText().toString().equals("")) {
			i++;
			layoutEnterPassword
					.setBackgroundResource(R.drawable.validation_background);
		} else {
			layoutEnterPassword
					.setBackgroundResource(R.drawable.normalfield_back);
		}

		if (editReEnterPassword.getText().toString().equals("")) {
			i++;
			layoutReEnterPassword
					.setBackgroundResource(R.drawable.validation_background);
		} else {
			layoutReEnterPassword
					.setBackgroundResource(R.drawable.normalfield_back);
		}

		if (i <= 0) {
			if (editEnterPassword.getText().toString()
					.equals(editReEnterPassword.getText().toString())) {
				LinkedHashMap<String, Object> postData = new LinkedHashMap<String, Object>();
				postData.put("Password", editEnterPassword.getText().toString());
				new ResetPasswordTask(ForgotPasswordActivity.this, postData)
						.execute();
			} else {
				Utility.showAlert("Password must match",
						ForgotPasswordActivity.this);
			}
		} else {
			Utility.showAlert("Please Enter Password in both field",
					ForgotPasswordActivity.this);
		}
	}

	/**
	 * To validate the controls
	 */
	private void validateInputs() {
		int i = 0;
		

		if (editSecurityAnswer.getText().toString().equals("")) {
			i++;
			layoutSecurityAnswer
					.setBackgroundResource(R.drawable.validation_background);
		} else {
			layoutSecurityAnswer
					.setBackgroundResource(R.drawable.normalfield_back);
		}

		if (i <= 0) {
			if(answer.equals(editSecurityAnswer.getText().toString())){
				layoutResetPassword.setVisibility(View.VISIBLE);
				layoutForgetPassword.setVisibility(View.GONE);
			}else{
				Utility.showAlert("Your answer does not match.",
						ForgotPasswordActivity.this);
			}
		} else {
			Utility.showAlert("Please fill details",
					ForgotPasswordActivity.this);
		}

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
	}

	
}
