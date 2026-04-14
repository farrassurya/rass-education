package com.example.rass_education.tugas_p4

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.rass_education.R

class Custom1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom1)

        val title = intent.getStringExtra(PageContract.EXTRA_PAGE_TITLE)
            ?: getString(R.string.p4_home_title)
        val description = intent.getStringExtra(PageContract.EXTRA_PAGE_DESCRIPTION)
            ?: getString(R.string.p4_home_description)

        findViewById<android.widget.ImageButton>(R.id.btnBackCustom1).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<TextView>(R.id.tvSourceTitleCustom1).text = title
    }
}


