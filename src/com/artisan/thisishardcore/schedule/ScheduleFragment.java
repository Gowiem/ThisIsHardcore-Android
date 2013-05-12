package com.artisan.thisishardcore.schedule;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.artisan.thisishardcore.UnifeedFragment;
import com.artisan.thisishardcore.parser.TIHEvent;
import com.artisan.thisishardcore.parser.TIHEventList;
import com.unifeed.AppState;
import com.unifeed.Constants;
import com.unifeed.MLog;
import com.unifeed.R;
import com.unifeed.responsetype.Authentication;
import com.unifeed.webservice.ResponseListener;

public class ScheduleFragment extends UnifeedFragment implements ResponseListener {
	private final static Logger log = Logger.getLogger(ScheduleFragment.class);
	
	private TIHEventList eventList; 
	private int failureWebServiceReq;
	private boolean isReqSent;

	public static ScheduleFragment newInstance() {
		log.debug("ScheduleFragment - newInstance");
		ScheduleFragment fragment = new ScheduleFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
							 ViewGroup container,
							 Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.news_results, container, false);
		return(result); 
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		log.debug("ScheduleFragment - onCreate");
		super.onCreate(savedInstanceState);
		//Add scroll listener and footer view to listview
		//((ListView)findViewById(R.id.listview)).setOnScrollListener(this);
		//View view = getLayoutInflater().inflate(R.layout.footer_view, null);
		//((ListView)findViewById(R.id.listview)).addFooterView(view);
		
		//send event web service
		sendEventRequest();
	}

	//Open Url via browser or webview based on constant varriable
	public void onEventRowClicked(View view){
		if(view.getTag() != null && view.getTag().toString().trim().length() > 0){
			int viewPosition = (Integer) view.getTag();
			TIHEvent event   = this.eventList.events.get(viewPosition); 
			
			Intent intent = new Intent(getActivity(), EventDetailActivity.class);
			intent.putExtra(EventDetailActivity.ARTIST_NAME, event.artistName);
			intent.putExtra(EventDetailActivity.ARTIST_TIME, event.getTime());
			intent.putExtra(EventDetailActivity.ARTIST_DESCRIPTION, event.description);
			startActivity(intent);
		}
	}

	//Send failure web service when "please try again" button clicked
	public void onTryAgainClicked(View view){
		getView().findViewById(R.id.connection_status_view).setVisibility(View.GONE);
		getView().findViewById(R.id.progress_view).setVisibility(View.VISIBLE);
		MLog.d("", "Failure web service = "+failureWebServiceReq);
		switch (failureWebServiceReq) {
		case Constants.GET_AUTHENTICATION_ID:
			sendEventRequest();
			break;
		case Constants.GET_EVENTS_DETAILS:
			sendEventRequest();
			break;
		}
	}

	@Override
	public void onResponseReceived(Object response, int requestType) {
		log.debug("onResponseReceived");
		AppState.stopActivityIndicator();
		if(response == null){
			failureWebServiceReq = requestType;
			getView().findViewById(R.id.connection_status_view).setVisibility(View.VISIBLE);
			getView().findViewById(R.id.listview).setVisibility(View.GONE);
			getView().findViewById(R.id.progress_view).setVisibility(View.GONE);
			return;
		}

		else{
			switch (requestType) {
			case Constants.GET_EVENTS_DETAILS:
				TIHEventList eventList = (TIHEventList)response;
				updateEventsUI(eventList);
				break;
			case Constants.GET_AUTHENTICATION_ID:
				Authentication authentication = (Authentication)response;
				AppState.AUTHENTICATION_TOKEN = authentication.authenticationID;
				MLog.d("", "ID = "+authentication.authenticationID);
				sendEventRequest();
				break;
			}
		}
	}
	
	//updating event view after getting response from server
	private void updateEventsUI(TIHEventList eventList) {
		isReqSent = false;
		log.debug("ScheduleFragment - updateEventsUI");
		if (eventList != null && !eventList.events.isEmpty()) {
			this.eventList = eventList;
			getView().findViewById(R.id.progress_view).setVisibility(View.GONE);
			getView().findViewById(R.id.connection_status_view).setVisibility(View.GONE);
			getView().findViewById(R.id.listview).setVisibility(View.VISIBLE);
			log.debug("updateEventsUI - eventList: " + eventList.toString());
			((ListView) getView().findViewById(R.id.listview))
					.setAdapter(new EventListAdapter(getView().getContext(), eventList));
		}
	}
}
