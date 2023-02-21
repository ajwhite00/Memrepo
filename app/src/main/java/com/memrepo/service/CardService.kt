package com.memrepo.service

import com.memrepo.RetrofitClientInstance
import com.memrepo.dao.ICardDAO
import com.memrepo.dto.NoteCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class CardService {

    suspend fun fetchNoteCards() : List<NoteCard>?{
        return withContext(Dispatchers.IO)
        {
            val service = RetrofitClientInstance.retrofitInstance?.create(ICardDAO::class.java)

            val notecards = async { service?.getAllNoteCards() }

            var result = notecards.await()?.awaitResponse()?.body()

            return@withContext result
        }
    }
}