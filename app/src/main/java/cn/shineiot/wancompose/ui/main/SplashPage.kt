package cn.shineiot.wancompose.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import cn.shineiot.wancompose.R
import cn.shineiot.wancompose.route.NavUtil
import cn.shineiot.wancompose.ui.theme.*
import cn.shineiot.wancompose.utils.RouteConfig
import cn.shineiot.wancompose.utils.SharePreferenceUtils
import kotlinx.coroutines.delay

/**
 * @Description
 * @Author : GF63
 * @Date : 2023/2/1
 */

@Preview
@Composable
fun SplashPage() {
    LaunchedEffect(key1 = Unit, block = {
        SharePreferenceUtils.default.printKey()

        delay(1500L)

        val cookie = SharePreferenceUtils.default.contains("www.wanandroid.com")

        if (cookie) {
            NavUtil.get().navigation(RouteConfig.ROUTE_MAIN)
        } else {
            NavUtil.get().navigation(RouteConfig.ROUTE_LOGIN)
        }
    })

    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = F2)
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.huojiang),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(dp150))
                Text(
                    text = "WanCompose",
                    color = RED,
                    fontSize = sp22
                )
            }
        }
    }
}