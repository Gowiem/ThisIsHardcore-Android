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

import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.TIHWebViewActivity;
import com.artisan.thisishardcore.UnifeedFragment;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHNewsItem;
import com.artisan.thisishardcore.models.TIHNewsList;
import com.artisan.thisishardcore.schedule.EventDetailActivity;
import com.artisan.thisishardcore.unifeed.TIHConstants;
import com.unifeed.webservice.ResponseListener;

public class NewsFragment extends UnifeedFragment implements ResponseListener {
	private static final TIHLogger logger = new TIHLogger(NewsFragment.class); 
	
	private String currentTab;
	private final String OFFICIAL_TAB = "OFFICIAL_TAB";
	private final String FAN_TAB = "FAN_TAB";
	
	private TIHNewsList officialNewsList;
	private TIHNewsList fanNewsList;
	
	private int failureWebServiceReq;
	private boolean isReqSent;
	
	private ImageView officialTabImageView;
	private ImageView fanTabImageView;
	private ListView listView;

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
		
		if (currentTab == null) {
			officialTabClicked(officialTabImageView);	
		} else {
			updateListForCurrentTab();
		}
		
		return(result); 
	}
	
	private void updateListForCurrentTab() {
		if (currentTab.equals(OFFICIAL_TAB)) {
			
		} else if (currentTab.equals(FAN_TAB)) {
			
		} else {
			
		}
	}
	
	private void listItemClicked(AdapterView<?> parent, View view, int position, long id) {
		TIHNewsItem itemClicked = null;
		if (currentTab.equals(OFFICIAL_TAB)) {
			itemClicked = officialNewsList.getNewsItemAtIndex(position);
		} else if (currentTab.equals(FAN_TAB)) {
			itemClicked = fanNewsList.getNewsItemAtIndex(position);
		}
		if (itemClicked != null) {
			Intent webViewIntent = new Intent(getActivity(), TIHWebViewActivity.class);
			webViewIntent.putExtra(TIHWebViewActivity.WEB_VIEW_URL, itemClicked.getUrl());
			startActivity(webViewIntent);
		}
	}
	
	public void officialTabClicked(View v) {
		logger.d("officialTabClicked");
		if (!currentTab.equals(OFFICIAL_TAB)) {
			currentTab = OFFICIAL_TAB;
			
			// If we haven't sent the request yet then send it, otherwise update the news list 
			// with the official feed items
			if (officialNewsList == null) {
				sendNewsRequest(0, TIHConstants.RESULT_PER_REQUEST, TIHConstants.GET_OFFICIAL_NEWS);	
			} else {
				updateNewsList(officialNewsList, TIHConstants.GET_OFFICIAL_NEWS);
			}
			
			// Swap the tab images 
			officialTabImageView.setImageResource(R.drawable.official_blue);
			fanTabImageView.setImageResource(R.drawable.fan_feed_grey);	
		}
	}
	
	public void fanTabClicked(View v) {
		logger.d("fanTabClicked");
		if (!currentTab.equals(FAN_TAB)) {
			currentTab = FAN_TAB;
			
			// If we haven't sent the request yet then send it, otherwise update the news list 
			// with the fan feed list 
			if (fanNewsList == null) {
				sendNewsRequest(0, TIHConstants.RESULT_PER_REQUEST, TIHConstants.GET_FAN_NEWS);		
			} else {
				updateNewsList(fanNewsList, TIHConstants.GET_FAN_NEWS);
			}
			
			// Swap the tab images
			officialTabImageView.setImageResource(R.drawable.official_grey);
			fanTabImageView.setImageResource(R.drawable.fan_feed_blue);
		}
	}

	@Override
	public void onResponseReceived(Object response, int requestType) {
		logger.d("onResponseReceived - reponse:", response);
		if(response == null){
			failureWebServiceReq = requestType;
//			getView().findViewById(R.id.connection_status_view).setVisibility(View.VISIBLE);
//			getView().findViewById(R.id.listview).setVisibility(View.GONE);
//			getView().findViewById(R.id.progress_view).setVisibility(View.GONE);
		} else {
			TIHNewsList newsList = (TIHNewsList)response;
			updateNewsList(newsList, requestType);
		}
	}
	
	//updating event view after getting response from server
	private void updateNewsList(TIHNewsList newsList, int requestType) {
		isReqSent = false;
		logger.d("updateEventsUI");
		if (newsList != null && !newsList.newsItems.isEmpty()) {
			if (requestType == TIHConstants.GET_FAN_NEWS) {
				this.fanNewsList = newsList;
			} else if (requestType == TIHConstants.GET_OFFICIAL_NEWS) {
				this.officialNewsList = newsList;
			}
//			getView().findViewById(R.id.progress_view).setVisibility(View.GONE);
//			getView().findViewById(R.id.connection_status_view).setVisibility(View.GONE);
			getView().findViewById(R.id.listview).setVisibility(View.VISIBLE);
			((ListView) getView().findViewById(R.id.listview))
					.setAdapter(new NewsListAdapter(getView().getContext(), newsList));
		}
	}
}
