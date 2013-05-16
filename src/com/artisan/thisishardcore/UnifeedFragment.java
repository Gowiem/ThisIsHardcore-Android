package com.artisan.thisishardcore;

import com.actionbarsherlock.app.SherlockFragment;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.unifeed.TIHRequestManager;

public class UnifeedFragment extends SherlockFragment {
	private final TIHLogger logger = new TIHLogger(UnifeedFragment.class);
	
	//event request
	public void sendEventRequest(){
		logger.d("sendEventRequest");
		TIHRequestManager.getEvents((SherlockFragment)this);
	}
	
	public void sendNewsRequest(int pageNum, int pageSize, String feedType) {
		logger.d("sendNewsRequest");
		TIHRequestManager.getNews((SherlockFragment)this, pageNum, pageSize, feedType);
	}

}
