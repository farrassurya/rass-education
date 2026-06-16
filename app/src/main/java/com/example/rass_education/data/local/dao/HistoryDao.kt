package com.example.rass_education.data.local.dao

import androidx.room.*
import com.example.rass_education.data.local.entity.History
import kotlinx.coroutines.flow.Flow

// P12 : Data Access Object untuk mengelola riwayat aktivitas di Room
@Dao
interface HistoryDao {
    // P12 : Mengambil 5 aktivitas terbaru secara real-time (Flow)
    @Query("SELECT * FROM history ORDER BY timestamp DESC LIMIT 5")
    fun getRecentHistory(): Flow<List<History>>

    // P12 : Menyimpan aktivitas baru
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: History)

    // P12 : Menghapus satu item riwayat
    @Delete
    suspend fun deleteHistoryItem(history: History)

    // P12 : Menghapus semua riwayat
    @Query("DELETE FROM history")
    suspend fun clearHistory()
}