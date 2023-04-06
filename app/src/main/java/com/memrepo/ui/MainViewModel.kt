package com.memrepo.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memrepo.dto.NoteCard
import com.memrepo.service.NoteCardService
import kotlinx.coroutines.launch

class MainViewModel(var noteCardService: NoteCardService) : ViewModel() {

  var noteCards = noteCardService.getNoteCardDAO().getAllNoteCards()
  var selectedNoteCard by mutableStateOf(NoteCard(0, "", ""))

  fun saveNoteCard(noteCard: NoteCard) {
    viewModelScope.launch {
      noteCardService.saveNoteCard(noteCard = noteCard)
    }
  }

  fun deleteNoteCard(noteCard: NoteCard) {
    viewModelScope.launch {
      noteCardService.deleteNoteCard(noteCard = noteCard)
    }
  }

  fun getNoteCardById(noteCard: NoteCard) {
    viewModelScope.launch {
      selectedNoteCard = noteCardService.getNoteCardDAO().getNoteCardById(noteCard.cardID)
    }
  }

}