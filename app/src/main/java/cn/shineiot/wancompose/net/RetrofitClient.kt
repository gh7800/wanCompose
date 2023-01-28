package cn.shineiot.wancompose.net

import android.text.TextUtils
import cn.shineiot.wancompose.IApp
import cn.shineiot.wancompose.utils.LogUtil
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private var token: String? = null

    //添加头部信息
    private val headerInterceptor: Interceptor = Interceptor {
        //token = SharePreferenceUtils.default.getString(ConstantUtil.TOKEN)
        token = "Bearer $token"

        val build = it.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", token ?: "")
            .addHeader("Type", "Android")
            .build()
        return@Interceptor it.proceed(build)
    }

    //httpLog
    private fun logInterceptor(): HttpLoggingInterceptor {
        val httpLogInterceptor = HttpLoggingInterceptor {
            LogUtil.printJson(it)
        }
        httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLogInterceptor
    }

    //重连
    private val retryConnectInterceptor: Interceptor = Interceptor {
        var retryNum = 0
        val request = it.request()
        var response = it.proceed(request)

        while (!response.isSuccessful && retryNum < 5) {
            retryNum++
            try {
                response = it.proceed(request)
            } catch (e: Exception) {
                e.stackTrace
            }
        }
        return@Interceptor response
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AddCookiesInterceptor(IApp.CONTEXT))
        .addInterceptor(SaveCookiesInterceptor(IApp.CONTEXT))
        .addInterceptor(logInterceptor())
        .addInterceptor(headerInterceptor)
        .addInterceptor(retryConnectInterceptor)
        //.addNetworkInterceptor(StethoInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(HttpService.getBaseUrl())
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
//        .addConverterFactory(MoshiConverterFactory.create(moShi)) //moshi
        .build()

    val httpService: HttpService by lazy {
        retrofit.create(HttpService::class.java)
    }

    fun getRetrofitPart(file: File): MultipartBody.Part {
        val requestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData("file", file.name, requestBody)
    }

    fun getPartMap(list: MutableList<String>): List<MultipartBody.Part> {
        val listPart : MutableList<MultipartBody.Part> = arrayListOf()

        list.map {
            if(!TextUtils.isEmpty(it)) {
                val file = File(it)
                val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val part = MultipartBody.Part.createFormData("files[]", file.name, requestBody)

                listPart.add(part)
            }
        }
        return listPart
    }

}