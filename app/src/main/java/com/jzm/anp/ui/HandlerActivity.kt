package com.jzm.anp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.jzm.anp.R
import kotlinx.android.synthetic.main.activity_handler.*
import kotlinx.android.synthetic.main.main_fragment.*

class HandlerActivity : AppCompatActivity() {
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler)

        handler = Handler {
            when(it.what) {
                1 -> {
                    handelr_text.text = it.obj as String
                }
                2 -> {
                    handelr_text.text = it.obj as String
                }
            }

            return@Handler false
        }

        start_btn.setOnClickListener {
            startThread()
        }
    }

    private fun startThread() {
        Thread(Runnable {
            Thread.sleep(1000)
            var msg = handler.obtainMessage()
            msg.what = 1
            msg.obj = "hello"

            handler.sendMessage(msg)
        }).start()

        Thread(Runnable {
            Thread.sleep(2000)
            var msg = handler.obtainMessage()
            msg.what = 2
            msg.obj = "world"

            handler.sendMessage(msg)

        }).start()
    }
}