package com.jzm.anp.crypto

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jzm.anp.R
import com.jzm.anp.databinding.ActivityDesBinding
import kotlin.random.Random


class DESActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityDesBinding
    private lateinit var desKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_des)
        mBinding.activity = this
    }

    fun onEncodeBtnClicked() {
        hideKeyboard()

        val inputText = mBinding.editText.text.toString()
        println("clear text: $inputText")

//        desKey = AES.randomKey()
        desKey = AES.KEY_USER
        println("des key: $desKey")

        val cipherText = AES.encrypt(desKey, inputText)


        mBinding.base64Text.text = cipherText
        println("des encrypted text: $cipherText")
    }

    fun onDecodeBtnClicked() {
        val input = mBinding.base64Text.text.toString()
        println("encrypted text: $input")

        println("des key: $desKey")

        val plainText = AES.decrypt(desKey, input)

        mBinding.base64Text.text = plainText
        println("decrypted text: $plainText")

    }

    @SuppressLint("ServiceCast")
    fun hideKeyboard() {
        val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun generateKey(): String {
        val alphanumeric = ('0'..'9') + ('A'..'Z') + ('a'..'z')
        return List(8) { Random.nextInt(alphanumeric.size) }
            .map { alphanumeric[it] }
            .joinToString(separator = "")
    }
}
