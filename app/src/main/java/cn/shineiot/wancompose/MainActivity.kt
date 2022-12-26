package cn.shineiot.wancompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.shineiot.wancompose.ui.bean.Message
import cn.shineiot.wancompose.ui.theme.WanComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WanComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(Message("JAVA", "Kotlin"))
                }
            }
        }
    }
}

@Composable
fun Greeting(msg: Message) {
    Row(modifier = Modifier.padding(5.dp)) {
        Image(
            painter = painterResource(R.drawable.huojiang),
            contentDescription = "image",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column() {
            Text(text = msg.msg, modifier = Modifier
                .width(100.dp)
                .background(color = Color(R.color.cardview_dark_background)))
            Text(text = msg.title)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WanComposeTheme {
        Greeting(Message("java", "kotlin"))
    }
}
