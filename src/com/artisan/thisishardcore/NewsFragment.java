package com.artisan.thisishardcore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class NewsFragment extends SherlockFragment {

	protected static NewsFragment newInstance() {
		NewsFragment contentFragment = new NewsFragment();
		Bundle args = new Bundle();
		contentFragment.setArguments(args);
		
		return contentFragment;
	}
	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		if (getSupportFragmentManager().findFragmentById(android.R.id.newsFragment) == null) {
//			SherlockFragment fragment = NewsFragment.newInstance();
//			getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
//		}
//	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
							 ViewGroup container,
							 Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.news, container, false);
		return(result); 
	}
}
