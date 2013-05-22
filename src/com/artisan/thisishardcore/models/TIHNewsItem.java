package com.artisan.thisishardcore.models;

import com.google.gson.annotations.SerializedName;


public class TIHNewsItem {
	
	public String toString() {
		return "----- News Item -> body: " + getBody();
	}
	
	public String getDateString() {
		return "??? - ???";
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
	
	public int getCreatedAt() {
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
		public int createdAt;
		
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
