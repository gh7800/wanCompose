package cn.shineiot.wancompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import cn.shineiot.wancompose.route.NavUtil
import cn.shineiot.wancompose.route.composableX
import cn.shineiot.wancompose.ui.login.LoginPage
import cn.shineiot.wancompose.ui.main.MainPage
import cn.shineiot.wancompose.ui.main.SplashPage
import cn.shineiot.wancompose.ui.main.home.HomePage
import cn.shineiot.wancompose.ui.main.profile.ProfilePage
import cn.shineiot.wancompose.utils.RouteConfig.ROUTE_HOME
import cn.shineiot.wancompose.utils.RouteConfig.ROUTE_LOGIN
import cn.shineiot.wancompose.utils.RouteConfig.ROUTE_MAIN
import cn.shineiot.wancompose.utils.RouteConfig.ROUTE_PROFILE
import cn.shineiot.wancompose.utils.RouteConfig.ROUTE_SPLASH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavContent()
        }
    }
}

@Composable
fun NavContent(route : String = ROUTE_SPLASH) {
    val navController = rememberNavController()

    //初始化工具类
    NavUtil.get().init(navController)

    NavHost(navController = navController, startDestination = route) {
        composableX(ROUTE_SPLASH) { SplashPage() }
        composableX(ROUTE_LOGIN) { LoginPage() }
        composableX(ROUTE_MAIN) { MainPage() }
        composableX(ROUTE_HOME) { HomePage() }
        composableX(ROUTE_PROFILE) { ProfilePage() }

    }
}



