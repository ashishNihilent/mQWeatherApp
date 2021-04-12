package com.kadamab.weather.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kadamab.weather.Common.RequestParam
import com.kadamab.weather.Common.SharedPreference
import com.kadamab.weather.ViewModel.WeatherViewModel
import com.kadamab.weather.databinding.ActivityMainBinding
import io.realm.Realm

/**

* Created by KADAMAB on 30 March 2021

*/

class MainActivity : AppCompatActivity(), FavClickListener  {

    private var currentWoeid = RequestParam.Default.CURRENT_CITY
    private lateinit var realm: Realm

    var weatherViewModel: WeatherViewModel? = null
    private lateinit var weatherRecycler: RecyclerView
    private lateinit var viewAdapterWeather: RecyclerView.Adapter<*>
    private lateinit var viewManagerWeather: RecyclerView.LayoutManager

    private lateinit var locationRecycler: RecyclerView
    private lateinit var viewAdapterLocation: RecyclerView.Adapter<*>
    private lateinit var viewManagerLocation: RecyclerView.LayoutManager

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bindings = ActivityMainBinding.inflate(layoutInflater);
        setContentView(bindings.root)
        realm = Realm.getDefaultInstance()
        SharedPreference.init(applicationContext)
        currentWoeid = SharedPreference.loadPreference(
            RequestParam.SharedPref.PREF_VAL_KEY,
            RequestParam.Default.CURRENT_CITY
        ).toString()

        viewManagerWeather = LinearLayoutManager(this)
        viewManagerLocation = LinearLayoutManager(this)

        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        weatherViewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)


        weatherViewModel!!.observeWeatherData().observe(this, Observer {
            if(it != null && it.main != null){
                val data = it.main
                bindings.mainContet.weatherLayout.textWeatherState.text = data.feels_like.toString()
                //holder.binding.textDate.text = data.get(position).applicable_date
                bindings.mainContet.weatherLayout.txtAverageValue.text = data.temp.toString() + "°"
                bindings.mainContet.weatherLayout.txtMinimumValue.text = data.temp_min.toString() + "°"
                bindings.mainContet.weatherLayout.txtMaximumValue.text = data.temp_max.toString() + "°"

                bindings.mainContet.weatherLayout.textAirPressureValue.text = data.pressure.toString() + " mbar"
                bindings.mainContet.weatherLayout.textHumidityValue.text = data.humidity.toString() + "%"

            }
        })


        bindings.mainContet.spinnerCities.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                weatherViewModel!!.requestWeatherData(parent.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
            weatherViewModel!!.requestWeatherData(currentWoeid)
    }

    override fun locationOnClick(pos: Int, woeid: String) {
        currentWoeid = woeid
        SharedPreference.savePreference(RequestParam.SharedPref.PREF_VAL_KEY, woeid)
        try {
            locationRecycler.visibility = View.GONE
            weatherRecycler.visibility = View.VISIBLE
        }catch(e: Exception){
            e.printStackTrace()
        }
    }
}

interface FavClickListener {
    fun locationOnClick(pos: Int, woeid: String)
}
