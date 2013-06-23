package com.artisan.thisishardcore;

import java.util.List;

import com.artisan.thisishardcore.models.TIHNewsItem;
import com.google.gson.annotations.SerializedName;

// Base class for the TIHNewsList and TIHPhotosList.
public class TIHFeedList<T extends TIHFeedItem> {
	
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
	
	public T getItemAtIndex(int index) {
		return items.get(index);
	}
	
	// Fields
	//////////
	
	@SerializedName("rows")
	//Java Generics... actually awesome?!?!?!?
	public List<T> items;
	
	@SerializedName("total_rows")
	public int totalRows;
	
	@SerializedName("offset")
	public int offset;
}
