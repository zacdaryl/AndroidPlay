package com.jzm.anp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import com.jzm.anp.R
import kotlinx.android.synthetic.main.activity_unicode.*

class UnicodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unicode)

        input.addTextChangedListener { text ->
            val s = text.toString()
            if(s.isEmpty()) return@addTextChangedListener

            val ci = s[0].toInt()
            val unicode = Integer.toHexString(ci).let {
                if (it.length == 2) "U+00$it" else "U+$it"
            }

            text_view.text = unicode
        }
    }
}