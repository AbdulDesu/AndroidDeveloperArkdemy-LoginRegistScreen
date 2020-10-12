package com.sizcorp.androiddeveloperarkdemy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sizcorp.androiddeveloperarkdemy.Model.Pengguna
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_welcome_member.*

class WelcomeMemberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_member)


        verfikasiAkunTerhubung()
        dapatkanAkun()

        btLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            Toast.makeText(this, "Berhasil Logout", Toast.LENGTH_LONG).show()
        }
    }
    private fun verfikasiAkunTerhubung() {
        val akun = FirebaseAuth.getInstance().uid

        if (akun == null){
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun dapatkanAkun() {
        val referensiAkun = FirebaseDatabase.getInstance().getReference("/pengguna")
        referensiAkun.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    Log.d("Welcome", "Berhasil Mendapatkan Data Pengguna Dari Firebase")
                    val pengguna = it.getValue(Pengguna::class.java)
                    if (pengguna != null){
                        akunSaatIni()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Welcome", "Gagal Terhubung Dengan Firebase")
            }
        })
    }

    private fun akunSaatIni() {
        val pengguna = FirebaseAuth.getInstance().currentUser
        pengguna?.let {
            val email = pengguna.email
            tvEmailLogedIn.text = email!!.capitalize()
        }
    }




}