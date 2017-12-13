package kz.programmer.loolzrules.endy

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.crypto.tink.Aead
import com.google.crypto.tink.Config
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadFactory
import com.google.crypto.tink.aead.AeadKeyTemplates
import com.google.crypto.tink.config.TinkConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import java.io.IOException
import java.security.GeneralSecurityException


class App : Application() {
    var aead: Aead? = null
    private val PREF_FILENAME = "ENDY_KEY_PREF_FILE"
    private val KEYSET_NAME = "ENDY_KEYSET_NAME"

    fun makeAead(key: String) {
        try {
            val realKey = if (key.length % 2 == 1) key.padEnd(key.length + 1, '0') else key
            getSharedPreferences(PREF_FILENAME, Context.MODE_PRIVATE).edit()
                    .putString(KEYSET_NAME, realKey).commit()
            Log.i("LOLF", "Keyset Ready")
            Config.register(TinkConfig.TINK_1_0_0)
            // TODO: for some reason he is unable to read keyset, this is the main reason for error, IMO
            aead = AeadFactory.getPrimitive(getOrGenerateNewKeysetHandle())
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class, GeneralSecurityException::class)
    private fun getOrGenerateNewKeysetHandle(): KeysetHandle {
        return AndroidKeysetManager.Builder()
                .withSharedPref(applicationContext, KEYSET_NAME, PREF_FILENAME)
                .withKeyTemplate(AeadKeyTemplates.AES256_GCM)
                .doNotUseKeystore()
                .build()
                .keysetHandle
    }
}