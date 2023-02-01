package cn.shineiot.wancompose.ui.main.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cn.shineiot.wancompose.ui.theme.RED
import cn.shineiot.wancompose.ui.theme.dp150
import cn.shineiot.wancompose.ui.theme.sp22
import cn.shineiot.wancompose.utils.LogUtil
import cn.shineiot.wancompose.utils.ToastUtil

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

    Scaffold() {
        ConstraintLayout(Modifier.fillMaxSize(1f)) {
            val (text) = createRefs()

            Text(
                text = "HomePage--$num",
                modifier = Modifier
                    .selectable(true, onClick = {
                        num++
                    })
                    .constrainAs(text) {
                        centerVerticallyTo(parent)
                        centerHorizontallyTo(parent)
                    },
            )
        }
    }
}
