package com.artisan.thisishardcore.models;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class TIHEvent implements Serializable {

	private static final long serialVersionUID = -1406942906345825244L;

	public String getTime() {
		// TODO: Deal with this shit. Fuck.  
		return "??? - ???";
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
	
	public String toString() {
		return String.format("TIHEvent ------------ \n Artist Name: %s Venue: %s \n -------------", 
				this.artistName, this.venue);
	}

}
