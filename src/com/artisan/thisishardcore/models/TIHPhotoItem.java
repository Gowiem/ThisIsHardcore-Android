package com.artisan.thisishardcore.models;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import android.R.integer;

import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.utils.TIHUtils;


public class TIHPhotoItem extends TIHFeedItem {
	private static final TIHLogger logger = new TIHLogger(TIHPhotoItem.class);
	
	// Methods
	///////////
	
	public String getTimeAgo() {
		Date createdAt = TIHUtils.convertEpochTimeToDate(this.getCreatedAt());
		logger.d("getTimeAgo =>> createdAt: ", createdAt);
		int difference = (int) (createdAt.getTime() - new Date().getTime());
		int months = (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
		if (months  > 0) {
			return "More than a month ago";
		} 
		difference = Math.abs(difference);
		int diffInDays = (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
		if (diffInDays > 0) {
			return diffInDays + " days ago";
		}
		int diffInHours = (int) TimeUnit.HOURS.convert(difference, TimeUnit.MILLISECONDS);
		if (diffInHours > 0) {
			return diffInHours + " hours ago";
		}
		int diffInMinutes = (int) TimeUnit.MINUTES.convert(difference, TimeUnit.MILLISECONDS);
		return diffInMinutes + " minutes ago";	
	}
	
	public String toString() {
		return "----- Photo Item -> author: " + getAuthor() + "body: " + getBody();
	}
}
