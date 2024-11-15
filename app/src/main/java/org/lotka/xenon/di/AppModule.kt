package org.lotka.xenon.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.lotka.xenon.data.remote.dao.local.database.ItemDatabase

import org.lotka.xenon.data.remote.dao.local.CategoryDao
import org.lotka.xenon.data.remote.dao.local.ItemsDao
import org.lotka.xenon.data.remote.dao.local.ProfileDao
import org.lotka.xenon.data.remote.dao.local.database.ProfileDataBase
import org.lotka.xenon.data.remote.repository.AuthRepositoryImpl
import org.lotka.xenon.domain.repository.AuthRepository
import org.lotka.xenon.domain.util.NewAccountManager


import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()


    @Provides
    fun provideNewAccountManager(
        @ApplicationContext context: Context
    ): NewAccountManager {
        return NewAccountManager(context)
    }



    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
      realtimeDatabase: FirebaseDatabase,
    ): AuthRepository = AuthRepositoryImpl(firebaseAuth,realtimeDatabase)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("your_shared_prefs_name", Context.MODE_PRIVATE)
    }


    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    fun provideCategoryDao(database: ItemDatabase): CategoryDao = database.categoryDao()

    @Provides
    fun provideItemsDao(database: ItemDatabase): ItemsDao = database.itemsDao()

    @Provides
    fun provideProfileDao(database: ProfileDataBase): ProfileDao = database.profileDao()

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()


}

