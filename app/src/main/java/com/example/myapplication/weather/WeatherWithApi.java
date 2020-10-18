package com.example.myapplication.weather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class WeatherWithApi {

    public void executeTheWeather(){
        String content;
        WeatherHelper weatherHelper = new WeatherHelper();
        try {
            content = weatherHelper.execute("http://api.openweathermap.org/data/2.5/weather?q=Minsk&appid=7110d8cc8802bbd242f674a682442a8a").get();

            JSONObject jsonObject = new JSONObject(content);
            String weather = jsonObject.getString("weather");
            JSONArray array = new JSONArray(weather);
            String main = "";
            String description = "";

            for (int i = 0; i< array.length(); i++){
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("main");
                description = weatherPart.getString("description");
            }
            Log.d("MY_TAG", main);
            Log.d("MY_TAG", description);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
