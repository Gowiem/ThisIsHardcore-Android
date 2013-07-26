package com.artisan.thisishardcore.news;

import org.apache.log4j.jmx.LoggerDynamicMBean;

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
	
	private static final int ICON_IMAGE_SIZE = 48; // Image is 48x48dp in news_item_row layout
	private final TIHNewsList newsList;
	private final String tabIdentifier;
	public static ImageFetcher imageFetcher; 

	public NewsListAdapter(Context context, TIHNewsList newsList, String tabIdentifier) {
		super(context, R.layout.news_item_row, newsList.items);
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
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.news_item_row, parent, false);
		}
		
		convertView.setTag(position);
		TextView dateTextView = (TextView) convertView.findViewById(R.id.news_date);
		TextView newsBodyTextView = (TextView) convertView.findViewById(R.id.news_body);
		TextView newsAuthorTextView = (TextView) convertView.findViewById(R.id.news_author);
		ImageView rowImageView = (ImageView)convertView.findViewById(R.id.row_image);
		ImageView indicatorView = (ImageView)convertView.findViewById(R.id.indicator);

		// Grab the newsItem for this position and get it's content
		TIHNewsItem newsItem = (TIHNewsItem) newsList.getItemAtIndex(position);
		String bodyText        = newsItem.getBody();
		String dateString      = newsItem.getDateString();
		String profileImageUrl = newsItem.getProfileUrl();
		String authorString    = newsItem.getAuthor();
		String postUrl         = newsItem.getUrl();
		
		// If this is the Fan Feed then we don't want the user to be able to click the news item row
		if (TIHUtils.isEmpty(postUrl)) {
			indicatorView.setVisibility(View.GONE);
		} else {
			indicatorView.setVisibility(View.VISIBLE);
		}
		
		// Set the content for the views
		newsBodyTextView.setText(bodyText);
		dateTextView.setText(dateString);
		if (tabIdentifier.equals(NewsFragment.OFFICIAL_TAB)) {
			newsAuthorTextView.setVisibility(View.GONE);
		}
		
		
		loadIconForProfileUrl(profileImageUrl, rowImageView, imageFetcher);

		return convertView;
	}
}
