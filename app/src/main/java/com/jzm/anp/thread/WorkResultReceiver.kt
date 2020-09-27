package com.jzm.anp.thread

import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.widget.TextView
import java.lang.ref.WeakReference

class WorkResultReceiver(handler: Handler?) : ResultReceiver(handler) {

    lateinit var refView: WeakReference<TextView>

    fun setView(view: TextView) {
        refView = WeakReference(view)
    }


    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        super.onReceiveResult(resultCode, resultData)

        val result = resultData?.get("result") as String
        println("onReceiveResult, resultCode: $resultCode, resultData: $result")
        println("receiver thread: ${Thread.currentThread().name}")

        refView.get()?.text = result
    }
}