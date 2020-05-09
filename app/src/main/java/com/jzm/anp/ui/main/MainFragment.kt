package com.jzm.anp.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
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
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.jzm.anp.R
import com.jzm.anp.crypto.Base64Activity
import com.jzm.anp.crypto.DESActivity
import com.jzm.anp.crypto.RSAActivity
import com.jzm.anp.databinding.MainFragmentBinding
import com.jzm.anp.ui.ScaleActivity
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterActivityLaunchConfigs
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.plugin.common.MethodChannel
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    val TAG = "MainFragment"

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var identityBtn: Button
    private lateinit var textView: TextView

    private lateinit var telephonyManager: TelephonyManager
    private lateinit var binding: MainFragmentBinding

    private val REQUEST_CODE_STATE = 1
    private val REQUEST_CODE_STORAGE = 2
    private val REQUEST_CODE_LOCATION = 3

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
        identityBtn = activity?.findViewById(R.id.identifier)!!
        textView = activity?.findViewById(R.id.message)!!
        telephonyManager = activity?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        identityBtn.setOnClickListener {
            if (activity?.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_PHONE_STATE),
                    REQUEST_CODE_STATE
                )
            } else {
                textView.text = getIdentifer()
            }
        }

        val engine = FlutterEngineCache.getInstance().get("app_engine_id")

        engine?.let {
            MethodChannel(
                it.dartExecutor.binaryMessenger,
                "com.jzm.anp.flutter/version"
            ).setMethodCallHandler { call, result ->
                if (call.method == "getVersionName") {
                    result.success("1.0.0")
                } else {
                    result.notImplemented()
                }
            }
        }


        flutter_btn.setOnClickListener {
            startActivity(
                this.context?.let { it1 ->
                    FlutterActivity
                        .withCachedEngine("app_engine_id")
                        .build(it1)
                }
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_STATE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    textView.text = getIdentifer()
                } else {
                    Toast.makeText(context, "没有获取设备id的权限", Toast.LENGTH_SHORT).show()
                }
            }
            REQUEST_CODE_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "恭喜！已获得存储权限！", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "没有获得存储权限！", Toast.LENGTH_SHORT).show()
                }
            }
            REQUEST_CODE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "恭喜！已获得位置权限！", Toast.LENGTH_SHORT).show()
                } else {
                    val builder: AlertDialog.Builder? = activity?.let {
                        AlertDialog.Builder(it)
                    }

                    builder?.setMessage(
                        "权限开启后在以下场景会使用该权限：\n" +
                                "-A业务\n" +
                                "-B业务某某场景\n" +
                                "-获取所在城市"
                    )
                        ?.setTitle("请求位置访问权限")
                        ?.setCancelable(false)
                    builder?.apply {
                        setPositiveButton("同意") { dialog, which ->

                        }
                        setNegativeButton("不同意") { dialog, which ->

                        }
                    }

                    val dialog: AlertDialog? = builder?.create()
                    dialog?.show()
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

    fun onStorageBtnClick() {
        if (checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_STORAGE
            )
        } else {
            Toast.makeText(context, "您已经获得存储权限！", Toast.LENGTH_SHORT).show()
        }
    }

    fun onLocationBtnClick() {
//        if (checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
//            != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
//        } else {
//            Toast.makeText(context, "您已经获得位置权限！", Toast.LENGTH_SHORT).show()
//        }
        if (checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        } else {
            Toast.makeText(context, "您已经获得位置权限！", Toast.LENGTH_SHORT).show()
        }
    }

    fun onScaleBtnClick() {
        startActivity(Intent(context, ScaleActivity::class.java))
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
                identifier =
                    Settings.System.getString(activity?.contentResolver, Settings.Secure.ANDROID_ID)
            }
        }

        return identifier
    }
}
