package com.artisan.thisishardcore;

import android.app.Application;

import com.artisan.armanager.ARConfigOption;
import com.artisan.armanager.ARConfigOption.AnalyticsEngineType;
import com.artisan.armanager.ARManager;
import com.artisan.arservices.ArtisanService;
import com.artisan.incodeapi.ARExperimentManager;

public class ArtisanInstrumentedApplication extends Application{

	@Override
	public void onCreate(){
		super.onCreate();

		ArtisanService.init(this);
		 
		String experiment = "E1";
		ARExperimentManager.registerExperimentWithDescription(experiment, "This is the first experiment");
		ARExperimentManager.addVariantForExperiment("default", experiment);
		ARExperimentManager.addVariantForExperiment("cyan", experiment);
		ARExperimentManager.addVariantForExperiment("red", experiment);
		
		
		String experimentDos = "E2";
		ARExperimentManager.registerExperimentWithDescription(experimentDos, "This is the first experiment");
		ARExperimentManager.addVariantForExperiment("default", experimentDos);
		ARExperimentManager.addVariantForExperiment("cyan", experimentDos);
		ARExperimentManager.addVariantForExperiment("red", experimentDos);
		
		ARManager.start("51d2e79e714375beaa000002", "1.0", loadLocalConfiguration());
	}
	
	private ARConfigOption loadLocalConfiguration() {

	    String apiAddressPortLocal = "http://10.1.10.62:3000"; // TODO

	    String messageHostPortLocal = "ws://10.1.10.62:8070"; // TODO

	    String messageAPIHostPortLocal = "http://10.1.10.62:8070"; // TODO

	    ARConfigOption.AnalyticsProperty analyticsPropLocal = new ARConfigOption.AnalyticsProperty();
	    analyticsPropLocal.setServerURL("http://appren.10.1.10.62:3001/analytics"); // TODO
	    analyticsPropLocal.setDebugEnabled(true);
	    analyticsPropLocal.setAnalyticsEnabled(true);
	    analyticsPropLocal.setAnalyticsEngineType(AnalyticsEngineType.ARTISAN_ANALYTICS_ENGINE);
	    analyticsPropLocal.setSampleRate(100);
	    analyticsPropLocal.setDispatchPeriod(60);

	    ARConfigOption localCongiuration = new ARConfigOption();
	    localCongiuration.setApiHostPort(apiAddressPortLocal);
	    localCongiuration.setMessageHostPort(messageHostPortLocal);
	    localCongiuration.setMessageAPIHostPort(messageAPIHostPortLocal);
	    localCongiuration.setDebugLogs(true);

	    localCongiuration.setAnalyticsProperty(analyticsPropLocal);
	    localCongiuration.setAbOnlyMode(true);
	    localCongiuration.setOverrideDesignMode(false);
	    localCongiuration.setRegisterDevice("/api/registerDevice");
	    localCongiuration.setMessageServerPath("/ms");
	    localCongiuration.setRetrieveContent("/fakeS3/");
	    localCongiuration.setUiDefinitions("/api/uiDefinitions");
	    localCongiuration.setCreateResourceFromExisting("/api/createResourceFromExisting");
	    localCongiuration.setSecureHTTP(false);
	    localCongiuration.setSecureWS(false);

	    return localCongiuration;
	}
}