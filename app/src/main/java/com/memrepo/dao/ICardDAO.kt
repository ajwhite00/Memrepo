package com.memrepo.dao

import androidx.room.*
import androidx.lifecycle.LiveData
import com.memrepo.dto.NoteCard

@Dao
interface ICardDAO {

    @Query("SELECT * FROM NoteCard")
    fun getAllNoteCards(): LiveData<List<NoteCard>>

    //@Query("SELECT * FROM user WHERE cardID IN (:userIDs)")
    //fun loadAllByIds(userIds: IntArray): List<NoteCard>

    @Update
    fun updateNoteCard(vararg noteCard: NoteCard)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(noteCard: ArrayList<NoteCard>)

    @Delete
    fun deleteNoteCard(noteCard: NoteCard)
}