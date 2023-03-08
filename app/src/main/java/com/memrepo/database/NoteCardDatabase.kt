package com.memrepo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.memrepo.dao.ICardDAO
import com.memrepo.dto.NoteCard

/**
 * @constructor essentially implements all entities of the DTO, NoteCard, into account.
 * The abstract function cardDAO is then passed into the front end to be used for database instances.
 */
@Database(entities = [NoteCard::class], version = 1)
abstract class NoteCardDatabase : RoomDatabase() {
    abstract fun cardDAO(): ICardDAO
}