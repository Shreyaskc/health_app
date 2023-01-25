package com.app.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.app.fragment.MemberInsuranceInformationfragment;
import com.app.utils.HSTApp;
import com.app.utils.HSTApp.TrackerName;
import com.app.utils.URL;
import com.app.utils.Utility;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

/**
 * This async Task is for Member insurance api call
 */
public class MemberInsuranceInfoTask extends AsyncTask<String, Void, String> {

	public static String TAG = "";
	String url = "";
	Activity context;
	HashMap<String, Object> postData;
	MemberInsuranceInformationfragment memberInsuranceInformationfragment;
	ProgressDialog progressDialog;

	public MemberInsuranceInfoTask(
			Activity context,
			MemberInsuranceInformationfragment memberInsuranceInformationfragment,
			HashMap<String, Object> postData) {
		this.context = context;
		this.postData = postData;
		this.memberInsuranceInformationfragment = memberInsuranceInformationfragment;
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
			HttpPost httppost = new HttpPost(URL.MEMBER_INSURANCE_INFO_URL);
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
				Log.v("TTT", "in result" + result);
				JSONObject jsonObject = new JSONObject(result);
				if (jsonObject.getString("status").equals("OK")) {
					JSONObject data = jsonObject.getJSONObject("message");
					memberInsuranceInformationfragment.txtIdData.setText(data
							.getString("Id"));
					memberInsuranceInformationfragment.txtMemberIdData
							.setText(data.getString("MemberId"));
					memberInsuranceInformationfragment.txtGroupIdData
							.setText(data.getString("GroupId"));
					String effectiveDate = data.getString("Effective_Date")
							.substring(
									0,
									data.getString("Effective_Date").indexOf(
											"T"));
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date date = format.parse(effectiveDate);
					SimpleDateFormat formatDate = new SimpleDateFormat(
							"dd/MM/yyyy");
					memberInsuranceInformationfragment.txtEffectiveDateData
							.setText(formatDate.format(date).toString());
					memberInsuranceInformationfragment.txtPlanData.setText(data
							.getString("Plan"));
					memberInsuranceInformationfragment.txtOfficeVisitCoPayAmountData
							.setText("$"
									+ data.getString("Office_Visit_Co_Pay_Amount"));
					memberInsuranceInformationfragment.txtDrugCoPayAmountData
							.setText("$" + data.getString("Drug_Co_Pay_Amount"));
					memberInsuranceInformationfragment.txtHospitalCoPayAmountData
							.setText("$"
									+ data.getString("Hospital_Co_Pay_Amount"));
					memberInsuranceInformationfragment.txtEmergencyVisitCoPayAmountData
							.setText("$"
									+ data.getString("Emergency_Visit_Co_Pay_Amount"));
					memberInsuranceInformationfragment.txtDeductibleData
							.setText("$" + data.getString("Deductible"));
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

}
