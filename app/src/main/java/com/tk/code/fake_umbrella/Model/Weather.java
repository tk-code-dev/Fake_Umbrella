package com.tk.code.fake_umbrella.Model;

import java.util.List;

public class Weather {
    public static List<String> dates;
    public static List<String> weatherIcons;

    public Weather(List<String> dates, List<String> weatherIcons) {
        this.dates = dates;
        this.weatherIcons = weatherIcons;
    }
}
