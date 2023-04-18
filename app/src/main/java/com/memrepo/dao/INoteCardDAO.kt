package com.memrepo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.memrepo.dto.NoteCard

/**
 * Interface that implements CRUD functions from the DAO.
 */
@Dao
interface INoteCardDAO {

    /**
     * getAllNoteCards makes a basic SQLLite statement that grabs all NoteCards from the database.
     */
    @Query("SELECT * FROM NoteCards")
    fun getAllNoteCards() : LiveData<List<NoteCard>>

    /**
     * getNoteCardById selects a NoteCard by the given id
     */
    @Query("SELECT * FROM NoteCards WHERE cardID=:id ")
    suspend fun getNoteCardById(id: Int): NoteCard

    /**
     * saveNoteCard inserts all note-card data into the database.
     */
    @Upsert
    suspend fun saveNoteCard(noteCard: NoteCard)

    /**
     * delete removes selected NoteCard from the database.
     */
    @Delete
    suspend fun deleteNoteCard(noteCard: NoteCard)

}