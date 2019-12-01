package com.tk.code.fake_umbrella.Model;

import java.io.Serializable;
import java.util.Comparator;

public class Customer implements Serializable {
    public String customerName;
    public String contactPerson;
    public String telephone;
    public String location;
    public int numberOfEmployees;

    public Customer() {
        // Default constructor required for calls to DataSnapshot.getValue(Customer.class)
    }

    public Customer(String customerName, String contactPerson, String telephone, String location, int numberOfEmployees) {
        this.customerName = customerName;
        this.contactPerson = contactPerson;
        this.telephone = telephone;
        this.location = location;
        this.numberOfEmployees = numberOfEmployees;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }


}
