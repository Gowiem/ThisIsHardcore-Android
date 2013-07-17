package com.artisan.thisishardcore.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ArrayAdapter;

import com.artisan.thisishardcore.logging.TIHLogger;

public abstract class TIHListAdapter<T> extends ArrayAdapter<T> {
	private static final TIHLogger logger = new TIHLogger(TIHListAdapter.class);
	
	private final Context context;
	
	public TIHListAdapter(Context context, int textViewResourceId, List objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
	}
}
