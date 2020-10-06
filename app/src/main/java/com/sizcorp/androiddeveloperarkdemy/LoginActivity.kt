package com.sizcorp.androiddeveloperarkdemy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btRegist.setOnClickListener {
            val intent = Intent(this,DaftarActivity::class.java)
            startActivity(intent)
            finish()
        }

        btLogin.setOnClickListener {
            // FireBase Auth Login Here
        }
    }
}