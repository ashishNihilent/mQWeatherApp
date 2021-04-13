package com.kadamab.weather.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kadamab.weather.ViewModel.WeatherViewModel
import com.kadamab.weather.databinding.LayoutDetailBinding
import kotlinx.android.synthetic.main.item_weather.view.*
import kotlinx.android.synthetic.main.layout_detail.view.*

class WeatherFragment : Fragment() {
    var weatherViewModel: WeatherViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = LayoutDetailBinding.inflate(inflater)
        getData(binding.root)
        return binding.root
    }

    private fun getData(root: ConstraintLayout) {
        val factory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)

        weatherViewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)

        weatherViewModel!!.observeWeatherData().observe(viewLifecycleOwner, Observer {
            if (it != null && it != null) {
                val data = it.main
                root.textDate.text = "${it.name} Live"
                root.weatherLayout.textWeatherState.text = data.feels_like.toString()

                root.weatherLayout.txtAverageValue.text = data.temp.toString() + "°"
                root.weatherLayout.txtMinimumValue.text = data.temp_min.toString() + "°"
                root.weatherLayout.txtMaximumValue.text = data.temp_max.toString() + "°"

                root.weatherLayout.textAirPressureValue.text = data.pressure.toString() + " mbar"
                root.weatherLayout.textHumidityValue.text = data.humidity.toString() + "%"

            }
        })
    }
}


