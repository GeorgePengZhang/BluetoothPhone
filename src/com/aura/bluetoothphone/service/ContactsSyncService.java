package com.aura.bluetoothphone.service;

import android.app.Service;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.aura.bluetoothphone.utils.connects.ContactsPhotoLoader;
import com.aura.bluetoothphone.utils.connects.ContactsUtils;


/**
 * @ClassName: ContactsSyncService
 * @Description: TODO
 * @author: steven zhang
 * @date: Sep 27, 2016 2:31:56 PM
 */
public class ContactsSyncService extends Service {
	
	public static final String UPDATE_CONTACTS = "com.android.provider.contacts.updated.sync";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		getContentResolver().registerContentObserver(ContactsUtils.Phone.CONTENT_CALLABLES_URI, true, contentObserver);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		getContentResolver().unregisterContentObserver(contentObserver);
	}
	
	ContentObserver contentObserver = new ContentObserver(new Handler()) {
		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int size = ContactsPhotoLoader.getInstance().size();
			if (size > 0) {
				ContactsPhotoLoader.getInstance().cleanCache();
			}
			
			Intent intent = new Intent(UPDATE_CONTACTS);
			LocalBroadcastManager.getInstance(ContactsSyncService.this).sendBroadcast(intent);
		}
	};
}
