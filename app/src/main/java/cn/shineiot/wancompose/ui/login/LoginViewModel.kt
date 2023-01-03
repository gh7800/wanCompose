package cn.shineiot.wancompose.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

/**
 * @Description
 * @Author : GF63
 * @Date : 2023/1/3
 */
class LoginViewModel : ViewModel() {
    //channel 信道
    private val loginChannel = Channel<LoginIntent>(Channel.UNLIMITED)

    //状态管理
    val loginStatus by mutableStateOf("1")

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            loginChannel.consumeAsFlow().collect() {
                when (it) {
                    LoginIntent.Login -> login()
                }
            }
        }
    }

    private fun login() {
        viewModelScope.launch {

        }
    }
}