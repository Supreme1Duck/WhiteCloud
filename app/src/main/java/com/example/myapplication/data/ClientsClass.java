package com.example.myapplication.data;

import java.io.Serializable;

public class ClientsClass implements Serializable {

    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private String district;

    public ClientsClass() {

    }

    public ClientsClass(String id1, String name1, String address1, String phoneNumber1, String district1){
        id = id1;
        name = name1;
        address = address1;
        phoneNumber = phoneNumber1;
        district = district1;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDistrict() {
        return district;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
