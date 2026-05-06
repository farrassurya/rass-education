package com.example.rass_education.UTS

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.rass_education.databinding.ActivityVerifikasiBinding
import com.example.rass_education.tugas_p3.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class VerifikasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifikasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // // BARU: ViewBinding Verifikasi
        binding = ActivityVerifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // // BARU: Ambil data dari Intent
        val nama = intent.getStringExtra("nama") ?: ""
        val noHp = intent.getStringExtra("no_hp") ?: ""
        val username = intent.getStringExtra("username") ?: ""
        val password = intent.getStringExtra("password") ?: ""

        binding.btnVerifikasi.setOnClickListener {
            val otpInput = binding.etOtp.text.toString()

            // // BARU: Logic Verifikasi (OTP = No HP)
            if (otpInput == noHp && otpInput.isNotEmpty()) {
                // // BARU: Simpan ke SharedPreferences
                val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("nama", nama)
                editor.putString("no_hp", noHp)
                editor.putString("username", username)
                editor.putString("password", password)
                editor.apply()

                // // BARU: Berhasil simpan, lanjut ke Login
                AlertDialog.Builder(this)
                    .setTitle("Registrasi Berhasil")
                    .setMessage("Akun Anda telah terdaftar. Silahkan login.")
                    .setPositiveButton("Login Sekarang") { _, _ ->
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    .setCancelable(false)
                    .show()
            } else {
                // // BARU: Error menggunakan MaterialAlertDialog
                MaterialAlertDialogBuilder(this)
                    .setTitle("Verifikasi Gagal")
                    .setMessage("Kode OTP yang Anda masukkan salah atau kosong.")
                    .setPositiveButton("Coba Lagi", null)
                    .show()
            }
        }
    }
}