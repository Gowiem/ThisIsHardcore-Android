package com.artisan.thisishardcore;

import java.util.List;

import com.artisan.thisishardcore.models.TIHNewsItem;
import com.google.gson.annotations.SerializedName;

public class TIHPhotoList extends TIHFeedList<TIHPhotoItem> {
	
	public TIHPhotoList(int totalRows, int offset, List<TIHPhotoItem> photoItems) {
		this.items     = photoItems;
		this.offset    = offset;
		this.totalRows = totalRows;
	}
	
	public TIHPhotoList() {}
}
