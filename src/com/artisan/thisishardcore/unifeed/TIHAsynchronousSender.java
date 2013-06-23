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
import org.apache.log4j.Logger;

import android.os.Handler;

import com.artisan.thisishardcore.PhotoPitFragment;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.parser.TIHParser;


public class TIHAsynchronousSender extends Thread {
	private static final TIHLogger logger = new TIHLogger(TIHAsynchronousSender.class);

	private DefaultHttpClient httpClient;
	private HttpRequest request;
	private Handler handler;
	private TIHCallbackWrapper wrapper;

	public TIHAsynchronousSender(HttpRequest request, Handler handler,
			TIHCallbackWrapper wrapper) {
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
			HttpResponse response;
			synchronized (this) {
				httpClient.setParams(httpParams);
				response = getClient().execute((HttpUriRequest) request);
			}
			Object object = invokeParser(response, wrapper.requestType);
			logger.d("Before setResponse");
			wrapper.setResponse(object);
			logger.d("Before posting to Handler");
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
		} catch (Exception e) {
			logger.e("exception: " + e);
		}
	}

	private synchronized HttpClient getClient() {
		return httpClient;
	}

	private Object invokeParser(HttpResponse response, int reqType){
		Object object = null;
		switch (reqType) {
		
		case TIHConstants.GET_FAN_NEWS:
			logger.d("Before parsing fan news list");
			object = TIHParser.parseNewsList(response);
			break;
		case TIHConstants.GET_OFFICIAL_NEWS:
			logger.d("Before parsing official news list");
			object = TIHParser.parseNewsList(response);
			break;
		case TIHConstants.GET_EVENTS_DETAILS:
			logger.d("Before parseEventList");
			object = TIHParser.parseEventList(response);
			break;
		case TIHConstants.GET_OFFICIAL_PHOTOS:
			object = TIHParser.parsePhotoList(response);
			break;
		case TIHConstants.GET_FAN_PHOTOS:
			object = TIHParser.parsePhotoList(response);
			break;
			
		default:
			break;
		}
		return object;
	}

}