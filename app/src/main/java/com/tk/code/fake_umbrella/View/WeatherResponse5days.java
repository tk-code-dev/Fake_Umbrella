package com.tk.code.fake_umbrella.View;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherResponse5days {

    @SerializedName("cod")
    public String cod;
    @SerializedName("message")
    public Float message;
    @SerializedName("cnt")
    public int cnt;
    @SerializedName("list")
    public ArrayList<List5> list = new ArrayList<List5>();
    @SerializedName("city")
    public City city;
}
class City {
    @SerializedName("name")
    public String name;
}
class List5 {
    @SerializedName("dt")
    public int dt;
    @SerializedName("main")
    public Main5 main;
    @SerializedName("weather")
    public ArrayList<Weather5> weather = new ArrayList<Weather5>();
    @SerializedName("dt_txt")
    public String dt_txt;
}
class Weather5 {
    @SerializedName("id")
    public int id;
    @SerializedName("main")
    public String main;
    @SerializedName("description")
    public String description;
    @SerializedName("icon")
    public String icon;
}

class Main5 {
    @SerializedName("temp")
    public float temp;
    @SerializedName("humidity")
    public float humidity;
    @SerializedName("pressure")
    public float pressure;
    @SerializedName("temp_min")
    public float temp_min;
    @SerializedName("temp_max")
    public float temp_max;
}