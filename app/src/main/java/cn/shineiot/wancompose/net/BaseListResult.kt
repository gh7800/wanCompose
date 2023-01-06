package cn.shineiot.wancompose.net

data class BaseListResult<T>(
    val datas: MutableList<T>,
    val errorMsg: String,
    val errorCode: Int,
    val curPage : Int,
    val total : Int,
    val pageCount : Int,
    val over : Boolean,
) {
    fun apiData(): MutableList<T> {
        if (errorCode == 0) {
            return datas
        } else {
            throw ApiException(false, errorMsg) //错误，抛出异常
        }
    }

}