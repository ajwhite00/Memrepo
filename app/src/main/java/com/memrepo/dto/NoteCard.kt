package com.memrepo.dto

data class NoteCard(var cardID : Int, var title : String, var snippet : String){

        /*hyphens can be used in words like self-evident or have a space after then hyphen in a sentence
        As workaround to this, hyphenAdjustedSnippet will replace hyphens with a space character
        Words with no space between hyphens will be properly separated and any instance with a double space will be removed by spacingAdjustedSnippet
         */
        var hyphenAdjustedSnippet = this.snippet.replace("-".toRegex(), " ")
        var spacingAdjustedSnippet = hyphenAdjustedSnippet.replace("  ".toRegex(), " ")
        var snippetDisplay = spacingAdjustedSnippet.replace("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]".toRegex(), "")
        var snippetDisplayList = snippetDisplay.split(" ").toList()
}
