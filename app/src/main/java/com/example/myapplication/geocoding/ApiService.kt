package com.example.myapplication.geocoding

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("geocoding/v5/mapbox.places/{address}.json?access_token=pk.eyJ1IjoiYW5kcmV3ZHVjayIsImEiOiJja2dveWhvYzAwNTF2Mnh0ZG0zeTY0ZDJrIn0.-WM8E3RIsTBzywfykcIXDw")
    fun Call(
        @Path("address") address: String
    ): Response<Context>
}