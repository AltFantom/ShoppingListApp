package com.kupriyanov.shoppinglistapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kupriyanov.shoppinglistapp.R
import com.kupriyanov.shoppinglistapp.domain.ShopItem

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }



    private fun launchRightMode() {
        val fragment = when (screenMode) {
            EXTRA_MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            EXTRA_MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            else ->
                throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.shopItemContainer, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != EXTRA_MODE_ADD && mode != EXTRA_MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == EXTRA_MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    companion object {
        private const val MODE_UNKNOWN = ""

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val EXTRA_MODE_ADD = "extra_mode_add"
        private const val EXTRA_MODE_EDIT = "extra_mode_edit"

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, EXTRA_MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, EXTRA_MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        finish()
    }
}