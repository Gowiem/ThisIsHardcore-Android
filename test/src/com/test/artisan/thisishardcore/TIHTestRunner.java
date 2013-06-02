package com.test.artisan.thisishardcore;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

import com.artisan.thisishardcore.logging.TIHConfigureLog4jTest;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.utils.TIHUtils;


public class TIHTestRunner extends RobolectricTestRunner {
	private final TIHLogger logger = new TIHLogger(TIHTestRunner.class);
	
    public TIHTestRunner(Class testClass) throws InitializationError {
        super(testClass);
        System.out.println("After setting runningTestSuite to true");
        TIHConfigureLog4jTest.configure();
        TIHUtils.runningTestSuite = true;
    }
    
//  @Override
//	public void afterTest(final Method method) { }
    
//	@Override
//	public void beforeTest(final Method method) { }

//	public void prepareTest(Object arg0) {} 
} 