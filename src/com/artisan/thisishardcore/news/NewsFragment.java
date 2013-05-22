package com.artisan.thisishardcore.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.UnifeedFragment;
import com.artisan.thisishardcore.R.id;
import com.artisan.thisishardcore.R.layout;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHEventList;
import com.artisan.thisishardcore.models.TIHNewsList;
import com.artisan.thisishardcore.schedule.EventListAdapter;
import com.unifeed.AppState;
import com.unifeed.Constants;
import com.unifeed.webservice.ResponseListener;

public class NewsFragment extends UnifeedFragment implements ResponseListener {
	private static final TIHLogger logger = new TIHLogger(NewsFragment.class); 
	public static final String OFFICIAL_FEED = "OFFICIAL_FEED";
	public static final String FAN_FEED      = "FAN_FEED";
	
	private TIHNewsList newsList; 
	private int failureWebServiceReq;
	private boolean isReqSent;

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
		sendNewsRequest(0, 20, OFFICIAL_FEED);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
							 ViewGroup container,
							 Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.news, container, false);
		return(result); 
	}

	@Override
	public void onResponseReceived(Object response, int requestType) {
		logger.d("onResponseReceived");
//		AppState.stopActivityIndicator();
		
		if(response == null){
			failureWebServiceReq = requestType;
			getView().findViewById(R.id.connection_status_view).setVisibility(View.VISIBLE);
			getView().findViewById(R.id.listview).setVisibility(View.GONE);
			getView().findViewById(R.id.progress_view).setVisibility(View.GONE);
		} else if (requestType == Constants.GET_NEWS) {
			TIHNewsList newsList = (TIHNewsList)response;
			updateEventsUI(newsList);
		}
	}
	
	//updating event view after getting response from server
	private void updateEventsUI(TIHNewsList newsList) {
		isReqSent = false;
		logger.d("updateEventsUI");
		if (newsList != null && !newsList.newsItems.isEmpty()) {
			this.newsList = newsList;
//			getView().findViewById(R.id.progress_view).setVisibility(View.GONE);
//			getView().findViewById(R.id.connection_status_view).setVisibility(View.GONE);
			getView().findViewById(R.id.listview).setVisibility(View.VISIBLE);
			((ListView) getView().findViewById(R.id.listview))
					.setAdapter(new NewsListAdapter(getView().getContext(), newsList));
		}
	}
}
