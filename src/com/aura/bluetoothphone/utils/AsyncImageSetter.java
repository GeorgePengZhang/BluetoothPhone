package com.aura.bluetoothphone.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.aura.bluetoothphone.R;



public class AsyncImageSetter {
	/** 异步加载头像对象 常驻内存的方法 */
	private static AsyncImageloader asyncLoadportrait = new AsyncImageloader(1, R.drawable.ic_launcher, 0, 100000);;
	/** 异步加载圆角图片对象 常驻内存的方法 */
	private static AsyncImageloader asyncLoadRoundImage = new AsyncImageloader(1, R.drawable.ic_launcher, 0, 10);;
	/** 异步加载矩形图片对象 常驻内存的方法 */
	private static AsyncImageloader asyncLoadRectImage = new AsyncImageloader(1, R.drawable.ic_launcher, 0, 0);;
	/** 异步加载图片对象 常驻内存的方法 */
	private static AsyncImageloader asyncLoadImage = new AsyncImageloader(1, R.drawable.ic_launcher, 0, 0);;
	/** 异步加载视频略缩图对象 常驻内存的方法 */
	private static AsyncImageloader asyncLoadVideo = new AsyncImageloader(1, R.drawable.ic_launcher, 0, 0, true);

	/**
	 * 描述：异步设置原图片
	 * 
	 * @version 1.0
	 * @createTime 2014-4-16 下午3:27:13
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-16 下午3:27:13
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param img
	 *            图片显示控件
	 * @param position
	 *            位置
	 * @param path
	 *            路径
	 * @param scaleType
	 *            图片绘制类型
	 */
	public static void setImage(final ImageView img, int position, String path, ScaleType scaleType) {
		setImage(asyncLoadImage, img, position, path, scaleType, false);
	}

	/**
	 * 描述：异步设置圆角图片
	 * 
	 * @version 1.0
	 * @createTime 2014-4-16 下午3:25:48
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-16 下午3:25:48
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param img
	 * @param position
	 * @param path
	 */
	public static void setImage(final ImageView img, int position, String path) {
		setImage(img, position, path, null, true);
	}

	/**
	 * 描述：异步设置圆角图片
	 * 
	 * @version 1.0
	 * @createTime 2014-4-16 下午3:23:45
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-16 下午3:23:45
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param img
	 *            图片显示控件
	 * @param position
	 *            位置
	 * @param path
	 *            路径
	 * @param scaleType
	 *            图片绘制类型
	 * @param is150Size
	 *            是否是小图
	 */
	public static void setImage(final ImageView img, int position, String path, final ScaleType scaleType, boolean is150Size) {
		setImage(asyncLoadRoundImage, img, position, path, scaleType, is150Size);
	}

	/**
	 * 描述：异步设置圆角图片
	 * 
	 * @version 1.0
	 * @createTime 2014-4-16 下午3:23:45
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-16 下午3:23:45
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param img
	 *            图片显示控件
	 * @param position
	 *            位置
	 * @param path
	 *            路径
	 * @param scaleType
	 *            图片绘制类型
	 * @param is150Size
	 *            是否是小图
	 */
	public static void setDfImage(final ImageView img, int position, String path, final ScaleType scaleType, boolean is150Size, boolean isBack) {
		setDfImage(asyncLoadRoundImage, img, position, path, scaleType, is150Size, isBack);
	}

	/**
	 * 异步加载矩形图片
	 * 
	 * @version 1.0
	 * @createTime 2014年8月8日,下午3:35:38
	 * @updateTime 2014年8月8日,下午3:35:38
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param img
	 *            图片显示控件
	 * @param position
	 *            位置
	 * @param path
	 *            路径
	 * @param scaleType
	 *            图片绘制类型
	 * @param is150Size
	 *            是否是小图
	 */
	public static void setRectImage(final ImageView img, int position, String path, final ScaleType scaleType, boolean is150Size) {
		setImage(asyncLoadRectImage, img, position, path, scaleType, is150Size);
	}

	/**
	 * 异步模糊处理图片
	 * 
	 * @version 1.0
	 * @createTime 2014年8月8日,下午3:35:38
	 * @updateTime 2014年8月8日,下午3:35:38
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param img
	 *            图片显示控件
	 * @param position
	 *            位置
	 * @param path
	 *            路径
	 * @param scaleType
	 *            图片绘制类型
	 * @param is150Size
	 *            是否是小图
	 */
	public static void setDimImage(final ImageView img, int position, String path, final ScaleType scaleType, boolean is150Size, Context context) {
		setImage(asyncLoadRectImage, img, position, path, scaleType, is150Size, context);
	}

	
	/**
	 * 描述：异步设置图片
	 * 
	 * @version 1.0
	 * @createTime 2014-4-16 下午3:23:03
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-16 下午3:23:03
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param asyncImageloader
	 *            异步加载器
	 * @param img
	 *            图片显示控件
	 * @param position
	 *            位置
	 * @param path
	 *            路径
	 * @param scaleType
	 *            图片绘制类型
	 * @param is150Size
	 *            是否是小图
	 */
	public static void setImage(AsyncImageloader asyncImageloader, final ImageView img, int position, String path, final ScaleType scaleType, boolean is150Size) {
		if (TextUtils.isEmpty(path)) {
			asyncImageloader.setError(img);
			return;
		}
//		img.setTag(position);
//		if (is150Size) {
//			path = path.replace(".jpg", "_150x150.jpg");
//		}
//		asyncImageloader.loadImageBitmap(img.getContext(), img, position, ServerConfig.SERVER_IMAGE_URL + path, null, new AsyncImageloader.ImageCallback() {
//			@Override
//			public void imageLoaded(int position, Bitmap bitmap, String imagePath) {
//				Object tag = img.getTag();
//				if (tag == null || tag.equals(position)) {
//					if (scaleType != null) {
//						img.setScaleType(scaleType);
//					}
//					img.setImageBitmap(bitmap);
//				}
//			}
//		}, false, null);
	}

