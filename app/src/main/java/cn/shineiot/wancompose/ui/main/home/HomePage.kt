package cn.shineiot.wancompose.ui.main.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cn.shineiot.wancompose.utils.LogUtil

/**
 * @Description
 * @Author : GF63
 * @Date : 2023/1/29
 */
@Preview
@Composable
fun HomePage() {
    var num: Int by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(Unit) {
        LogUtil.e(num)
    }

    SideEffect {
        LogUtil.e("HomePage-- $num")
    }

    DisposableEffect(Unit) {
        onDispose {
            LogUtil.e("homepage--disposable")
        }
    }

    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "HomePage--$num")
        }
    }
}