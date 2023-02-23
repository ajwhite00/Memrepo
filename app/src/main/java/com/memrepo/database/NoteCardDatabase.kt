package com.memrepo.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteCard::class], version = 1)
abstract class NoteCardDatabase : RoomDatabase() {
    abstract fun cardDAO(): ICardDAO
}