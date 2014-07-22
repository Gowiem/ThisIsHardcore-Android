package com.useartisan.artisan.thisishardcore.logging;

import java.io.File;

import org.apache.log4j.Level;

import android.os.Environment;
import android.util.Log;
import de.mindpipe.android.logging.log4j.LogConfigurator;

public class TIHConfigureLog4j {

	/**
	 * Call {@link #configure()} from your application's activity.
	 */
	public static void configure() {
		try {
			final LogConfigurator logConfigurator = new LogConfigurator();
			logConfigurator.setFileName(Environment.getExternalStorageDirectory() + File.separator + "tih-android.log");
			logConfigurator.setRootLevel(Level.DEBUG);
			
			// Set log level of a specific logger
			logConfigurator.setLevel("org.apache", Level.ERROR);
			logConfigurator.configure();
			TIHLogger.enabled = true;
		} catch (Exception e) {
			Log.e("com.useartisan.artisan.thisishardcore", "ERROR: Couldn't configure logger.", e);
			TIHLogger.enabled = false;
			return;
		}
	}
}
