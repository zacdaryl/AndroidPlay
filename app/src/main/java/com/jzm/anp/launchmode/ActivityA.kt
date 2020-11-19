package com.jzm.anp.launchmode

import android.content.Intent
import android.os.Bundle
import com.jzm.anp.R
import kotlinx.android.synthetic.main.activity_a.*

class ActivityA : LaunchModeBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)

        start_btn_a.setOnClickListener {
            startActivity(Intent(this, ActivityB::class.java))
        }
    }
}