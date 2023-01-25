package com.app.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.app.hstapp.MemberActivity;
import com.app.utils.HSTApp;
import com.app.utils.HSTApp.TrackerName;
import com.app.utils.PreferenceManager;
import com.app.utils.URL;
import com.app.utils.Utility;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

/**
 * This async Task is for Get app detail api call
 */
public class GetAppDetail extends AsyncTask<String, Void, String> {

	Context context;
	public static String TAG = "";
	ProgressDialog progressDialog;

	public GetAppDetail(Context context) {
		super();
		this.context = context;
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
			HttpPost httpPost = new HttpPost(URL.GET_APP_DETAIL_URL);
			HttpClient httpclient = new DefaultHttpClient();
			ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("MemberId", PreferenceManager
					.getMemberId(context)));
			list.add(new BasicNameValuePair("GroupId", PreferenceManager
					.getGroupId(context)));
			
			httpPost.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
			Log.v(TAG, "TTT" + list);
			HttpResponse response = httpclient.execute(httpPost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inStream = entity.getContent();
				String result = Utility.convertStreamToString(inStream);
				Log.v("Response", result);
				return result;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Utility.showAlert("Unable to login please try again later.", context);
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		progressDialog.dismiss();
		try {
			if (result != null) {
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getString("status").equals("OK")) {
					Log.v("TTT", "in result");
					JSONObject json = jsonObject.getJSONObject("message");
					// {"BtnColor":"74967F","TextColor":"3232D9","BgColor":"FFA673","LogoFile":"orderedList0.png"}
					PreferenceManager.setButtonColor(context,
							"#" + json.get("BtnColor"));
					PreferenceManager.setTextColor(context,
							"#" + json.get("TextColor"));
					PreferenceManager.setBackgroudColor(context,
							"#" + json.get("BgColor"));
					PreferenceManager.setLogo(context, json.get("LogoFile")
							.toString());
					PreferenceManager.setButtonStartColor(context,
							"#" + json.get("BtnStartColor"));
					PreferenceManager.setButtonEndColor(context,
							"#" + json.get("BtnEndColor"));
					PreferenceManager.setTextHeaderColor(context,
							"#" + json.get("TextHeaderColor"));
				/*	Log.v(TAG,
							"jsonObject"
									+ PreferenceManager.getButtonColor(context)
									+ PreferenceManager.getTextColor(context)
									+ PreferenceManager.getBackgroudColor(context));*/
					Intent intent = new Intent(context, MemberActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
					((Activity) context).finish();
				} else if (jsonObject.getString("status").equals("Error")) {
					// {"status":"Error","message":"Your Account is de-activated"}
					String data = jsonObject.getString("message");
					if (data.equalsIgnoreCase("Your Account is de-activated")) {
						Utility.showAlertWithClick(data, context);
					} else {
						Utility.showAlert(data, context);
					}
				} else {
					Utility.showAlert(
							"Unable to login please try again later.", context);
				}
			} else {
				Utility.showAlert("Unable to login please try again later.",
						context);
			}
		} catch (Exception e) {
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