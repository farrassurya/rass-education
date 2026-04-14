package com.example.rass_education.tugas_p3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rass_education.R
import com.example.rass_education.databinding.ActivityWelcomeBinding
import com.example.rass_education.tugas_p2.HitungBangunActivity
import com.example.rass_education.tugas_p4.Custom1Activity
import com.example.rass_education.tugas_p4.Custom2Activity
import com.example.rass_education.tugas_p4.PageContract
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AlertDialog

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var pageTitle: String
    private lateinit var pageDescription: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pageTitle = getString(R.string.p4_home_title)
        pageDescription = getString(R.string.p4_home_description)

        binding.tvWelcomeTitle.text = pageTitle
        binding.tvWelcomeDescription.text = pageDescription

        if (savedInstanceState == null && intent.getBooleanExtra(PageContract.EXTRA_SHOW_LOGIN_SUCCESS, false)) {
            Snackbar.make(binding.welcomeRoot, R.string.p4_login_success, Snackbar.LENGTH_SHORT).show()
            intent.removeExtra(PageContract.EXTRA_SHOW_LOGIN_SUCCESS)
        }

        binding.btnBackWelcome.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnGoBangun.setOnClickListener {
            navigateWithDescription(HitungBangunActivity::class.java)
        }

        binding.btnGoCustom1.setOnClickListener {
            navigateWithDescription(Custom1Activity::class.java)
        }

        binding.btnGoCustom2.setOnClickListener {
            navigateWithDescription(Custom2Activity::class.java)
        }

        binding.btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }
    }

    private fun navigateWithDescription(target: Class<*>) {
        val intent = Intent(this, target).apply {
            putExtra(PageContract.EXTRA_PAGE_TITLE, pageTitle)
            putExtra(PageContract.EXTRA_PAGE_DESCRIPTION, pageDescription)
        }
        startActivity(intent)
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle(R.string.p4_logout_title)
            .setMessage(R.string.p4_logout_message)
            .setPositiveButton(R.string.p4_logout_yes) { _, _ ->
                val intent = Intent(this, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
            }
            .setNegativeButton(R.string.p4_logout_no) { dialog, _ ->
                dialog.dismiss()
                Snackbar.make(binding.welcomeRoot, R.string.p4_logout_cancelled, Snackbar.LENGTH_SHORT).show()
            }
            .show()
    }
}