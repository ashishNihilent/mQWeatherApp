package com.kadamab.weather.Adapters

import Main
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kadamab.weather.databinding.ItemWeatherBinding

import java.text.DecimalFormat

/**

*Created by KADAMAB on 30 March 2021

*/
class WeatherAdapter(private val data: Main) : RecyclerView.Adapter<WeatherAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val df = DecimalFormat()
        df.setMaximumFractionDigits(2)
        holder.binding.textWeatherState.text = data.feels_like.toString()
        holder.binding.textAirPressureValue.text = data.pressure.toString() + " mbar"
        holder.binding.textHumidityValue.text = data.humidity.toString() + "%"

    }

    override fun getItemCount(): Int = 1
}