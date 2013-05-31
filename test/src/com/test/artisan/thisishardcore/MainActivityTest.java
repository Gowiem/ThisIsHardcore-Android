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
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHEventList;


@RunWith(TIHTestRunner.class)
public class MainActivityTest {
	private final TIHLogger logger = new TIHLogger(MainActivityTest.class);
	
	@Before
    public void setUp() throws Exception {
		logger.d("setUp");
    }
}
