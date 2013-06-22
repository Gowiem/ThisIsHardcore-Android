package com.artisan.thisishardcore;

import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHNewsList;
import com.artisan.thisishardcore.unifeed.TIHConstants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;


public class PhotoPitFragment extends FeedFragment {
	private static final TIHLogger logger = new TIHLogger(PhotoPitFragment.class);
	
	// Lifecycle
	/////////////

	protected static PhotoPitFragment newInstance() {
		logger.d("newInstance");
		PhotoPitFragment contentFragment = new PhotoPitFragment();
		Bundle args = new Bundle();
		contentFragment.setArguments(args);
		
		return contentFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
							 ViewGroup container,
							 Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.photo_pit, container, false);
		return(result); 
	}
	
	// FeedFragment Methods
	////////////////////////
	
	@Override
	public void sendRequest(String tabIdentifier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUI(String tabIdentifier) {
		// TODO Auto-generated method stub
		
	}

}
