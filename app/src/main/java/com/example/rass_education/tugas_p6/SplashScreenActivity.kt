package com.example.rass_education.tugas_p6

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.rass_education.R
import com.example.rass_education.tugas_p11.OnboardingActivity
import com.example.rass_education.tugas_p3.LoginActivity
import com.example.rass_education.tugas_p3.WelcomeActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // // BARU: SplashScreen dengan delay 2 detik
        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
            val isLogin = sharedPref.getBoolean("isLogin", false)
            val hasSeenOnboarding = sharedPref.getBoolean(OnboardingActivity.KEY_HAS_SEEN_ONBOARDING, false)

            // P11: splash memprioritaskan welcome, lalu onboarding sekali saja, lalu login.
            if (isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
            } else if (!hasSeenOnboarding) {
                startActivity(Intent(this, OnboardingActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 2000)
    }
}