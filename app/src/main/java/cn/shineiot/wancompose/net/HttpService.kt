package cn.shineiot.wancompose.net

import cn.shineiot.wancompose.bean.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @Description
 * @Author : GF63
 * @Date : 2023/1/6
 */
interface HttpService {
    companion object{
        private const val BaseUrl : String = "https://www.wanandroid.com/"

        fun getBaseUrl() : String{
            return BaseUrl
        }
    }

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username")username : String,@Field("password")password : String) : BaseResult<User>
}