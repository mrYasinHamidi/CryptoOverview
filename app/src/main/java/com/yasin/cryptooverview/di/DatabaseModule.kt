package com.yasin.cryptooverview.di

import android.content.Context
import androidx.room.Room
import com.yasin.cryptooverview.database.CryptoDatabase
import com.yasin.cryptooverview.destinations.DetailFragmentArgs
import com.yasin.cryptooverview.models.CryptoCurrency
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class DatabaseModule {

    @Provides
    @ActivityRetainedScoped
    fun provideDatabase(@ApplicationContext context: Context): CryptoDatabase =
        Room.databaseBuilder(context, CryptoDatabase::class.java, "CryptoDatabase").build()
}