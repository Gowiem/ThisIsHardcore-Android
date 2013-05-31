package com.artisan.thisishardcore.schedule;



import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.artisan.thisishardcore.PhotoPitFragment;
import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.UnifeedFragment;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHEvent;
import com.artisan.thisishardcore.models.TIHEventList;
import com.unifeed.AppState;
import com.unifeed.Constants;
import com.unifeed.MLog;
import com.unifeed.webservice.ResponseListener;

public class ScheduleFragment extends UnifeedFragment implements ResponseListener {
	private static final TIHLogger logger = new TIHLogger(ScheduleFragment.class);
	
	private TIHEventList eventList; 
	
	// Lifecycle
	//////////////
	
	public static ScheduleFragment newInstance() {
		logger.d("newInstance");
		ScheduleFragment fragment = new ScheduleFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		logger.d("onCreate");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.schedule, container, false);
		
		// Set the List item click listener
		ListView listView = (ListView)result.findViewById(R.id.listview);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
		            int position, long id) {
				onEventRowClicked(view);
			}
		});
		
		return(result); 
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    logger.d("onActivityCreated");

	    setContentView(R.layout.schedule);
	    // Setup text for empty content
	    // setEmptyText(R.string.news_empty);
		
		if (currentTab == null) {
			sendEventRequest();
		} else {
			updateForCurrentTab();
		}
	}
	
	// Tab Methods
	///////////////
	
	public void updateForCurrentTab() {
		
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

	// Send failure web service when "please try again" button clicked
	public void onTryAgainClicked(View view) {
		logger.w("onTryAgainClicked");
	}

	@Override
	public void onResponseReceived(Object response, int requestType) {
		logger.d("onResponseReceived");
		super.onResponseReceived(response, requestType);
		if(response != null){
			TIHEventList eventList = (TIHEventList)response;
			updateUI(eventList);
		}
	}
	
	//updating event view after getting response from server
	private void updateUI(TIHEventList eventList) {
		logger.d("updateUI");
		super.updateUI(eventList);;
		if (eventList != null && !eventList.events.isEmpty()) {
			this.eventList = eventList;
			((ListView) getView().findViewById(R.id.listview))
					.setAdapter(new EventListAdapter(getView().getContext(), eventList));
		}
	}
}
