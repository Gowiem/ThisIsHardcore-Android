package com.artisan.thisishardcore;

import org.apache.log4j.Logger;

import com.actionbarsherlock.app.SherlockFragment;
import com.artisan.thisishardcore.unifeed.TIHRequestManager;
import com.unifeed.AppState;

public class UnifeedFragment extends SherlockFragment {
	private final Logger log = Logger.getLogger(UnifeedFragment.class);
	
	//authentication request
	public void sendAuthenticationRequest(){
		log.debug("send AuthenticationRequest - Fragment: " + this);
		SherlockFragment that = (SherlockFragment)this;
		TIHRequestManager.getAuthenticationToken(that);	
	}
	
	//event request
	public void sendEventRequest(){
		log.debug("UnifeedFragment - sendEventRequest");
		if(AppState.AUTHENTICATION_TOKEN == null) {
			log.debug("UnifeedFragment - No authentication token, sending Auth Request");
			sendAuthenticationRequest();
		} else {
			log.debug("UnifeedFragment - Has Auth Token, Sending getEventDetails with fragment: " + this);
			TIHRequestManager.getEvents((SherlockFragment)this);
		}
	}

}
