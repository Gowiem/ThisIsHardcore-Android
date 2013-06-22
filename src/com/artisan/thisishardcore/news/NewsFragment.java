package com.artisan.thisishardcore.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.artisan.thisishardcore.FeedFragment;
import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.TIHWebViewActivity;
import com.artisan.thisishardcore.UnifeedFragment;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHNewsItem;
import com.artisan.thisishardcore.models.TIHNewsList;
import com.artisan.thisishardcore.unifeed.TIHConstants;
import com.unifeed.webservice.ResponseListener;

public class NewsFragment extends FeedFragment implements ResponseListener {
	private static final TIHLogger logger = new TIHLogger(NewsFragment.class); 
	
	// Lifecycle
	/////////////

	public static NewsFragment newInstance() {
		logger.d("newInstance");
		NewsFragment contentFragment = new NewsFragment();
		Bundle args = new Bundle();
		contentFragment.setArguments(args);
		
		return contentFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		logger.d("onCreate");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
							 ViewGroup container,
							 Bundle savedInstanceState) {
		logger.d("onCreateView");
		View result = inflater.inflate(R.layout.news, container, false);
		
		listView = (ListView) result.findViewById(R.id.listview);
		officialTabImageView = (ImageView) result.findViewById(R.id.official_tab);
		fanTabImageView = (ImageView) result.findViewById(R.id.fan_feed_tab);
		
		// Setup click listeners for the list items and the tabs
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				listItemClicked(parent, view, position, id);
			};
		});
		
		officialTabImageView.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) { officialTabClicked(v); }
		});
		
		fanTabImageView.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) { fanTabClicked(v); }
		});
		
		return(result); 
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    logger.d("onActivityCreated");
		
	    // Setup content view
	    setContentView(R.layout.news);
	    // Setup text for empty content
	    // setEmptyText(R.string.news_empty);
		if (currentTab == null) {
			officialTabClicked(officialTabImageView);	
		} else {
			updateForCurrentTab();
		}
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		logger.d("onDestroyView");
		currentTab = null;
	}
	
	// FeedFragment Methods
	////////////////////////
	
	public void sendRequest(String tabIdentifier) {
		if (tabIdentifier.equals(OFFICIAL_TAB)) {
			sendNewsRequest(0, TIHConstants.RESULT_PER_REQUEST, TIHConstants.GET_OFFICIAL_NEWS);
		} else if (tabIdentifier.equals(FAN_TAB)) {
			sendNewsRequest(0, TIHConstants.RESULT_PER_REQUEST, TIHConstants.GET_FAN_NEWS);
		} else {
			logger.d("sendRequest tabIdentifier: ", tabIdentifier, "didn't equal one of the expected values");
		}
	}
	
	public void updateUI(String tabIdentifier) {
		logger.d("UpdateUI called with tabIdentifier:", tabIdentifier);
		if (tabIdentifier.equals(OFFICIAL_TAB)) {
			logger.d("UpdateUI -- Updating for OFFICIAL TAB");
			updateNewsUI((TIHNewsList)officialList, TIHConstants.GET_OFFICIAL_NEWS);
		} else if (tabIdentifier.equals(FAN_TAB)) {
			logger.d("UpdateUI -- Updating for FAN TAB");
			updateNewsUI((TIHNewsList)fanList, TIHConstants.GET_FAN_NEWS);
		} else {
			logger.d("updateUI tabIdentifier: ", tabIdentifier, "didn't equal one of the expected values");
		}
	}
	
	// List Functionality
	//////////////////////
	
	private void listItemClicked(AdapterView<?> parent, View view, int position, long id) {
		TIHNewsItem itemClicked = null;
		// Don't allow users to check out fan news items
		if (currentTab.equals(FAN_TAB)) {
			return;
		}	
		itemClicked = ((TIHNewsList)officialList).getNewsItemAtIndex(position);
		if (itemClicked != null) {
			Intent webViewIntent = new Intent(getActivity(), TIHWebViewActivity.class);
			webViewIntent.putExtra(TIHWebViewActivity.WEB_VIEW_URL, itemClicked.getUrl());
			startActivity(webViewIntent);
		}
	}
	
	// UnifeedFragment Overrides
	//////////////////////////////

	@Override
	public void onResponseReceived(Object response, int requestType) {
		logger.d("onResponseReceived");
		super.onResponseReceived(response, requestType);
		if(response != null){
			TIHNewsList newsList = (TIHNewsList)response;
			updateNewsUI(newsList, requestType);
		}
	}
	
	//updating event view after getting response from server
	private void updateNewsUI(TIHNewsList newsList, int requestType) {
		super.updateUI(newsList, requestType);
		logger.d("updateNewsUI");
		if (newsList != null && !newsList.newsItems.isEmpty()) {
			String tabIdentifier;
			if (requestType == TIHConstants.GET_FAN_NEWS) {
				this.fanList = newsList;
				tabIdentifier = FAN_TAB;
			} else if (requestType == TIHConstants.GET_OFFICIAL_NEWS) {
				this.officialList = newsList;
				tabIdentifier = OFFICIAL_TAB;
			} else {
				logger.e("updateNewsUI - requestType was not one of the expected values. Something went wrong");
				return;
			}
			((ListView) getView().findViewById(R.id.listview))
					.setAdapter(new NewsListAdapter(getView().getContext(), newsList, tabIdentifier));
		}
	}
}
