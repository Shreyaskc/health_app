package com.app.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * use for connection check
 */
final public class SystemInfoService {

	private static final String TAG = "SystemInfoService";
	private static SystemInfoService _instance = null;
	private Context context = null;

	public static SystemInfoService getInstance(Context context) {
		if (_instance == null) {
			_instance = new SystemInfoService(context);
		}

		return _instance;
	}

	// Connectivity manager
	private ConnectivityManager connectivityManager;

	private SystemInfoService(Context context) {
		this.context = context;
		connectivityManager = (ConnectivityManager) this.context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	public boolean checkActualInternetConnection() {
		return testActualConnection();
	}

	public InetAddress lookup(String hostname) {
		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getByName(hostname);
		} catch (UnknownHostException e) {
			Log.d(TAG, "DNS Fail");
			return null;
		}
		Log.d(TAG, "IP " + inetAddress.getHostAddress());

		return inetAddress;
	}

	private boolean testActualConnection() {
		URL url;
		try {
			url = new URL(
					"http://astest-singapore.s3-website-ap-southeast-1.amazonaws.com/channelList.xml");
			url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public ConnectionStatus getConnectionStatus() {

		if (!isOnline()) {
			return ConnectionStatus.OFF;
		} else if (isWifiOnline() == DeviceConnectionStatus.CONNECTED) {
			return ConnectionStatus.WIFI_ON;
		} else if (isWifiOnline() == DeviceConnectionStatus.CONNECTING) {
			return ConnectionStatus.WIFI_CONNECTING;
		} else if (isMobileOnline() == DeviceConnectionStatus.CONNECTED) {
			return ConnectionStatus.MOBILE_ON;
		} else if (isMobileOnline() == DeviceConnectionStatus.CONNECTING) {
			return ConnectionStatus.MOBILE_CONNECTING;
		} else if (isWiMaxOnline() == DeviceConnectionStatus.CONNECTED) {
			return ConnectionStatus.WIMAX_ON;
		} else if (isWiMaxOnline() == DeviceConnectionStatus.CONNECTING) {
			return ConnectionStatus.WIMAX_CONNECTING;
		}

		return ConnectionStatus.ON;
	}

	private boolean isOnline() {
		if (null == connectivityManager) {
			connectivityManager = (ConnectivityManager) this.context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();

		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;

	}

	private DeviceConnectionStatus isMobileOnline() {
		return isDeviceOnline(ConnectivityManager.TYPE_MOBILE);
	}

	private DeviceConnectionStatus isWifiOnline() {
		return isDeviceOnline(ConnectivityManager.TYPE_WIFI);
	}

	private DeviceConnectionStatus isWiMaxOnline() {
		return isDeviceOnline(ConnectivityManager.TYPE_WIMAX);
	}

	private DeviceConnectionStatus isDeviceOnline(int device) {

		if (null == connectivityManager) {
			return DeviceConnectionStatus.DISCONNECTED;
		}

		NetworkInfo ni = connectivityManager.getNetworkInfo(device);
		if (ni.isConnected()) {
			return DeviceConnectionStatus.CONNECTED;
		} else if (ni.isConnectedOrConnecting()) {
			return DeviceConnectionStatus.CONNECTING;
		}
		return DeviceConnectionStatus.DISCONNECTED;
	}

	private enum DeviceConnectionStatus {
		CONNECTED, DISCONNECTED, CONNECTING;
	}
}