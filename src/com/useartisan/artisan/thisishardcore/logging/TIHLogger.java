package com.useartisan.artisan.thisishardcore.logging;

import org.apache.log4j.Logger;

import com.useartisan.artisan.thisishardcore.utils.TIHUtils;

public class TIHLogger {
	private static final Logger logger = Logger.getLogger("com.useartisan.artisan.thisishardcore");
	private final Class<?> clazz;
	public static boolean enabled;
	
	public TIHLogger(Class<?> clazz) {
//		if(clazz.getName().contains("com.test.artisan")) {
//			System.out.println("clazz name included com.test.artisan, setting runningTestSuite to true");
//			System.out.println("loggerIsConfigured: " + loggerIsConfigured);
//			TIHUtils.runningTestSuite = true;
//		}
//		if(!loggerIsConfigured) {
//			System.out.println("runningTestSuite:" + TIHUtils.runningTestSuite);
//			System.out.println("clazz getName: " + clazz.getName());
//			if (TIHUtils.runningTestSuite) {
//				TIHConfigureLog4jTest.configure();
//				System.out.println("Configured logger for Testing");
//			} else {
//				TIHConfigureLog4j.configure();
//				System.out.println("Configured logger for Android");
//			}
//			loggerIsConfigured = true;
//		}
		this.clazz = clazz;
	}
	
	public void f(Object... logObjects) { fatal(logObjects); } // Fatal
	public void d(Object... logObjects) { debug(logObjects); } // Debug
	public void w(Object... logObjects) { warn(logObjects); }  // Warn
	public void e(Object... logObjects) { error(logObjects); } // Error
	public void i(Object... logObjects) { info(logObjects); }  // Info
	
	public void fatal(Object... logObjects) { if (enabled) { logger.fatal(buildLogMessage(logObjects)); }} // Fatal
	public void debug(Object... logObjects) { if (enabled) { logger.debug(buildLogMessage(logObjects)); }} // Debug
	public void warn(Object... logObjects) { if (enabled) { logger.warn(buildLogMessage(logObjects)); }}   // Warn
	public void error(Object... logObjects) { if (enabled) { logger.error(buildLogMessage(logObjects)); }} // Error
	public void info(Object... logObjects) { if (enabled) { logger.info(buildLogMessage(logObjects)); }}   // Info

	public void logLongMessage(String messageString) {
		if(messageString.length() > 4000) {
			d("reponse: ", messageString.substring(0, 4000));
			logLongMessage(messageString.substring(4000));
		} else {
			d("response: ", messageString);
		}
	}
	
	private String buildLogMessage(Object... logObjects) {
		StringBuilder logBuilder = new StringBuilder();
		logBuilder.append(clazz.getSimpleName().toString());
		logBuilder.append("==> ");
		for(Object logObject : logObjects) {
			if (logObject != null && !TIHUtils.isEmpty(logObject.toString())) {
				logBuilder.append(logObject.toString());
			} else {
				logBuilder.append("-NULL-");
			}
			logBuilder.append(" ");
		}
		return logBuilder.toString();
	}

}
