package com.tk.code.fake_umbrella.Model;

public class Customer {
    public String customerName;
    public String contactPerson;
    public String telephone;
    public String location;
    public Integer numberOfEmployees;

    public Customer(String customerName, String contactPerson, String telephone, String location, Integer numberOfEmployees) {
        this.customerName = customerName;
        this.contactPerson = contactPerson;
        this.telephone = telephone;
        this.location = location;
        this.numberOfEmployees = numberOfEmployees;
    }
}
