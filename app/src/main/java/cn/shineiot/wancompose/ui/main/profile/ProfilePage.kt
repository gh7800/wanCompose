package cn.shineiot.wancompose.ui.main.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cn.shineiot.wancompose.utils.ToastUtil

/**
 * @Description
 * @Author : GF63
 * @Date : 2023/1/29
 */
@Preview
@Composable
fun ProfilePage() {

    LaunchedEffect(key1 = Unit){
    }

    SideEffect {
        ToastUtil.showCenter("ProfilePage--")
    }

    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "ProfilePage")
        }
    }
}