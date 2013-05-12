package com.artisan.thisishardcore.parser;

import java.util.List;


public class TIHEventList {
	
	public List<TIHEvent> events;
	
	public TIHEventList(List<TIHEvent> events) {
		this.events = events;
	}
	
	public String toString() {
		return this.events.toString();
	}

}
