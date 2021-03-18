package com.yasin.cryptooverview.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CryptoCurrencyTable::class], version = 1, exportSchema = false)
abstract class CryptoDatabase : RoomDatabase() {
    abstract val cryptoDao: CryptoDao
}