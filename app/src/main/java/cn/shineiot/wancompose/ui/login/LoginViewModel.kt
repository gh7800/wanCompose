package cn.shineiot.wancompose.ui.login

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.shineiot.wancompose.utils.LogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Description
 * @Author : GF63
 * @Date : 2023/1/3
 */
@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {
    var viewStates by mutableStateOf(LoginState())
        private set

    //channel 信道
    private val loginChannel = Channel<LoginEvent>(Channel.UNLIMITED)
    val viewEvents = loginChannel.receiveAsFlow()

    fun dispatch(action: LoginAction) {
        when (action) {
            is LoginAction.Login -> login()
            is LoginAction.UpdateUserName -> updateUserName(action.username)
            is LoginAction.UpdatePassWord ->updatePassWord(action.password)
        }
    }

    private fun updateUserName(username: String){
        viewStates = viewStates.copy(username = username)
        LogUtil.e("$viewStates")
    }

    private fun updatePassWord(password: String){
        viewStates = viewStates.copy(password = password)
    }

    //登录请求
    private fun login() {
        viewModelScope.launch {
            when {
                viewStates.username.isEmpty() -> loginChannel.send(LoginEvent.Error("请输入账号"))
                viewStates.password.isEmpty() -> loginChannel.send(LoginEvent.Error("请输入账号"))
                else -> {
                    viewModelScope.launch {
                        Log.e("tag", "$viewStates")
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
    data class Error(var msg: String) : LoginEvent()
}

/**
 * View意图
 */
sealed class LoginAction {
    object Login : LoginAction()
    data class UpdateUserName(val username : String) : LoginAction()
    data class UpdatePassWord(val password : String) : LoginAction()
}
