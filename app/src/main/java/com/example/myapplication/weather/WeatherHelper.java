package com.example.myapplication.weather;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherHelper extends AsyncTask<String, Void, String> {

    private HttpURLConnection connection;
    @Override
    protected String doInBackground(String... address) {
        try {
            URL url = new URL(address[0]);
            connection = (HttpURLConnection)url.openConnection();
            connection.connect();

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int data = isr.read();
            String content = "";
            char ch;
            while (data != 0){
                ch = (char) data;
                content = content + ch;
                data = isr.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }
        return null;
    }
}
