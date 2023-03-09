package com.memrepo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.memrepo.dao.ICardDAO
import com.memrepo.dto.NoteCard

/**
 * @constructor essentially implements all entities of the DTO, NoteCard, into account.
 * The abstract function cardDAO is then passed into the front end to be used for database instances.
 */
@Database(entities = [NoteCard::class], version = 1, exportSchema = false)
abstract class NoteCardDatabase : RoomDatabase() {
    abstract fun cardDAO(): ICardDAO

    companion object
    {
        @Volatile
        private var INSTANCE: NoteCardDatabase? = null

        fun getDatabase(context: Context): NoteCardDatabase
        {

            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(context.applicationContext, NoteCardDatabase::class.java, "note_card_database").build()

                INSTANCE = instance
                instance
            }
        }
    }
}