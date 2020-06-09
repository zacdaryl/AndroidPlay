package com.jzm.anp.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterActivityLaunchConfigs
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.StandardMessageCodec

class FlutterSubActivity: FlutterActivity() {
    private val TAG = "FlutterSubActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun getInitialRoute(): String {
        Log.d(TAG, "getInitialRoute")
        return super.getInitialRoute()
    }


    override fun provideFlutterEngine(context: Context): FlutterEngine? {
        Log.d(TAG, "provideFlutterEngine")
//        return FlutterEngineCache.getInstance().get("app_engine_id")
        return null
    }

    override fun getBackgroundMode(): FlutterActivityLaunchConfigs.BackgroundMode {
        return super.getBackgroundMode()
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        Log.d(TAG, "configureFlutterEngine")
        val bmc = BasicMessageChannel(flutterEngine.dartExecutor.binaryMessenger,
            "basic_msg_channel", StandardMessageCodec.INSTANCE)

        val map = HashMap<String, String>()
        map.put("a", "1")
        map.put("b", "2")

        bmc.send(map)

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")

        flutterEngine?.let {
            val bmc = BasicMessageChannel(it.dartExecutor.binaryMessenger,
                "basic_msg_channel", StandardMessageCodec.INSTANCE)

            val map = HashMap<String, String>()
            map.put("a", "1")
            map.put("b", "2")

            bmc.send(map)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}