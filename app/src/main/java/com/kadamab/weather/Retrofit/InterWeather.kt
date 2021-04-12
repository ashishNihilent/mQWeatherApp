package com.kadamab.weather.Retrofit

import WeatherModel
import com.kadamab.weather.Common.RequestParam
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/*

Created by KADAMAB on 30 March 2021

*/
interface InterWeather {
    @GET(RequestParam.RequestMethod.GET_WEATHER)
    fun getWeather(
        @Query(RequestParam.CITY) woeid: String,
        @Query(RequestParam.APIKEY) key: String
    ): Call<WeatherModel>
}

