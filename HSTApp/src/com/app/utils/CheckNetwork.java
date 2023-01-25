package com.app.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.NetworkInfo;
import android.util.Log;
import android.net.ConnectivityManager;
import android.provider.Settings;

/**
 * To check network connectivity
 */
public class CheckNetwork {

	private static final String TAG = CheckNetwork.class.getSimpleName();

	public static int TYPE_WIFI = 1;
	public static int TYPE_MOBILE = 2;
	public static int TYPE_NOT_CONNECTED = 0;

	/**
	 * To check is Internet available
	 */
	public static boolean isInternetAvailable(Context context) {
		NetworkInfo info = (NetworkInfo) ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		if (info == null) {
			Log.d(TAG, "no internet connection");
			return false;
		} else {
			if (info.isConnected()) {
				Log.d(TAG, " internet connection available...");
				return true;
			} else {
				Log.d(TAG, " internet connection");
				return true;
			}
		}
	}

	/**
	 * To show network dialog
	 */
	public static void showNetworkDialog(final Context context) {
		new AlertDialog.Builder(context)
				.setMessage(
						"Internet conection is not available Whould u like to ON Internet?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
								// Intent intent = new
								// Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
								// Intent intent = new
								// Intent(Settings.ACTION_WIRELESS_SETTINGS);
								Intent intent = new Intent(
										Settings.ACTION_SETTINGS);
								context.startActivity(intent);
								System.exit(0);
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
	}

	/**
	 * To get connectivity Status from connectivity manager
	 */
	public static int getConnectivityStatus(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (null != activeNetwork) {
			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
				return TYPE_WIFI;

			if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
				return TYPE_MOBILE;
		}
		return TYPE_NOT_CONNECTED;
	}

	/**
	 * To get connectivity Status from connectivity status string
	 */
	public static String getConnectivityStatusString(Context context) {
		int conn = getConnectivityStatus(context);
		String status = null;
		if (conn == TYPE_WIFI) {
			status = "Wifi enabled";
		} else if (conn == TYPE_MOBILE) {
			status = "Mobile data enabled";
		} else if (conn == TYPE_NOT_CONNECTED) {
			status = "Not connected to Internet";
		}
		return status;
	}
}
