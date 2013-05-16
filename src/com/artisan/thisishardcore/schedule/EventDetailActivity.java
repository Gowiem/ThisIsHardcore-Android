package com.artisan.thisishardcore.schedule;


import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.artisan.thisishardcore.MainActivity;
import com.artisan.thisishardcore.PhotoPitFragment;
import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.utils.TIHUtils;


public class EventDetailActivity extends SherlockActivity {
	private static final TIHLogger logger = new TIHLogger(EventDetailActivity.class);
	
	public final static String ARTIST_NAME        = "ARTIST_NAME";
	public final static String ARTIST_TIME 	      = "ARTIST_TIME";
	public final static String ARTIST_DESCRIPTION = "ARTIST_DESCRIPTION";
	public final static String ARTIST_IMAGE_URL   = "ARTIST_IMAGE_URL";
	public final static String WEBSITE_URL        = "WEBSITE_URL";
	public final static String FACEBOOK_URL       = "FACEBOOK_URL";
	public final static String TWITTER_URL        = "TWITTER_URL";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		logger.d("onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Grab the event details from the intent
		Intent intent = getIntent();
		String artistName 		 = intent.getStringExtra(ARTIST_NAME);
		String artistTime 		 = intent.getStringExtra(ARTIST_TIME);
		String artistDescription = intent.getStringExtra(ARTIST_DESCRIPTION);
		String artistImageUrl    = intent.getStringExtra(ARTIST_IMAGE_URL);
		String artistWebsiteUrl  = intent.getStringExtra(WEBSITE_URL);
		String artistFacebookUrl = intent.getStringExtra(FACEBOOK_URL);
		String artistTwitterUrl  = intent.getStringExtra(TWITTER_URL);
		
		// Grab the views which we need to change
		ImageView artistImageView          = (ImageView) findViewById(R.id.artist_image); 
		TextView artistNameTextView 	   = (TextView) findViewById(R.id.artist_name);
		TextView artistTimeTextView 	   = (TextView) findViewById(R.id.artist_time);
		TextView artistDescriptionTextView = (TextView) findViewById(R.id.artist_description);
		ImageButton websiteButton  		   = (ImageButton) findViewById(R.id.website_button);
		ImageButton facebookButton 		   = (ImageButton) findViewById(R.id.facebook_button);
		ImageButton twitterButton  		   = (ImageButton) findViewById(R.id.twitter_button);
		
		// Assign the details to the views
		if (!TIHUtils.isEmpty(artistImageUrl)) {
			// TODO: Figure out how to lazy load images for views 			
		}
		artistNameTextView.setText(artistName);
		artistTimeTextView.setText(artistTime);
		artistDescriptionTextView.setText(artistDescription);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // This is called when the Home (Up) button is pressed
	            // in the Action Bar.
	            finish();
	            return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
}
