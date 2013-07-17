package com.artisan.thisishardcore;

import java.util.logging.Logger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.imageutils.ImageFetcher;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHPhotoItem;
import com.artisan.thisishardcore.models.TIHPhotoList;
import com.artisan.thisishardcore.news.NewsListAdapter;
import com.artisan.thisishardcore.utils.TIHListAdapter;
import com.artisan.thisishardcore.utils.TIHUtils;

public class PhotoPitListAdapter extends TIHListAdapter<TIHPhotoList> {
	private static final TIHLogger logger = new TIHLogger(TIHListAdapter.class);

	private static final int ICON_IMAGE_SIZE = 48; // Image is 48x48dp in photo_pit_row layout
	private static final int INSTA_IMAGE_SIZE = 306; // Image is 306x306 in photo_pit_row layout
	private final TIHPhotoList photoList;
	private final String tabIdentifier;
	public static ImageFetcher iconFetcher;
	public static ImageFetcher instaFetcher;

	public PhotoPitListAdapter(Context context, TIHPhotoList photoList, String tabIdentifier) {
		super(context, R.layout.photo_pit_row, photoList.items);
		this.photoList = photoList;
		this.tabIdentifier = tabIdentifier;
		setupImageFetcher();
	}
	
	private void setupImageFetcher() {
		float screenDensity = context.getResources().getDisplayMetrics().density;
		iconFetcher.setImageSize((int)(ICON_IMAGE_SIZE * screenDensity));
		instaFetcher.setImageSize((int)(INSTA_IMAGE_SIZE * screenDensity));
		instaFetcher.setLoadingImage(R.drawable.band_load);
		iconFetcher.setLoadingImage(R.drawable.tih_news_icon);
	}
	
	@Override
	public boolean isEnabled(int position) {
		return false;
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
		
		loadIconForProfileUrl(profileImageUrl, userImageView, iconFetcher);
		 
		if (!TIHUtils.isEmpty(instaImageUrl)) {
			logger.d("Requesting instagrame Image at URL: ", instaImageUrl);
			instaFetcher.loadImage(instaImageUrl, instaImageView);
		}

		return rowView;
	}
}
