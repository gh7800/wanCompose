package cn.shineiot.wancompose.ui.login

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.shineiot.wancompose.net.RetrofitClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Description
 * @Author : GF63
 * @Date : 2023/1/3
 */
@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    var viewStates by mutableStateOf(LoginState())
        private set

    //channel 信道
    private val loginChannel = Channel<LoginEvent>(Channel.UNLIMITED)
    val viewEvents = loginChannel.receiveAsFlow()

    fun dispatch(action: LoginAction) {
        when (action) {
            is LoginAction.Login -> login()
            is LoginAction.UpdateUserName -> updateUserName(action.username)
            is LoginAction.UpdatePassWord -> updatePassWord(action.password)
        }
    }

    private fun updateUserName(username: String) {
        viewStates = viewStates.copy(username = username)
    }

    private fun updatePassWord(password: String) {
        viewStates = viewStates.copy(password = password)
    }

    //登录请求
    private fun login() {
        viewModelScope.launch {
            when {
                viewStates.username.isEmpty() -> loginChannel.send(LoginEvent.Error("请输入账号"))
                viewStates.password.isEmpty() -> loginChannel.send(LoginEvent.Error("请输入密码"))
                else -> {
                    viewModelScope.launch {
                        flow {
                            val result = RetrofitClient.httpService.login(
                                viewStates.username,
                                viewStates.password
                            )
                            emit(result)
                        }.map {
                            if (it.errorCode == 0) {
                                loginChannel.send(LoginEvent.Success)
                            } else {
                                throw Exception(it.errorMsg)
                            }
                        }.catch {
                            loginChannel.send(LoginEvent.Error(it.message))
                        }.collect()

                    }
                }
            }

        }
    }

}

/**
 * 状态
 */
data class LoginState(
    var username: String = "",
    var password: String = ""
)

/**
 * 一次性事件
 */
sealed class LoginEvent() {
    object Loading : LoginEvent()
    object Success : LoginEvent()
    data class Error(var msg: String?) : LoginEvent()
}

/**
 * View意图
 */
sealed class LoginAction {
    object Login : LoginAction()
    data class UpdateUserName(val username: String) : LoginAction()
    data class UpdatePassWord(val password: String) : LoginAction()
}
