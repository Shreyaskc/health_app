package com.app.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.app.fragment.MemberDemographicFragment;
import com.app.utils.HSTApp;
import com.app.utils.HSTApp.TrackerName;
import com.app.utils.URL;
import com.app.utils.Utility;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

/**
 * This async Task is for Memberdemographic info api call
 */
public class MemberDemographicInfoTask extends AsyncTask<String, Void, String> {

	public static String TAG = "";
	String url = "";
	Activity context;
	HashMap<String, Object> postData;
	ProgressDialog progressDialog;
	MemberDemographicFragment memberDemographicFragment;

	public MemberDemographicInfoTask(Activity context,
			MemberDemographicFragment memberDemographicFragment,
			HashMap<String, Object> postData) {
		this.context = context;
		this.postData = postData;
		this.memberDemographicFragment = memberDemographicFragment;
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
			HttpPost httppost = new HttpPost(URL.GET_DEMO_GRAPHIC_INFO_URL);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			for (String key : this.postData.keySet()) {
				nvps.add(new BasicNameValuePair(key, this.postData.get(key)
						.toString()));
			}
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
					Log.v("TTT", "in result");
					JSONObject data = jsonObject.getJSONObject("message");
				/*	memberDemographicFragment.txtMemberIdData.setText(data
							.getString("MemberId"));
					memberDemographicFragment.txtGroupIdData.setText(data
							.getString("GroupId"));*/
					// memberDemographicFragment.txtAgeData.setText(data.getString("Age"));
					//Log.v(TAG, "TTTT" + data.getString("Gender"));
					/*memberDemographicFragment.txtGenderData.setText(!data
							.getString("Gender").equals("null") ? data
							.getString("Gender").toUpperCase() : "");
					memberDemographicFragment.txtAddress1Data.setText(!data
							.getString("Address1").equals("null") ? data
							.getString("Address1") : "");
					memberDemographicFragment.txtAddress2Data.setText(!data
							.getString("Address2").equals("null") ? data
							.getString("Address2") : "");
					memberDemographicFragment.txtCityData.setText(!data
							.getString("City").equals("null") ? data
							.getString("City") : "");
					memberDemographicFragment.txtStateData.setText(!data
							.getString("State").equals("null") ? data
							.getString("State") : "");
					memberDemographicFragment.txtZipData.setText(!data
							.getString("Zip").equals("null") ? data
							.getString("Zip") : "");
					memberDemographicFragment.txtHomePhoneData.setText(!data
							.getString("HomePhone").equals("null") ? data
							.getString("HomePhone") : "");
					memberDemographicFragment.txtCellPhoneData.setText(!data
							.getString("CellPhone").equals("null") ? data
							.getString("CellPhone") : "");*/
					String memberGender = "Member Gender : ";
					memberDemographicFragment.txtGenderData.setText(!data
							.getString("Gender").equals("null") ? memberGender+data
							.getString("Gender").toUpperCase() : memberGender);
					String ageString = "Member Age : "; 
					int age = 0;
					String DOB = data.getString("DOB").substring(0,
							data.getString("DOB").indexOf("T"));
					SimpleDateFormat format;
					Date date =null;
					try{
						format = new SimpleDateFormat("yyyy-MM-dd");
						date =  format.parse(DOB);
						age =getAge(date);
					}
					catch (Exception e){
						age =0;
					}
					ageString+=age+"";
				
					memberDemographicFragment.txtAge
							.setText(ageString);
				/*	memberDemographicFragment.txtNameData.setText(data
							.getString("FirstName")
							+ " "
							+ data.getString("LastName"));*/
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
							"Error while Getting Member DemoGraphic Information",
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
	public static int getAge(Date dateOfBirth) {

	    Calendar today = Calendar.getInstance();
	    Calendar birthDate = Calendar.getInstance();

	    int age = 0;

	    birthDate.setTime(dateOfBirth);
	    if (birthDate.after(today)) {
	        throw new IllegalArgumentException("Can't be born in the future");
	    }

	    age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

	    // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year   
	    if ( (birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
	            (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH ))){
	        age--;

	     // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
	    }else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH )) &&
	              (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH ))){
	        age--;
	    }

	    return age;
	}
}
