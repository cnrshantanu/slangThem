package com.zakoi.social.slangApp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.zakoi.social.slangApp.gcm.Constants;

public class Common extends Application {
	
	public static final String PROFILE_ID = "profile_id";
	
	//parameters recognized by demo server
	public static final String FROM = "chatId";
	public static final String REG_ID = "regId";
	public static final String DISP_NAME = "name";
	public static final String PIC_URL = "pic_url";
	public static final String MSG = "msg";
	public static final String TO = "chatId2";	
		
	private static SharedPreferences prefs;

	@Override
	public void onCreate() {
		super.onCreate();
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
	}
	

	public static boolean getLoginStatus() {
		return prefs.getBoolean("login_status",false);
	}
	
	public static void setLoginStatus(boolean logstatus) {
		prefs.edit().putBoolean("login_status",logstatus).commit();
	}
	
	public static String getPhotoLink() {
		return prefs.getString("photo_link", "unknown");
	}
	
	public static void setPhotoLink(String photoLink) {
		prefs.edit().putString("photo_link", photoLink).commit();
	}
	
	public static String getDisplayName() {
		return prefs.getString("display_name", "unknown");
	}
	
	public static void setDisplayName(String displayName) {
		prefs.edit().putString("display_name", displayName).commit();
	}
	
	public static String getChatId() {
		return prefs.getString("chat_id","");
	}
	
	public static void setChatId(String chatId) {
		prefs.edit().putString("chat_id", chatId).commit();
	}
	
	public static String getCurrentChat() {
		return prefs.getString("current_chat", null);
	}
	public static void setCurrentChat(String chatId) {
		prefs.edit().putString("current_chat", chatId).commit();
	}	
	
	public static boolean isNotify() {
		return prefs.getBoolean("notifications_new_message", true);
	}	
	
	public static String getRingtone() {
		return prefs.getString("notifications_new_message_ringtone", android.provider.Settings.System.DEFAULT_NOTIFICATION_URI.toString());
	}
	
	public static String getServerUrl() {
		return prefs.getString("server_url_pref", Constants.SERVER_URL);
	}
	
	public static String getSenderId() {
		return prefs.getString("sender_id_pref", Constants.SENDER_ID);
	}	
}
