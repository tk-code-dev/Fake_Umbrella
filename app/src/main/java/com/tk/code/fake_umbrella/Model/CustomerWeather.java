package com.tk.code.fake_umbrella.Model;


public class CustomerWeather {
    public  Customer customer;
    public String locationWeather;

    public CustomerWeather(Customer customer, String locationWeather) {
        this.customer = customer;
        this.locationWeather = locationWeather;
    }
}
