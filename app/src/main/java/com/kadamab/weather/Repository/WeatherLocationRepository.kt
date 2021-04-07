package com.kadamab.weather.Repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kadamab.weather.Retrofit.MetaWeather
import com.kadamab.weather.Retrofit.ServiceBuilder
import com.kadamab.weather.Serializeable.WeatherLocation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/**

*Created by KADAMAB on 31 March 2021

*/
class WeatherLocationRepository : ViewModel() {

    private val weatherLocation: MutableLiveData<List<WeatherLocation>?> = MutableLiveData()

    fun observeWeatherLocation(): MutableLiveData<List<WeatherLocation>?> {
        return weatherLocation
    }

    fun requestWeatherLocation(text: String): MutableLiveData<List<WeatherLocation>?> {

        val weatherLocationCall: Call<List<WeatherLocation>> = myInterface.getLocation(text)
        weatherLocationCall.enqueue(object : Callback<List<WeatherLocation>?> {
            override fun onResponse(call: Call<List<WeatherLocation>?>, response: Response<List<WeatherLocation>?>) {
                weatherLocation.setValue(response.body())
            }

            override fun onFailure(call: Call<List<WeatherLocation>?>, t: Throwable) {
                weatherLocation.postValue(null)
            }
        })

        return weatherLocation
    }

    companion object {
        private lateinit var myInterface: MetaWeather
        private var weatherLocationRepository: WeatherLocationRepository? = null
        val instance: WeatherLocationRepository?
            get() {
                if (weatherLocationRepository == null) {
                    weatherLocationRepository = WeatherLocationRepository()
                }
                return weatherLocationRepository
            }
    }

    init {
        myInterface = ServiceBuilder.buildService(MetaWeather::class.java)
    }
}
