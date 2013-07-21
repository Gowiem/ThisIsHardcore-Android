package com.artisan.thisishardcore;

import android.os.Bundle;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHNewsList;
import com.artisan.thisishardcore.models.TIHPhotoList;
import com.artisan.thisishardcore.unifeed.TIHConstants;


public class PhotoPitFragment extends FeedFragment {
	private static final TIHLogger logger = new TIHLogger(PhotoPitFragment.class);

	// Lifecycle
	/////////////

	protected static PhotoPitFragment newInstance() {
		logger.d("newInstance");
		PhotoPitFragment contentFragment = new PhotoPitFragment();
		contentFragment.init();
		Bundle args = new Bundle();
		contentFragment.setArguments(args);
		return contentFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.photo_pit, container, false);
		// Set up our listView, and tab image views
		onCreateViewHelper(result);

		return(result); 
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Setup content view
		setContentView(R.layout.photo_pit);
		if (currentTab == null) {
			officialTabClicked(officialTabImageView);	
		} else {
			updateForCurrentTab();
		}
	}

	// FeedFragment Methods
	////////////////////////

	@Override
	public void sendRequest(String tabIdentifier, int pageNumber) {
		isLoading = true;
		if (tabIdentifier.equals(OFFICIAL_TAB)) {
			sendPhotoPitRequest(pageNumber, TIHConstants.RESULT_PER_REQUEST, TIHConstants.GET_OFFICIAL_PHOTOS);
		} else if (tabIdentifier.equals(FAN_TAB)) {
			sendPhotoPitRequest(pageNumber, TIHConstants.RESULT_PER_REQUEST, TIHConstants.GET_FAN_PHOTOS);
		} else {
			isLoading = false;
			logger.d("sendRequest tabIdentifier: ", tabIdentifier, "didn't equal one of the expected values");
		}
	}

	@Override
	public void updateUI(String tabIdentifier) {
		logger.d("updateUI tabIdentifier: ", tabIdentifier);
		try {
			if (tabIdentifier.equals(OFFICIAL_TAB)) {
				logger.d("UpdateUI -- Updating for OFFICIAL TAB");
				updatePhotoPitUI((TIHPhotoList)officialList, TIHConstants.GET_OFFICIAL_PHOTOS);
			} else if (tabIdentifier.equals(FAN_TAB)) {
				logger.d("UpdateUI -- Updating for FAN TAB");
				updatePhotoPitUI((TIHPhotoList)fanList, TIHConstants.GET_FAN_PHOTOS);
			} else {
				logger.d("updateUI tabIdentifier: ", tabIdentifier, "didn't equal one of the expected values");
			}
		} catch (NullPointerException e) {
			logger.d("updateUI - NullPointerException throw by updateUI");
		}
	}

	// UnifeedFragment Overrides
	//////////////////////////////

	@Override
	public void onResponseReceived(Object response, int requestType) {
		logger.d("onResponseReceived");
		super.onResponseReceived(response, requestType);
		if(response != null){
			TIHPhotoList photoList = (TIHPhotoList)response;
			updatePhotoPitUI(photoList, requestType);
		}
	}

	// TODO: Can be abstracted out to FeedFragment
	private void updatePhotoPitUI(TIHPhotoList photoList, int requestType) {
		try {
			super.updateUI(photoList, requestType);
			logger.d("updatePhotoUI");
			if (photoList != null && !photoList.items.isEmpty()) {
				boolean feedListCreated;
				if (requestType == TIHConstants.GET_FAN_PHOTOS) {
					this.fanList = createOrUpdateModel(photoList, this.fanList, FAN_TAB);
					feedListCreated = this.fanList.getWasJustCreated();
					updateListView(this.fanList, feedListCreated, FAN_TAB);
				} else if (requestType == TIHConstants.GET_OFFICIAL_PHOTOS) {
					this.officialList = createOrUpdateModel(photoList, this.officialList, OFFICIAL_TAB);
					feedListCreated = this.officialList.getWasJustCreated(); 
					updateListView(this.officialList, feedListCreated, OFFICIAL_TAB);
				} else {
					logger.e("updatePhotoUI - requestType was not one of the expected values. Something went wrong");
				}
			}
		} catch (NullPointerException e) {
			logger.d("updatePhotoPitUI threw NullPointerException");
		}
		isLoading = false;
	}
}
