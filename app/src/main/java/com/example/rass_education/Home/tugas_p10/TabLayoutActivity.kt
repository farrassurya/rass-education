package com.example.rass_education.Home.tugas_p10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rass_education.databinding.ActivityTabLayoutBinding
import com.google.android.material.tabs.TabLayoutMediator

class TabLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupViewPager()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun setupViewPager() {
        val adapter = LandPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Daftar Lahan"
                    tab.icon = null // Bisa ditambah icon jika ada drawable-nya
                }
                1 -> {
                    tab.text = "Grid Lahan"
                }
            }
        }.attach()
        
        // Contoh penambahan badge pada tab pertama (List)
        val badge = binding.tabLayout.getTabAt(0)?.orCreateBadge
        badge?.isVisible = true
        badge?.number = 5 // Menandakan ada 5 record tanah
    }
}
