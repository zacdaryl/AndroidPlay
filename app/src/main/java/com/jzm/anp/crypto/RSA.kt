package com.jzm.anp.crypto

import android.util.Base64
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

class RSA {
    companion object {
        private const val transformation = "RSA/ECB/PKCS1Padding"
        private const val ALGORITHM = "RSA"
        private const val SIGN_ALGORITHM = "SHA1withRSA"

        // 公钥加密
        fun encrypt(publicKey: String, plainText: String): String {
            val byteArray = Base64.decode(publicKey, Base64.DEFAULT)
            val keyFactory = KeyFactory.getInstance(ALGORITHM)
            val keySpec = X509EncodedKeySpec(byteArray)
            val pubKey = keyFactory.generatePublic(keySpec) as PublicKey

            val cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.ENCRYPT_MODE, pubKey)

            val cipherByteArray = cipher.doFinal(plainText.toByteArray())
            val base64 = Base64.encode(cipherByteArray, Base64.DEFAULT)

            return String(base64)
        }

        // 私钥解密
        fun decrypt(privateKey: String, cipherText: String): String {
            val base64Buffer = Base64.decode(privateKey, Base64.DEFAULT)
            val keySpec = PKCS8EncodedKeySpec(base64Buffer)
            val keyFactory = KeyFactory.getInstance(ALGORITHM)
            val priKey = keyFactory.generatePrivate(keySpec) as PrivateKey

            val cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.DECRYPT_MODE, priKey)

            val rawByteArray = Base64.decode(cipherText.toByteArray(), Base64.DEFAULT)
            val plainBuffer = cipher.doFinal(rawByteArray)
            return String(plainBuffer)
        }

        // 公钥验证签名
        fun verify(publicKey: String, sign: String, data: String): Boolean {
            val byteArray = Base64.decode(publicKey, Base64.DEFAULT)
            val keyFactory = KeyFactory.getInstance(ALGORITHM)
            val keySpec = X509EncodedKeySpec(byteArray)
            val pubKey = keyFactory.generatePublic(keySpec) as PublicKey

            val signature = Signature.getInstance(SIGN_ALGORITHM)
            signature.initVerify(pubKey)
            signature.update(data.toByteArray())

            return signature.verify(Base64.decode(sign, Base64.DEFAULT))
        }

        // 私钥签名
        fun sign(privateKey: String, data: String): String {
            val base64Buffer = Base64.decode(privateKey, Base64.DEFAULT)
            val keySpec = PKCS8EncodedKeySpec(base64Buffer)
            val keyFactory = KeyFactory.getInstance(ALGORITHM)
            val priKey = keyFactory.generatePrivate(keySpec) as PrivateKey

            val signature = Signature.getInstance(SIGN_ALGORITHM)
            signature.initSign(priKey)
            signature.update(data.toByteArray())

            val byteArray = signature.sign()
            return Base64.encodeToString(byteArray, Base64.DEFAULT)
        }

        //生成公私钥对
        fun generateKeyPair(): Pair<String, String> {
            val keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM)
            val keypair = keyPairGenerator.genKeyPair()

            val private = Base64.encodeToString(keypair.private.encoded, Base64.DEFAULT)
            val public = Base64.encodeToString(keypair.public.encoded, Base64.DEFAULT)

            return Pair(private, public)
        }
    }
}