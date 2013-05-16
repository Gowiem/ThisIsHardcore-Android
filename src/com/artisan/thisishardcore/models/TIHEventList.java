package com.artisan.thisishardcore.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class TIHEventList {
	public List<TIHEvent> events;
	
	public TIHEventList(List<TIHEvent> events) {
		this.events = events;
	}
	
	public String toString() {
		return this.events.toString();
	}

}
