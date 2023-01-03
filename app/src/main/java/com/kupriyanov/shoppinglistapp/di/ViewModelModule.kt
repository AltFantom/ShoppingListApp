package com.kupriyanov.shoppinglistapp.di

import androidx.lifecycle.ViewModel
import com.kupriyanov.shoppinglistapp.presentation.viewModels.MainViewModel
import com.kupriyanov.shoppinglistapp.presentation.viewModels.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun bindShopItemViewModel(shopItemViewModel: ShopItemViewModel): ViewModel
}