package com.memrepo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.memrepo.dto.NoteCard

@Dao
interface ICardDAO {

    @Query("SELECT * FROM NoteCards")
    fun getAllNoteCards() : LiveData<List<NoteCard>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNoteCard(noteCard: NoteCard)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNoteCard(noteCard: NoteCard)

    @Delete
    suspend fun delete(noteCard: NoteCard)
}