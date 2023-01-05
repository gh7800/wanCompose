package cn.shineiot.wancompose.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import cn.shineiot.wancompose.RouteConfig
import cn.shineiot.wancompose.route.NavUtil
import cn.shineiot.wancompose.ui.theme.*
import cn.shineiot.wancompose.utils.LogUtil
import javax.inject.Inject

/**
 * login
 */
@Composable
fun LoginPage(
    viewModel: LoginViewModel = LoginViewModel()
) {
    
    val viewState = viewModel.viewStates

    val scaffoldState: ScaffoldState = rememberScaffoldState()

    LaunchedEffect(Unit) {
        viewModel.viewEvents.collect {
            if (it is LoginEvent.Error) {
                LogUtil.e("error--${it.msg}")

                scaffoldState.snackbarHostState.showSnackbar(message = it.msg)
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .background(F2)
                    .padding(start = dp10, end = dp20),
                verticalArrangement = Arrangement.Center, //垂直居中
                horizontalAlignment = Alignment.CenterHorizontally //水平居中
            ) {

                OutlinedTextField( //输入框样式
                    value = viewState.username,
                    placeholder = { Text(text = "请输入用户名..") },
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

                        //NavUtil.get().navigation(RouteConfig.ROUTE_MAIN)
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
        })
}

@Preview
@Composable
fun LoginPageView() {
    LoginPage()
}

