package com.artisan.thisishardcore;

import java.lang.Thread.UncaughtExceptionHandler;

import com.artisan.thisishardcore.imageutils.ImageFetcher;
import com.artisan.thisishardcore.logging.TIHLogger;

public class TIHExceptionHandler implements UncaughtExceptionHandler {
	private static final TIHLogger logger = new TIHLogger(TIHExceptionHandler.class);
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		logger.e("UncaughtException -> exception: ", ex.getLocalizedMessage());	
	}

}
