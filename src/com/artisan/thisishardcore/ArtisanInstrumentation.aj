package com.artisan.thisishardcore;

public aspect ArtisanInstrumentation{


public void com.artisan.thisishardcore.MainActivity.onResume(){
		super.onResume();
}
public void com.artisan.thisishardcore.MainActivity.onPause(){
		super.onPause();
}
public void com.artisan.thisishardcore.MainActivity.onDestroy(){
		super.onDestroy();
}


public void com.artisan.thisishardcore.SplashScreenActivity.onResume(){
		super.onResume();
}
public void com.artisan.thisishardcore.SplashScreenActivity.onPause(){
		super.onPause();
}
public void com.artisan.thisishardcore.SplashScreenActivity.onDestroy(){
		super.onDestroy();
}


public void com.artisan.thisishardcore.TIHWebViewActivity.onResume(){
		super.onResume();
}
public void com.artisan.thisishardcore.TIHWebViewActivity.onPause(){
		super.onPause();
}
public void com.artisan.thisishardcore.TIHWebViewActivity.onDestroy(){
		super.onDestroy();
}


}