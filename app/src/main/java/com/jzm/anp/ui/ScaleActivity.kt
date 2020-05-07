package com.jzm.anp.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import com.jzm.anp.R

import kotlinx.android.synthetic.main.activity_scale.*

class ScaleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.clear)
        val scaled = Bitmap.createScaledBitmap(bitmap, 40, 40, true)
        val bitmapDrawable = BitmapDrawable(resources, scaled)
        bitmap.recycle()
//        scaled.recycle()

        imageView.setImageDrawable(bitmapDrawable)
    }

}
