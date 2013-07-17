package com.artisan.thisishardcore.news;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artisan.thisishardcore.FeedFragment;
import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.imageutils.ImageFetcher;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHNewsItem;
import com.artisan.thisishardcore.models.TIHNewsList;
import com.artisan.thisishardcore.utils.TIHListAdapter;
import com.artisan.thisishardcore.utils.TIHUtils;

public class NewsListAdapter extends TIHListAdapter<TIHNewsList> {
	private static final TIHLogger logger = new TIHLogger(NewsListAdapter.class);

	private static final String JOE_PROFILE_IMAGE_URL = "http://a0.twimg.com/profile_images/1372387612/tihsmall_normal.jpg";
	private static final int ICON_IMAGE_SIZE = 48; // Image is 48x48dp in news_item_row layout
	private final Context context; 
	private final TIHNewsList newsList;
	private final String tabIdentifier;
	public static ImageFetcher imageFetcher; 

	public NewsListAdapter(Context context, TIHNewsList newsList, String tabIdentifier) {
		super(context, R.layout.news_item_row, newsList.items);
		this.context = context; 
		this.newsList = newsList;
		this.tabIdentifier = tabIdentifier;
		setupImageFetcher();
	}
	
	private void setupImageFetcher() {
		float screenDensity = context.getResources().getDisplayMetrics().density;
		imageFetcher.setImageSize((int)(ICON_IMAGE_SIZE * screenDensity));
		imageFetcher.setLoadingImage(R.drawable.tih_news_icon);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Grab the views for this row
		View rowView = inflater.inflate(R.layout.news_item_row, parent, false);
		rowView.setTag(position);
		ImageView newsIconImageView = (ImageView)rowView.findViewById(R.id.news_icon); 
		TextView dateTextView = (TextView) rowView.findViewById(R.id.news_date);
		TextView newsBodyTextView = (TextView) rowView.findViewById(R.id.news_body);
		TextView newsAuthorTextView = (TextView) rowView.findViewById(R.id.news_author);
		ImageView rowImageView = (ImageView)rowView.findViewById(R.id.row_image);
		ImageView indicatorView = (ImageView)rowView.findViewById(R.id.indicator);

		// Grab the newsItem for this position and get it's content
		TIHNewsItem newsItem = (TIHNewsItem) newsList.getItemAtIndex(position);
		String bodyText        = newsItem.getBody();
		String dateString      = newsItem.getDateString();
		String profileImageUrl = newsItem.getProfileUrl();
		String authorString    = newsItem.getAuthor();
		
		// If this is the Fan Feed then we don't want the user to be able to click the news item row
		if (tabIdentifier.equals(FeedFragment.FAN_TAB)) {
			indicatorView.setVisibility(View.GONE);
		}
		
		// Set the content for the views
		newsBodyTextView.setText(newsItem.getBody());
		dateTextView.setText(dateString);
		newsAuthorTextView.setText(authorString);
		
		// If we have the profile image url then grab it from the cache or the internet
		if (!TIHUtils.isEmpty(profileImageUrl) && !profileImageUrl.equalsIgnoreCase(JOE_PROFILE_IMAGE_URL)) {
			logger.d("Requesting profile image url: ", profileImageUrl);
			imageFetcher.loadImage(profileImageUrl, rowImageView);
		}

		return rowView;
	}
}