	/**
	 * 描述：异步设置图片
	 * 
	 * @version 1.0
	 * @createTime 2014-4-16 下午3:23:03
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-16 下午3:23:03
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param asyncImageloader
	 *            异步加载器
	 * @param img
	 *            图片显示控件
	 * @param position
	 *            位置
	 * @param path
	 *            路径
	 * @param scaleType
	 *            图片绘制类型
	 * @param is150Size
	 *            是否是小图
	 */
	public static void setImage(AsyncImageloader asyncImageloader, final ImageView img, int position, String path, final ScaleType scaleType, boolean is150Size,
			final Context context) {
		if (TextUtils.isEmpty(path)) {
			asyncImageloader.setError(img);
			return;
		}
//		img.setTag(position);
//		if (is150Size) {
//			path = path.replace(".jpg", "_150x150.jpg");
//		}
//		asyncImageloader.loadImageBitmap(img.getContext(), img, position, ServerConfig.SERVER_IMAGE_URL + path, null, new AsyncImageloader.ImageCallback() {
//			@Override
//			public void imageLoaded(int position, Bitmap bitmap, String imagePath) {
//				Object tag = img.getTag();
//				if (tag == null || tag.equals(position)) {
//					if (scaleType != null) {
//						img.setScaleType(scaleType);
//					}
//					img.setImageDrawable(ImageUtil.BlurImages(bitmap, context));
//				}
//			}
//		}, false, null);
	}

	/**
	 * 描述：异步设置图片
	 * 
	 * @version 1.0
	 * @createTime 2014-4-16 下午3:23:03
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-16 下午3:23:03
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param asyncImageloader
	 *            异步加载器
	 * @param img
	 *            图片显示控件
	 * @param position
	 *            位置
	 * @param path
	 *            路径
	 * @param scaleType
	 *            图片绘制类型
	 * @param is150Size
	 *            是否是小图
	 */
	public static void setDfImage(AsyncImageloader asyncImageloader, final ImageView img, int position, String path, final ScaleType scaleType, boolean is150Size, boolean isBack) {
		if (TextUtils.isEmpty(path)) {
			asyncImageloader.setError(img);
			return;
		}
//		img.setTag(position);
//		if (is150Size) {
//			path = path.replace(".jpg", "_150x150.jpg");
//		}
//		asyncImageloader.loadImageBitmap(img.getContext(), img, position, ServerConfig.SERVER_IMAGE_URL + path, null, new AsyncImageloader.ImageCallback() {
//			@Override
//			public void imageLoaded(int position, Bitmap bitmap, String imagePath) {
//				Object tag = img.getTag();
//				if (tag == null || tag.equals(position)) {
//					if (scaleType != null) {
//						img.setScaleType(scaleType);
//					}
//					img.setImageBitmap(bitmap);
//				}
//			}
//		}, isBack, null);
	}

	

	/**
	 * 设置本地图片
	 * 
	 * @version 1.0
	 * @createTime 2014年4月11日,下午12:47:35
	 * @updateTime 2014年4月11日,下午12:47:35
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param img
	 *            图片控件
	 * @param position
	 *            图片位置
	 * @param path
	 *            图片路径
	 * @param scaleType
	 *            图片绘制类型
	 */
	public static void setLocalImage(final ImageView img, int position, String path, final ScaleType scaleType) {
		if (path == null) {
			asyncLoadRectImage.setDefaul(img);
			return;
		}
		img.setTag(position);
		asyncLoadRectImage.loadImageBitmap(img.getContext(), img, position, path, null, new AsyncImageloader.ImageCallback() {
			@Override
			public void imageLoaded(int position, Bitmap bitmap, String imagePath) {
				Object tag = img.getTag();
				if (tag == null || tag.equals(position)) {
					if (scaleType != null) {
						img.setScaleType(scaleType);
					}
					img.setImageBitmap(bitmap);
				}
			}
		}, false, null);
	}

