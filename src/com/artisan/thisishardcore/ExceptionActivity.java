package com.artisan.thisishardcore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;

public class ExceptionActivity extends Activity {
	
	public final static String ERROR_REPORT = "ERROR_REPORT";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exception);
		
		Intent intent = getIntent();
		final String errorReport = intent.getExtras().getString(ERROR_REPORT); 
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Error Occured");
		builder.setMessage(R.string.error_message);
		builder.setCancelable(false);
		builder.setPositiveButton("Email", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				sendMeAnEmail(errorReport);
				System.exit(0);
			}
		});
		builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				System.exit(0);
			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	private void sendMeAnEmail(String errorReport) {
		/* Create the Intent */
		final Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","mgowie@useartisan.com", null));

		/* Fill it with Data */
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "This is Hardcore Error");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey I got an error: " + errorReport);

		/* Send it off to the Activity-Chooser */
		this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	}

}
