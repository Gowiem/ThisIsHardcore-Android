package com.useartisan.artisan.thisishardcore.models;

import com.useartisan.artisan.thisishardcore.logging.TIHLogger;
import com.useartisan.artisan.thisishardcore.models.TIHFeedItem.Value;
import com.useartisan.artisan.thisishardcore.utils.TIHUtils;
import com.google.gson.annotations.SerializedName;


public class TIHNewsItem extends TIHFeedItem {
	private static final TIHLogger logger = new TIHLogger(TIHNewsItem.class);
	
	// Methods
	///////////
	
	public String toString() {
		return "----- News Item -> author: " + getAuthor() + "body: " + getBody();
	}
}
