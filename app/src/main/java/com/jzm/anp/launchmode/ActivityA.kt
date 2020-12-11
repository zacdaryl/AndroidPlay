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
//            startActivity(Intent(this, ActivityB::class.java))
            Intent(this, ActivityB::class.java)
                .apply {
//                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                .also {
    //                startActivity(it)
                    //使用application启动Activity, 查看task变化
                    application.startActivity(it)
                }
        }
    }
}