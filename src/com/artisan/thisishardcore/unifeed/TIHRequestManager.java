package com.artisan.thisishardcore.unifeed;



import org.apache.log4j.Logger;

import com.actionbarsherlock.app.SherlockFragment;
import com.artisan.thisishardcore.R;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.news.NewsFragment;
import com.unifeed.Constants;
import com.unifeed.MLog;


public class TIHRequestManager {
	private static final TIHLogger logger = new TIHLogger(TIHRequestManager.class);
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// TIH METHODS TILL I REMOVE ALL THE OTHERS
    ////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void getEvents(SherlockFragment fragment) {
		String path = fragment.getResources().getString(R.string.event_url);
		logger.d("getEvents path: ", path);
		TIHRestClient client = new TIHRestClient(Constants.URL + path, Constants.GET_EVENTS_DETAILS);
		try {
			client.execute(TIHRestClient.REQUEST_METHOD.GET, fragment);
		} catch (Exception e) {
			logger.e(e.toString());
			e.printStackTrace();
		}
	}
	
	public static void getNews(SherlockFragment fragment, int pageNum, int pageSize, int requestType) {
		logger.d("Page No = ", pageNum);
		logger.d("Size = ", pageSize);
		String newsUrl = buildNewsUrl(fragment, requestType);
		logger.d("getNews sending with requestType: ", requestType);
		TIHRestClient client = new TIHRestClient(newsUrl, requestType);
		if(pageNum >= 0 && pageSize >= 0){
			client.addParameter("page", String.valueOf(pageNum));
			client.addParameter("size", String.valueOf(pageSize));
		}
		try {
			logger.d("Before client execute with fragment: ", fragment);
			client.execute(TIHRestClient.REQUEST_METHOD.GET, fragment);
		} catch (Exception e) {
			logger.e(e.toString());
			e.printStackTrace();
		}
	}
	
	private static String buildNewsUrl(SherlockFragment fragment, int feedType) {
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(Constants.URL);
		urlBuilder.append(fragment.getResources().getString(R.string.news_url));
		if (feedType == TIHConstants.GET_OFFICIAL_NEWS) {
			logger.d("building news URL for Official Feed");
			urlBuilder.append("official.json");
		} else if (feedType == TIHConstants.GET_FAN_NEWS) {
			logger.d("building news URL for Fan Feed");
			urlBuilder.append("fanfeed.json");
		} else {
			logger.e("buildNewsUrl - Error: feedType was not one of the expected values.");
		}
		return urlBuilder.toString();
	}
	
//	public static void getPhotoDetails(SherlockFragment fragment, String  no) {
//		Constants.SELECTED_PHOTO = no;
//		MLog.d("", "SELECTED_PHOTO = "+no);
//
//		String photo_url1_path = fragment.getResources().getString(R.string.photo_url1);
//		String photo_url2_path = fragment.getResources().getString(R.string.photo_url2);
//		MLog.d("", "URL = "+Constants.URL + photo_url1_path +no+ photo_url2_path);
//		TIHRestClient client = new TIHRestClient(Constants.URL + photo_url1_path +no+ photo_url2_path, Constants.GET_PHOTOS_DETAILS);
//		client = fillCommonParameters(client);
//		try {
//			client.execute(TIHRestClient.REQUEST_METHOD.GET, fragment);
//		} catch (Exception e) {
//			MLog.e("", e.toString());
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
