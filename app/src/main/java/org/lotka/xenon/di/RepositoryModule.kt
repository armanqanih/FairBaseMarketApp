package org.lotka.xenon.di

import ExploreRepositoryImpl
import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.lotka.xenon.data.remote.dao.local.CardDao
import org.lotka.xenon.data.remote.dao.local.CategoryDao
import org.lotka.xenon.data.remote.dao.local.ItemsDao
import org.lotka.xenon.data.remote.dao.local.WishListDao
import org.lotka.xenon.data.remote.dao.local.database.CardDatabase
import org.lotka.xenon.data.remote.dao.local.database.ItemDatabase
import org.lotka.xenon.data.remote.dao.local.database.WishListDatabase
import org.lotka.xenon.data.remote.repository.CardRepositoryImpl
import org.lotka.xenon.data.remote.repository.WishListRepositoryImpl
import org.lotka.xenon.domain.repository.CardRepository
import org.lotka.xenon.domain.repository.ExploreRepository
import org.lotka.xenon.domain.repository.WishListRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideExploreRepository(
        realtimeDatabase: FirebaseDatabase, db:ItemDatabase,context:Context
    ): ExploreRepository {
        return ExploreRepositoryImpl(realtimeDatabase, db,context)
    }

    @Provides
    @Singleton
    fun provideCardRepository(
        db:CardDatabase,
    ): CardRepository {
        return CardRepositoryImpl(db)
    }

    @Provides
    @Singleton
    fun provideWishListRepository(
        db: WishListDatabase,
    ): WishListRepository {
        return WishListRepositoryImpl(db)
    }

}