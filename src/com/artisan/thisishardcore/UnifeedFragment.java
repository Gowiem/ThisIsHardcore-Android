package com.artisan.thisishardcore;

import com.actionbarsherlock.app.SherlockFragment;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.progress.ProgressSherlockFragment;
import com.artisan.thisishardcore.unifeed.TIHRequestManager;
import com.unifeed.webservice.ResponseListener;

public abstract class UnifeedFragment extends ProgressSherlockFragment implements ResponseListener {
	private final TIHLogger logger = new TIHLogger(UnifeedFragment.class);
	
	// Shared fields
	/////////////////
	
	public String currentTab;
	
	// Tab Methods
	///////////////
	
	abstract public void updateForCurrentTab();
	
	// Response Methods
	////////////////////
	
	public void onResponseReceived(Object response, int requestType) {
		if (response == null) {
			logger.d("onResponseRecieved, reponse was null. RequestType was:", requestType, 
					"Showing Emptry Message.");
			setContentEmpty(true);
			setContentShown(true);	
		}
	}
	
	public void updateUI(Object list, int requestType) {
		setContentShown(true);
	}
	
	public void updateUI(Object list) {
		setContentShown(true);
	}
	
	// Request Methods
	///////////////////
	
	public void sendEventRequest(){
		logger.d("sendEventRequest");
		setContentShown(false);
		TIHRequestManager.getEvents((SherlockFragment)this);
	}
	
	public void sendNewsRequest(int pageNum, int pageSize, int feedType) {
		logger.d("sendNewsRequest");
		setContentShown(false);
		TIHRequestManager.getNews((SherlockFragment)this, pageNum, pageSize, feedType);
	}

}
