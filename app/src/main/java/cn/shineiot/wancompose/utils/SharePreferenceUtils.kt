package cn.shineiot.wancompose.utils

import android.content.Context
import android.content.SharedPreferences
import cn.shineiot.wancompose.IApp

/**
 * SharePreference
 */
class SharePreferenceUtils {

    private val name = "COOKIE_wanCompose"
    private val instance: SharedPreferences = IApp.CONTEXT.getSharedPreferences(name, Context.MODE_PRIVATE)


    companion object {
        val default: SharePreferenceUtils by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            SharePreferenceUtils()
        }
    }

    /**
     * 获取存放数据
     * @return 值
     */
    fun getValue(key: String, default: Any): Any? = with(instance) {

        return when (default) {
            is Int -> getInt(key, default)
            is String -> getString(key, default)
            is Long -> getLong(key, default)
            is Float -> getFloat(key, default)
            is Boolean -> getBoolean(key, default)
            else -> throw IllegalArgumentException("SharedPreferences 类型错误")
        }
    }

    fun getString(key: String, default: String = ""): String {
        return getValue(key, default) as String
    }

    fun getInt(key: String, default: Int = 0): Int {
        return getValue(key, default) as Int
    }

    fun getLong(key: String, default: Long = 0): Long {
        return getValue(key, default) as Long
    }

    fun getBoolean(key: String, default: Boolean = false): Boolean {
        return getValue(key, default) as Boolean
    }

    fun getFloat(key: String, default: Float = 0f): Float {
        return getValue(key, default) as Float
    }

    /**
     * 存放SharedPreferences
     * @param key 键
     * @param value 值
     */
    fun saveValue(key: String, value: Any) = with(instance.edit()) {
        when (value) {
            is Long -> putLong(key, value)
            is Int -> putInt(key, value)
            is String -> putString(key, value)
            is Float -> putFloat(key, value)
            is Boolean -> putBoolean(key, value)
            else -> throw IllegalArgumentException("SharedPreferences 类型错误")
        }.apply()
    }

    /**
     * 是否包含
     */
    fun contains(str : String) : Boolean{
        return instance.contains(str)
    }

    /**
     * 清除
     */
    fun clear() {
        instance.edit().clear().apply()
    }

    /**
     * 删除某Key的值
     */
    fun remove(key: String) {
        instance.edit().remove(key).apply()
    }
}