package com.memrepo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                    MainScreen()
                }
            }
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {TopAppBar(
            title = {Text(
                "MemRepo")},
        ) },
        
        bottomBar = { BottomAppBar {
            Box(Modifier.fillMaxWidth()) {
                Button(
                    onClick = {},
                    modifier = Modifier.align(Alignment.Center).fillMaxWidth().fillMaxHeight()
                ) {
                    (Text(text = "Add"))
                }
            }

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
            MainScreen()
        }
    }
}