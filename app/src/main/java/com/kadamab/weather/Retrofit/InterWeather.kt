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
   // @GET("location/{woeid}/")
    @GET("q={city}&appid=fae7190d7e6433ec3a45285ffcf55c86")

    fun getWeather(@Path("city") woeid: String): Call<WeatherModel>

}

