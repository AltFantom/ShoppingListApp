package com.kupriyanov.shoppinglistapp.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kupriyanov.shoppinglistapp.R
import com.kupriyanov.shoppinglistapp.domain.ShopItem

class ShopItemAdapter : RecyclerView.Adapter<ShopItemAdapter.ShopItemViewHolder>() {

    var count = 0

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        Log.d("ShopItemAdapter", "onCreateViewHolder ${++count}")
        val layout = when(viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val view = shopList[position]
        viewHolder.textName.text = view.name
        viewHolder.textCount.text = view.count.toString()
        viewHolder.view.setOnLongClickListener {
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (shopList[position].enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textName = view.findViewById<TextView>(R.id.tvName)
        val textCount = view.findViewById<TextView>(R.id.tvCount)
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0
        const val MAX_POOL_SIZE = 15
    }
}