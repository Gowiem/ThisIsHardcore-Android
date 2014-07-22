package com.useartisan.artisan.thisishardcore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.useartisan.artisan.thisishardcore.logging.TIHLogger;

public class TIHWebViewActivity extends SherlockActivity {
private static final TIHLogger logger = new TIHLogger(TIHWebViewActivity.class);
	
	public final static String WEB_VIEW_URL = "WEB_VIEW_URL";
	
	private WebView webView;
	private View progressBarContainter;
	
	@SuppressLint("SetJavaScriptEnabled")
	public void onCreate(Bundle savedInstanceState) {
		//logger.d("onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Grab the url from the intent
		Intent intent = getIntent();
		String url = intent.getStringExtra(WEB_VIEW_URL);
		
		progressBarContainter = (View) findViewById(R.id.progress_container);
		progressBarContainter.setVisibility(View.VISIBLE);
		
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new TIHWebViewClient());
		webView.loadUrl(url);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            finish();
	            return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	private class TIHWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        
        @Override
        public void onPageFinished(WebView view, String url) {
        	//logger.d("onPageFinished Loading");
        	final Handler handler = new Handler();
    	    handler.postDelayed(new Runnable() {
    	      @Override
    	      public void run() {
    	    	  progressBarContainter.setVisibility(View.GONE);
    	      }
    	    }, 1000);
        }
    }
}
