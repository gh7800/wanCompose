package cn.shineiot.wancompose.ui.main

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cn.shineiot.wancompose.NavContent
import cn.shineiot.wancompose.RouteConfig
import cn.shineiot.wancompose.ui.login.LoginPage
import cn.shineiot.wancompose.ui.main.home.HomePage
import cn.shineiot.wancompose.ui.main.profile.ProfilePage
import cn.shineiot.wancompose.ui.theme.*

/**
 * main 主页
 */
@Preview
@Composable
fun MainPage(
    navController: NavHostController = rememberNavController()
) {
    val bottomNumber = listOf("首页", "导航", "收藏", "我的")
    val bottomIcon = listOf(Icons.Default.Home, Icons.Default.Clear, Icons.Default.Call, Icons.Default.Email)
    val bottomNavPages = listOf(RouteConfig.ROUTE_HOME,RouteConfig.ROUTE_PROFILE,RouteConfig.ROUTE_HOME,RouteConfig.ROUTE_PROFILE)

    var checkIndex by remember {
        mutableStateOf(0)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "title") })
        },
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                bottomNumber.forEachIndexed { index, content ->
                    BottomNavigationItem(
                        selected = currentDestination?.hierarchy?.any{ it.route == bottomNavPages[checkIndex]} == true,
                        onClick = {
                            checkIndex = index
                            val route = bottomNavPages[checkIndex]
                            navController.navigate(route){
                                popUpTo(navController.graph.findStartDestination().id){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(text = content) },
                        icon = {
                            if (checkIndex == index) {
                                BadgedBox(badge = { Text(text = "3+") }) {
                                    Icon(imageVector = bottomIcon[index], contentDescription = null)
                                }
                            } else {
                                Icon(imageVector = bottomIcon[index], contentDescription = null)
                            }
                        },
                        unselectedContentColor = WRITE,
                        selectedContentColor = GREEN,
                    )
                }
            }
        },
        content = {
            NavContent(RouteConfig.ROUTE_HOME)
            /*NavHost(navController = navController, startDestination = RouteConfig.ROUTE_HOME, builder = {
                composable(RouteConfig.ROUTE_HOME) { HomePage() }
                composable(RouteConfig.ROUTE_PROFILE){ ProfilePage() }
                composable(RouteConfig.ROUTE_HOME) { HomePage() }
                composable(RouteConfig.ROUTE_PROFILE){ ProfilePage() }
            })*/
        }
    )
//    {
//        ConstraintLayout(
//            Modifier
//                .background(color = F2)
//                .fillMaxSize(1f)
//        ) {
//
//            val (txt, txt2) = createRefs()//ConstraintLayout 需要先声明组件
//
//            Text(text = "main", modifier = Modifier
//                .selectable(
//                    selected = true,
//                    onClick = {
//                        NavUtil
//                            .get()
//                            .navigation(RouteConfig.ROUTE_LOGIN)
//                    }
//                )
//                .constrainAs(txt) {
////                    top.linkTo(parent.top, margin = dp10)
////                    start.linkTo(parent.start, margin = dp10)
//
//                    centerVerticallyTo(parent)
//                    centerHorizontallyTo(parent)
//
//                })
//
//            Text(text = "main2", modifier = Modifier
//                .padding(dp10)
//                .selectable(
//                    selected = true,
//                    onClick = {
//                        NavUtil
//                            .get()
//                            .onBack()
//                        //NavUtil.get().navigation(RouteConfig.ROUTE_LOGIN)
//                    }
//                )
//                .constrainAs(txt2) {
//                    top.linkTo(txt.top, margin = dp100)
//                    start.linkTo(txt.start, margin = dp100)
//                },
//                color = RED,
//                fontSize = sp16
//            )
//        }
//    }

}

