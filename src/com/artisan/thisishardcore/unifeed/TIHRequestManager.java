package com.artisan.thisishardcore.unifeed;

import com.actionbarsherlock.app.SherlockFragment;
import com.artisan.thisishardcore.unifeed.TIHRestClient.REQUEST_METHOD;
import com.unifeed.AppState;
import com.unifeed.Constants;
import com.unifeed.MLog;
import com.unifeed.R;


public class TIHRequestManager {
	final static String TAG = "RequestManager";

	public static void getAuthenticationToken(SherlockFragment fragment){
		TIHRestClient restClient = new TIHRestClient(Constants.URL + Constants.AUTHENTICATION_URL, Constants.GET_AUTHENTICATION_ID);
		restClient.addParameter("unique_id", "npcompete");
		//fillCommonParameters(restClient);
		try {
			restClient.execute(REQUEST_METHOD.POST, fragment);
		} catch (Exception e) {
			MLog.e("", e.toString());
			e.printStackTrace();
		}
	}



	public static TIHRestClient fillCommonParameters(TIHRestClient client) {
		if (AppState.AUTHENTICATION_TOKEN != null) {
			client.addParameter("auth_token", AppState.AUTHENTICATION_TOKEN);
		}
		return client;

	}



	public static void getResults(SherlockFragment fragment, int pageNo, int size, int requestType) {
		MLog.d("", "Page No = "+pageNo);
		MLog.d("", "Size = "+size);
		MLog.d("", "requestType = "+requestType);
		String path = fragment.getResources().getString(R.string.results_url);
		TIHRestClient client = new TIHRestClient(Constants.URL + path, requestType);
		if(pageNo >= 0 && size >= 0){
			client.addParameter("page", String.valueOf(pageNo));
			client.addParameter("size", String.valueOf(size));
			//client.addParameter("size", String.valueOf(size * (pageNo - 1)));
		}
		client = fillCommonParameters(client);
		try {
			client.execute(TIHRestClient.REQUEST_METHOD.POST, fragment);
		} catch (Exception e) {
			MLog.e("", e.toString());
			e.printStackTrace();
		}
	}



	public static void getAlbumDetails(SherlockFragment fragment) {
		String path = fragment.getResources().getString(R.string.album_url);
		TIHRestClient restClient = new TIHRestClient(Constants.URL + path, Constants.GET_ALBUM_DETAILS);
		restClient = fillCommonParameters(restClient);
		try {
			restClient.execute(TIHRestClient.REQUEST_METHOD.POST, fragment);
		} catch (Exception e) {
			MLog.e("", e.toString());
			e.printStackTrace();
		}
	}

	public static void getVideoDetails(SherlockFragment fragment) {
		String path = fragment.getResources().getString(R.string.video_url);
		TIHRestClient restClient = new TIHRestClient(Constants.URL + path, Constants.GET_VIDEO_DETAILS);
		restClient = fillCommonParameters(restClient);
		try {
			restClient.execute(TIHRestClient.REQUEST_METHOD.POST, fragment);
		} catch (Exception e) {
			MLog.e("", e.toString());
			e.printStackTrace();
		}
	}



	public static void getEvents(SherlockFragment fragment) {
		String path = fragment.getResources().getString(R.string.event_url);
		MLog.d(TAG, path);
		MLog.d(TAG, "fragment: " + fragment);
		TIHRestClient client = new TIHRestClient(Constants.URL + path, Constants.GET_EVENTS_DETAILS);
		client = fillCommonParameters(client);
		try {
			client.execute(TIHRestClient.REQUEST_METHOD.GET, fragment);
		} catch (Exception e) {
			MLog.e("", e.toString());
			e.printStackTrace();
		}
	}



	public static void getStatusDetails(SherlockFragment fragment) {
		String path = fragment.getResources().getString(R.string.stats_url);
		TIHRestClient client = new TIHRestClient(Constants.URL + path, Constants.GET_STATUS_DETAILS);
		client = fillCommonParameters(client);
		try {
			client.execute(TIHRestClient.REQUEST_METHOD.GET, fragment);
		} catch (Exception e) {
			MLog.e("", e.toString());
			e.printStackTrace();
		}

	}



	public static void getPhotoDetails(SherlockFragment fragment, String  no) {
		Constants.SELECTED_PHOTO = no;
		MLog.d("", "SELECTED_PHOTO = "+no);

		String photo_url1_path = fragment.getResources().getString(R.string.photo_url1);
		String photo_url2_path = fragment.getResources().getString(R.string.photo_url2);
		MLog.d("", "URL = "+Constants.URL + photo_url1_path +no+ photo_url2_path);
		TIHRestClient client = new TIHRestClient(Constants.URL + photo_url1_path +no+ photo_url2_path, Constants.GET_PHOTOS_DETAILS);
		client = fillCommonParameters(client);
		try {
			client.execute(TIHRestClient.REQUEST_METHOD.GET, fragment);
		} catch (Exception e) {
			MLog.e("", e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
