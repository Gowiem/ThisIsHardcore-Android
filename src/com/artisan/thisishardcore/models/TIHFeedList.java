package com.artisan.thisishardcore.models;

import java.util.Collection;
import java.util.List;

import android.R.integer;

import com.google.gson.annotations.SerializedName;

// Base class for the TIHNewsList and TIHPhotosList.
public class TIHFeedList<T extends TIHFeedItem> {
	
	// Fields
	//////////
	
	@SerializedName("rows")
	//Java Generics... actually awesome?!?!?!?
	public List<T> items;
	
	@SerializedName("total_rows")
	public int totalRows;
	
	@SerializedName("offset")
	public int offset;
	
	private boolean wasJustCreated;
	
	// Setters/Getters
	///////////////////
	
	public void setWasJustCreated(boolean wasJustCreated) {
		this.wasJustCreated = wasJustCreated;
	}
	
	public boolean getWasJustCreated() {
		return wasJustCreated;
	}
	
	// Methods
	///////////
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n---- " + this.getClass() + " ----\n");
		for (TIHFeedItem item : this.items) {
			builder.append(item.toString());
			builder.append("\n");
		}
		builder.append("---- " + this.getClass() + " END ----");
		return builder.toString();
	}
	
	public String shortDescription() {
		return "TIHFeedList - type: " + getFeedItemType() + " count: " + items.size() + " wasJustCreated?: " + getWasJustCreated();
	}
	
	private String getFeedItemType() {
		String itemType;
		if (items.get(0) != null) {
			itemType = items.get(0).getAuthor().equals("This Is Hc Fest 2013") ? "OFFICIAL" : "FAN";
		} else {
			itemType = "UNKNOWN";
		}
		return itemType;
	}
	
	public T getItemAtIndex(int index) {
		return items.get(index);
	}
	
	@SuppressWarnings("unchecked")
	public void mergeItems(TIHFeedList<? extends TIHFeedItem> feedList) {
		for(TIHFeedItem newItem : feedList.items) {
			if (!isNewItemInItemsList(newItem.id)) {
				items.add((T)newItem);
			}
		}
	}
	
	private boolean isNewItemInItemsList(String itemId) {
		for(TIHFeedItem currentItem : items) {
			if (currentItem.id.equals(itemId)) {
				return true;
			}
		}
		return false;
	}
}
