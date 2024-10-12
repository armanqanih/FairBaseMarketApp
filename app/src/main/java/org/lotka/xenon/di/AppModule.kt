package org.lotka.xenon.di

   import HomeRepositoryImpl
   import android.content.Context
   import androidx.room.Room

   import com.google.firebase.database.FirebaseDatabase
   import com.google.firebase.firestore.FirebaseFirestore
    import dagger.Module
    import dagger.Provides
    import dagger.hilt.InstallIn
   import dagger.hilt.android.qualifiers.ApplicationContext
   import dagger.hilt.components.SingletonComponent
   import org.lotka.xenon.data.remote.dao.AppDatabase

   import org.lotka.xenon.data.remote.dao.AppDatabase.Companion.MIGRATION_2_3

   import org.lotka.xenon.data.remote.dao.CategoryDao
   import org.lotka.xenon.data.remote.dao.ItemsDao



   import org.lotka.xenon.domain.repository.HomeRepository

   import javax.inject.Singleton

    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule {


        @Module
        @InstallIn(SingletonComponent::class)
        object DatabaseModule {

            @Provides
            @Singleton
            fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
                return Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    "app_database"
                ).addMigrations(MIGRATION_2_3)
                    .fallbackToDestructiveMigration()
                    .build()
            }

            @Provides
            fun provideCategoryDao(database: AppDatabase): CategoryDao = database.categoryDao()

            @Provides
            fun provideItemsDao(database: AppDatabase): ItemsDao = database.itemsDao()
        }





        @Provides
        @Singleton
        fun provideFirebaseDatabase(): FirebaseDatabase {
            return FirebaseDatabase.getInstance()
        }


        @Provides
        @Singleton
        fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

        @Provides
        @Singleton
        fun provideHomeRepository(realtimeDatabase: FirebaseDatabase,
            categoryDao: CategoryDao,itemsDao: ItemsDao): HomeRepository {
            return HomeRepositoryImpl(realtimeDatabase,categoryDao,itemsDao)
        }
    }


