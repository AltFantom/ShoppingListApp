package com.kupriyanov.shoppinglistapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.kupriyanov.shoppinglistapp.R
import com.kupriyanov.shoppinglistapp.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var buttonSave: Button
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews()
        launchRightMode()
        addTextChangedListeners()
        observeViewModel()
        shouldCloseScreen()
    }

    private fun launchRightMode() {
        when (screenMode) {
            EXTRA_MODE_EDIT -> launchEditMode()
            EXTRA_MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        buttonSave.setOnClickListener {
            val name = etName.text?.toString()
            val count = etCount.text?.toString()
            viewModel.editShopItem(name, count)
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            val name = etName.text.toString()
            val count = etCount.text.toString()
            viewModel.addShopItem(name, count)
        }

    }

    private fun shouldCloseScreen() {
        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }

    private fun addTextChangedListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    private fun observeViewModel() {
        viewModel.errorInputCount.observe(this) {
            if (it) {
                tilCount.error = "Error"
            } else {
                tilCount.error = null
            }
        }
        viewModel.errorInputName.observe(this) {
            if (it) {
                tilName.error = "Error"
            } else {
                tilName.error = null
            }
        }
    }

    private fun initViews() {
        buttonSave = findViewById(R.id.buttonSave)
        tilName = findViewById(R.id.tilName)
        tilCount = findViewById(R.id.tilCount)
        etName = findViewById(R.id.etName)
        etCount = findViewById(R.id.etCount)
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
}