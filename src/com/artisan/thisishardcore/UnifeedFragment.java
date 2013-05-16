package com.artisan.thisishardcore;



import org.apache.log4j.Logger;

import com.actionbarsherlock.app.SherlockFragment;
import com.artisan.thisishardcore.unifeed.TIHRequestManager;

public class UnifeedFragment extends SherlockFragment {
	private final Logger log = Logger.getLogger(UnifeedFragment.class);
	
	//event request
	public void sendEventRequest(){
		log.debug("UnifeedFragment - sendEventRequest");
		log.debug("UnifeedFragment - Sending getEventDetails with fragment: " + this);
		TIHRequestManager.getEvents((SherlockFragment)this);
	}
	
	public void sendNewsRequest(int pageNum, int pageSize, String feedType) {
		log.debug("UnifeedFragment - sendNewsRequest");
		TIHRequestManager.getNews((SherlockFragment)this, pageNum, pageSize, feedType);
	}

}
