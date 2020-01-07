package com.jzm.anp.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.jzm.anp.R
import com.jzm.anp.crypto.Base64Activity
import com.jzm.anp.crypto.DESActivity
import com.jzm.anp.crypto.RSAActivity
import com.jzm.anp.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    val TAG = "MainFragment"

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var button: Button
    private lateinit var textView: TextView

    private lateinit var telephonyManager: TelephonyManager
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.fragment = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        button = activity?.findViewById(R.id.identifier)!!
        textView = activity?.findViewById(R.id.message)!!
        telephonyManager = activity?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        button.setOnClickListener {
            if (activity?.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), 1)
            } else {
                textView.text = getIdentifer()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    textView.text = getIdentifer()
                } else {
                    Toast.makeText(context, "没有获取设备id的权限", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    fun onBase64BtnClick() {
        Log.d(TAG, "base64 button clicked")
        startActivity(Intent(activity, Base64Activity::class.java))
    }

    fun onDesBtnClick() {
        startActivity(Intent(activity, DESActivity::class.java))
    }

    fun onRsaBtnClick() {
        startActivity(Intent(activity, RSAActivity::class.java))
    }

    @SuppressLint("MissingPermission", "NewApi")
    fun getIdentifer(): String {
        var identifier = ""

        when {
            //低于8.0的设备
            Build.VERSION.SDK_INT < Build.VERSION_CODES.O -> {
                identifier = telephonyManager.deviceId
            }
            //8.0 ~ 10.0 的设备
            Build.VERSION.SDK_INT < Build.VERSION_CODES.Q -> {
                identifier = telephonyManager.imei
            }
            //10.0 设备，使用AndroidId
            else -> {
                identifier =  Settings.System.getString(activity?.contentResolver, Settings.Secure.ANDROID_ID)
            }
        }

        return identifier
    }
}
