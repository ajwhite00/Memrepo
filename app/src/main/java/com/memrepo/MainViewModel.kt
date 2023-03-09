package com.memrepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.memrepo.database.NoteCardRepository
import com.memrepo.dto.NoteCard
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NoteCardRepository): ViewModel() {

    var noteCards: LiveData<List<NoteCard>> = repository.allNoteCards

    fun addNoteCard(newNoteCard: NoteCard) = viewModelScope.launch {
        repository.insertNoteCard(newNoteCard)
    }

    fun updateNoteCard(noteCard: NoteCard) = viewModelScope.launch{
        repository.updateNoteCard(noteCard)
    }

    fun deleteNoteCard(noteCard: NoteCard) = viewModelScope.launch{
        repository.deleteNoteCard(noteCard)
    }

}

class NoteCardModelFactory(private val repository: NoteCardRepository): ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(repository) as T

        throw IllegalArgumentException("Error: Unknown class for view model.")
    }
}