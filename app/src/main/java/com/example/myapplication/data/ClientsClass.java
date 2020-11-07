package com.example.myapplication.data;

import java.io.Serializable;

public class ClientsClass implements Serializable {

    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private String district;
    private double longtitude;
    private double latitude;

    public ClientsClass() {

    }

    public ClientsClass(String id1, String name1, String address1, String phoneNumber1, String district1, double latitude1, double latitude2) {
        id = id1;
        name = name1;
        address = address1;
        phoneNumber = phoneNumber1;
        district = district1;
        latitude = latitude1;
        longtitude = latitude2;
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

    public double getLongtitude() {
        return longtitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
