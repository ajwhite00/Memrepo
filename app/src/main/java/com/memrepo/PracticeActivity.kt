package com.memrepo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memrepo.ui.theme.MemrepoTheme
import kotlinx.coroutines.launch

class PracticeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemrepoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    practiceScreen()
                }
            }
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun practiceScreen(){
    val mContext = LocalContext.current
    Scaffold(
        // Bar across top of screen
        topBar = {
            TopAppBar(

                title = {Text(text = "Practice")},
                navigationIcon = {
                    IconButton(onClick = {
                        mContext.startActivity(Intent(mContext, MainActivity::class.java))
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back" )
                    }
                }
            )
        },
        content = {
            MyContent()
        }
    )
}

@Composable
fun MyContent(){
    Box(Modifier.fillMaxSize()){
        Column(Modifier.padding(10.dp)) {

            Text(text = "Name all the Oceans?", Modifier.fillMaxSize(), fontSize = 25.sp)


        }
        Text(text = "filler data in here for now", Modifier.align(Alignment.CenterStart).padding(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MemrepoTheme {
        practiceScreen()
    }
}