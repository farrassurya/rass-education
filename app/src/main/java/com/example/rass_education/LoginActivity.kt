package com.example.rass_education

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.rass_education.Home.UTS.RegisterActivity
import com.example.rass_education.Home.WelcomeActivity
import com.example.rass_education.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // // BARU: Navigasi ke RegisterActivity
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            // // MODIF: Logic Login berdasarkan soal
            val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
            val savedUsername = sharedPref.getString("username", "")
            val savedPassword = sharedPref.getString("password", "")

            val isValidOldRule = username == password && username.isNotEmpty()
            val isValidSavedData = username == savedUsername && password == savedPassword && username.isNotEmpty()

            if (isValidOldRule || isValidSavedData) {
                // // UPDATE: Simpan status login
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.apply()

                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // // UPDATE: AlertDialog jika salah/kosong
                AlertDialog.Builder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Username atau Password salah")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }
}