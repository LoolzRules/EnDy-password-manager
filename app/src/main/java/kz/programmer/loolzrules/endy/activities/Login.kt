package kz.programmer.loolzrules.endy.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kz.programmer.loolzrules.endy.App
import kz.programmer.loolzrules.endy.R
import java.security.MessageDigest
import kotlin.experimental.and

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_enter.setOnClickListener {
            val md = MessageDigest.getInstance("SHA-512")
            val digest = md.digest(et_decryption_key.text.toString().toByteArray())
            val sb = StringBuilder()

            for (i in digest.indices) {
                sb.append(Integer.toString((digest[i] and 0xff.toByte()) + 0x100, 16).substring(1))
            }

            val app = application as App
            app.makeAead(sb.toString())

            startActivity(Intent(this, Home::class.java))
            finish()

        }
    }
}
