package com.example.spendwise.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val amount: Double,
    val type: String,
    val merchant: String?,
    val rawText: String
)
