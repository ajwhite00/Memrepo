package com.memrepo.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.memrepo.dto.NoteCard

@Dao
interface ICardDAO {

    @Query("SELECT * FROM NoteCard")
    fun getAll(): List<NoteCard>

    @Query("SELECT * FROM user WHERE cardID IN (:userIDs)")
    fun loadAllByIds(userIds: IntArray): List<NoteCard>



    @Update
    fun updateNoteCard(vararg noteCard: NoteCard)

    @Insert
    fun insertAll(vararg noteCard: NoteCard)

    @Delete
    fun delete(noteCard: NoteCard)
}