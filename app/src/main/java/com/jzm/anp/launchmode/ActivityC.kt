package com.jzm.anp.launchmode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jzm.anp.R
import kotlinx.android.synthetic.main.activity_a.*
import kotlinx.android.synthetic.main.activity_c.*

class ActivityC : LaunchModeBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        start_btn_c.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            startActivity(intent)
        }

        tasks()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        tasks()
    }
}