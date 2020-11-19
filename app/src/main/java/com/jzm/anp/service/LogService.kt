package com.jzm.anp.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LogService : Service(){
    private val tag = "LogService"
    private lateinit var fixedThreadPool: ExecutorService
    private lateinit var mBinder: MyBinder


    override fun onCreate() {
        super.onCreate()
        Log.d(tag, "onCreate")

        fixedThreadPool = Executors.newFixedThreadPool(6)
        mBinder = MyBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(tag, "onStartCommand")

        runTask()

        Log.d(tag, "onStartCommand before return")

        return START_REDELIVER_INTENT
//        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(tag, "onBind")
        runTask()
        return mBinder
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d(tag, "onRebind")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(tag, "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d(tag, "onDestroy")
        fixedThreadPool.shutdownNow()
        super.onDestroy()
    }

    private fun runTask() {
        fixedThreadPool.execute {
            while (true) {

                //如果线程被中断，则退出循环
//                if (Thread.currentThread().isInterrupted) {
//                    break
//                }

                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    Log.d(tag, "thread: ${Thread.currentThread().name} is interrupted")
                    break
                }

                Log.d(tag, "log from LogService, thread: ${Thread.currentThread().name}")
            }
        }
    }
}

class MyBinder : Binder() {
    fun test() {
        Log.d("binder", "MyBinder test")
    }
}