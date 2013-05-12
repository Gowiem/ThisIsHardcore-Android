package com.artisan.thisishardcore.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class TIHUtils {

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
