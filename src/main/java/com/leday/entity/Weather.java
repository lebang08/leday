package com.leday.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/2.
 */
public class Weather implements Serializable{

    private String date;
    private String night;
    private String dayTime;
    private String temperature;
    private String week;
    private String wind;

    public Weather(String date, String night, String dayTime, String temperature, String week, String wind) {
        this.date = date;
        this.night = night;
        this.dayTime = dayTime;
        this.temperature = temperature;
        this.week = week;
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date='" + date + '\'' +
                ", night='" + night + '\'' +
                ", dayTime='" + dayTime + '\'' +
                ", temperature='" + temperature + '\'' +
                ", week='" + week + '\'' +
                ", wind='" + wind + '\'' +
                '}';
    }

    public Weather() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
}