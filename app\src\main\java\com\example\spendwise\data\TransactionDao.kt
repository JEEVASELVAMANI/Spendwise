package com.example.spendwise.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(txn: Transaction)

    @Query("SELECT * FROM `Transaction` ORDER BY timestamp DESC LIMIT :limit")
    suspend fun recent(limit: Int): List<Transaction>

    @Query("SELECT SUM(amount) FROM `Transaction` WHERE type = 'debit'")
    suspend fun totalDebits(): Double?

    @Query("SELECT SUM(amount) FROM `Transaction` WHERE type = 'credit'")
    suspend fun totalCredits(): Double?
}
