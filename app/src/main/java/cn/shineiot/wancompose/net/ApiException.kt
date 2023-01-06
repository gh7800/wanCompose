package cn.shineiot.wancompose.net

class ApiException(var code: Boolean, override var message: String) : RuntimeException()