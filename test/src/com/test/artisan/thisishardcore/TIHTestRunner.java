package com.test.artisan.thisishardcore;

import java.lang.reflect.Method;

import org.junit.runners.model.InitializationError;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.os.Build;


public class TIHTestRunner extends RobolectricTestRunner {
	private static final int SDK_INT = Build.VERSION.SDK_INT;
	
    public TIHTestRunner(Class testClass) throws InitializationError {
        super(testClass);
        System.out.println("TIHTestRunner constructor");
        System.out.println("SDK INT: " + SDK_INT);
//        Robolectric.Reflection.setFinalStaticField(Build.VERSION.class, "SDK_INT", 10);
    }
    
//    @Override
//	public void afterTest(final Method method) {
//		Robolectric.Reflection.setFinalStaticField(Build.VERSION.class, "SDK_INT", SDK_INT);
//	}
//
//	@Override
//	public void beforeTest(final Method method) { 
//		System.out.println("FUCK FUCK SHIT FUCK");
//		Robolectric.Reflection.setFinalStaticField(Build.VERSION.class, "SDK_INT", 15);
//	}

	public void prepareTest(Object arg0) {} 
} 