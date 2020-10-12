package com.sizcorp.androiddeveloperarkdemy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.sizcorp.androiddeveloperarkdemy.Model.Pengguna
import kotlinx.android.synthetic.main.activity_daftar.*

class DaftarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)

        btBackLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btDaftar.setOnClickListener {
            actionDaftar()
        }
    }

    private fun actionDaftar() {
        val email= etRegistEmail.text.toString()
        val konfirmasiEmail = etConfirmEmail.text.toString()
        val password = etRegistPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Email/Password Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
            return
        }
        if (email != konfirmasiEmail){
            Toast.makeText(this, "Email Tidak Sama!", Toast.LENGTH_LONG).show()
            return
        }
        Log.d("DaftarActivity", "Email:" + email)
        Log.d("DaftarActivity", "Password: $password" )

        //input data ke firebase
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(!it.isSuccessful) return@addOnCompleteListener

                Log.d("Daftar", "Member Baru Bergabung dengan UID: ${it.result?.user?.uid}")

                kirimDataKeFirebase()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal : ${it.message}", Toast.LENGTH_LONG).show()
                Log.d("Register","Gagal Membuat Akun: ${it.message}")
            }

    }

    private fun kirimDataKeFirebase() {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val referensiData = FirebaseDatabase.getInstance().getReference("pengguna/$uid")

        val pengguna = Pengguna(uid, etRegistEmail.text.toString()) // Paket Data Pengguna
        referensiData.setValue(pengguna)
            .addOnSuccessListener {
                Log.d("Daftar", "Data Berhasil disimpan ke database")
                val intent = Intent(this, WelcomeMemberActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener{
                Log.d("Daftar", "Data gagal disimpan ke firebase: ${it.message}")
            }
    }
}