package com.artisan.thisishardcore.utils;


import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.ImageView;

import com.artisan.thisishardcore.logging.TIHLogger;

public class TIHUtils {
	private static final TIHLogger logger = new TIHLogger(TIHUtils.class);
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm a - EEE MMM dd, yyyy");
	
	public static boolean runningTestSuite = true;
	
	public static String convertEpochTimeToString(long epochTime) {
		Date epochDate = convertEpochTimeToDate(epochTime);
		String dateString = dateFormatter.format(epochDate);
		// logger.d("Converted epochInt:", epochTime, "to date:", epochDate, "and then to date string:", dateString);
		return dateString;
	}
	
	public static Date convertEpochTimeToDate(long epochTime) {
		Date epochDate = new Date(epochTime*1000);
		return epochDate;
	}

	public static boolean isEmpty(String string) {
		if (string == null || string.trim().length() <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		private ImageView imageView;
		private String url;

		public DownloadImageTask(ImageView imageView) {
			this.imageView = imageView;
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
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					imageView.setImageBitmap(result);	
				}        
			};
			handler.postDelayed(runnable, 310000);
		}
	}

}
