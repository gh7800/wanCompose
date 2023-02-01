package cn.shineiot.wancompose.ui.main.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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

    Scaffold() {
        ConstraintLayout(Modifier.fillMaxSize(1f)) {
            val (text) = createRefs()

            Text(text = "ProfilePage", modifier = Modifier.constrainAs(text) {
                centerVerticallyTo(parent)
                centerHorizontallyTo(parent)
            })
        }
    }
}
