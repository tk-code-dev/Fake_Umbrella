package com.tk.code.fake_umbrella.View;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("data/2.5/forecast?")
    Call<WeatherResponse5days> getCurrentWeatherData(@Query("q") String city, @Query("") String country, @Query("APPID") String app_id);
}

