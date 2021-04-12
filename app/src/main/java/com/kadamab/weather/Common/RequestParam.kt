package com.kadamab.weather.Common

/**

 *Created by KADAMAB on 12 April 2021

 */
object RequestParam {
    const val CITY = "q"
    const val APIKEY = "appid"
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    object RequestMethod {
        const val GET_WEATHER = "weather"
    }

    object Default {
        const val CURRENT_CITY = "pune"
        const val APIKEY_VALUE = "fae7190d7e6433ec3a45285ffcf55c86"
    }

    object SharedPref {
        const val PREF_VAL_KEY = "_woeid"
    }
}