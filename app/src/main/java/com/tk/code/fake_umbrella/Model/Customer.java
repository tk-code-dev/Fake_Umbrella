package com.tk.code.fake_umbrella.Model;

import java.util.Comparator;

public class Customer {
    public String customerName;
    public String contactPerson;
    public String telephone;
    public String location;
    public int numberOfEmployees;

    public Customer() {
    }

    public Customer(String customerName, String contactPerson, String telephone, String location, int numberOfEmployees) {
        this.customerName = customerName;
        this.contactPerson = contactPerson;
        this.telephone = telephone;
        this.location = location;
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }


}
