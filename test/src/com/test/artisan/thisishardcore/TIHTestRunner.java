package com.test.artisan.thisishardcore;

import java.lang.reflect.Method;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHEventList;

import android.os.Build;


public class TIHTestRunner extends RobolectricTestRunner {
	private final TIHLogger logger = new TIHLogger(TIHTestRunner.class);
	
    public TIHTestRunner(Class testClass) throws InitializationError {
        super(testClass);
        logger.d("TIHTestRunner constructor");
    }
    
//  @Override
//	public void afterTest(final Method method) { }
    
//	@Override
//	public void beforeTest(final Method method) { }

//	public void prepareTest(Object arg0) {} 
} 