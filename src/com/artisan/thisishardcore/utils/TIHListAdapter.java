package com.artisan.thisishardcore.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ArrayAdapter;

import com.artisan.thisishardcore.logging.TIHLogger;

public abstract class TIHListAdapter<T> extends ArrayAdapter<T> {
	private static final TIHLogger logger = new TIHLogger(TIHListAdapter.class);
	
	private final Context context;
	
//	public static DiskLruCache diskCache;
//	private boolean diskCacheStarting = true;
//	private final Object diskCacheLock = new Object();
//	private static final int DISK_CACHE_SIZE = 1024 * 1024 * 30; // 30MB
//	public static final String DISK_CACHE_SUBDIR = "tihcImages";
	
	protected Map<String, Bitmap> networkImages;
	
	public TIHListAdapter(Context context, int textViewResourceId, List objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.networkImages = new HashMap<String, Bitmap>();
	}
	
//	private void setupDiskCache() {
//		logger.d("-- setupDiskCache called --");
//		File cacheDir = getDiskCacheDir(context, DISK_CACHE_SUBDIR);
//	    new InitDiskCacheTask().execute(cacheDir);
//	}
//	
//	// Creates a unique subdirectory of the designated app cache directory. Tries to use external
//	// but if not mounted, falls back on internal storage.
//	public static File getDiskCacheDir(Context context, String uniqueName) {
//	    // Check if media is mounted or storage is built-in, if so, try and use external cache dir
//	    // otherwise use internal cache dir
//	    final String cachePath;
//	    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
//	                    !Environment.isExternalStorageRemovable()) {
//	    	cachePath = context.getExternalCacheDir().getPath();
//	    } else {
//	    	cachePath = context.getCacheDir().getPath();
//	    }
//
//	    return new File(cachePath + File.separator + uniqueName);
//	}
//	
//	public class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
//	    @Override
//	    protected Void doInBackground(File... params) {
//	    	synchronized (diskCacheLock) {
//	            File cacheDir = params[0];
//	            try {
//					diskCache = DiskLruCache.open(cacheDir, 1, 1, DISK_CACHE_SIZE);
//				} catch (IOException e) {
//					logger.e("error with opening disk cache");
//					e.printStackTrace();
//				}
//	            diskCacheStarting = false; // Finished initialization
//	            diskCacheLock.notifyAll(); // Wake any waiting threads
//	        }
//	        return null;
//	    }
//	}
	
//	public void addBitmapToCache(String key, Bitmap bitmap) {
//	    // Add to memory cache as before
////	    if (getBitmapFromMemCache(key) == null) {
////	        mMemoryCache.put(key, bitmap);
////	    }
//
//	    // Also add to disk cache
//	    synchronized (diskCacheLock) {
//	        if (diskCache != null && diskCache.get(key) == null) {
//	        	diskCache.put(key, bitmap);
//	        }
//	    }
//	}
//
//	public Bitmap getBitmapFromDiskCache(String key) {
//	    synchronized (diskCacheLock) {
//	        // Wait while disk cache is started from background thread
//	        while (diskCacheStarting) {
//	            try {
//	                diskCacheLock.wait();
//	            } catch (InterruptedException e) {}
//	        }
//	        if (diskCache != null) {
//	            return diskCache.get(key);
//	        }
//	    }
//	    return null;
//	}
	
//	public class CachingDownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//		private ImageView imageView;
//		private View progressView;
//		private String url;
//
//		public CachingDownloadImageTask(ImageView imageView) {
//			this.imageView = imageView;
//		}
//		
//		public CachingDownloadImageTask(ImageView imageView, View progressView) {
//			this.imageView = imageView;
//			this.progressView = progressView;
//			if (progressView != null) {
//				progressView.setVisibility(View.VISIBLE);
//			}
//		}
//
//		protected Bitmap doInBackground(String... urls) {
//		    
//			
//			
//			url = urls[0];
//			Bitmap bitmapImage = null;
//			try {
//				InputStream inputStream = new URL(url).openStream();
//				bitmapImage = BitmapFactory.decodeStream(inputStream);
//			} catch (Exception e) {
//				logger.e("Error trying to get bitmap from URL:", url, "Error message: ", e.getMessage());
//				e.printStackTrace();
//			}
//			return bitmapImage;
//		}
//
//		protected void onPostExecute(Bitmap result) {
//			networkImages.put(url, result);
//			if (progressView != null) {
//				progressView.setVisibility(View.GONE);
//			}
//			imageView.setImageBitmap(result);
//		}
//	}
}
