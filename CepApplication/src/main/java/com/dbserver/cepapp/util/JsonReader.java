package com.dbserver.cepapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class JsonReader {

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) {
	URL http_url = null;
	HttpURLConnection connection = null;
	int httpCode = 0;
	InputStream is = null;
	try {
		http_url = new URL(url);
		connection = (HttpURLConnection) http_url.openConnection();
		httpCode = connection.getResponseCode();
		if(httpCode < HttpURLConnection.HTTP_BAD_REQUEST){
			is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject jsonObj = new JSONObject(jsonText);
			return jsonObj;
		}
		else {
			JSONObject jsonError = new JSONObject();
			jsonError.put("httpError", httpCode);
			return jsonError;
		}
	} catch(IOException e) {
		//In case an HTTP Error is encountered
		e.printStackTrace();
	} catch(JSONException e) {
		e.printStackTrace();
    }
	
	return null;
  }
  
  public static JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {
    URL http_url = null;
	HttpURLConnection connection = null;
	int httpCode = 0;
	InputStream is = null;
	try {
		http_url = new URL(url);
		connection = (HttpURLConnection) http_url.openConnection();
		httpCode = connection.getResponseCode();
		if(httpCode < HttpURLConnection.HTTP_BAD_REQUEST){
			is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONArray jsonArray = new JSONArray(jsonText);
			return jsonArray;
		}
		else {
			JSONArray jsonArrayError = new JSONArray();
			JSONObject jsonError = new JSONObject();
			jsonError.put("http_error", httpCode);
			jsonArrayError.put(jsonError);
			return jsonArrayError;
		}
	} catch(IOException e) {
		//In case an HTTP Error is encountered
		e.printStackTrace();
	} catch(JSONException e) {
		e.printStackTrace();
    }
	
	return null;
  }
  
}