package com.example.rass_education.Home.UTS

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rass_education.databinding.ActivityRegisterBinding
import com.example.rass_education.Home.UTS.VerifikasiActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // // BARU: ViewBinding Register
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val nama = binding.etNama.text.toString()
            val noHp = binding.etNoHp.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            // // BARU: Validasi Sederhana
            if (nama.isNotEmpty() && noHp.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                // // BARU: Intent ke VerifikasiActivity dengan membawa data
                val intent = Intent(this, VerifikasiActivity::class.java)
                intent.putExtra("nama", nama)
                intent.putExtra("no_hp", noHp)
                intent.putExtra("username", username)
                intent.putExtra("password", password)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}