package com.example.rass_education.tugas_p3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.rass_education.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // // BARU: SplashScreen dengan delay 2 detik
        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            val isLogin = sharedPref.getBoolean("isLogin", false)

            // Logic: Cek isLogin
            if (isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 2000)
    }
}