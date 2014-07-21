package com.zakoi.social.slangApp;

import java.util.List;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zakoi.social.slangApp.gcm.GcmListener;
import com.zakoi.social.slangApp.gcm.GcmUtil;

public class MainActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor>, GcmListener {
	
	private SimpleCursorAdapter adapter;
	private AlertDialog disclaimer;
	private final String TAG = "MainActivity";
	private GcmUtil gcmUtil;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		adapter = new SimpleCursorAdapter(this, 
				R.layout.main_list_item, 
				null, 
				new String[]{DataProvider.COL_NAME, DataProvider.COL_COUNT}, 
				new int[]{R.id.text1, R.id.text2},
				0);
		
		adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				switch(view.getId()) {
				case R.id.text2:
					int count = cursor.getInt(columnIndex);
					if (count > 0) {
						((TextView)view).setText(String.format("%d new message%s", count, count==1 ? "" : "s"));
					}
					return true;					
				}
				return false;
			}
		});
		
		setListAdapter(adapter);
		
		gcmUtil = new GcmUtil(getApplicationContext());
		connect();
		
		getLoaderManager().initLoader(0, null, this);
	    final Intent intent = getIntent();
	    final String action = intent.getAction();

	    if (Intent.ACTION_VIEW.equals(action)) {
	        final List<String> segments = intent.getData().getPathSegments();
	        if (segments.size() == 1) {
	           	addUser(segments.get(0),false);
	           	setIntent(null);
	        }
	    }
		//disclaimer = Disclaimer.show(this);
	}
	
	private void connect() {
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(Common.getDisplayName());
		actionBar.setSubtitle("connecting...");
		
		if (!TextUtils.isEmpty(Common.getServerUrl()) && !TextUtils.isEmpty(Common.getSenderId()) && gcmUtil.register(this)) {
			onRegister(true);
		} else {
			onRegister(false);
		}		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (disclaimer == null) {
			boolean noServerUrl = TextUtils.isEmpty(Common.getServerUrl());
			boolean noSenderId = TextUtils.isEmpty(Common.getSenderId());
			
			if (noServerUrl && noSenderId) {
				Toast.makeText(this, "Enter Server Url and Sender Id in Settings", Toast.LENGTH_LONG).show();
			} else if (noServerUrl) {
				Toast.makeText(this, "Enter Server Url in Settings", Toast.LENGTH_LONG).show();
			} else if (noSenderId) {
				Toast.makeText(this, "Enter Sender Id in Settings", Toast.LENGTH_LONG).show();
			} else if (TextUtils.isEmpty(Common.getChatId())) {
				connect();
			}
		}
	}

	@Override
	public void onRegister(boolean status) {
		if (status) {
			getActionBar().setTitle(Common.getDisplayName());
			getActionBar().setSubtitle("online");
		} else {
			getActionBar().setSubtitle("offline");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.friend_list_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_share:
			Util.share(this, Common.getChatId(), false);
			return true;		
		case R.id.action_add:
			AddContactDialog dialog = new AddContactDialog();
			dialog.show(getFragmentManager(), "AddContactDialog");
			return true;
			
		/*case R.id.action_create:
			new CreateGroupTask(this).execute();
			return true;		*/	
			
		/*case R.id.action_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;			*/
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, ChatActivity.class);
		intent.putExtra(Common.PROFILE_ID, String.valueOf(id));
		startActivity(intent);
	}	

	@Override
	protected void onDestroy() {
		if (disclaimer != null) 
			disclaimer.dismiss();
		gcmUtil.cleanup();
		super.onDestroy();
	}	
	
	//----------------------------------------------------------------------------

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader loader = new CursorLoader(this, 
				DataProvider.CONTENT_URI_PROFILE, 
				new String[]{DataProvider.COL_ID, DataProvider.COL_NAME, DataProvider.COL_COUNT}, 
				null, 
				null, 
				DataProvider.COL_COUNT + " DESC, " + DataProvider.COL_ID + " DESC"); 
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}	
	
	private void addUser(String chatId,boolean isGroup){
		try {
			ContentValues values = new ContentValues(2);
			values.put(DataProvider.COL_NAME,chatId);
			values.put(DataProvider.COL_CHATID, chatId);
			values.put(DataProvider.COL_ISGROUP, isGroup);
			getContentResolver().insert(DataProvider.CONTENT_URI_PROFILE, values);
		} catch (SQLException sqle) { 
			Toast.makeText(this, "Cannot add user ", Toast.LENGTH_LONG).show();
		}
	}

}
