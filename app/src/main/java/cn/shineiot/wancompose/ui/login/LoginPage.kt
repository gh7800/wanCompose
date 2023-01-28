package cn.shineiot.wancompose.ui.login

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cn.shineiot.wancompose.RouteConfig
import cn.shineiot.wancompose.route.NavUtil
import cn.shineiot.wancompose.ui.theme.*
import cn.shineiot.wancompose.utils.LogUtil

/**
 * login
 */
@Composable
fun LoginPage(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val viewState: LoginState = viewModel.viewStates
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    /**生命周期函数，第一次调用compose时执行*/
    LaunchedEffect(Unit) {
        LogUtil.e("LaunchedEffect")
        viewModel.viewEvents.collect {
            when (it) {
                is LoginEvent.Loading -> {
                    scaffoldState.snackbarHostState.showSnackbar("loading")
                }
                is LoginEvent.Error -> {
                    it.msg?.let { it1 -> scaffoldState.snackbarHostState.showSnackbar(message = it1) }
                }
                is LoginEvent.Success -> {
                    LogUtil.e("user ${it.user}")

                    scaffoldState.snackbarHostState.showSnackbar("success", duration = SnackbarDuration.Short)

                    NavUtil.get().navigation(RouteConfig.ROUTE_MAIN)
                }
            }
        }
    }

    /**生命周期函数，页面退出是执行onDispose()函数*/
    DisposableEffect(key1 = Unit, effect = {
        onDispose {
            LogUtil.e("DisposableEffect onDispose")
        }
    })

    /**compose每次执行都会调用此方法*/
    SideEffect {
        LogUtil.e("SideEffect")
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Login",
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth(1f),
                        color = BLACk
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        NavUtil.get().onBack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "", tint = Color.Black)
                    }
                },
                backgroundColor = Color.White,
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .background(WRITE),
                verticalArrangement = Arrangement.Center, //垂直居中
                horizontalAlignment = Alignment.CenterHorizontally, //水平居中
            ) {

                OutlinedTextField( //输入框样式
                    value = viewState.username,
                    placeholder = { Text(text = "请输入用户名..", color = Color.Gray) },
                    onValueChange = {
                        viewModel.dispatch(LoginAction.UpdateUserName(it))
                    },
                    label = { Text(text = "请输入用户名") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = viewState.password,
                    onValueChange = {
                        viewModel.dispatch(LoginAction.UpdatePassWord(it))
                    },
                    modifier = Modifier.padding(top = dp10),
                    placeholder = { Text(text = "请输入密码..") },
                    label = { Text(text = "请输入密码") },
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = {
                        NavUtil.get().navigation(RouteConfig.ROUTE_MAIN)
                    }),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),//输入类型

                )

                Button(
                    onClick = {
                        viewModel.dispatch(LoginAction.Login)
                    },
                    Modifier.padding(top = dp20)
                ) {
                    Text(
                        text = "登录",
                        Modifier
                            .padding(start = dp50, end = dp50)
                            .width(dp40),
                        fontSize = sp18
                    )
                }
            }
        }
    )

    @Composable
    fun SetTopAppBar() {
        TopAppBar(
            title = {
                Text(
                    text = "这是标题123",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(1f)
                )
            },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "", tint = Color.White)
                }
            },
            actions = {
                IconButton(onClick = { }) {
                    Icon(Icons.Filled.Share, contentDescription = "分享")
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Filled.Settings, contentDescription = "设置")
                }
            }
        )
    }

}


