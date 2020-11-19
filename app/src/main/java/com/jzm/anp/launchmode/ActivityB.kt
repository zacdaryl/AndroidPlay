package com.jzm.anp.launchmode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jzm.anp.R
import kotlinx.android.synthetic.main.activity_b.*

class ActivityB : LaunchModeBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)

        start_btn_b.setOnClickListener {
            startActivity(Intent(this, ActivityC::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }

        tasks()
    }
}