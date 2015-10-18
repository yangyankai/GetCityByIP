/*
 * Copyright (c) 2015-2015 by Shanghai shootbox Information Technology Co., Ltd.
 * Create: 2015/10/8 6:48:52
 * Project: GetCityByIP
 * File: WeatherJsonUtil.java
 * Class: WeatherJsonUtil
 * Module: app
 * Author: yangyankai
 * Version: 1.0
 */

package com.ykai.getcitybyip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyankai on 2015/8/31.
 */
public class WeatherJsonUtil {

    public static List<WeatherModel> parseJson(String strResult) {


        List<WeatherModel> modelList=new ArrayList<>();
        JSONArray jsonArray=null;

        try {
            JSONObject jsonObject = new JSONObject(strResult);
            jsonArray= jsonObject.getJSONArray("data");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        modelList=parseJsonBase(jsonArray);
        return modelList;
    }


    /**
     * @param infoUserBase
     * @return 单个 weather。   此处用户信息是单个的，只要返回单个 json 解析结果即可
     */
    public static List<WeatherModel> parseJsonBase(JSONArray infoUserBase) {

        List<WeatherModel> modelList=new ArrayList<WeatherModel>();

        for (int i = 0; i < infoUserBase.length(); i++) {
            try {
                WeatherModel weather = new WeatherModel();
                JSONObject jsonObject = (JSONObject) infoUserBase.get(i);

                weather.dWeather = jsonObject.getString("Weather");
                weather.dTemperature = jsonObject.getString("TempMin") +"° ～ "+jsonObject.getString("TempMax") +"°"  ;
                weather.dWindDirection = jsonObject.getString("WindA");
                weather.dWindLevel = jsonObject.getString("Wind");
                weather.date = jsonObject.getString("Year")+"/"+jsonObject.getString("Month")+"/"+jsonObject.getString("Day");

                modelList.add(weather);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return modelList;
    }



}
