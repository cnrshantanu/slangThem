package com.zakoi.social.slangApp;

import java.util.List;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity.Header;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;

public class SettingsActivity extends BasePreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * A preference value change listener that updates the preference's summary
	 * to reflect its new value.
	 */
	private static final BasePreferenceChangeListener sListener = new BasePreferenceChangeListener();
	
	@Override
	protected void addPreferences() {
		// Add 'general' preferences.
		addPreferencesFromResource(R.xml.pref_general);
		
		Preference chatPref = findPreference("chat_id");
		chatPref.setSummary(Common.getChatId());
		
		EditTextPreference exampleText = (EditTextPreference) findPreference("display_name");
		exampleText.setText(Common.getDisplayName());		

		// Add 'notifications' preferences, and a corresponding header.
		PreferenceCategory fakeHeader = new PreferenceCategory(this);
		fakeHeader.setTitle(R.string.pref_header_notifications);
		getPreferenceScreen().addPreference(fakeHeader);
		addPreferencesFromResource(R.xml.pref_notification);
		
		// Add 'messaging' preferences, and a corresponding header.
		fakeHeader = new PreferenceCategory(this);
		fakeHeader.setTitle(R.string.pref_header_messaging);
		getPreferenceScreen().addPreference(fakeHeader);
		addPreferencesFromResource(R.xml.pref_messaging);		
	}
	
	@Override
	protected void bindPreferences() {
		// Bind the summaries of EditText/List/Dialog/Ringtone preferences to
		// their values. When their values change, their summaries are updated
		// to reflect the new value, per the Android Design guidelines.
		sListener.bindPreferenceSummaryToValue(findPreference("display_name"));
		sListener.bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"));
	}
	
	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void loadHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.pref_headers, target);
	}
	
	/**
	 * This fragment shows general preferences only. It is used when the
	 * activity is showing a two-pane settings UI.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class GeneralPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.pref_general);
			
			Preference chatPref = findPreference("chat_id");
			chatPref.setSummary(Common.getChatId());
			
			EditTextPreference exampleText = (EditTextPreference) findPreference("display_name");
			exampleText.setText(Common.getDisplayName());			

			// Bind the summaries of EditText/List/Dialog/Ringtone preferences
			// to their values. When their values change, their summaries are
			// updated to reflect the new value, per the Android Design
			// guidelines.
			sListener.bindPreferenceSummaryToValue(findPreference("display_name"));
		}
	}
	
	/**
	 * This fragment shows notification preferences only. It is used when the
	 * activity is showing a two-pane settings UI.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class NotificationPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.pref_notification);

			// Bind the summaries of EditText/List/Dialog/Ringtone preferences
			// to their values. When their values change, their summaries are
			// updated to reflect the new value, per the Android Design
			// guidelines.
			sListener.bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"));
		}
	}
	
	/**
	 * This fragment shows messaging preferences only. It is used when the
	 * activity is showing a two-pane settings UI.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class MessagingPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.pref_messaging);
		}
	}	
	
}
