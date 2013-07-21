package com.artisan.thisishardcore.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.artisan.thisishardcore.imageutils.ImageFetcher;
import com.artisan.thisishardcore.logging.TIHLogger;

public abstract class TIHListAdapter<T> extends ArrayAdapter<T> {
	private static final TIHLogger logger = new TIHLogger(TIHListAdapter.class);
	
	public static final String JOE_PROFILE_IMAGE_URL = "http://a0.twimg.com/profile_images/1372387612/tihsmall_normal.jpg";
	public final Context context;
	public int lastViewed;
	
	public TIHListAdapter(Context context, int textViewResourceId, List objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
	}
	
	public void loadIconForProfileUrl(String profileImageUrl, ImageView imageView, ImageFetcher imageFetcher) {
		// If we have the profile image url then grab it from the cache or the internet
		if (!TIHUtils.isEmpty(profileImageUrl) && !profileImageUrl.equalsIgnoreCase(this.JOE_PROFILE_IMAGE_URL)) {
			try {
				imageFetcher.loadImage(profileImageUrl, imageView);
			} catch (Exception e) {
				logger.d("Exception throw by imageFetcher.loadImage for profileImageUrl: ", profileImageUrl);
			}
		}
	}
}
