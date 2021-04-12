package com.kadamab.weather.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kadamab.weather.View.FavClickListener
import com.kadamab.weather.databinding.ItemLocationBinding
import com.kadamab.weather.rm.City
import io.realm.RealmResults

/**

*Created by KADAMAB on 31 March 2021

*/
class LocationAdapter(private val data: RealmResults<City>, private val cellClickListener: FavClickListener) : RecyclerView.Adapter<LocationAdapter.MyViewHolder>()  {

    class MyViewHolder(val binding: ItemLocationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemLocationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.textName.text = data.get(position).name
        holder.itemView.setOnClickListener {
            cellClickListener.locationOnClick(position, data.get(position).name)
        }
    }

    override fun getItemCount(): Int = data.size
}