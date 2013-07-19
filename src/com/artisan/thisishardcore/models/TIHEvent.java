package com.artisan.thisishardcore.models;

import java.io.Serializable;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.utils.TIHUtils;
import com.google.gson.annotations.SerializedName;

public class TIHEvent implements Serializable, Comparable<TIHEvent> {
	private static final TIHLogger logger = new TIHLogger(TIHEvent.class);

	private static final long serialVersionUID = -1406942906345825244L;
	
	@Override
	public int compareTo(TIHEvent event) {
		if (getStartDate() == null || event.getStartDate() == null) {
			logger.w("TIHEvent.compareTo - one of the events we were trying to compare " +
					"had a null start date");
			return 0;
		}
		return getStartDate().compareTo(event.getStartDate());
	}
	
	
	
	public String getEventTimeString() {
		DateTime startTime = getStartDateTime();
		DateTime endTime = getEndDateTime();
		if (startTime.isEqual(endTime)) {
			return "??? - ???";
		}
		DateTimeFormatter sFormatter = DateTimeFormat.forPattern("hh:mm");
		//PeriodFormatter formatter = new PeriodFormatterBuilder().appendHours().appendSeparator(":").appendMinutes().toFormatter();
		String startString = sFormatter.print(startTime);
		startString = stripLeadingZero(startString); 
		DateTimeFormatter eFormatter = DateTimeFormat.forPattern("hh:mm");
		String endString = eFormatter.print(endTime);
		endString = stripLeadingZero(endString);
		String amOrPmStartTime = startTime.getHourOfDay() > 12 ? "PM" : "AM";
		return startString + " - " + endString + " " + amOrPmStartTime;
	}
	
	private String stripLeadingZero(String timeString) {
		if (timeString.startsWith("0")) {
			return timeString.substring(1, timeString.length());
		} else {
			return timeString;
		}
	}
	
	private DateTime eventStartDateTime;
	private DateTime eventEndDateTime;
	
	// Joda DateTime Methods
	public DateTime getStartDateTime() {
		if(eventStartDateTime == null) {
			eventStartDateTime = TIHUtils.convertEpochTimeToDateTime(startTime);
		}
		return eventStartDateTime;
	}
	
	public DateTime getEndDateTime() {
		if(eventEndDateTime == null) {
			eventEndDateTime = TIHUtils.convertEpochTimeToDateTime(endTime);
		}
		return eventEndDateTime;
	}
	
	private Date eventStartDate;
	private Date eventEndDate;
	
	// Date Methods
	public Date getEndDate() {
		if(eventEndDate == null) {
			eventEndDate = TIHUtils.convertEpochTimeToDate(endTime);
		}
		return eventEndDate;
	}
	
	public Date getStartDate() {
		if(eventStartDate == null) {
			eventStartDate = TIHUtils.convertEpochTimeToDate(startTime);
		}
		return eventStartDate;
	}
	
	public String toString() {
		return String.format("TIHEvent --- \n Artist Name: %s Venue: %s \n ---", 
				this.artistName, this.venue);
	}
	
	// ========================================================================
	// == JSON Attributes =====================================================
	// ========================================================================

	@SerializedName("id")
	public int id;
	
	@SerializedName("persona_id")
	public int personaId;
	
	@SerializedName("artist_name")
	public String artistName;
	
	@SerializedName("venue")
	public String venue;

	@SerializedName("artist_website")
	public String artistWebsite;
	
	@SerializedName("description")
	public String description;
	
	@SerializedName("facebook_url")
	public String facebookUrl;
	
	@SerializedName("twitter_url")
	public String twitterUrl;
	
	@SerializedName("image_url")
	public String imageUrl;
	
	@SerializedName("image_content_type")
	public String imageType;
	
	@SerializedName("image_file_name")
	public String imageFileName;
	
	@SerializedName("image_file_size")
	public int imageFileSize;
	
	@SerializedName("image_updated_at")
	public int imageUpdatedAt;
	
	@SerializedName("icon_url")
	public String iconUrl;
	
	@SerializedName("icon_content_type")
	public String iconType;
	
	@SerializedName("icon_file_name")
	public String iconFileName;
	
	@SerializedName("icon_updated_at")
	public int iconUpdatedAt;
	
	@SerializedName("icon_file_size")
	public int iconFileSize;
	
	@SerializedName("start_time")
	public int startTime;
	
	@SerializedName("end_time")
	public int endTime;
	
	@SerializedName("updated_at")
	public int updatedAt;

	@SerializedName("created_at")
	public int createdAt;
}
