package com.tk.code.fake_umbrella.Model;

public class Customer {
    String customerName;
    String contactPerson;
    String telephone;
    String location;
    String numberOfEmployees;
    String weather;

    Customer() {}

    public Customer(String customerName, String contactPerson, String telephone, String location, String numberOfEmployees) {
        this.customerName = customerName;
        this.contactPerson = contactPerson;
        this.telephone = telephone;
        this.location = location;
        this.numberOfEmployees = numberOfEmployees;
    }

    public Customer(String customerName, String contactPerson, String telephone, String location, String numberOfEmployees, String weather) {
        this.customerName = customerName;
        this.contactPerson = contactPerson;
        this.telephone = telephone;
        this.location = location;
        this.numberOfEmployees = numberOfEmployees;
        this.weather = weather;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getLocation() {
        return location;
    }

    public String getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public String getWeather() {
        return weather;
    }
}
