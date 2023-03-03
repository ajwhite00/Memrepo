package com.memrepo.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.memrepo.dao.ICardDAO
import com.memrepo.dto.NoteCard

@Database(entities = [NoteCard::class], version = 1)
abstract class NoteCardDatabase : RoomDatabase() {
    abstract fun cardDAO(): ICardDAO
}