package com.useartisan.artisan.thisishardcore;

import java.util.logging.Logger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.useartisan.artisan.thisishardcore.R;
import com.useartisan.artisan.thisishardcore.imageutils.ImageFetcher;
import com.useartisan.artisan.thisishardcore.logging.TIHLogger;
import com.useartisan.artisan.thisishardcore.models.TIHPhotoItem;
import com.useartisan.artisan.thisishardcore.models.TIHPhotoList;
import com.useartisan.artisan.thisishardcore.news.NewsListAdapter;
import com.useartisan.artisan.thisishardcore.utils.TIHListAdapter;
import com.useartisan.artisan.thisishardcore.utils.TIHUtils;

public class PhotoPitListAdapter extends TIHListAdapter<TIHPhotoList> {
	private static final TIHLogger logger = new TIHLogger(PhotoPitListAdapter.class);

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
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.photo_pit_row, parent, false);
		}
		
		convertView.setTag(position);
		ImageView instaImageView = (ImageView)convertView.findViewById(R.id.insta_image);
		TextView dateTextView = (TextView) convertView.findViewById(R.id.days_ago);
		TextView photoBodyTextView = (TextView) convertView.findViewById(R.id.photo_body);
		TextView photoAuthorTextView = (TextView) convertView.findViewById(R.id.photo_author);
		ImageView userImageView = (ImageView)convertView.findViewById(R.id.user_image);

		// Grab the newsItem for this position and get it's content
		TIHPhotoItem photoItem = (TIHPhotoItem) photoList.getItemAtIndex(position);
		String bodyText        = photoItem.getBody();
		String dateString      = photoItem.getTimeAgo();
		String instaImageUrl   = photoItem.getUrl();
		String profileImageUrl = photoItem.getProfileUrl();
		String authorString    = photoItem.getAuthor();
		
		// Set the content for the views
		photoBodyTextView.setText(bodyText);
		dateTextView.setText(dateString);
		photoAuthorTextView.setText(authorString);
		
		loadIconForProfileUrl(profileImageUrl, userImageView, iconFetcher);
		 
		if (!TIHUtils.isEmpty(instaImageUrl)) {
			instaFetcher.loadImage(instaImageUrl, instaImageView);
		}

		return convertView;
	}
}
