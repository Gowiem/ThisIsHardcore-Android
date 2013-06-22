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
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHNewsItem;
import com.artisan.thisishardcore.models.TIHNewsList;
import com.artisan.thisishardcore.unifeed.TIHConstants;
import com.artisan.thisishardcore.utils.TIHListAdapter;
import com.artisan.thisishardcore.utils.TIHListAdapter.CachingDownloadImageTask;
import com.artisan.thisishardcore.utils.TIHUtils;

public class NewsListAdapter extends TIHListAdapter<TIHNewsList> {
	private static final TIHLogger logger = new TIHLogger(NewsListAdapter.class);

	private final Context context; 
	private final TIHNewsList newsList;
	private final String tabIdentifier;

	public NewsListAdapter(Context context, TIHNewsList newsList, String tabIdentifier) {
		super(context, R.layout.news_item_row, newsList.newsItems);
		this.context = context; 
		this.newsList = newsList;
		this.tabIdentifier = tabIdentifier;
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
		TIHNewsItem newsItem = newsList.newsItems.get(position);
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
		
		// If we have the profile image url then either grab it from the hashmap 
		// cache and set it to the ImageView or use the DownloadImageTask and 
		// to download the image 
		if (!TIHUtils.isEmpty(profileImageUrl)) {
			if (this.networkImages.containsKey(profileImageUrl)) {
				rowImageView.setImageBitmap(this.networkImages.get(profileImageUrl));
			} else {
				new CachingDownloadImageTask(rowImageView).execute(profileImageUrl);	
			}
		}

		return rowView;
	}
}
