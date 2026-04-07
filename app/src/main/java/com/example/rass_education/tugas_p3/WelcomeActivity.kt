package com.example.rass_education.tugas_p3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rass_education.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Kode lainnya di sini
    }
}