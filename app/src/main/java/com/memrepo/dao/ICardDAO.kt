package com.memrepo.dao

import com.memrepo.dto.NoteCard
import retrofit2.Call
import retrofit2.http.GET

interface ICardDAO {

    @GET("")
    fun getAllNoteCards() : Call<ArrayList<NoteCard>>
}