package com.artisan.thisishardcore.models;

import java.util.List;
import java.util.Map;

import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.unifeed.TIHConstants;
import com.artisan.thisishardcore.utils.TIHUtils;


public class TIHEventList {
	private final TIHLogger logger = new TIHLogger(TIHEventList.class);

	public List<TIHEvent> events;
	public Map<Integer, List<TIHEvent>> eventsByDay;

	public TIHEventList(List<TIHEvent> events) {
		this.events = events;
		sectionUpEventsByDay();
	}
	
	private void sectionUpEventsByDay() {
		
	}

	public List<TIHEvent> getEventsByDay(final int eventDay) {
		switch (eventDay) {
			case TIHConstants.FEST_DAY_ONE:
				// Day 1
				return null;
			case TIHConstants.FEST_DAY_TWO:
				// Day 2
				return null;
			case TIHConstants.FEST_DAY_THREE:
				// Day 3
				return null;
			case TIHConstants.FEST_DAY_FOUR:
				// Day 4
				return null;
			default: 
				// Event Day is an unexpected value
				logger.e("Event Day value was unexpected. eventDay was", eventDay, 
						"but needs to be 1 - 4");
		}
		return null;
	}

	public String toString() {
		return this.events.toString();
	}

}
