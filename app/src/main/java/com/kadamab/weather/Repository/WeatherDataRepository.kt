package com.kadamab.weather.Repository

import WeatherModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kadamab.weather.Retrofit.InterWeather
import com.kadamab.weather.Retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/**

*Created by KADAMAB on 30 March 2021

*/
class WeatherDataRepository : ViewModel() {

    private val weatherModel: MutableLiveData<WeatherModel?> = MutableLiveData()

    fun observeWeatherData(): MutableLiveData<WeatherModel?> {
        return weatherModel
    }

    fun requestWeatherData(woeid: String) {

        val weatherDataCall: Call<WeatherModel> = myInterface.getWeather("mumbai")
        weatherDataCall.enqueue(object : Callback<WeatherModel?> {
            override fun onResponse(call: Call<WeatherModel?>, response: Response<WeatherModel?>) {
                weatherModel.setValue(response.body())
            }

            override fun onFailure(call: Call<WeatherModel?>, t: Throwable) {
                weatherModel.postValue(null)
            }
        })

    }

    companion object {
        private lateinit var myInterface: InterWeather
        private var weatherDataRepository: WeatherDataRepository? = null
        val instance: WeatherDataRepository?
            get() {
                if (weatherDataRepository == null) {
                    weatherDataRepository = WeatherDataRepository()
                }
                return weatherDataRepository
            }
    }

    init {
        myInterface = ServiceBuilder.buildService(InterWeather::class.java)
    }
}
