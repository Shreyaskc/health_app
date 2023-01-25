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

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.app.hstapp.RegistrationActivity;
import com.app.utils.HSTApp;
import com.app.utils.HSTApp.TrackerName;
import com.app.utils.URL;
import com.app.utils.Utility;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

/**
 * This async Task is for Get Agreement api call
 */
public class GetAgreementTask extends AsyncTask<String, Void, String> {

	RegistrationActivity context;
	public static String TAG = "";
	public static String groupId;
	public static String memberId;
	ProgressDialog progressDialog;

	public GetAgreementTask(RegistrationActivity context, String groupId,
			String memberId) {
		super();
		this.context = context;
		TAG = context.getClass().getName();
		GetAgreementTask.groupId = groupId;
		GetAgreementTask.memberId = memberId;
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
			HttpPost httpPost = new HttpPost(URL.GET_AGREEMENT_URL);
			HttpClient httpclient = new DefaultHttpClient();
			ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("MemberId", memberId));
			list.add(new BasicNameValuePair("GroupId", groupId));
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
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		try {
			super.onPostExecute(result);
			progressDialog.dismiss();
			if (result != null) {
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getString("status").equals("Ok")) {
					String agreement = (String) jsonObject.get("message");
					context.showAgreement(agreement);
				} else if (jsonObject.getString("status").equals("Error")) {
					// {"status":"Error","message":"Your Account is de-activated"}
					String data = jsonObject.getString("message");
					Utility.showAlert(data, context);
				} else {
					Utility.showAlert((String) jsonObject.get("message"),
							context);
				}
			}else{
				Utility.showAlert("Error while Register", context); 
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