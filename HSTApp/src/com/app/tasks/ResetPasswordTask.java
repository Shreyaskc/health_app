package com.app.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.app.fragment.SettingsFragment;
import com.app.hstapp.LoginActivity;
import com.app.utils.HSTApp;
import com.app.utils.PreferenceManager;
import com.app.utils.URL;
import com.app.utils.Utility;
import com.app.utils.HSTApp.TrackerName;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

/**
 * This async Task is for reset password api call
 */
public class ResetPasswordTask extends AsyncTask<String, Void, String> {

	public static String TAG = "";
	String url = "";
	Activity context;
	HashMap<String, Object> postData;
	boolean isDialog = false;
	SettingsFragment settingsFragment;
	ProgressDialog progressDialog;

	public ResetPasswordTask(Activity context, HashMap<String, Object> postData) {
		this.context = context;
		this.postData = postData;
		TAG = context.getClass().getName();
	}

	public ResetPasswordTask(Activity context,
			SettingsFragment settingsFragment, boolean isDialog,
			HashMap<String, Object> postData) {
		this.context = context;
		this.postData = postData;
		this.isDialog = isDialog;
		this.settingsFragment = settingsFragment;
		TAG = context.getClass().getName();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("Please wait...");
		progressDialog.show();
	}

	@Override
	protected String doInBackground(String... url) {
		try {
			String deviceId = PreferenceManager.getDeviceId(context);
			Log.v("TTT", "deviceId..." + deviceId);
			HttpPost httppost = new HttpPost(URL.RESET_PASSWORD_URL);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			for (String key : this.postData.keySet()) {
				nvps.add(new BasicNameValuePair(key, this.postData.get(key)
						.toString()));
			}
			nvps.add(new BasicNameValuePair("DeviceId", deviceId));
			Log.v(TAG, "TTT" + nvps);
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = Utility.convertStreamToString(instream);
				Log.v(TAG, "result" + result);
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getString("status").equals("Ok")) {
					Log.v("TTT", "in result");
					return result;
				}
			}
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		progressDialog.dismiss();
		try {
			if (result != null) {
				Log.v(TAG, "result" + result);
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getString("status").equals("Ok")) {
					if (isDialog) {
						settingsFragment.hideDialog();
						Utility.showAlert(
								"Your Password has been successfully reset.",
								context);
					} else {
						showAlert("Your Password has been successfully reset.",
								context);
					}
				} else if (jsonObject.getString("status").equals("Error")) {
					// {"status":"Error","message":"Your Account is de-activated"}
					String data = jsonObject.getString("message");
					if (data.equalsIgnoreCase("Your Account is de-activated")
							&& isDialog) {
						Utility.showAlertWithClick(data, context);
					} else {
						Utility.showAlert(data, context);
					}
				}
			} else {
				Utility.showAlert("Error while reset password", context);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Exception", "showAlert..." + e.getMessage());
			Tracker t = ((HSTApp) ((Activity) context).getApplication())
					.getTracker(TrackerName.APP_TRACKER);
			t.send(new HitBuilders.ExceptionBuilder()
					.setDescription(
							new StandardExceptionParser(context, null)
									.getDescription(Thread.currentThread()
											.getName(), e)).setFatal(false)
					.build());
		}
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
							Intent intent = new Intent(context,
									LoginActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							context.startActivity(intent);
						}
					});
			AlertDialog alertDialog1 = alert1.create();
			alertDialog1.setCancelable(false);
			alertDialog1.show();
		} catch (Exception e) {
			Log.e("Exception", "showAlert..." + e.getMessage());
			e.printStackTrace();
			Utility.showAlert("Please try again", context);
			Tracker t = ((HSTApp) ((Activity) context).getApplication())
					.getTracker(TrackerName.APP_TRACKER);
			t.send(new HitBuilders.ExceptionBuilder()
					.setDescription(
							new StandardExceptionParser(context, null)
									.getDescription(Thread.currentThread()
											.getName(), e)).setFatal(false)
					.build());
		}
	}

}
