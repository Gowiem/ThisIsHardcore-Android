package com.artisan.thisishardcore;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class TIHPhotoItem extends TIHFeedItem {
	
	// Methods
	///////////
	
	public String toString() {
		return "----- Photo Item -> author: " + getAuthor() + "body: " + getBody();
	}
}
