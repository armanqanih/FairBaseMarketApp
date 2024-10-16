package org.lotka.xenon.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.lotka.xenon.data.remote.dao.local.database.CardDatabase
import org.lotka.xenon.data.remote.dao.local.database.ItemDatabase
import org.lotka.xenon.data.remote.dao.local.database.ProfileDataBase

import org.lotka.xenon.data.remote.dao.local.database.WishListDatabase
import org.lotka.xenon.data.util.Constants.CARD_DATABASE
import org.lotka.xenon.data.util.Constants.ITEM_DATABASE
import org.lotka.xenon.data.util.Constants.PROFILE_DATABASE
import org.lotka.xenon.data.util.Constants.WISHLIST_DATABASE

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideItemDatabase(@ApplicationContext appContext: Context): ItemDatabase {
        return Room.databaseBuilder(
            appContext,
            ItemDatabase::class.java,
            ITEM_DATABASE
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCartDatabase(@ApplicationContext appContext: Context): CardDatabase {
        return Room.databaseBuilder(
            appContext,
            CardDatabase::class.java,
            CARD_DATABASE
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWishListDatabase(@ApplicationContext appContext: Context): WishListDatabase {
        return Room.databaseBuilder(
            appContext,
            WishListDatabase::class.java,
            WISHLIST_DATABASE
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideProfileDatabase(@ApplicationContext appContext: Context): ProfileDataBase {
        return Room.databaseBuilder(
            appContext,
            ProfileDataBase::class.java,
            PROFILE_DATABASE
        ).fallbackToDestructiveMigration()
            .build()
    }


}
