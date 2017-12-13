package kz.programmer.loolzrules.endy.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.crypto.tink.Aead
import kotlinx.android.synthetic.main.activity_add_new_entry.*
import kz.programmer.loolzrules.endy.App
import kz.programmer.loolzrules.endy.DBHelper
import kz.programmer.loolzrules.endy.DBHelper.Companion.COLUMN_PASSWORDS
import kz.programmer.loolzrules.endy.DBHelper.Companion.COLUMN_RESOURCES
import kz.programmer.loolzrules.endy.DBHelper.Companion.COLUMN_USERNAMES
import kz.programmer.loolzrules.endy.R
import kz.programmer.loolzrules.endy.dataModels.DBEntryEncrypted
import kz.programmer.loolzrules.endy.database
import org.jetbrains.anko.db.insert
import java.io.UnsupportedEncodingException
import java.security.GeneralSecurityException

class AddNewEntry : AppCompatActivity() {

    private var aead: Aead? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_entry)

        button_cancel.setOnClickListener {
            finish()
        }

        aead = (application as App).aead

        button_confirm.setOnClickListener {

            val encryptedEntry = DBEntryEncrypted(
                    encrypt(et_new_resource.text.toString()),
                    encrypt(et_new_username.text.toString()),
                    encrypt(et_new_password.text.toString()))

            database.use {
                insert(DBHelper.TABLE_NAME,
                        COLUMN_RESOURCES to encryptedEntry.getResource(),
                        COLUMN_USERNAMES to encryptedEntry.getUsername(),
                        COLUMN_PASSWORDS to encryptedEntry.getPassword()
                )
            }
            finish()
        }
    }

    @Throws(UnsupportedEncodingException::class, GeneralSecurityException::class, IllegalArgumentException::class)
    private fun encrypt(string: String): ByteArray {
        var toReturn: ByteArray? = null
        try {
            toReturn = aead?.encrypt(string.toByteArray(Charsets.UTF_8), null)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        return if (toReturn != null) toReturn else ByteArray(0)
    }
}
