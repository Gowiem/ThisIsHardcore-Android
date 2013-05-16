package com.artisan.thisishardcore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artisan.thisishardcore.logging.TIHLogger;
import com.unifeed.webservice.ResponseListener;

public class NewsFragment extends UnifeedFragment implements ResponseListener {
	private final TIHLogger logger = new TIHLogger(NewsFragment.class); 
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
		logger.d("onResponseReceived - reponse: ", response);
	}
}
