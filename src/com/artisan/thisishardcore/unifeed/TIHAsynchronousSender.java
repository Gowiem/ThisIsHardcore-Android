package com.artisan.thisishardcore.unifeed;

import java.io.IOException;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.Handler;

import com.artisan.thisishardcore.parser.TIHParser;
import com.unifeed.Constants;
import com.unifeed.MLog;
import com.unifeed.parser.Parser;
import com.unifeed.webservice.CallbackWrapper;

public class TIHAsynchronousSender extends Thread {
	private static String TAG = "AsynchronousSender";

	private DefaultHttpClient httpClient;
	private HttpRequest request;
	private Handler handler;
	private CallbackWrapper wrapper;

	public TIHAsynchronousSender(HttpRequest request, Handler handler,
			CallbackWrapper wrapper) {
		this.request = request;
		this.handler = handler;
		this.wrapper = wrapper;
		httpClient = new DefaultHttpClient();
	}

	@Override
	public void run() {
		try {
			HttpParams httpParams = new BasicHttpParams();
			int timeout = 60000;
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
			httpParams = httpParams.setParameter("JSON", true);
			MLog.d(TAG, "httParams.json: " + httpParams.getParameter("JSON"));
			HttpResponse response;
			synchronized (this) {
				httpClient.setParams(httpParams);
				response = getClient().execute((HttpUriRequest) request);
			}
			Object object = invokeParser(response, wrapper.requestType);
			MLog.d(TAG, "wrapper: " + wrapper);
			MLog.d(TAG, "handle: " + handler);
			wrapper.setResponse(object);
			handler.post(wrapper);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Object object = invokeParser(null, wrapper.requestType);
			wrapper.setResponse(object);
			handler.post(wrapper);

		} catch (IOException e) {
			e.printStackTrace();
			Object object = invokeParser(null, wrapper.requestType);
			wrapper.setResponse(object);
			handler.post(wrapper);
		}catch (Exception e) {
			MLog.d(TAG, "exception: " + e);
		}
	}

	private synchronized HttpClient getClient() {
		return httpClient;
	}

	private Object invokeParser(HttpResponse response, int reqType){
		Object object = null;
		switch (reqType) {
		case Constants.GET_AUTHENTICATION_ID:
			object = Parser.parseAuthenticationToken(response);
			break;
		case Constants.GET_NEWS_DETAILS:
			//object = Parser.outPutResponse(response);
			object = Parser.parseResult(response);
			break;

		case Constants.GET_ALBUM_DETAILS:
			//object = Parser.outPutResponse(response);
			object = Parser.parseAlbumDetails(response);
			break;

		case Constants.GET_VIDEO_DETAILS:
			//object = Parser.outPutResponse(response);
			object = Parser.paresVideoDetails(response);
			break;
		case Constants.GET_EVENTS_DETAILS:
			MLog.d("GET_EVENTS_DETAILS", "parseEventDetails");
			object = TIHParser.parseEventList(response);
			break;
		case Constants.GET_STATUS_DETAILS:
			//object = Parser.outPutResponse(response);
			object = Parser.parseStatsDetails(response);
			break;

		case Constants.GET_PHOTOS_DETAILS:
			//object = Parser.outPutResponse(response);
			object = Parser.parsePhotoDetails(response);
			break;



		default:
			break;
		}
		return object;
	}

}