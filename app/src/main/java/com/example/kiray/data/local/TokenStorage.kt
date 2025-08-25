package com.example.kiray.data.local

import android.content.Context
import android.util.Base64
import android.util.Base64.DEFAULT
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TokenStorage @Inject constructor(
    @ApplicationContext private val context: Context
){
    private val prefs = "secure_prefs"
    private val keyIv = "jwt_iv"
    private val keyToken = "jwt_token"


    fun saveJwt(jwt: String) {
        val (iv, cipherText) = KeystoreUtil.encrypt(jwt)
        val prefs = context.getSharedPreferences(prefs, Context.MODE_PRIVATE)
        prefs.edit {
            putString(keyIv, Base64.encodeToString(iv,DEFAULT))
                .putString(keyToken, Base64.encodeToString(cipherText, DEFAULT))
        }
    }

    fun getJwt(): String? {
        val prefs = context.getSharedPreferences(prefs, Context.MODE_PRIVATE)
        val ivB64 = prefs.getString(keyIv, null) ?: return null
        val tokenB64 = prefs.getString(keyToken, null) ?: return null
        val iv = Base64.decode(ivB64, DEFAULT)
        val cipherText = Base64.decode(tokenB64, DEFAULT)
        return KeystoreUtil.decrypt(iv, cipherText)
    }

    fun clearJwt() {
        context.getSharedPreferences(prefs, Context.MODE_PRIVATE)
            .edit { remove(keyIv).remove(keyToken) }
    }
}