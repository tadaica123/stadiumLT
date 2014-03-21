/**
 * ============================================================================
 *
 * Copyright (C) 2012 Android MySportactivities Systems.  All rights reserved. The content 
 * presented herein may not, under any circumstances, be reproduced, in 
 * whole or in any part or form, without written permission from 
 * MySportactivities Systems.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are NOT permitted. Neither the name of MySportactivities Systems,
 * nor the names of contributors may be used to endorse or promote products 
 * derived from this software without specific prior written permission.
 *
 * ============================================================================
 *
 * Author: tuan
 *  
 *
 * Revision History
 * ----------------------------------------------------------------------------
 * Date                Author              Comment, bug number, fix description
 *
 * Feb 28, 2012      tuan@edge-works.net           version 1.0
 *
 * ----------------------------------------------------------------------------
 */
package com.leontran.stadiumlt.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.leontran.stadiumlt.model.StadiumDetailModel;

import android.app.Activity;
import android.util.Log;



// TODO: Auto-generated Javadoc
/**
 * Created on Sep 17, 2012.
 * 
 * @author leon
 * @version 1.0
 * @copyright Copyright (c) Android MySportactivities Systems, all rights
 *            reserved
 */
public class Services {

	/**
	 * Do post.
	 * 
	 * 'url' the url @ aram 'kvPairs' the kv pairs
	 * 
	 * @return the http response
	 * @throws ClientProtocolException
	 *             the client protocol exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */

	public static HttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	@SuppressWarnings("null")
	public static HttpResponse doPost(String url, Map<String, String> kvPairs)
			throws ClientProtocolException, IOException {
		// HttpClient httpclient = new DefaultHttpClient();

		HttpClient httpclient = getNewHttpClient();

		HttpPost httppost = new HttpPost(url);
		// setProxy(defaultHttpClient);

		if (kvPairs != null || kvPairs.isEmpty() == false) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
					kvPairs.size());
			String k, v;
			Iterator<String> itKeys = kvPairs.keySet().iterator();

