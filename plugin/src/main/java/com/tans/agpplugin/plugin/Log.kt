package com.tans.agpplugin.plugin

object Log {
    fun d(tag: String = DEFAULT_TAG, msg: String) {
        println("[$tag] [D]: $msg")
    }

    fun e(tag: String = DEFAULT_TAG, msg: String) {
        println("[$tag] [E]: $msg")
    }

    const val DEFAULT_TAG = "TansPlugin"
}