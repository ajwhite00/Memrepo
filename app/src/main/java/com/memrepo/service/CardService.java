package com.memrepo.service;

import android.app.Application;
import androidx.room.RoomDatabase;
import com.memrepo.dao.NoteCardDatabase;
import com.memrepo.dao.ICardDAO;
import kotlinx.coroutines.Dispatchers;

/*
public class CardService(val application: Application) : ICardService {
    override suspend fun fetchNoteCards() : List<NoteCard>?
    {
        return withContext(Dispatchers.IO)
        {
            val service = NoteCardDatabase.retrofitInstance?create(ICardDAO::class.java)
            val notecards = async {service?.getAllNoteCards()}
            var result = notecards.await()?.awaitResponse()?.body()
            return@withContext result
        }
    }
}
*/
