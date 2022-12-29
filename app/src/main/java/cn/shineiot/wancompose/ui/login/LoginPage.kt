package cn.shineiot.wancompose.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.shineiot.wancompose.RouteConfig
import cn.shineiot.wancompose.route.NavUtil
import cn.shineiot.wancompose.ui.theme.F2
import cn.shineiot.wancompose.ui.theme.dp10
import cn.shineiot.wancompose.ui.theme.dp20
import cn.shineiot.wancompose.ui.theme.dp30

/**
 * login
 */
@Preview
@Composable
fun LoginPage() {
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(F2)
            .padding(start = dp10, end = dp20)
    ) {

        var username by remember {
            mutableStateOf("请输入用户名")
        }

        OutlinedTextField(
            value = username,
            onValueChange = {},
            placeholder = {},
            label = {}
        )
        Button(
            onClick = { NavUtil.get().navigation(RouteConfig.ROUTE_MAIN) },
            Modifier.padding(5.dp)
        ) {
            Text(
                text = "Login",
            )
        }
    }
}

