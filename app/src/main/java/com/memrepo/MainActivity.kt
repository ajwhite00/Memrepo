package com.memrepo

import android.os.Bundle
import android.util.TypedValue
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.memrepo.ui.theme.MemrepoTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.my_text_view).typeface = Res.Font(this, R.font.best)
        findViewById<TextView>(R.id.my_text_view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 50.toFloat())

        setContent {
            MemrepoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}
/*val fontPath = "fonts/myfont.ttf"
val typeface = Typeface.createFromAsset(context.assets, fontPath)
textView.typeface = typeface*/



@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MemrepoTheme {
        Greeting("Android")
    }
}