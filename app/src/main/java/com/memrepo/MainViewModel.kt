package com.memrepo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memrepo.dto.NoteCard
import com.memrepo.service.NoteCardService
import kotlinx.coroutines.launch

class MainViewModel(var noteCardService: NoteCardService) : ViewModel() {

  var noteCards = noteCardService.getNoteCardDAO().getAllNoteCards()

  fun saveNoteCard(noteCard: NoteCard) {
    viewModelScope.launch {
      noteCardService.saveNoteCard(noteCard = noteCard)
    }
  }

  fun updateNoteCard(noteCard: NoteCard) {
    viewModelScope.launch {
      noteCardService.updateNoteCard(noteCard = noteCard)
    }
  }

  fun deleteNoteCard(noteCard: NoteCard) {
    viewModelScope.launch {
      noteCardService.deleteNoteCard(noteCard = noteCard)
    }
  }

}