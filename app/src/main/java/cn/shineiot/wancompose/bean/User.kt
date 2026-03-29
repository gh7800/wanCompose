package cn.shineiot.wancompose.bean

/**
 * @Description
 * @Author : GF63
 * @Date : 2023/1/6
 */
data class User(
    val id : Int ,
    val admin : Boolean,
    val publicName : String,
    val realname : String,
    val username : String,
    val token : String,
    val uuid : String,
    val company_uuid : String,
    val department_uuid : String,
)
