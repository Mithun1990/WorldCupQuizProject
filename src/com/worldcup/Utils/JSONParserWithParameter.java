package com.worldcup.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.telephony.TelephonyManager;
import android.util.Log;

public class JSONParserWithParameter {
	String possibleEmail = "mit", possiblePhoneNumber = "123";
	TelephonyManager tm;
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	static String jsonRet;
	static JSONObject jObjRet;

	// constructor
	public JSONParserWithParameter() {

	}

	public JSONObject getJSONFromUrl(String url, String data, String method)
			throws JSONException {

		// Making HTTP request
		try {
			// defaultHttpClient
			jObjRet = new JSONObject(
					"{\"loginback\":{\"employeename\":\"Naim\"}}");

			List<NameValuePair> registration = new ArrayList<NameValuePair>();
			registration.add(new BasicNameValuePair(method, data));

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			httpPost.setEntity(new UrlEncodedFormEntity(registration));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			Log.e("Str", json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());

		}

		// return JSON String
		return jObj;

	}
	
	public String getJSONFromUrlReturnString(String url, String data, String method)
			throws JSONException {

		// Making HTTP request
		try {
			// defaultHttpClient
			jObjRet = new JSONObject(
					"{\"loginback\":{\"employeename\":\"Naim\"}}");

			List<NameValuePair> registration = new ArrayList<NameValuePair>();
			registration.add(new BasicNameValuePair(method, data));

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			httpPost.setEntity(new UrlEncodedFormEntity(registration));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			Log.e("Str", json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		
		// return JSON String
		return json;

	}
}