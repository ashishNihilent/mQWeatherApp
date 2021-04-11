package com.kadamab.weather.Retrofit

import Main
import WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
/*

Created by KADAMAB on 30 March 2021

*/
interface InterWeather {
    @GET("weather")
    fun getWeather(@Query("q") woeid: String, @Query("appid") key: String): Call<WeatherModel>
}

