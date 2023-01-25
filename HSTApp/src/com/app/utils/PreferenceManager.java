package com.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.hstapp.R;

/**
 * To store data in PreferenceManager
 */
public class PreferenceManager {
	public static final String NAME_PREFERENCE = "Prefs";
	public static final String IS_REGISTERED = "REGISTERED_KEY";
	public static final String IS_LOGGED_IN = "LOGGED_IN";
	public static final String MEMBER_ID = "MEMBER_ID";
	public static final String GROUP_ID = "GROUP_ID";
	public static final String TEXT_COLOR = "TEXT_COLOR";
	public static final String BACKGROUND_COLOR = "BACKGROUND_COLOR";
	public static final String BUTTON_COLOR = "BUTTON_COLOR";
	public static final String BUTTON_START_COLOR = "BUTTON_START_COLOR";
	public static final String BUTTON_END_COLOR = "BUTTON_END_COLOR";
	public static final String TEXT_HEADER_COLOR = "TEXT_HEADER_COLOR";
	public static final String LOGO = "LOGO";
	public static final String DEVICE_ID = "DEVICE_ID";
	public static final String DEVICE_GCM_ID = "DEVICE_GCM_ID";
	public static final String MISSED_MESSAGES = "MISSED_MESSAGES";
	public static final String NO_OF_MISSED_MESSAGES = "NO_OF_MISSED_MESSAGES";
	public static final String IS_EMERGENCY_MESSAGE = "IS_EMERGENCY_MESSAGE";

	public static boolean isRegistered(Context context) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		return pref.getBoolean(IS_REGISTERED, false);
	}

	public static void setRegistered(Context context, boolean isRegistered) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		pref.edit().putBoolean(IS_REGISTERED, isRegistered).commit();
	}

	public static boolean isLogin(Context context) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		return pref.getBoolean(IS_LOGGED_IN, false);
	}

	public static void setLogin(Context context, boolean isLogin) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		pref.edit().putBoolean(IS_LOGGED_IN, isLogin).commit();
	}

	public static String getMemberId(Context context) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		return pref.getString(MEMBER_ID, "");
	}

	public static void setMemberId(Context context, String memberId) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		pref.edit().putString(MEMBER_ID, memberId).commit();
	}

	public static String getGroupId(Context context) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		return pref.getString(GROUP_ID, "");
	}

	public static void setGroupId(Context context, String groupId) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		pref.edit().putString(GROUP_ID, groupId).commit();
	}

	public static String getBackgroudColor(Context context) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		return pref.getString(BACKGROUND_COLOR, context.getResources()
				.getString(R.color.white));
	}

	public static void setBackgroudColor(Context context, String backgroundColor) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		pref.edit().putString(BACKGROUND_COLOR, backgroundColor).commit();
	}

	public static String getButtonStartColor(Context context) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		return pref.getString(BUTTON_START_COLOR,
				context.getResources().getString(R.color.darker_gray));
	}

	public static void setButtonStartColor(Context context, String buttonStartColor) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		pref.edit().putString(BUTTON_START_COLOR, buttonStartColor).commit();
	}

	public static String getButtonEndColor(Context context) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		return pref.getString(BUTTON_END_COLOR,
				context.getResources().getString(R.color.darker_gray));
	}

	public static void setButtonEndColor(Context context, String buttonEndColor) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		pref.edit().putString(BUTTON_END_COLOR, buttonEndColor).commit();
	}

	
	public static String getTextHeaderColor(Context context) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		return pref.getString(TEXT_HEADER_COLOR,
				context.getResources().getString(R.color.darker_gray));
	}

	public static void setTextHeaderColor(Context context, String textHeaderColor) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		pref.edit().putString(TEXT_HEADER_COLOR, textHeaderColor).commit();
	}

	
	
	public static String getButtonColor(Context context) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		return pref.getString(BUTTON_COLOR,
				context.getResources().getString(R.color.darker_gray));
	}

	public static void setButtonColor(Context context, String buttonColor) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		pref.edit().putString(BUTTON_COLOR, buttonColor).commit();
	}
	
	
	public static String getTextColor(Context context) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		return pref.getString(TEXT_COLOR,
				context.getResources().getString(R.color.black));
	}

	public static void setTextColor(Context context, String textColor) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		pref.edit().putString(TEXT_COLOR, textColor).commit();
	}

	public static String getLogo(Context context) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		return pref.getString(LOGO,
				context.getResources().getString(R.color.black));
	}

	public static void setLogo(Context context, String logo) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		pref.edit().putString(LOGO, logo).commit();
	}

	public static String getDeviceId(Context context) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		return pref.getString(DEVICE_ID, "");
	}

	public static void setDeviceId(Context context, String deviceId) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		pref.edit().putString(DEVICE_ID, deviceId).commit();
	}

	public static String getDeviceGcmId(Context context) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		return pref.getString(DEVICE_GCM_ID, "");
	}

	public static void setDeviceGcmId(Context context, String deviceId) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		pref.edit().putString(DEVICE_GCM_ID, deviceId).commit();
	}

	public static String getMissedMessages(Context context) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		return pref.getString(MISSED_MESSAGES, "");
	}

	public static void setMissedMessages(Context context, String missedMessages) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		pref.edit().putString(MISSED_MESSAGES, missedMessages).commit();
	}

	public static int getNoOfMissedMessages(Context context) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		return pref.getInt(NO_OF_MISSED_MESSAGES, 0);
	}

	public static void setNoOfMissedMessages(Context context,
			int noOfMissedMessages) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		pref.edit().putInt(NO_OF_MISSED_MESSAGES, noOfMissedMessages).commit();
	}

	public static boolean getIsEmergencyMessage(Context context) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		return pref.getBoolean(IS_EMERGENCY_MESSAGE, false);
	}

	public static void setIsEmergencyMessage(Context context,
			boolean isEmergencyMessage) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		pref.edit().putBoolean(IS_EMERGENCY_MESSAGE, isEmergencyMessage)
				.commit();
	}

	public static void clearAll(Context context) {
		SharedPreferences pref = context.getSharedPreferences(NAME_PREFERENCE,
				Context.MODE_PRIVATE);
		pref.edit().remove(TEXT_COLOR).commit();
		pref.edit().remove(BACKGROUND_COLOR).commit();
		pref.edit().remove(BUTTON_COLOR).commit();
		pref.edit().remove(BUTTON_START_COLOR).commit();
		pref.edit().remove(BUTTON_END_COLOR).commit();
		pref.edit().remove(TEXT_HEADER_COLOR).commit();
		pref.edit().remove(LOGO).commit();
		pref.edit().remove(IS_EMERGENCY_MESSAGE).commit();
		setLogin(context, false);
	}

}