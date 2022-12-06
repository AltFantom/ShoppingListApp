package com.kupriyanov.shoppinglistapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kupriyanov.shoppinglistapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var rvShopItems: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvShopItems = findViewById(R.id.rvShopItems)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupRecyclerView()
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        shopListAdapter = ShopListAdapter()
        with(rvShopItems) {
            this.adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
        setupSwipeListener()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        shopListAdapter.onShopItemLongClick = {
            viewModel.changeEnableState(it)
        }
        shopListAdapter.onShopItemClick = {
            val intent = ShopItemActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
        val fabAddShopItem = findViewById<FloatingActionButton>(R.id.fabAddShopItem)
        fabAddShopItem.setOnClickListener {
            val intent = ShopItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setupSwipeListener() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val shopItem = shopListAdapter.currentList[position]
                viewModel.deleteShopItem(shopItem)
            }
        }).attachToRecyclerView(rvShopItems)
    }
}