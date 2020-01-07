package com.jzm.anp.crypto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.jzm.anp.R
import com.jzm.anp.databinding.ActivityRsaBinding

class RSAActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRsaBinding
    private lateinit var keyPair: Pair<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_rsa)
        mBinding.activity = this
        keyPair = RSA.generateKeyPair()
    }

    fun onEncryptBtnClicked() {
        val input = mBinding.editText.text.toString()
        println(keyPair)

        mBinding.resultText.text = RSA.encrypt(keyPair.second, input)
        printResult("encrypt")
    }

    fun onDecryptBtnClicked() {
        val input = mBinding.resultText.text.toString()

        mBinding.resultText.text = RSA.decrypt(keyPair.first, input)
        printResult("decrypt")
    }

    fun onSignBtnClicked() {
        val input = mBinding.editText.text.toString()

        mBinding.resultText.text = RSA.sign(keyPair.first, input)
        printResult("sign")
    }

    fun onVerifyBtnClicked() {
        val input = mBinding.editText.text.toString()
        val sign = mBinding.resultText.text.toString()

        mBinding.resultText.text = RSA.verify(keyPair.second, sign, input).toString()
        printResult("verify")

    }

    private fun printResult(type: String) {
        println("$type : ${mBinding.resultText.text}")
    }
}
