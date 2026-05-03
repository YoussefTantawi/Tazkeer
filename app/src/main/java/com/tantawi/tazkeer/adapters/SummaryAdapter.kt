package com.tantawi.tazkeer.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.tantawi.tazkeer.R
import com.tantawi.tazkeer.models.SummaryItem

// Adapter connects dashboard summary numbers to RecyclerView cards.
class SummaryAdapter : RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>() {
    private val items = mutableListOf<SummaryItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<SummaryItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_summary_card, parent, false)
        return SummaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context
        holder.titleText.setText(item.titleResId)
        holder.countText.text = item.countText
        holder.card.setCardBackgroundColor(ContextCompat.getColor(context, item.colorResId))
    }

    override fun getItemCount(): Int = items.size

    class SummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: MaterialCardView = itemView.findViewById(R.id.summaryCard)
        val titleText: TextView = itemView.findViewById(R.id.summaryTitleText)
        val countText: TextView = itemView.findViewById(R.id.summaryCountText)
    }
}
