package com.useartisan.artisan.thisishardcore;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.useartisan.artisan.thisishardcore.logging.TIHLogger;

public class TIHExceptionHandler implements UncaughtExceptionHandler {

	private Context context;
	
	public TIHExceptionHandler(Context context) {
		this.context = context;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable e) {
		StackTraceElement[] arr = e.getStackTrace();
		final StringBuffer report = new StringBuffer(e.toString());
		final String lineSeperator = "-------------------------------\n\n";
		final String SINGLE_LINE_SEP = "\n";
		final String DOUBLE_LINE_SEP = "\n\n";
		report.append(DOUBLE_LINE_SEP);
		report.append("--------- Stack trace ---------\n\n");
		for (int i = 0; i < arr.length; i++) {
			report.append( "    ");
			report.append(arr[i].toString());
			report.append(SINGLE_LINE_SEP);
		}
		report.append(lineSeperator);
		// If the exception was thrown in a background thread inside
		// AsyncTask, then the actual exception can be found with getCause
		report.append("--------- Cause ---------\n\n");
		Throwable cause = e.getCause();
		if (cause != null) {
			report.append(cause.toString());
			report.append(DOUBLE_LINE_SEP);
			arr = cause.getStackTrace();
			for (int i = 0; i < arr.length; i++) {
				report.append("    ");
				report.append(arr[i].toString());
				report.append(SINGLE_LINE_SEP);
			}
		}
		// Getting the Device brand,model and sdk verion details.
		report.append(lineSeperator);
		report.append("--------- Device ---------\n\n");
		report.append("Brand: ");
		report.append(Build.BRAND);
		report.append(SINGLE_LINE_SEP);
		report.append("Device: ");
		report.append(Build.DEVICE);
		report.append(SINGLE_LINE_SEP);
		report.append("Model: ");
		report.append(Build.MODEL);
		report.append(SINGLE_LINE_SEP);
		report.append("Id: ");
		report.append(Build.ID);
		report.append(SINGLE_LINE_SEP);
		report.append("Product: ");
		report.append(Build.PRODUCT);
		report.append(SINGLE_LINE_SEP);
		report.append(lineSeperator);
		report.append("--------- Firmware ---------\n\n");
		report.append(SINGLE_LINE_SEP);
		report.append("Release: ");
		report.append(Build.VERSION.RELEASE);
		report.append(SINGLE_LINE_SEP);
		report.append("Incremental: ");
		report.append(Build.VERSION.INCREMENTAL);
		report.append(SINGLE_LINE_SEP);
		report.append(lineSeperator);

		Log.e("com.useartisan.artisan.thisishardcore", "Report ::" + report.toString());
		Intent crashedIntent = new Intent(context, ExceptionActivity.class);
		crashedIntent.putExtra(ExceptionActivity.ERROR_REPORT, report.toString());
//		crashedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//		crashedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(crashedIntent);

		System.exit(0);
	}

}
