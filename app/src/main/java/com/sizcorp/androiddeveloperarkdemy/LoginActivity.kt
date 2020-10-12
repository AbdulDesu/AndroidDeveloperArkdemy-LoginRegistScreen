package com.sizcorp.androiddeveloperarkdemy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        btRegist.setOnClickListener {
            val intent = Intent(this,DaftarActivity::class.java)
            startActivity(intent)
            finish()
        }

        btLogin.setOnClickListener {
            val email = etLoginEmail.text.toString()
            val password = etLoginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Email/Password Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

                //Firebase Authentication
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener {
                        Log.d("LoginActivity", "Login Berhasil")
                        val intent = Intent(this, WelcomeMemberActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Periksa Kembali Data yang dimasukan", Toast.LENGTH_LONG).show()
                        etLoginEmail.text
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
            Log.d("LoginActivity", "Percobaan Login: $email")
            Log.d("LoginActivity", "Menggunakan Password: $password")
        }
    }
}