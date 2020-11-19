package com.jzm.anp.launchmode

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jzm.anp.R
import kotlinx.android.synthetic.main.activity_c.*
import kotlinx.android.synthetic.main.activity_d.*

class ActivityD : LaunchModeBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d)

        start_btn_d.setOnClickListener {
            startActivity(Intent(this, ActivityC::class.java))
        }

        tasks()
    }
}