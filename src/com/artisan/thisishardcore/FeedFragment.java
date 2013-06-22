package com.artisan.thisishardcore;

import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.news.NewsFragment;
import com.artisan.thisishardcore.unifeed.TIHConstants;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

public abstract class FeedFragment extends UnifeedFragment {
	private static final TIHLogger logger = new TIHLogger(FeedFragment.class);
	
	// Shared Fields
	/////////////////
	
	// Tab Identifiers
	public static final String OFFICIAL_TAB = "OFFICIAL_TAB";
	public static final String FAN_TAB = "FAN_TAB";
	
	// Views
	public ImageView officialTabImageView;
	public ImageView fanTabImageView;
	public ListView listView;
	
	public TIHFeedList officialList;
	public TIHFeedList fanList;
	
	public abstract void sendRequest(String tabIdentifier);
	public abstract void updateUI(String tabIdentifier);

	@Override
	public void updateForCurrentTab() {
		if (currentTab.equals(OFFICIAL_TAB)) {
			officialTabClicked(officialTabImageView);
		} else if (currentTab.equals(FAN_TAB)) {
			fanTabClicked(fanTabImageView);
		} else {
			logger.e("updateListForCurrentTab -- currentTab was not one of the expected values");
		}
	}
	
	public void officialTabClicked(View v) {
		logger.d("--- officialTabClicked ---");
		logger.d("currentTab:", currentTab);
		if (currentTab == null || !currentTab.equals(OFFICIAL_TAB)) {
			currentTab = OFFICIAL_TAB;
			logger.d("Sending or Updating for official tab");
			
			// If we haven't sent the request yet then send it, otherwise update the official list 
			// with the official feed items
			if (officialList == null) {
				sendRequest(OFFICIAL_TAB);	
			} else {
				updateUI(OFFICIAL_TAB);
			}
			
			// Swap the tab images 
			officialTabImageView.setImageResource(R.drawable.official_blue);
			fanTabImageView.setImageResource(R.drawable.fan_feed_grey);	
		}
	}
	
	public void fanTabClicked(View v) {
		logger.d("fanTabClicked");
		if (currentTab == null || !currentTab.equals(FAN_TAB)) {
			currentTab = FAN_TAB;
			
			// If we haven't sent the request yet then send it, otherwise update the fan list 
			// with the fan feed list 
			if (fanList == null) {
				sendRequest(FAN_TAB);
			} else {
				updateUI(FAN_TAB);
			}
			
			// Swap the tab images
			officialTabImageView.setImageResource(R.drawable.official_grey);
			fanTabImageView.setImageResource(R.drawable.fan_feed_blue);
		}
	}

}
