package cn.shineiot.wancompose.ui.login

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import cn.shineiot.wancompose.RouteConfig
import cn.shineiot.wancompose.route.NavUtil

/**
 * login
 */
@Composable
fun LoginPage() {
    Button(onClick = { NavUtil.get().navigation(RouteConfig.ROUTE_MAIN) }) {
        Text(text = "Login")
    }
}

