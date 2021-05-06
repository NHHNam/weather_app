package com.example.tieuluan.Weather.OpenWeather;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAPI {
    public static final String URL = "http://api.openweathermap.org";

    private static Retrofit retrofit;

    public static WeatherAPI weatherAPI() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(WeatherAPI.class);
    }
}
