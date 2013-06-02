package com.test.artisan.thisishardcore;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.artisan.thisishardcore.models.TIHEvent;
import com.artisan.thisishardcore.models.TIHEventList;
import com.artisan.thisishardcore.unifeed.TIHConstants;

@RunWith(TIHTestRunner.class)
public class TIHEventListTest {
	TIHEventList eventList;
	TIHEvent event1, event2, event3, event4;

	@Before
	public void beforeTests() { 
		List<TIHEvent> events = new ArrayList<TIHEvent>();
		event1 = new TIHEvent();
		event2 = new TIHEvent();
		event3 = new TIHEvent();
		event4 = new TIHEvent();

		// Kid Dynamite Start Time - Day 1
		event1.startTime = 1375934400; 
		// Modern Life is War Start Time - Day 2 
		event2.startTime = 1376024400;
		// Defeater Start Time - Day 3
		event3.startTime = 1376143200;
		// Suicide File Start Time - Day 4
		event4.startTime = 1376208000;

		events.add(event2);
		events.add(event4);
		events.add(event1);
		events.add(event3);

		eventList = new TIHEventList(events);
	}

	@Test 
	public void testSectionUpEventsByDateForDayOne() {
		TIHEvent dayOneEvent = eventList.eventsByDay.get(TIHConstants.FEST_DAY_ONE).get(0);
		assertEquals("eventsByDay at position FEST_DAY_ONE should have the day 1 event", dayOneEvent, event1);
	}

	@Test 
	public void testSectionUpEventsByDateForDayTwo() {
		TIHEvent dayTwoEvent = eventList.eventsByDay.get(TIHConstants.FEST_DAY_TWO).get(0);
		assertEquals("eventsByDay at position FEST_DAY_TWO should have the day 2 event", dayTwoEvent, event2);
	}

	@Test 
	public void testSectionUpEventsByDateForDayThree() {
		TIHEvent dayThreeEvent = eventList.eventsByDay.get(TIHConstants.FEST_DAY_THREE).get(0);
		assertEquals("eventsByDay at position FEST_DAY_THREE should have the day 3 event", dayThreeEvent, event3);
	}

	@Test 
	public void testSectionUpEventsByDateForDayFour() {
		TIHEvent dayFourEvent = eventList.eventsByDay.get(TIHConstants.FEST_DAY_FOUR).get(0);
		assertEquals("eventsByDay at position FEST_DAY_FOUR should have the day 4 event", dayFourEvent, event4);
	}

	@Test
	public void testGetEventsByDayForDayOne() {
		ArrayList<TIHEvent> dayOneEventList = eventList.getEventsByDay(TIHConstants.FEST_DAY_ONE);
		assertEquals("getEventsByDay should return the day one events for FEST_DAY_ONE", event1, dayOneEventList.get(0));
	}

	@Test
	public void testGetEventsByDayForNonexistantDay() {
		try {
			ArrayList<TIHEvent> dayOneEventList = eventList.getEventsByDay(5);
			fail("Should have thrown SomeException but did not!");
		} catch( final RuntimeException e ) {
			String exceptionMessage = "No events for the eventDay:";
			assert(e.getMessage().contains(exceptionMessage));
		}
	}

}
