package com.kadamab.weather.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kadamab.weather.Repository.WeatherDataRepository
import com.kadamab.weather.Repository.WeatherLocationRepository
import com.kadamab.weather.Serializeable.WeatherLocation
import com.kadamab.weather.Serializeable.WeatherModel

/**

*Created by KADAMAB on 31 March 2021

*/
class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val weatherDataRepository: WeatherDataRepository = WeatherDataRepository.instance!!
    private val weatherLocationRepository: WeatherLocationRepository = WeatherLocationRepository.instance!!

    /**
     * Requesting Methods
     */

    fun requestWeatherData(woeid: Int) {
        weatherDataRepository.requestWeatherData(woeid)
    }

    fun requestWeatherLocation(text: String) {
        weatherLocationRepository.requestWeatherLocation(text)
    }

    /**
     * Get Observer
     */

    fun observeWeatherData(): MutableLiveData<WeatherModel?> {
        return weatherDataRepository.observeWeatherData()
    }

    fun observeWeatherLocation(): MutableLiveData<List<WeatherLocation>?> {
        return weatherLocationRepository.observeWeatherLocation()
    }

}
