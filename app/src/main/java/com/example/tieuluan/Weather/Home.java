package com.example.tieuluan.Weather;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tieuluan.Google.SignIn;
import com.example.tieuluan.R;
import com.example.tieuluan.Google.UserInformation;
import com.example.tieuluan.Weather.OpenWeather.BaseJSON;
import com.example.tieuluan.Weather.OpenWeather.RestAPI;
import com.example.tieuluan.Weather.OpenWeather.WeatherAPI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {
    public static final String LAT = "10.75";
    public static final String LON = "106.666672";
    public static final String EXCLUDE = "minutely,hourly";
    public static final String UNITS = "metric";
    public static final String API_KEY = "ad5f1e17bfd71f504fb1b082fb7d3f56";

    private GoogleSignInClient mGoogleSignInClient;

    private WeatherAPI api;

    private TextView tvTemp;
    private TextView tvCity;
    private TextView tvMain;
    private TextView tvWind;
    private TextView tvHumidity;
    private ImageView imgIcon;
    private TextView tvFeelsLike;
    private TextView tvCurrentDate;

    private RecyclerView recyclerView;
    private WeatherAdapter adapter;
    private List<Weather> weatherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_home);

        setBottomNavigation();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // create API call
        api = RestAPI.weatherAPI();

        bindUIControls();

        setRecyclerView();

        getWeatherData();
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.rv_weather);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
    }

    private void bindUIControls() {
        tvCity = findViewById(R.id.tv_city);

        tvTemp = findViewById(R.id.tv_temperature);
        tvMain = findViewById(R.id.tv_main);
        imgIcon = findViewById(R.id.img_icon);

        tvCurrentDate = findViewById(R.id.tv_current_date);

        tvWind = findViewById(R.id.tv_wind);
        tvFeelsLike = findViewById(R.id.tv_feels_like);
        tvHumidity = findViewById(R.id.tv_humidity);
    }

    public void getWeatherData() {
        api.getWeather(LAT, LON, EXCLUDE, UNITS, API_KEY).enqueue(new Callback<BaseJSON>() {
            @Override
            public void onResponse(Call<BaseJSON> call, Response<BaseJSON> response) {
                BaseJSON data = response.body();
                if (data != null) {
                    bindDataToUI(data);
                } else {
                    Toast.makeText(Home.this, "Cannot receive data from server", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "Cannot receive data from server");
                }
            }

            @Override
            public void onFailure(Call<BaseJSON> call, Throwable t) {
                Toast.makeText(Home.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("TAG", t.getMessage());
            }
        });
        
    }

    private void bindDataToUI(BaseJSON data) {
        tvCity.setText(data.timeZone);

        tvTemp.setText((int) Math.round(data.current.temp) + "");
        tvMain.setText(data.current.weather.get(0).main);
        Glide.with(Home.this)
                .load("http://openweathermap.org/img/wn/" +
                        data.current.weather.get(0).icon + ".png")
                .into(imgIcon);

        tvWind.setText(data.current.windSpeed + "m/s");
        tvFeelsLike.setText(data.current.feelsLike + "\u2103");
        tvHumidity.setText(data.current.humidity + "%");

        // current day
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        Calendar calendar = Calendar.getInstance();

        tvCurrentDate.setText(simpleDateFormat.format(calendar.getTime()));

        // next 7 days weather
        weatherList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date date = calendar.getTime();

            Weather weather = new Weather(
                    data.daily.get(i).weather.get(0).icon,
                    simpleDateFormat.format(date),
                    String.valueOf(data.daily.get(i).temp.day),
                    String.valueOf(data.daily.get(i).feelsLike.day));

            weatherList.add(weather);
        }

        adapter = new WeatherAdapter(Home.this, weatherList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.weather_nav);

        bottomNavigationView.setSelectedItemId(R.id.home_bottom_weather);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_bottom_weather:
                    return true;

                case R.id.home_bottom_info:
                    startActivity(new Intent(Home.this, UserInformation.class));
                    overridePendingTransition(0,0);
                    finish();
                    return true;

                case R.id.home_bottom_sign_out:
                    mGoogleSignInClient.signOut()
                            .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    startActivity(new Intent(Home.this, SignIn.class));
                                    overridePendingTransition(0,0);
                                    finish();
                                }
                            });

                    return true;
            }

            return false;
        });
    }
}