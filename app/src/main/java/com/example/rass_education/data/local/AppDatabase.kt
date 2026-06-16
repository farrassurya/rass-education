package com.example.rass_education.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rass_education.data.local.dao.HistoryDao
import com.example.rass_education.data.local.dao.NoteDao
import com.example.rass_education.data.local.entity.History
import com.example.rass_education.data.local.entity.Note

// P12 : Upgrade versi ke 3 untuk memastikan tabel History terbuat dengan benar
@Database(entities = [Note::class, History::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rass_education_db"
                )
                // P12 : Destructive migration akan menghapus DB lama dan membuat yang baru sesuai skema terbaru
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}