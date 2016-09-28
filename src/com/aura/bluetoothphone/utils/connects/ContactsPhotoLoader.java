package com.aura.bluetoothphone.utils.connects;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;


/**
 * @ClassName: ContactsPhotoLoader
 * @Description: TODO
 * @author: steven zhang
 * @date: Sep 27, 2016 2:32:08 PM
 */
public class ContactsPhotoLoader {

	private static final int LOADER_END = 0;
	
	private LruCache<Uri, Bitmap> mLruCache;
	
	private static ContactsPhotoLoader mInstance;

	private ExecutorService mExecutorService;
	
	private Handler mHandler = new Handler(Looper.getMainLooper()) {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == LOADER_END) {
				ImageData data = (ImageData) msg.obj;
				if (data.key.equals(data.image.getTag())) {
					Bitmap bitmap = getBitmapFromMemCache(data.key);
					data.image.setImageBitmap(bitmap);
				}
			}
		}
	};
	
	private ContactsPhotoLoader() {
		long maxMemory = Runtime.getRuntime().maxMemory();
		Log.d("TAG", "maxMemory:"+(maxMemory/1024/1024));
		mLruCache = new LruCache<Uri, Bitmap>((int) (maxMemory/(1024*8))) {
			@Override
			protected int sizeOf(Uri key, Bitmap value) {
				return value.getRowBytes() * value.getHeight() / 1024;
			}
		};
	}
	
	public static ContactsPhotoLoader getInstance() {
		if (mInstance == null) {
			synchronized (ContactsPhotoLoader.class) {
				if (mInstance == null) {
					mInstance = new ContactsPhotoLoader();
				}
			}
		}
		
		return mInstance;
	}
	
	private synchronized ExecutorService getExecutorService() {
		if (mExecutorService == null) {
			int capacity = Runtime.getRuntime().availableProcessors()+1;
			mExecutorService = Executors.newFixedThreadPool(capacity);
		}
		
		return mExecutorService;
	}
	
	public int size() {
		return mLruCache.size();
	}
	
	public void cleanCache() {
		mLruCache.evictAll();
	}
	
	public void addBitmapToMemCache(Uri id, Bitmap bitmap) {
		if (getBitmapFromMemCache(id) == null) {
			mLruCache.put(id, bitmap);
		}
	}
	
	public Bitmap getBitmapFromMemCache(Uri id) {
		return mLruCache.get(id);
	}
	
//	public void loadImage(final Context context, final ImageView imageView, final int contactid, long photoid, int defaultRes) {
//		imageView.setImageResource(defaultRes);
//		if (photoid > 0) {
//			Bitmap bmp = getBitmapFromMemCache(contactid);
//			if (bmp != null) {
//				imageView.setImageBitmap(bmp);
//				return ;
//			}
//			
//			imageView.setTag(contactid);
//			
//			getExecutorService().execute(new Runnable() {
//				
//				@Override
//				public void run() {
//					Bitmap bmp = ContactsUtils.loadContactPhotoThumbnail(context, contactid);
//					addBitmapToMemCache(contactid, bmp);
//					
//					ImageData data = new ImageData();
//					data.key = contactid;
//					data.image = imageView;
//					Message message = mHandler.obtainMessage();
//					message.what = LOADER_END;
//					message.obj = data;
//					mHandler.sendMessage(message);
//				}
//			});
//		}
//	}
	
	public void loadImage(final Context context, final ImageView imageView, final Uri uri, long photoid, int defaultRes) {
		imageView.setImageResource(defaultRes);
		if (photoid > 0) {
			Bitmap bmp = getBitmapFromMemCache(uri);
			if (bmp != null) {
				imageView.setImageBitmap(bmp);
				return ;
			}
			
			imageView.setTag(uri);
			
			getExecutorService().execute(new Runnable() {
				
				@Override
				public void run() {
					Bitmap bmp = ContactsUtils.loadContactPhotoThumbnail(context, uri);
					addBitmapToMemCache(uri, bmp);
					
					ImageData data = new ImageData();
					data.key = uri;
					data.image = imageView;
					Message message = mHandler.obtainMessage();
					message.what = LOADER_END;
					message.obj = data;
					mHandler.sendMessage(message);
				}
			});
		}
	}
	
	private static class ImageData {
		private ImageView image;
		private Uri key;
	}
	
}
