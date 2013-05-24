package com.artisan.thisishardcore.models;

import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.utils.TIHUtils;
import com.google.gson.annotations.SerializedName;


public class TIHNewsItem {
	private static final TIHLogger logger = new TIHLogger(TIHNewsItem.class);
	
	public String toString() {
		return "----- News Item -> author: " + getAuthor() + "body: " + getBody();
	}
	
	public String getDateString() {
		return TIHUtils.convertEpochTimeToString(this.getCreatedAt());
	}
	
	///////////////////////////////////////////////////////////////////////////
	// Fields
    ///////////////////////////////////////////////////////////////////////////
	
	@SerializedName("id")
	public long id;
	
	@SerializedName("key")
	public long key;
	
	@SerializedName("value")
	public Value value;
	
	public String getAuthor() {
		return value.author;
	}
	
	public String getBody() {
		return value.body;
	}
	
	public long getCreatedAt() {
		return value.createdAt;
	}
	
	public String getProfileUrl() {
		return value.profileUrl;
	}
	
	public String getProvider() {
		return value.provider;
	}
	
	public String getTags() {
		return value.tags;
	}
	
	public String getUrl() {
		return value.url;
	}
	
	public static class Value {
		
		@SerializedName("author")
		public String author;
		
		@SerializedName("body")
		public String body;
		
		@SerializedName("created_at")
		public long createdAt;
		
		@SerializedName("profile_url")
		public String profileUrl;
		
		@SerializedName("provider")
		public String provider;
		
		@SerializedName("tags")
		public String tags;
		
		@SerializedName("url")
		public String url;
	}
	

	
}
