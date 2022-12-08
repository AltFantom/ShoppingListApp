package com.kupriyanov.shoppinglistapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kupriyanov.shoppinglistapp.domain.ShopItem
import com.kupriyanov.shoppinglistapp.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private var autoIncrementId = 0

    init {
        for (i in 0 until 10) {
            val item = ShopItem("name $i", i, Random.nextBoolean())
            addShopItem(item)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (ShopItem.UNDEFINED_ID == shopItem.id) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateLD()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateLD()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItem(shopItem.id)
        shopList.remove(oldShopItem)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Element with id $shopItemId not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    private fun updateLD() {
        shopListLD.value = shopList.toList()
    }
}