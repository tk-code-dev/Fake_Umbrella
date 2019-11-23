package com.tk.code.fake_umbrella.Model;

public class Customer_Test {
    String customerName;
    String contactPerson;
    String telephone;
    String location;
    String numberOfEmployees;
    String weather;

    Customer_Test() {}

    public Customer_Test(String customerName, String contactPerson, String telephone, String location, String numberOfEmployees) {
        this.customerName = customerName;
        this.contactPerson = contactPerson;
        this.telephone = telephone;
        this.location = location;
        this.numberOfEmployees = numberOfEmployees;
    }

    public Customer_Test(String customerName, String contactPerson, String telephone, String location, String numberOfEmployees, String weather) {
        this.customerName = customerName;
        this.contactPerson = contactPerson;
        this.telephone = telephone;
        this.location = location;
        this.numberOfEmployees = numberOfEmployees;
        this.weather = weather;
    }
}
