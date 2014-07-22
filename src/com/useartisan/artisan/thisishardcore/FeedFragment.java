package com.artisan.thisishardcore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHFeedItem;
import com.artisan.thisishardcore.models.TIHFeedList;
import com.artisan.thisishardcore.models.TIHNewsList;
import com.artisan.thisishardcore.models.TIHPhotoList;
import com.artisan.thisishardcore.news.NewsListAdapter;
import com.artisan.thisishardcore.utils.TIHListAdapter;

public abstract class FeedFragment extends UnifeedFragment implements OnScrollListener {
	private static final TIHLogger logger = new TIHLogger(FeedFragment.class);
	
	// Shared Fields
	/////////////////
	
	// Tab Identifiers
	public static final String OFFICIAL_TAB = "OFFICIAL_TAB";
	public static final String FAN_TAB = "FAN_TAB";
	
	// Views
	public ImageView officialTabImageView;
	public ImageView fanTabImageView;
	
	public ListView officalListView;
	public ListView fanListView;
	private View footerView; 
	
	public TIHFeedList<?> officialList;
	public TIHFeedList<?> fanList;
	
	private int officialTabPageNumber;
	private int fanTabPageNumber;
	public boolean isLoading;
	
	public abstract void sendRequest(String tabIdentifier, int pageNumber);
	public abstract void updateUI(String tabIdentifier);
	
	// Lifecycle
	/////////////
	
	public void init() {
		officialTabPageNumber = 1;
		fanTabPageNumber = 1;
	}
	
	public View onCreateViewHelper(View resultView) {
		//logger.d("onCreateViewHelper - officialTabPageNumber: ", officialTabPageNumber, "fanTabPageNumber: ", fanTabPageNumber);
		officalListView = (ListView) resultView.findViewById(R.id.official_list);
		officalListView.setOnScrollListener(this);
		fanListView = (ListView) resultView.findViewById(R.id.fan_list);
		fanListView.setOnScrollListener(this);
		
		footerView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loading_footer, null, false);
		officalListView.addFooterView(footerView, null, false);
		fanListView.addFooterView(footerView, null, false);
		
		officialTabImageView = (ImageView) resultView.findViewById(R.id.official_tab);
		fanTabImageView = (ImageView) resultView.findViewById(R.id.fan_feed_tab);
		
