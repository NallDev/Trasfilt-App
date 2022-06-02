package com.example.trafilt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trafilt.databinding.ItemPickUpBinding
import com.example.trafilt.model.PickupData

class ListPickUpAdapter(private val listPickUp: ArrayList<PickupData>) : RecyclerView.Adapter<ListPickUpAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemPickUpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, _, city) = listPickUp[position]
        holder.binding.pickupName.text = name
        holder.binding.pickupCity.text = city
        holder.binding.phone.setOnClickListener{
            onItemClickCallback.onItemClicked(listPickUp[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listPickUp.size

    class ListViewHolder(var binding: ItemPickUpBinding): RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: PickupData)
    }
}