package com.memrepo.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.memrepo.dto.NoteCard

@Database(entities = arrayOf(NoteCard::class), version = 1)
abstract class NoteCardDatabase : RoomDatabase() {
    abstract fun CardDAO(): ICardDAO
}