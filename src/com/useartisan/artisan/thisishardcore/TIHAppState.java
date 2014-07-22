package com.useartisan.artisan.thisishardcore;



import org.apache.log4j.Logger;

import com.useartisan.artisan.thisishardcore.logging.TIHLogger;

import android.app.ProgressDialog;
import android.content.Context;

public class TIHAppState {	
	private static final TIHLogger logger = new TIHLogger(TIHAppState.class);

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
		//logger.d("showActivityIndicator");
		if(progressDialog != null && progressDialog.isShowing()){
			progressDialog.setMessage(msg);
		}
		else{
			progressDialog = ProgressDialog.show(context, null, msg);
			progressDialog.show();
			//logger.d("progressDialog.show() was called");
		}
	}

	public static void showActivityIndicator(Context context){
		showActivityIndicator(context, "please wait...");
	}

	public static void stopActivityIndicator(){
		//logger.d("stopActivityIndicator - progressDialog: ", progressDialog);
		try {
			if(progressDialog != null && progressDialog.isShowing())
				progressDialog.dismiss();
		} catch (Exception e) {

		}

	}

}
