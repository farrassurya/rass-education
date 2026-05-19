package com.example.rass_education.tugas_p3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rass_education.R
import com.example.rass_education.databinding.ActivityWelcomeBinding
import com.example.rass_education.tugas_p7.AboutFragment
import com.example.rass_education.tugas_p7.HomeFragment
import com.example.rass_education.tugas_p7.ProfileFragment
import com.example.rass_education.tugas_p9.SettingsFragment

class WelcomeActivity : AppCompatActivity() {

    // // UPDATE: WelcomeActivity sebagai container Fragment
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // // BARU: Default Fragment (Home)
        replaceFragment(HomeFragment())

        // // BARU: Bottom Navigation Logic
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_about -> {
                    replaceFragment(AboutFragment())
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                // // REVISI: Tambahkan navigasi ke Settings yang sebelumnya terlewat
                R.id.nav_settings -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }
    }

    // // BARU: Function replaceFragment untuk navigasi BottomNav
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}