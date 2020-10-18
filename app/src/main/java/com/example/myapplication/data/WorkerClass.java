package com.example.myapplication.data;

public class WorkerClass {

    private String email;
    private String id;
    private String name;
    private String age;
    private String district;
    private String phoneNumber;

    public WorkerClass(){
        
    }

    public WorkerClass(String email1,String id1, String name1, String age1, String district1, String phoneNumber1){
        email = email1;
        id = id1;
        name = name1;
        age = age1;
        district = district1;
        phoneNumber = phoneNumber1;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAge() {
        return age;
    }

    public String getDistrict() {
        return district;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
