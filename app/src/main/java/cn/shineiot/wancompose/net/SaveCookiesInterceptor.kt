package cn.shineiot.wancompose.net

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import cn.shineiot.wancompose.utils.SharePreferenceUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


/**
 * @Description
 * @Author : GF63
 * @Date : 2023/1/28
 */
open class SaveCookiesInterceptor constructor(context: Context) : Interceptor {
    private val mContext: Context

    init {
        mContext = context
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response: Response = chain.proceed(request)
        //set-cookie可能为多个
        if (response.headers("set-cookie").isNotEmpty()) {
            val cookies: List<String> = response.headers("set-cookie")
            val cookie = encodeCookie(cookies)
            saveCookie(request.url.toString(), request.url.host, cookie)
        }
        return response
    }

    /**
     * 整合cookie为唯一字符串
     */
    private fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set: MutableSet<String> = HashSet()
        for (cookie in cookies) {
            val arr = cookie.split(";").toTypedArray()
            for (s in arr) {
                if (set.contains(s)) {
                    continue
                }
                set.add(s)
            }
        }
        for (cookie in set) {
            sb.append(cookie).append(";")
        }
        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }
        return sb.toString()
    }

    /**
     * 保存cookie到本地，这里我们分别为该url和host设置相同的cookie，其中host可选
     * 这样能使得该cookie的应用范围更广
     */
    private fun saveCookie(url: String, domain: String, cookies: String) {
        //val sp: SharedPreferences = mContext.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE)
        //val editor = sp.edit()
        if (TextUtils.isEmpty(url)) {
            throw NullPointerException("url is null.")
        } else {
            SharePreferenceUtils.default.saveValue(url,cookies)
            //editor.putString(url, cookies)
        }
        if (!TextUtils.isEmpty(domain)) {
            SharePreferenceUtils.default.saveValue(domain,cookies)
            //editor.putString(domain, cookies)
        }
        //editor.apply()
    }

}