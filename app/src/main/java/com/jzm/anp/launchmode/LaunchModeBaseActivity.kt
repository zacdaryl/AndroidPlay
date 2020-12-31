package com.jzm.anp.launchmode

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem

open class LaunchModeBaseActivity : AppCompatActivity() {
    val tag = "launchMode"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = localClassName.substringAfter('.')
        Log.d(tag, "$this -> onCreate")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "$this -> onResume")
        tasks()
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "$this -> onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "$this -> onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "$this -> onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(tag, "$this -> onSaveInstanceState")
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(tag, "$this -> onNewIntent")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "$this -> onDestroy")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun tasks() {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        activityManager.appTasks.forEach {apptask ->
            Log.d(tag, "------tasks------")
            Log.d(tag, apptask.taskInfo.baseActivity.toString())
            Log.d(tag, "${apptask.taskInfo.numActivities}")
            Log.d(tag, apptask.taskInfo.topActivity.toString())
            Log.d(tag, "------tasks end------")
        }
    }
}