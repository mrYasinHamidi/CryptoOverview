package com.yasin.cryptooverview.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yasin.cryptooverview.CryptoConstants
import com.yasin.cryptooverview.network.CryptoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier

@Module
@InstallIn(ActivityRetainedComponent::class)
class NetworkModule {
    @Qualifier
    annotation class CoinCap

    @Qualifier
    annotation class Nomics

    @Provides
    @Nomics
    fun provideBaseUrlForNomicsApi() = CryptoConstants.NOMICS_BASE_URL

    @Provides
    @CoinCap
    fun provideBaseUrlForCoinCapApi() = CryptoConstants.COIN_CAP_BASE_URL

    @ActivityRetainedScoped
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Nomics
    @ActivityRetainedScoped
    @Provides
    fun provideRetrofitForNomicsApi(moshi: Moshi,@Nomics BASE_URL: String): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    @CoinCap
    @ActivityRetainedScoped
    @Provides
    fun provideRetrofitForCoinCapApi(moshi: Moshi,@CoinCap BASE_URL: String): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    @Nomics
    @Provides
    @ActivityRetainedScoped
    fun provideApiServiceForNomics(@Nomics retrofit: Retrofit) = retrofit.create(CryptoApiService::class.java)

    @CoinCap
    @Provides
    @ActivityRetainedScoped
    fun provideApiServiceForCoinCap(@CoinCap retrofit: Retrofit) = retrofit.create(CryptoApiService::class.java)

}