package com.artisan.thisishardcore.utils;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.artisan.thisishardcore.logging.TIHLogger;

public abstract class TIHListAdapter<T> extends ArrayAdapter<T> {
	private static final TIHLogger logger = new TIHLogger(TIHListAdapter.class);
	
	private final Context context;
	protected Map<String, Bitmap> networkImages;
	
	public TIHListAdapter(Context context, int textViewResourceId, List objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.networkImages = new HashMap<String, Bitmap>();
	}
	
	public class CachingDownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		private ImageView imageView;
		private View progressView;
		private String url;

		public CachingDownloadImageTask(ImageView imageView) {
			this.imageView = imageView;
		}
		
		public CachingDownloadImageTask(ImageView imageView, View progressView) {
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

		protected void onPostExecute(Bitmap result) {
			networkImages.put(url, result);
			if (progressView != null) {
				progressView.setVisibility(View.GONE);
			}
			imageView.setImageBitmap(result);
		}
	}
}
