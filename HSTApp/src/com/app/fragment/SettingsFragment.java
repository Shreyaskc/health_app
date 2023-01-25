package com.app.fragment;

import java.util.LinkedHashMap;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.app.hstapp.LoginActivity;
import com.app.hstapp.R;
import com.app.tasks.ResetPasswordTask;
import com.app.utils.HSTApp;
import com.app.utils.PreferenceManager;
import com.app.utils.Utility;
import com.app.utils.HSTApp.TrackerName;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * This fragment is for the Settings details
 */
public class SettingsFragment extends Fragment {

	TextView txtEmergencyMessage;
	Button btnChangePassword, btnLogout;
	ToggleButton btnEmergencyMessage;
	Dialog dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.settings, container, false);
		initControls(view);
		return view;
	}

	/**
	 * To initialize the controls View parent view
	 */
	private void initControls(View view) {
		LinearLayout layoutSetting = (LinearLayout) view
				.findViewById(R.id.layoutSetting);
		txtEmergencyMessage = (TextView) view
				.findViewById(R.id.txtEmergencyMessage);
		btnLogout = (Button) view.findViewById(R.id.btnLogout);
		btnChangePassword = (Button) view.findViewById(R.id.btnChangePassword);
		btnEmergencyMessage = (ToggleButton) view
				.findViewById(R.id.btnEmergencyMessage);
		if (PreferenceManager.getIsEmergencyMessage(getActivity())) {
			btnEmergencyMessage.setChecked(true);
		} else {
			btnEmergencyMessage.setChecked(false);
		}
		/*txtEmergencyMessage.setTextColor(Color.parseColor(PreferenceManager
				.getTextHeaderColor(getActivity())));
		btnChangePassword.setTextColor(Color.parseColor(PreferenceManager
				.getTextHeaderColor(getActivity())));
		btnLogout.setTextColor(Color.parseColor(PreferenceManager
				.getTextHeaderColor(getActivity())));*/
		btnEmergencyMessage
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							PreferenceManager.setIsEmergencyMessage(
									getActivity(), true);
						} else {
							PreferenceManager.setIsEmergencyMessage(
									getActivity(), false);
						}
					}
				});
		btnChangePassword.setOnClickListener(new OnClickListener() {

			@SuppressLint("InflateParams")
			@Override
			public void onClick(View v) {
				Tracker t = ((HSTApp) getActivity().getApplication())
						.getTracker(TrackerName.APP_TRACKER);
				//Build and send an Event.
				t.send(new HitBuilders.EventBuilder()
						.setCategory("Settings")
						.setAction("ChangePassword Click")
						.setLabel(
								getActivity().getString(
										R.string.change_password)).build());
				if (dialog == null) {
					dialog = new Dialog(getActivity());
				}
				if (!dialog.isShowing()) {
					dialog.dismiss();
					dialog = null;
					dialog = new Dialog(getActivity());
					dialog.getWindow().setBackgroundDrawable(
							new ColorDrawable(0));
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					@SuppressWarnings("static-access")
					LayoutInflater inflater = (LayoutInflater) getActivity()
							.getBaseContext().getSystemService(
									getActivity().LAYOUT_INFLATER_SERVICE);
					View view = inflater
							.inflate(R.layout.change_password, null);
					final LinearLayout layoutOldEnterPassword = (LinearLayout) view
							.findViewById(R.id.layoutOldEnterPassword);
					final LinearLayout layoutEnterPassword = (LinearLayout) view
							.findViewById(R.id.layoutEnterPassword);
					final LinearLayout layoutReEnterPassword = (LinearLayout) view
							.findViewById(R.id.layoutReEnterPassword);
					ImageView imgClose = (ImageView) view
							.findViewById(R.id.imgClose);
					final EditText editOldEnterPassword = (EditText) view
							.findViewById(R.id.editOldEnterPassword);
					final EditText editEnterPassword = (EditText) view
							.findViewById(R.id.editEnterPassword);
					final EditText editReEnterPassword = (EditText) view
							.findViewById(R.id.editReEnterPassword);
					Button btnResetSubmit = (Button) view
							.findViewById(R.id.btnResetSubmit);

					imgClose.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});

					btnResetSubmit.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Tracker t = ((HSTApp) getActivity()
									.getApplication())
									.getTracker(TrackerName.APP_TRACKER);
							// Build and send an Event.
							t.send(new HitBuilders.EventBuilder()
									.setCategory("Settings")
									.setAction("ResetSubmit Click")
									.setLabel(
											getActivity().getString(
													R.string.reset_password))
									.build());
							int i = 0;
							if (editOldEnterPassword.getText().toString()
									.equals("")) {
								i++;
								layoutOldEnterPassword
										.setBackgroundResource(R.drawable.validation_background);
							} else {
								layoutOldEnterPassword
										.setBackgroundResource(R.drawable.normalfield_back);
							}
							if (editEnterPassword.getText().toString()
									.equals("")) {
								i++;
								layoutEnterPassword
										.setBackgroundResource(R.drawable.validation_background);
							} else {
								layoutEnterPassword
										.setBackgroundResource(R.drawable.normalfield_back);
							}
							if (editReEnterPassword.getText().toString()
									.equals("")) {
								i++;
								layoutReEnterPassword
										.setBackgroundResource(R.drawable.validation_background);
							} else {
								layoutReEnterPassword
										.setBackgroundResource(R.drawable.normalfield_back);
							}
							final boolean validateControls;
							if (i == 0)
								validateControls = true;
							else
								validateControls = false;
							if (validateControls) {
								if (editEnterPassword
										.getText()
										.toString()
										.equals(editReEnterPassword.getText()
												.toString())) {
									LinkedHashMap<String, Object> postData = new LinkedHashMap<String, Object>();
									postData.put("Password", editEnterPassword
											.getText().toString());
									new ResetPasswordTask(getActivity(),
											SettingsFragment.this, true,
											postData).execute();
								} else {
									Utility.showAlert(
											"Please enter same password",
											getActivity());
								}
							} else {
								Utility.showAlert(
										"Please enter all the values",
										getActivity());
							}
						}
					});
					dialog.setContentView(view);
					if (!dialog.isShowing())
						dialog.show();
				}

			}
		});

		btnLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Tracker t = ((HSTApp) getActivity().getApplicationContext())
						.getTracker(TrackerName.APP_TRACKER);
				// Build and send an Event.
				t.send(new HitBuilders.EventBuilder().setCategory("Settings")
						.setAction("Logout Click")
						.setLabel(getActivity().getString(R.string.logout))
						.build());
				PreferenceManager.clearAll(getActivity());
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				getActivity().startActivity(intent);
				getActivity().finish();
			}
		});

	}

	/**
	 * To hide the dialog box
	 */
	public void hideDialog() {
		if (dialog != null)
			dialog.dismiss();
	}
}
