package com.jzm.anp.crypto

import android.annotation.SuppressLint
import android.content.Context
import android.inputmethodservice.InputMethodService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.jzm.anp.R
import com.jzm.anp.databinding.ActivityBase64Binding

class Base64Activity : AppCompatActivity() {
    private lateinit var mBinding: ActivityBase64Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_base64)
        mBinding.activity = this
    }

    fun onEncodeBtnClicked() {
        hideKeyboard()

        val inputText = mBinding.editText.text.toString()

        val byteArray = Base64.encode(inputText.toByteArray(), Base64.DEFAULT)
        val base64Str = String(byteArray)

        mBinding.base64Text.text = base64Str
    }

    fun onDecodeBtnClicked() {
        val base64 = mBinding.base64Text.text.toString()
        if (base64.isEmpty()) return

        val result = Base64.decode(base64, Base64.DEFAULT)
        mBinding.base64Text.text = String(result)
    }

    @SuppressLint("ServiceCast")
    fun hideKeyboard() {
        val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
