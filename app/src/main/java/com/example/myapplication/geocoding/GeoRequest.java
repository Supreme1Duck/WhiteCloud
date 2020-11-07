package com.example.myapplication.geocoding;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mapbox.geojson.Geometry;

import java.util.List;
import java.util.Properties;

public class GeoRequest {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("place_type")
    @Expose
    private List<String> placeType = null;
    @SerializedName("relevance")
    @Expose
    private Integer relevance;
    @SerializedName("properties")
    @Expose
    private Properties properties;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("place_name")
    @Expose
    private String placeName;
    @SerializedName("center")
    @Expose
    private List<Double> center = null;
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("context")
    @Expose
    private List<Context> context = null;
    @SerializedName("matching_place_name")
    @Expose
    private String matchingPlaceName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getPlaceType() {
        return placeType;
    }

    public void setPlaceType(List<String> placeType) {
        this.placeType = placeType;
    }

    public Integer getRelevance() {
        return relevance;
    }

    public void setRelevance(Integer relevance) {
        this.relevance = relevance;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public List<Double> getCenter() {
        return center;
    }

    public void setCenter(List<Double> center) {
        this.center = center;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Context> getContext() {
        return context;
    }

    public void setContext(List<Context> context) {
        this.context = context;
    }

    public String getMatchingPlaceName() {
        return matchingPlaceName;
    }

    public void setMatchingPlaceName(String matchingPlaceName) {
        this.matchingPlaceName = matchingPlaceName;
    }
}
