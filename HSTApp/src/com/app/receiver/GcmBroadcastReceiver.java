package com.app.receiver;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.app.service.GcmIntentService;

/**
 * To receive gcm messages
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

	public static String TAG = "";

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			TAG = context.getClass().getName();
			// Explicitly specify that GcmIntentService will handle the intent.
			if (intent != null) {
				ComponentName comp = new ComponentName(
						context.getPackageName(),
						GcmIntentService.class.getName());
				// Start the service, keeping the device awake while it is
				// launching.
				startWakefulService(context, (intent.setComponent(comp)));
				setResultCode(Activity.RESULT_OK);
				for (String key : intent.getExtras().keySet()) {
					Log.v(TAG, "key" + key + " = "
							+ intent.getExtras().get(key));
				}
			} else {
				Log.v(TAG, "Intent is null");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
