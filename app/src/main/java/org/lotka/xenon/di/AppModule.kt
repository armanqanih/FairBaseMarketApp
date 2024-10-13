package org.lotka.xenon.di

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.lotka.xenon.data.remote.dao.local.database.ItemDatabase

import org.lotka.xenon.data.remote.dao.local.CategoryDao
import org.lotka.xenon.data.remote.dao.local.ItemsDao


import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideCategoryDao(database: ItemDatabase): CategoryDao = database.categoryDao()

    @Provides
    fun provideItemsDao(database: ItemDatabase): ItemsDao = database.itemsDao()


    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }


    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()


}

