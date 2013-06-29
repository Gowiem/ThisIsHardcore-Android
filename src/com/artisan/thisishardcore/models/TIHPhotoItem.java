package com.artisan.thisishardcore.models;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.utils.TIHUtils;


public class TIHPhotoItem extends TIHFeedItem {
	private static final TIHLogger logger = new TIHLogger(TIHPhotoItem.class);
	
	// Methods
	///////////
	
	public String getTimeAgo() {
		DateTime createdAt = TIHUtils.convertEpochTimeToDateTime(this.getCreatedAt());
		DateTime now = new DateTime();
		PeriodType monthDayTime = PeriodType.yearMonthDayTime().withYearsRemoved();
		Period difference = new Period(createdAt, now, monthDayTime);
		if (difference.getMonths() > 0) {
			return String.format("About %s month ago", difference.getMonths());
		} else if (difference.getDays() > 0) {
			return String.format("%s days ago", difference.getDays());
		} else if (difference.getHours() > 0) {
			return String.format("%s hours ago", difference.getHours());
		} else if (difference.getMinutes() > 0) {
			return String.format("%s minutes ago", difference.getMinutes());
		} else {
			return "No idea when";
		}
	}
	
	public String toString() {
		return "----- Photo Item -> author: " + getAuthor() + "body: " + getBody();
	}
}
