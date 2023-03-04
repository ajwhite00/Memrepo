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
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

@Composable
fun SpeechRecognizerComponent(context: Context, activity: Activity) {

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.RECORD_AUDIO), 1)
    }

    if(!SpeechRecognizer.isRecognitionAvailable(context)){
        Toast.makeText(context, "Speech recognition is not available", Toast.LENGTH_LONG).show()
    }

    val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)


    val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
    speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

    var status by remember { mutableStateOf("") }
    var isListening by remember { mutableStateOf(false) }

    speechRecognizer.setRecognitionListener(object : RecognitionListener {
        override fun onReadyForSpeech(p0: Bundle?) {
            isListening = true
            status = "Ready"
        }

        override fun onBeginningOfSpeech() {
            status = "Listening..."
        }

        override fun onRmsChanged(p0: Float) {}

        override fun onBufferReceived(p0: ByteArray?) {}

        override fun onEndOfSpeech() {
            status = ""
            isListening = false
        }

        override fun onError(p0: Int) {
            Toast.makeText(context, "ERROR $p0", Toast.LENGTH_SHORT).show()
        }

        override fun onResults(bundle: Bundle?) {
            val data = bundle!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            Toast.makeText(context, data!![0], Toast.LENGTH_LONG).show()
        }

        override fun onPartialResults(bundle: Bundle?) {}

        override fun onEvent(p0: Int, p1: Bundle?) {}

    })

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Button(
                onClick = {
                     if (!isListening) speechRecognizer.startListening(speechRecognizerIntent) else speechRecognizer.stopListening()
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_microphone_foreground),
                    contentDescription = "Microphone"
                )
            }
            Text(text = status, modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column( modifier = Modifier.align(Alignment.Center) ) {
            Button(
                onClick = {},
            ){
                Icon(painter = painterResource(id = R.drawable.ic_microphone_foreground), contentDescription = "Microphone")
            }
            Text(text = "Text", modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}