package com.artisan.thisishardcore.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class TIHNewsList {
	
	@SerializedName("rows")
	public List<TIHNewsItem> newsList;
	
	@SerializedName("total_rows")
	public int totalRows; 
	
	public TIHNewsList(List<TIHNewsItem> newsItems) {
		this.newsList = newsItems;
	}

}
