package com.kupriyanov.shoppinglistapp.di

import android.app.Application
import com.kupriyanov.shoppinglistapp.data.AppDatabase
import com.kupriyanov.shoppinglistapp.data.ShopListDao
import com.kupriyanov.shoppinglistapp.data.ShopListRepositoryImpl
import com.kupriyanov.shoppinglistapp.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @AppScope
    @Binds
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object {

        @AppScope
        @Provides
        fun provideShopListDao(
            application: Application
        ): ShopListDao = AppDatabase.getInstance(application).shopListDao()
    }
}