package com.artisan.thisishardcore.schedule;



import java.util.ArrayList;

import org.apache.log4j.Logger;

import android.R.integer;
import android.R.raw;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.artisan.thisishardcore.PhotoPitFragment;
import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.UnifeedFragment;
import com.artisan.thisishardcore.R.id;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHEvent;
import com.artisan.thisishardcore.models.TIHEventList;
import com.artisan.thisishardcore.unifeed.TIHConstants;
import com.unifeed.AppState;
import com.unifeed.Constants;
import com.unifeed.MLog;
import com.unifeed.webservice.ResponseListener;

public class ScheduleFragment extends UnifeedFragment implements ResponseListener {
	private static final TIHLogger logger = new TIHLogger(ScheduleFragment.class);
	
	private final String DAY_ONE_TAB   = "DAY_ONE_TAB";
	private final String DAY_TWO_TAB   = "DAY_TWO_TAB";
	private final String DAY_THREE_TAB = "DAY_THREE_TAB";
	private final String DAY_FOUR_TAB  = "DAY_FOUR_TAB";
	
	private ImageView dayOneTabImageView;
	private ImageView dayTwoTabImageView;
	private ImageView dayThreeTabImageView;
	private ImageView dayFourTabImageView;
	
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
		
		// Grab the tab image views
		dayOneTabImageView = (ImageView) result.findViewById(R.id.day_one_tab);
		dayTwoTabImageView = (ImageView) result.findViewById(R.id.day_two_tab);
		dayThreeTabImageView = (ImageView) result.findViewById(R.id.day_three_tab);
		dayFourTabImageView = (ImageView) result.findViewById(R.id.day_four_tab);
		
		// Set tags for the image views so they can be used to swap the image
		dayOneTabImageView.setTag(R.drawable.day_one_tab_blue);
		dayTwoTabImageView.setTag(R.drawable.day_two_tab_grey);
		dayThreeTabImageView.setTag(R.drawable.day_three_tab_grey);
		dayFourTabImageView.setTag(R.drawable.day_four_tab_grey);
		
