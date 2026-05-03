package com.tantawi.tazkeer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.tantawi.tazkeer.R
import com.tantawi.tazkeer.models.PrayerItem

// Adapter connects prayer time data to RecyclerView grid cards.
class PrayerAdapter(
    private val onPrayerChecked: (PrayerItem, Boolean) -> Unit
) : RecyclerView.Adapter<PrayerAdapter.PrayerViewHolder>() {
    private val prayers = mutableListOf<PrayerItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_prayer_card, parent, false)
        return PrayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PrayerViewHolder, position: Int) {
        val item = prayers[position]
        val context = holder.itemView.context
        holder.card.setCardBackgroundColor(ContextCompat.getColor(context, item.colorResId))
        holder.nameText.text = item.displayName
        holder.timeText.text = item.time
        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = item.completed
        holder.checkBox.setOnCheckedChangeListener { _, isChecked -> onPrayerChecked(item, isChecked) }
    }

    override fun getItemCount(): Int = prayers.size

    class PrayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: MaterialCardView = itemView.findViewById(R.id.prayerCard)
        val nameText: TextView = itemView.findViewById(R.id.prayerNameText)
        val timeText: TextView = itemView.findViewById(R.id.prayerTimeText)
        val checkBox: CheckBox = itemView.findViewById(R.id.prayerCheckBox)
    }
}
