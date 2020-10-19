package com.example.myapplication.data;

import org.jetbrains.annotations.NotNull;

public class DistrictClass {
    private String district;

    public DistrictClass() {

    }

    public DistrictClass(String district1) {
        district = district1;
    }

    public String getDistrict() {
        return district;
    }

    @NotNull
    @Override
    public String toString() {
        return getDistrict();
    }
}
