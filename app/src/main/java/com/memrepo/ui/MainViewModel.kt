package com.memrepo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memrepo.dto.NoteCard
import com.memrepo.service.NoteCardService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(var noteCardService: NoteCardService) : ViewModel() {

  val noteCards = noteCardService.getNoteCardDAO().getAllNoteCards()

  fun saveNoteCard(noteCard: NoteCard) {
    viewModelScope.launch(Dispatchers.IO) {
      noteCardService.saveNoteCard(noteCard = noteCard)
    }
  }

  fun updateNoteCard(noteCard: NoteCard) {
    viewModelScope.launch(Dispatchers.IO) {
      noteCardService.updateNoteCard(noteCard = noteCard)
    }
  }

  fun deleteNoteCard(noteCard: NoteCard) {
    viewModelScope.launch(Dispatchers.IO) {
      noteCardService.deleteNoteCard(noteCard = noteCard)
    }
  }

}