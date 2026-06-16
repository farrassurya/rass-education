package com.example.rass_education

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.rass_education.LoginActivity
import com.example.rass_education.Home.WelcomeActivity
import com.example.rass_education.Home.tugas_p11.OnboardingActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // // BARU: SplashScreen dengan delay 2 detik
        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
            val isLogin = sharedPref.getBoolean("isLogin", false)
            val hasSeenOnboarding = sharedPref.getBoolean(OnboardingActivity.Companion.KEY_HAS_SEEN_ONBOARDING, false)

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