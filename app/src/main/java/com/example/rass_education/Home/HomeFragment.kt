package com.example.rass_education.Home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rass_education.R
import com.example.rass_education.data.local.AppDatabase
import com.example.rass_education.data.local.entity.History
import com.example.rass_education.databinding.FragmentHomeBinding
import com.example.rass_education.Home.tugas_p10.TabLayoutActivity
import com.example.rass_education.Home.tugas_p11.NewsAdapter
import com.example.rass_education.Home.tugas_p11.NewsRepository
import com.example.rass_education.Home.tugas_p11.OnboardingActivity
import com.example.rass_education.Home.tugas_p2.HitungBangunActivity
import com.example.rass_education.LoginActivity
import com.example.rass_education.Home.tugas_p4.Custom1Activity
import com.example.rass_education.Home.tugas_p4.Custom2Activity
import com.example.rass_education.Home.tugas_p6.WebViewActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val newsAdapter = NewsAdapter { article ->
        openNewsLink(article.url)
    }
    private val newsRepository = NewsRepository()
    
    // P12 : Integrasi Room - Inisialisasi Adapter dan Database
    private lateinit var historyAdapter: HistoryAdapter
    private val database by lazy { AppDatabase.getDatabase(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Farrassurya") ?: "Farrassurya"
        binding.tvWelcomeTitle.text = getString(R.string.p11_welcome_format, username)

        // Setup News RecyclerView
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.adapter = newsAdapter
        loadLatestNews()

        // P12 : Logic Setup RecyclerView untuk History
        setupHistoryRecyclerView()
        
        // P12 : Mengamati data history secara Real-time dari Room
        observeHistory()

        setupActionButtons()

        binding.btnLogout.setOnClickListener {
            showLogoutDialog(sharedPref)
        }
    }

    // P12 : Fungsi inisialisasi Adapter History dengan aksi hapus
    private fun setupHistoryRecyclerView() {
        historyAdapter = HistoryAdapter(
            onDelete = { history -> deleteHistoryItem(history) }
        )
        binding.rvHistory.adapter = historyAdapter
    }

    // P12 : Fungsi untuk menghapus satu item riwayat
    private fun deleteHistoryItem(history: History) {
        viewLifecycleOwner.lifecycleScope.launch {
            database.historyDao().deleteHistoryItem(history)
        }
    }

    // P12 : Logic untuk mengambil data history (Flow) dan update UI Visibility
    private fun observeHistory() {
        viewLifecycleOwner.lifecycleScope.launch {
            database.historyDao().getRecentHistory().collectLatest { historyList ->
                historyAdapter.submitList(historyList)
                
                // P12 : Update visibilitas jika data kosong agar fitur terlihat "aktif"
                if (historyList.isEmpty()) {
                    binding.tvEmptyHistory.visibility = View.VISIBLE
                    binding.rvHistory.visibility = View.GONE
                } else {
                    binding.tvEmptyHistory.visibility = View.GONE
                    binding.rvHistory.visibility = View.VISIBLE
                }
            }
        }
    }

    // P12 : Fungsi untuk menyimpan log aktivitas ke Room
    private fun saveHistory(action: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            database.historyDao().insertHistory(History(activityName = action))
        }
    }

    private fun setupActionButtons() {
        binding.btnRumus.setOnClickListener {
            // P12 : Simpan log aktivitas
            saveHistory("Membuka Rumus Lahan")
            startActivity(Intent(requireContext(), HitungBangunActivity::class.java))
        }

        binding.btnFocus.setOnClickListener {
            // P12 : Simpan log aktivitas
            saveHistory("Membuka Zona Pantau")
            startActivity(Intent(requireContext(), Custom1Activity::class.java))
        }

        binding.btnInspirasi.setOnClickListener {
            // P12 : Simpan log aktivitas
            saveHistory("Membuka Galeri Desa")
            startActivity(Intent(requireContext(), Custom2Activity::class.java))
        }

        binding.btnWebView.setOnClickListener {
            // P12 : Simpan log aktivitas
            saveHistory("Membuka Portal Web")
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }

        binding.btnTabLayout.setOnClickListener {
            // P12 : Simpan log aktivitas
            saveHistory("Membuka Tab Layout")
            startActivity(Intent(requireContext(), TabLayoutActivity::class.java))
        }
    }

    private fun showLogoutDialog(sharedPref: android.content.SharedPreferences) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.p4_logout_title)
            .setMessage(R.string.p4_logout_message)
            .setPositiveButton(R.string.p4_logout_yes) { _, _ ->
                sharedPref.edit {
                    remove("isLogin")
                    remove(OnboardingActivity.Companion.KEY_HAS_SEEN_ONBOARDING)
                }
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            }
            .setNegativeButton(R.string.p4_logout_no, null)
            .show()
    }

    private fun loadLatestNews() {
        val currentBinding = _binding ?: return
        currentBinding.tvNewsStatus.text = getString(R.string.p11_news_loading)
        currentBinding.tvNewsStatus.visibility = View.VISIBLE

        newsRepository.fetchLatestNews(limit = 5) { result ->
            val bindingSnapshot = _binding ?: return@fetchLatestNews
            result.onSuccess { articles ->
                newsAdapter.submitList(articles)
                bindingSnapshot.tvNewsStatus.visibility = if (articles.isEmpty()) View.VISIBLE else View.GONE
                if (articles.isEmpty()) {
                    bindingSnapshot.tvNewsStatus.text = getString(R.string.p11_news_empty)
                }
            }.onFailure {
                newsAdapter.submitList(emptyList())
                bindingSnapshot.tvNewsStatus.visibility = View.VISIBLE
                bindingSnapshot.tvNewsStatus.text = getString(R.string.p11_news_error)
            }
        }
    }

    private fun openNewsLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        runCatching {
            startActivity(intent)
        }.onFailure {
            Toast.makeText(requireContext(), R.string.p11_news_link_error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        binding.rvNews.adapter = null
        binding.rvHistory.adapter = null
        super.onDestroyView()
        _binding = null
    }
}