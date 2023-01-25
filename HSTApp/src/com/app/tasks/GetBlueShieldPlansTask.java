package com.app.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.app.fragment.SpecificProviderListFragment;
import com.app.utils.HSTApp;
import com.app.utils.HSTApp.TrackerName;
import com.app.utils.URL;
import com.app.utils.Utility;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

/**
 * This async Task is for provider list api call
 */
public class GetBlueShieldPlansTask extends AsyncTask<String, Void, String> {

	public static String TAG = "";
	String url = "";
	Context context;
	ProgressDialog progressDialog;
	String doctorId;
	SpecificProviderListFragment specificProviderListFragment;
	
	public GetBlueShieldPlansTask(Context context,String doctorId,SpecificProviderListFragment specificProviderListFragment) {
		this.context = context;
		this.doctorId = doctorId;
		TAG = context.getClass().getName();
		this.specificProviderListFragment = specificProviderListFragment; 
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
			HttpPost httppost = new HttpPost(URL.BLUE_SHIELD_PLANS_URL);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			nvps.add(new BasicNameValuePair("DoctorID", doctorId));
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = Utility.convertStreamToString(instream);
				Log.v(TAG, "result" + result);
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
		if (result != null) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getString("status").equals("OK")) {
					JSONArray  jsonArray = jsonObject.getJSONArray("message");
					ArrayList<String> arrayList = new ArrayList<String>();
					for(int i=0;i<jsonArray.length();i++){
						arrayList.add(((JSONObject)jsonArray.get(i)).get("PlanName").toString());
					}
					specificProviderListFragment.onNextClick.OnBlueShiledPlans(arrayList);
					Log.v("TTT","...."+jsonArray.getString(0));
					Log.v("TTT","" + jsonObject.toString());
				} else if (jsonObject.getString("status").equals("Error")) {
					String data = jsonObject.getString("message");
					if (data.equalsIgnoreCase("Your Account is de-activated")) {
						Utility.showAlertWithClick(data, context);
					} else {
						Utility.showAlert(data, context);
					}
				} else {
					Utility.showAlert("Error while Getting Provider List",
							context);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Utility.showAlert("Please try again", context);
				Tracker t = ((HSTApp) context.getApplicationContext())
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
}
