package com.artisan.thisishardcore.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artisan.thisishardcore.FeedFragment;
import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.TIHPhotoItem;
import com.artisan.thisishardcore.TIHPhotoList;
import com.artisan.thisishardcore.utils.TIHListAdapter;
import com.artisan.thisishardcore.utils.TIHUtils;

public class PhotoPitListAdapter extends TIHListAdapter<TIHPhotoList> {
	
	private final Context context; 
	private final TIHPhotoList photoList;
	private final String tabIdentifier;

	public PhotoPitListAdapter(Context context, TIHPhotoList photoList, String tabIdentifier) {
		super(context, R.layout.photo_pit_row, photoList.items);
		this.context = context; 
		this.photoList = photoList;
		this.tabIdentifier = tabIdentifier;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Grab the views for this row
		View rowView = inflater.inflate(R.layout.photo_pit_row, parent, false);
		rowView.setTag(position);
//		ImageView newsIconImageView = (ImageView)rowView.findViewById(R.id.news_icon); 
//		TextView dateTextView = (TextView) rowView.findViewById(R.id.news_date);
//		TextView newsBodyTextView = (TextView) rowView.findViewById(R.id.news_body);
//		TextView newsAuthorTextView = (TextView) rowView.findViewById(R.id.news_author);
//		ImageView rowImageView = (ImageView)rowView.findViewById(R.id.row_image);

		// Grab the newsItem for this position and get it's content
		TIHPhotoItem photoItem = (TIHPhotoItem) photoList.getItemAtIndex(position);
		String bodyText        = photoItem.getBody();
		String dateString      = photoItem.getDateString();
		String profileImageUrl = photoItem.getProfileUrl();
		String authorString    = photoItem.getAuthor();
		
		// Set the content for the views
//		newsBodyTextView.setText(photoItem.getBody());
//		dateTextView.setText(dateString);
//		newsAuthorTextView.setText(authorString);
		
		// If we have the profile image url then either grab it from the hashmap 
		// cache and set it to the ImageView or use the DownloadImageTask and 
		// to download the image 
//		if (!TIHUtils.isEmpty(profileImageUrl)) {
//			if (this.networkImages.containsKey(profileImageUrl)) {
//				rowImageView.setImageBitmap(this.networkImages.get(profileImageUrl));
//			} else {
//				new CachingDownloadImageTask(rowImageView).execute(profileImageUrl);	
//			}
//		}

		return rowView;
	}
}
