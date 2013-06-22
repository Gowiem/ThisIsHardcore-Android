package com.artisan.thisishardcore.models;

import java.util.List;

import com.artisan.thisishardcore.TIHFeedList;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.news.NewsFragment;
import com.google.gson.annotations.SerializedName;

public class TIHNewsList extends TIHFeedList {
	private static final TIHLogger logger = new TIHLogger(TIHNewsList.class); 
	
	@SerializedName("rows")
	public List<TIHNewsItem> newsItems;
	
	@SerializedName("total_rows")
	public int totalRows;
	
	@SerializedName("offset")
	public int offset; 
	
	public TIHNewsList(int totalRows, int offset, List<TIHNewsItem> newsItems) {
		this.newsItems  = newsItems;
		this.offset    = offset;
		this.totalRows = totalRows;
	}
	
	public TIHNewsList() {
		
	}
	
	public TIHNewsItem getNewsItemAtIndex(int index) {
		return newsItems.get(index);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n---- TIHNewsList ----\n");
		for (TIHNewsItem newsItem : this.newsItems) {
			builder.append(newsItem.toString());
			builder.append("\n");
		}
		builder.append("---- TIHNewsList END ----");
		return builder.toString();
	}

}
