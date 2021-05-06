package com.kadamab.seersorders.Adapters

import Orders
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kadamab.seersorders.R
import com.kadamab.seersorders.View.FavClickListener
import com.kadamab.seersorders.databinding.ItemOrderListBinding


/**

 *Created by KADAMAB on 05 May 2021

 */
class OrderAdapter(
    context: Context?,
    private val data: Orders,
    private val cellClickListener: FavClickListener
) : RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemOrderListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemOrderListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvOrder.text = data.customers.get(position).serviceRequested
        holder.binding.textType.text = data.customers.get(position).orderRef
        holder.binding.tvScheduleAt.text = data.customers.get(position).scheduleDate
        when (data.customers.get(position).status) {
            "AT" -> holder.binding.ivStatus.setBackgroundColor(
                ContextCompat.getColor(
                    holder.binding.ivStatus.context,
                    R.color.colorGreen
                )
            )
            else -> holder.binding.ivStatus.setBackgroundColor(
                ContextCompat.getColor(
                    holder.binding.ivStatus.context,
                    R.color.colorPrimary
                )
            )
        }

        val media = data.customers.get(position).imageUrl
        Glide.with(holder.binding.tvProductImage.context)
            .load(media)
            .into(holder.binding.tvProductImage)
        holder.itemView.setOnClickListener {
            cellClickListener.locationOnClick(position)
        }
    }

    override fun getItemCount(): Int = data.customers.size
}