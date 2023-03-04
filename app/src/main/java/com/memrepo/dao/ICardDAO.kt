package com.memrepo.dao

import androidx.room.*
import com.memrepo.dto.NoteCard

@Dao
interface ICardDAO {

    @Query("SELECT * FROM NoteCard")
    suspend fun getAllNoteCards() : List<NoteCard>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveNoteCard(vararg notecards: NoteCard)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNoteCard(vararg notecards: NoteCard)

    @Delete
    suspend fun deleteNoteCard(vararg notecards: NoteCard)
}