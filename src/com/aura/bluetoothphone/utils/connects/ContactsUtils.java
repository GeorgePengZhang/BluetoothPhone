package com.aura.bluetoothphone.utils.connects;

import java.io.InputStream;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;


/**
 * @ClassName: ContactsUtils
 * @Description: TODO
 * @author: steven zhang
 * @date: Sep 27, 2016 2:32:21 PM
 */
public class ContactsUtils {

	public static final class Phone {
		/**
		 * 普通读取方式
		 */
		public static final Uri CONTENT_PHONE_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		
		/**
		 * 系统联系人读取方式
		 */
		public static final Uri CONTENT_CALLABLES_URI = Uri.withAppendedPath(ContactsContract.Data.CONTENT_URI, "callables").buildUpon()
				.appendQueryParameter(ContactsContract.DIRECTORY_PARAM_KEY, "0")
				.appendQueryParameter("address_book_index_extras", "true")
				.appendQueryParameter("remove_duplicate_entries", "true")
				.build();
		
		public static Cursor getContactsCursor(Context context) {
			Cursor cursor = context.getContentResolver().query(CONTENT_PHONE_URI, PROJECTION_PRIMARY, null, null, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY);
			return cursor;
		}
		
		public static Cursor getSystemContactsCursor(Context context) {
			Cursor cursor = context.getContentResolver().query(CONTENT_CALLABLES_URI, PROJECTION_PRIMARY, null, null, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY);
			return cursor;
		}
		
		public static final String[] PROJECTION_PRIMARY = new String[] {
			ContactsContract.CommonDataKinds.Phone._ID, // 0
			ContactsContract.CommonDataKinds.Phone.TYPE, // 1
			ContactsContract.CommonDataKinds.Phone.LABEL, // 2
			ContactsContract.CommonDataKinds.Phone.NUMBER, // 3
			ContactsContract.CommonDataKinds.Phone.CONTACT_ID, // 4
			ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY, // 5
			ContactsContract.CommonDataKinds.Phone.PHOTO_ID, // 6
			ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY, // 7
			ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY, //8
		};
		
		public static final int PHONE_ID           	= 0;
        public static final int PHONE_TYPE         	= 1;
        public static final int PHONE_LABEL        	= 2;
        public static final int PHONE_NUMBER       	= 3;
        public static final int PHONE_CONTACT_ID   	= 4;
        public static final int PHONE_LOOKUP_KEY   	= 5;
        public static final int PHONE_PHOTO_ID     	= 6;
        public static final int PHONE_DISPLAY_NAME 	= 7;
        public static final int PHONE_SORT_KEY	 	= 8;
	}
	
	//通话记录类
	public static final class Calls {
		public static final Uri CONTENT_URI = CallLog.Calls.CONTENT_URI;
		
		public static Cursor getCallLogCursor(Context context) {
			Cursor cursor = context.getContentResolver().query(CONTENT_URI, PROJECTION_PRIMARY, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
			return cursor;
		}
		
		 public static final String[] PROJECTION_PRIMARY = new String[] {
	            CallLog.Calls._ID,                       // 0
	            CallLog.Calls.NUMBER,                    // 1
	            CallLog.Calls.DATE,                      // 2
	            CallLog.Calls.DURATION,                  // 3
	            CallLog.Calls.TYPE,                      // 4
	            "countryiso", //CallLog.Calls.COUNTRY_ISO,               // 5
	            "geocoded_location", //CallLog.Calls.GEOCODED_LOCATION,         // 6
	            CallLog.Calls.CACHED_NAME,               // 7
	            CallLog.Calls.CACHED_NUMBER_TYPE,        // 8
	            CallLog.Calls.CACHED_NUMBER_LABEL,       // 9
	            "lookup_uri", //CallLog.Calls.CACHED_LOOKUP_URI,         // 10
	            "matched_number", //CallLog.Calls.CACHED_MATCHED_NUMBER,     // 11
	            "normalized_number", //CallLog.Calls.CACHED_NORMALIZED_NUMBER,  // 12
	            "photo_id", //CallLog.Calls.CACHED_PHOTO_ID,           // 13
	            "formatted_number", //CallLog.Calls.CACHED_FORMATTED_NUMBER,   // 14
	            CallLog.Calls.IS_READ,                   // 15
	    };

	    public static final int ID = 0;
	    public static final int NUMBER = 1;
	    public static final int DATE = 2;
	    public static final int DURATION = 3;
	    public static final int CALL_TYPE = 4;
	    public static final int COUNTRY_ISO = 5;
	    public static final int GEOCODED_LOCATION = 6;
	    public static final int CACHED_NAME = 7;
	    public static final int CACHED_NUMBER_TYPE = 8;
	    public static final int CACHED_NUMBER_LABEL = 9;
	    public static final int CACHED_LOOKUP_URI = 10;
	    public static final int CACHED_MATCHED_NUMBER = 11;
	    public static final int CACHED_NORMALIZED_NUMBER = 12;
	    public static final int CACHED_PHOTO_ID = 13;
	    public static final int CACHED_FORMATTED_NUMBER = 14;
	    public static final int IS_READ = 15;
	}
	
	/**
	 * 获取联系人头像
	 * @param context
	 * @param contactid
	 * @return
	 */
	public static Bitmap loadContactPhotoThumbnail(Context context, int contactid) {
		Bitmap bitmap = null;
		Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
		InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), uri);
		bitmap = BitmapFactory.decodeStream(input);
		return bitmap;
	}
	
	/**
	 * 获取联系人头像uri
	 * @param contactid
	 * @return
	 */
	public static Uri getContactPhotoUri(int contactid) {
		Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
		return uri;
	}
	
	/**
	 * 获取联系人头像
	 * @param context
	 * @param uri
	 * @return
	 */
	public static Bitmap loadContactPhotoThumbnail(Context context, Uri uri) {
		Bitmap bitmap = null;
		InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), uri);
		bitmap = BitmapFactory.decodeStream(input);
		return bitmap;
	}
}


