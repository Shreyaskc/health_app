package com.app.tasks;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.app.fragment.FindProviderByNameFragment;
import com.app.fragment.ProviderMapFragment;
import com.app.utils.Utility;

public class GetAddressAsyncTask extends AsyncTask<Void, Void, String> {
	
	Activity activity;
	double latitude;
	double longitude;
	public static String locationAddress = null;
	ProgressDialog progressDialog;
	int requestCode;
	ProviderMapFragment providerMapFragment;
	FindProviderByNameFragment findProviderByNameFragment;
	public GetAddressAsyncTask(Activity activity, double latitude , double longitude,ProviderMapFragment providerMapFragment) {
		this.activity = activity;
		this.latitude = latitude;
		this.longitude = longitude;
		this.providerMapFragment = providerMapFragment;
	}
	
	public GetAddressAsyncTask(Activity activity, double latitude , double longitude,FindProviderByNameFragment findProviderByNameFragment) {
		this.activity = activity;
		this.latitude = latitude;
		this.longitude = longitude;
		this.findProviderByNameFragment = findProviderByNameFragment;
	}
	
	@Override
	protected void onPreExecute() {
		progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage("Please wait...");
		progressDialog.setCancelable(false);
		progressDialog.show();
		super.onPreExecute();
	}
	
	
	@Override
	protected String doInBackground(Void... params) {
		JSONObject ret = getLocationInfo();
		JSONObject location;
		String location_string = null;
		
		try {
		    location = ret.getJSONArray("results").getJSONObject(0);
		    location_string = location.getString("formatted_address");
		    Log.v("TTT", "formattted address:" + location_string);
		} catch (JSONException e1) {
		    e1.printStackTrace();
		}
		return location_string;
	}
	
	@Override
	protected void onPostExecute(String result) {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
			  }
		try {
			if(findProviderByNameFragment != null) {
			String countryName = result.replace(result.substring(0, result.lastIndexOf(" ")),"");
			findProviderByNameFragment.txtCurrentLocation.setText(countryName.trim());
			FindProviderByNameFragment.curlocationAddress = countryName.trim();
			String address = result.substring(0, result.lastIndexOf(",")) + "";
			findProviderByNameFragment.txtCurrentLocation.setText(address.trim());
			FindProviderByNameFragment.curlocationAddress = countryName.trim()+" "+address.trim();
			locationAddress = result;
			
			} else {
				final String countryName = result.replace(result.substring(0, result.lastIndexOf(" ")),"");
				final String address = result.substring(0, result.lastIndexOf(",")) + "";
				locationAddress = result;
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						providerMapFragment.getActivity());
		 
					// set title
					alertDialogBuilder.setTitle("Another Address Confirmation");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("Are you want to set "+countryName.trim()+address.trim()+" as Another Address?")
						.setCancelable(false)
						.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close
								// current activity
								providerMapFragment.showFragment(countryName.trim()+address.trim(),latitude,longitude);
							}
						  })
						.setNegativeButton("No",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, just close
								// the dialog box and do nothing
								dialog.cancel();
							}
						});
		 
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
		 
						// show it
						alertDialog.show();
				
			}
			//progressDialog.dismiss();
		} catch (Exception e) {
			//progressDialog.dismiss();
			if(findProviderByNameFragment != null) {
				FindProviderByNameFragment.curlocationAddress = "Location Not found";
				findProviderByNameFragment.txtCurrentLocation.setText("Location Not found");
			} 
			Utility.showAlert("Address Not Found.", activity);
			e.printStackTrace();
		}
		super.onPostExecute(result);
	}

	public JSONObject getLocationInfo() {
        HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/geocode/json?latlng="+latitude+","+longitude+"&sensor=true");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
	
	/*//create enum of FragmentName
		public enum ProviderFragmentName{
			//give number to all fragment
			FindProviderByNameFragment(1),ProviderMapFragment(2);
			public final int value;
			ProviderFragmentName(int value) {
			        this.value = value;
			    }
			//get value
		    public int getValue() {
		        return value;
		    }
		    //get Fragment name by Fragment code
		    public static ProviderFragmentName getNameByCode(int code){
	    	  for(ProviderFragmentName e : ProviderFragmentName.values()){
	    	    if(code == e.value) return e;
	    	  }
	    	  return null;
	    	}
		}*/
}