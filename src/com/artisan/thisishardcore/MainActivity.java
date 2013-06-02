package com.artisan.thisishardcore;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.artisan.thisishardcore.logging.TIHConfigureLog4j;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.news.NewsFragment;
import com.artisan.thisishardcore.schedule.ScheduleFragment;

public class MainActivity extends SherlockFragmentActivity implements com.actionbarsherlock.app.ActionBar.TabListener{
	private final TIHLogger logger = new TIHLogger(MainActivity.class);
	
	public static final String TAB_SELECTED = "TAB_SELECTED"; 
	public final String NEWS_TAB = "NEWS_TAB";
	public final String SCHEDULE_TAB = "SCHEDULE_TAB";
	public final String PHOTO_PIT_TAB = "PHOTO_PIT_TAB";
	
	private NewsFragment newsFragment;
	private ScheduleFragment scheduleFragment;
	private PhotoPitFragment photoPitFragment;
	
	public String currentlySelectedTab;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		logger.d("onCreate");
		
		final ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.addTab(bar.newTab()
                .setText("News")
                .setTag(NEWS_TAB)
                .setTabListener(this));
		bar.addTab(bar.newTab()
				.setText("Schedule")
				.setTag(SCHEDULE_TAB)
				.setTabListener(this));
		bar.addTab(bar.newTab()
				.setText("Photo Pit")
				.setTag(PHOTO_PIT_TAB)
				.setTabListener(this));	

		logger.d("savedInstanceState: ", savedInstanceState);
		if (savedInstanceState != null) {
			String tabIdentifier = savedInstanceState.getString(TAB_SELECTED);
			logger.d("savedInstanceState was not null, trying to select tab with identifier: ", tabIdentifier.toString());
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			selectTab(tabIdentifier, ft);
		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: 
			return(true);
		case R.id.bookmarks: 
			return(true);
		case R.id.more: 
			return(true);
		}
		return(super.onOptionsItemSelected(item));
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		logger.d("onSaveInstanceState -  setting currentlySelectedTab: ", currentlySelectedTab);
		outState.putString(TAB_SELECTED, currentlySelectedTab);
	}
	
	private void detachFragment(FragmentTransaction ft) {
		logger.d("Detaching Fragment:", currentlySelectedTab);
		if (currentlySelectedTab == null) { return; }; 
		if (currentlySelectedTab.equals(NEWS_TAB)) {
			ft.detach(newsFragment);
		} else if (currentlySelectedTab.equals(PHOTO_PIT_TAB)) {
			ft.detach(photoPitFragment);
		} else if (currentlySelectedTab.equals(SCHEDULE_TAB)) {
			ft.detach(scheduleFragment);
		} else {
			logger.d("Currently selected tab was null, nothing to detach");
		}
	}
	
	private void selectTab(String tabIdentifier, final FragmentTransaction ft) {
		detachFragment(ft);
		if (tabIdentifier.equals(NEWS_TAB)) {
			logger.d("News Tab Selected");
			currentlySelectedTab = NEWS_TAB;
			if (newsFragment == null) {
				logger.d("News Fragment was null - creating new instance");
				newsFragment = NewsFragment.newInstance();
				ft.add(R.id.fragment_container, newsFragment, NEWS_TAB);
			} else {
				ft.attach(newsFragment);	
			}
		} else if (tabIdentifier.equals(SCHEDULE_TAB)) {
			logger.d("Schedule Tab Selected");
			currentlySelectedTab = SCHEDULE_TAB;
			if (scheduleFragment == null) {
				logger.d("Schedule Fragment was null - creating new instance");
				scheduleFragment = ScheduleFragment.newInstance();
				ft.add(R.id.fragment_container, scheduleFragment, SCHEDULE_TAB);
			} else {
				ft.attach(scheduleFragment);	
			}
		} else if (tabIdentifier.equals(PHOTO_PIT_TAB)) {
			logger.d("Photo Pit Tab Selected");
			currentlySelectedTab = PHOTO_PIT_TAB;
			if (photoPitFragment == null) {
				logger.d("Photo Pit Fragment was null - creating new instance");
				photoPitFragment = PhotoPitFragment.newInstance();
				ft.add(R.id.fragment_container, photoPitFragment, PHOTO_PIT_TAB);
			} else {
				ft.attach(photoPitFragment);	
			}
		} else {
			logger.d("onTabSelected -> couldn't find the tab with tag: ", tabIdentifier);
		}
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		String tabIdentifier = (String) tab.getTag();
		selectTab(tabIdentifier, ft);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) { }

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) { }
}
