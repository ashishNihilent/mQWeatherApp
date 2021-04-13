package com.kadamab.weather.ViewModel

import WeatherModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kadamab.weather.Repository.WeatherDataRepository

/**

 *Created by KADAMAB on 31 March 2021

 */
class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val weatherDataRepository: WeatherDataRepository = WeatherDataRepository.instance!!

    fun requestWeatherData(woeid: String) {
        weatherDataRepository.requestWeatherData(woeid)
    }

    fun observeWeatherData(): MutableLiveData<WeatherModel> {
        return weatherDataRepository.observeWeatherData()
    }

}
