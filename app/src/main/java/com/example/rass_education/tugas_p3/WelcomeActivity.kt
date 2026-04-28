package com.example.rass_education.tugas_p3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.rass_education.databinding.ActivityWelcomeBinding
import com.example.rass_education.tugas_p2.HitungBangunActivity
import com.example.rass_education.tugas_p4.Custom1Activity
import com.example.rass_education.tugas_p4.Custom2Activity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // // MODIF: Tombol Back
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // // UPDATE: Nama dari SharedPreferences
        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Farrassurya")
        binding.tvWelcomeTitle.text = "Welcome $username!"

        // // MODIF: Navigasi Menu Rumus
        binding.btnRumus.setOnClickListener {
            startActivity(Intent(this, HitungBangunActivity::class.java))
        }

        // // MODIF: Navigasi Menu Focus
        binding.btnFocus.setOnClickListener {
            startActivity(Intent(this, Custom1Activity::class.java))
        }

        // // UPDATE: Navigasi Inspirasi Belajar ke Custom2Activity
        binding.btnInspirasi.setOnClickListener {
            startActivity(Intent(this, Custom2Activity::class.java))
        }

        // // BARU: Tombol Web View Bina Desa
        binding.btnWebView.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }

        // // MODIF: Logout
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { _, _ ->
                    sharedPref.edit().remove("isLogin").apply()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Tidak", null)
                .show()
        }
    }
}