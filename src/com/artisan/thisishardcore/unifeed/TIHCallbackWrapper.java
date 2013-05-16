package com.artisan.thisishardcore.unifeed;


import org.apache.log4j.Logger;

import com.artisan.thisishardcore.PhotoPitFragment;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.unifeed.webservice.ResponseListener;


public class TIHCallbackWrapper implements Runnable {
	private static final TIHLogger logger = new TIHLogger(TIHCallbackWrapper.class);

	private ResponseListener callbackActivity;
	private Object response;
	public int requestType;

	public TIHCallbackWrapper(ResponseListener callbackActivity, int requestType) {
		this.callbackActivity = callbackActivity;
		this.requestType = requestType;
	}

	@Override
	public void run() {
		callbackActivity.onResponseReceived(response, requestType);
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public void setResponseStr(Object response) {
		this.response = response;
	}

}