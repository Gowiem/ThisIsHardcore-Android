package com.artisan.thisishardcore.parser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unifeed.MLog;
import com.unifeed.responsetype.AlbumsDetails;
import com.unifeed.responsetype.Authentication;
import com.unifeed.responsetype.PhotoDetails;
import com.unifeed.responsetype.Results;
import com.unifeed.responsetype.StatsDetails;
import com.unifeed.responsetype.VideoDetails;



public class TIHParser {
	private static final String TAG = "PARSER";
	
	public static void logResponse(String str) {
	    if(str.length() > 4000) {
	        MLog.d(TAG, str.substring(0, 4000));
	        logResponse(str.substring(4000));
	    } else {
	    	MLog.d(TAG, str);
	    }
	}

	public static Reader response(Reader reader){
		BufferedReader br = new BufferedReader(reader);
		try {
			StringBuilder builder = new StringBuilder();
			String response = "";
			String string;
			while ((string = br.readLine()) != null) {
				builder.append(string);
			}
			response = builder.toString();
			logResponse(response);
			InputStream inputStream = new ByteArrayInputStream(response.getBytes("UTF-8"));
			Reader inReader = new InputStreamReader(inputStream);
			MLog.d("", response);
			return inReader;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Authentication parseAuthenticationToken(HttpResponse response){
		if(response == null){
			return null;
		}
		else {
			try {
				Reader reader = new InputStreamReader(response.getEntity()
						.getContent());
				reader = response(reader);
				return new Gson().fromJson(reader, Authentication.class);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}

	public static String outPutResponse(HttpResponse httpResponse){
		if(httpResponse == null){
			return null;
		}
		else{
			try {
				Reader reader = new InputStreamReader(httpResponse.getEntity().getContent());
				BufferedReader br = new BufferedReader(reader);
				String response = "";
				String str = br.readLine();
				while (str!= null) {
					response += str;
					str = br.readLine();

				}
				MLog.d("", response);
				return response;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}

	public static Results parseResult(HttpResponse response){
		if(response == null){
			return null;
		}
		else {
			try {
				Reader reader = new InputStreamReader(response.getEntity()
						.getContent());
				reader = response(reader);
				return new Gson().fromJson(reader, Results.class);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}

	public static AlbumsDetails parseAlbumDetails(HttpResponse response) {
		if(response == null)
			return null;
		else{
			try {
				Reader reader = new InputStreamReader(response.getEntity().getContent());
				reader = response(reader);
				return new Gson().fromJson(reader, AlbumsDetails.class);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static TIHEventList parseEventList(HttpResponse response) {
		if(response == null){
			return null;
		}
		else{
			try {
				MLog.d(TAG, "parseEventDetails - response: " + response);
				Reader reader = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
				Reader newReader = response(reader);
				MLog.d(TAG, "reader: " + reader);
				Type listType = new TypeToken<List<TIHEvent>>(){}.getType();
				List<TIHEvent> events = (List<TIHEvent>)new Gson().fromJson(newReader, listType);
				TIHEventList eventList = new TIHEventList(events);
				return eventList;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	public static Object paresVideoDetails(HttpResponse response) {
		if(response == null){
			return null;
		}
		else{
			try {
				Reader reader = new InputStreamReader(response.getEntity().getContent());
				reader = response(reader);
				return new Gson().fromJson(reader, VideoDetails.class);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

	public static Object parseStatsDetails(HttpResponse response) {
		if(response == null){
			return null;
		}
		else{
			try {
				Reader reader = new InputStreamReader(response.getEntity().getContent());
				reader = response(reader);
				return new Gson().fromJson(reader, StatsDetails.class);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

	public static PhotoDetails parsePhotoDetails(HttpResponse response) {
		if(response == null){
			return null;
		}
		else{
			try {
				Reader reader = new InputStreamReader(response.getEntity().getContent());
				reader = response(reader);
				return new Gson().fromJson(reader, PhotoDetails.class);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

}
