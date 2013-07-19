package com.artisan.thisishardcore;

import com.actionbarsherlock.app.SherlockFragment;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.progress.ProgressSherlockFragment;
import com.artisan.thisishardcore.progress.SherlockProgressFragment;
import com.artisan.thisishardcore.unifeed.TIHRequestManager;
import com.artisan.thisishardcore.unifeed.TIHResponseListener;

public abstract class UnifeedFragment extends SherlockProgressFragment implements TIHResponseListener {
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
			try {
				setContentEmpty(true);
				setContentShown(true);
			} catch (IllegalStateException e) {
				logger.d("IllegalStateException on onResponseReceived::setContentEmpty|setContentShown");
			}
		}
	}
	
	public void updateUI(Object list, int requestType) {
		logger.d("UnifeedFragment UpdateUI");
		try {
			setContentShown(true);	
		} catch (IllegalStateException e) {
			logger.d("IllegalStateException on updateUI::setContentShown");
		}
	}
	
	public void updateUI(Object list) {
		logger.d("UnifeedFragment UpdateUI");
		try {
			setContentShown(true);	
		} catch (IllegalStateException e) {
			logger.d("IllegalStateException on updateUI::setContentShown");
		}
	}
	
	// Request Methods
	///////////////////
	
	public void sendEventRequest(){
		logger.d("--- sendEventRequest ---");
		setContentShown(false);
		TIHRequestManager.getEvents((SherlockFragment)this);
	}
	
	public void sendNewsRequest(int pageNum, int pageSize, int feedType) {
		logger.d("--- sendNewsRequest ---");
		setContentShown(false);
		TIHRequestManager.getNews((SherlockFragment)this, pageNum, pageSize, feedType);
	}
	
	public void sendPhotoPitRequest(int pageNum, int pageSize, int feedType) {
		logger.d("--- sendPhotoPitRequest ---");
		setContentShown(false);
		TIHRequestManager.getPhotos((SherlockFragment)this, pageNum, pageSize, feedType);
	}

}
