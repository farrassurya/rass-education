package com.example.rass_education.tugas_p11

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.rass_education.R
import com.example.rass_education.databinding.ActivityOnboardingBinding
import com.example.rass_education.tugas_p3.LoginActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var pageCallback: ViewPager2.OnPageChangeCallback

    private val onboardingItems = listOf(
        OnboardingItem(
            R.string.p11_onboarding_title_1,
            R.string.p11_onboarding_desc_1,
            android.R.drawable.ic_dialog_info
        ),
        OnboardingItem(
            R.string.p11_onboarding_title_2,
            R.string.p11_onboarding_desc_2,
            android.R.drawable.ic_menu_view
        ),
        OnboardingItem(
            R.string.p11_onboarding_title_3,
            R.string.p11_onboarding_desc_3,
            android.R.drawable.ic_media_play
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // P11: ViewPager2 dipakai untuk onboarding agar alurnya tetap sederhana dan jelas.
        val adapter = OnboardingPagerAdapter(onboardingItems)
        binding.viewPagerOnboarding.adapter = adapter
        binding.viewPagerOnboarding.isUserInputEnabled = true
        binding.viewPagerOnboarding.offscreenPageLimit = 1
        binding.viewPagerOnboarding.setPageTransformer(MarginPageTransformer(24))

        // P11: dots indicator menandai halaman aktif onboarding.
        binding.dotsIndicator.attachTo(binding.viewPagerOnboarding)

        pageCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateButtonState(position)
            }
        }
        binding.viewPagerOnboarding.registerOnPageChangeCallback(pageCallback)
        updateButtonState(binding.viewPagerOnboarding.currentItem)

        binding.btnSkip.setOnClickListener {
            // P11: tombol Lewati disediakan agar user tetap bisa lanjut login saat swipe terkendala.
            finishOnboardingAndOpenLogin()
        }

        binding.btnNext.setOnClickListener {
            val nextPage = binding.viewPagerOnboarding.currentItem + 1
            if (nextPage <= onboardingItems.lastIndex) {
                binding.viewPagerOnboarding.currentItem = nextPage
            }
        }

        binding.btnStart.setOnClickListener {
            // P11: tombol Ayo Mulai tetap khusus halaman terakhir onboarding.
            finishOnboardingAndOpenLogin()
        }
    }

    private fun updateButtonState(position: Int) {
        val isLastPage = position == onboardingItems.lastIndex
        binding.btnStart.isVisible = isLastPage
        binding.btnNext.isVisible = !isLastPage
    }

    private fun finishOnboardingAndOpenLogin() {
        // P11: onboarding ditandai selesai agar splash tidak menampilkan onboarding berulang.
        getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit {
            putBoolean(KEY_HAS_SEEN_ONBOARDING, true)
        }
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }

    override fun onDestroy() {
        if (::pageCallback.isInitialized) {
            binding.viewPagerOnboarding.unregisterOnPageChangeCallback(pageCallback)
        }
        super.onDestroy()
    }

    companion object {
        const val PREF_NAME = "user_pref"
        const val KEY_HAS_SEEN_ONBOARDING = "hasSeenOnboarding"
    }
}




