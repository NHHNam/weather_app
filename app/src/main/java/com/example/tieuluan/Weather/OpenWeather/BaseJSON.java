
package com.example.tieuluan.Weather.OpenWeather;

import android.widget.LinearLayout;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseJSON {

    @SerializedName("timezone")
    public String timeZone;

    @SerializedName("current")
    public Current current;

    public class Current {

        @SerializedName("temp")
        public Double temp;

        @SerializedName("feels_like")
        public Double feelsLike;

        @SerializedName("humidity")
        public Integer humidity;

        @SerializedName("wind_speed")
        public Double windSpeed;

        @SerializedName("weather")
        public List<Weather> weather;

        public class Weather {

            @SerializedName("main")
            public String main;

            @SerializedName("description")
            public String description;

            @SerializedName("icon")
            public String icon;
        }
    }

    @SerializedName("daily")
    public List<Daily> daily;

    public class Daily {

        @SerializedName(value = "temp", alternate = "daily_temp")
        public Temp temp;

        public class Temp {
            @SerializedName("day")
            public Double day;

            @SerializedName("min")
            public Double min;

            @SerializedName("max")
            public Double max;
        }

        @SerializedName(value = "feels_like", alternate = "daily_feels_like")
        public FeelsLike feelsLike;

        public class FeelsLike {
            @SerializedName(value = "day", alternate = "feels_like_day")
            public Double day;
        }

        @SerializedName(value = "humidity", alternate = "daily_humidity")
        public Integer humidity;

        @SerializedName(value = "wind_speed", alternate = "daily_wind_speed")
        public Double windSpeed;

        @SerializedName(value = "weather", alternate = "daily_weather")
        public List<Weather> weather;

        public class Weather {

            @SerializedName(value = "main", alternate = "daily_main")
            public String main;

            @SerializedName(value = "description", alternate = "daily_description")
            public String description;

            @SerializedName(value = "icon", alternate = "daily_icon")
            public String icon;
        }
    }
}
