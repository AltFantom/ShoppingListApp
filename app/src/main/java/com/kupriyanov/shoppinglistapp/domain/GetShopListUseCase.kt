package com.kupriyanov.shoppinglistapp.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetShopListUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository
    ) {

    fun getShopList(): LiveData<List<ShopItem>> = shopListRepository.getShopList()
}