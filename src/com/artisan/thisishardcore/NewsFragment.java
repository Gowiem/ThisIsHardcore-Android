package com.artisan.thisishardcore;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.unifeed.webservice.ResponseListener;

public class NewsFragment extends UnifeedFragment implements ResponseListener {
	private final Logger log = Logger.getLogger(NewsFragment.class); 
	public static final String OFFICIAL_FEED = "OFFICIAL_FEED";
	public static final String FAN_FEED      = "FAN_FEED";

	protected static NewsFragment newInstance() {
		NewsFragment contentFragment = new NewsFragment();
		Bundle args = new Bundle();
		contentFragment.setArguments(args);
		
		return contentFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sendNewsRequest(0, 20, OFFICIAL_FEED);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
							 ViewGroup container,
							 Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.news, container, false);
		return(result); 
	}

	@Override
	public void onResponseReceived(Object response, int requestType) {
		log.debug("onResponseReceived - reponse: " + response);
	}
}
