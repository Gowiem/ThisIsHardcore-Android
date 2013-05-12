package com.artisan.thisishardcore.schedule;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.parser.TIHEvent;
import com.artisan.thisishardcore.parser.TIHEventList;
import com.artisan.thisishardcore.utils.TIHUtils;

public class EventListAdapter extends ArrayAdapter<TIHEvent>{
	private final Context context; 
	private final TIHEventList eventList;
	
	public EventListAdapter(Context context, TIHEventList eventList) {
		super(context, R.layout.event_row, eventList.events);
		this.context = context; 
		this.eventList = eventList;
	}
	
	 @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
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
	    artistNameTextView.setText(eventList.events.get(position).artistName);
	    eventTimeTextView.setText("??? - ???");
	    if (event.iconUrl != null && !event.iconUrl.isEmpty()) {
//	    	Bitmap imageBitmap = TIHUtils.getBitmapFromURL(event.iconUrl);
//	    	iconImageView.setImageBitmap(imageBitmap);
	    }
	   
	    return rowView;
	  }
}
