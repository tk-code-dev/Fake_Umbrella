package com.tk.code.fake_umbrella.Model;

public class CustomerListWeather {
    public  Customer customer;
    public String[] description;
    public String[] date;
    public String[] icon;

    public CustomerListWeather(Customer customer, String[] description, String[] date, String[] icon) {
        this.customer = customer;
        this.description = description;
        this.date = date;
        this.icon = icon;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

