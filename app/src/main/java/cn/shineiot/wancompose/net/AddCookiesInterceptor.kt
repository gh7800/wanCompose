package cn.shineiot.wancompose.net

import android.content.Context
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
class AddCookiesInterceptor(context: Context) : Interceptor {
    private val mContext: Context

    init {
        mContext = context
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val builder: Request.Builder = request.newBuilder()
        val cookie = getCookie(request.url.toString(), request.url.host)
        if (!TextUtils.isEmpty(cookie)) {
            if (cookie != null) {
                builder.addHeader("Cookie", cookie)
            }
        }
        return chain.proceed(builder.build())
    }

    private fun getCookie(url: String, domain: String): String? {
        //val sp: SharedPreferences = mContext.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE)
        if (!TextUtils.isEmpty(url) && SharePreferenceUtils.default.contains(url) && !TextUtils.isEmpty(
                SharePreferenceUtils.default.getString(
                    url,
                    ""
                )
            )
        ) {
            return SharePreferenceUtils.default.getString(url, "")
        }
        return if (!TextUtils.isEmpty(domain) && SharePreferenceUtils.default.contains(domain) && !TextUtils.isEmpty(
                SharePreferenceUtils.default.getString(
                    domain,
                    ""
                )
            )
        ) {
            SharePreferenceUtils.default.getString(domain, "")
        } else null
    }

}