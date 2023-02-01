package cn.shineiot.wancompose.utils

import android.view.Gravity
import android.widget.Toast
import cn.shineiot.wancompose.IApp

/**
 * android 11 之后只能用默认的Toast
 * @author gf63
 */
object ToastUtil {
    private var toast: Toast? = null

    /**
     * 默认居下显示
     */
    fun show(msg: String?, duration: Int = Toast.LENGTH_SHORT) {
        msg?.let {
            toast = Toast.makeText(IApp.CONTEXT, it, duration)
            toast?.show()
        }
    }

    /**
     * 默认居下显示
     */
    fun showToast(msg: String?, duration: Int = Toast.LENGTH_SHORT) {
        if (msg?.isEmpty() == true) return
        show(msg, duration)
    }

    /**居中显示*/
    fun showCenter(msg: String?, duration: Int = Toast.LENGTH_SHORT) {
        msg?.let {
            toast = Toast.makeText(IApp.CONTEXT, msg, duration)

            toast?.let {
                it.setGravity(Gravity.CENTER, 0, 0)
                it.show()
            }
        }
    }

    /**居下显示*/
    fun showBottom(msg: String?, duration: Int = Toast.LENGTH_SHORT) {
        msg?.let {
            toast = Toast.makeText(IApp.CONTEXT, msg, duration)

            toast?.let {
                it.setGravity(Gravity.BOTTOM, 0, 0)
                it.show()
            }
        }
    }

    /**居上显示*/
    fun showTop(msg: String?, duration: Int = Toast.LENGTH_SHORT) {
        msg?.let {
            toast = Toast.makeText(IApp.CONTEXT, msg, duration)

            toast?.let {
                it.setGravity(Gravity.TOP, 0, 0)
                it.show()
            }
        }
    }

    /**
     * 取消toast
     */
    fun cancel() {
        toast?.cancel()
        toast = null
    }

}