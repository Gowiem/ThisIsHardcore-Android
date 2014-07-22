package com.useartisan.artisan.thisishardcore;

import android.R.anim;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.useartisan.artisan.thisishardcore.logging.TIHLogger;
import com.useartisan.artisan.thisishardcore.progress.SherlockProgressFragment;
import com.useartisan.artisan.thisishardcore.unifeed.TIHRequestManager;
import com.useartisan.artisan.thisishardcore.unifeed.TIHResponseListener;

public abstract class UnifeedFragment extends SherlockProgressFragment implements TIHResponseListener {
	private final TIHLogger logger = new TIHLogger(UnifeedFragment.class);
	
	// Shared fields
	/////////////////
	
	public String currentTab;
	
	// Abstract Methods
	////////////////////
	
	abstract public void updateForCurrentTab();
	
	// Lifecycle
	/////////////
	
	// Response Methods
	////////////////////
	
	public void onResponseReceived(Object response, int requestType) {
		if (response == null) {
			//logger.d("onResponseRecieved, reponse was null. RequestType was:", requestType, "Showing Empty Message.");
			try {
				hideContentViews();
				setContentEmpty(true);
				setContentShown(true);
			} catch (IllegalStateException e) {
				//logger.d("IllegalStateException on onResponseReceived::setContentEmpty|setContentShown");
			}
		}
	}
	
	public void updateUI(Object list, int requestType) {
		//logger.d("UnifeedFragment UpdateUI");
		try {
			setContentShown(true);
		} catch (IllegalStateException e) {
			//logger.d("IllegalStateException on updateUI::setContentShown");
		}
	}
	
	public void updateUI(Object list) {
		//logger.d("UnifeedFragment UpdateUI");
		try {
			setContentShown(true);	
		} catch (IllegalStateException e) {
			//logger.d("IllegalStateException on updateUI::setContentShown");
		}
	}
	
	// Hide/Show Views
	///////////////////
	
	private void hideContentViews() {
		ViewGroup contentContainer = (ViewGroup) getView().findViewById(R.id.content_container);
		for (int i = 0; i < contentContainer.getChildCount(); i++) {
			View childView = contentContainer.getChildAt(i);
			if (childView.getId() != android.R.id.empty) {
				childView.setVisibility(View.GONE);
			}
		}
	}
	
	private void showContentViews() {
		ViewGroup contentContainer = (ViewGroup) getContentView();
		for (int i = 0; i < contentContainer.getChildCount(); i++) {
			View childView = contentContainer.getChildAt(i);
			if (childView.getId() != android.R.id.empty) {
				childView.setVisibility(View.VISIBLE);
			}
		}
	}
	
	// Request Methods
	///////////////////
	
	public void sendEventRequest(){
		//logger.d("--- sendEventRequest ---");
		setContentShown(false); // Show the progress fragment
		TIHRequestManager.getEvents((SherlockFragment)this);
	}
	
	public void sendNewsRequest(int pageNumber, int pageSize, int feedType) {
		//logger.d("--- sendNewsRequest ---");
		showProgressFragment(pageNumber);
		TIHRequestManager.getNews((SherlockFragment)this, pageNumber, pageSize, feedType);
	}
	
	public void sendPhotoPitRequest(int pageNumber, int pageSize, int feedType) {
		//logger.d("--- sendPhotoPitRequest ---");
		showProgressFragment(pageNumber);
		TIHRequestManager.getPhotos((SherlockFragment)this, pageNumber, pageSize, feedType);
	}
	
	private void showProgressFragment(int pageNumber) {
		// We only want to show the progress fragment when we are loading the first page 
		if (pageNumber == 1) {
			setContentShown(false);
		}
	}

}
