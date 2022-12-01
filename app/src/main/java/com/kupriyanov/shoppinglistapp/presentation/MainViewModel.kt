package com.kupriyanov.shoppinglistapp.presentation

import androidx.lifecycle.ViewModel
import com.kupriyanov.shoppinglistapp.data.ShopListRepositoryImpl
import com.kupriyanov.shoppinglistapp.domain.DeleteShopItemUseCase
import com.kupriyanov.shoppinglistapp.domain.EditShopItemUseCase
import com.kupriyanov.shoppinglistapp.domain.GetShopListUseCase
import com.kupriyanov.shoppinglistapp.domain.ShopItem

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }
}