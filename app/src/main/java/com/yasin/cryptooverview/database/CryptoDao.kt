package com.yasin.cryptooverview.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import retrofit2.http.GET

@Dao
interface CryptoDao {
    @Query("SELECT * FROM CryptoCurrencyTable ORDER BY rank")
    fun getAllCrypto(): LiveData<List<CryptoCurrencyTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg cryptoTb: CryptoCurrencyTable)

    @Query("SELECT * FROM CryptoCurrencyTable WHERE name LIKE :name ORDER BY rank")
    fun search(name: String): List<CryptoCurrencyTable>

    @Query("SELECT * FROM CryptoCurrencyTable WHERE symbol = :name")
    fun get(name: String): CryptoCurrencyTable

}