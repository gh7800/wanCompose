package cn.shineiot.wancompose.ui.main

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cn.shineiot.wancompose.RouteConfig
import cn.shineiot.wancompose.ui.main.home.HomePage
import cn.shineiot.wancompose.ui.main.profile.ProfilePage

/**
 * main 主页
 */
@Preview
@Composable
fun MainPage(
    navController: NavHostController = rememberNavController()
) {
    val bottomNumber = listOf("首页", "我的")
    val bottomIcon = listOf(Icons.Default.Home, Icons.Default.Email)
    val bottomNavPages = listOf(RouteConfig.ROUTE_HOME,RouteConfig.ROUTE_PROFILE)

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
                        label = { Text(text = content)},
                        icon = {
                            if (checkIndex == index) {
                                BadgedBox(badge = { Text(text = "3+") }) {
                                    Icon(imageVector = bottomIcon[index], contentDescription = null)
                                }
                            } else {
                                Icon(imageVector = bottomIcon[index], contentDescription = null)
                            }
                        },
                        selectedContentColor = Color.Green,
                        unselectedContentColor = Color.White,
                        onClick = {
                            checkIndex = index
                            navController.navigate(bottomNavPages[checkIndex]){
                                navController.graph.startDestinationRoute?.let {
                                    popUpTo(it){
                                        saveState = true
                                    }
                                }
                                /*popUpTo(navController.graph.findStartDestination().id){
                                    saveState = true
                                }*/
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        alwaysShowLabel = true
                    )
                }
            }
        },
        content = {
            //NavContent(bottomNavPages[checkIndex])
            NavHost(navController = navController, startDestination = bottomNavPages[checkIndex]){
                composable(RouteConfig.ROUTE_HOME){ HomePage()}
                composable(RouteConfig.ROUTE_PROFILE){ ProfilePage() }
            }
        }
    )

}

