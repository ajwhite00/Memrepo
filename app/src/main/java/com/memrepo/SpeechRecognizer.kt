package com.memrepo

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.memrepo.dto.NoteCard
import java.util.*

@Composable
fun SpeechRecognizerComponent(context: Context, activity: Activity, noteCard: NoteCard, speechRecognizer: SpeechRecognizer) {

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.RECORD_AUDIO), 1)
    }

    if(!SpeechRecognizer.isRecognitionAvailable(context)){
        Toast.makeText(context, "Speech recognition is not available", Toast.LENGTH_LONG).show()
    }

    val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
    speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
    speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

    var status by remember { mutableStateOf("") }
    var isListening by remember { mutableStateOf(false) }
    var revealed by remember { mutableStateOf(false) }

    var correctWordList : List<String> = mutableListOf()

    var remainingWords by remember { mutableStateOf(noteCard.createSnippetDisplayList()) }
    var correctWords by remember { mutableStateOf(correctWordList) }
    var incorrectWord by remember { mutableStateOf("") }
    var partialWords by remember { mutableStateOf(mutableListOf<String>()) }
    var blur by remember { mutableStateOf(Color.Gray) }

    // Callback functions can't change values of correctWords or remainingWords within their scope so this function is declared outside their scope
    fun updateList() {
        Log.d("SpeechRecognizer.updateList()", "Removing '${remainingWords[0]}' from remainingWords")
        correctWords = correctWords + remainingWords.removeFirst()
        Log.d("SpeechRecognizer.updateList()", "Correct words $correctWords")
    }

    // Return true or false if word is found in partialWords or all words in partialWords has been iterated through
    fun checkPartialWordsList(word: String, i: Int) : Boolean {
        if (partialWords.size - 1 >= i) {
            return word.lowercase() == partialWords[i]
        }
        return false
    }

    var data : ArrayList<String>?

    speechRecognizer.setRecognitionListener(object : RecognitionListener {
        override fun onReadyForSpeech(p0: Bundle?) {
            isListening = true
            status = "Ready"
            if(incorrectWord.isNotEmpty()){
                incorrectWord = ""
            }
        }

        override fun onBeginningOfSpeech() {
            status = "Listening..."
        }

        override fun onRmsChanged(p0: Float) {}

        override fun onBufferReceived(p0: ByteArray?) {}

        override fun onEndOfSpeech() {
            Log.d("SpeechRecognizer.onEndOfSpeech()","Correct Words: $correctWords")
            Log.d("SpeechRecognizer.onEndOfSpeech()","Remaining Words: $remainingWords")
        }

        override fun onError(p0: Int) {
            status = ""
            isListening = false
            Log.w("SpeechRecognizer.onError()", "Error: $p0")
        }

        override fun onResults(bundle: Bundle?) {
            data = bundle!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)

            var i = 0
            var resultsAfterPartialClear : MutableList<String> = mutableListOf()

            for (word in data!![0].split(" ")) {
                // Check if remainingWords is empty
                if (remainingWords.isEmpty() || word == incorrectWord){
                    break
                }

                // Add word to resultsAfterPartialClear if it is not already in partialWords
                if(!checkPartialWordsList(word, i)){
                    resultsAfterPartialClear.add(word.lowercase())
                }

                i++
            }

            if (resultsAfterPartialClear.isNotEmpty()){
                for (word in resultsAfterPartialClear) {
                    if (word == (remainingWords[0])) {
                        if (incorrectWord.isNotEmpty()) {
                            incorrectWord = ""
                        }
                        // Add to correct word list and remove remaining list
                        updateList()

                    } else {
                        // add to incorrect word list and remove from remaining
                        Log.d("SpeechRecognizer.onResults", "Incorrect word: '$word'")
                        incorrectWord = word
                        break
                    }
                }
            }

            Log.d("SpeechRecognizer.onResults()", "Results: ${data!![0]}")
            Toast.makeText(context, data!![0], Toast.LENGTH_LONG).show()
            speechRecognizer.destroy()
            partialWords.clear()
            status = ""
            isListening = false
            println("End of Results")
        }

        override fun onPartialResults(partialResults: Bundle?) {
            data = partialResults!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            Log.d("SpeechRecognizer.onPartialResults()","Detected partial words: ${data!![0]}")

            fun checkWord(){
                if (remainingWords.isNotEmpty() && partialWords.last() == (remainingWords[0])) {
                    // Add to correct word list and remove remaining list
                    updateList()
                } else {
                    // add to incorrect word list and remove from remaining
                    incorrectWord = partialWords.last()
                    Log.d("SpeechRecognizer.onPartialResults()", "Incorrect word: '${partialWords.last()}'")
                    speechRecognizer.cancel()
                    status = ""
                    isListening = false
                }
            }

            // Update partialWords to add the last word detected as partial result
            fun addResultToPartialWords(data:  ArrayList<String>?) {
                if(data!![0].split(" ").last().isNotEmpty()) {
                    partialWords.add(data!![0].split(" ").last().lowercase())
                    checkWord()
                }
            }

            // partialWords is initially empty we need to add the first partial result or else an exception will be thrown with partialWords.last()
            if(partialWords.isEmpty()){
                addResultToPartialWords(data)
            }
            // Add the most recent partial result to partialWords
            else if (data!![0].split(" ").last().lowercase() != partialWords.last()){
                addResultToPartialWords(data)
            }

            Log.d("SpeechRecognizer.onPartialResults()", "Partial Words: $partialWords")

        }

        override fun onEvent(p0: Int, p1: Bundle?) {}

    })

    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Green)) {
                            Log.d("SpeechRecognizer", "Drawing correctWords: $correctWords")
                            append(correctWords.toString().replace("[,\\[\\]]".toRegex(), ""))
                        }
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append(" $incorrectWord ")
                        }
                        remainingWords.forEach { word ->
                            withStyle(style = SpanStyle(color = Color.Gray, background = blur)) {
                                append(word)
                            }
                            append(" ")
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                     if (!isListening && remainingWords.isNotEmpty()) speechRecognizer.startListening(speechRecognizerIntent)
                          },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_reset_foreground),
                    contentDescription = "Reset"
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_microphone_foreground),
                    contentDescription = "Microphone"
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_reveal_foreground),
                    contentDescription = "Reveal"
                )
            }
            Text(text = status, modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        // Show the remaining text
        Button(modifier = Modifier.align(Alignment.BottomStart).padding(10.dp), onClick = {
            if(!revealed){
                blur = Color.Transparent
                revealed = true
            } else {
                blur = Color.Gray
                revealed = false
            }
        }) {
            if(!revealed){
                Text(text = "Reveal")
            }
            else {
                Text(text = "Hide")
            }
        }
        // Rest all of the progress
        Button(modifier = Modifier.align(Alignment.BottomEnd).padding(10.dp), onClick = {
            incorrectWord = ""
            correctWords = emptyList()
            remainingWords = noteCard.createSnippetDisplayList()
        }) {
            Text(text = "Reset")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    var words = arrayListOf("This", "is", "a", "test")
    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        Column( modifier = Modifier.align(Alignment.Center)) {
            Row( modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Green)) {
                            append("This is a test")
                        }
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append(" This is a test")
                        }
                        words.forEach { word ->
                            append(" ")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Gray,
                                    background = Color.Gray
                                )
                            ) {
                                append(word)
                            }
                        }

                    }
                )
            }

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {},
            ){
                Icon(painter = painterResource(id = R.drawable.ic_microphone_foreground), contentDescription = "Microphone")
            }
            Text(text = "Text", modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        Button(modifier = Modifier.align(Alignment.BottomStart).padding(10.dp), onClick = {/*TODO*/ }) {
            Text(text = "Reveal")
        }
        Button(modifier = Modifier.align(Alignment.BottomEnd).padding(10.dp), onClick = { /*TODO*/ }) {
            Text(text = "Reset")
        }
    }

}