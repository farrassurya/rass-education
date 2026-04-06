package com.example.rass_education.tugas_p2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.rass_education.R

class HitungBangunActivity : AppCompatActivity() {

    // Identitas Logcat untuk tugas praktikum
    private val TAG = "TugasP2_Hitung"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hitung_bangun)

        // 1. Inisialisasi Layout Container (Biar bisa disembunyiin/tampilin)
        val layoutMenu = findViewById<LinearLayout>(R.id.layoutMenu)
        val layoutPersegi = findViewById<LinearLayout>(R.id.layoutPersegi)
        val layoutTabung = findViewById<LinearLayout>(R.id.layoutTabung)

        // 2. Inisialisasi Tombol Navigasi (Mulai Hitung & Kembali)
        val btnMenuPersegi = findViewById<Button>(R.id.btnMenuPersegi)
        val btnMenuTabung = findViewById<Button>(R.id.btnMenuTabung)
        val btnBackPersegi = findViewById<ImageButton>(R.id.btnBackPersegi)
        val btnBackTabung = findViewById<ImageButton>(R.id.btnBackTabung)

        // 3. Inisialisasi Komponen Hitung Luas
        val etPanjang = findViewById<EditText>(R.id.etPanjang)
        val etLebar = findViewById<EditText>(R.id.etLebar)
        val btnHitungLuas = findViewById<Button>(R.id.btnHitungLuas)
        val tvHasilLuas = findViewById<TextView>(R.id.tvHasilLuas)

        // 4. Inisialisasi Komponen Hitung Volume
        val etJari = findViewById<EditText>(R.id.etJari)
        val etTinggi = findViewById<EditText>(R.id.etTinggi)
        val btnHitungVolume = findViewById<Button>(R.id.btnHitungVolume)
        val tvHasilVolume = findViewById<TextView>(R.id.tvHasilVolume)

        // --- LOGIKA NAVIGASI ---

        // Klik "Mulai Hitung" di Persegi Panjang
        btnMenuPersegi.setOnClickListener {
            layoutMenu.visibility = View.GONE
            layoutPersegi.visibility = View.VISIBLE
            Log.d(TAG, "Navigasi ke Halaman Persegi Panjang")
        }

        // Klik "Mulai Hitung" di Tabung
        btnMenuTabung.setOnClickListener {
            layoutMenu.visibility = View.GONE
            layoutTabung.visibility = View.VISIBLE
            Log.d(TAG, "Navigasi ke Halaman Tabung")
        }

        // Tombol Kembali
        btnBackPersegi.setOnClickListener {
            layoutPersegi.visibility = View.GONE
            layoutMenu.visibility = View.VISIBLE
        }

        btnBackTabung.setOnClickListener {
            layoutTabung.visibility = View.GONE
            layoutMenu.visibility = View.VISIBLE
        }

        // --- LOGIKA PERHITUNGAN ---

        btnHitungLuas.setOnClickListener {
            val p = etPanjang.text.toString()
            val l = etLebar.text.toString()

            if (p.isNotEmpty() && l.isNotEmpty()) {
                val hasil = p.toDouble() * l.toDouble()
                tvHasilLuas.text = "Hasil: $hasil cm²"
                Log.i(TAG, "Dihitung Luas: $hasil")
            } else {
                Toast.makeText(this, "Mohon isi semua data!", Toast.LENGTH_SHORT).show()
            }
        }

        btnHitungVolume.setOnClickListener {
            val r = etJari.text.toString()
            val t = etTinggi.text.toString()

            if (r.isNotEmpty() && t.isNotEmpty()) {
                val hasil = 3.14 * r.toDouble() * r.toDouble() * t.toDouble()
                tvHasilVolume.text = "Hasil: $hasil cm³"
                Log.i(TAG, "Dihitung Volume: $hasil")
            } else {
                Toast.makeText(this, "Mohon isi semua data!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}