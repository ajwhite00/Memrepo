package com.memrepo.dto

import androidx.room.PrimaryKey

data class NoteCard(var cardID : Int, var title : String, var snippet : String,){
        fun createSnippetDisplayList(): List<String> {
                return this.snippet //has three rounds of replacement
                        .replace("-".toRegex(), " ") //replace hyphens with space because of two cases: split words with hyphen in between and remove hyphen in the middle of a sentence
                        .replace("  ".toRegex(), " ") //replace any double spaces caused by hyphen replacement with a single space
                        .replace("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]".toRegex(), "") //remove punctuation and other non-letter chars
                        .lowercase() //change everything to lowercase to be used later for mic input comparison
                        .split(" ") //separates words as space
                        .toList() //creates a list individual words to be used later for mic input comparison
        }
}
