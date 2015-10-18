/*
 * Copyright (c) 2015-2015 by Shanghai shootbox Information Technology Co., Ltd.
 * Create: 2015/10/8 6:49:7
 * Project: GetCityByIP
 * File: WeatherModel.java
 * Class: WeatherModel
 * Module: app
 * Author: yangyankai
 * Version: 1.0
 */

package com.ykai.getcitybyip;

/**
 * Created by yangyankai on 2015/8/28.
 */
public class WeatherModel {
    /**
     * 城市（区县）
     */
    public String city;
    /**
     * 日期
     */
    public String date;
    /**
     * 星期几
     */
    public String weekDay;

    /**
     * 白天天气状况
     */
    public String dWeather;         //天气
    public String dTemperature;     //温度
    public String dWindDirection;         //风向
    public String dWindLevel;       //风力

    public String toString(){
        return date+"\t"+dWeather+"\t"+dTemperature+"\t"+dWindDirection+"\t"+dWindLevel+"\n";
    }
}
