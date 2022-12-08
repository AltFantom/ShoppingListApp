package com.kupriyanov.shoppinglistapp.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.kupriyanov.shoppinglistapp.R
import com.kupriyanov.shoppinglistapp.domain.ShopItem

class ShopItemFragment : Fragment() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private lateinit var buttonSave: Button
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText

    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LifecycleFragment", "onCreate")
        parseParams()
    }

    override fun onAttach(context: Context) {
        Log.d("LifecycleFragment", "onAttach")
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw java.lang.RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("LifecycleFragment", "onCreateView")
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("LifecycleFragment", "onViewCreated")
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)
        launchRightMode()
        addTextChangedListeners()
        observeViewModel()
        shouldCloseScreen()
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner) {
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
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
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
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            if (it) {
                tilCount.error = "Error"
            } else {
                tilCount.error = null
            }
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            if (it) {
                tilName.error = "Error"
            } else {
                tilName.error = null
            }
        }
    }

    private fun initViews(view: View) {
        buttonSave = view.findViewById(R.id.buttonSave)
        tilName = view.findViewById(R.id.tilName)
        tilCount = view.findViewById(R.id.tilCount)
        etName = view.findViewById(R.id.etName)
        etCount = view.findViewById(R.id.etCount)
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    interface OnEditingFinishedListener {

        fun onEditingFinished()
    }

    override fun onStart() {
        super.onStart()
        Log.d("LifecycleFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LifecycleFragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LifecycleFragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LifecycleFragment", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("LifecycleFragment", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LifecycleFragment", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("LifecycleFragment", "onDetach")
    }

    companion object {
        private const val MODE_UNKNOWN = ""

        private const val SCREEN_MODE = "extra_mode"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_ADD = "extra_mode_add"
        private const val MODE_EDIT = "extra_mode_edit"

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }

}