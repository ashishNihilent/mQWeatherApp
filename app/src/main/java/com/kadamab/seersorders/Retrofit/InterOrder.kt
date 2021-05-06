package com.kadamab.seersorders.Retrofit

import Orders
import com.kadamab.seersorders.Common.RequestParam
import retrofit2.Call
import retrofit2.http.GET

/*

*Created by KADAMAB on 6 May 2021

*/
interface InterOrder {
    @GET(RequestParam.RequestMethod.GET_WEATHER)
    fun getOrder(): Call<Orders>
}

