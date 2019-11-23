package com.tk.code.fake_umbrella.Model;

import java.io.Serializable;

public class CustomerChart implements Serializable {
    String companyName;
    Integer num;
    Boolean isRain;

    public CustomerChart(String companyName, Integer num, Boolean isRain) {
        this.companyName = companyName;
        this.num = num;
        this.isRain = isRain;
    }
}
