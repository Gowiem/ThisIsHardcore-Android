package com.artisan.thisishardcore;



import org.apache.log4j.Logger;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.artisan.thisishardcore.logging.TIHConfigureLog4j;
import com.artisan.thisishardcore.schedule.ScheduleFragment;

public class MainActivity extends SherlockFragmentActivity implements com.actionbarsherlock.app.ActionBar.TabListener{
	private final Logger log = Logger.getLogger("MainActivity-ThisIsHardcore");
	
	public static final String TAB_SELECTED = "TAB_SELECTED";  
	public enum TabIdentifier{ NEWS_TAB, SCHEDULE_TAB, PHOTOS_TAB }
	
	public TabIdentifier currentlySelectedTab;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TIHConfigureLog4j.configure();
		log.debug("onCreate");
		
		final ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.addTab(bar.newTab()
                .setText("News")
                .setTag(TabIdentifier.NEWS_TAB)
                .setTabListener(this));
		bar.addTab(bar.newTab()
				.setText("Schedule")
				.setTag(TabIdentifier.SCHEDULE_TAB)
				.setTabListener(this));
		bar.addTab(bar.newTab()
				.setText("Photo Pit")
				.setTag(TabIdentifier.PHOTOS_TAB)
				.setTabListener(this));	

		log.debug("savedInstanceState: " + savedInstanceState);
		if (savedInstanceState != null) {
			TabIdentifier tabIdentifier = TabIdentifier.valueOf(savedInstanceState.getString(TAB_SELECTED));
			log.debug("savedInstanceState was not null, trying to select tab with identifier: " 
					+ tabIdentifier.toString());
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
		log.debug("onSaveInstanceState -  setting currentlySelectedTab: " + currentlySelectedTab.toString());
		outState.putString(TAB_SELECTED, currentlySelectedTab.toString());
	}
	
	private void selectTab(TabIdentifier tabIdentifier, final FragmentTransaction ft) {
		if (tabIdentifier == TabIdentifier.NEWS_TAB) {
			log.debug("News Tab Selected");
			currentlySelectedTab = TabIdentifier.NEWS_TAB;
			ft.replace(android.R.id.content, NewsFragment.newInstance());
		} else if (tabIdentifier == TabIdentifier.SCHEDULE_TAB) {
			log.debug("Schedule Tab Selected");
			currentlySelectedTab = TabIdentifier.SCHEDULE_TAB;
			log.debug("After printing ScheduleFragment.class");
			ft.replace(android.R.id.content, ScheduleFragment.newInstance());
		} else if (tabIdentifier == TabIdentifier.PHOTOS_TAB) {
			log.debug("Photo Pit Tab Selected");
			currentlySelectedTab = TabIdentifier.PHOTOS_TAB;
			ft.replace(android.R.id.content, PhotoPitFragment.newInstance());
		} else {
			log.debug("onTabSelected -> couldn't find the tab with tag: " + tabIdentifier);
		}
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		TabIdentifier tabIdentifier = (TabIdentifier) tab.getTag();
		selectTab(tabIdentifier, ft);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) { }

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) { }
}
