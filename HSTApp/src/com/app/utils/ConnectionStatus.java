package com.app.utils;

import java.util.ArrayList;
import java.util.Iterator;
import android.net.ConnectivityManager;

/**
 * To get connection status
 */
public enum ConnectionStatus implements Iterable<ConnectionStatus> {

	OFF(0), ON(1), CONNECTING(2), WIFI_ON(11), MOBILE_ON(12), WIMAX_ON(13), WIFI_CONNECTING(
			21), MOBILE_CONNECTING(22), WIMAX_CONNECTING(23);

	private final int _connectionStatusCode;

	private ConnectionStatus(int id) {
		this._connectionStatusCode = id;
	}

	public final int getCode() {
		return this._connectionStatusCode;
	}

	public final boolean isOffline() {
		if (this._connectionStatusCode < 10) {
			return true;
		}
		return false;
	}

	public final boolean isOnline() {
		if (this._connectionStatusCode > 10 && this._connectionStatusCode < 20) {
			return true;
		}
		return false;
	}

	public final boolean isConnecting() {
		if (this._connectionStatusCode > 20) {
			return true;
		}
		return false;
	}

	public final int getConnectionType() {
		int r = this._connectionStatusCode % 10;
		int result = 0;

		if (this._connectionStatusCode < 10) {
			// do noothing
		} else if (r == 1) {
			result = ConnectivityManager.TYPE_WIFI;
		} else if (r == 2) {
			result = ConnectivityManager.TYPE_MOBILE;
		} else if (r == 3) {
			result = ConnectivityManager.TYPE_WIMAX;
		}
		return result;
	}

	public Iterator<ConnectionStatus> iterator() {
		ArrayList<ConnectionStatus> al = new ArrayList<ConnectionStatus>(
				java.util.EnumSet.of(this));
		return al.iterator();
	}

}