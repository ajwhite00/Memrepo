package com.memrepo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memrepo.dto.NoteCard
import com.memrepo.service.CardService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var notecards : MutableLiveData<List<NoteCard>> = MutableLiveData<List<NoteCard>>()
    var cardService : CardService = CardService()

    fun fetchNoteCards(){

        viewModelScope.launch{
            val innerNoteCard = cardService.fetchNoteCards()
            notecards.postValue(innerNoteCard)
        }
    }

}