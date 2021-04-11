package com.kadamab.weather.View

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kadamab.weather.Adapters.LocationAdapter
import com.kadamab.weather.Adapters.WeatherAdapter
import com.kadamab.weather.Common.SharedPreference
import com.kadamab.weather.ViewModel.WeatherViewModel
import com.kadamab.weather.databinding.ActivityMainBinding

/**

* Created by KADAMAB on 30 March 2021

*/

class MainActivity : AppCompatActivity(), CellClickListener  {

    private var currentWoeid = "mumbai"
    var weatherViewModel: WeatherViewModel? = null

    private lateinit var weatherRecycler: RecyclerView
    private lateinit var viewAdapterWeather: RecyclerView.Adapter<*>
    private lateinit var viewManagerWeather: RecyclerView.LayoutManager

    private lateinit var locationRecycler: RecyclerView
    private lateinit var viewAdapterLocation: RecyclerView.Adapter<*>
    private lateinit var viewManagerLocation: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bindings = ActivityMainBinding.inflate(layoutInflater);
        setContentView(bindings.root)

        SharedPreference.init(applicationContext)
        currentWoeid = SharedPreference.loadPreference("_woeid", "mumbai").toString()

        viewManagerWeather = LinearLayoutManager(this)
        viewManagerLocation = LinearLayoutManager(this)

        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        weatherViewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)


        weatherViewModel!!.observeWeatherData().observe(this, Observer {
            if(it != null){
             //to be done
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

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // another interface callback
            }
        }

            weatherViewModel!!.requestWeatherData(currentWoeid)
    }

    override fun locationOnClick(pos: Int, woeid: String) {
        weatherViewModel!!.requestWeatherData(woeid)
        currentWoeid = woeid
        SharedPreference.savePreference("_woeid", woeid)
        try {
            locationRecycler.visibility = View.GONE
            weatherRecycler.visibility = View.VISIBLE
        }catch(e: Exception){
            e.printStackTrace()
        }
    }
}

interface CellClickListener {
    fun locationOnClick(pos: Int, woeid: String)
}
