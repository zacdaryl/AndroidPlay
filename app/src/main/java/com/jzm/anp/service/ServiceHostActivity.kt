package com.jzm.anp.service

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.jzm.anp.R
import kotlinx.android.synthetic.main.activity_service_host.*

class ServiceHostActivity : AppCompatActivity() {
    private val tag = "ServiceHostActivity"

    private var mBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_host)

        start_btn.setOnClickListener {
            startService(Intent(this, LogService::class.java))
        }

        bind_btn.setOnClickListener {
            bindService(Intent(this, LogService::class.java), mConnection, BIND_AUTO_CREATE)
        }

        unbind_btn.setOnClickListener {
            if (mBound) {
                unbindService(mConnection)
                mBound = false
            }
        }
    }

    private val mConnection = object: ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(tag, "onServiceDisconnected")
            mBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(tag, "onServiceConnected")
            mBound = true
            (service as MyBinder).test()
        }

    }

    override fun onDestroy() {
        Log.d(tag, "onDestroy")

        if (mBound) {
            unbindService(mConnection)
            mBound = false
        }

        stopService(Intent(this, LogService::class.java))

        super.onDestroy()
    }
}