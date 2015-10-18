/*
 * Copyright (c) 2015-2015 by Shanghai shootbox Information Technology Co., Ltd.
 * Create: 2015/10/8 5:51:45
 * Project: GetCityByIP
 * File: GetIPUtil.java
 * Class: GetIPUtil
 * Module: app
 * Author: yangyankai
 * Version: 1.0
 */

/*
 * Copyright (c) 2015-2015 by Shanghai shootbox Information Technology Co., Ltd.
 * Create: 2015/10/8 11:49:47
 * Project: GetIP
 * File: GetIPUtil.java
 * Class: GetIPUtil
 * Module: app
 * Author: yangyankai
 * Version: 1.0
 */

package com.ykai.getcitybyip;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangyankai on 2015/10/8.
 */
public class GetIPUtil {
//	static String getWiFiIP(Context context){
//		//获取wifi服务
//		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//		//判断wifi是否开启
//		if (!wifiManager.isWifiEnabled()) {
//			wifiManager.setWifiEnabled(true);
//		}
//		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//		int ipAddress = wifiInfo.getIpAddress();
//		String ip = intToIp(ipAddress);
//		return ip;
//	}
//
//	private static String intToIp(int i) {
//
//		return (i & 0xFF ) + "." +
//				((i >> 8 ) & 0xFF) + "." +
//				((i >> 16 ) & 0xFF) + "." +
//				( i >> 24 & 0xFF) ;
//	}
//
//
// static String getLocalIpAddress(Context context) {
//		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//		// 获取32位整型IP地址
//		int ipAddress = wifiInfo.getIpAddress();
//
//		//返回整型地址转换成“*.*.*.*”地址
//		return String.format("%d.%d.%d.%d",
//							 (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
//							 (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
//	}



//用这个
	//  获取 GPRS & WiFi   IP 地址
	public static String getIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& inetAddress instanceof Inet4Address) {
						// if (!inetAddress.isLoopbackAddress() && inetAddress
						// instanceof Inet6Address) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String GetNetIp() {
		URL               infoUrl = null;
		InputStream       inStream = null;
		String ipLine = "";
		HttpURLConnection httpConnection = null;
		try {
			infoUrl = new URL("http://city.ip138.com/ip2city.asp");
			URLConnection connection = infoUrl.openConnection();
			httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				inStream = httpConnection.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inStream, "utf-8"));
				StringBuilder strber = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null)
					strber.append(line + "\n");

				Pattern pattern = Pattern
						.compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
				Matcher matcher = pattern.matcher(strber.toString());
				if (matcher.find()) {
					ipLine = matcher.group();
				}
				else {
					Log.e("aaabc", "not find");
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inStream.close();
				httpConnection.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ipLine;
	}

	//
//
//	public  static String getLocalIpAddress() {
//		String ipAddress = null;
//		try {
//			List<NetworkInterface> interfaces = Collections
//					.list(NetworkInterface.getNetworkInterfaces());
//			for (NetworkInterface iface : interfaces) {
//				if (iface.getDisplayName().equals("eth0")) {
//					List<InetAddress> addresses = Collections.list(iface.getInetAddresses());
//					for (InetAddress address : addresses) {
//						if (address instanceof Inet4Address) {
//							ipAddress = address.getHostAddress();
//						}
//					}
//				} else if (iface.getDisplayName().equals("wlan0")) {
//					List<InetAddress> addresses = Collections.list(iface
//																		   .getInetAddresses());
//					for (InetAddress address : addresses) {
//						if (address instanceof Inet4Address) {
//							ipAddress = address.getHostAddress();
//						}
//					}
//				}
//			}
//		} catch (SocketException e) {
//			e.printStackTrace();
//		}
//		return ipAddress;
//	}



}
