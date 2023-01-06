package cn.shineiot.wancompose.utils

import android.util.Log

/**
 * @Description
 * @Author : GF63
 * @Date : 2023/1/5
 */
object LogUtil {
    private const val tag = "tag"

    private const val length = 3 * 1024  //Log最大打印 4　* 1024

    fun e(msg: Any?) {
        if (msg is String) {
            var message: String = msg

            if (message.length >= length) {
                while (message.length > length) {
                    val subMsg = message.substring(0, length)
                    Log.e(tag, subMsg)          //打印 3 * 1024
                    message = message.replace(subMsg, "")
                }
                Log.e(tag, message)     //打印剩余的
            } else {
                Log.e(tag, message)
            }

        } else {
            Log.e(tag, "$msg")
        }
    }
}