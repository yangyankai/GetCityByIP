/*
 * Copyright (c) 2015-2015 by Shanghai shootbox Information Technology Co., Ltd.
 * Create: 2015/10/8 1:41:22
 * Project: GetCityByIP
 * File: MainActivity.java
 * Class: MainActivity
 * Module: app
 * Author: yangyankai
 * Version: 1.0
 */

package com.ykai.getcitybyip;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {

	private Handler handler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final TextView textView = (TextView) findViewById(R.id.TV);

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg)
			{
				//				super.handleMessage(msg);
				textView.setText("" + msg.obj.toString());
			}
		};
		String json = "";
		final String[] mIP = new String[1];
		new Thread(new Runnable() {
			@Override
			public void run()
			{
				mIP[0] = GetIPUtil.GetNetIp();
				AsyncHttpClientPost(mIP[0]);

			}
		}).start();


	}


	public static String UnicodeToString(String str)
	{
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char    ch;
		while (matcher.find())
		{
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}


	private void AsyncHttpClientPost(String mIP)
	{
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

		mIP = GetIPUtil.GetNetIp();
		asyncHttpClient.addHeader("apikey", "0d5a78a4601098826d3644197adb5f77");
		asyncHttpClient.post("http://apis.baidu.com/apistore/iplookupservice/iplookup?ip=" + mIP, new ResponseHandlerInterface() {
			@Override
			public void sendResponseMessage(HttpResponse httpResponse) throws IOException
			{
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpResponse.getStatusLine().getStatusCode() == 200)
				{
					InputStream inputStream = httpEntity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

					StringBuilder sb = new StringBuilder();


					String line = null;

					try
					{

						while ((line = reader.readLine()) != null)
						{

							sb.append(line + "");

						}

					} catch (IOException e)
					{

						e.printStackTrace();

					} finally
					{

						try
						{

							inputStream.close();

						} catch (IOException e)
						{

							e.printStackTrace();

						}
					}

					String Json = UnicodeToString(sb.toString());

					Json.toString();
					Gson gson = new Gson();
					CityJsonBean cityJsonBean = gson.fromJson(Json, CityJsonBean.class);
					cityJsonBean.toString();
					//							Log.e("aaaNN", "" + UnicodeToString(sb.toString()));
					String mCity = cityJsonBean.retData.city;

					/**      */
					try
					{
						HttpClient httpClient = new DefaultHttpClient();
						HttpGet httpGet = new HttpGet("http://api.lib360.net/open/weather.json?city=" + mCity);
						HttpResponse mHttpResponse = httpClient.execute(httpGet);
						if (httpResponse.getStatusLine().getStatusCode() == 200)
						{       // 请求和响应都成功了
							HttpEntity entity = mHttpResponse.getEntity();
							String response = EntityUtils.toString(entity, "utf-8");


							List<WeatherModel> modelList = WeatherJsonUtil.parseJson(response);


							modelList.toString();

							Message message = new Message();

							message.obj = modelList.toString();
							handler.sendMessage(message);
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}
					/**  */

				}


				//				Toast.makeText(MainActivity.this,"ok",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void sendStartMessage()
			{

			}

			@Override
			public void sendFinishMessage()
			{

			}

			@Override
			public void sendProgressMessage(int i, int i1)
			{

			}

			@Override
			public void sendCancelMessage()
			{

			}

			@Override
			public void sendSuccessMessage(int i, Header[] headers, byte[] bytes)
			{

			}

			@Override
			public void sendFailureMessage(int i, Header[] headers, byte[] bytes, Throwable throwable)
			{

			}

			@Override
			public void sendRetryMessage(int i)
			{

			}

			@Override
			public URI getRequestURI()
			{
				return null;
			}

			@Override
			public Header[] getRequestHeaders()
			{
				return new Header[0];
			}

			@Override
			public void setRequestURI(URI uri)
			{

			}

			@Override
			public void setRequestHeaders(Header[] headers)
			{

			}

			@Override
			public void setUseSynchronousMode(boolean b)
			{

			}

			@Override
			public boolean getUseSynchronousMode()
			{
				return false;
			}

			@Override
			public void onPreProcessResponse(ResponseHandlerInterface responseHandlerInterface, HttpResponse httpResponse)
			{

			}

			@Override
			public void onPostProcessResponse(ResponseHandlerInterface responseHandlerInterface, HttpResponse httpResponse)
			{

			}
		});
	}

	private void AsyncHttpClientGet1()
	{
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.get("http://www.baidu.com", new ResponseHandlerInterface() {
			@Override
			public void sendResponseMessage(HttpResponse httpResponse) throws IOException
			{
				Log.e("aaa", "" + httpResponse.toString());
			}

			@Override
			public void sendStartMessage()
			{

			}

			@Override
			public void sendFinishMessage()
			{

			}

			@Override
			public void sendProgressMessage(int i, int i1)
			{

			}

			@Override
			public void sendCancelMessage()
			{

			}

			@Override
			public void sendSuccessMessage(int i, Header[] headers, byte[] bytes)
			{

			}

			@Override
			public void sendFailureMessage(int i, Header[] headers, byte[] bytes, Throwable throwable)
			{

			}

			@Override
			public void sendRetryMessage(int i)
			{

			}

			@Override
			public URI getRequestURI()
			{
				return null;
			}

			@Override
			public Header[] getRequestHeaders()
			{
				return new Header[0];
			}

			@Override
			public void setRequestURI(URI uri)
			{

			}

			@Override
			public void setRequestHeaders(Header[] headers)
			{

			}

			@Override
			public void setUseSynchronousMode(boolean b)
			{

			}

			@Override
			public boolean getUseSynchronousMode()
			{
				return false;
			}

			@Override
			public void onPreProcessResponse(ResponseHandlerInterface responseHandlerInterface, HttpResponse httpResponse)
			{

			}

			@Override
			public void onPostProcessResponse(ResponseHandlerInterface responseHandlerInterface, HttpResponse httpResponse)
			{

			}
		});
	}

	private void AsyncHttpClientGet()
	{

		final AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://www.baidu.com", new AsyncHttpResponseHandler() {

			@Override
			public void onStart()
			{
				// called before request is started
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] response)
			{
				// called when response HTTP status is "200 OK"
				Log.e("aaa", "bk 200 \n" + response.toString());
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e)
			{
				// called when response HTTP status is "4XX" (eg. 401, 403, 404)
				Log.e("aaa", "bk error " + statusCode);
			}

			@Override
			public void onRetry(int retryNo)
			{
				// called when request is retried
			}
		});


	}

	private void SendHttpClientPost()
	{
		new Thread(new Runnable() {
			@Override
			public void run()
			{

				String httpUrl = "http://www.baidu.com";
				// HttpPost连接对象
				HttpPost httpRequest = new HttpPost(httpUrl);
				// 使用NameValuePair来保存要传递的Post参数
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// 添加要传递的参数

				NameValuePair pair1 = new BasicNameValuePair("par", "HttpClient_android_Post");

				params.add(pair1);
				//params.add(new BasicNameValuePair("par", "HttpClient_android_Post"));
				try

				{
					// 设置字符集
					HttpEntity httpentity = new UrlEncodedFormEntity(params, "gb2312");
					//
					// 请求httpRequest
					httpRequest.setEntity(httpentity);
					// 取得默认的HttpClient
					HttpClient httpclient = new DefaultHttpClient();
					// 取得HttpResponse
					HttpResponse httpResponse = httpclient.execute(httpRequest);
					// HttpStatus.SC_OK表示连接成功
					if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)

					{
						// 取得返回的字符串
						String strResult = EntityUtils.toString(httpResponse.getEntity());

						Log.e("aaa", "" + strResult);

						//						mTextView.setText(strResult);
					}

					else

					{
						//						mTextView.setText("请求错误!");
					}
				} catch (ClientProtocolException e)

				{
					//					mTextView.setText(e.getMessage().toString());
				} catch (IOException e)

				{
					//					mTextView.setText(e.getMessage().toString());
				} catch (Exception e)

				{
					//					mTextView.setText(e.getMessage().toString());
				}


			}
		}).start();

	}

	private void SendHttpClientGet()
	{
		new Thread(new Runnable() {
			@Override
			public void run()
			{
				//先将参数放入List，再对参数进行URL编码
				List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("param1", "中国"));
				params.add(new BasicNameValuePair("param2", "value2"));

				//对参数编码
				String param = URLEncodedUtils.format(params, "UTF-8");

				//baseUrl
				String baseUrl = "http://ubs.free4lab.com/php/method.php";

				//将URL与参数拼接
				HttpGet getMethod = new HttpGet("http://www.baidu.com");//baseUrl + "?" + param);

				HttpClient httpClient = new DefaultHttpClient();

				try
				{
					HttpResponse response = httpClient.execute(getMethod); //发起GET请求

					Log.e("aaa", "resCode = " + response.getStatusLine().getStatusCode()); //获取响应码
					Log.e("aaa", "result = " + EntityUtils.toString(response.getEntity(), "utf-8"));
					//获取服务器响应内容
				} catch (ClientProtocolException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.i("aaa", "Error Code");
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();


	}
}
