package com.artisan.thisishardcore;



import org.apache.log4j.Logger;

import android.app.ProgressDialog;
import android.content.Context;

public class TIHAppState {	
	private static final Logger log = Logger.getLogger(TIHAppState.class);

	public static ProgressDialog progressDialog;

	public TIHAppState() {
		
	}

	private static class TIHAppStateHolder {
		private static final TIHAppState INSTANCE = new TIHAppState();
	}

	public static TIHAppState getInstance() {
		return TIHAppStateHolder.INSTANCE;
	}


	public static void showActivityIndicator(Context context, String msg){
		log.debug("showActivityIndicator");
		if(progressDialog != null && progressDialog.isShowing()){
			progressDialog.setMessage(msg);
		}
		else{
			progressDialog = ProgressDialog.show(context, null, msg);
			progressDialog.show();
			log.debug("progressDialog.show() was called");
		}
	}

	public static void showActivityIndicator(Context context){
		showActivityIndicator(context, "please wait...");
	}

	public static void stopActivityIndicator(){
		log.debug("stopActivityIndicator - progressDialog: " + progressDialog);
		try {
			if(progressDialog != null && progressDialog.isShowing())
				progressDialog.dismiss();
		} catch (Exception e) {

		}

	}

}
