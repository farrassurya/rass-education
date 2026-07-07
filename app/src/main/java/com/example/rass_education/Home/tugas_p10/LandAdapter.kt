package com.example.rass_education.Home.tugas_p10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rass_education.databinding.ItemLandBinding

class LandAdapter(private val landList: List<LandRecord>) :
    RecyclerView.Adapter<LandAdapter.LandViewHolder>() {

    class LandViewHolder(val binding: ItemLandBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandViewHolder {
        val binding = ItemLandBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LandViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LandViewHolder, position: Int) {
        val land = landList[position]
        holder.binding.apply {
            tvLandId.text = "NIB: ${land.id}"
            tvOwnerName.text = "Pemilik: ${land.ownerName}"
            tvLandType.text = land.type
            tvAreaSize.text = "Luas: ${land.areaSize} m²"
            tvStatus.text = "Status: ${land.status}"
        }
    }

    override fun getItemCount(): Int = landList.size
}