			while (itKeys.hasNext()) {
				k = itKeys.next();
				v = kvPairs.get(k);
				nameValuePairs.add(new BasicNameValuePair(k, v));
			}

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		}
		HttpResponse response;
		response = httpclient.execute(httppost);
		return response;

	}
	
	public static HttpResponse doPost(String url, String json)
			throws ClientProtocolException, IOException {
		// HttpClient httpclient = new DefaultHttpClient();

		HttpClient httpclient = getNewHttpClient();

		HttpPost httppost = new HttpPost(url);
		// setProxy(defaultHttpClient);
		StringEntity string = new StringEntity(json, "UTF-8");
		string.setContentType("application/json");
		httppost.setEntity(string);
		HttpResponse response;
		response = httpclient.execute(httppost);
		return response;

	}
	
	public static HttpResponse doPut(String url, String json)
			throws ClientProtocolException, IOException {
		// HttpClient httpclient = new DefaultHttpClient();

		HttpClient httpclient = getNewHttpClient();

		HttpPut httpput = new HttpPut(url);
		// setProxy(defaultHttpClient);
		StringEntity string = new StringEntity(json, "UTF-8");
		string.setContentType("application/json");
		httpput.setEntity(string);
		HttpResponse response;
		response = httpclient.execute(httpput);
		return response;

	}
	
	

	public static HttpResponse doRemove(String url)
			throws ClientProtocolException, IOException {

		HttpClient httpclient = getNewHttpClient();

		HttpDelete httpdelete = new HttpDelete(url);
		// setProxy(defaultHttpClient);
		HttpResponse response;

		response = httpclient.execute(httpdelete);

		return response;
	}
	
	@SuppressWarnings("null")
	public static HttpResponse doPut(String url, Map<String, String> kvPairs)
			throws ClientProtocolException, IOException {
		// HttpClient httpclient = new DefaultHttpClient();

		HttpClient httpclient = getNewHttpClient();

		HttpPut httppost = new HttpPut(url);
		// setProxy(defaultHttpClient);

		if (kvPairs != null || kvPairs.isEmpty() == false) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
					kvPairs.size());
			String k, v;
			Iterator<String> itKeys = kvPairs.keySet().iterator();

			while (itKeys.hasNext()) {
				k = itKeys.next();
				v = kvPairs.get(k);
				nameValuePairs.add(new BasicNameValuePair(k, v));
			}

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		}

		HttpResponse response;
		response = httpclient.execute(httppost);
		return response;

	}
	
	public  String  doGet(String url)
			throws ClientProtocolException, IOException {
		// HttpClient httpclient = new DefaultHttpClient();

		HttpClient httpclient = getNewHttpClient();

		HttpGet httppost = new HttpGet(url);

		HttpResponse response;
		response = httpclient.execute(httppost);
		String temp = EntityUtils.toString(response.getEntity());
		return temp;

	}

	public static HttpResponse doPostFile(String url, MultipartEntity kvPairs)
			throws ClientProtocolException, IOException {
		// HttpClient httpclient = new DefaultHttpClient();

		HttpClient httpclient = getNewHttpClient();

		HttpPost httppost = new HttpPost(url);
		// setProxy(defaultHttpClient);

		httppost.setEntity(kvPairs);
		HttpResponse response;
		response = httpclient.execute(httppost);
		return response;
	}
	
	public String readJsonFromUrl(String url) throws Exception {

		String result = "";
		HttpPost httpPost = null;
		DefaultHttpClient httpClient = null;
		while (httpPost == null || httpClient == null) {
			httpClient = httpClient();
			httpPost = new HttpPost(url);
		}
		HttpResponse httpResponseGet = httpClient.execute(httpPost);

		HttpEntity resEntityGet = httpResponseGet.getEntity();

		result = EntityUtils.toString(resEntityGet);
		Log.d("lay json ve ne", result);
		if (result.contains("\n")) {
			result = result.replace("\n", "");
		}
		return result;
	}
	
	public static DefaultHttpClient httpClient() throws Exception {

		try {

			SSLContext ctx = SSLContext.getInstance("TLS");
			
			ctx.init(null, new TrustManager[] { new CustomX509TrustManager() }, new SecureRandom());

			SSLSocketFactory sf = new CustomSSLSocketFactory(ctx);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
		// return result;

	}

	public static class CustomSSLSocketFactory extends SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public CustomSSLSocketFactory(KeyStore truststore)
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new CustomX509TrustManager();

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		public CustomSSLSocketFactory(SSLContext context)
				throws KeyManagementException, NoSuchAlgorithmException,
				KeyStoreException, UnrecoverableKeyException {
			super(null);
			sslContext = context;
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host,
					port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}

	
	public static class CustomX509TrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		@Override
		public void checkServerTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
				throws CertificateException {

		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}


	}

	/**
	 * Sets the proxy.
	 * 
	 * @param httpclient
	 *            the new proxy
	 */
	public static void setProxy(DefaultHttpClient httpclient) {
		final String PROXY_IP = "173.11.68.8";
		final int PROXY_PORT = 80;

		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope(PROXY_IP, PROXY_PORT),
				new UsernamePasswordCredentials("demo", "msa123"));

		HttpHost proxy = new HttpHost(PROXY_IP, PROXY_PORT);

		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
				proxy);

	}

	public String Login(Activity activity, String url, String username,
			String password) {
		String result = "";
		try {

			JSONObject json = new JSONObject();
			json.put("password", password);
			json.put("userName", username);
			
			HttpResponse re = Services.doPost(url, json.toString());
			String temp = EntityUtils.toString(re.getEntity());

			Log.i("Resulst:", temp);
			Log.i("json:", json.toString());
			result = temp;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public String addNewStadium(Activity activity, String url, StadiumDetailModel data) {
		String result = "";
		try {

			JSONObject json = new JSONObject();
			json.put("name", data.getName());
			json.put("address", data.getAddress());
			json.put("phone", data.getPhone());
			json.put("email", data.getEmail());
			json.put("contact", "");
			
			JSONObject jsonfield = new JSONObject();
			jsonfield.put("five_people", data.getField().getFive_people());
			jsonfield.put("seven_people", data.getField().getSeven_people());
			json.put("field_number", jsonfield);
			
			JSONObject jsonPrice = new JSONObject();
			jsonfield.put("morning", data.getPrice5().getPriceMorning());
			jsonfield.put("afternoon", data.getPrice5().getPriceAfternoon());
			jsonfield.put("evening", data.getPrice5().getPriceEvening());
			json.put("price_five", jsonPrice);
			
			jsonPrice = new JSONObject();
			jsonfield.put("morning", data.getPrice7().getPriceMorning());
			jsonfield.put("afternoon", data.getPrice7().getPriceAfternoon());
			jsonfield.put("evening", data.getPrice7().getPriceEvening());
			json.put("price_seven", jsonPrice);
			
			JSONObject jsonMap = new JSONObject();
			jsonMap.put("long", data.getMap().getLng());
			jsonMap.put("lat", data.getMap().getLat());
			json.put("map", jsonMap);
			
			JSONObject jsonDistrict = new JSONObject();
			jsonDistrict.put("id", data.getDistrict().getId());
			jsonDistrict.put("name", data.getDistrict().getName());
			json.put("district", jsonDistrict);
			
			json.put("description", data.getDescription());
			json.put("ownerId", data.getOwnerId());

			HttpResponse re = Services.doPost(url, json.toString());
			String temp = EntityUtils.toString(re.getEntity());
			
			Log.i("Resulst:", temp);
			Log.i("json:", json.toString());
			result = temp;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}


}
