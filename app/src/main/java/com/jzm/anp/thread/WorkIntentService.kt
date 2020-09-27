package com.jzm.anp.thread

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import kotlin.random.Random

class WorkIntentService : IntentService("work intent service") {

    override fun onHandleIntent(intent: Intent?) {
        println("\n onHandleIntent, start")
        println("current thread: " + Thread.currentThread().name)

        val millis = Random.nextLong(6000)
        println("sleep $millis millis")
        Thread.sleep(millis)

        val extra = intent?.getStringExtra("extra")
        println("process in intent service \n $extra")
        val index = extra?.substring(extra.length - 1)

        val wrr = intent?.getParcelableExtra<ResultReceiver>("rr")
        wrr?.send(1, Bundle().apply {
            putString("result", "result from intent service: $index")
        })
    }
}