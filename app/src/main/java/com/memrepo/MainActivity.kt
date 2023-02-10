package com.memrepo

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Outline
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.WhitePoint
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

@Composable
fun MemRepo() {
    Scaffold(
        topBar = {TopAppBar(
            title = {Text(
                "MemRepo",
                color = androidx.compose.ui.graphics.Color.White)},
            backgroundColor = Color(0xff0f9d58)
        ) },
        content = {}
    )
}
@Composable
fun MyContent(){
    var title by remember { mutableStateOf("") }
    var snippet by remember { mutableStateOf("") }
    val mContext = LocalContext.current

    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        // Creating an Outlined Button and setting
        // the shape attribute to CircleShape
        // When the Button is clicked, a Toast
        // message would be displayed
        OutlinedButton(onClick = { Toast.makeText(mContext, "This is a Circular Button with a + Icon", Toast.LENGTH_LONG).show()},
            modifier= Modifier.size(100.dp),
            shape = CircleShape,
            border= BorderStroke(5.dp, Color(0XFF0F9D58)),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Blue)
        ) {
            // Adding an Icon "Add" inside the Button
            Icon(Icons.Default.Add ,contentDescription = "content description", tint=Color(0XFF0F9D58))
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