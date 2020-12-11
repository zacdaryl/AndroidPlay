package com.jzm.anp.launchmode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.jzm.anp.R
import kotlinx.android.synthetic.main.activity_b.*

class ActivityB : LaunchModeBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)

        start_btn_b.setOnClickListener {
//            startActivity(Intent(this, ActivityC::class.java).apply {
//                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            })
            startActivity(Intent(this, ActivityA::class.java))
        }

        //测试AlertDialog和Toast对Activity生命周期的影响
        alert_btn_b.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setMessage("hell world!")
                setTitle("Alert")
            }.create().show()

            Toast.makeText(this, "hello!", Toast.LENGTH_LONG).show()
        }

//        tasks()
    }
}