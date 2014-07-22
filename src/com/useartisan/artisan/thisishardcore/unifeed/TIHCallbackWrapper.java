package com.artisan.thisishardcore.unifeed;


import com.artisan.thisishardcore.logging.TIHLogger;


public class TIHCallbackWrapper implements Runnable {
	private static final TIHLogger logger = new TIHLogger(TIHCallbackWrapper.class);

	private TIHResponseListener callbackActivity;
	private Object response;
	public int requestType;

	public TIHCallbackWrapper(TIHResponseListener callbackActivity, int requestType) {
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