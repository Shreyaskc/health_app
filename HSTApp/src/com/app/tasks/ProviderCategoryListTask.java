package com.app.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.app.adapter.FindProviderCategory;
import com.app.fragment.ProviderListFragment.OnProviderFragmentClick;
import com.app.model.ProviderCategory;
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
public class ProviderCategoryListTask extends AsyncTask<String, Void, String> {

	public static String TAG = "";
	String url = "";
	Context context;
	ArrayList<ProviderCategory> providerCategoriesList = new ArrayList<ProviderCategory>();
	ProgressDialog progressDialog;
	ListView listFindProvider;
	OnProviderFragmentClick fragmentClick;
	public ProviderCategoryListTask(Context context, ListView listFindProvider, OnProviderFragmentClick onProviderFragmentClick) {
		this.context = context;
		this.listFindProvider = listFindProvider;
		this.fragmentClick = onProviderFragmentClick;
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
			HttpGet httpGet = new HttpGet(URL.PROVIDER_CATEGORY_URL);

			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(httpGet);

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
					Log.v("TTT","...."+jsonArray.getString(0));
					if(jsonArray.length() > 0) {
						try {
							providerCategoriesList.clear();
							for(int i = 0; i<jsonArray.length(); i++) {
								JSONObject categoryjsonObject = jsonArray.getJSONObject(i);
								ProviderCategory category = new ProviderCategory();
								category.setCategoryId(categoryjsonObject.getString("CategoryId"));
								category.setCategoryName(categoryjsonObject.getString("CategoryName"));
								providerCategoriesList.add(category);
							}
						} catch(Exception e) {
							Utility.showAlert("Error while Getting Provider List",
									context);
						}
					}
					FindProviderCategory adapter = new FindProviderCategory(context, providerCategoriesList,fragmentClick);
					listFindProvider.setAdapter(adapter);
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
