package com.example.rass_education.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val activityName: String,
    val timestamp: Long = System.currentTimeMillis()
)