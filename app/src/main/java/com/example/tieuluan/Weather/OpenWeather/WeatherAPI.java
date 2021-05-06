package com.example.tieuluan.Weather.OpenWeather;

import com.example.tieuluan.Weather.OpenWeather.BaseJSON;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {
    @GET("/data/2.5/onecall?")
    Call<BaseJSON> getWeather (
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("exclude") String exclude,
            @Query("units") String units,
            @Query("appid") String appid
    );
}
