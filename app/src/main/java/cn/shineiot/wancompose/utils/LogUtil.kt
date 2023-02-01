package cn.shineiot.wancompose.utils

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * @Description
 * @Author : GF63
 * @Date : 2023/1/5
 */
object LogUtil {
    private const val tag = "tag"
    private const val HTTP = "HTTP"

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

    private val LINE_SEPARATOR: String = System.getProperty("line.separator") as String

    private fun printLine(tag: String?, isTop: Boolean) {
        if (isTop) {
            Log.e(
                tag,
                "╔═══════════════════════════════════════════════════════════════════════════════════════"
            )
        } else {
            Log.e(
                tag,
                "╚═══════════════════════════════════════════════════════════════════════════════════════"
            )
        }
    }

    fun printJson(msg: String) {
        val message: String = try {
            if (msg.startsWith("{")) {
                val jsonObject = JSONObject(msg)
                jsonObject.toString(4) //最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
            } else if (msg.startsWith("[")) {
                val jsonArray = JSONArray(msg)
                jsonArray.toString(4)
            } else {
                msg
            }
        } catch (e: JSONException) {
            msg
        }
        //printLine(tag, true)
        //message = "HTTP Header$LINE_SEPARATOR$message"
        val lines = message.split(LINE_SEPARATOR).toTypedArray()

        val stringBuilder = java.lang.StringBuilder()
        for (line in lines) {
            //Log.e(HTTP, "║ $line")
            stringBuilder.append("$line \n")
        }
        Log.e(HTTP, stringBuilder.toString())

        //printLine(HTTP, false)
    }
}