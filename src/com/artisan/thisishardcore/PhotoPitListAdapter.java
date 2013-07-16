package com.artisan.thisishardcore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.models.TIHPhotoItem;
import com.artisan.thisishardcore.models.TIHPhotoList;
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
		ImageView instaImageView = (ImageView)rowView.findViewById(R.id.insta_image);
		ProgressBar progressImageView = (ProgressBar)rowView.findViewById(R.id.image_progress);
		TextView dateTextView = (TextView) rowView.findViewById(R.id.days_ago);
		TextView photoBodyTextView = (TextView) rowView.findViewById(R.id.photo_body);
		TextView photoAuthorTextView = (TextView) rowView.findViewById(R.id.photo_author);
		ImageView userImageView = (ImageView)rowView.findViewById(R.id.user_image);

		// Grab the newsItem for this position and get it's content
		TIHPhotoItem photoItem = (TIHPhotoItem) photoList.getItemAtIndex(position);
		String bodyText        = photoItem.getBody();
		String dateString      = photoItem.getTimeAgo();
		String instaImageUrl   = photoItem.getUrl();
		String profileImageUrl = photoItem.getProfileUrl();
		String authorString    = photoItem.getAuthor();
		
		// Set the content for the views
		photoBodyTextView.setText(photoItem.getBody());
		dateTextView.setText(dateString);
		photoAuthorTextView.setText(authorString);
		
		if (!TIHUtils.isEmpty(profileImageUrl)) {
//			if (this.networkImages.containsKey(profileImageUrl)) {
//				userImageView.setImageBitmap(this.networkImages.get(profileImageUrl));
//			} else {
//				new CachingDownloadImageTask(userImageView).execute(profileImageUrl);	
//			}
		}
		 
		if (!TIHUtils.isEmpty(instaImageUrl)) {
//			if (this.networkImages.containsKey(instaImageUrl)) {
//				instaImageView.setImageBitmap(this.networkImages.get(instaImageUrl));
//			} else {
//				new CachingDownloadImageTask(instaImageView, progressImageView).execute(instaImageUrl);	
//			}
		}

		return rowView;
	}
}
