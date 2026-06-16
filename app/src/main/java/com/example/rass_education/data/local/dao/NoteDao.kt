package com.example.rass_education.data.local.dao

import androidx.room.*
import com.example.rass_education.data.local.entity.Note
import kotlinx.coroutines.flow.Flow

// P12 : Data Access Object untuk tabel Notes
@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY date DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}