package com.artisan.thisishardcore.schedule;

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
import com.artisan.thisishardcore.utils.TIHListAdapter.DownloadImageTask;
import com.artisan.thisishardcore.utils.TIHUtils;

public class EventListAdapter extends TIHListAdapter<TIHEvent>{
	private static final TIHLogger logger = new TIHLogger(EventListAdapter.class);

	private final Context context; 
	private final TIHEventList eventList;

	public EventListAdapter(Context context, TIHEventList eventList) {
		super(context, R.layout.event_row, eventList.events);
		logger.d("EventListAdapter constructor");
		this.context = context; 
		this.eventList = eventList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		logger.d("getView - position:", position);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Grab the views for this row
		View rowView = inflater.inflate(R.layout.event_row, parent, false);
		rowView.setTag(position);
		ImageView iconImageView = (ImageView)rowView.findViewById(R.id.event_icon_image); 
		TextView artistNameTextView = (TextView) rowView.findViewById(R.id.artist_name);
		TextView eventTimeTextView = (TextView) rowView.findViewById(R.id.event_time);

		// Grab the event for this position and set up the views with it's data
		TIHEvent event = eventList.events.get(position);
		artistNameTextView.setText(event.artistName);
		eventTimeTextView.setText("??? - ???");
		
		// Grab the image from the cache or download it from the netz
		String bandIconUrl = event.iconUrl;
		if (TIHUtils.isEmpty(bandIconUrl)) {
			if (this.networkImages.containsKey(bandIconUrl)) {
				iconImageView.setImageBitmap(this.networkImages.get(bandIconUrl));
			} else {
				new DownloadImageTask(iconImageView).execute(bandIconUrl);	
			}
		}

		return rowView;
	}
}
