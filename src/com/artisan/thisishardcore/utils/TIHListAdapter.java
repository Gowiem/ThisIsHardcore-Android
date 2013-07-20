package com.artisan.thisishardcore.utils;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.imageutils.ImageFetcher;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.commonsware.cwac.endless.EndlessAdapter;
import com.commonsware.cwac.endless.demo.EndlessAdapterCustomTaskFragment.FetchDataTask;

public abstract class TIHListAdapter<T> extends EndlessAdapter {
	private static final TIHLogger logger = new TIHLogger(TIHListAdapter.class);
	
	public static final String JOE_PROFILE_IMAGE_URL = "http://a0.twimg.com/profile_images/1372387612/tihsmall_normal.jpg";
	public final Context context;	
	
	public TIHListAdapter(Context context, int textViewResourceId, List objects) {
		super(new ArrayAdapter<Integer>(context,
                                    R.layout.row,
                                    objects));
//		super(context, textViewResourceId, objects);
		this.context = context;
	}
	// EndlessAdapter Methods
	//////////////////////////
    @Override
    protected View getPendingView(ViewGroup parent) {
    	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	View pendingView = inflater.inflate(R.layout.row_loading, parent, false);

    	return(pendingView);
    }

    @Override
    protected boolean cacheInBackground() throws Exception {
      new FetchDataTask(this, items.size()).execute();
      
      return(true);
    }
	
	
	// Image Fetcher Helper
	///////////////////////
	public void loadIconForProfileUrl(String profileImageUrl, ImageView imageView, ImageFetcher imageFetcher) {
		// If we have the profile image url then grab it from the cache or the internet
		if (!TIHUtils.isEmpty(profileImageUrl) && !profileImageUrl.equalsIgnoreCase(this.JOE_PROFILE_IMAGE_URL)) {
			try {
				logger.d("Requesting profile image url: ", profileImageUrl);
				imageFetcher.loadImage(profileImageUrl, imageView);
			} catch (Exception e) {
				logger.d("Exception throw by imageFetcher.loadImage for profileImageUrl: ", profileImageUrl);
			}
		}
	}
}
