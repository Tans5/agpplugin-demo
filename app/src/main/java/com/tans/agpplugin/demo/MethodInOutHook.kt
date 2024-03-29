package com.tans.agpplugin.demo

import android.util.Log
import androidx.annotation.Keep

/**
 * For hook invoke.
 */
@Keep
object MethodInOutHook {

    @JvmStatic
    fun methodIn() {
        val currentThread = Thread.currentThread()
        val method = currentThread.stackTrace[3]
        Log.d(TAG, "MethodIn Thread=${currentThread.name}, Class=${method.className}, Method=${method.methodName}")
    }

    @JvmStatic
    fun methodOut() {
        val currentThread = Thread.currentThread()
        val method = currentThread.stackTrace[3]
        Log.d(TAG, "MethodOut Thread=${currentThread.name}, Class=${method.className}, Method=${method.methodName}")
    }

    private const val TAG = "MethodInOutHook"
}