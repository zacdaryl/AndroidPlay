package com.jzm.anp.crypto

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

class AES {
    companion object {
        private const val transformation = "AES/ECB/PKCS5Padding"
        const val KEY_USER = "Cxex10gx7k9qrFB34q7A1zaozMNV2e3t"

        fun encrypt(key: String, plainText: String): String {
            val secretKey = secretKey(key)
            val keySpec = SecretKeySpec(secretKey.encoded, "AES")
            val cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.ENCRYPT_MODE, keySpec)

            val cipherByteArray = cipher.doFinal(plainText.toByteArray())
            return encodeToString(cipherByteArray)
        }

        fun decrypt(key: String, cipherText: String): String{
            val secretKey = secretKey(key)
            val keySpec = SecretKeySpec(secretKey.encoded, "AES")
            val cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.DECRYPT_MODE, keySpec)

            val base64DecodeArray = Base64.decode(cipherText, Base64.NO_WRAP)
            val plainByteArray = cipher.doFinal(base64DecodeArray)

            return String(plainByteArray)
        }

        fun randomKey(): String{
            val alphanumeric = ('0'..'9') + ('a'..'z') + ('A'..'Z')
            return List(32) { Random.nextInt(alphanumeric.size) }
                .map { alphanumeric[it] }
                .joinToString(separator = "")
        }

        private fun secretKey(key: String): SecretKey {
            val keyGenerator = KeyGenerator.getInstance("AES")
            val secureRandom = SecureRandom.getInstance("SHA1PRNG")
            secureRandom.setSeed(key.toByteArray())
            keyGenerator.init(128, secureRandom)
            return keyGenerator.generateKey()
        }

        private fun encodeToString(byteArray: ByteArray): String {
            return String(Base64.encode(byteArray, Base64.NO_WRAP))
        }
    }
}