package com.memrepo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.ModuleInfo
import android.graphics.Outline
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.memrepo.ui.theme.MemrepoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemrepoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MemRepo()
                }
            }
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MemRepo() {
    Scaffold(
        topBar = {TopAppBar(
            title = {Text(
                "MemRepo")},
        ) },
        
        bottomBar = { BottomAppBar (){

        }},
        content = {
            MyContent()
        }
    )

}
@Composable
fun MyContent() {
    var title by rememberSaveable { mutableStateOf("") }
    var snippet by rememberSaveable { mutableStateOf("") }
    val paddingModifier = Modifier.padding(10.dp)

    Box(Modifier.fillMaxSize()) {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = 10.dp,
            modifier = Modifier.padding(10.dp)

        ) {
            Column(paddingModifier) {
                Text(text = "Title", Modifier.fillMaxWidth())
                Text(text = "Snippet", Modifier.fillMaxWidth())
            }
            Button(modifier = Modifier.align(Alignment.TopEnd), onClick = {}){

                Text("...")

            }
            }
        }
    }
@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    MemrepoTheme {
        Column {
            MemRepo()


        }
    }
}