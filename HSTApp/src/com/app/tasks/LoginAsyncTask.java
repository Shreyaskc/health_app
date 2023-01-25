/**
 * 
 */
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
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.app.utils.HSTApp;
import com.app.utils.PreferenceManager;
import com.app.utils.URL;
import com.app.utils.Utility;
import com.app.utils.HSTApp.TrackerName;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

/**
 * This async Task is for login api call
 */
public class LoginAsyncTask extends AsyncTask<String, Void, String> {

	public static String TAG = "";
	String url = "";
	Activity context;
	HashMap<String, Object> postData;
	ProgressDialog progressDialog;

	public LoginAsyncTask(Activity context, HashMap<String, Object> postData) {
		this.context = context;
		this.postData = postData;
		TAG = context.getClass().getName();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("Please wait while login");
		progressDialog.show();
	}

	@Override
	protected String doInBackground(String... url) {
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String deviceId = telephonyManager.getDeviceId();
			Log.v(TAG, "deviceId..." + deviceId + URL.LOGIN_USER_URL);
			HttpPost httppost = new HttpPost(URL.LOGIN_USER_URL);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (String key : this.postData.keySet()) {
				nvps.add(new BasicNameValuePair(key, this.postData.get(key)
						.toString()));
			}
			nvps.add(new BasicNameValuePair("DeviceId", deviceId));
			// StringEntity stringEntity = new StringEntity(nvps.toString(),
			// "UTF-8");
			Log.v(TAG, "TTT" + postData);
			// new UrlEncodedFormEntity(nvps, HTTP.UTF_8)
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = Utility.convertStreamToString(instream);
				return result;
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
					Log.v("TTT", "in result");
					PreferenceManager.setLogin(context, true);
					new GetAppDetail(context).execute();
					// context.finish();
				} else if (jsonObject.getString("status").equals("Error")) {
					String data = jsonObject.getString("message");
					Utility.showAlert(data, context);
				}
			}else{
				Utility.showAlert("Please try again", context);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Utility.showAlert("Please try again", context);
			Tracker t = ((HSTApp) context.getApplication())
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
