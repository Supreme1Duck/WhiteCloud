package com.example.myapplication.geocoding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mapbox.geojson.Feature;

import java.util.List;

public class Example {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("query")
    @Expose
    private List<String> query = null;
    @SerializedName("features")
    @Expose
    private List<Feature> features = null;
    @SerializedName("attribution")
    @Expose
    private String attribution;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getQuery() {
        return query;
    }

    public void setQuery(List<String> query) {
        this.query = query;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

}
