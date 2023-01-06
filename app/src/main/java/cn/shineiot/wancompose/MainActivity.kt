package cn.shineiot.wancompose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cn.shineiot.wancompose.RouteConfig.ROUTE_LOGIN
import cn.shineiot.wancompose.RouteConfig.ROUTE_MAIN
import cn.shineiot.wancompose.bean.Message
import cn.shineiot.wancompose.route.NavUtil
import cn.shineiot.wancompose.route.composableX
import cn.shineiot.wancompose.ui.login.LoginPage
import cn.shineiot.wancompose.ui.main.MainPage
import cn.shineiot.wancompose.ui.theme.WanComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavContent()
        }
    }
}

@Composable
fun NavContent(){
    val navController = rememberNavController()

    //初始化工具类
    NavUtil.get().init(navController)

    NavHost(navController = navController, startDestination = ROUTE_LOGIN){
        composableX(ROUTE_LOGIN){ LoginPage() }
        composableX(ROUTE_MAIN){ MainPage() }

    }
}

/**
 * pages
 */
object RouteConfig {
    const val ROUTE_LOGIN = "login"
    const val ROUTE_MAIN = "main"
}





/**
 *
 * --------------------------------------------------------------------------------------------------------------------------------------------
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
                /*var name = remember {
                    mutableListOf<String>()
                }*/
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
                            color = MaterialTheme.colors.primary,
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.selectable(
                                selected = true,
                                onClick = {}
                            )
                        )
                        Text(
                            text = msg.title,
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(1f),
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center,
                        )
                        Button(
                            onClick = {},
                            modifier = Modifier.padding(5.dp),
                        ) {
                            Text(text = "Button")
                        }
                    }
                }
            }
        }
    }
}



