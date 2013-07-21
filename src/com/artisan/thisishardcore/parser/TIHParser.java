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
import org.apache.log4j.Logger;

import com.artisan.thisishardcore.MainActivity;
import com.artisan.thisishardcore.logging.TIHLogger;
import com.artisan.thisishardcore.models.TIHEvent;
import com.artisan.thisishardcore.models.TIHEventList;
import com.artisan.thisishardcore.models.TIHFeedList;
import com.artisan.thisishardcore.models.TIHNewsList;
import com.artisan.thisishardcore.models.TIHPhotoList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class TIHParser {
	private static final TIHLogger logger = new TIHLogger(TIHParser.class);

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
			InputStream inputStream = new ByteArrayInputStream(response.getBytes("UTF-8"));
			Reader inReader = new InputStreamReader(inputStream);
			return inReader;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static TIHNewsList parseNewsList(HttpResponse response) {
		if(response != null){
			try {
				Reader reader = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
				Reader newReader = response(reader);
				Type listType = new TypeToken<TIHNewsList>(){}.getType();
				TIHNewsList newsList = (TIHNewsList)new Gson().fromJson(newReader, listType);
				return newsList;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static TIHEventList parseEventList(HttpResponse response) {
		if(response != null){
			try {
				Reader reader = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
				Reader newReader = response(reader);
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

	public static TIHPhotoList parsePhotoList(HttpResponse response) {
		if(response != null){
			try {
				Reader reader = new InputStreamReader(response.getEntity().getContent());
				Reader newReader = response(reader);
				Type listType = new TypeToken<TIHPhotoList>(){}.getType();
				TIHPhotoList photoList = (TIHPhotoList)new Gson().fromJson(newReader, listType);
				return photoList;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
