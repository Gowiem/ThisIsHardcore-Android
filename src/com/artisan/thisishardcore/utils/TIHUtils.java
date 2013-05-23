package com.artisan.thisishardcore.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.artisan.thisishardcore.logging.TIHLogger;

public class TIHUtils {
	private static final TIHLogger logger = new TIHLogger(TIHUtils.class);
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm a - EEE MMM dd, yyyy"); 
	
	public static String convertEpochTimeToString(long epochTime) {
		Date epochDate = new Date(epochTime*1000);
		String dateString = dateFormatter.format(epochDate);
//		logger.d("Converted epochInt:", epochTime, "to date:", epochDate, "and then to date string:", dateString);
		return dateString;
	}

	public static boolean isEmpty(String string) {
		if (string == null || string.trim().length() <= 0) {
			return true;
		} else {
			return false;
		}
	}

}
