package cn.shineiot.wancompose.ui.main

import android.content.res.Configuration
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import cn.shineiot.wancompose.RouteConfig
import cn.shineiot.wancompose.route.NavUtil
import cn.shineiot.wancompose.ui.theme.WanComposeTheme

/**
 * main
 */
@Composable
fun MainPage() {
    Text(text = "main", modifier = Modifier.selectable(
        selected = true,
        onClick = {
            NavUtil.get().navigation(RouteConfig.ROUTE_LOGIN)
        }
    ))
}
