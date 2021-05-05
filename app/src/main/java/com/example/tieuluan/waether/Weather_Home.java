package com.example.tieuluan.waether;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tieuluan.R;
import com.example.tieuluan.logout.logout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Weather_Home extends AppCompatActivity {
    private EditText name_city;
    private TextView temp;
    private TextView humidty;
    private ImageView btn_search;
    String url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key} ";
    String apikey = "ef9a45f24c38098438115e983016f4d2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);

        name_city = findViewById(R.id.ed_name_city);
        temp = findViewById(R.id.tv_tempurature);
        humidty = findViewById(R.id.tv_humidty);
        btn_search = findViewById(R.id.btn_search_city);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getweather(v);
            }
        });

        setBottomNavigation();
    }

    public void getweather(View v){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherapi myapi = retrofit.create(weatherapi.class);
        Call<Example> exampleCall = myapi.getweather(name_city.getText().toString().trim(), apikey);
        exampleCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.code() == 404){
                    Toast.makeText(Weather_Home.this, "please enter valid city!", Toast.LENGTH_SHORT).show();
                }else if (!(response.isSuccessful())){
                    Toast.makeText(Weather_Home.this, response.code(),Toast.LENGTH_SHORT).show();
                }
                Example mydata = response.body();
                Main main = mydata.getMain();
                Double t = main.getTemp();
                Integer temperature = (int)(t-273.15);
                temp.setText("Nhiệt độ: " + String.valueOf(temperature) + "C");
                Integer humid = main.getHumidity();
                humidty.setText("Độ ẩm: " + String.valueOf(humid) + "ml");
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(Weather_Home.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.weather_nav);

        bottomNavigationView.setSelectedItemId(R.id.home_bottom_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_bottom_home:
                    return true;

                case R.id.home_bottom_info:
                    startActivity(new Intent(Weather_Home.this, logout.class));
                    overridePendingTransition(0,0);
                    finish();
                    return true;
            }

            return false;
        });
    }
}