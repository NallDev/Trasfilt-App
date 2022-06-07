package com.example.trafilt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.trafilt.api.PickUpItem
import com.example.trafilt.databinding.ItemPickUpBinding
import com.example.trafilt.utility.MyDiffUtil

class ListPickUpAdapter : RecyclerView.Adapter<ListPickUpAdapter.ListViewHolder>() {
    private var oldPickUpList = emptyList<PickUpItem>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(ItemPickUpBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding.pickupName.text = oldPickUpList[position].namePickUp
        holder.binding.pickupCity.text = oldPickUpList[position].cityPickUp
        holder.binding.phone.setOnClickListener{
            onItemClickCallback.onItemClicked(oldPickUpList[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = oldPickUpList.size

    class ListViewHolder(val binding: ItemPickUpBinding): RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: PickUpItem)
    }

    fun setData(newPickUpList: List<PickUpItem>){
        val diffUtil = MyDiffUtil(oldPickUpList, newPickUpList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldPickUpList = newPickUpList
        diffResult.dispatchUpdatesTo(this)
    }
}