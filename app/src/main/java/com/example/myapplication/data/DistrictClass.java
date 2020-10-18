package com.example.myapplication.data;

public class DistrictClass {
    private String district;

    public DistrictClass(){

    }

    public DistrictClass(String district1){
        district = district1;
    }

    public String getDistrict() {
        return district;
    }

    @Override
    public String toString() {
        return getDistrict();
    }
}
