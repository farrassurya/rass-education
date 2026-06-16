package com.example.rass_education.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rass_education.data.local.entity.History
import com.example.rass_education.databinding.ItemHistoryBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(
    private val onDelete: (History) -> Unit
) : ListAdapter<History, HistoryAdapter.HistoryViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) {
            binding.tvActivityName.text = history.activityName
            binding.tvTimestamp.text = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault()).format(Date(history.timestamp))
            
            // P12 : Aksi hapus item riwayat
            binding.btnDeleteHistory.setOnClickListener { onDelete(history) }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean = oldItem == newItem
    }
}