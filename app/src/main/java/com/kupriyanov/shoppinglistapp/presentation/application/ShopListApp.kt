package com.kupriyanov.shoppinglistapp.presentation.application

import android.app.Application
import com.kupriyanov.shoppinglistapp.di.DaggerAppComponent

class ShopListApp: Application() {
    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
}