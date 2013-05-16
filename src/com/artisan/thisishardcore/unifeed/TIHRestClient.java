package com.artisan.thisishardcore.unifeed;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

import android.os.Handler;

import com.actionbarsherlock.app.SherlockFragment;
import com.unifeed.MLog;
import com.unifeed.webservice.AsynchronousSender;
import com.unifeed.webservice.CallbackWrapper;
import com.unifeed.webservice.ResponseListener;

public class TIHRestClient {
	private final Logger log = Logger.getLogger(TIHRestClient.class);
	
	private ArrayList<NameValuePair> headers;
	private ArrayList<NameValuePair> parameters;

	public enum REQUEST_METHOD {
		GET, POST, PUT
	};

	private String url;
	private int requestType;

	public TIHRestClient(String url, int requestType) {
		this.url = url;
		this.requestType = requestType;
		headers = new ArrayList<NameValuePair>();
		parameters = new ArrayList<NameValuePair>();

	}

	public void execute(REQUEST_METHOD method, SherlockFragment fragment)
			throws Exception {
		switch (method) {
		case GET:
			String combinedParams = addParamsToUrl();
            log.debug("execute request - Final URL: " + url + combinedParams);
            HttpGet request = new HttpGet(url + combinedParams);

            //add headers
            for(NameValuePair h : headers){
                request.addHeader(h.getName(), h.getValue());
            }
            
			new TIHAsynchronousSender(request,
					new Handler(),
					new TIHCallbackWrapper((ResponseListener) fragment, requestType)
			).start();
            break;

//		case POST:
//			MLog.d("", "url = "+url);
//			HttpPost postRequest = new HttpPost(this.url);
//			for (NameValuePair param :parameters) {
//				MLog.d("", ""+param);
//			}
//
//			if (!parameters.isEmpty()) {
//				postRequest.setEntity(new UrlEncodedFormEntity(parameters,
//						HTTP.UTF_8));
//				new AsynchronousSender(postRequest, new Handler(),
//						new CallbackWrapper((ResponseListener) fragment,
//								requestType)).start();
//			}
//			break;
		default:
			break;
		}
	}

	private String addParamsToUrl() throws UnsupportedEncodingException {
		String combinedParams = "";
		if(!parameters.isEmpty()){
		    combinedParams += "?";
		    for(NameValuePair p : parameters)
		    {
		    	// if array that is json formatted array, just add the value to URL
		    	if(p.getName().equalsIgnoreCase("array")) {
		    		combinedParams += p.getValue();
		    	} else {
		            String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(),"UTF-8");
		            if(combinedParams.length() > 1){
		                combinedParams  +=  "&" + paramString;
		            }
		            else{
		                combinedParams += paramString;
		            }
		    	}

		    }
		}
		return combinedParams;
	}

	public void addParameter(String name, String value) {
		parameters.add(new BasicNameValuePair(name, value));
	}

	public void AddHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}


}
