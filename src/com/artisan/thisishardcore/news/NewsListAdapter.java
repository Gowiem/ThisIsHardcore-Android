package com.artisan.thisishardcore.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHNewsItem;
import com.artisan.thisishardcore.models.TIHNewsList;
import com.artisan.thisishardcore.utils.TIHListAdapter;
import com.artisan.thisishardcore.utils.TIHListAdapter.DownloadImageTask;
import com.artisan.thisishardcore.utils.TIHUtils;

public class NewsListAdapter extends TIHListAdapter<TIHNewsList> {
	private static final TIHLogger logger = new TIHLogger(NewsListAdapter.class);

	private final Context context; 
	private final TIHNewsList newsList;

	public NewsListAdapter(Context context, TIHNewsList newsList) {
		super(context, R.layout.news_item_row, newsList.newsItems);
		this.context = context; 
		this.newsList = newsList;
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

		// Grab the newsItem for this position and get it's content
		TIHNewsItem newsItem = newsList.newsItems.get(position);
		String bodyText        = newsItem.getBody();
		String dateString      = newsItem.getDateString();
		String profileImageUrl = newsItem.getProfileUrl();
		String authorString    = newsItem.getAuthor();
		
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
				new DownloadImageTask(rowImageView).execute(profileImageUrl);	
			}
		}

		return rowView;
	}
}
