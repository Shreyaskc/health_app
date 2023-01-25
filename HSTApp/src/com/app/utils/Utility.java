package com.app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.app.hstapp.LoginActivity;
import com.app.utils.HSTApp.TrackerName;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.FrameLayout;

/**
 * Utility class used in all
 */
public class Utility {

	/**
	 * @Purpose Show alert according to the message
	 */
	public static void showAlert(String msg, Context context) {
		try {
			AlertDialog.Builder alert1 = new AlertDialog.Builder(context);
			alert1.setTitle("Message"); // Set Alert dialog title here
			alert1.setMessage(msg);
			alert1.setCancelable(false);
			alert1.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							dialog.cancel();
						}
					});
			AlertDialog alertDialog1 = alert1.create();
			alertDialog1.setCancelable(false);
			alertDialog1.show();
		} catch (Exception e) {
			Log.e("Exception", "showAlert..." + e.getMessage());
		}
	}

	/**
	 * @Purpose Show alert according to the message
	 */
	public static void showAlertWithClick(String msg, final Context context) {
		try {
			AlertDialog.Builder alert1 = new AlertDialog.Builder(context);
			alert1.setTitle("Message"); // Set Alert dialog title here
			alert1.setMessage(msg);
			alert1.setCancelable(false);
			alert1.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							dialog.cancel();
							PreferenceManager.clearAll(context);
							Intent intent = new Intent(context,
									LoginActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
									| Intent.FLAG_ACTIVITY_NEW_TASK);
							context.startActivity(intent);
							((Activity) context).finish();
						}
					});
			AlertDialog alertDialog1 = alert1.create();
			alertDialog1.setCancelable(false);
			alertDialog1.show();
		} catch (Exception e) {
			Log.e("Exception", "showAlert..." + e.getMessage());
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

	/**
	 * @Purpose convert stream to string
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * Return Larger item size from adapter
	 * 
	 * @param context
	 * @param adapter
	 * @return int to get widest view
	 */
	public static int getWidestView(Context context, Adapter adapter) {
		int maxWidth = 0;
		View view = null;
		FrameLayout fakeParent = new FrameLayout(context);
		for (int i = 0, count = adapter.getCount(); i < count; i++) {
			view = adapter.getView(i, view, fakeParent);
			view.measure(View.MeasureSpec.UNSPECIFIED,
					View.MeasureSpec.UNSPECIFIED);
			int width = view.getMeasuredWidth();
			if (width > maxWidth) {
				maxWidth = width;
			}
		}
		return maxWidth;
	}

	// public void createDrawable(Context context){
	// ShapeDrawable sd1 = new ShapeDrawable(new RectShape());
	// sd1.getPaint().setColor(Color.parseColor("#33000000"));
	// sd1.getPaint().setStyle(Style.STROKE);
	// sd1.getPaint().setStrokeWidth(1);
	//
	// sd1.getPaint().setColor(context.getResources().getColor(R.color.transparent));
	// sd1.getPaint().setStyle(Style.FILL);
	// sd1.set;
	// }

	/**
	 * Checking for internet connection
	 * 
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * @Purpose get Distance between two locations 
	 * @param latLng1 latitude longitude of first location
	 * @param latLng2 latitude longitude of second location
	 * @return distance in float
	 */
	public static float getDistance(LatLng latLng1, LatLng latLng2) {
		Float f = Float.MIN_VALUE;
		Location location1 = new Location("first");
		Location location2 = new Location("second");
		location1.setLatitude(latLng1.latitude);
		location1.setLongitude(latLng1.longitude);
		location2.setLatitude(latLng2.latitude);
		location2.setLongitude(latLng2.longitude);
		f = location1.distanceTo(location2);
		f =(float) (f * 0.00062137);
		return f;
	}
}
