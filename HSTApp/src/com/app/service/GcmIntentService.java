package com.app.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.app.hstapp.MemberActivity;
import com.app.hstapp.R;
import com.app.receiver.GcmBroadcastReceiver;
import com.app.utils.PreferenceManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * To show gcm message notification
 */
public class GcmIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	public static final String TAG = "GcmIntentService";

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);
		Log.v(TAG, messageType + "messageType");
		if (PreferenceManager.isLogin(this)) {
			if (!extras.isEmpty()) { // has effect of unparcelling Bundle
			// if (GoogleCloudMessaging.
			// MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
			// sendNotification("Send error: " + extras.toString());
			// } else if (GoogleCloudMessaging.
			// MESSAGE_TYPE_DELETED.equals(messageType)) {
			// sendNotification("Deleted messages on server: " +
			// extras.toString());
			// } else if (GoogleCloudMessaging.
			// MESSAGE_TYPE_MESSAGE.equals(messageType)) {
			// Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
				sendNotification(extras);
				// }
			}
		} else {
			if (intent.getExtras() != null) {
				// Log.v("TTT","TTT" + "extras not null" + bundle.toString());
				for (String key : intent.getExtras().keySet()) {
					Log.v("TTT", "TTT" + "extras" + intent.getExtras().keySet()
							+ "" + intent.getExtras().getString("message"));
					if (key.equals("message")) {
						String messages = PreferenceManager
								.getMissedMessages(this);
						PreferenceManager.setMissedMessages(
								this,
								messages.equals("") ? intent.getExtras()
										.getString("message") : messages
										+ ","
										+ intent.getExtras().getString(
												"message"));
						PreferenceManager
								.setNoOfMissedMessages(this, PreferenceManager
										.getNoOfMissedMessages(this) + 1);
					}
				}
			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	/**
	 * To send notification to user
	 */
	private void sendNotification(Bundle bundle) {
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent2open = new Intent(this, MemberActivity.class);
		intent2open.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent2open.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent2open.putExtras(bundle);
		String message = "";
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent2open, Notification.DEFAULT_SOUND
						| Notification.DEFAULT_LIGHTS
						| Notification.FLAG_AUTO_CANCEL
						| PendingIntent.FLAG_UPDATE_CURRENT);
		if (bundle != null) {
			// Log.v("TTT","TTT" + "extras not null" + bundle.toString());
			for (String key : bundle.keySet()) {
				Log.v("TTT",
						"TTT" + "extras" + bundle.keySet() + ""
								+ bundle.getString("message"));
				if (key.equals("message")) {
					message = bundle.getString("message");
				}
			}
		}
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("HST App")
				.setStyle(
						new NotificationCompat.BigTextStyle().bigText(message))
				.setContentText(message).setContentIntent(contentIntent);

		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}
}