package com.zakoi.social.slangApp;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.zakoi.social.slangApp.gcm.Constants;

public class Common extends Application implements Constants {
	
	public static final String PROFILE_ID = "profile_id";
	
	//parameters recognized by demo server
	public static final String FROM = "chatId";
	public static final String REG_ID = "regId";
	public static final String MSG = "msg";
	public static final String TO = "chatId2";	
	
	private static SharedPreferences prefs;

	@Override
	public void onCreate() {
		super.onCreate();
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = prefs.edit();
		if (!prefs.contains("chat_id")) {
			editor.putString("chat_id", "");
	      }
	    else if (!prefs.contains("display_name")) {
	    	editor.putString("display_name", "");
	    }
	    else if (!prefs.contains("server_url_pref")) {
	    	editor.putString("server_url_pref", SERVER_URL);
	    }
	    else if (!prefs.contains("sender_id_pref")) {
	    	editor.putString("sender_id_pref", "SENDER_ID");
	    }
		
		
		
	}
	
	public static String getDisplayName() {
		return prefs.getString("display_name", "");
	}
	
	public static String getChatId() {
		return prefs.getString("chat_id", "");
	}
	
	public static String getChatId1() {
		return "shan";
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
	
	public static String getServerUrl1() {
		String SERVER_URL = "http://learned-ocean-628.appspot.com/";
		return SERVER_URL;
	}
	
	public static String getServerUrl() {
		return prefs.getString("server_url_pref", Constants.SERVER_URL);
	}
	
	public static String getSenderId() {
		return prefs.getString("sender_id_pref", Constants.SENDER_ID);
	}	
	public static String getSenderId1() {
		String SENDER_ID = "236960445058";
		return SENDER_ID;
	}	
    	
}