		officialTabImageView.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) { officialTabClicked(v); }
		});
		
		fanTabImageView.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) { fanTabClicked(v); }
		});
		
		return resultView;
	}
	

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		//logger.d("onDestroyView");
		currentTab = null;
	}
	
	// OnScrollListener Methods
	///////////////////////////
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		int loadedItems = firstVisibleItem + visibleItemCount;
		// Sometimes onScroll can be called with totalItem = 0. Causes issues. Return here to fix
		if (totalItemCount == 0) { return; } 
		if((loadedItems == totalItemCount) && !isLoading && currentTab != null) {
			
			int pageNumber = incrementAndGetPageNumber();
			if (pageNumber <= 5) { // Let's cut the user off at 5 pages... They've had enough..
				//logger.d("User scrolled to end of the list. Sending another request for the next page");
				sendRequest(currentTab, pageNumber);
			} else {
				getListViewForTabIdentifier(currentTab).removeFooterView(footerView);
			}
		}
	}
	
	private void addLoadingFooterView() {
		//logger.d("Add Loading Footer View");
		ListView listView = getListViewForTabIdentifier(currentTab);
		View footerView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loading_footer, null, false);
		//logger.d("footerView: ", footerView);
		listView.addFooterView(footerView);
	}
	
	private void removeLoadingFooterView() {
		//logger.d("Remove Loading Footer View");
		ListView listView = getListViewForTabIdentifier(currentTab);
		View footerView = listView.findViewById(R.layout.loading_footer);
		listView.removeFooterView(footerView);
	}
	
	private int incrementAndGetPageNumber() {
		if (currentTab.equals(FAN_TAB)) {
			fanTabPageNumber += 1;
			return fanTabPageNumber;
		} else {
			officialTabPageNumber += 1;
			return officialTabPageNumber;
		}
	}
	
	private ListView getListViewForTabIdentifier(String tabIdentifier) {
		if (tabIdentifier.equals(OFFICIAL_TAB)) {
			return officalListView;
		} else {
			return fanListView;
		}
	}
	
	// Update UI Helpers
	/////////////////////
	
	// Creates or updates the given TIHFeedList 'currentList' returning the new list 
	public TIHFeedList<? extends TIHFeedItem> createOrUpdateModel(TIHFeedList<? extends TIHFeedItem> newList, 
				TIHFeedList<? extends TIHFeedItem> currentList, 
				String tabIdentifier) {
		if (currentList == null) {
			currentList = newList;
			currentList.setWasJustCreated(true);
		} else {
			currentList.mergeItems((TIHFeedList<? extends TIHFeedItem>)newList);
			currentList.setWasJustCreated(false);
		}
		return currentList;
	}
	
	public void updateListView(TIHFeedList<? extends TIHFeedItem> itemList, boolean feedListCreated, String tabIdentifier) {
//		int listViewId = tabIdentifier.equals(OFFICIAL_TAB) ? R.id.official_list : R.id.fan_list;
//		ListView feedListView = (ListView) getView().findViewById(listViewId); 
		ListView feedListView = getListViewForTabIdentifier(tabIdentifier);
		if (feedListCreated || feedListView.getAdapter() == null) { // We just received the first FeedList page, create the adapter
			if (itemList.getClass() == TIHNewsList.class) {
				feedListView.setAdapter(new NewsListAdapter(getView().getContext(), (TIHNewsList)itemList, tabIdentifier));
			} else if (itemList.getClass() == TIHPhotoList.class) {
				feedListView.setAdapter(new PhotoPitListAdapter(getView().getContext(), (TIHPhotoList)itemList, tabIdentifier));
			}
		} else {
//			removeLoadingFooterView();
			@SuppressWarnings("unchecked")
			HeaderViewListAdapter headerAdapter = (HeaderViewListAdapter)feedListView.getAdapter();
			TIHListAdapter<TIHFeedList<? extends TIHFeedItem>> tihAdapter = 
					((TIHListAdapter<TIHFeedList<? extends TIHFeedItem>>)headerAdapter.getWrappedAdapter()); 
			tihAdapter.notifyDataSetChanged();
		}
	}

	// Tab Methods
	///////////////
	
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
		if (currentTab == null || !currentTab.equals(OFFICIAL_TAB)) {
			currentTab = OFFICIAL_TAB;
			//logger.d("Sending or Updating for official tab");
			
			// If we haven't sent the request yet then send it, otherwise update the official list 
			// with the official feed items
			if (officialList == null) {
				sendRequest(OFFICIAL_TAB, 1);	
			} else {
				updateUI(OFFICIAL_TAB);
			}
			
			showOfficialFeed();
		}
	}
	
	public void fanTabClicked(View v) {
		if (currentTab == null || !currentTab.equals(FAN_TAB)) {
			currentTab = FAN_TAB;
			//logger.d("Sending or Updating for fan tab");
			
			// If we haven't sent the request yet then send it, otherwise update the fan list 
			// with the fan feed list 
			if (fanList == null) {
				sendRequest(FAN_TAB, 1);
			} else {
				updateUI(FAN_TAB);
			}
			showFanFeed();
		}
	}
	
	private void showOfficialFeed() {
		// Swap the tab images 
		officialTabImageView.setImageResource(R.drawable.official_blue);
		fanTabImageView.setImageResource(R.drawable.fan_feed_grey);
		
		// Hide the fan list view and show the official list view
		officalListView.setVisibility(View.VISIBLE);
		fanListView.setVisibility(View.GONE);
	}
	
	private void showFanFeed() {
		// Swap the tab images
		officialTabImageView.setImageResource(R.drawable.official_grey);
		fanTabImageView.setImageResource(R.drawable.fan_feed_blue);
		
		// Hide the official list view and show the fan list view
		officalListView.setVisibility(View.GONE);
		fanListView.setVisibility(View.VISIBLE);
	}

}
