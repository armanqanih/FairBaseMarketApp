package org.lotka.xenon.di

   import HomeRepositoryImpl
   import com.google.firebase.database.FirebaseDatabase
   import com.google.firebase.firestore.FirebaseFirestore
    import dagger.Module
    import dagger.Provides
    import dagger.hilt.InstallIn
    import dagger.hilt.components.SingletonComponent


    import org.lotka.xenon.domain.repository.HomeRepository
    import javax.inject.Singleton

    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule {

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
        fun provideHomeRepository(realtimeDatabase: FirebaseDatabase): HomeRepository {
            return HomeRepositoryImpl(realtimeDatabase)
        }
    }


