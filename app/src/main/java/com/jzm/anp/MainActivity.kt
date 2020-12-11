package com.jzm.anp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.jzm.anp.ui.main.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        var handler = MyHandler()
        handler.sendEmptyMessage(1)
    }

    class MyHandler: Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            println("handle message")
        }
    }

}
