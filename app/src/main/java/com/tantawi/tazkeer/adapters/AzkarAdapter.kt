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
import com.tantawi.tazkeer.models.AzkarItem

// Adapter connects Azkar cards to a RecyclerView grid.
class AzkarAdapter(
    private val onAzkarClicked: (AzkarItem) -> Unit,
    private val onAzkarChecked: (AzkarItem, Boolean) -> Unit
) : RecyclerView.Adapter<AzkarAdapter.AzkarViewHolder>() {
    private val azkarItems = mutableListOf<AzkarItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AzkarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_azkar_card, parent, false)
        return AzkarViewHolder(view)
    }

    override fun onBindViewHolder(holder: AzkarViewHolder, position: Int) {
        val item = azkarItems[position]
        val context = holder.itemView.context
        holder.card.setCardBackgroundColor(ContextCompat.getColor(context, item.colorResId))
        holder.titleText.setText(item.titleResId)
        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = item.completed
        holder.checkBox.setOnCheckedChangeListener { _, isChecked -> onAzkarChecked(item, isChecked) }
        holder.itemView.setOnClickListener { onAzkarClicked(item) }
    }

    override fun getItemCount(): Int = azkarItems.size

    class AzkarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: MaterialCardView = itemView.findViewById(R.id.azkarCard)
        val titleText: TextView = itemView.findViewById(R.id.azkarTitleText)
        val checkBox: CheckBox = itemView.findViewById(R.id.azkarCheckBox)
    }
}
