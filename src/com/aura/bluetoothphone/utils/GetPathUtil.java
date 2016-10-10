package com.aura.bluetoothphone.utils;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

/**
 * 
 *
 * @Description 解决4.4以上版本，选择图片后没有路径,只有图片编号。
 * @author CodeApe
 * @version 1.0
 * @date 2014年9月18日
 * @Copyright: Copyright (c) 2014 Shenzhen Utoow Technology Co., Ltd. All rights
 *             reserved.
 *
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class GetPathUtil {

	/**
	 * 4.3或以下,选了图片之后,根据Uri来做处理
	 * 4.4返回的却是content://com.android.providers.media.documents/document/image:
	 * 3951没有路径,只有图片编号的uri.这就导致接下来无法根据图片路径来裁剪的步骤了.
	 *
	 * @version 1.0
	 * @createTime 2014年9月18日,下午4:21:15
	 * @updateTime 2014年9月18日,下午4:21:15
	 * @createAuthor yeqing
	 * @updateAuthor yeqing
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat) {
			if (DocumentsContract.isDocumentUri(context, uri)) {
				// ExternalStorageProvider
				if (isExternalStorageDocument(uri)) {
					final String docId = DocumentsContract.getDocumentId(uri);
					final String[] split = docId.split(":");
					final String type = split[0];

					if ("primary".equalsIgnoreCase(type)) {
						return Environment.getExternalStorageDirectory() + "/" + split[1];
					}
				}
				// DownloadsProvider
				else if (isDownloadsDocument(uri)) {

					final String id = DocumentsContract.getDocumentId(uri);
					final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

					return getDataColumn(context, contentUri, null, null);
				}
				// MediaProvider媒体
				else if (isMediaDocument(uri)) {
					final String docId = DocumentsContract.getDocumentId(uri);
					final String[] split = docId.split(":");
					final String type = split[0];

					Uri contentUri = null;
					if ("image".equals(type)) {
						contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
					} else if ("video".equals(type)) {
						contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
					} else if ("audio".equals(type)) {
						contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
					}

					final String selection = "_id=?";
					final String[] selectionArgs = new String[] { split[1] };

					return getDataColumn(context, contentUri, selection, selectionArgs);
				}
			} else if (!DocumentsContract.isDocumentUri(context, uri)) {
				// 如果在4.4上面不用"图片"来选,用"图库"来选,就会无法读取到图片路径,所以只需要加个判断,如果是用旧方式来选,就用旧方式来读,就是如果
				// DocumentsContract.isDocumentUri(context, uri)返回false的话,就用旧的方式
				if (uri != null) {
					String uriStr = uri.toString();
					String path = uriStr.substring(10, uriStr.length());
					if (path.startsWith("com.sec.android.gallery3d")) {
						return null;
					}
				}
				return getDataColumn(context, uri, null, null);
			}
		}

		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	/**
	 * 得到这个Uri的数据列的值。这是有用的 MediaStore uri,和其他文件的内容提供者。得到这个Uri的数据列的值。这是有用的
	 * MediaStore uri,和其他文件的内容提供者。
	 *
	 * @param context
	 *                The context.
	 * @param uri
	 *                The Uri to query.
	 * @param selection
	 *                (Optional) Filter used in the query.
	 * @param selectionArgs
	 *                (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file
	 *         path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String[] projection = { MediaStore.Images.Media.DATA };

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(projection[0]);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *                The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *                The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *                The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *                The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}
}
