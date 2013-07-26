package com.artisan.thisishardcore.utils;


import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Instant;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.imageutils.ImageCache;
import com.artisan.thisishardcore.imageutils.ImageFetcher;
import com.artisan.thisishardcore.logging.TIHLogger;

public class TIHUtils {
	private static final TIHLogger logger = new TIHLogger(TIHUtils.class);
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm a - EEE MMM dd, yyyy");
	
	private static final String IMAGE_CACHE_DIR = "images";
	public static boolean runningTestSuite = true;
	
	public static String convertEpochTimeToString(long epochTime) {
		Date epochDate = convertEpochTimeToDate(epochTime);
		String dateString = dateFormatter.format(epochDate);
		// //logger.d("Converted epochInt:", epochTime, "to date:", epochDate, "and then to date string:", dateString);
		return dateString;
	}
	
	public static Date convertEpochTimeToDate(long epochTime) {
		Date epochDate = new Date(epochTime*1000);
		return epochDate;
	}
	
	public static DateTime convertEpochTimeToDateTime(long epochTime) {
		Instant epochDateTime = new Instant(epochTime * 1000);
		return epochDateTime.toDateTime();
	}

	public static boolean isEmpty(String string) {
		if (string == null || string.trim().length() <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static ImageFetcher newImageFetcher(Context context, float memCacheSize, android.support.v4.app.FragmentManager supportFragmentManager) {
		ImageCache.ImageCacheParams cacheParams =
			new ImageCache.ImageCacheParams(context, IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(memCacheSize); // Set memory cache to 25% of app memory
		ImageFetcher imageFetcher = new ImageFetcher(context, 0);
		imageFetcher.addImageCache(supportFragmentManager, cacheParams);
		return imageFetcher;
	}
	
    /**
    * Simple network connection check.
    *
    * @param context
    */
    private void checkConnection(Context context) {
        final ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            Toast.makeText(context, R.string.no_network_connection_toast, Toast.LENGTH_LONG).show();
            logger.e("checkConnection - no connection found");
        }
    }
	
	public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		private ImageView imageView;
		private View progressView;
		private String url;		
		
		public DownloadImageTask(ImageView imageView) {
			this.imageView = imageView;
		}

		public DownloadImageTask(ImageView imageView, View progressView) {
			this.imageView = imageView;
			this.progressView = progressView;
			if (progressView != null) {
				progressView.setVisibility(View.VISIBLE);
			}
		}

		protected Bitmap doInBackground(String... urls) {
			url = urls[0];
			Bitmap bitmapImage = null;
			try {
				InputStream inputStream = new URL(url).openStream();
				bitmapImage = BitmapFactory.decodeStream(inputStream);
			} catch (Exception e) {
				logger.e("Error trying to get bitmap from URL:", url, "Error message: ", e.getMessage());
				e.printStackTrace();
			}
			return bitmapImage;
		}

		protected void onPostExecute(final Bitmap result) {
			Handler handler = new Handler();
			//logger.d("onPostExecute()");
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					imageView.setImageBitmap(result);
					if (progressView != null) {
						progressView.setVisibility(View.GONE);
					}
				}        
			};
			handler.postDelayed(runnable, 500);
		}
	}

}
