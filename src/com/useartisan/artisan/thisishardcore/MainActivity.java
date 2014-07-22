package com.useartisan.artisan.thisishardcore;

import org.joda.time.DateTimeZone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.artisan.activity.ArtisanActivity;
import com.artisan.services.ArtisanBoundActivity;
import com.artisan.services.ArtisanService;
import com.useartisan.artisan.thisishardcore.imageutils.ImageFetcher;
import com.useartisan.artisan.thisishardcore.logging.TIHConfigureLog4j;
import com.useartisan.artisan.thisishardcore.logging.TIHLogger;
import com.useartisan.artisan.thisishardcore.news.NewsFragment;
import com.useartisan.artisan.thisishardcore.news.NewsListAdapter;
import com.useartisan.artisan.thisishardcore.schedule.EventListAdapter;
import com.useartisan.artisan.thisishardcore.schedule.ScheduleFragment;
import com.useartisan.artisan.thisishardcore.utils.TIHUtils;

public class MainActivity extends SherlockFragmentActivity implements com.actionbarsherlock.app.ActionBar.TabListener, ArtisanBoundActivity {
	private final TIHLogger logger = new TIHLogger(MainActivity.class);

	public static final String TAB_SELECTED = "TAB_SELECTED";
	public final String NEWS_TAB = "NEWS_TAB";
	public final String SCHEDULE_TAB = "SCHEDULE_TAB";
	public final String PHOTO_PIT_TAB = "PHOTO_PIT_TAB";

	private NewsFragment newsFragment;
	private ScheduleFragment scheduleFragment;
	private PhotoPitFragment photoPitFragment;

	public String currentlySelectedTab;

	// Artisan Interface Methods
	/////////////////////////////

	@Override
	public ArtisanService getArtisanService() {
		return ArtisanActivity._getArtisanService();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// Call Artisan AFTER super onStart
		ArtisanActivity.artisanOnStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		// Call Artisan AFTER super onStop
		ArtisanActivity.artisanOnStop(this);
	}

	@Override
	protected void onDestroy() {
		// Call Artisan method BEFORE super onDestroy
		ArtisanActivity.artisanOnDestroy();
		super.onDestroy();
	}

	@Override
	public void setContentView(int layoutResID) {
		View view = ArtisanActivity.artisanGetContentView(layoutResID, this);
		super.setContentView(view);
	}

	@Override
	public void setContentView(View view) {
		view = ArtisanActivity.artisanGetContentView(view, this);
		super.setContentView(view);
	}

	@Override
    public void setContentView(View view, LayoutParams params) {
        View contentView = ArtisanActivity.artisanGetContentView(view, params, this);
        super.setContentView(contentView);
    }

	// Lifecycle
	/////////////

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Call Artisan AFTER super onCreate
		ArtisanActivity.artisanOnCreate(this);
		setContentView(R.layout.main);
		TIHConfigureLog4j.configure();
		//logger.d("onCreate");
		Thread.setDefaultUncaughtExceptionHandler(new TIHExceptionHandler(this));

		// Setting Default TimeZone for Application since I didn't know where else to put it.. It's later.
		DateTimeZone.setDefault(DateTimeZone.UTC);

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

		setupImageFetcher();

