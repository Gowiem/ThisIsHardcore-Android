package com.artisan.thisishardcore.schedule;


import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.imageutils.ImageFetcher;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHEvent;
import com.artisan.thisishardcore.utils.TIHListAdapter;
import com.artisan.thisishardcore.utils.TIHUtils;

public class EventListAdapter extends TIHListAdapter<TIHEvent>{
	private static final TIHLogger logger = new TIHLogger(EventListAdapter.class);

	private final Context context;
	private final ArrayList<TIHEvent> events;
	
	public static ImageFetcher imageFetcher; 

	public EventListAdapter(Context context, ArrayList<TIHEvent> events) {
		super(context, R.layout.event_row, events);
		this.context = context; 
		this.events = events;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Grab the views for this row
		View rowView = inflater.inflate(R.layout.event_row, parent, false);
		rowView.setTag(position);
		TextView artistNameTextView = (TextView) rowView.findViewById(R.id.artist_name);
		TextView eventTimeTextView = (TextView) rowView.findViewById(R.id.event_time);

		// Grab t)he event for this position and set up the views with it's data
		final TIHEvent event = events.get(position);
		artistNameTextView.setText(event.artistName);
		eventTimeTextView.setText("??? - ???");
		
		// Grab the icon image view and set it up for downloading from web or cache
		final ImageView iconImageView = (ImageView) rowView.findViewById(R.id.event_icon_image);
		ViewTreeObserver viewTreeObserver = iconImageView.getViewTreeObserver();
		viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				int height = iconImageView.getMeasuredHeight();
				int width = iconImageView.getMeasuredWidth();
				imageFetcher.setImageSize(width, height);
				
				// Grab the image from the cache or download it from the netz
				String bandIconUrl = event.iconUrl;
				if (!TIHUtils.isEmpty(bandIconUrl) && !bandIconUrl.equalsIgnoreCase("/icons/original/missing.png")) {
					logger.d("calling loadImage for url: ", bandIconUrl);
					imageFetcher.loadImage(bandIconUrl, iconImageView);
				}
				
				return true;
        	}
	    });
		
		return rowView;
	}
}
