package com.kadamab.weather.View

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
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

    private var currentWoeid = 0
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
        currentWoeid = SharedPreference.loadPreference("_woeid", 0)

        viewManagerWeather = LinearLayoutManager(this)
        viewManagerLocation = LinearLayoutManager(this)

        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        weatherViewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)

        weatherViewModel!!.observeWeatherLocation().observe(this, Observer {
            if (it != null) {
                viewAdapterLocation = LocationAdapter(it, this)
                locationRecycler = bindings.mainContet.weatherLocation.apply {
                    setHasFixedSize(true)
                    layoutManager = viewManagerLocation
                    adapter = viewAdapterLocation
                }
                try {
                    locationRecycler.visibility = View.VISIBLE
                    weatherRecycler.visibility = View.GONE
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        })

        weatherViewModel!!.observeWeatherData().observe(this, Observer {
            if(it != null){
                viewAdapterWeather = WeatherAdapter(it.consolidated_weather)
                weatherRecycler = bindings.mainContet.weatherRecycler.apply {
                    setHasFixedSize(true)
                    layoutManager = viewManagerWeather
                    adapter = viewAdapterWeather
                }
                try {
                    locationRecycler.visibility = View.GONE
                    weatherRecycler.visibility = View.VISIBLE
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        })

        bindings.mainContet.searchBoxEdit.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s != "") {
                    weatherViewModel!!.requestWeatherLocation(bindings.mainContet.searchBoxEdit.text.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })

        if(currentWoeid != 0){
            weatherViewModel!!.requestWeatherData(currentWoeid);
        }
    }

    override fun locationOnClick(pos: Int, woeid: Int) {
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
    fun locationOnClick(pos: Int, woeid: Int)
}