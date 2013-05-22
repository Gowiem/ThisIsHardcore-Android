package com.artisan.thisishardcore.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.news.NewsListAdapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class TIHUtils {
	private static final TIHLogger logger = new TIHLogger(TIHUtils.class);

	public static Bitmap getBitmapFromURL(String src) {
		try {
			Log.e("src",src);
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("Exception",e.getMessage());
			return null;
		}
	}

	public static boolean isEmpty(String string) {
		if (string == null || string.trim().length() <= 0) {
			return true;
		} else {
			return false;
		}
	}

}
