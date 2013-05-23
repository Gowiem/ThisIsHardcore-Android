package com.artisan.thisishardcore.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.UnifeedFragment;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHNewsList;
import com.artisan.thisishardcore.unifeed.TIHConstants;
import com.unifeed.webservice.ResponseListener;

public class NewsFragment extends UnifeedFragment implements ResponseListener {
	private static final TIHLogger logger = new TIHLogger(NewsFragment.class); 
	public static final String OFFICIAL_FEED = "OFFICIAL_FEED";
	public static final String FAN_FEED      = "FAN_FEED";
	
	private TIHNewsList officialNewsList;
	private TIHNewsList fanNewsList;
	
	private int failureWebServiceReq;
	private boolean isReqSent;
	
	private ImageView officialTab;
	private ImageView fanTab;

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
		View result = inflater.inflate(R.layout.news, container, false);
		
		officialTab = (ImageView) result.findViewById(R.id.official_tab);
		fanTab = (ImageView) result.findViewById(R.id.fan_feed_tab);
		
		officialTabClicked(officialTab);
		return(result); 
	}
	
	public void officialTabClicked(View v) {
		sendNewsRequest(0, TIHConstants.RESULT_PER_REQUEST, TIHConstants.GET_OFFICIAL_NEWS);
		officialTab.setImageResource(R.drawable.official_blue);
		fanTab.setImageResource(R.drawable.fan_feed_grey);
	}
	
	public void fanTabClicked(View v) {
		sendNewsRequest(0, TIHConstants.RESULT_PER_REQUEST, TIHConstants.GET_FAN_NEWS);
		officialTab.setImageResource(R.drawable.official_grey);
		fanTab.setImageResource(R.drawable.fan_feed_blue);
		
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
		} else {
			TIHNewsList newsList = (TIHNewsList)response;
			updateEventsUI(newsList, requestType);
		}
	}
	
	//updating event view after getting response from server
	private void updateEventsUI(TIHNewsList newsList, int requestType) {
		isReqSent = false;
		logger.d("updateEventsUI");
		if (newsList != null && !newsList.newsItems.isEmpty()) {
			if (requestType == TIHConstants.GET_FAN_NEWS) {
				this.officialNewsList = newsList;
			} else if (requestType == TIHConstants.GET_OFFICIAL_NEWS) {
				this.fanNewsList = newsList;
			}
//			getView().findViewById(R.id.progress_view).setVisibility(View.GONE);
//			getView().findViewById(R.id.connection_status_view).setVisibility(View.GONE);
			getView().findViewById(R.id.listview).setVisibility(View.VISIBLE);
			((ListView) getView().findViewById(R.id.listview))
					.setAdapter(new NewsListAdapter(getView().getContext(), newsList));
		}
	}
}