		// Set the List item click listener
		ListView listView = (ListView)result.findViewById(R.id.listview);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
		            int position, long id) {
				onEventRowClicked(view);
			}
		});
		
		dayOneTabImageView.setOnClickListener(new OnClickListener() {
		    @Override public void onClick(View v) { tabClicked(v, DAY_ONE_TAB); }
		});
		
		dayTwoTabImageView.setOnClickListener(new OnClickListener() {
		    @Override public void onClick(View v) { tabClicked(v, DAY_TWO_TAB); }
		});
		
		dayThreeTabImageView.setOnClickListener(new OnClickListener() {
		    @Override public void onClick(View v) { tabClicked(v, DAY_THREE_TAB); }
		});
		
		dayFourTabImageView.setOnClickListener(new OnClickListener() {
		    @Override public void onClick(View v) { tabClicked(v, DAY_FOUR_TAB); }
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
	
	public void tabClicked(View view, String tabIdentifier) {
		logger.d("tabClicked:", tabIdentifier);
		// Don't update if we're already on the clicked tab
		if (currentTab == tabIdentifier) { return; }
		
		// Get the image views for the images we need to swap
		ImageView currentTabImageView = getTabImageViewForTabIdentifier(currentTab);
		ImageView clickedTabImageView = getTabImageViewForTabIdentifier(tabIdentifier);
		
		logger.d("currentTabImageView:", currentTabImageView);
		logger.d("clickedTabImageView:", clickedTabImageView);
		
		// Get the resource names of the images we need to swap
		int currentTabResource = (Integer)currentTabImageView.getTag();
		int clickedTabResource = (Integer)clickedTabImageView.getTag();
		
		logger.d("currentTabResource:", currentTabResource);
		logger.d("clickedTabResources:", clickedTabResource);
		
		// Create tab drawable resource strings 
		int greyResource = swapDrawable(currentTabResource);
		int blueResource = swapDrawable(clickedTabResource);
		
		logger.d("greyResource:", greyResource);
		logger.d("blueResource:", blueResource);
		
		// Assign new resources to the tabs
		currentTabImageView.setImageResource(greyResource);
		clickedTabImageView.setImageResource(blueResource);
		
		// Assign new tags to the tabs
		currentTabImageView.setTag(greyResource);
		clickedTabImageView.setTag(blueResource);
		
		// Set the currentTab to the identifier and update the list for this new tab
		currentTab = tabIdentifier;
		updateForCurrentTab();
	}
	
	private int swapDrawable(int drawable) {
		switch (drawable) {
		// Swap Tab One
		case R.drawable.day_one_tab_grey:
			return R.drawable.day_one_tab_blue;
		case R.drawable.day_one_tab_blue:
			return R.drawable.day_one_tab_grey;
		// Swap Tab Two
		case R.drawable.day_two_tab_grey:
			return R.drawable.day_two_tab_blue;
		case R.drawable.day_two_tab_blue:
			return R.drawable.day_two_tab_grey;
		// Swap Tab Three
		case R.drawable.day_three_tab_grey:
			return R.drawable.day_three_tab_blue;
		case R.drawable.day_three_tab_blue:
			return R.drawable.day_three_tab_grey;
		// Swap Tab Four
		case R.drawable.day_four_tab_grey:
			return R.drawable.day_four_tab_blue;
		case R.drawable.day_four_tab_blue:
			return R.drawable.day_four_tab_grey;
		default:
			logger.e("swapDrawable - Couldn't find drawable resource for int:", drawable);
			return 0;
		}
	}
	
	private String createTabDrawableString(String drawableString, boolean tabIsSelected) {
		String resultString;
		if (tabIsSelected) {
			resultString = drawableString + "blue.png";
		} else {
			resultString = drawableString + "grey.png";
		}
		return resultString;
	}
	
	public void updateForCurrentTab() {
		ArrayList<TIHEvent> events = getEventsForCurrentTab();
		((ListView) getView().findViewById(R.id.listview))
			.setAdapter(new EventListAdapter(getView().getContext(), events));
	}
	
	private ArrayList<TIHEvent> getEventsForCurrentTab() {
		if (currentTab.equals(DAY_ONE_TAB)) {
			return eventList.getEventsByDay(TIHConstants.FEST_DAY_ONE); 
		} else if (currentTab.equals(DAY_TWO_TAB)) {
			return eventList.getEventsByDay(TIHConstants.FEST_DAY_TWO);
		} else if (currentTab.equals(DAY_THREE_TAB)) {
			return eventList.getEventsByDay(TIHConstants.FEST_DAY_THREE);
		} else if (currentTab.equals(DAY_FOUR_TAB)) {
			return eventList.getEventsByDay(TIHConstants.FEST_DAY_FOUR);
		} else {
			throw new RuntimeException("Trying to get Events for Current Tab. " + currentTab + 
					" was not one of the expected values.");
		}
	}

	private ImageView getTabImageViewForTabIdentifier(String tabIdentifier) {
		logger.d("getTabImageViewForTabIdentifier - tabIdentifier:", tabIdentifier);
		if (tabIdentifier.equals(DAY_ONE_TAB)) {
			return dayOneTabImageView; 
		} else if (tabIdentifier.equals(DAY_TWO_TAB)) {
			return dayTwoTabImageView;
		} else if (tabIdentifier.equals(DAY_THREE_TAB)) {
			return dayThreeTabImageView;
		} else if (tabIdentifier.equals(DAY_FOUR_TAB)) {
			return dayFourTabImageView;
		} else {
			throw new RuntimeException("Trying to get ImageView for current Tab. " + currentTab + 
					" was not one of the expected values.");
		}
	}
	
	// List view Clicked 
	/////////////////////
	
	//Open Url via browser or webview based on constant varriable
	public void onEventRowClicked(View view){
		if(view.getTag() != null && view.getTag().toString().trim().length() > 0){
			int viewPosition = (Integer) view.getTag();
			ArrayList<TIHEvent> events = getEventsForCurrentTab();  
			TIHEvent event = events.get(viewPosition); 
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
		super.updateUI(eventList);
		if (eventList != null && !eventList.events.isEmpty()) {
			this.eventList = eventList;
			currentTab = DAY_ONE_TAB;
			updateForCurrentTab();
		}
	}
}