		//logger.d("savedInstanceState: ", savedInstanceState);
		if (savedInstanceState != null) {
			String tabIdentifier = savedInstanceState.getString(TAB_SELECTED);
			//logger.d("savedInstanceState was not null, trying to select tab with identifier: ", tabIdentifier.toString());
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			selectTab(tabIdentifier, ft);
		}
	}

	// Image Cache
	///////////////

	// Holy shit. This has to be a mistake... Fuck.
	private void setupImageFetcher() {
		ImageFetcher imageFetcher = TIHUtils.newImageFetcher(this, 0.15f, getSupportFragmentManager());
		EventListAdapter.imageFetcher = imageFetcher;
		NewsListAdapter.imageFetcher = imageFetcher;
		PhotoPitListAdapter.iconFetcher = TIHUtils.newImageFetcher(this, 0.05f, getSupportFragmentManager());
		PhotoPitListAdapter.instaFetcher = TIHUtils.newImageFetcher(this, 0.15f, getSupportFragmentManager());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String urlString;
		switch (item.getItemId()) {
		case android.R.id.home:
			return(true);
		case R.id.stuff_to_do_item:
			urlString = getResources().getString(R.string.stuff_to_do_url);
			launchWebViewWithUrl(urlString);
			return(true);
		case R.id.accommodations_item:
			urlString = getResources().getString(R.string.accommodations_url);
			launchWebViewWithUrl(urlString);
			return(true);
		case R.id.vendors_item:
			urlString = getResources().getString(R.string.vendors_url);
			launchWebViewWithUrl(urlString);
			return(true);
		case R.id.facebook_item:
			urlString = getResources().getString(R.string.tihc_facebook_url);
			launchWebViewWithUrl(urlString);
			return(true);
		case R.id.twitter_item:
			urlString = getResources().getString(R.string.tihc_twitter_url);
			launchWebViewWithUrl(urlString);
			return(true);
		case R.id.about_tihc_item:
			urlString = getResources().getString(R.string.about_tihc_url);
			launchWebViewWithUrl(urlString);
			return(true);
		case R.id.about_artisan_item:
			urlString = getResources().getString(R.string.about_artisan_url);
			launchWebViewWithUrl(urlString);
			return(true);
		}
		return(super.onOptionsItemSelected(item));
	}

	private void launchWebViewWithUrl(String url) {
		Intent webViewIntent = new Intent(this, TIHWebViewActivity.class);
		webViewIntent.putExtra(TIHWebViewActivity.WEB_VIEW_URL, url);
		startActivity(webViewIntent);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		//logger.d("onSaveInstanceState -  setting currentlySelectedTab: ", currentlySelectedTab);
		outState.putString(TAB_SELECTED, currentlySelectedTab);
	}

	private void detachFragment(FragmentTransaction ft) {
		//logger.d("Detaching Fragment:", currentlySelectedTab);
		if (currentlySelectedTab == null) { return; };
		if (currentlySelectedTab.equals(NEWS_TAB)) {
			ft.detach(newsFragment);
		} else if (currentlySelectedTab.equals(PHOTO_PIT_TAB)) {
			ft.detach(photoPitFragment);
		} else if (currentlySelectedTab.equals(SCHEDULE_TAB)) {
			ft.detach(scheduleFragment);
		} else {
			//logger.d("Currently selected tab was null, nothing to detach");
		}
	}

	private void selectTab(String tabIdentifier, final FragmentTransaction ft) {
		detachFragment(ft);
		if (tabIdentifier.equals(NEWS_TAB)) {
			//logger.d("News Tab Selected");
			currentlySelectedTab = NEWS_TAB;
			if (newsFragment == null) {
				//logger.d("News Fragment was null - creating new instance");
				newsFragment = NewsFragment.newInstance();
				ft.add(R.id.fragment_container, newsFragment, NEWS_TAB);
			} else {
				ft.attach(newsFragment);
			}
		} else if (tabIdentifier.equals(SCHEDULE_TAB)) {
			//logger.d("Schedule Tab Selected");
			currentlySelectedTab = SCHEDULE_TAB;
			if (scheduleFragment == null) {
				//logger.d("Schedule Fragment was null - creating new instance");
				scheduleFragment = ScheduleFragment.newInstance();
				ft.add(R.id.fragment_container, scheduleFragment, SCHEDULE_TAB);
			} else {
				ft.attach(scheduleFragment);
			}
		} else if (tabIdentifier.equals(PHOTO_PIT_TAB)) {
			//logger.d("Photo Pit Tab Selected");
			currentlySelectedTab = PHOTO_PIT_TAB;
			if (photoPitFragment == null) {
				//logger.d("Photo Pit Fragment was null - creating new instance");
				photoPitFragment = PhotoPitFragment.newInstance();
				ft.add(R.id.fragment_container, photoPitFragment, PHOTO_PIT_TAB);
			} else {
				ft.attach(photoPitFragment);
			}
		} else {
			//logger.d("onTabSelected -> couldn't find the tab with tag: ", tabIdentifier);
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
