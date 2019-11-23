package com.tk.code.fake_umbrella.View;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchWeather extends AppCompatActivity {

    public static String BaseUrl = "http://api.openweathermap.org/";
    public static String AppId = "33a82b8c0da7233c25d00cf6f830cb9a";
    public static String reformatDate1, reformatDate2, reformatDate3, reformatDate4, reformatDate5;

    public static void getCurrentData(String city) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);
        Call<WeatherResponse5days> call = service.getCurrentWeatherData(city, "", AppId);
        call.enqueue(new Callback<WeatherResponse5days>() {

            @Override
            public void onResponse(@NonNull Call<WeatherResponse5days> call, @NonNull Response<WeatherResponse5days> response) {
                if (response.code() == 200) {
                    FetchWeather fetchWeather = new FetchWeather();
                    WeatherResponse5days weatherResponse5days = response.body();
                    assert weatherResponse5days != null;

                    String strCity = weatherResponse5days.city.name;
                    String strWeather1 = weatherResponse5days.list.get(7).weather.get(0).description;
                    String strWeather2 = weatherResponse5days.list.get(15).weather.get(0).description;
                    String strWeather3 = weatherResponse5days.list.get(23).weather.get(0).description;
                    String strWeather4 = weatherResponse5days.list.get(31).weather.get(0).description;
                    String strWeather5 = weatherResponse5days.list.get(39).weather.get(0).description;
                    String strIcon1 = weatherResponse5days.list.get(7).weather.get(0).icon;
                    String strIcon2 = weatherResponse5days.list.get(15).weather.get(0).icon;
                    String strIcon3 = weatherResponse5days.list.get(23).weather.get(0).icon;
                    String strIcon4 = weatherResponse5days.list.get(31).weather.get(0).icon;
                    String strIcon5 = weatherResponse5days.list.get(39).weather.get(0).icon;
                    String date1 = weatherResponse5days.list.get(7).dt_txt;
                    String date2 = weatherResponse5days.list.get(15).dt_txt;
                    String date3 = weatherResponse5days.list.get(23).dt_txt;
                    String date4 = weatherResponse5days.list.get(31).dt_txt;
                    String date5 = weatherResponse5days.list.get(39).dt_txt;
                    SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                    SimpleDateFormat myFormat = new SimpleDateFormat("dd");
                    try {
                        reformatDate1 = myFormat.format(fromUser.parse(date1));
                        reformatDate2 = myFormat.format(fromUser.parse(date2));
                        reformatDate3 = myFormat.format(fromUser.parse(date3));
                        reformatDate4 = myFormat.format(fromUser.parse(date4));
                        reformatDate5 = myFormat.format(fromUser.parse(date5));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String[] responseData = {strCity + "@" + strWeather1 + ">" + strWeather2 + ">" + strWeather3 + ">" + strWeather4 + ">" + strWeather5 + "@" +
                            strIcon1 + ">" + strIcon2 + ">" + strIcon3 + ">" + strIcon4 + ">" + strIcon5 + ">" + "@" +
                            reformatDate1 + ">" + reformatDate2 + ">" + reformatDate3 + ">" + reformatDate4 + ">" + reformatDate5};
                    String joinedStringData = TextUtils.join(" ", responseData);
                    fetchWeather.saveResponse(strCity, joinedStringData);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse5days> call, @NonNull Throwable t) {
                Log.d("Error", "Error !!");
            }
        });
    }

    void saveResponse(String city, String res) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("weather." + city.toLowerCase(), Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(res);
            out.close();
            fileOutputStream.close();
            Log.d("saved","saved"+res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

