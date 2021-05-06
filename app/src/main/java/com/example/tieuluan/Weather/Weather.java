package com.example.tieuluan.Weather;

public class Weather {
    private String icon;
    private String date;
    private String temp;
    private String feelsLike;

    public Weather(String icon, String date, String min, String max) {
        this.icon = icon;
        this.date = date;
        this.temp = min;
        this.feelsLike = max;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMin() {
        return temp;
    }

    public void setMin(String min) {
        this.temp = min;
    }

    public String getMax() {
        return feelsLike;
    }

    public void setMax(String max) {
        this.feelsLike = max;
    }
}
