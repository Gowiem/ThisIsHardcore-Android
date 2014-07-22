package com.artisan.thisishardcore.unifeed;



import org.apache.log4j.Logger;

import android.R.integer;

import com.actionbarsherlock.app.SherlockFragment;
import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.news.NewsFragment;


public class TIHRequestManager {
	private static final TIHLogger logger = new TIHLogger(TIHRequestManager.class);
	
	public static void getEvents(SherlockFragment fragment) {
		String path = fragment.getResources().getString(R.string.event_url);
		String url  = TIHConstants.URL + path; 
		TIHRestClient client = new TIHRestClient(url, TIHConstants.GET_EVENTS_DETAILS);
		try {
			client.execute(TIHRestClient.REQUEST_METHOD.GET, fragment);
		} catch (Exception e) {
			logger.e("error with TIHRestClient.execute - e: ", e.getLocalizedMessage());
		}
	}
	
	public static void getNews(SherlockFragment fragment, int pageNum, int pageSize, int requestType) {
		//logger.d("--- getNews ---");
		//logger.d("Page No = ", pageNum);
		String newsUrl = buildFeedUrl(fragment, requestType, R.string.news_url);
		TIHRestClient client = new TIHRestClient(newsUrl, requestType);
		if(pageNum >= 0 && pageSize >= 0) {
			client.addParameter("page", String.valueOf(pageNum));
			client.addParameter("size", String.valueOf(pageSize));
		}
		try {
			client.execute(TIHRestClient.REQUEST_METHOD.GET, fragment);
		} catch (Exception e) {
			logger.e("error with TIHRestClient.execute - e: ", e.getLocalizedMessage());
		}
	}
	
	public static void getPhotos(SherlockFragment fragment, int pageNum, int pageSize, int feedType) {
		//logger.d("---- getPhotos ---");
		String photosUrl = buildFeedUrl(fragment, feedType, R.string.photo_pit_url); 
		TIHRestClient client = new TIHRestClient(photosUrl, feedType);
		if(pageNum >= 0 && pageSize >= 0) {
			client.addParameter("page", String.valueOf(pageNum));
			client.addParameter("size", String.valueOf(pageSize));
		}
		try {
			client.execute(TIHRestClient.REQUEST_METHOD.GET, fragment);
		} catch (Exception e) {
			logger.e("error with TIHRestClient.execute - e: ", e.getLocalizedMessage());
		}
	}
	
	private static String buildFeedUrl(SherlockFragment fragment, int feedType, int urlId) {
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(TIHConstants.URL);
		urlBuilder.append(fragment.getResources().getString(urlId));
		// Check the feedType for this request and append the corresponding feed to the URL
		if (feedType == TIHConstants.GET_OFFICIAL_NEWS || feedType == TIHConstants.GET_OFFICIAL_PHOTOS) {
			urlBuilder.append("official.json");
		} else if (feedType == TIHConstants.GET_FAN_NEWS) {
			urlBuilder.append("fanfeed.json");
		} else if (feedType == TIHConstants.GET_FAN_PHOTOS) {
			urlBuilder.append("tagged.json");
		} else {
			logger.e("buildNewsUrl - Error: feedType was not one of the expected values.");
		}
		return urlBuilder.toString();
	}
}
