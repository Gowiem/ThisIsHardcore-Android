package com.useartisan.artisan.thisishardcore.models;

import java.util.List;

import com.useartisan.artisan.thisishardcore.logging.TIHLogger;
import com.useartisan.artisan.thisishardcore.news.NewsFragment;
import com.google.gson.annotations.SerializedName;

public class TIHNewsList extends TIHFeedList<TIHNewsItem> {
	private static final TIHLogger logger = new TIHLogger(TIHNewsList.class); 
	
	public TIHNewsList(int totalRows, int offset, List<TIHNewsItem> newsItems) {
		this.items = newsItems;
		this.offset    = offset;
		this.totalRows = totalRows;
	}
	
	public TIHNewsList() {}
}
