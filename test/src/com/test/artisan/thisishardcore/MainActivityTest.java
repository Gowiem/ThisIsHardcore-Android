package com.test.artisan.thisishardcore;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.internal.ActionBarSherlockCompat;
import com.actionbarsherlock.internal.ActionBarSherlockNative;
import com.artisan.thisishardcore.MainActivity;


@RunWith(TIHTestRunner.class)
public class MainActivityTest {
	private MainActivity activity;
	private String appName;
	
	@Before
    public void setUp() throws Exception {
		System.out.println("SHRED");
		
        // Robolectric && ABS => http://robolectric.blogspot.com/2013/03/using-actionbarsherlock-with.html
        ActionBarSherlock.registerImplementation(ActionBarSherlockRobolectric.class);
        ActionBarSherlock.unregisterImplementation(ActionBarSherlockNative.class);
        ActionBarSherlock.unregisterImplementation(ActionBarSherlockCompat.class);
        
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
    }
	
	@Test
    public void shouldHaveThisIsHardcoreTitle() throws Exception {
		System.out.println("SHRED");
		assertThat((String) activity.getSupportActionBar().getTitle(), equalTo("This is Hardcore"));
    }
}
