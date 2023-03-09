package com.memrepo

import android.app.Application
import com.memrepo.database.NoteCardDatabase
import com.memrepo.database.NoteCardRepository

class NoteCardApplication: Application()
{
    private val database by lazy { NoteCardDatabase.getDatabase(this) }
    val repository by lazy { NoteCardRepository(database.cardDAO()) }
}