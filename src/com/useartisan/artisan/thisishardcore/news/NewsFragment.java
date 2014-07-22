package com.useartisan.artisan.thisishardcore.news;

import java.nio.channels.SelectableChannel;
import java.util.List;
import java.util.logging.Logger;

import org.apache.log4j.jmx.LoggerDynamicMBean;

import android.R.integer;
import android.R.raw;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.useartisan.artisan.thisishardcore.FeedFragment;
import com.useartisan.artisan.thisishardcore.R;
import com.useartisan.artisan.thisishardcore.TIHWebViewActivity;
import com.useartisan.artisan.thisishardcore.logging.TIHLogger;
import com.useartisan.artisan.thisishardcore.models.TIHFeedItem;
import com.useartisan.artisan.thisishardcore.models.TIHFeedList;
import com.useartisan.artisan.thisishardcore.models.TIHNewsItem;
import com.useartisan.artisan.thisishardcore.models.TIHNewsList;
import com.useartisan.artisan.thisishardcore.unifeed.TIHConstants;
import com.useartisan.artisan.thisishardcore.utils.TIHListAdapter;
import com.useartisan.artisan.thisishardcore.utils.TIHUtils;

public class NewsFragment extends FeedFragment {
	private static final TIHLogger logger = new TIHLogger(NewsFragment.class); 
	
	// Lifecycle
	/////////////

	public static NewsFragment newInstance() {
		//logger.d("newInstance");
		NewsFragment contentFragment = new NewsFragment();
		contentFragment.init();
		Bundle args = new Bundle();
		contentFragment.setArguments(args);
		
		return contentFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		logger.i("onCreate");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
							 ViewGroup container,
							 Bundle savedInstanceState) {
		logger.i("onCreateView");
		View result = inflater.inflate(R.layout.news, container, false);
		
		// Set up our listView, and tab image views
		onCreateViewHelper(result);
		
		// Setup click listener for the list items
		officalListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				listItemClicked(parent, view, position, id);
			};
		});
		
		fanListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				listItemClicked(parent, view, position, id);
			};
		});
				
		return(result); 
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    //logger.d("onActivityCreated");
		
	    // Setup content view
	    setEmptyText(R.string.news_empty);
	    setContentView(R.layout.news);
		if (currentTab == null) {
			officialTabClicked(officialTabImageView);	
		} else {
			updateForCurrentTab();
		}
	}
	
	// FeedFragment Methods
	////////////////////////
	
	public void sendRequest(String tabIdentifier, int pageNumber) {
		isLoading = true;
		if (tabIdentifier.equals(OFFICIAL_TAB)) {
			sendNewsRequest(pageNumber, TIHConstants.RESULT_PER_REQUEST, TIHConstants.GET_OFFICIAL_NEWS);
		} else if (tabIdentifier.equals(FAN_TAB)) {
			sendNewsRequest(pageNumber, TIHConstants.RESULT_PER_REQUEST, TIHConstants.GET_FAN_NEWS);
		} else {
			isLoading = false;
			//logger.d("sendRequest tabIdentifier: ", tabIdentifier, "didn't equal one of the expected values");
		}
	}
	
	// Called when the User switched tabs
	public void updateUI(String tabIdentifier) {
		if (tabIdentifier.equals(OFFICIAL_TAB)) {
			//logger.d("UpdateUI -- Updating for OFFICIAL TAB");
			updateNewsUI((TIHNewsList)officialList, TIHConstants.GET_OFFICIAL_NEWS);
		} else if (tabIdentifier.equals(FAN_TAB)) {
			//logger.d("UpdateUI -- Updating for FAN TAB");
			updateNewsUI((TIHNewsList)fanList, TIHConstants.GET_FAN_NEWS);
		} else {
			//logger.d("updateUI tabIdentifier: ", tabIdentifier, "didn't equal one of the expected values");
		}
	}
	
	// Item Clicked Functionality
	//////////////////////////////
	
	private void listItemClicked(AdapterView<?> parent, View view, int position, long id) {
		TIHFeedList<?> modelList = getNewsListForTab(currentTab);
		//logger.d("modelList: " + modelList);
		TIHNewsItem itemClicked = (TIHNewsItem) modelList.getItemAtIndex(position);
		//logger.d("itemClicked: " + itemClicked);
		if (itemClicked != null && !TIHUtils.isEmpty(itemClicked.getUrl())) {
			//logger.d("itemClicked - url: " + itemClicked.getUrl());
			Intent webViewIntent = new Intent(getActivity(), TIHWebViewActivity.class);
			webViewIntent.putExtra(TIHWebViewActivity.WEB_VIEW_URL, itemClicked.getUrl());
			startActivity(webViewIntent);
		}
	}
	
	// UnifeedFragment Overrides
	/////////////////////////////

	@Override
	public void onResponseReceived(Object response, int requestType) {
		//logger.d("---- onResponseReceived ----");
		super.onResponseReceived(response, requestType);
		if(response != null){
			TIHNewsList newsList = (TIHNewsList)response;
			updateNewsUI(newsList, requestType);
		}
	}
	
	//updating feed list/list view after getting response from server
	private void updateNewsUI(TIHNewsList newsList, int requestType) {
		try {
			super.updateUI(newsList, requestType);
			//logger.d("------- updateNewsUI ------");
			if (newsList != null && !newsList.items.isEmpty()) {
				boolean feedListCreated;
				if (requestType == TIHConstants.GET_FAN_NEWS) {
					//logger.d("FAN TAB");
					this.fanList = createOrUpdateModel(newsList, this.fanList, FAN_TAB);
					feedListCreated = this.fanList.getWasJustCreated();
					updateListView(this.fanList, feedListCreated, FAN_TAB);
				} else if (requestType == TIHConstants.GET_OFFICIAL_NEWS) {
					this.officialList = createOrUpdateModel(newsList, this.officialList, OFFICIAL_TAB);
					feedListCreated = this.officialList.getWasJustCreated();
					updateListView(this.officialList, feedListCreated, OFFICIAL_TAB);
				} else {
					logger.e("updateNewsUI - requestType was not one of the expected values. Something went wrong");
				}
			}
		} catch (NullPointerException e) {
			logger.e("updateNewsUI threw NullPointerException: ", e.getLocalizedMessage());
		}
		isLoading = false;
	}
	
	// News Helpers
	////////////////
	
	private TIHFeedList<?> getNewsListForTab(String tabIdentifier) {
		if (tabIdentifier.equals(OFFICIAL_TAB)) {
			return officialList;
		} else {
			return fanList;
		}
	}
}
