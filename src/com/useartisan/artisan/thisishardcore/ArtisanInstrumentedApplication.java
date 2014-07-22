package com.useartisan.artisan.thisishardcore;

import com.artisan.application.ArtisanApplication;
import com.artisan.manager.ArtisanManager;


public class ArtisanInstrumentedApplication extends ArtisanApplication {

	@Override
	public void onCreate() {
		super.onCreate();

		// GCM_SENDER_ID is your Project Number from the Google Developer Console.
		// More information at http://developer.android.com/google/gcm/gs.html
		ArtisanManager.setPushSenderId("123580659132");
		ArtisanManager.startArtisan(this, "53cbf05afc67c718cc000005");
	}

	/**
	 * Register your Artisan Power Hook variables and Power Hook blocks here
	 *
	 * For example:
	 *
	 * PowerHookManager.registerVariable("WelcomeText", "Welcome Text Sample PowerHook", "Welcome to Artisan!");
	 *
	 * <code>
	 * 	HashMap<String, String> defaultData = new HashMap<String, String>();
	 * 	defaultData.put("discountCode", "012345ABC");
	 * 	defaultData.put("discountAmount", "25%");
	 * 	defaultData.put("shouldDisplay", "true");
	 *
	 * 	PowerHookManager.registerBlock("showAlert", "Show Alert Block", defaultData, new ArtisanBlock() {
	 * 		public void execute(Map<String, String> data, Map<String, Object> extraData) {
	 * 			if ("true".equalsIgnoreCase(data.get("shouldDisplay"))) {
	 * 				StringBuilder message = new StringBuilder();
	 * 				message.append("Buy another for a friend! Use discount code ");
	 * 				message.append(data.get("discountCode"));
	 * 				message.append(" to get ");
	 * 				message.append(data.get("discountAmount"));
	 * 				message.append(" off your purchase of 2 or more!");
	 * 				Toast.makeText((Context) extraData.get("context"), message, Toast.LENGTH_LONG).show();
	 * 			}
	 * 		}
	 * });
	 * </code>
	 *
	 * More examples at http://docs.useartisan.com/dev/quickstart-for-android/#power-hooks
	 *
	 */
	@Override
	public void registerPowerhooks() {

	}

	/**
	 * Register your Artisan In-code Experiments here
	 *
	 * For example:
	 *
	 * ArtisanExperimentManager.registerExperiment("my first experiment");
	 * ArtisanExperimentManager.addVariantForExperiment("blue variation", "my first experiment");
	 * ArtisanExperimentManager.addVariantForExperiment("green variation", "my first experiment");
	 *
	 * More examples at http://docs.useartisan.com/dev/quickstart-for-android/#in-code
	 */
	@Override
	public void registerInCodeExperiments() {

	}

	/**
	 * Register your Artisan In-code Experiments here
	 *
	 * For example:
	 *
	 * ArtisanProfileManager.registerDateTime("lastSeenAt", new Date());
	 * ArtisanProfileManager.registerLocation("lastKnownLocation");
	 * ArtisanProfileManager.registerNumber("totalOrderCount", ArtisanDemoApplication.totalOrderCount);
	 * ArtisanProfileManager.registerString("visitorType", "anonymous");
	 * ArtisanProfileManager.setGender(Gender.Female);
	 * ArtisanProfileManager.setUserAge(29);
	 * ArtisanProfileManager.setSharedUserId("abcdef123456789");
	 * ArtisanProfileManager.setUserAddress("234 Market Street, Philadelphia, PA 19106");
	 *
	 * More examples at http://docs.useartisan.com/dev/quickstart-for-android/#api
	 */
	@Override
	public void registerUserProfileVariables() {

	}
}