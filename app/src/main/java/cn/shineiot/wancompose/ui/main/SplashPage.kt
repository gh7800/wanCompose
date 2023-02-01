package cn.shineiot.wancompose.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import cn.shineiot.wancompose.R
import cn.shineiot.wancompose.RouteConfig
import cn.shineiot.wancompose.route.NavUtil
import cn.shineiot.wancompose.ui.theme.*
import cn.shineiot.wancompose.utils.LogUtil
import cn.shineiot.wancompose.utils.SharePreferenceUtils
import kotlinx.coroutines.delay
import java.util.*
import java.util.logging.Handler
import kotlin.concurrent.fixedRateTimer

/**
 * @Description
 * @Author : GF63
 * @Date : 2023/2/1
 */

@Preview
@Composable
fun SplashPage() {

    class MyTimerTask : TimerTask() {
        override fun run() {
        }
    }

    val task = MyTimerTask()

    LaunchedEffect(key1 = Unit, block = {
        //Timer().schedule(task, 2000L)

        //SharePreferenceUtils.default.printKey()

        delay(1500L)//延迟1500毫秒

        NavUtil.get().navigation(RouteConfig.ROUTE_MAIN)

        /*val cookie = SharePreferenceUtils.default.contains("www.wanandroid.com")

        if (cookie) {
        } else {
            NavUtil.get().navigation(RouteConfig.ROUTE_LOGIN)
        }*/
    })

    Scaffold() {
        ConstraintLayout(
            modifier = Modifier
                .background(color = F2)
                .fillMaxSize(1f)
        ) {
            val img = createRef()
            val (image, text) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.huojiang),
                contentDescription = null,
                modifier = Modifier.constrainAs(image) {
                    /*
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    */

                    centerVerticallyTo(parent)
                    centerHorizontallyTo(parent)
                }
            )
            Text(
                text = "WanCompose",
                modifier = Modifier
                    .constrainAs(text) {
                        top.linkTo(image.bottom, margin = dp150)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                maxLines = 1,
                color = RED,
                fontSize = sp22
            )
        }
    }
}