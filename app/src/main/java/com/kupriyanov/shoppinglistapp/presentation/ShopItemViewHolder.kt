package com.kupriyanov.shoppinglistapp.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kupriyanov.shoppinglistapp.R

class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val textName = view.findViewById<TextView>(R.id.tvName)
    val textCount = view.findViewById<TextView>(R.id.tvCount)
}