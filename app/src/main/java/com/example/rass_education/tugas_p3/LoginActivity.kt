package com.example.rass_education.tugas_p3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rass_education.databinding.ActivityLoginBinding
import com.example.rass_education.tugas_p4.PageContract

class LoginActivity : AppCompatActivity() {

    // Inisialisasi ViewBinding
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            // Setelah login, masuk ke halaman utama di WelcomeActivity
            val intent = Intent(this, WelcomeActivity::class.java).apply {
                putExtra(PageContract.EXTRA_SHOW_LOGIN_SUCCESS, true)
            }
            startActivity(intent)
        }
    }
}