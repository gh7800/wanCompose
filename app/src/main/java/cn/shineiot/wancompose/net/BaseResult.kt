package cn.shineiot.wancompose.net

data class BaseResult<T>(
    var data: T,
    var errorMsg: String,
    var errorCode: Int,
    var success: Boolean,
    var message : String
    ) {
    fun apiData(): T {
        if (success) {
            return data
        } else {
            throw ApiException(false, message) //错误，抛出异常
        }
    }
}