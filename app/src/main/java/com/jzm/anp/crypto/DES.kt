package com.jzm.anp.crypto

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

class DES {
    companion object {
        private const val transformation = "DES/ECB/PKCS5Padding"

        fun encrypt(key: String, plainText: String): String {
            val keySpec = SecretKeySpec(key.toByteArray(), "DES")
            val cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.ENCRYPT_MODE, keySpec)

            val cipherByteArray = cipher.doFinal(plainText.toByteArray())
            val base64ByteArray = Base64.encode(cipherByteArray, Base64.DEFAULT)

            return String(base64ByteArray)
        }

        fun decrypt(key: String, cipherText: String): String{
            val keySpec = SecretKeySpec(key.toByteArray(), "DES")
            val cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.DECRYPT_MODE, keySpec)

            val base64DecodeArray = Base64.decode(cipherText, Base64.DEFAULT)
            val plainByteArray = cipher.doFinal(base64DecodeArray)

            return String(plainByteArray)
        }

        fun randomKey(): String{
            val alphanumeric = ('0'..'9') + ('a'..'z') + ('A'..'Z')
            return List(8) { Random.nextInt(alphanumeric.size) }
                .map { alphanumeric[it] }
                .joinToString(separator = "")
        }
    }
}