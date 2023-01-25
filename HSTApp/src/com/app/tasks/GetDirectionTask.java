package com.app.tasks;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;

import com.app.fragment.FindProviderByNameFragment;
import com.app.fragment.ProviderMapFragment;
import com.app.utils.Utility;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class GetDirectionTask extends AsyncTask<String, Void, String>{

	LatLng latLng2;
	Activity activity;
	ProgressDialog progressDialog;
	String BASE_URL = "https://maps.googleapis.com/maps/api/directions/json";
	ProviderMapFragment providerMapFragment;
	public GetDirectionTask(Activity activity,LatLng latLng2,ProviderMapFragment providerMapFragment) {
		this.activity = activity;
		this.latLng2 = latLng2;
		this.providerMapFragment = providerMapFragment;
	}
	
	@Override
	protected void onPreExecute() {
		progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage("Please wait...");
		progressDialog.setCancelable(false);
		progressDialog.show();
		super.onPreExecute();
	}
	
    // Downloading data in non-ui thread
    @Override
    protected String doInBackground(String... url) {
        // For storing data from web service
        try{
            // Fetching the data from web service
    		String parameters = "origin="+FindProviderByNameFragment.latitude+"," + FindProviderByNameFragment.longitude+"&destination="+latLng2.latitude+"," + latLng2.longitude+"&sensor=false";
    		HttpGet httpGet = new HttpGet(BASE_URL+"?"+parameters);
    		HttpClient client = new DefaultHttpClient();
    		HttpResponse response;
    		response = client.execute(httpGet);
    		HttpEntity entity = response.getEntity();
    		InputStream stream = entity.getContent();
    		String strResponse = Utility.convertStreamToString(stream);
    		return strResponse;
        }catch(Exception e){
        	e.printStackTrace();
        }
        return null;
    }

    // Executes in UI thread, after the execution of
    // doInBackground()
	@Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        try {
        	if(result != null){
	        	ArrayList<LatLng> points = new ArrayList<LatLng>();
	        	JSONObject jObject = new JSONObject(result);
	        	JSONArray jRoutes = jObject.getJSONArray("routes");
	            for(int i=0;i<jRoutes.length();i++){
	            	JSONArray jLegs = ((JSONObject)jRoutes.get(i)).getJSONArray("legs");
	                for(int j=0;j<jLegs.length();j++){
	                	JSONArray jSteps = ((JSONObject)jLegs.get(j)).getJSONArray("steps");
	                    for(int k=0;k<jSteps.length();k++){
	                        String polyline = "";
	                        polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
	                        ArrayList<LatLng> list = decodePoly(polyline);
	                        for(int l=0;l<list.size();l++){
	                            points.add(new LatLng(list.get(l).latitude, list.get(l).longitude));
	                        }
	                    }
	                }
	            }
	            PolylineOptions lineOptions = new PolylineOptions();;
	            lineOptions.addAll(points);
	            lineOptions.width(5);
	            lineOptions.color(Color.RED);
	            providerMapFragment.googleMap.addPolyline(lineOptions);
        	}
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e){
        	e.printStackTrace();
        }
    }
    
    private ArrayList<LatLng> decodePoly(String encoded) {

        ArrayList<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                                 (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }

}
