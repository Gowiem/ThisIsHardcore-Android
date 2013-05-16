package com.artisan.thisishardcore;



import org.apache.log4j.Logger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PhotoPitFragment extends UnifeedFragment {
	private final static Logger log = Logger.getLogger(PhotoPitFragment.class);

	protected static PhotoPitFragment newInstance() {
		log.debug("PhotoPitFragment - newInstance");
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

	
}
