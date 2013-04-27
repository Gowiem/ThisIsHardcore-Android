package com.artisan.thisishardcore;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.artisan.thisishardcore.logging.TIHConfigureLog4j;

public class MainActivity extends SherlockFragmentActivity implements com.actionbarsherlock.app.ActionBar.TabListener{
	private final Logger log = Logger.getLogger(MainActivity.class);
	private final int NEWS_TAB_TAG = 0;
	private final int SCHEDULE_TAB_TAG = 1;
	private final int PHOTOS_TAB_TAG = 3;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.d("test", "External Storage Directory: " + Environment.getExternalStorageDirectory().toString());
		TIHConfigureLog4j.configure();
		
		final ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.addTab(bar.newTab()
                .setText("News")
                .setTag(NEWS_TAB_TAG)
                .setTabListener(this));
		bar.addTab(bar.newTab()
				.setText("Schedule")
				.setTag(SCHEDULE_TAB_TAG)
				.setTabListener(this));
		bar.addTab(bar.newTab()
				.setText("Photo Pit")
				.setTag(PHOTOS_TAB_TAG)
				.setTabListener(this));	
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
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		log.debug("onTabSelected");
		int tabIdentifier = (Integer) tab.getTag();
		if (tabIdentifier == NEWS_TAB_TAG) {
			log.debug("News Tab Selected");
			
			ft.replace(android.R.id.content, NewsFragment.newInstance());
		} else if (tabIdentifier == SCHEDULE_TAB_TAG) {
			log.debug("Schedule Tab Selected");
			
		} else if (tabIdentifier == PHOTOS_TAB_TAG) {
			log.debug("Photo Pit Tab Selected");
		} else {
			log.debug("onTabSelected -> couldn't find the tab with tag: " + tabIdentifier);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) { }

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) { }
}
