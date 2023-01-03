package com.kupriyanov.shoppinglistapp.di

import android.app.Application
import com.kupriyanov.shoppinglistapp.presentation.activities.MainActivity
import com.kupriyanov.shoppinglistapp.presentation.activities.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [ViewModelModule::class, DataModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(shopItemFragment: ShopItemFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

}