package com.artisan.thisishardcore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.artisan.thisishardcore.logging.TIHLogger;

public class TIHWebViewActivity extends SherlockActivity {
private static final TIHLogger logger = new TIHLogger(TIHWebViewActivity.class);
	
	public final static String WEB_VIEW_URL = "WEB_VIEW_URL";
	
	private WebView webView;
	
	@SuppressLint("SetJavaScriptEnabled")
	public void onCreate(Bundle savedInstanceState) {
		logger.d("onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Grab the url from the intent
		Intent intent = getIntent();
		String url = intent.getStringExtra(WEB_VIEW_URL);
		
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new TIHWebViewClient());
		webView.loadUrl(url);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // This is called when the Home (Up) button is pressed
	            // in the Action Bar.
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
    }
}
