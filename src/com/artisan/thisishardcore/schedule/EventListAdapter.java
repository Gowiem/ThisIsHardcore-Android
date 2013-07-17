package com.artisan.thisishardcore.schedule;


import java.util.ArrayList;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.imageutils.ImageFetcher;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHEvent;
import com.artisan.thisishardcore.utils.TIHListAdapter;
import com.artisan.thisishardcore.utils.TIHUtils;

public class EventListAdapter extends TIHListAdapter<TIHEvent>{
	private static final TIHLogger logger = new TIHLogger(EventListAdapter.class);
	
	private final int ICON_IMAGE_SIZE = 60; // Image is 60x60dp in event_row layout
	private final Context context;
	private final ArrayList<TIHEvent> events;
	public static ImageFetcher imageFetcher; 
	

	public EventListAdapter(Context context, ArrayList<TIHEvent> events) {
		super(context, R.layout.event_row, events);
		this.context = context; 
		this.events = events;
		setupImageFetcher();
	}
	
	private void setupImageFetcher() {
		float screenDensity = context.getResources().getDisplayMetrics().density;
		imageFetcher.setImageSize((int)(ICON_IMAGE_SIZE * screenDensity));
		imageFetcher.setLoadingImage(R.drawable.default_event_icon);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Grab the views for this row
		View rowView = inflater.inflate(R.layout.event_row, parent, false);
		TextView artistNameTextView = (TextView) rowView.findViewById(R.id.artist_name);
		TextView eventTimeTextView = (TextView) rowView.findViewById(R.id.event_time);

		// Grab the event for this position and set up the views with it's data
		final TIHEvent event = events.get(position);
		artistNameTextView.setText(event.artistName);
		eventTimeTextView.setText("??? - ???");
		
		// Grab the icon image view and set it up for downloading from web or cache
		ImageView iconImageView = (ImageView) rowView.findViewById(R.id.event_icon_image);
		String bandIconUrl = event.iconUrl;
		if (!TIHUtils.isEmpty(bandIconUrl) && !bandIconUrl.equalsIgnoreCase("/icons/original/missing.png")) {
			logger.d("calling loadImage for url: ", bandIconUrl);
			imageFetcher.loadImage(bandIconUrl, iconImageView);
		}
		
		// Tag the row for later use. Note: not yet implemented
		rowView.setTag(position);
		
		return rowView;
	}
}
