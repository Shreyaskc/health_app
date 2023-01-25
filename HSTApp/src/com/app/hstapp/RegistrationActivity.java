package com.app.hstapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.tasks.GetAgreementTask;
import com.app.tasks.RegistrationAsyncTask;
import com.app.utils.HSTApp;
import com.app.utils.HSTApp.TrackerName;
import com.app.utils.PreferenceManager;
import com.app.utils.Utility;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * This activity is for Registration
 */
public class RegistrationActivity extends Activity implements OnClickListener {
	private GoogleCloudMessaging gcm;
	String regid;
	static final int DATE_DIALOG_ID = 999;
	RelativeLayout layoutInstruction;
	LinearLayout layoutRegistration;
	TextView txtInstruction, txtCreateSecurityQuestions;
//	editAddress1, editAddress2,editZipCode
//	layoutAddress1, layoutAddress2,layoutZipCode
	EditText editMemberId, editGroupId, editCreatePassword, editSecurityAnswer,editFirstName,editLastName,editBirthDate;
	LinearLayout layoutMemberId, layoutGroupId, layoutCreatePassword, layoutSecurityAnswer,layoutFirstName,layoutLastName,layoutBirthDate;
	RelativeLayout layoutCreateSecurityQuestions;
	Button btnNext, btnRegister, btnCancelRegistration, btnCancelAgreement;
	// Spinner spinCreateSecurityQuestions;
	CheckBox chkInstruction;
	ListPopupWindow dropDownPopup;
	boolean isOpenPopup = false;
	public static String TAG;
	private int year;
	private int month;
	private int day;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		TAG = getClass().getName();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		Tracker t = ((HSTApp) getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName(this.getClass().getName());
		t.send(new HitBuilders.AppViewBuilder().build());

		gcm = GoogleCloudMessaging.getInstance(getBaseContext());
		register();

		dropDownPopup = new ListPopupWindow(RegistrationActivity.this);
		dropDownPopup.setVerticalOffset(1);
		dropDownPopup.setHorizontalOffset(1);
		txtCreateSecurityQuestions = (TextView) findViewById(R.id.txtCreateSecurityQuestions);
		final ArrayList<String> stringList = new ArrayList<String>(
				Arrays.asList(getResources().getStringArray(
						R.array.security_question)));

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				dropdownPopupWindow(layoutCreateSecurityQuestions, stringList,
						0);
			}
		}, 1000);

		btnNext = (Button) findViewById(R.id.btnNext);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnCancelRegistration = (Button) findViewById(R.id.btnCancelRegistration);
		btnCancelAgreement = (Button) findViewById(R.id.btnCancelAgreement);
		btnNext.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		btnCancelAgreement.setOnClickListener(this);
		btnCancelRegistration.setOnClickListener(this);
		chkInstruction = (CheckBox) findViewById(R.id.chkInstruction);
		layoutInstruction = (RelativeLayout) findViewById(R.id.layoutInstruction);
		layoutRegistration = (LinearLayout) findViewById(R.id.layoutRegistration);
		editMemberId = (EditText) findViewById(R.id.editMemberId);
		editGroupId = (EditText) findViewById(R.id.editGroupId);
		editFirstName = (EditText) findViewById(R.id.editFirstName);
		editLastName = (EditText) findViewById(R.id.editLastName);
		editBirthDate = (EditText) findViewById(R.id.editBirthDate);
		editCreatePassword = (EditText) findViewById(R.id.editCreatePassword);
		editCreatePassword.setTypeface(Typeface.DEFAULT);
		editCreatePassword
				.setTransformationMethod(new PasswordTransformationMethod());

		txtCreateSecurityQuestions.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isOpenPopup) {
					dropDownPopup.dismiss();
					isOpenPopup = false;
				} else {
					dropdownPopupWindow(layoutCreateSecurityQuestions,
							stringList, -1);
				}
			}
		});

		editSecurityAnswer = (EditText) findViewById(R.id.editSecurityAnswer);
		txtInstruction = (TextView) findViewById(R.id.txtInstruction);
		layoutMemberId = (LinearLayout) findViewById(R.id.layoutMemberId);
		layoutGroupId = (LinearLayout) findViewById(R.id.layoutGroupId);
		layoutFirstName = (LinearLayout) findViewById(R.id.layoutFirstName);
		layoutLastName = (LinearLayout) findViewById(R.id.layoutLastName);
		layoutBirthDate = (LinearLayout) findViewById(R.id.layoutBirthDate);
		layoutCreatePassword = (LinearLayout) findViewById(R.id.layoutCreatePassword);
		layoutCreateSecurityQuestions = (RelativeLayout) findViewById(R.id.layoutCreateSecurityQuestions);
		layoutSecurityAnswer = (LinearLayout) findViewById(R.id.layoutSecurityAnswer);
		editBirthDate.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(v.hasFocus()){
					showDialog(DATE_DIALOG_ID);
				}
			}
		});
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		
		setCurrentDateOnView();
		// set date picker as current date
		DatePickerDialog dialog = new DatePickerDialog(this, mDateSetListener, year, month, day);
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		dialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
		/*final Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
        dialog.getDatePicker().setMinDate(cal.getTimeInMillis());*/
        return dialog;
			
	}
	
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int ayear, int monthOfYear,int dayOfMonth) {
			String date_selected = String.valueOf(monthOfYear + 1) + "/"+ String.valueOf(dayOfMonth) + "/" + String.valueOf(ayear);
			editBirthDate.setText(date_selected);
		}
	};
			
	private void setCurrentDateOnView() {
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * To validate the controls
	 */
	private void validateInputs() {
		int i = 0;
		if (editMemberId.getText().toString().equals("")) {
			i++;
			layoutMemberId
					.setBackgroundResource(R.drawable.validation_background);
		} else {
			layoutMemberId.setBackgroundResource(R.drawable.normalfield_back);
		}

		if (editGroupId.getText().toString().equals("")) {
			i++;
			layoutGroupId
					.setBackgroundResource(R.drawable.validation_background);
		} else {
			layoutGroupId.setBackgroundResource(R.drawable.normalfield_back);
		}

		if (editFirstName.getText().toString().equals("")) {
			i++;
			layoutFirstName
					.setBackgroundResource(R.drawable.validation_background);
		} else {
			layoutFirstName.setBackgroundResource(R.drawable.normalfield_back);
		}

		if (editLastName.getText().toString().equals("")) {
			i++;
			layoutLastName
					.setBackgroundResource(R.drawable.validation_background);
		} else {
			layoutLastName.setBackgroundResource(R.drawable.normalfield_back);
		}

		if (editBirthDate.getText().toString().equals("")) {
			i++;
			layoutBirthDate
					.setBackgroundResource(R.drawable.validation_background);
		} else {
			layoutBirthDate.setBackgroundResource(R.drawable.normalfield_back);
		}

		if (editCreatePassword.getText().toString().equals("")) {
			i++;
			layoutCreatePassword
					.setBackgroundResource(R.drawable.validation_background);
		} else {
			layoutCreatePassword
					.setBackgroundResource(R.drawable.normalfield_back);
		}

		if (editSecurityAnswer.getText().toString().equals("")) {
			i++;
			layoutSecurityAnswer
					.setBackgroundResource(R.drawable.validation_background);
		} else {
			layoutCreateSecurityQuestions
					.setBackgroundResource(R.drawable.normalfield_back);
		}

		if (i <= 0) {
			new GetAgreementTask(RegistrationActivity.this, editGroupId
					.getText().toString(), editMemberId.getText().toString())
					.execute();
		} else {
			Utility.showAlert("Please fill all details",
					RegistrationActivity.this);
		}

	}

	/**
	 * To Show company agreement
	 */
	public void showAgreement(String agreement) {
		txtInstruction.setText(agreement);
		layoutInstruction.setVisibility(View.VISIBLE);
		layoutRegistration.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnNext:
			validateInputs();
			break;
		case R.id.btnRegister:
			if (chkInstruction.isChecked()) {
				Tracker t = ((HSTApp) getApplication())
						.getTracker(TrackerName.APP_TRACKER);
				t.send(new HitBuilders.EventBuilder()
						.setCategory(getClass().getName())
						.setAction("Registration Click")
						.setLabel(getString(R.string.registration)).build());
			
				LinkedHashMap<String, Object> postData = new LinkedHashMap<String, Object>();
				postData.put("MemberId", editMemberId.getText().toString());
				postData.put("GroupId", editGroupId.getText().toString());
				postData.put("FirstName", editFirstName.getText().toString());
				postData.put("LastName", editLastName.getText().toString());
				postData.put("DOB", editBirthDate.getText().toString());
				postData.put("Password", editCreatePassword.getText()
						.toString());
				postData.put("SecurityQ1", txtCreateSecurityQuestions.getText()
						.toString());
				postData.put("Ans1", editSecurityAnswer.getText().toString());
				postData.put("DeviceId", PreferenceManager
						.getDeviceId(RegistrationActivity.this));
				postData.put("Device_Token_Id", PreferenceManager
						.getDeviceGcmId(RegistrationActivity.this));
				postData.put("Device_Type", "android");
				PreferenceManager.setGroupId(RegistrationActivity.this,
						editGroupId.getText().toString());
				PreferenceManager.setMemberId(RegistrationActivity.this,
						editMemberId.getText().toString());
				new RegistrationAsyncTask(RegistrationActivity.this, postData)
						.execute();

			} else {
				Log.v(TAG, "Not Checked");
				Utility.showAlert("You must agree to agreement",
						RegistrationActivity.this);
			}
			break;
		case R.id.btnCancelRegistration:
			onBackPressed();
			break;
		case R.id.btnCancelAgreement:
			layoutInstruction.setVisibility(View.GONE);
			layoutRegistration.setVisibility(View.VISIBLE);
			break;
		default:
			Log.v(TAG, "Default case");
			break;
		}
	}

	/**
	 * To Open drop down view
	 */
	@SuppressLint("NewApi")
	public void dropdownPopupWindow(View anchor,
			final ArrayList<String> STRINGS, int selectedPosition) {
		isOpenPopup = true;
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				RegistrationActivity.this, R.layout.spinnerstyle, STRINGS);
		dropDownPopup.setAdapter(adapter);
		dropDownPopup.setAnchorView(anchor);
		// Log.v("TTT", "setWidth123..." + (anchor.getRight() -
		// anchor.getLeft()));

		FrameLayout fakeParent = new FrameLayout(RegistrationActivity.this);
		if (adapter.getCount() > 6) {
			View item = adapter.getView(0, null, fakeParent);
			item.measure(0, 0);
			dropDownPopup.setHeight((int) (6.6 * item.getMeasuredHeight()));
		} else if (adapter.getCount() > 0) {
			View item = adapter.getView(0, null, fakeParent);
			item.measure(0, 0);
			dropDownPopup.setHeight(LayoutParams.WRAP_CONTENT);
		}

		if (((anchor.getRight() - anchor.getLeft()) != 0)
				&& (Utility.getWidestView(RegistrationActivity.this, adapter) > (anchor
						.getRight() - anchor.getLeft()))) {
			dropDownPopup.setWidth(Utility.getWidestView(
					RegistrationActivity.this, adapter) + 20);
		} else {
			dropDownPopup.setWidth(anchor.getRight() - anchor.getLeft());
		}

		dropDownPopup.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Log.v("On item click","layoutCreateSecurityQuestions " +
				// dropDownPopup.getAnchorView().getId());
				switch (dropDownPopup.getAnchorView().getId()) {
				case R.id.layoutCreateSecurityQuestions:
					// Log.v("TTT","" + dropDownPopup.getAnchorView().getId() +
					// "   " + STRINGS.get(position));
					txtCreateSecurityQuestions.setText(STRINGS.get(position));
					dropDownPopup.dismiss();
					break;
				}
				isOpenPopup = false;
			}
		});
		// if(dropDownPopup.isShowing())
		// dropDownPopup.dismiss();
		// else
		dropDownPopup.show();

		if (selectedPosition >= 0) {
			dropDownPopup.performItemClick(selectedPosition);
		}
	}

	/**
	 * To register gcm id
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void register() {
		new AsyncTask() {
			@SuppressWarnings("unused")
			String msg;

			protected Object doInBackground(final Object... params) {

				String token;
				try {
					// Log.i("TTTT", "TTTT");
					token = gcm.register(getString(R.string.project_number));
					regid = token;
					if (token != null && !token.equals(""))
						PreferenceManager.setDeviceGcmId(
								RegistrationActivity.this, token);
					Log.v("registrationId", "token  " + token);
					return true;
				} catch (IOException e) {
					Log.v("Registration Error", e.getMessage());
					msg = e.getMessage();
					e.printStackTrace();
				}
				return false;
			}

			protected void onPostExecute(Object result) {
				super.onPostExecute(result);
				Boolean b = Boolean.parseBoolean(result.toString());
				if (!b)
					showAlert("Please Check your Internet Connection",
							RegistrationActivity.this);
			};
		}.execute(null, null, null);
	}

	/**
	 * @Purpose Show alert according to the message
	 */
	public static void showAlert(String msg, final Context context) {
		try {
			AlertDialog.Builder alert1 = new AlertDialog.Builder(context);
			alert1.setTitle("Message"); // Set Alert dialog title here
			alert1.setMessage(msg);
			alert1.setCancelable(false);
			alert1.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						dialog.cancel();
					}
				});
			AlertDialog alertDialog1 = alert1.create();
			alertDialog1.setCancelable(false);
			alertDialog1.show();
		} catch (Exception e) {
			Log.e("Exception", "showAlert..." + e.getMessage());
		}
	}
}
