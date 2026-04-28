package com.example.rass_education.tugas_p3

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.rass_education.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // // MODIF: Set Toolbar
        setSupportActionBar(binding.toolbarWebView)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarWebView.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // // BARU: Setup WebView
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl("https://rass.alwaysdata.net/")
        }
    }
}