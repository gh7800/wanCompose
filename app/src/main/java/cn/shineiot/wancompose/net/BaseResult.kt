package cn.shineiot.wancompose.net

data class BaseResult<T>(
     var data: T,
     var errorMsg: String,
     var errorCode: Int
) {
    fun apiData(): T {
        if (errorCode == 0) {
            return data
        } else {
            throw ApiException(false, errorMsg) //错误，抛出异常
        }
    }
}