	/**
	 * 描述：异步设置头像
	 * 
	 * @version 1.0
	 * @createTime 2014-4-8 下午4:54:55
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-8 下午4:54:55
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param img
	 *            图片控件
	 * @param position
	 *            图片位置
	 * @param path
	 *            图片路径
	 */
	public static void setPortrait(final ImageView img, int position, String path) {
		if (TextUtils.isEmpty(path)) {
			asyncLoadportrait.setPortraitDefaul(img);
			return;
		}
//		img.setTag(position);
////		if (path!=null) {
////			path = path.replace(".jpg", "_150x150.jpg");
////		}
//		asyncLoadportrait.loadImageBitmap(img.getContext(), img, position, ServerConfig.SERVER_IMAGE_URL + path, null, new AsyncImageloader.ImageCallback() {
//			@Override
//			public void imageLoaded(int position, Bitmap bitmap, String imagePath) {
//				Object tag = img.getTag();
//				if (tag == null || tag.equals(position)) {
//					img.setImageBitmap(bitmap);
//				}
//			}
//		}, true, null);
	}

	
	/**
	 * 下载图片
	 * 
	 * @version 1.0
	 * @createTime 2014年5月24日,下午9:14:08
	 * @updateTime 2014年5月24日,下午9:14:08
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 *            上下文
	 * @param path
	 *            图片路径
	 * @param callback
	 *            回调
	 * @return
	 */
	public static Bitmap loadImage(Context context, String path, AsyncImageloader.ImageCallback callback) {
		if (TextUtils.isEmpty(path)) {
			return asyncLoadportrait.defaultBitmap;
		}
//		return asyncLoadportrait.loadImageBitmap(context, null, 0, ServerConfig.SERVER_IMAGE_URL + path, null, callback, false, null);
		return null ;
	}

	/**
	 * 描述：设置本地视频的图片
	 * 
	 * @version 1.0
	 * @createTime 2014-4-12 下午2:03:58
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-12 下午2:03:58
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param img
	 *            图片控件
	 * @param path
	 *            图片路径
	 * @param position
	 *            图片位置
	 */
	public static void setLocalVideoImage(final ImageView img, final String path, final int position) {
		if (path == null) {
			asyncLoadVideo.setDefaul(img);
			return;
		}
		img.setTag(position);
		asyncLoadVideo.loadImageBitmap(img.getContext(), img, position, path, null, new AsyncImageloader.ImageCallback() {
			@Override
			public void imageLoaded(int position, Bitmap bitmap, String imagePath) {
				Object tag = img.getTag();
				if (tag == null || tag.equals(position)) {
					img.setScaleType(ScaleType.CENTER_CROP);
					img.setImageBitmap(bitmap);
				}
			}
		}, true);
	}

	/**
	 * 设置媒体库图片
	 * 
	 * @version 1.0
	 * @createTime 2014年6月4日,上午10:03:37
	 * @updateTime 2014年6月4日,上午10:03:37
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param img
	 *            图片控件
	 * @param photoId
	 *            图片在媒体库里边的id
	 * @param position
	 *            图片在列表的位置
	 */
	public static void setMediaImage(final ImageView img, final int photoId, final int position) {
		if (photoId == 0) {
			asyncLoadImage.setDefaul(img);
			return;
		}
		img.setTag(position);
		asyncLoadImage.loadImageBitmap(img.getContext(), img, position, photoId, null, new AsyncImageloader.ImageCallback() {
			@Override
			public void imageLoaded(int position, Bitmap bitmap, String imagePath) {
				Object tag = img.getTag();
				if (tag == null || tag.equals(position)) {
					img.setScaleType(ScaleType.CENTER_CROP);
					img.setImageBitmap(bitmap);
				}
			}
		}, true);
	}

	/**
	 * 描述：异步设模糊头像
	 * 
	 * @version 1.0
	 * @createTime 2014-12-1,下午3:49:57
	 * @updateTime 2014-12-1,下午3:49:57
	 * @createAuthor LiGang
	 * @updateAuthor LiGang
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param img
	 *            图片控件
	 * @param position
	 *            图片位置
	 * @param path
	 *            图片路径
	 * @param context
	 */
	public static void setPortrait(final ImageView img, int position, String path, final Context context) {
		if (TextUtils.isEmpty(path)) {
			asyncLoadportrait.setPortraitDefaul(img);
			return;
		}
		img.setTag(position);
//		asyncLoadportrait.loadImageBitmap(img.getContext(), img, position, ServerConfig.SERVER_IMAGE_URL + path, null, new AsyncImageloader.ImageCallback() {
//			@Override
//			public void imageLoaded(int position, Bitmap bitmap, String imagePath) {
//				Object tag = img.getTag();
//				if (tag == null || tag.equals(position)) {
//					Drawable drawable = ImageUtil.BlurImages(bitmap, context);
//					BitmapDrawable bd = (BitmapDrawable) drawable;
//					bitmap = bd.getBitmap();
//					bitmap = OperationImage.cutRound(bitmap, 10000);
//					img.setImageBitmap(bitmap);
//				}
//			}
//		}, true, null);
	}
}
