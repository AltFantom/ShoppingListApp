package com.kupriyanov.shoppinglistapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.kupriyanov.shoppinglistapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopItemAdapter: ShopItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopItemAdapter = ShopItemAdapter()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupRecyclerView()
        viewModel.shopList.observe(this) {
            shopItemAdapter.shopList = it
        }
    }

    private fun setupRecyclerView() {
        val rvShopItems = findViewById<RecyclerView>(R.id.rvShopItems)
        with (rvShopItems) {
            this.adapter = shopItemAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopItemAdapter.VIEW_TYPE_ENABLED,
                ShopItemAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopItemAdapter.VIEW_TYPE_DISABLED,
                ShopItemAdapter.MAX_POOL_SIZE
            )
        }

    }
}