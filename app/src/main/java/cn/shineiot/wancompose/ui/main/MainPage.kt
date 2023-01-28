package cn.shineiot.wancompose.ui.main

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import cn.shineiot.wancompose.R
import cn.shineiot.wancompose.RouteConfig
import cn.shineiot.wancompose.route.NavUtil
import cn.shineiot.wancompose.ui.theme.*

/**
 * main
 */
@Preview
@Composable
fun MainPage() {
    val bottomNumber = listOf("首页", "清除", "电话", "邮箱")
    val bottomIcon =
        listOf(Icons.Default.Home, Icons.Default.Clear, Icons.Default.Call, Icons.Default.Email)

    var checkIndex by remember {
        mutableStateOf(0)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "title") })
        },
        bottomBar = {
            BottomNavigation() {
                bottomNumber.forEachIndexed { index, content ->
                    BottomNavigationItem(
                        selected = checkIndex == index,
                        onClick = {
                            checkIndex = index
                        },
                        label = { Text(text = content) },
                        icon = {
                            if (checkIndex == index) {
                                BadgedBox(badge = { Text(text = "3+") }) {
                                    Icon(imageVector = bottomIcon[index], contentDescription = null)
                                }
                            } else {
                                Icon(imageVector = bottomIcon[index], contentDescription = null)
                            }
                        },
                        selectedContentColor = Red,
                        unselectedContentColor = WRITE
                    )
                }
            }
        },
    ) {
        ConstraintLayout(
            Modifier
                .background(color = F2)
                .fillMaxSize(1f)
        ) {

            val (txt, txt2) = createRefs()//ConstraintLayout 需要先声明组件

            Text(text = "main", modifier = Modifier
                .selectable(
                    selected = true,
                    onClick = {
                        NavUtil
                            .get()
                            .navigation(RouteConfig.ROUTE_LOGIN)
                    }
                )
                .constrainAs(txt) {
//                    top.linkTo(parent.top, margin = dp10)
//                    start.linkTo(parent.start, margin = dp10)

                    centerVerticallyTo(parent)
                    centerHorizontallyTo(parent)

                })

            Text(text = "main2", modifier = Modifier
                .padding(dp10)
                .selectable(
                    selected = true,
                    onClick = {
                        NavUtil
                            .get()
                            .onBack()
                        //NavUtil.get().navigation(RouteConfig.ROUTE_LOGIN)
                    }
                )
                .constrainAs(txt2) {
                    top.linkTo(txt.top, margin = dp100)
                    start.linkTo(txt.start, margin = dp100)
                },
                color = RED,
                fontSize = sp16
            )
        }
    }

}

