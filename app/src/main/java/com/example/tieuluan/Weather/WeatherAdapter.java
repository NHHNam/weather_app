package com.example.tieuluan.Weather;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tieuluan.R;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherHolder> {

    private final List<Weather> weatherList;
    private final Context context;

    public WeatherAdapter(Context context, List<Weather> weatherList) {
        this.context = context;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_row, parent, false);

        return new WeatherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHolder holder, int position) {
        Weather weather = weatherList.get(position);

        holder.bindUI(weather);

    }

    @Override
    public int getItemCount() {
        if (weatherList != null) return weatherList.size();
        return 0;
    }

    class WeatherHolder extends RecyclerView.ViewHolder {
        private final ImageView imgDateIcon;
        private final TextView tvDate;
        private final TextView tvDateTempMin;
        private final TextView tvDateTempMax;

        public WeatherHolder(@NonNull View itemView) {
            super(itemView);

            imgDateIcon = itemView.findViewById(R.id.img_date_icon);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvDateTempMin = itemView.findViewById(R.id.tv_date_temp_min);
            tvDateTempMax = itemView.findViewById(R.id.tv_date_temp_max);
        }

        public void bindUI(Weather weather) {
            Glide.with(context)
                    .load("http://openweathermap.org/img/wn/" +
                            weather.getIcon() + ".png")
                    .into(imgDateIcon);

            tvDate.setText(weather.getDate());
            tvDateTempMin.setText(weather.getMin());
            tvDateTempMax.setText(weather.getMax());
        }
    }
}
