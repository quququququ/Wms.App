package com.wms.newwmsapp.tool;

import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public final class ImageManager {

	public static final String QINIU_WEBP_FORMAT = "?imageMogr2/format/webp";
	private static ImageLoader imageLoader;
	private static BitmapConfig bitmapConfig;

	public static class BitmapConfig {

		public static final int JPEG = 0;
		public static final int PNG = 1;
		public static final int WEBP = 2;
		public static final int OTHER = 3;

		public static final int MAX_SIZE = 1024;

		public int allowByteCount = MAX_SIZE;
		public int imageFormat = OTHER;
	}

	static {
		if (imageLoader == null) {
			imageLoader = ImageLoader.getInstance();
		}

		if (bitmapConfig == null) {
			bitmapConfig = new BitmapConfig();
		}
	}

	public static ImageLoader getImageLoader() {
		return imageLoader;
	}

	public static BitmapConfig getBitmapConfig() {
		return bitmapConfig;
	}

	private static String formatUrl(String url) {
		String format = url;
		if (bitmapConfig.imageFormat == BitmapConfig.WEBP) {
			format = url + QINIU_WEBP_FORMAT;
		}
		return format;
	}

	public static void displayNetworkImage(String url, ImageView imageView,
			DisplayImageOptions displayImageOptions,
			ImageLoadingListener listener) {
		imageLoader.displayImage(formatUrl(url), imageView,
				displayImageOptions, listener);
	}

	public static void displayNetworkImage(String url, ImageView imageView,
			ImageLoadingListener listener) {
		imageLoader.displayImage(formatUrl(url), imageView, listener);
	}

	public static void displayNetworkImage(String url, ImageView imageView) {
		imageLoader.displayImage(formatUrl(url), imageView);
	}

	public static void displayNetworkImage(String url, ImageView imageView,
			DisplayImageOptions displayImageOptions) {
		imageLoader
				.displayImage(formatUrl(url), imageView, displayImageOptions);
	}

	public static void displayNativeImage(String path, ImageView image,
			DisplayImageOptions options, ImageLoadingListener listener) {

		imageLoader.displayImage("file://" + path, image, options, listener);
	}
}
