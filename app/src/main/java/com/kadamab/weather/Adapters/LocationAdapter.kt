package com.kadamab.weather.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kadamab.weather.View.FavClickListener
import com.kadamab.weather.databinding.ItemLocationBinding


/**

*Created by KADAMAB on 31 March 2021

*/
class LocationAdapter(private val data: List<String>, private val cellClickListener: FavClickListener) : RecyclerView.Adapter<LocationAdapter.MyViewHolder>()  {

    class MyViewHolder(val binding: ItemLocationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemLocationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.textName.text = data.get(position)
        holder.itemView.setOnClickListener {
            cellClickListener.locationOnClick(position, data.get(position))
        }


    }

    override fun getItemCount(): Int = data.size
}