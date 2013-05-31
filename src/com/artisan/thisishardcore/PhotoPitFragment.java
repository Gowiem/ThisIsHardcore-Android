package com.artisan.thisishardcore;

import com.artisan.thisishardcore.logging.TIHLogger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PhotoPitFragment extends UnifeedFragment {
	private static final TIHLogger logger = new TIHLogger(PhotoPitFragment.class);

	protected static PhotoPitFragment newInstance() {
		logger.d("PhotoPitFragment - newInstance");
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

	public void updateForCurrentTab() {
		
	}
}
