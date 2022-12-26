package cn.shineiot.wancompose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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

/**
 * TopAppBar
 */
@Composable
fun SetTopAppBar() {
    TopAppBar(
        title = { Text(text = "这是标题123", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(1f) )},
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "", tint = Color.White)
            }
        },
        actions = {
            IconButton(onClick = {  }) {
                Icon(Icons.Filled.Share, contentDescription = "分享")
            }
            IconButton(onClick = {  }) {
                Icon(Icons.Filled.Settings, contentDescription = "设置")
            }
        }
    )
}

//预览
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Dark Mode1")
@Composable
fun DefaultPreview() {
    Greeting(Message("java", "kotlin"))
}

@Composable
fun Greeting(msg: Message) {
    WanComposeTheme() {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column {
                SetTopAppBar()

                Row(modifier = Modifier.padding(5.dp)) {
                    Image(
                        painter = painterResource(R.drawable.huojiang),
                        contentDescription = "image",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(8.dp))//分割线

                    Column() {
                        Text(
                            text = msg.msg,
                            color = MaterialTheme.colors.secondaryVariant,
                            style = MaterialTheme.typography.subtitle1,
                        )
                        Text(
                            text = msg.title,
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(1f),
                            style = MaterialTheme.typography.body2,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}



