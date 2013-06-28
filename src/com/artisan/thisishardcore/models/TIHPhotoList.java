package com.artisan.thisishardcore.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class TIHPhotoList extends TIHFeedList<TIHPhotoItem> {
	
	public TIHPhotoList(int totalRows, int offset, List<TIHPhotoItem> photoItems) {
		this.items     = photoItems;
		this.offset    = offset;
		this.totalRows = totalRows;
	}
	
	public TIHPhotoList() {}
}
