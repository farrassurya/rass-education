package com.example.rass_education.tugas_p7

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rass_education.R
import com.example.rass_education.databinding.FragmentHomeBinding
import com.example.rass_education.tugas_p2.HitungBangunActivity
import com.example.rass_education.tugas_p3.LoginActivity
import com.example.rass_education.tugas_p4.Custom1Activity
import com.example.rass_education.tugas_p4.Custom2Activity
import com.example.rass_education.tugas_p6.WebViewActivity
import com.example.rass_education.tugas_p11.NewsAdapter
import com.example.rass_education.tugas_p11.NewsRepository
import com.example.rass_education.tugas_p11.OnboardingActivity
import com.example.rass_education.tugas_p10.TabLayoutActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val newsAdapter = NewsAdapter { article ->
        openNewsLink(article.url)
    }
    private val newsRepository = NewsRepository()

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

        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.adapter = newsAdapter

        // P11: RecyclerView ditempatkan di dalam NestedScrollView agar daftar berita tetap bisa digulir.
        loadLatestNews()

        binding.btnRumus.setOnClickListener {
            startActivity(Intent(requireContext(), HitungBangunActivity::class.java))
        }

        binding.btnFocus.setOnClickListener {
            startActivity(Intent(requireContext(), Custom1Activity::class.java))
        }

        binding.btnInspirasi.setOnClickListener {
            startActivity(Intent(requireContext(), Custom2Activity::class.java))
        }

        binding.btnWebView.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }

        binding.btnTabLayout.setOnClickListener {
            startActivity(Intent(requireContext(), TabLayoutActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.p4_logout_title)
                .setMessage(R.string.p4_logout_message)
                .setPositiveButton(R.string.p4_logout_yes) { _, _ ->
                    sharedPref.edit {
                        remove("isLogin")
                        // P11: reset onboarding flag saat logout agar muncul lagi saat login berikutnya.
                        remove(OnboardingActivity.KEY_HAS_SEEN_ONBOARDING)
                    }
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton(R.string.p4_logout_no, null)
                .show()
        }
    }

    private fun loadLatestNews() {
        val currentBinding = _binding ?: return
        currentBinding.tvNewsStatus.text = getString(R.string.p11_news_loading)
        currentBinding.tvNewsStatus.visibility = View.VISIBLE

        // P11: berita diambil dari API publik Spaceflight News yang tidak butuh API key.
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
        // P11: klik item berita membuka link sumber agar user bisa membaca artikel lengkap.
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        runCatching {
            startActivity(intent)
        }.onFailure {
            Toast.makeText(requireContext(), R.string.p11_news_link_error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        binding.rvNews.adapter = null
        super.onDestroyView()
        _binding = null
    }
}