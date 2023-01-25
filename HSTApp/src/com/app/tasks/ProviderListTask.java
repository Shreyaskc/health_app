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
import android.os.AsyncTask;
import android.util.Log;

import com.app.fragment.FindProviderByNameFragment;
import com.app.fragment.FindProviderByNameFragment.OnProviderSearchClick;
import com.app.model.Provider;
import com.app.model.ProviderSearch;
import com.app.utils.HSTApp;
import com.app.utils.HSTApp.TrackerName;
import com.app.utils.URL;
import com.app.utils.Utility;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * This async Task is for provider list api call
 */
public class ProviderListTask extends AsyncTask<String, Void, String> {

	public static String TAG = "";
	String url = "";
	Activity context;
	HashMap<String, Object> postData;
	ProgressDialog progressDialog;
	OnProviderSearchClick onProviderSearchClick;
	FindProviderByNameFragment findProviderByNameFragment;
	ProviderSearch providerSearch;
	public ProviderListTask(Activity context,
			FindProviderByNameFragment findProviderByNameFragment,
			HashMap<String, Object> postData, OnProviderSearchClick onProviderSearchClick, ProviderSearch providerSearch) {
		this.context = context;
		this.findProviderByNameFragment = findProviderByNameFragment;
		this.postData = postData;
		this.onProviderSearchClick = onProviderSearchClick;
		this.providerSearch = providerSearch;
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
			HttpPost httppost = new HttpPost(URL.PROVIDER_LIST_URL);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			for (String key : this.postData.keySet()) {
				nvps.add(new BasicNameValuePair(key, this.postData.get(key)
						.toString()));
			}
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
					Gson gson = new Gson();
					ArrayList<Provider> providerList = gson.fromJson(
							jsonObject.getString("message"),
							new TypeToken<List<Provider>>() {
							}.getType());
					onProviderSearchClick.OnProviderSearch(providerList,providerSearch);
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

}
