package com.artisan.thisishardcore.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;

import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.schedule.EventListAdapter;
import com.artisan.thisishardcore.unifeed.TIHConstants;


public class TIHEventList {
	private final TIHLogger logger = new TIHLogger(TIHEventList.class);

	private Calendar calendar;
	private Date DAY_1;
	private Date DAY_2;
	private Date DAY_3;
	private Date DAY_4;

	public List<TIHEvent> events;
	public Map<Integer, ArrayList<TIHEvent>> eventsByDay;

	public TIHEventList(List<TIHEvent> events) {
		this.events = events;
		this.eventsByDay = new HashMap<Integer, ArrayList<TIHEvent>>();
		instantiateEventDays();
		sectionUpEventsByDay();
		sortEvents();
	}

	private void instantiateEventDays() {
		calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2014);
		calendar.set(Calendar.MONTH, Calendar.JULY);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 0);
		// Day 1
		calendar.set(Calendar.DAY_OF_MONTH, 24);
		DAY_1 = calendar.getTime();
		// Day 2
		calendar.set(Calendar.DAY_OF_MONTH, 25);
		DAY_2 = calendar.getTime();
		// Day 3
		calendar.set(Calendar.DAY_OF_MONTH, 26);
		DAY_3 = calendar.getTime();
		// Day 4
		calendar.set(Calendar.DAY_OF_MONTH, 27);
		DAY_4 = calendar.getTime();
	}

	private void sectionUpEventsByDay() {
		for (TIHEvent event : events) {
			Date startDate = event.getStartDate();
			if (startDate.before(DAY_1)) {
				addEventToEventDayList(event, TIHConstants.FEST_DAY_ONE);
			} else if (startDate.before(DAY_2)) {
				addEventToEventDayList(event, TIHConstants.FEST_DAY_TWO);
			} else if (startDate.before(DAY_3)) {
				addEventToEventDayList(event, TIHConstants.FEST_DAY_THREE);
			} else if (startDate.before(DAY_4)) {
				addEventToEventDayList(event, TIHConstants.FEST_DAY_FOUR);
			} else {
				throw new RuntimeException("Tried to add Event which didn't fit into one of the three dates");
			}
		}
	}
	
	private void sortEvents() {
		for (int eventDay : eventsByDay.keySet()) {
			ArrayList<TIHEvent> eventList = eventsByDay.get(eventDay);  
			if(eventList.size() != 0) {
				Collections.sort(eventList);
			}
		}
	}

	private void addEventToEventDayList(TIHEvent event, int eventDayIdentifier) {
		if (eventsByDay.containsKey(eventDayIdentifier)) {
			ArrayList<TIHEvent> eventList = eventsByDay.get(eventDayIdentifier);
			eventList.add(event);
		} else {
			ArrayList<TIHEvent> eventList = new ArrayList<TIHEvent>();
			eventList.add(event);
			eventsByDay.put(eventDayIdentifier, eventList);
		}
	}

	public ArrayList<TIHEvent> getEventsByDay(final int eventDay) {
		if (eventsByDay.containsKey(eventDay)) {
			return eventsByDay.get(eventDay);		
		} else {
			throw new RuntimeException("No events for the eventDay: " + eventDay);
		}
	}

	public String toString() {
		return this.events.toString();
	}

}
