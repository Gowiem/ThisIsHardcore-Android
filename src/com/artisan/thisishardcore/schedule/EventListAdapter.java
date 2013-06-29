package com.artisan.thisishardcore.schedule;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHEvent;
import com.artisan.thisishardcore.models.TIHEventList;
import com.artisan.thisishardcore.utils.TIHListAdapter;
import com.artisan.thisishardcore.utils.TIHListAdapter.CachingDownloadImageTask;
import com.artisan.thisishardcore.utils.TIHUtils;

public class EventListAdapter extends TIHListAdapter<TIHEvent>{
	private static final TIHLogger logger = new TIHLogger(EventListAdapter.class);

	private final Context context; 
	private final ArrayList<TIHEvent> events;

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
		ImageView iconImageView = (ImageView) rowView.findViewById(R.id.event_icon_image); 
		TextView artistNameTextView = (TextView) rowView.findViewById(R.id.artist_name);
		TextView eventTimeTextView = (TextView) rowView.findViewById(R.id.event_time);

		// Grab the event for this position and set up the views with it's data
		TIHEvent event = events.get(position);
		artistNameTextView.setText(event.artistName);
		eventTimeTextView.setText("??? - ???");
		
		// Grab the image from the cache or download it from the netz
		String bandIconUrl = event.iconUrl;
		if (!TIHUtils.isEmpty(bandIconUrl) && !bandIconUrl.equalsIgnoreCase("/icons/original/missing.png")) {
			if (this.networkImages.containsKey(bandIconUrl)) {
				iconImageView.setImageBitmap(this.networkImages.get(bandIconUrl));
			} else {
				new CachingDownloadImageTask(iconImageView).execute(bandIconUrl);
			}
		}
		
//		if (!TIHUtils.isEmpty(profileImageUrl)) {
//			if (this.networkImages.containsKey(profileImageUrl)) {
//				userImageView.setImageBitmap(this.networkImages.get(profileImageUrl));
//			} else {
//				new CachingDownloadImageTask(userImageView).execute(profileImageUrl);	
//			}
//		}

		return rowView;
	}
}
